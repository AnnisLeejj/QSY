package com.heking.qsy.util;



import com.heking.qsy.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 自定义下拉列表,自己往里面加视图必须只有一个视图 
 */
public class MySpinnerSelect extends LinearLayout implements View.OnClickListener {

	/**
	 * 头部点击的那个布局
	 */
	private LinearLayout mHeadLinearLayout;
	/**
	 * 头部布局中的文字
	 */
	private TextView mTitleTextView;
	/**
	 * 头部布局中的图标
	 */
	private ImageView mIconImageView;

	/**
	 * 其包含的视图，只能有一个总的
	 */
	private View mContentView;

	/**
	 * 是否加载过一次layout
	 */
	private boolean loadOnce;

	/**
	 * 记录点击的状态
	 */
	private int i = 1;
	private static final String TAG = "MySpinnerSelect";

	/**
	 * 自定义属性的值
	 */
	private int mBackgroundColor, mTextColor, mSelectBackgroundColor, mSelectTextColor, mIconSrc;
	private float mViewWidth, mViewHeight;

	public MySpinnerSelect(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHeadLinearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.spinner_top, null, true);
		mTitleTextView = (TextView) mHeadLinearLayout.findViewById(R.id.spinner_title);
		mIconImageView = (ImageView) mHeadLinearLayout.findViewById(R.id.spinner_icon);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MySpinnerSelect);
		mBackgroundColor = typedArray.getColor(R.styleable.MySpinnerSelect_backgroundColor,
				getResources().getColor(R.color.white));
		mTextColor = typedArray.getColor(R.styleable.MySpinnerSelect_textColor, getResources().getColor(android.R.color.black));
		mSelectBackgroundColor = typedArray.getColor(R.styleable.MySpinnerSelect_selectBackgroundColor,
				getResources().getColor(R.color.white));
		mSelectTextColor = typedArray.getColor(R.styleable.MySpinnerSelect_selectTextColor,
				getResources().getColor(android.R.color.black));
		mIconSrc = typedArray.getResourceId(R.styleable.MySpinnerSelect_iconSrc, R.drawable.image_firm_1);
		mViewWidth = typedArray.getDimensionPixelSize(R.styleable.MySpinnerSelect_viewWidth, 100);
		mViewHeight = typedArray.getDimensionPixelSize(R.styleable.MySpinnerSelect_viewHeight, 60);

		mHeadLinearLayout.setBackgroundColor(mBackgroundColor);
		mTitleTextView.setTextColor(mTextColor);
		mIconImageView.setImageResource(mIconSrc);

		LinearLayout.LayoutParams layoutParams = new LayoutParams((int) mViewWidth, (int) mViewHeight);
		mHeadLinearLayout.setLayoutParams(layoutParams);
		setOrientation(VERTICAL);
		addView(mHeadLinearLayout, 0);
		mHeadLinearLayout.setOnClickListener(this);

		typedArray.recycle();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed && !loadOnce) {
			mContentView = getChildAt(1);
			if (mContentView != null)
				mContentView.setVisibility(GONE);
			loadOnce = true;

		}
	}

	public int getBackgroundColor() {
		return mBackgroundColor;
	}

	public int getIconSrc() {
		return mIconSrc;
	}

	public int getSelectBackgroundColor() {
		return mSelectBackgroundColor;
	}

	public int getSelectTextColor() {
		return mSelectTextColor;
	}

	public int getTextColor() {
		return mTextColor;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.spinner_top:
			changeView();
			break;
		}
	}

	@Override
	public void setBackgroundColor(int backgroundColor) {
		mBackgroundColor = backgroundColor;
	}

	public void setIconSrc(int iconSrc) {
		mIconSrc = iconSrc;
	}

	public void setSelectBackgroundColor(int selectBackgroundColor) {
		mSelectBackgroundColor = selectBackgroundColor;
	}

	public void setSelectTextColor(int selectTextColor) {
		mSelectTextColor = selectTextColor;
	}

	public void setTextColor(int textColor) {
		mTextColor = textColor;
	}

	public float getViewHeight() {
		return mViewHeight;
	}

	public float getViewWidth() {
		return mViewWidth;
	}

	public void setViewHeight(float viewHeight) {
		mViewHeight = viewHeight;
	}

	public void setViewWidth(float viewWidth) {
		mViewWidth = viewWidth;
	}

	/**
	 * 改变视图的状态，发生在点击事件中(提供给外部想让其改变时，调用该方法)
	 */
	public void changeView() {
		changeViewVisiblity();
		changeIconAnimation();
		i *= -1;
	}

	/**
	 * 改变视图的状态
	 */
	private void changeViewVisiblity() {
		// i>0说明包含的视图是隐藏的，需要把它显示出来
		if (i > 0) {
			if (mContentView != null)
				mContentView.setVisibility(VISIBLE);
			mHeadLinearLayout.setBackgroundColor(mSelectBackgroundColor);
			mTitleTextView.setTextColor(mSelectTextColor);
		} else if (i < 0) {
			if (mContentView != null)
				mContentView.setVisibility(GONE);
			mHeadLinearLayout.setBackgroundColor(mBackgroundColor);
			mTitleTextView.setTextColor(mTextColor);
		}
	}

	/**
	 * 根据当前状态改变上面箭头的方向
	 */
	private void changeIconAnimation() {
		Log.i(TAG, "-->changeIconAnimation");
		float pivotX = mIconImageView.getWidth() / 2f;
		float pivotY = mIconImageView.getHeight() / 2f;
		float fromDegree = 0f;
		float toDegree = 0f;
		if (i > 0) {
			fromDegree = 0f;
			toDegree = 180f;
		} else if (i < 0) {
			fromDegree = 180f;
			toDegree = 360f;
		}

		RotateAnimation animation = new RotateAnimation(fromDegree, toDegree, pivotX, pivotY);

		animation.setDuration(100);
		animation.setFillAfter(true);

		mIconImageView.startAnimation(animation);
	}
}