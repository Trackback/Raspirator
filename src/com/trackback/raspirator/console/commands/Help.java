package com.trackback.raspirator.console.commands;

import java.util.Iterator;

import com.trackback.raspirator.console.Command;
import com.trackback.raspirator.console.Interpreter;
import com.trackback.raspirator.system.Boot;

public class Help extends Command{

	public Help(Interpreter listener) {
		super(listener);
		init();
	}
	
	private void init(){
		String dsc = Boot.bf.getStringFromFile("values/help.dsc");
		sendToClient(dsc);
		
		Iterator<String> iterator = getCommandsList().iterator();
		
		while(iterator.hasNext()){
			sendToClient(iterator.next()+" \n");
		}
		onCommandFinish();
	}

}
