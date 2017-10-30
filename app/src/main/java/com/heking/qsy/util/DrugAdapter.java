package com.heking.qsy.util;

import java.util.ArrayList;

import com.heking.qsy.R;
import com.heking.qsy.activity.DrugInformationActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DrugAdapter extends BaseAdapter {
private ArrayList<DrugBean.Data> List;
private LayoutInflater mInflater;
private Context context;
private Bundle bundledata;
	public DrugAdapter(Context context,ArrayList<DrugBean.Data> List){
		this.List=List;
		this.mInflater=LayoutInflater.from(context);
		this.context=context;
		bundledata=new Bundle();
	}
	@Override
	public int getCount() {
		return List!=null?List.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		return List.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int poiress, View arg1, ViewGroup arg2) {
		toView view=null;
		if(view==null){
			
			arg1=mInflater.inflate(R.layout.ites_list_drug, null);
		}
		view=	(toView) arg1.getTag();	
		
		if(view==null){
			
			view =new toView();
			view.mName=(TextView) arg1.findViewById(R.id.drug_name);
			view.mCoall=(TextView) arg1.findViewById(R.id.drug_firm);
			view.layout=(LinearLayout) arg1.findViewById(R.id.lin_drug_layout);
			
			arg1.setTag(view);
		}
		view.cate();
		
		bundledata.putString("Name", List .get(poiress).getGenericName());
		bundledata.putString("QY", List .get(poiress).getManufacturer());
		bundledata.putString("pzwh", List .get(poiress).getApprovalNumber());
		view.mName.setText(List .get(poiress).getGenericName());
		view.mCoall.setText(List .get(poiress).getManufacturer());
		view.layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent =new Intent(context, DrugInformationActivity.class);
				intent.putExtras(bundledata);
				context.startActivity(intent);
			}
		});
		return arg1;
	}
	
	private class toView{
		
		TextView mName;
		TextView mCoall;
		LinearLayout layout;
		
		public  void cate(){
				mName.setText("");
				mCoall.setText("");
		}
	}

}
