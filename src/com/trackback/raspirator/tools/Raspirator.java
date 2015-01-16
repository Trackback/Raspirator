package com.trackback.raspirator.tools;

public class Raspirator extends Life implements LifeListener {
	private final String TAG = "Raspirator";
	
	public Raspirator() {
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
