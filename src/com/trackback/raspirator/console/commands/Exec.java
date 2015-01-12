package com.trackback.raspirator.console.commands;

import com.trackback.raspirator.console.Command;
import com.trackback.raspirator.console.Interpreter;
import com.trackback.raspirator.tools.StreamGobbler;
import com.trackback.raspirator.tools.StreamGobblerListener;


public class Exec extends Command implements StreamGobblerListener{

	public Exec(Interpreter listener) {
		super(listener);
	}
	
	public void exec(String command){
		try{
	            Runtime rt = Runtime.getRuntime();
	            Process proc = rt.exec(command);
	            
	            // any error message?
	            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERR", this);            
	            
	            // any output?
	            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUT", this);
	                
	            // kick them off
	            errorGobbler.start();
	            outputGobbler.start();
	                                    
	            // any error???
	            int exitVal = proc.waitFor();
	            System.out.println("ExitValue: " + exitVal);
		}catch(Exception e){
			e.printStackTrace();
			sendToClient(e.toString());
		}
	}

	@Override
	public void onGlobberOutput(String out) {
		sendToClient(out);
	}

}
