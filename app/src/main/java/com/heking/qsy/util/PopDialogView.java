package com.heking.qsy.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;

import com.heking.qsy.R;



public class PopDialogView extends Dialog {
	
	public PopDialogView(Context context) {
		this(context,R.style.Dialog);
		init(context);
	}
	public PopDialogView(Context context,boolean boo) {
		this(context,R.style.Dialog);
		init(context,boo);
	}

	public PopDialogView(Context context, int theme) {
		super(context, theme==0?R.style.Dialog:theme);
		init(context);
	}
	public PopDialogView(Context context, int theme,boolean boo) {
		super(context, theme==0?R.style.Dialog:theme);
		init(context,boo);
	}

	
	private void init(Context context){
		Window window = getWindow();
		window.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
		setCancelable(true);
		
	}
	private void init(Context context,boolean bool){
		if(bool){
		Window window = getWindow();
		window.setGravity(Gravity.CENTER|Gravity.BOTTOM);
		setCancelable(true);
		}else{
			init(context);
		}
	}

	
}
