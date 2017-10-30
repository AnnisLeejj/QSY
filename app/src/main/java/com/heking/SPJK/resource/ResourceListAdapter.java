package com.heking.SPJK.resource;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.hikvision.vmsnetsdk.CameraInfo;
import com.hikvision.vmsnetsdk.ControlUnitInfo;
import com.hikvision.vmsnetsdk.RegionInfo;

import MyUtils.LogUtils.LogUtils;

public class ResourceListAdapter extends BaseAdapter {

    private Context mContext = null;
    public static ArrayList mList = null;//支撑播放的数据
    private LayoutInflater mListContainer = null;


    public ResourceListAdapter(Context context, ArrayList list) {
        mContext = context;
        mList = list;
        // 创建视图容器并设置上下文
        mListContainer = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mList == null) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            // 获取list_item布局文件的视图
            convertView = mListContainer.inflate(R.layout.resource_item_layout_hkws, null);
            // 获取控件对象
            viewHolder.tv = (TextView) convertView.findViewById(R.id.item_txt);
            if (AppContext.THEME) {
                viewHolder.tv.setTextColor(Color.parseColor("#333333"));
            } else {
                viewHolder.tv.setTextColor(Color.parseColor("#EB6024"));
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //
        Object itemData = getItem(position);
        String itemName = getItemName(itemData);
        viewHolder.tv.setText(itemName);

        return convertView;
    }

    //获得资源列表名称
    private String getItemName(Object itemData) {
        if (itemData instanceof ControlUnitInfo) {
            ControlUnitInfo info = (ControlUnitInfo) itemData;
            return info.getName();
        }

        if (itemData instanceof RegionInfo) {
            RegionInfo info = (RegionInfo) itemData;
            return info.getName();
        }

        if (itemData instanceof CameraInfo) {
            CameraInfo info = (CameraInfo) itemData;
            return info.getName();
        }

        return null;
    }

    public void setData(ArrayList data) {
        this.mList = data;
        LogUtils.w("shipin_adpter",  new Gson().toJson(data));
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView tv = null;
    }

}
