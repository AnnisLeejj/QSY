package com.heking.qsy.yuying;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;

public class YYshow {
	private PopupWindow mYuying;
	private View yyView;
	private TextView mYYButton;
	
	private YuYIng YUyingUlit=new YuYIng();
	private Context context;
	public YYshow(final Context context,Activity activity,YUMessageData messageData){
		this.context=context;
		yyView=LayoutInflater.from(context).inflate(R.layout.yuying, null);
		mYYButton=(TextView) yyView.findViewById(R.id.text);
		YUyingUlit.setYymssage(messageData);
		
		if(AppContext.THEME){
			mYYButton.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.yuyin_01));
		}else{
			mYYButton.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.yuyin_02));
		}
		
		mYYButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				YUyingUlit.setYuYing(context);
			}
		});
		
		SoftKeyBoardListener.setListener(activity,
				new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
					@Override
					public void keyBoardShow(int height) {
						
						mShowPopwindow(height);
						
					}

					@Override
					public void keyBoardHide(int height) {
						if(mYuying!=null){
			        		mYuying.dismiss();
			        	}
						}
				});
		
	}
	/**
	 * TODO:语音输入功能
	 * @param xINt
	 */
		private  void mShowPopwindow(int xINt) {

			if (mYuying == null) {
				mYuying = new PopupWindow(yyView, LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
				// 设置一个空白的背景
				mYuying.setBackgroundDrawable(new BitmapDrawable());
				// 点击屏幕可消失
				mYuying.setOutsideTouchable(false);
				// 获得焦点
				mYuying.setFocusable(false);
				//设置pop消失的监听事件
				
			}
			mYuying.showAtLocation(LayoutInflater.from(context).inflate(R.layout.complaint_reporting_activity_2, null), Gravity.CENTER, xINt, 0);
			////设置按钮不可以点击
//			mText.setEnabled(false);
		}
}
