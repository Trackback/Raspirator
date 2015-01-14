package com.trackback.raspirator.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

<<<<<<< HEAD
=======
import com.trackback.raspirator.console.CommandListner;

>>>>>>> 849731cb6b4e1dfc4b46ca61a9a98d9c2619683c
public class StreamGobbler extends Thread {
	private InputStream is;
	private String type;
	private StreamGobblerListener listner;
	
	public StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	public StreamGobbler(InputStream is, String type, StreamGobblerListener listener) {
		this.is = is;
		this.type = type;
		this.listner = listener;
	}
	
	public boolean isListened(){
		return (listner == null)? false : true;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null){
<<<<<<< HEAD
				D.log(type + ">" + line);
=======
				System.out.println(type + ">" + line);
>>>>>>> 849731cb6b4e1dfc4b46ca61a9a98d9c2619683c
				if(isListened()){
					listner.onGlobberOutput(type + ">" + line);
				}
			}
<<<<<<< HEAD
		} catch (Exception ioe) {
=======
		} catch (IOException ioe) {
>>>>>>> 849731cb6b4e1dfc4b46ca61a9a98d9c2619683c
			ioe.printStackTrace();
		}
	}
}