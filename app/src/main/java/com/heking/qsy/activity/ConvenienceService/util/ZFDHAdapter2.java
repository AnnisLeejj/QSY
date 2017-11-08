package com.heking.qsy.activity.ConvenienceService.util;

import java.util.ArrayList;

import com.heking.qsy.R;
import com.heking.qsy.activity.ConvenienceService.ZFDHActivity2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ZFDHAdapter2 extends BaseAdapter {
    private ArrayList<TB_NavigationGovernment.JsonData> List;
    private LayoutInflater mInflater;
    private ZFDHActivity2 zfdhActivity2;

    public ZFDHAdapter2(Context context, ArrayList<TB_NavigationGovernment.JsonData> List) {
        this.List = List;
        this.mInflater = LayoutInflater.from(context);
        this.zfdhActivity2 = (ZFDHActivity2) context;
    }

    @Override
    public int getCount() {
        return List != null ? List.size() : 0;
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
        toView view = null;
        if (view == null) {
            arg1 = mInflater.inflate(R.layout.zfdha_list_view_activity, null);
        }
        view = (toView) arg1.getTag();
        if (view == null) {
            view = new toView();
            view.mName = (TextView) arg1.findViewById(R.id.text_view_name);
            view.mCoall = (TextView) arg1.findViewById(R.id.coall_view);
            view.mAddss = (TextView) arg1.findViewById(R.id.addss_view);
            view.mDaoHan = (TextView) arg1.findViewById(R.id.dh_textview);
            arg1.setTag(view);
        }
        view.cate();

        String addss = List.get(poiress).getPhone();
        if (addss == null || addss.trim().equals("")) {
            addss = "暂无电话";

        }
        view.mName.setText(List.get(poiress).getName());
        view.mCoall.setText("电话：" + addss);
        view.mAddss.setText("地址：" + List.get(poiress).getAddress());

        view.mDaoHan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                zfdhActivity2.LocationSearch(new BNDemoMainActivity.Address(List.get(poiress).longitude, List.get(poiress).latitude, List.get(poiress).city, List.get(poiress).getAddress()));
            }
        });
        return arg1;
    }

    private class toView {
        TextView mName;
        TextView mCoall;
        TextView mAddss;
        TextView mDaoHan;

        public void cate() {
            mName.setText("");
            mCoall.setText("");
            mAddss.setText("");
        }
    }

}
