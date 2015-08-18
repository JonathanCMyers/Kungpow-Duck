/*
 * Description: 		Not sure how I'm going to do this yet, this is just one attempt at saving.
 * Date Created: 		Jun 12, 2015
 * Date Last Modified: 	Jun 12, 2015
 * Modification Notes:	
 */
package UtilityClasses;

import java.io.*;
import java.net.URL;
import java.util.*;

import Entity.PartyMember;
import GameState.Level1State;

import org.apache.commons.io.*;

public class Save {
	//private RandomAccessFile raf;
	private byte[] data;
	private File file;
	
	public Save(String fileName) {
		try {
			// read something about java.utils.prefs.Preferences
			file = new File(fileName);
			if(!file.exists()){
				FileOutputStream out = new FileOutputStream(fileName);
				out.close();
				System.out.println("file made");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(byte[] data) throws IOException {
		this.data = data;
		System.out.println(data.length);
		if(file.length() > 1000){
			System.out.println("Exists");
			// Prompt user to overwrite the save file
			// Allow user to overwrite save file
		}
		else{
		try {
			FileOutputStream out = new FileOutputStream(file);
			out.write(data);
			out.close();
			//IOUtils.write(data, output);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}
	}
	
	public Level1State read() {
		try{
			data = new byte[(int) file.length()];
			FileInputStream in = new FileInputStream(file);
			in.read(data);
			in.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		Level1State l1s = Level1State.deserialize(data);
		return l1s;
	}
}
