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

/**
 * The <code>SoundBase</code> class is the base for classes implementing
 * the Java Sound API.
 * @author Kenneth C.
 * @version 1.0.0
 */
public abstract class SoundBase {
	protected boolean playing;
	protected boolean repeating;
	protected File soundFile;
	
	public abstract void start();
	public abstract void stop();
	public abstract void pause();
	public abstract boolean setVolume(float value);	
	public abstract void setRepeat(boolean repeat);
	
	/**
	 * Returns the location string passed in on initialization
	 * @return A string with the file's location
	 */
	public String getPath()	{
		return soundFile.getPath();
	}
	
	/**
	 * Returns whether the sound file is currently being played
	 * @return A boolean indicating if it's playing
	 */
	public boolean isPlaying() {
		return playing;
	}
	
	/**
	 * Returns whether the sound will repeat at the end
	 * @return A boolean indicating if it's repeating
	 */
	public boolean isRepeating() {
		return repeating;
	}
}
