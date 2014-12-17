package com.trackback.raspirator.core;

public class Ribot extends Life implements LifeListener {
	private final String TAG = "Ribot";
	
	public Ribot() {
		onCreate();
	}

	@Override
	public void onCreate() {
		setListener(this);
		D.log(TAG, "Ready");

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		removeListner(this);
	}

}
