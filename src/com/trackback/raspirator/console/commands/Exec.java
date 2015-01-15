package com.trackback.raspirator.console.commands;

import com.trackback.raspirator.console.Command;
import com.trackback.raspirator.console.Interpreter;
import com.trackback.raspirator.tools.StreamGobbler;
import com.trackback.raspirator.tools.StreamGobblerListener;


public class Exec extends Command implements StreamGobblerListener{
	private Top top;
	
	public Exec(Interpreter listener, Top top) {
		super(listener);
		this.top = top;
	}
	
	public void exec(String command){
		try{
	            Runtime rt = Runtime.getRuntime();
	            Process proc = rt.exec(command);
	            
	            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERR", this);            
	            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUT", this);
	                
	            errorGobbler.start();
	            outputGobbler.start();
	                                    
	            sendToClient(top.addProcess(proc, command));

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
