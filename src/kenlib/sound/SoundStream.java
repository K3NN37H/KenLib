/*
 *  Copyright (c) 2012, 2014 Kenneth C.
 *
 *  This file is part of KenLib.
 *
 *  KenLib is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  KenLib is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with KenLib.  If not, see <http://www.gnu.org/licenses/>.
 */
package kenlib.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The <code>SoundStream</code> class is an easy-to-use implementation of the Java Sound API's
 * {@link SourceDataLine} to stream audio from a file for playing.
 * @author Kenneth C.
 * @version 1.0.1
 */
public class SoundStream extends SoundBase{
	private SourceDataLine soundLine;
	private volatile AudioInputStream ais;
	private Thread lineWriter;
	private volatile boolean killThread;
	
	/**
	 * Creates a new SoundStream with the specified audio file
	 * @param file A <code>File</code> object representing the audio file 
	 */
	public SoundStream(File file) {
		try {
			killThread = false;
			soundFile = file;
			ais = AudioSystem.getAudioInputStream(soundFile);
			AudioFileFormat af = AudioSystem.getAudioFileFormat(soundFile);
			soundLine = AudioSystem.getSourceDataLine(af.getFormat());
			soundLine.open(af.getFormat());
			initLineWriter();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new SoundStream with the specified string
	 * @param loc The location or filename of the audio file
	 */
	public SoundStream(String loc) {
		this(new File(loc));
	}
	
	/**
	 * Initializes the audio I/O thread that will write to the sound line
	 */
	private void initLineWriter() {
		lineWriter = new Thread() {
			public void run() {
				byte[] data = new byte[1024];
				try {
					while(!killThread) {
						if(playing) {
							if(ais.available() > 0) {
								ais.read(data);
								soundLine.write(data, 0, data.length);
							}
							else if(repeating) {
								resetAudio();
							}
							else {
								SoundStream.this.stop();
							}
						}
						else {
							threadPause();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		lineWriter.start();
		System.out.println("running!");
	}
	
	private synchronized void threadPause() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void threadResume() {
		notify();
	}
	
	/**
	 * Starts the audio playback
	 */
	@Override
	public void start() {
		soundLine.start();
		playing = true;
		threadResume();
	}

	/**
	 * Stops the audio playback, flushes the audio buffer and resets the audio
	 * back to the beginning.
	 */
	@Override
	public void stop() {
		pause();
		soundLine.flush();
		resetAudio();
	}
	
	/**
	 * Pauses the audio playback.  This method does not flush the buffer,
	 * therefore allowing continuity of sound on resuming.
	 */
	@Override
	public void pause() {
		soundLine.stop();
		playing = false;
	}
	
	/**
	 * Closes the audio file, terminates the audio thread and releases system resources
	 */
	public void close() {
		killThread = true;
		threadResume();
		try {
			ais.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		soundLine.close();
	}
	
	/**
	 * Sets whether the audio file should repeat when the end is reached.
	 */
	@Override
	public void setRepeat(boolean repeat) {
		repeating = repeat;
	}
	
	/**
	 * Sets the volume at which the sound is played at
	 * @param value A float indicating the volume level
	 * @return <code>true</code> if the volume was successfully set, otherwise <code>false</code>
	 */
	@Override
	public boolean setVolume(float value) {
		if(soundLine.isControlSupported(FloatControl.Type.VOLUME)) {
			FloatControl volume = (FloatControl) soundLine.getControl(FloatControl.Type.VOLUME);
			volume.setValue(value);
			return true;
		}
		else if(soundLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
			FloatControl volume = (FloatControl) soundLine.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(value);
			return true;
		}
		return false;
	}

	/**
	 * Resets the audio position back to the beginning.
	 */
	private void resetAudio() {
		try {
			ais.close();
			ais = AudioSystem.getAudioInputStream(soundFile);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
