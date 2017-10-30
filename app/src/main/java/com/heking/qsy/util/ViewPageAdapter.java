package com.heking.qsy.util;

import java.util.ArrayList;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewPageAdapter extends PagerAdapter {
  private ArrayList<View> list;
	public ViewPageAdapter(ArrayList<View> list){
		this.list=list;
	} 
	@Override
	public int getCount() {
		return list==null?0:list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		
		return arg0 == (View)arg1;
	}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			
			container.removeView((View)object);
		}
		@Override
			public Object instantiateItem(ViewGroup container, int position) {
			View v = list.get(position); 
			// 判断 view的父容器是不是为NULL 如果不为空就要先移除后添加；
			if (v.getParent()!=null) {
				((ViewGroup) v.getParent()).removeAllViews();
			}
			container.addView(v);
			return v;
			}
		
		
			
			
}
