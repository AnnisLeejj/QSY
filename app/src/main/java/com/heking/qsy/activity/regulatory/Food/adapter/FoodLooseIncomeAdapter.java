package com.heking.qsy.activity.regulatory.Food.adapter;

import java.util.ArrayList;

import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.RegulatoryUTil;
import com.heking.qsy.activity.regulatory.Food.FoodFirmType;
import com.heking.qsy.activity.regulatory.Food.tab.TB_FoodLooseIncome;
import com.heking.qsy.util.FirmTypeBean;
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

public class FoodLooseIncomeAdapter extends BaseAdapter {
	
	private ArrayList<TB_FoodLooseIncome.Data>  list;
	private Context context;
	private LayoutInflater mInflater;
	private int state;
	private FirmTypeBean.Data data;
	private RegulatoryUTil uTil;
	public  FoodLooseIncomeAdapter(Context context,ArrayList<TB_FoodLooseIncome.Data> list,FirmTypeBean.Data  data,RegulatoryUTil uTil){
		this.list=list;
		this.context=context;
		this.state=state;
		this.data=data;
		this.uTil=uTil;
		this.mInflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int pronts, View arg1, ViewGroup arg2) {
		Layout layout=null;
		if(layout==null){
			
			arg1=mInflater.inflate(R.layout.view_show, null);
		}
		layout=	(Layout) arg1.getTag();	
		
		if(layout==null){
			
			layout =new Layout();
			layout.Name=(TextView) arg1.findViewById(R.id.name_biaoti);
			layout.Nmaetype=(TextView) arg1.findViewById(R.id.name_biaoti_type);
			layout.button=(LinearLayout) arg1.findViewById(R.id.button_layout);
			arg1.setTag(layout);
		}
		layout.cose();
		layout.Name.setText(list.get(pronts).getGenericName());
		layout.Nmaetype.setText(list.get(pronts).getLooseIncomeReason());//new Tooluite().getDate(list.get(pronts).getProductionDate())+"-"+list.get(pronts).getLooseIncomeReason());
		layout.button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				Bundle bundle=new Bundle();
				bundle.putSerializable("data_class_message", list.get(pronts));
				bundle.putInt("state", 2300692);
				bundle.putSerializable("RegulatoryUTil", uTil);
				
				bundle.putSerializable("Firm", data);
				intent.setClass(context, FoodFirmType.class);
				intent.putExtras(bundle);
				context.startActivity(intent);
				
				
			}
			
			
			
		});
		return arg1;
	}
	
	
	
private class Layout{
private TextView Name;
private TextView Nmaetype;
private LinearLayout button;

	
private void cose(){
		Name.setText("");	
		Nmaetype.setText("");
	}
	
}
}