package com.lzhitwh.smarthome;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

	private final static String TAG = "MainActivity";
	private EditText dataEditText = null;
	private EditText ipEditText= null;
	private EditText portEditText = null;
	private Button button=null;
	public final String UDP_MESSAGE = "com.lzhitwh.intent.udpＭess.receive";
	private UdpMessageRecevicer udpMessageRecevicer = new UdpMessageRecevicer();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        dataEditText = (EditText) findViewById(R.id.data_editText);
        ipEditText = (EditText) findViewById(R.id.ip_editText);
        portEditText = (EditText) findViewById(R.id.port_editText);
        button = (Button) findViewById(R.id.button);
        
        IntentFilter udpMessage = new IntentFilter(UDP_MESSAGE);
		registerReceiver(udpMessageRecevicer,udpMessage);
		
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ip = ipEditText.getText().toString();
				int port = Integer.parseInt((portEditText.getText().toString()));
				((SmartApplication)getApplication()).getUdpBinder().setUdpDestIp(ip);
				((SmartApplication)getApplication()).getUdpBinder().setUdpDestPort(port);
				((SmartApplication)getApplication()).getUdpBinder().udpDataSend(dataEditText.getText().toString().getBytes());
			}
		});
    }
    
	public class UdpMessageRecevicer extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int dataLength = intent.getIntExtra("udpDataLength", 1);
			MainActivity.this.dataEditText.setText(""+dataLength);
		}
	}
}
