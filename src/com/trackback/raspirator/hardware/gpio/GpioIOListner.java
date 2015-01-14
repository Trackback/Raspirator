package com.trackback.raspirator.hardware.gpio;

public interface GpioIOListner {
	public abstract void onGPIOInput();
	public abstract void onGPIOOutput();
}
