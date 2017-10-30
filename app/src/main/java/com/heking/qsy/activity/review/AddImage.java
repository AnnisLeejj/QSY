package com.heking.qsy.activity.review;

import com.heking.qsy.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class AddImage {
	private TextView mIamge;
	private View mView;
	
	@SuppressLint("NewApi")
	public View onImage (Context  context,Bitmap bitmap){
		mView=LayoutInflater.from(context).inflate(R.layout.add_image_layout_item, null);
		mIamge=(TextView) mView.findViewById(R.id.image_test);
		mIamge.setBackground(new BitmapDrawable(bitmap));
		return mView;
	}
	
}
