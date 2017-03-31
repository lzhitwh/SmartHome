package com.lzhitwh.smarthome;

import com.lzhitwh.smarthome.service.ReceiveService;
import com.lzhitwh.smarthome.service.ReceiveService.UdpBinder;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class SmartApplication extends Application {
	
	public static final String TAG = "SmartApplication";
	public final String UDP_MESSAGE = "com.lzhitwh.intent.udpＭess.receive";
	private UdpBinder udpBinder = null;
	
	public SmartApplication() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Intent intentService = new Intent(this,ReceiveService.class);
		this.startService(intentService);	//启动service
		this.bindService(intentService, new UdpServiceConnection(),BIND_AUTO_CREATE);
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		Intent intentService = new Intent(this,ReceiveService.class);
		this.stopService(intentService);
		
		super.onTerminate();
	}
	
	private class UdpServiceConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			udpBinder = (UdpBinder) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
	}

	public UdpBinder getUdpBinder() {
		return udpBinder;
	}
}
