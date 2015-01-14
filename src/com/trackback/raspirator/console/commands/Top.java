package com.trackback.raspirator.console.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.util.Pair;

import com.trackback.raspirator.console.Command;
import com.trackback.raspirator.console.Interpreter;
import com.trackback.raspirator.tools.D;
import com.trackback.raspirator.tools.StreamGobbler;
import com.trackback.raspirator.tools.StreamGobblerListener;

public class Top extends Command implements StreamGobblerListener{
	List<Pair<String, Process>>index = new ArrayList<Pair<String, Process>>();
	private List<String> commandsList = new ArrayList<String>();
	
	public Top(Interpreter listener) {
		super(listener);
		
		commandsList.add("");
		commandsList.add("kill");
		commandsList.add("killall");
	}
	
	public void exec(String args){
		D.log("TOP", "Lookup "+args);
		try{
			String[] arg = args.split(" ");
			for (String string : arg) {
				int index = commandsList.indexOf(string.trim());
				if(index >= 0){
					switch (index) {
					case 0:
						top();
						break;
					case 1:
						int pid = Integer.parseInt(arg[arg.length - 1]);
						if(pid >= 0){
							kill(pid);
						}else{
							sendToClient("Wrong PID number!");
						}
						break;
					case 2:
						killall();
						break;
					default:
						break;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			sendToClient(e.getMessage());
		}
	}
	
	public String addProcess(Process p, String command){
		index.add(new Pair(command, p));
		return "Added "+command+" at pid "+index.size()+" \n Type top to get list of all processes or \n top kill 'pid' to terminate process or \n top killall to terminate all processes";
	}
	
	public void top(){
		Iterator<Pair<String, Process>> iterator = index.iterator();
		sendToClient("PID : Command");
		int pid = 0;
		while(iterator.hasNext()){
			Pair<String, Process> item = iterator.next();
			sendToClient(pid + " : "+item.getKey() );
			pid++;
		}
	}
	
	public void kill(int pid){
		try{
			Pair<String, Process> item = index.get(pid);
			Process result = item.getValue().destroyForcibly();
			StreamGobbler outputGobbler = new StreamGobbler(result.getInputStream(), "TOP", this);
			outputGobbler.start();
			int exitVal = result.waitFor();
            System.out.println("ExitValue: " + exitVal);
		}catch(Exception e){
			e.printStackTrace();
			sendToClient(e.getMessage());
		}
		
	}
	
	public void killall(){
		try{
			Iterator<Pair<String, Process>> iterator = index.iterator();
			while(iterator.hasNext()){
				Pair<String, Process> item = iterator.next();
				Process result = item.getValue().destroyForcibly();
				StreamGobbler outputGobbler = new StreamGobbler(result.getInputStream(), "TOP", this);
				outputGobbler.start();
				int exitVal = result.waitFor();
	            System.out.println("ExitValue: " + exitVal);
			}
		}catch(Exception e){
			e.printStackTrace();
			sendToClient(e.getMessage());
		}
	}

	@Override
	public void onGlobberOutput(String out) {
		sendToClient(out);		
	}

}
