package com.trackback.raspirator.hardware.gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.GpioPinAnalogOutput;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

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
	
	public void reset(){
		gController.shutdown();
	}
	
	public void destroyPin(GpioPin pin){
		gController.unprovisionPin(pin);
	}
	
	public GpioPinAnalogInput createAnalogPinInput(int pin, String name, String state){
		return gController.provisionAnalogInputPin(getPin(pin), name);
	}
	
	public GpioPinAnalogOutput createAnalogPinOutput(int pin, String name, String state){
		return gController.provisionAnalogOutputPin(getPin(pin), name);
	}
	
	public GpioPinDigitalInput createPinInput(int pin, String name, String state){
		return gController.provisionDigitalInputPin(getPin(pin), name);
	}
	
	public GpioPinDigitalOutput createPinOutput(int pin, String name, String state){
		return gController.provisionDigitalOutputPin(getPin(pin), name);
	}
	
	public PinState getPinState(String state){
		if(state.equals("on"))
			return PinState.HIGH;
		else
			return PinState.LOW;
	}
	
	public Pin getPin(int pin){
		switch (pin) {
		case 0:
			return RaspiPin.GPIO_00;
		case 1:
			return RaspiPin.GPIO_01;
		case 2:
			return RaspiPin.GPIO_02;
		case 3:
			return RaspiPin.GPIO_03;
		case 4:
			return RaspiPin.GPIO_04;
		case 5:
			return RaspiPin.GPIO_05;
		case 6:
			return RaspiPin.GPIO_06;
		case 7:
			return RaspiPin.GPIO_07;
		case 8:
			return RaspiPin.GPIO_08;
		case 9:
			return RaspiPin.GPIO_09;
		case 10:
			return RaspiPin.GPIO_10;
		case 11:
			return RaspiPin.GPIO_11;
		case 12:
			return RaspiPin.GPIO_12;
		case 13:
			return RaspiPin.GPIO_13;
		case 14:
			return RaspiPin.GPIO_14;
		case 15:
			return RaspiPin.GPIO_15;
		case 16:
			return RaspiPin.GPIO_16;
		case 17:
			return RaspiPin.GPIO_17;
		case 18:
			return RaspiPin.GPIO_18;
		case 19:
			return RaspiPin.GPIO_19;
		case 20:
			return RaspiPin.GPIO_20;
		default:
			return RaspiPin.GPIO_00;
		}
		
	}
	
}
