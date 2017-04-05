package com.lzhitwh.smarthome.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.widget.Toast;

public class WifiControl {
	
	private Context	context;
	public static final String udpRecAction = "com.lzhitwh.Udp_Recevice";
	private WifiManager wifiManager=null;
	private ConnectivityManager connManager=null;
	private DatagramSocket udpSocket=null;
	
	public WifiControl(Context context){
		this.context = context;
		if(wifiManager == null){
			wifiManager = (WifiManager)context.getSystemService(context.WIFI_SERVICE);
		}
		if(connManager == null){
			connManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
		}
		if(udpSocket == null){
			try{
				udpSocket = new DatagramSocket(54321);
			}catch(Exception e){
				e.printStackTrace();
				Toast.makeText(context,"������ʱ�޷�ʹ��",Toast.LENGTH_LONG);
			}
		}
	}
	
	public void openWifi(){
		wifiManager.setWifiEnabled(true);
	}
	
	public void connectWifi(){
		Intent intent  = new Intent(Settings.ACTION_WIFI_SETTINGS);
		context.startActivity(intent);
	}
	
	public int getWifiStatus(){
		return wifiManager.getWifiState();
	}
	
	public void closeWifi(){
		wifiManager.setWifiEnabled(false);
	}
	
	public DatagramSocket getUdpSocket(){
		return udpSocket;
	}
	
	public boolean isWifiConnect(){
		NetworkInfo netInfo = connManager.getActiveNetworkInfo();
		String netType = netInfo.getTypeName();
		if("wifi".equals(netType.toLowerCase())){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean wifiSendData(String data){
		try{
			DatagramPacket udpPacket = new DatagramPacket(data.getBytes(),data.length(),InetAddress.getByName("192.168.1.255"),54321);
			udpSocket.send(udpPacket);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
}
