package com.lzhitwh.smarthome.surface.ui;

import com.lzhitwh.smarthome.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TelevisionFragment extends Fragment{
	
	private Button boxMute=null,boxSwitch=null;
	private Button teleMute=null,teleSwitch=null;
	private Button boxVolumeDrop=null,boxVolumeRise=null;
	private Button teleVolumeDrop=null,teleVolumeRise=null;
	private Button num0=null,num1=null,num2=null,num3=null,num4=null;
	private Button num5=null,num6=null,num7=null,num8=null,num9=null;
	private Button backBefore=null,lockCurrent=null;
	private Button volumeDrop=null,volumeRise=null;
	private Button programDrop=null,programRise=null;
	private Button func1=null,func2=null,func3=null,func4=null;
	
	public static TelevisionFragment newInstance(){
		TelevisionFragment televisionFragment = new TelevisionFragment();
		return televisionFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.television_control,container,false);
		return view;
	}
	
	private void getComponentId(View view){
		boxMute = (Button) view.findViewById(R.id.box_mute);
		boxSwitch = (Button) view.findViewById(R.id.box_switch);
		teleMute = (Button) view.findViewById(R.id.tele_mute);
		teleSwitch = (Button) view.findViewById(R.id.tele_switch);
		boxVolumeDrop = (Button) view.findViewById(R.id.box_volume_drop);
		boxVolumeRise = (Button) view.findViewById(R.id.box_volume_rise);
		teleVolumeDrop = (Button) view.findViewById(R.id.tele_volume_drop);
		teleVolumeRise = (Button) view.findViewById(R.id.tele_volume_rise);
		num0 = (Button)view.findViewById(R.id.num_0);
		num1 = (Button)view.findViewById(R.id.num_1);
		num2 = (Button)view.findViewById(R.id.num_2);
		num3 = (Button)view.findViewById(R.id.num_3);
		num4 = (Button)view.findViewById(R.id.num_4);
		num5 = (Button)view.findViewById(R.id.num_5);
		num6 = (Button)view.findViewById(R.id.num_6);
		num7 = (Button)view.findViewById(R.id.num_7);
		num8 = (Button)view.findViewById(R.id.num_8);
		num9 = (Button)view.findViewById(R.id.num_9);
		backBefore = (Button)view.findViewById(R.id.back_before);
		lockCurrent = (Button)view.findViewById(R.id.locking_current);
		func1 = (Button)view.findViewById(R.id.fun1);
		func2 = (Button)view.findViewById(R.id.fun2);
		func3 = (Button)view.findViewById(R.id.fun3);
		func4 = (Button)view.findViewById(R.id.fun4);
		volumeRise = (Button)view.findViewById(R.id.volume_rise);
		volumeDrop = (Button)view.findViewById(R.id.volume_drop);
		programRise = (Button)view.findViewById(R.id.program_rise);
		programDrop = (Button)view.findViewById(R.id.program_drop);
	}

}
