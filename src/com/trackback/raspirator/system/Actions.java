package com.trackback.raspirator.system;

import com.trackback.raspirator.tools.D;
import com.trackback.raspirator.tools.Raspirator;

public class Actions extends Raspirator{
	public final String TAG = "Actions";
	
	public Actions() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate() {
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
	}

}
