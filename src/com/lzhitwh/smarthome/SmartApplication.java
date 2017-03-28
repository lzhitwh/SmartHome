package com.lzhitwh.smarthome;

import com.lzhitwh.smarthome.service.ReceiveService;

import android.app.Application;
import android.content.Intent;

public class SmartApplication extends Application {
	
	public static final String TAG = "SmartApplication";
	public final String UDP_MESSAGE = "com.lzhitwh.intent.udpＭess.receive";
	
	public SmartApplication() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Intent intentService = new Intent(this,ReceiveService.class);
		this.startService(intentService);
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		Intent intentService = new Intent(this,ReceiveService.class);
		this.stopService(intentService);
		
		super.onTerminate();
	}
}
