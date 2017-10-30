package com.heking.qsy.activity.regulatory.Food.Bevera;

import com.heking.qsy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TypeView {
	private LinearLayout mLayout;
	private View mViewLayout;
	private Context context;

	public TypeView(Context context) {
		this.context = context;
		mViewLayout = LayoutInflater.from(context).inflate(
				R.layout.m_view_layout, null);
		mLayout = (LinearLayout) mViewLayout.findViewById(R.id.type_layout_);
	}

	public void setValue(String Name1, String Value1, String Name2,
			String Value2) {
		TextView mTextName1, mTextName2, mTextValue1, mTextValue2;
		View mView;

		mView = LayoutInflater.from(context).inflate(R.layout.zi_view_, null);
		mTextName1 = (TextView) mView.findViewById(R.id.type_name_01);
		mTextValue1 = (TextView) mView.findViewById(R.id.type_value_01);

		mTextName2 = (TextView) mView.findViewById(R.id.type_name_02);
		mTextValue2 = (TextView) mView.findViewById(R.id.type_value_02);

		// 设置为黑体加粗
		mTextName1.getPaint().setFakeBoldText(true);
		mTextName2.getPaint().setFakeBoldText(true);

		mTextName1.setText(Name1);
		mTextValue1.setText(Value1);

		mTextName2.setText(Name2);
		mTextValue2.setText(Value2);

		mLayout.addView(mView);
	}

	public void setValue(String Name1, String Value1) {
		TextView mTextName1, mTextValue1;
		View mView;
		mView = LayoutInflater.from(context).inflate(R.layout.zi_view_, null);
		mTextName1 = (TextView) mView.findViewById(R.id.type_name_01);
		mTextValue1 = (TextView) mView.findViewById(R.id.type_value_01);
		
		mTextName1.getPaint().setFakeBoldText(true);


		mTextName1.setText(Name1);
		mTextValue1.setText(Value1);

		mLayout.addView(mView);
	}

	public View getmViewLayout() {
		return mViewLayout;
	}

}
