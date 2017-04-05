package com.lzhitwh.smarthome.surface.ui;

import com.lzhitwh.smarthome.R;
import com.lzhitwh.smarthome.SmartApplication;
import com.lzhitwh.smarthome.surface.MainActivity;
import com.lzhitwh.smarthome.temper.HumidityView;
import com.lzhitwh.smarthome.temper.TemperatureView;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


public class LightFragment extends Fragment{

	private TemperatureView temperView = null;
	private HumidityView humidityView = null;
	
	public static LightFragment newInstance(){
		LightFragment lightFragment = new LightFragment();
		return lightFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.light_control,container,false);
		LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.temper_humidity);
		int displayWith = getActivity().getResources().getDisplayMetrics().widthPixels;
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(displayWith/2, displayWith/2);
		
		temperView = new TemperatureView(getActivity());
		temperView.setLayoutParams(layoutParams);
		linearLayout.addView(temperView);
		
		humidityView = new HumidityView(getActivity());
		humidityView.setLayoutParams(layoutParams);
		linearLayout.addView(humidityView);
		
		((MainActivity)getActivity()).getTemperHandler().post(temperView.getUpdateTemper());
		((MainActivity)getActivity()).getHumidityHandler().post(humidityView.getUpdateHumidity());
		
		
		return view;
	}
		

}
