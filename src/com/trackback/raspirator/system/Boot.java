package com.trackback.raspirator.system;

import com.trackback.raspirator.console.Interpreter;
import com.trackback.raspirator.hardware.Hardware;
import com.trackback.raspirator.server.ServerAdmin;
import com.trackback.raspirator.tools.BaseFunction;
import com.trackback.raspirator.tools.D;
import com.trackback.raspirator.tools.Life;

public class Boot {
	private final String TAG = "Boot";
	
	public static Life life;
	public static Hardware hw;
	public static Actions actions;
	public static Interpreter interpreter;
	public static BaseFunction bf;
	
	public Boot() {
		D.log(TAG, "Initializing...");
		bf = new BaseFunction();
		life = new Life();
		hw = new Hardware();
		actions = new Actions();
		interpreter = new Interpreter();
		ServerAdmin.init(interpreter);
		onCreate();	
	}
	
	private void onCreate(){
		life.onCreate();
		D.log(TAG, "Initilized");
	}

}
