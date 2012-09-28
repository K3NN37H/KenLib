/*
 *  Copyright (c) 2012 Kenneth Chan
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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The <code>SoundClip</code> class is an easy-to-use implementation of the Java Sound API's
 * <code>{@link Clip}</code> to play audio.
 * @author Kenneth C.
 * @version 1.0.0
 */
public class SoundClip extends SoundBase{
	private Clip soundClip;
	
	/**
	 * Constructs a new sound clip with the specified file.  The audio file will be loaded
	 * into memory and be ready for playback.
	 * @param file A File object representing the file to be loaded
	 */
	public SoundClip(File file) {
		soundFile = file;
		try {
			soundClip = AudioSystem.getClip();
			AudioInputStream aus = AudioSystem.getAudioInputStream(soundFile);
			soundClip.open(aus);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructs a new sound clip with the file at the path specified by the string
	 * @param loc A string containing the path to the audio file
	 * @see #SoundClip(File)
	 */
	public SoundClip(String loc) {
		this(new File(loc));
	}
	
	/**
	 * Starts playing the sound clip.
	 */
	@Override
	public void start() {
		soundClip.start();
		playing = true;
	}
	
	/**
	 * Stops the playback of the sound clip, flushes the audio buffer and resets the
	 * audio back to the beginning.
	 */
	@Override
	public void stop() {
		pause();
		soundClip.flush();
		soundClip.setFramePosition(0);
	}
	
	/**
	 * Pauses the playback of the sound clip.  This method does not flush the buffer,
	 * therefore allowing continuity of sound on resuming.
	 */
	@Override
	public void pause() {
		soundClip.stop();
		playing = false;
	}
	
	/**
	 * Sets the volume at which the sound is played at
	 * @param value A float indicating the volume level
	 * @return <code>true</code> if the volume was successfully set, otherwise <code>false</code>
	 */
	@Override
	public boolean setVolume(float value) {
		if(soundClip.isControlSupported(FloatControl.Type.VOLUME)) {
			FloatControl volume = (FloatControl) soundClip.getControl(FloatControl.Type.VOLUME);
			volume.setValue(value);
			return true;
		}
		else if(soundClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
			FloatControl volume = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(value);
			return true;
		}
		return false;
	}
	
	/**
	 * Sets whether the sound clip should repeat when it reaches the end<br>
	 * <b>Note:</b> Turning on repeat will cause the sound clip to start playing immediately
	 * @param repeat A boolean to set the repeat status
	 */
	@Override
	public void setRepeat(boolean repeat) {
		repeating = repeat;
		if(repeat) {
			soundClip.loop(Clip.LOOP_CONTINUOUSLY);
			soundClip.setLoopPoints(0, -1);
		}
		else {
			soundClip.loop(0);
		}
	}
	
}
