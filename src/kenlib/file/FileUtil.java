/*
 *  Copyright (c) 2012 Kenneth C.
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
package kenlib.file;
import java.io.*;

public class FileUtil {
	
	/**
	 * Copies a file from the specified source to the specified destination 
	 * @param src A string representing the path to the file to be copied
	 * @param dest A File representing the path to the location to copy to
	 * @return A boolean indicating whether the action was successful
	 * @see #copyFile(File, File)
	 */
	public static boolean copyFile(String src, String dest) {
		return copyFile(new File(src), new File(dest));
	}
	
	/**
	 * Copies a file from the specified source to the specified destination 
	 * @param src The file to be copied
	 * @param dest The location of where to create the copy
	 * @return A boolean indicating whether the action was successful
	 */
	public static boolean copyFile(File src, File dest) {
		try {
			FileInputStream fis = new FileInputStream(src);
			FileOutputStream fos = new FileOutputStream(dest);
			byte[] data = new byte[1024];
			while(fis.read(data) != -1) {
				fos.write(data);
			}
			fis.close();
			fos.flush();
			fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Moves a file from the specified source to the specified destination
	 * @param src The file to be moved
	 * @param dest The location of where to move the file
	 * @return <code>true</code> if the move was successful, otherwise <code>false</code>
	 * @see #moveFile(File, File)
	 */
	public static boolean moveFile(String src, String dest) {
		return moveFile(new File(src), new File(dest));
	}
	
	/**
	 * Moves a file from the specified source to the specified destination
	 * @param src The file to be moved
	 * @param dest The location of where to move the file
	 * @return <code>true</code> if the move was successful, otherwise <code>false</code>
	 */
	public static boolean moveFile(File src, File dest) {
		if(copyFile(src, dest)) {
			src.delete();
			return true;
		}
		return false;
	}
}
