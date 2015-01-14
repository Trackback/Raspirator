package com.trackback.raspirator.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
				D.log(type + ">" + line);
				if(isListened()){
					listner.onGlobberOutput(type + ">" + line);
				}
			}
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}
}