package com.heking.qsy.activity.ConvenienceService.util;

import java.util.ArrayList;

import com.heking.qsy.R;
import com.heking.qsy.activity.FirmShow.BaiDuFirmTypeActivity;
import com.heking.qsy.util.FirmTypeBean;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MonitoringAdapter extends BaseAdapter {

	private ArrayList<FirmTypeBean.Data> list;
	private Context context;
	private LayoutInflater mInflater;
	private int State;
		public MonitoringAdapter(ArrayList<FirmTypeBean.Data> list,Context context,int State){
			this.list=list;
			this.State=State;
			this.context=context;
			mInflater=LayoutInflater.from(context);
		}
		
		
		public int getCount() {
			// TODO Auto-generated method stub
			return list!=null?list.size():0;
		}

		
		public Object getItem(int arg0) {
			
			return list.get(arg0);
		}

		
		public long getItemId(int arg0) {
			return arg0;
		}

		@SuppressLint("NewApi")
		@SuppressWarnings("deprecation")
		public View getView(final int proenat, View arg1, ViewGroup arg2) {
			Layout layout=null;
			if(layout==null){
				arg1=mInflater.inflate(R.layout.list_view_firm3, null);
			}
			layout=	(Layout) arg1.getTag();	
			
			if(layout==null){
				layout =new Layout();
				layout.textView=(TextView) arg1.findViewById(R.id.file_list_textview);
				
			
				
//				if(AppContext.THEME){
//					layout.textView.setTextColor(Color.parseColor("#70b9e0"));
//				}else{
//					layout.textView.setTextColor(Color.parseColor("#f9986e"));
//				}
//				
				arg1.setTag(layout);
			}
			layout.cose();
		
			((TextView) arg1.findViewById(R.id.ccxxvvxx)).setVisibility(LinearLayout.VISIBLE);
			layout.textView.setText(list.get(proenat).getFirmName());
			arg1.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					FirmTypeBean.Data data=list.get(proenat);
					Intent intent=new Intent(context, BaiDuFirmTypeActivity.class);
					Bundle bundle=new Bundle();
					bundle.putSerializable("FIRMTYPE", data);
					bundle.putInt("State", State);
					intent.putExtras(bundle);
					context.startActivity(intent);
				}
			});
			return arg1;
		}
	private class Layout{
	private TextView textView;
	private void cose(){
			textView.setText("");	
			
		}
		
	}
	}
