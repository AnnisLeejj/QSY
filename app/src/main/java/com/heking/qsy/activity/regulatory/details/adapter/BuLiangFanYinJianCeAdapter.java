package com.heking.qsy.activity.regulatory.details.adapter;

import java.util.ArrayList;

import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.ToolUtil;
import com.heking.qsy.activity.regulatory.details.type.Tooluite;
import com.heking.qsy.activity.regulatory.details.type.TypeShow;
import com.heking.qsy.activity.regulatory.tab.TB_BuLiangFanyingJianCe;
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

public class BuLiangFanYinJianCeAdapter extends BaseAdapter {
	
	private ArrayList<TB_BuLiangFanyingJianCe.data>  list;
	private Context context;
	private LayoutInflater mInflater;
	private int state;
	private FirmTypeBean.Data data;
	public  BuLiangFanYinJianCeAdapter(Context context,ArrayList<TB_BuLiangFanyingJianCe.data> list){
		this.list=list;
		this.context=context;
		this.state=state;
		this.data=data;
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
		layout.Name.setText(list.get(pronts).getPatientName());
		layout.Nmaetype.setText(new Tooluite().getDate(list.get(pronts).getReportDateTime())+"-"+list.get(pronts).getReporterName());
		layout.button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String data="{\"RequestMethod\":\"get_firm_data_AdverseReaction_by_id\",\"RequestParams\":\"{\\\"Id\\\":"+list.get(pronts).getID()+"}\"}";
				
				Intent intent=new Intent();
				Bundle bundle=new Bundle();
				bundle.putSerializable("data_class_message", list.get(pronts));
				bundle.putInt("state", 99086);
				intent.setClass(context, TypeShow.class);
				intent.putExtra("Dataurl", ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getSouSuoURL(data));
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