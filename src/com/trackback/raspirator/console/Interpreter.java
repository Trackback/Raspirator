package com.trackback.raspirator.console;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.trackback.raspirator.console.commands.Exec;
import com.trackback.raspirator.console.commands.Help;
<<<<<<< HEAD
import com.trackback.raspirator.console.commands.Pin;
import com.trackback.raspirator.console.commands.Top;
import com.trackback.raspirator.constants.ConstantsArgs;
import com.trackback.raspirator.hardware.gpio.Gpio;
=======
import com.trackback.raspirator.constants.ConstantsArgs;
>>>>>>> 849731cb6b4e1dfc4b46ca61a9a98d9c2619683c
import com.trackback.raspirator.server.onServerGetRequest;
import com.trackback.raspirator.settings.Settings;
import com.trackback.raspirator.system.Boot;
import com.trackback.raspirator.tools.D;

public class Interpreter implements onServerGetRequest, CommandListner{
	private ServerBridg bridg;
	private List<String> commandsList = new ArrayList<String>();
	private Top top;
	private Exec exec;
	private Interpreter self = this;
	private Pin pin;
	public Interpreter() {
		init();
	}
	
	private void init(){
		prepareCommandsIndex();
		top = new Top(this);
		exec = new Exec(this, top);
		pin = new Pin(this);
	}
	
	private void prepareCommandsIndex(){
		String[] splited = ConstantsArgs.commands.split(",");
		for (int i = 0; i < splited.length; i++) {
			String string = splited[i];
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
	
	public void find(final String[] args){
		D.log("Try to find "+args[0]);
		final String str = Boot.bf.join(" ",args);
		int index = commandsList.indexOf(args[0]);
		
		if(index >= 0){
			switch (index) {
			case 0:
				new Help(self);
				break;
			case 1:
<<<<<<< HEAD
				new Thread(new Runnable() {
					
					@Override
					public void run() {				

						String args = str.replace("exec ", "");
						exec.exec(args);
					}
				}).run();
=======
				Exec e = new Exec(this);
				String str = Boot.bf.join(" ",args);
				str = str.replace("exec ", "");
				e.exec(str);
>>>>>>> 849731cb6b4e1dfc4b46ca61a9a98d9c2619683c
				break;
			case 2:
				bridg.sendResponseToClient("Raspirator version is "+Settings.ver);
				break;
<<<<<<< HEAD
			case 3:
			case 4:
			case 5:
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						D.log("Full args "+str);
						String args = str.replace("top ", "");
						top.exec(args);
					}
				}).run();
				break;
			case 6:
				pin.exec(str);
				break;
=======
>>>>>>> 849731cb6b4e1dfc4b46ca61a9a98d9c2619683c
			default:
				bridg.sendResponseToClient("What do you want? Ha!?");
				break;
			}
		}else{
			bridg.sendResponseToClient("Command "+args[0]+" not found \n Pleas, type help to get commands list "+commandsList.size());
			Iterator<String> it = commandsList.iterator();
			
			while(it.hasNext()){
				bridg.sendResponseToClient(it.next());
			}
			
		}
	}

}
