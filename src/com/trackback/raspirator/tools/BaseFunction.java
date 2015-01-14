package com.trackback.raspirator.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
<<<<<<< HEAD
import java.io.InputStream;
=======
>>>>>>> 849731cb6b4e1dfc4b46ca61a9a98d9c2619683c
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import com.trackback.raspirator.Main;

public class BaseFunction {
	private Preferences pref = Preferences.systemNodeForPackage(getClass());
	public BaseFunction() {
	}

	/**
	 * Convert milliseconds to hh:mm:ss time format
	 * 
	 * @param <long> millis - time
	 * 
	 * @return <String> formated - String hh:mm:ss
	 */
	public String formatTime(long millis) {
		String output = "00:00:00";
		long seconds = millis / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;

		seconds = seconds % 60;
		minutes = minutes % 60;
		hours = hours % 60;

		String secondsD = String.valueOf(seconds);
		String minutesD = String.valueOf(minutes);
		String hoursD = String.valueOf(hours);

		if (seconds < 10)
			secondsD = "0" + seconds;
		if (minutes < 10)
			minutesD = "0" + minutes;
		if (hours < 10)
			hoursD = "0" + hours;

		output = hoursD + " : " + minutesD + " : " + secondsD;
		return output;
	}

	public void writeToFile(String str, String path){
		try {
			PrintWriter writer =  new PrintWriter(new FileWriter(Main.class.getResource("resources/"+path).getPath(), true));
			writer.append(str);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int string2Int(String string) {
		return Integer.parseInt(string);
	}

	public void putToSettings(String key, String value){
		pref.put(key, value);
	}

	public void putToSettings(String key, boolean value){
		pref.putBoolean(key, value);
	}
	
	public void putToSettings(String key, int value){
		pref.putInt(key, value);
	}
	
	public void putToSettings(String key, Float value){
		pref.putFloat(key, value);
	}
	
	public void putToSettings(String key, Double value){
		pref.putDouble(key, value);
	}
	
	public String getFromSettings(String key, String defaultValue){
		return pref.get(key, defaultValue);
	}
	
	public int getFromSettings(String key, int defaultValue){
		return pref.getInt(key, defaultValue);
	}
	
	public boolean getFromSettings(String key, boolean defaultValue){
		return pref.getBoolean(key, defaultValue);
	}
	
	public float getFromSettings(String key, float defaultValue){
		return pref.getFloat(key, defaultValue);
	}
	
	public Double getFromSettings(String key, Double defaultValue){
		return pref.getDouble(key, defaultValue);
	}
	
	public long getFrimSettings(String key, long defaultValue){
		return pref.getLong(key, defaultValue);
	}
	
	public boolean containsInSettings(String key){
		try {
			return pref.nodeExists(key);
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String join (String glue, String[] array){
		String joined = "";
		for (String item : array) {
			if(item != null && !item.equals(null) && item.length() > 0){
				joined += item+glue;
			}
		}
		joined = joined.replace("\n$", "");
		return joined;
	}
	
	public static String readFileAsString(String file) {
		String result = "";
		try {
<<<<<<< HEAD
			result = new Scanner(new File(file)).useDelimiter("[//r?//n]+").next();
=======
			result = new Scanner(new File(file)).useDelimiter("[\\r\\n]+").next();
>>>>>>> 849731cb6b4e1dfc4b46ca61a9a98d9c2619683c
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String getStringFromFile(String path) {
<<<<<<< HEAD
		String upend = "";
	    try {  
	        InputStream in = Main.class.getResourceAsStream("/com/trackback/raspirator/resources/"+ path);
	        Scanner scanner = new Scanner(in).useDelimiter("/n?\n");
//	        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
	        String line;
	        while (scanner.hasNext()) {
	            upend += scanner.next();
	        }
	        scanner.close();
=======
        
		String upend = "";
	    try {
	        Scanner s = new Scanner(new File(Main.class.getResource("resources/"+ path).getPath())).useDelimiter("/n");
	        while (s.hasNext()) {
	        	upend += s.next();
	        }
>>>>>>> 849731cb6b4e1dfc4b46ca61a9a98d9c2619683c
	    } catch (Exception ex) {
	        System.err.println(ex);
	        ex.printStackTrace();
	    }
		return upend;
	}
	
}