package com.heking.qsy.activity.review;

import java.util.ArrayList;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.IWantToReview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MapAdapter extends BaseAdapter {
private LayoutInflater mInflater;
private Context context;
 private ArrayList<Object> list;
private IWantToReview mIWantToReview;

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int i) {
		return null;
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		return null;
	}

		public MapAdapter(Context context,ArrayList<Object> list) {
		this.context=context;
		this.list=list;
		mInflater=LayoutInflater.from(context);
		mIWantToReview=(IWantToReview) context;
	}
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return list==null?0:list.size();
//	}
//
//	@Override
//	public Object getItem(int arg0) {
//		// TODO Auto-generated method stub
//		return list.get(arg0);
//	}
//
//	@Override
//	public long getItemId(int arg0) {
//		// TODO Auto-generated method stub
//		return arg0;
//	}
//
//	@Override
//	public View getView(final int intpot, View arg1, ViewGroup arg2) {
//		toView view=null;
//		if(view==null){
//
//			arg1=mInflater.inflate(R.layout.map_adapter_list, null);
//		}
//		view=	(toView) arg1.getTag();
//
//		if(view==null){
//
//			view =new toView();
//			view.textView=(TextView) arg1.findViewById(R.id.text_button_map);
//			view.Review=(TextView) arg1.findViewById(R.id.Review_get_to);
//			if(AppContext.THEME){
//				view.textView.setTextColor(Color.parseColor("#4390d4"));
//				view.Review.setTextColor(Color.parseColor("#2578f5"));
//			}else{
//				view.textView.setTextColor(Color.parseColor("#EB6024"));
//				view.Review.setTextColor(Color.parseColor("#50EB6024"));
//			}
//
//
//			arg1.setTag(view);
//		}
//		view.cate();
//		view.textView.setText(list.get(intpot).getTitle());
//		view.Review.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				Intent intent =new Intent(context, ToReview.class);
//				Bundle bundle=new Bundle();
//				AppContext.BundelPoiMess.poiItem=list.get(intpot);
//				context.startActivity(intent);
//			}
//		});
//		view.textView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				LatLonPoint lonPoint=list.get(intpot).getLatLonPoint();
//				LatLng latLng=new LatLng(lonPoint.getLatitude(), lonPoint.getLongitude());
//				mIWantToReview.toaddMarker(latLng);
//			}
//		});
//		return arg1;
//	}
//
private class toView{
		TextView textView;
		TextView Review;
		public  void cate(){
			textView.setText("");
		}

	}
	
	
}
