package com.heking.qsy.activity.review;

import java.util.List;
import com.baidu.mapapi.search.core.PoiInfo;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class BaiDuMapAdapter extends BaseAdapter {
private LayoutInflater mInflater;
private Context context;
private List<PoiInfo> list;

	public BaiDuMapAdapter(Context context,List<PoiInfo> list) {
		this.context=context;
		this.list=list;
		mInflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list==null?0:list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int intpot, View arg1, ViewGroup arg2) {
		toView view=null;
		if(view==null){
			
			arg1=mInflater.inflate(R.layout.map_adapter_list, null);
		}
		view=	(toView) arg1.getTag();	
		
		if(view==null){
			
			view =new toView();
			view.textView=(TextView) arg1.findViewById(R.id.text_button_map);
			view.Review=(TextView) arg1.findViewById(R.id.Review_get_to);
//			if(AppContext.THEME){
//				view.textView.setTextColor(Color.parseColor("#808080"));
//				view.Review.setTextColor(Color.parseColor("#fb5747"));
//			}else{
//				view.textView.setTextColor(Color.parseColor("#EB6024"));
//				view.Review.setTextColor(Color.parseColor("#50EB6024"));
//			}
			arg1.setTag(view);
		}
		view.cate();
		view.textView.setText(list.get(intpot).name);
		view.Review.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent =new Intent(context, ToReviewforBaidu.class);
				AppContext.BundelPoiMess.poiInfo=list.get(intpot);
				context.startActivity(intent);
			}
		});
		view.textView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//TODO
				
				
			}
		});
		return arg1;
	}

private class toView{
	TextView textView;
	TextView Review;
	public  void cate(){
		textView.setText("");
	}
	
}
	
	
}
