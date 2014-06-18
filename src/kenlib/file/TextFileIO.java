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
package kenlib.file;
import java.io.*;
import java.util.ArrayList;

public class TextFileIO {
	
	/**
	 * Reads an entire text file and returns it as a String array
	 * @param file The location or filename of the file
	 * @return The data as an array of Strings
	 * @see #readFile(File)
	 */
	public static String[] readFile(String file){
		return readFile(new File(file));
	}
	
	/**
	 * Reads an entire text file and returns it as a String array
	 * @param file A <code>File</object> representing the text file
	 * @return The data as an array of Strings if successful, otherwise a zero-length String array
	 */
	public static String[] readFile(File file){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line;
			ArrayList<String> lines = new ArrayList<String>();
			while((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
			String[] output = new String[lines.size()];
			return lines.toArray(output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String[0];
	}
	
	/**
	 * Writes the given array of Strings into a text file 
	 * @param file The location or filename of the text file
	 * @param lines The data to be written
	 * @see #writeFile(File, String[])
	 */
	public static void writeFile(String file, String[] lines) {
		writeFile(new File(file), lines);
	}
	
	/**
	 * Writes the given array of Strings into a text file 
	 * @param file A <code>File</code> object representing the text file
	 * @param lines The data to be written
	 */
	public static void writeFile(File file, String[] lines){
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			for(String s : lines){
				pw.println(s);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a single String of text, appended to the end of a text file
	 * @param file The location or filename of the file
	 * @param line The String to be written
	 * @see #writeFile(File, String[])
	 */
	public static void writeLine(String file, String line) {
		writeLine(new File(file), line);
	}
	
	/**
	 * Writes a single String of text, appended to the end of a text file
	 * @param file A <code>File</object> object representing the text file
	 * @param line The String to be written
	 */
	public static void writeLine(File file, String line){
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
			pw.println(line);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
