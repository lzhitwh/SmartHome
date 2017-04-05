package com.lzhitwh.smarthome.temper;

import java.text.DecimalFormat;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.lzhitwh.smarthome.surface.MainActivity;;

public class TemperatureView extends View {

	private Context context = null;
	private float viewSize = 0.0f;
	private float temperValue = -30.0f;
	private int temperPosition = 0;
	private int currentPosition = 0;
	
	public TemperatureView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public TemperatureView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
		// TODO Auto-generated constructor stub
	}

	public TemperatureView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int viewWidth = (int)MeasureSpec.getSize(widthMeasureSpec);
		int viewHeight = (int)MeasureSpec.getSize(heightMeasureSpec);
		viewSize = Math.min(viewWidth, viewHeight)/2;
		setMeasuredDimension(viewWidth, viewHeight);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.save();									//���滭��״̬
		canvas.translate(getWidth()/2,getHeight()/2);	//������ԭ���ƶ������롣
		drawBackgroundCir(canvas);						//������Բ��
		drawPointArc(canvas);							//��ָʾԲ�����������֣�
		drawMarkArc(canvas);							//���̶�Բ���Լ��̶ȣ�
		drawPointer(canvas);							//��ָ�롣
		drawTemperText(canvas,temperValue);
		canvas.restore();           					//������ԭ���ƻ����Ͻ�
	}
	
	private void drawBackgroundCir(Canvas canvas){
		Paint mPaint = new Paint();
		//��������Բ��
		mPaint.setColor(Color.rgb(255, 255, 224));
		mPaint.setStyle(Style.FILL);
		canvas.drawCircle(0,0,viewSize*0.90f, mPaint);	//����Ϊ��������뾶��90%
	}
	
	private void drawPointArc(Canvas canvas){
		//����ָʾԲ��������
		Paint mPaint = new Paint();
		canvas.save();
		RectF rectF = new RectF(-viewSize*0.90f,-viewSize*0.90f,viewSize*0.90f,viewSize*0.90f);	//ָʾԲ���뱳����С��ͬ��
		mPaint.setColor(Color.rgb(39, 101,203));
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeCap(Paint.Cap.BUTT);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(viewSize*0.12f);			//����ָʾԲ��Ϊ����뾶��С��12%
		canvas.drawArc(rectF, 135,152.2f,false, mPaint);
		mPaint.setColor(Color.rgb(17, 134, 3));
		canvas.drawArc(rectF, 287,33.5f,false, mPaint);
		mPaint.setColor(Color.RED);
		canvas.drawArc(rectF, 320.5f,84.5f,false, mPaint);
	}
	
	private void drawMarkArc(Canvas canvas){
		Paint mPaint = new Paint();
		//���̶Ȼ��ߡ�
		float markRadius = viewSize*0.84f;		//���ÿ̶Ȼ���̶��� radius = (1-90%)+(12%/2)
		RectF markRect = new RectF(-markRadius,-markRadius,markRadius,markRadius);
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(viewSize*0.016f);
		canvas.drawArc(markRect, 135, 270,false, mPaint);
		
		//���̶ȡ�
		float markAngle = 270f/40f;
		canvas.save();
		canvas.rotate(-135);
		mPaint.setStrokeWidth(viewSize*0.014f);		//���ÿ̶��߿��Ϊ����뾶��1.4%
		int markNum=-40;
		for(int i=0;i<41;i++){
			if(i%5==0){			//��̶� ���ȿ̶Ȼ��뾶��8%
				canvas.drawLine(0,-markRadius*0.92f,0,-markRadius, mPaint);
				markNum=markNum+10;
				mPaint.setTextSize(markRadius*0.16f);			//���������С
				canvas.drawText(""+markNum,-(""+markNum).length()*markRadius*0.04f,-markRadius*0.80f , mPaint);
			}else{				//С�̶� ���ȿ̶Ȼ��뾶��5%
				canvas.drawLine(0,-markRadius*0.95f,0,-markRadius, mPaint);
			}
			canvas.rotate(markAngle);
		}
		canvas.restore();		//�ָ�һ�λ���״̬
	}
	
	private void drawPointer(Canvas canvas){
		Paint mPaint = new Paint();
		//��ָ��
		//ָ��Բ
		
		mPaint.setStyle(Style.FILL_AND_STROKE);
		mPaint.setColor(Color.rgb(136, 136, 136));
		canvas.drawCircle(0,0,viewSize*0.14f, mPaint);	//����ָ����Բ��С
		//ָ�벿��
		canvas.save();
		canvas.rotate(-135f+currentPosition);
		mPaint.setColor(Color.rgb(244, 239, 233));
		mPaint.setStyle(Style.FILL_AND_STROKE);
		Path pointPath = new Path();
		pointPath.moveTo(-viewSize*0.08f,0);	
		pointPath.lineTo(0,-viewSize*0.70f); 		//ָ�볤��
		pointPath.lineTo(viewSize*0.08f,0);
		pointPath.addArc(new RectF(-viewSize*0.08f,-viewSize*0.08f,viewSize*0.08f,viewSize*0.08f),0,180);
		pointPath.close();
		canvas.drawPath(pointPath,mPaint);
		canvas.restore();
		//�ڲ�Բ
		mPaint.setColor(Color.rgb(136, 136, 136));
		canvas.drawCircle(0,0,viewSize*0.04f, mPaint);
	}
	
	private void drawTemperText(Canvas canvas,float temper){
		Paint mPaint = new Paint();
		//��������ʾ��
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.WHITE);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawRect(new RectF(-viewSize*0.3f,viewSize*0.5f,viewSize*0.3f,viewSize*0.66f),mPaint);
		//��ʾ����
		String temperString = new DecimalFormat("#.00").format(temper);
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(viewSize*0.14f);
		canvas.drawText(temperString,-viewSize*0.14f/4f*temperString.length(),viewSize*0.64f, mPaint);
	}
	
	private int dp2Pix(int dp){
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
	}
	private int sp2Pix(int sp){
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
	}
	
	public void refreshTemper(){
		postInvalidate();
	}
	
	public Runnable updateTemper = new Runnable(){
		public void run(){
			temperPosition =(int)((temperValue+30)*3.375f+0.5f);
			if(currentPosition < temperPosition){
				currentPosition++;
				refreshTemper();
			}else if(currentPosition > temperPosition){
				currentPosition--;
				refreshTemper();
			}else{
			}
			((MainActivity)context).getTemperHandler().postDelayed(updateTemper,10);
		}
	};

	public float getTemperValue() {
		return temperValue;
	}

	public void setTemperValue(float temperValue) {
		this.temperValue = temperValue;
	}

	public Runnable getUpdateTemper() {
		return updateTemper;
	}
}	
