package com.trackback.raspirator.console.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.trackback.raspirator.console.Command;
import com.trackback.raspirator.console.Interpreter;
import com.trackback.raspirator.hardware.gpio.Gpio;
import com.trackback.raspirator.tools.D;

public class Pin extends Command {
	
	private List<PinItem> pins = new ArrayList<PinItem>();
	private List<String> commandsList = new ArrayList<String>();
	private List<String> pinArgs = new ArrayList<String>();
	private Gpio gpio;
	
	public Pin(Interpreter listener){
		super(listener);
		gpio = new Gpio();
		
		commandsList.add("-c");
		commandsList.add("-r");
		commandsList.add("-R");
		commandsList.add("-l");
		commandsList.add("-s");
		commandsList.add("-L");
		commandsList.add("-e");
		
		pinArgs.add("-n");
		pinArgs.add("-s");
		pinArgs.add("-io");
		pinArgs.add("-i");
		pinArgs.add("-c");
		
		
	}
	
	public void exec(String args){
		D.log("PIN", "Lookup "+args);
		try{
			String[] parsedItem = parseArgs(args);
			for (String arg : parsedItem) {
				int index = commandsList.indexOf(arg);
				if(index >= 0){
					switch (index) {
					case 0:
						create(parseItem(new PinItem(),parsedItem));
						break;
					case 1:
						remove();
						break;
					case 2:
						replace();
						break;
					case 3:
						list();
						break;
					case 4:
						save();
						break;
					case 5:
						load();
						break;
					case 6:
						break;
					default:
						sendToClient("Wrong arguments, pleas, check see manual!");
						break;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			sendToClient(e.getMessage());
		}
	}
	
	private void create(PinItem item){
		if(item.ioType.equals("input")){
			item.pinInput = gpio.createPinInput(item.number, item.name, item.state);
			sendToClient(("Created pin "+item.name+" at "+item.number+" position with i/o type "+item.ioType));
			pins.add(item);
		}else if (item.ioType.equals("output")) {
			item.pinOutput = gpio.createPinOutput(item.number, item.name, item.state);
			sendToClient(("Created pin "+item.name+" at "+item.number+" position with i/o type "+item.ioType));
			pins.add(item);
		}else{
			sendToClient("Pin "+item.name+" at "+item.number+" position with i/o type "+item.ioType+" not found");
		}
	}
	
	private void edit(int pid){
		
	}
	
	private void remove(){
		
	}
	
	private void replace(){
		
	}
	
	private void list(){
		Iterator<PinItem> iterator = pins.iterator();
		int i = 0;
		sendToClient(i+" - Name - Pin number - I/O type");
		while(iterator.hasNext()){
			PinItem item = iterator.next();
			sendToClient(i+" - "+item.name+" - "+item.number+" - "+item.ioType);
			i++;
		}
	}
	
	private void save(){
		
	}
	
	private void load(){
		
	}
	
	private String[] parseArgs(String argsString){
		argsString = argsString.replace("pin ", "");
		String[] args = argsString.trim().split(" ");
		return args;
	}
	
	private PinItem parseItem(PinItem item, String[] args){
		try{
			int key = 0;
			for (String string : args) {
				int index = pinArgs.indexOf(string);
				if(index >= 0){
					switch (index) {
					case 0:
						if(key+1 < args.length){
							item.name = args[key+1];
						}else{
							sendToClient("Error! Miss or wrong argument for flag -n");
						}
						break;
					case 1:
						if(key+1 < args.length){
							item.state = args[key+1];
						}else{
							sendToClient("Error! Miss or wrong argument for flag -s");
						}
						break;
					case 2:
						if(key+1 < args.length){
							item.ioType = args[key+1];
						}else{
							sendToClient("Error! Miss or wrong argument for flag -oi");
						}
						break;
					case 3:
						if(key+1 < args.length){
							item.number = Integer.parseInt(args[key+1]);
						}else{
							sendToClient("Error! Miss or wrong argument for flag -i");
						}
						break;
					case 5:
						if(key+1 < args.length){
							item.command = args[key+1];
						}else{
							sendToClient("Error! Miss or wrong argument for flag -c");
						}
						break;
					default:
						break;
					}
				}
				key++;
			}
		}catch(Exception e){
			e.printStackTrace();
			sendToClient(e.getMessage());
		}
		return item;
	}
	
	public class PinItem{
		public String name ="";
		public String state = "on";
		public int number = 0;
		public String ioType = "input";
		public GpioPinDigitalOutput pinOutput = null;
		public GpioPinDigitalInput pinInput = null;
		public String command = "";
		
		public PinItem(){
			
		}
		
		public boolean isPin(){
			return (pinInput == null && pinOutput == null)? false : true;
		}
		
		public boolean isInputType(){
			return (pinInput == null)? false : true;
		}
		
		public boolean isOutputType(){
			return (pinOutput == null)? false : true;
		}
		
		public boolean on(){
			if(isPin()){
				if(pinOutput.isLow()){
					pinOutput.high();
					sendToClient("Pin "+name+" is ON");
					return true;
				}else{
					sendToClient("You can't on/off pins with input I/O type!");
				}
			}
			return false;
		}
		
		public boolean off(){
			if(isPin()){
				if(pinOutput.isLow()){
					pinOutput.low();
					sendToClient("Pin "+name+" is OFF");
					return true;
				}else{
					sendToClient("You can't on/off pins with input I/O type!");
				}
			}
			return false;		
		}
		
		public boolean toggle(){
			if(isPin()){
				if(pinOutput.isLow()){
					pinOutput.toggle();
					sendToClient("Pin "+name+" is toggled");
					return true;
				}else{
					sendToClient("You can't on/off pins with input I/O type!");
				}
			}
			return false;
		}
		
		public boolean pulse(long time){
			if(isPin()){
				if(pinOutput.isLow()){
					pinOutput.pulse(time);
					sendToClient("Pin "+name+" is pulse "+time+"ms");
					return true;
				}else{
					sendToClient("You can't pulse/blinkin pins with input I/O type!");
				}
			}
			return false;
		}
		
		public void setOnInput(final String command){
			this.command = command;
			if(isPin()){
				if(isInputType()){
					pinInput.addListener(new GpioPinListenerDigital() {
						
						@Override
						public void handleGpioPinDigitalStateChangeEvent(
								GpioPinDigitalStateChangeEvent event) {
								sendToClient("Pin "+name+" change state to "+event.getState());
								if(event.getState() == PinState.HIGH){
									getListener().onCommandSad(command);
								}
						}
					});
				}else{
					
				}
			}
		}
		
	}

}
