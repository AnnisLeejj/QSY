package com.heking.qsy.activity.Patrol;


import java.util.List;

import com.heking.qsy.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TongzhiAdapter  extends BaseAdapter{
	
	List<?> mViews;
	ViewHolder viewHolder;
	
	
	
	public TongzhiAdapter(List<?> mViews) {
		super();
		this.mViews = mViews;
	}

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mViews.size();
	}

	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mViews.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xunjian_tongzhi,null);
			viewHolder.tvTitle=	(TextView) convertView.findViewById(R.id.tvTitle);
	
			viewHolder.tvUserName=	(TextView) convertView.findViewById(R.id.tvUserName);
			viewHolder.tvContent=	(TextView) convertView.findViewById(R.id.tvContent);	
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		SupervisionBean supervisionBean=(SupervisionBean) mViews.get(position);
		//supervisionBean.get
		viewHolder.tvTitle.setText(supervisionBean.getSupervisionDate().substring(0, 10));
		
		viewHolder.tvUserName.setText(supervisionBean.getUserName());
		
		viewHolder.tvContent.setText(supervisionBean.getSupervisionContent());
		return convertView;
	}
	
	static class ViewHolder{
		TextView tvTitle,tvUserName,tvContent;
	} 

}
