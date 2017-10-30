package com.heking.qsy.util;

import java.util.ArrayList;
import java.util.LinkedList;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewAdapter extends PagerAdapter{
	private ArrayList<Integer> list;
	private LinkedList<View> mViews;
	private LayoutInflater mInflater;
	private Context context;
	private ViewPager mviewpager;
	
	public ViewAdapter(ArrayList<Integer> list, Context context,
			ViewPager mviewpager) {
		super();
		this.list = list;
		this.context = context;
		mInflater=LayoutInflater.from(context);
		this.mviewpager = mviewpager;
		if(list!=null){
			mViews=new LinkedList<View>();
			
		}
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		return super.instantiateItem(container, position);
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
