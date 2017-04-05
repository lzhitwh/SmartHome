package com.lzhitwh.smarthome.roundcontrol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PVControl extends View {

	private Path up_p, down_p, left_p, right_p, center_p;
	private Region up, down, left, right, center;
	private int CENTER = 0, UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4, touchFlag = -1, currentFlag = -1;
	private MenuListener mListener = null;
	private Paint mDeafultPaint = null;
	private Matrix mMapMatrix = null;

	private int mTouchedColor = 0xFFDF9C81;
	// private int mDefauColor = 0xFF4E5268;
	private int mDefauColor = 0xFF575757;

	public PVControl(Context context) {
		this(context, null);
	}

	public PVControl(Context context, AttributeSet attrs) {
		super(context, attrs);

		up_p = new Path();
		down_p = new Path();
		left_p = new Path();
		right_p = new Path();
		center_p = new Path();

		up = new Region();
		down = new Region();
		left = new Region();
		right = new Region();
		center = new Region();

		mDeafultPaint = new Paint();
		mDeafultPaint.setColor(mDefauColor);
		mDeafultPaint.setAntiAlias(true);

		mMapMatrix = new Matrix();
		mMapMatrix.reset();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int minWidth = 0;
		if (widthSize == 0 || heightSize == 0) {
			if (widthSize == 0 && heightSize != 0) {
				minWidth = heightSize;
			} else if (widthSize != 0 && heightSize == 0) {
				minWidth = widthSize;
			} else {
				minWidth = 500;
			}
		} else {
			minWidth = (int) Math.min(widthSize, heightSize);
		}
		minWidth = (int) (minWidth * 0.8);
		widthSize = minWidth;
		heightSize = minWidth;
		setMeasuredDimension(minWidth, minWidth);
		// ע���������Ĵ�С
		Region globalRegion = new Region(-widthSize, -heightSize, widthSize, heightSize);

		int br = minWidth / 2;
		RectF bigCircle = new RectF(-br, -br, br, br);

		int sr = minWidth / 4;
		RectF smallCircle = new RectF(-sr, -sr, sr, sr);

		float bigSweepAngle = 84;
		float smallSweepAngle = -80;

		// ������ͼ��С����ʼ�� Path �� Region
		center_p.addCircle(0, 0, 0.2f * minWidth, Path.Direction.CW);
		center.setPath(center_p, globalRegion);

		right_p.addArc(bigCircle, -40, bigSweepAngle);
		right_p.arcTo(smallCircle, 40, smallSweepAngle);
		right_p.close();
		right.setPath(right_p, globalRegion);

		down_p.addArc(bigCircle, 50, bigSweepAngle);
		down_p.arcTo(smallCircle, 130, smallSweepAngle);
		down_p.close();
		down.setPath(down_p, globalRegion);

		left_p.addArc(bigCircle, 140, bigSweepAngle);
		left_p.arcTo(smallCircle, 220, smallSweepAngle);
		left_p.close();
		left.setPath(left_p, globalRegion);

		up_p.addArc(bigCircle, 230, bigSweepAngle);
		up_p.arcTo(smallCircle, 310, smallSweepAngle);
		up_p.close();
		up.setPath(up_p, globalRegion);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float[] pts = new float[2];
		pts[0] = event.getX();
		pts[1] = event.getY();
		mMapMatrix.mapPoints(pts);

		int x = (int) pts[0];
		int y = (int) pts[1];

		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			touchFlag = getTouchedPath(x, y);
			currentFlag = touchFlag;
			break;
		case MotionEvent.ACTION_MOVE:
			currentFlag = getTouchedPath(x, y);
			break;
		case MotionEvent.ACTION_UP:
			currentFlag = getTouchedPath(x, y);
			// �����ָ���������̧��������ͬ�Ҳ�Ϊ�գ����жϵ���¼�
			if (currentFlag == touchFlag && currentFlag != -1 && mListener != null) {
				if (currentFlag == CENTER) {
					mListener.onCenterCliched();
				} else if (currentFlag == UP) {
					mListener.onUpCliched();
				} else if (currentFlag == RIGHT) {
					mListener.onRightCliched();
				} else if (currentFlag == DOWN) {
					mListener.onDownCliched();
				} else if (currentFlag == LEFT) {
					mListener.onLeftCliched();
				}
			}
			touchFlag = currentFlag = -1;
			break;
		case MotionEvent.ACTION_CANCEL:
			touchFlag = currentFlag = -1;
			break;
		}

		invalidate();
		return true;
	}

	// ��ȡ��ǰ���������ĸ�����
	int getTouchedPath(int x, int y) {
		if (center.contains(x, y)) {
			return 0;
		} else if (up.contains(x, y)) {
			return 1;
		} else if (right.contains(x, y)) {
			return 2;
		} else if (down.contains(x, y)) {
			return 3;
		} else if (left.contains(x, y)) {
			return 4;
		}
		return -1;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(getWidth() / 2, getHeight() / 2);

		// ��ȡ��������(�����)
		if (mMapMatrix.isIdentity()) {
			canvas.getMatrix().invert(mMapMatrix);
		}

		// ����Ĭ����ɫ
		canvas.drawPath(center_p, mDeafultPaint);
		canvas.drawPath(up_p, mDeafultPaint);
		canvas.drawPath(right_p, mDeafultPaint);
		canvas.drawPath(down_p, mDeafultPaint);
		canvas.drawPath(left_p, mDeafultPaint);

		// ���ƴ���������ɫ
		mDeafultPaint.setColor(mTouchedColor);
		if (currentFlag == CENTER) {
			canvas.drawPath(center_p, mDeafultPaint);
		} else if (currentFlag == UP) {
			canvas.drawPath(up_p, mDeafultPaint);
		} else if (currentFlag == RIGHT) {
			canvas.drawPath(right_p, mDeafultPaint);
		} else if (currentFlag == DOWN) {
			canvas.drawPath(down_p, mDeafultPaint);
		} else if (currentFlag == LEFT) {
			canvas.drawPath(left_p, mDeafultPaint);
		}
		mDeafultPaint.setColor(mDefauColor);
	}

	public void setListener(MenuListener listener) {
		mListener = listener;
	}

	// ����¼�������
	public interface MenuListener {
		void onCenterCliched();

		void onUpCliched();

		void onRightCliched();

		void onDownCliched();

		void onLeftCliched();
	}
}
