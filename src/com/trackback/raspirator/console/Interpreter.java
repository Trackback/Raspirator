package com.trackback.raspirator.console;

import java.util.ArrayList;
import java.util.List;

import com.trackback.raspirator.console.commands.Help;
import com.trackback.raspirator.server.onServerGetRequest;
import com.trackback.raspirator.system.Boot;
import com.trackback.raspirator.tools.D;

public class Interpreter implements onServerGetRequest, CommandListner{
	private ServerBridg bridg;
	private List<String> commandsList = new ArrayList<String>();
	
	public Interpreter() {
		init();
	}
	
	private void init(){
		prepareCommandsIndex();
	}
	
	private void prepareCommandsIndex(){
		String index = Boot.bf.getStringFromFile("values/commands.list");
		String[] splited = index.split(",");
		for (String string : splited) {
			D.log("Add to index "+string);
			commandsList.add(string.trim());
		}
	}
	
	public List<String> getCommandsIndex(){
		return commandsList;
	}
	
	public onServerGetRequest getGetRequestListner(){
		return this;
	}
	
	public void setServerBridg(ServerBridg bridg){
		this.bridg = bridg;
	}
	
	public boolean isBridgeBuilded(){
		return (bridg == null)? false : true;
	}

	@Override
	public void onGetRequest(String data) {
		eatCommand(data);
	}

	@Override
	public void onCommandStart() {
		
	}

	@Override
	public void onCommandFinish() {
		
	}

	@Override
	public void onCommandSad(String data) {
		if(isBridgeBuilded()){
			bridg.sendResponseToClient(data);
		}
	}
	
	public void eatCommand(String command){
		String[] args = command.split(" ");
		find(args);
	}
	
	public void find(String[] args){
		D.log("Try to find "+args[0]);
		int index = commandsList.indexOf(args[0]);
		
		if(index >= 0){
			switch (index) {
			case 0:
				new Help(this);
				break;

			default:
				break;
			}
		}else{
			bridg.sendResponseToClient("Command not found");
		}
	}

}
