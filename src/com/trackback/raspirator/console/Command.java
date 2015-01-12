package com.trackback.raspirator.console;

import java.util.List;

public class Command {
	CommandListner listener;
	Interpreter interpreter;
	
	public Command(Interpreter interpreter) {
		this.listener = interpreter;
		this.interpreter = interpreter;
		listener.onCommandStart();
	}
	
	public void sendToClient(String data){
		listener.onCommandSad(data);
	}
	
	public void onCommandFinish(){
		listener.onCommandFinish();
	}
	
	public List<String> getCommandsList(){
		return interpreter.getCommandsIndex();
	}
	
	public CommandListner getListener(){
		return listener;
	}

}
