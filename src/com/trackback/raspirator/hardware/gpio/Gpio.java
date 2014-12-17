package com.trackback.raspirator.hardware.gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

public class Gpio {
	private GpioController gController;
	
	public Gpio() {
		gController = GpioFactory.getInstance();
	}

}
