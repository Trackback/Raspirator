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
import com.trackback.raspirator.json.JSONArray;
import com.trackback.raspirator.json.JSONObject;
import com.trackback.raspirator.json.JSONStringer;
import com.trackback.raspirator.system.Boot;
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
	
	public boolean exec(String args){
		D.log("PIN", "Lookup "+args);
		try{
			String[] parsedItem = parseArgs(args);
			int key = 0;
			for (String arg : parsedItem) {
				int index = commandsList.indexOf(arg);
				if(index >= 0){
					switch (index) {
					case 0:
						create(parseItem(new PinItem(),parsedItem));
						return false;
					case 1:
						if(key + 1 < parsedItem.length){
							int pid = Integer.parseInt(parsedItem[key + 1]);
							remove(pid);
						}else{
							sendToClient("Wrong pin id!");
						}
						return false;
					case 2:
						list();
						return false;
					case 3:
						save();
						return false;
					case 4:
						load();
						return false;
					case 5:
						if(key + 1 < parsedItem.length){
							int pid = Integer.parseInt(parsedItem[key + 1]);
							edit(pid, parsedItem);
						}else{
							sendToClient("Wrong pin id!");
						}						
						return false;
					default:
						sendToClient("Wrong arguments, pleas, check see manual!");
						return false;
					}
				}
				key++;
			}
		}catch(Exception e){
			e.printStackTrace();
			sendToClient(e.getMessage());
		}
		return false;
	}
	
	private void create(PinItem item){
		if(item.ioType.equals("input")){
			item.pinInput = makePinInput(item);
			sendToClient(("Created pin "+item.name+" at "+item.number+" position with i/o type "+item.ioType));
			pins.add(item);
		}else if (item.ioType.equals("output")) {
			item.pinOutput = makePinOutput(item);
			sendToClient(("Created pin "+item.name+" at "+item.number+" position with i/o type "+item.ioType));
			pins.add(item);
		}else{
			sendToClient("Pin "+item.name+" at "+item.number+" position with i/o type "+item.ioType+" not found");
		}
	}
	
	private PinItem makePin(PinItem item){
		if(item.ioType.equals("input")){
			item.pinInput = makePinInput(item);
		}else if (item.ioType.equals("output")) {
			item.pinOutput = makePinOutput(item);
		}
		return item;
	}
	
	private GpioPinDigitalInput makePinInput(PinItem item){
			return gpio.createPinInput(item.number, item.name, item.state);
	}
	
	private GpioPinDigitalOutput makePinOutput(PinItem item){
			return gpio.createPinOutput(item.number, item.name, item.state);
	}	
	private void edit(int pid, String[] args){
		if(pins.size() > pid){
			pins.set(pid, makePin(parseItem(new PinItem(),args)));
			sendToClient("Pin with id "+pid+" was saved successful!");
		}else{
			sendToClient("Pin with id "+pid+" not found!");
		}
		
	}
	
	private void remove(int pid){
		List<PinItem> tmpPins = new  ArrayList<Pin.PinItem>();
		tmpPins = pins;
		
		pins.clear();
		Iterator<PinItem> iterator = tmpPins.iterator();
		int id = 0;
		while(iterator.hasNext()){
			PinItem item = iterator.next();
			if(id != pid){
				pins.add(item);
			}else{
				item.destroy();
			}
			id++;
		}
		tmpPins.clear();
	}
	
	private void clear(){
		Iterator<PinItem>iterator = pins.iterator();
		while(iterator.hasNext()){
			PinItem pin = iterator.next();
			pin.destroy();
		}
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
		Iterator<PinItem> iterator = pins.iterator();
		int i = 0;
		sendToClient("Saving...");
		String pins = "";
		while(iterator.hasNext()){
			PinItem item = iterator.next();
			pins += item.toString()+" ## ";
			sendToClient(i+" - "+item.name+" - "+item.number+" - "+item.ioType+" saved");
			i++;
		}
		Boot.bf.putToSettings("pins", pins);
		sendToClient("Saving complite");
	}
	
	private void load(){
		String str = Boot.bf.getFromSettings("pins", "");
		gpio.reset();
		gpio = new Gpio();
		clear();
		
		if(str.length() > 0){
			String[] pinsStrings = str.split(" ## ");
			if(pinsStrings.length > 0){
				pins.clear();
				for (String pin : pinsStrings) {
					PinItem item = new PinItem();
					item.fromString(pin);
					pins.add(makePin(item));
					sendToClient("Restored "+item.name+" with I/O type "+item.ioType);
				}
				sendToClient("Loading finish!");
			}else{
				sendToClient("Opps. We have a problem at restoring pins! Data was buged!");
				sendToClient(str);
			}
		}else{
			sendToClient("Saved pins not found!");
		}
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
		
		public String toString(){
			String str = "";
			try{
				str = new JSONStringer()
				.array()
					.object()
						.key("name")
						.value(name)
						.key("state")
						.value(state)
						.key("number")
						.value(number)
						.key("iotype")
						.value(ioType)
						.key("command")
						.value(command)
					.endObject()
				.endArray()
				.toString();
			}catch(Exception e){
				e.printStackTrace();
				sendToClient(e.getMessage());
			}
			
			return str;
		}
		
		public void fromString(String str){
			JSONArray array = new JSONArray(str);

			JSONObject obj = array.getJSONObject(0);
			name = obj.getString("name");
			state = obj.getString("state");
			number = obj.getInt("number");
			ioType = obj.getString("iotype");
			command = obj.getString("command");
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
					sendToClient("The outputs pins did not has listners");
				}
			}
		}
		
		public void destroy(){
			if(isInputType()){
				pinInput.clearProperties();
			}else{
				pinOutput.clearProperties();
			}
			
		}
		
	}

}
