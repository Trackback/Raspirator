package com.trackback.raspirator.hardware.gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

public class Gpio {
	private GpioController gController;
	private GpioIOListner listener;
	
	public Gpio() {
		gController = GpioFactory.getInstance();
	}
	
	public void setListener(GpioIOListner listner){
		this.listener = listner;
	}
	
	public boolean isListned(){
		return (listener == null)? false : true;
	}
	

}
