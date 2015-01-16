package com.trackback.raspirator.console.commands;

import com.trackback.raspirator.console.Command;
import com.trackback.raspirator.console.Interpreter;
import com.trackback.raspirator.system.Boot;

public class Man extends Command {

	public Man(Interpreter listener) {
		super(listener);
	}
	
	public void exec(String args){
		sendToClient("Looking for manual for command "+args);
		String man = Boot.bf.getStringFromFile("mans/"+args.trim()+".man");
		if(man.equals("")){
			sendToClient("Command "+args+" not has the manual!");
		}else{
			showMan(man);
		}
	}
	
	private void showMan(String man){
		sendToClient(man);
	}

}
