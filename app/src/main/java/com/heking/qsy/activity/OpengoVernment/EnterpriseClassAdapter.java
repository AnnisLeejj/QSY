package com.heking.qsy.activity.OpengoVernment;

import com.heking.qsy.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EnterpriseClassAdapter extends BaseAdapter {
	private	ViewHolder viewHolder;
	private String[] enterpriseClassName;
	private int seletorTag;
	
	public EnterpriseClassAdapter(Context context) {
		enterpriseClassName=context.getResources().getStringArray(R.array.enterprise_class_name1);
	}
//	enterprise_class_name
	@Override
	public int getCount() {
		return enterpriseClassName.length;
	}

	@Override
	public Object getItem(int position) {
		return enterpriseClassName[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			viewHolder =new ViewHolder();
			convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.enterprise_class_gc_item, null);
			viewHolder.tv=(TextView) convertView.findViewById(R.id.tv_EnterpriseClass);
			convertView.setTag(viewHolder);
		
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.tv.setText(enterpriseClassName[position]);
		if(seletorTag==position){
			viewHolder.tv.setActivated(true);
			viewHolder.tv.setTextColor(Color.parseColor("#3cafdf"));
		}else {
			viewHolder.tv.setActivated(false);
			viewHolder.tv.setTextColor(Color.GRAY);
		}
		return convertView;
	}
	
	
	
	
	static class ViewHolder{
	TextView tv;

}
	public void setSeletor(int seletorTag) {
		this.seletorTag = seletorTag;
		notifyDataSetChanged();
	}
	

}
