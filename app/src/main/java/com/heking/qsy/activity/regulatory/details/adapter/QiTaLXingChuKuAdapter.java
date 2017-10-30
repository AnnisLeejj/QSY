package com.heking.qsy.activity.regulatory.details.adapter;

import java.util.ArrayList;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.ToolUtil;
import com.heking.qsy.activity.regulatory.details.type.ChaiGouRuKuType;
import com.heking.qsy.activity.regulatory.tab.TB_qitaleixingchuku;
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

public class QiTaLXingChuKuAdapter extends BaseAdapter {
	
	private ArrayList<TB_qitaleixingchuku.data>  list;
	private Context context;
	private LayoutInflater mInflater;
	private int state;
	private FirmTypeBean.Data data;
	public  QiTaLXingChuKuAdapter(Context context,ArrayList<TB_qitaleixingchuku.data> list,FirmTypeBean.Data data){
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
		layout.Name.setText(list.get(pronts).getOutID());
		layout.Nmaetype.setText(list.get(pronts).getOutTypeName()+"-"+list.get(pronts).getClientName());
		layout.button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String url=ToolUtil.DataUrl.getUrl(AppContext.Parameter.GET_FIRM_DATA)+ToolUtil.getType("get_other_out_Storages_detail_list", list.get(pronts).getID());
				Intent intent=new Intent();
				Bundle bundle=new Bundle();
				
				bundle.putSerializable("data", data);
				bundle.putSerializable("MESSAGE", list.get(pronts));
				bundle.putInt("statess", 1310035);
				
				intent.putExtras(bundle);
				intent.putExtra("keyUrl", url);
				intent.setClass(context, ChaiGouRuKuType.class);
				
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