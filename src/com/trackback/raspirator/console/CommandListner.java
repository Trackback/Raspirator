package com.trackback.raspirator.console;

public interface CommandListner {
	public abstract void onCommandStart();
	public abstract void onCommandFinish();
	public abstract void onCommandSad(String data);
}
