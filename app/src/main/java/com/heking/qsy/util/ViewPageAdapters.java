package com.heking.qsy.util;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewPageAdapters extends PagerAdapter {
  private ArrayList<Integer> list;
  private Context context;
	public ViewPageAdapters(ArrayList<Integer> list,Context context){
		this.list=list;
		this.context=context;
	} 

   

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @SuppressLint("NewApi")
	public Object instantiateItem(android.view.ViewGroup container, int position) {
        TextView item = new TextView(context);
//        View view=list.get(position);
        item.setBackground(context.getResources().getDrawable(list.get(position%list.size())));
        container.addView( item);
        return item;
    }

    public void destroyItem(android.view.ViewGroup container, int position, Object object) {
        container.removeView( (View)object);
    }
}
