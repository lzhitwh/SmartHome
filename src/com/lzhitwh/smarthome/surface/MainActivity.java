package com.lzhitwh.smarthome.surface;

import com.lzhitwh.smarthome.R;
import com.lzhitwh.smarthome.surface.ui.AirconditionerFragment;
import com.lzhitwh.smarthome.surface.ui.LightFragment;
import com.lzhitwh.smarthome.surface.ui.OtherFragment;
import com.lzhitwh.smarthome.surface.ui.TelevisionFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity {
	
	private LightFragment lightFragment;
	private TelevisionFragment televisionFragment;
	private AirconditionerFragment airconditionFragment;
	private OtherFragment otherFragment;
	private RadioButton lightRadioButton;
	private RadioButton televisionRadioButton;
	private RadioButton airconditionerRadioButton;
	private RadioButton otherRadioButton;
	private Handler humidityHandler;
	private Handler temperHandler;
	private ImageButton userPhotoButton;
	private FragmentManager fm;
	private FragmentTransaction ft;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		//
//		Bitmap userPhoto = BitmapFactory.decodeResource(this.getResources(),R.drawable.headphoto);
//		ImageButton userImageButton =(ImageButton)findViewById(R.id.user_pho);
//		userPhoto = zoomImg(userPhoto);
//		userImageButton.setImageBitmap(userPhoto);
		
		//
		fm = getFragmentManager();
		ft = fm.beginTransaction();
		lightFragment = LightFragment.newInstance();
		televisionFragment = TelevisionFragment.newInstance();
		airconditionFragment = AirconditionerFragment.newInstance();
		otherFragment = OtherFragment.newInstance();
		ft.add(R.id.app_cont,lightFragment, "lightFragment");
		ft.add(R.id.app_cont,televisionFragment, "televisionFragment");
		ft.add(R.id.app_cont,airconditionFragment, "airconditionFragment");
		ft.add(R.id.app_cont,otherFragment, "otherFragment");
		ft.hide(lightFragment).show(televisionFragment).hide(airconditionFragment).hide(otherFragment).commit();
		lightRadioButton = (RadioButton) findViewById(R.id.light_con);
		televisionRadioButton = (RadioButton) findViewById(R.id.televison_con);
		airconditionerRadioButton = (RadioButton) findViewById(R.id.aircondition_con);
		otherRadioButton = (RadioButton) findViewById(R.id.other_con);
		userPhotoButton = (ImageButton) findViewById(R.id.user_pho);
		lightRadioButton.setTextColor(Color.RED);
		
		RadioGroup funRadioGroup = (RadioGroup) findViewById(R.id.fun_select);
		funRadioGroup.setOnCheckedChangeListener(new functionOnCheckedChangeListener());
		userPhotoButton.setOnClickListener(new ImageButtonClickListener());
		humidityHandler = new Handler();
		temperHandler = new Handler();
	}
	
	private class functionOnCheckedChangeListener implements OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			fm = getFragmentManager();
			ft = fm.beginTransaction();
			switch(arg1){
				case R.id.light_con:		
					lightRadioButton.setTextColor(Color.RED);
					televisionRadioButton.setTextColor(Color.BLACK);
					airconditionerRadioButton.setTextColor(Color.BLACK);
					otherRadioButton.setTextColor(Color.BLACK);
					ft.show(lightFragment).hide(televisionFragment).hide(airconditionFragment).hide(otherFragment).commit();
				break;
					
				case R.id.televison_con:
					lightRadioButton.setTextColor(Color.BLACK);
					televisionRadioButton.setTextColor(Color.RED);
					airconditionerRadioButton.setTextColor(Color.BLACK);
					otherRadioButton.setTextColor(Color.BLACK);
					ft.hide(lightFragment).show(televisionFragment).hide(airconditionFragment).hide(otherFragment).commit();
				break;
				
				case R.id.aircondition_con:	
					lightRadioButton.setTextColor(Color.BLACK);
					televisionRadioButton.setTextColor(Color.BLACK);
					airconditionerRadioButton.setTextColor(Color.RED);
					otherRadioButton.setTextColor(Color.BLACK);
//					ft.hide(lightFragment).hide(televisionFragment).show(airconditionFragment).hide(otherFragment).commit();
				break;
				
				case R.id.other_con:		
					lightRadioButton.setTextColor(Color.BLACK);
					televisionRadioButton.setTextColor(Color.BLACK);
					airconditionerRadioButton.setTextColor(Color.BLACK);
					otherRadioButton.setTextColor(Color.RED);
//					ft.hide(lightFragment).hide(televisionFragment).hide(airconditionFragment).show(otherFragment).commit();
				break;
				
				default:	break;
			}
		}
	}
	
	private class ImageButtonClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent setIntent = new Intent(MainActivity.this,SettingActivity.class);
			startActivity(setIntent);
		}
	}
	
	public Bitmap zoomImg(Bitmap bm){    
	    // 
	    int width = bm.getWidth();    
	    int height = bm.getHeight();    
	    //   
	    DisplayMetrics metrics = getResources().getDisplayMetrics(); 
	    
	    float scaleHeight = ((float)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, metrics))/height;    
	    //     
	    Matrix matrix = new Matrix();    
	    matrix.postScale(scaleHeight, scaleHeight);    
	    //     
	    Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);    
	    return newbm;    
	}

	public Handler getHumidityHandler() {
		return humidityHandler;
	}

	public Handler getTemperHandler() {
		return temperHandler;
	}
	
}
