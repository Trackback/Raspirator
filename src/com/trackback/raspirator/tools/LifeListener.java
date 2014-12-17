package com.trackback.raspirator.tools;

public interface LifeListener {
	
	public void onCreate();
	public void onPause();
	public void onResume();
	public void onStop();
	public void onDestroy();
}
