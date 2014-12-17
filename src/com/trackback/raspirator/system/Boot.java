package com.trackback.raspirator.system;

import com.trackback.raspirator.core.D;
import com.trackback.raspirator.core.Life;
import com.trackback.raspirator.hardware.Hardware;

public class Boot {
	private final String TAG = "Boot";
	
	public static Life life;
	public static Hardware hw;
	public static Actions actions;
	
	public Boot() {
		D.log(TAG, "Initializing...");
		life = new Life();
		hw = new Hardware();
		actions = new Actions();
		
		
		onCreate();
	}
	
	private void onCreate(){
		life.onCreate();
		D.log(TAG, "Initilized");
	}

}
