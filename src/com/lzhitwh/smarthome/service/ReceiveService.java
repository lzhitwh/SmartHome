package com.lzhitwh.smarthome.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ReceiveService extends Service{
	
	private final static String TAG = "ReceiveService";
	public final String UDP_MESSAGE = "com.lzhitwh.intent.udpＭess.receive";
	
	private DatagramSocket udpSocket = null;			//UDP数据收发Socket
	private int udpPort = 54321;						//数据网络端口，本地端口
	
	private UdpReceviceThread udpReceviceThread = new UdpReceviceThread();
//	private UdpBinder udpBinder = new UdpBinder();

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		if(udpSocket ==null){
			try {
				udpSocket = new DatagramSocket(udpPort);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		
		Log.i(TAG,"onCreate Success");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		return new UdpBinder();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		if(udpReceviceThread.isAlive()==false){
			udpReceviceThread.start();
		}else{
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private class UdpReceviceThread extends Thread{
		
		private boolean status = true;
		private byte[] udpData = new byte[8];
		private DatagramPacket udpReceivePacket = null;
		
		public UdpReceviceThread(){
			try{
				udpReceivePacket = new DatagramPacket(udpData, udpData.length);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(status){
				try {
					udpSocket.receive(udpReceivePacket);
					Intent udpDataIntent = new Intent(UDP_MESSAGE);
					udpDataIntent.putExtra("udpDataLength",udpReceivePacket.getLength());
					udpDataIntent.putExtra("udpData", udpData);
					ReceiveService.this.sendBroadcast(udpDataIntent);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public void setStatus(boolean status){
			status = this.status;
		}
	}
	
	private class UdpBinder extends Binder{
		
		private DatagramPacket udpSendPacket = null;
		private String udpDestIp = "192.168.1.100";
		private int udpDestPort = 54321;
		
		public UdpBinder() {
			super();
			// TODO Auto-generated constructor stub
			try{
				udpSendPacket = new DatagramPacket(null,0,
						InetAddress.getByName(udpDestIp),udpDestPort);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public void udpDataSend(byte[] data) throws UnknownHostException{
			try {
				udpSendPacket.setAddress(InetAddress.getByName(udpDestIp));
				udpSendPacket.setPort(udpPort);
				udpSendPacket.setData(data,0,data.length);
				udpSocket.send(udpSendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public String getUdpDestIp() {
			return udpDestIp;
		}

		public void setUdpDestIp(String udpDestIp) {
			this.udpDestIp = udpDestIp;
		}

		public int getUdpDestPort() {
			return udpDestPort;
		}

		public void setUdpDestPort(int udpDestPort) {
			this.udpDestPort = udpDestPort;
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		udpReceviceThread.setStatus(false);
		udpReceviceThread.interrupt();
		udpSocket.close();
		
		super.onDestroy();
	}
}
