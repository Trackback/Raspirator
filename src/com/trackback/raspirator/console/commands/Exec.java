package com.trackback.raspirator.console.commands;

import com.trackback.raspirator.console.Command;
import com.trackback.raspirator.console.Interpreter;
<<<<<<< HEAD
import com.trackback.raspirator.tools.D;
=======
>>>>>>> 849731cb6b4e1dfc4b46ca61a9a98d9c2619683c
import com.trackback.raspirator.tools.StreamGobbler;
import com.trackback.raspirator.tools.StreamGobblerListener;


public class Exec extends Command implements StreamGobblerListener{
<<<<<<< HEAD
	private Top top;
	
	public Exec(Interpreter listener, Top top) {
		super(listener);
		this.top = top;
=======

	public Exec(Interpreter listener) {
		super(listener);
>>>>>>> 849731cb6b4e1dfc4b46ca61a9a98d9c2619683c
	}
	
	public void exec(String command){
		try{
	            Runtime rt = Runtime.getRuntime();
	            Process proc = rt.exec(command);
	            
<<<<<<< HEAD
	            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERR", this);            
	            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUT", this);
	                
	            errorGobbler.start();
	            outputGobbler.start();
	                                    
	            sendToClient(top.addProcess(proc, command));
=======
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
>>>>>>> 849731cb6b4e1dfc4b46ca61a9a98d9c2619683c
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
