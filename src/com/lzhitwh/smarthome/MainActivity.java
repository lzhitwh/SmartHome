package com.lzhitwh.smarthome;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

	private final static String TAG = "MainActivity";
	private EditText editText = null;
	private Button button=null;
	public final String UDP_MESSAGE = "com.lzhitwh.intent.udpＭess.receive";
	private UdpMessageRecevicer udpMessageRecevicer = new UdpMessageRecevicer();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        editText = (EditText) findViewById(R.id.message);
        button = (Button) findViewById(R.id.button);
        
        IntentFilter udpMessage = new IntentFilter();
		udpMessage.addAction(UDP_MESSAGE);
		registerReceiver(udpMessageRecevicer,udpMessage);
    }
    
	public class UdpMessageRecevicer extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int dataLength = intent.getIntExtra("udpDataLength", 1);
			MainActivity.this.editText.setText(""+dataLength);
		}
	}
}
