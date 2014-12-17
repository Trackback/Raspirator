package com.trackback.raspirator.core;

import java.util.ArrayList;
import java.util.List;

public class Life {
	private List<LifeListener> listeners = new ArrayList<LifeListener>();
	private final String TAG = "Life";
	
	public Life() {
	}
	
	public void setListener(LifeListener listener){
		this.listeners.add(listener);
	}
	
	public void removeListner(LifeListener listener){
		listeners.remove(listener);
	}
	
	public void onCreate(){
		for (LifeListener listener : listeners) {
			listener.onCreate();
		}
	}
	
	public void onDestroy(){
		for (LifeListener listener : listeners) {
			listener.onDestroy();
		}		
	}

}
