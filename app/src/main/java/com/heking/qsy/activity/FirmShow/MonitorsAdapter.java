package com.heking.qsy.activity.FirmShow;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.heking.qsy.R;
import com.hikvision.vmsnetsdk.CameraInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.LogWriter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MonitorsAdapter extends BaseAdapter {

    private ArrayList<CameraInfo> list;
    private Context context;
    private LayoutInflater mInflater;
    boolean openStatus;

    /**
     * @param list
     * @param context
     * @param openStatus 是否显示状态
     */
    public MonitorsAdapter(ArrayList<CameraInfo> list, Context context, boolean openStatus) {
        this.list = list;
        this.context = context;
        this.openStatus = openStatus;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return list != null ? list.size() : 0;
    }

    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    public long getItemId(int arg0) {
        return arg0;
    }

    @SuppressWarnings("deprecation")

    public View getView(final int proenat, View view, ViewGroup arg2) {
        ViewHodler layout = null;
        if (layout == null) {
            view = mInflater.inflate(R.layout.list_view_firm, null);
        }
        layout = (ViewHodler) view.getTag();
        if (layout == null) {
            layout = new ViewHodler();
            layout.textView = (TextView) view.findViewById(R.id.file_list_textview);
            layout.mSP = (TextView) view.findViewById(R.id.img_sp);
            layout.mStatu = (TextView) view.findViewById(R.id.img_pingji);
            layout.mLayout = (RelativeLayout) view;
            view.setTag(layout);
        }
        layout.cose();
        layout.mSP.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.youbian));
        layout.mSP.setHeight(30);
        layout.mSP.setWidth(30);
        if (list.get(proenat) == null || TextUtils.isEmpty(list.get(proenat).getName())) {
            layout.textView.setText("-");
        } else {
            layout.textView.setText(list.get(proenat).getName());
        }
        if (openStatus) {//需要才显示
            layout.mStatu.setVisibility(View.VISIBLE);
            layout.mStatu.setBackgroundResource(list.get(proenat).isOnline() ? R.drawable.camera_online : R.drawable.camera_unline);
        }
//        layout.mLayout.setOnClickListener(new OnClickListener() {
//
//            public void onClick(View arg0) {
//                LogUtils.w("shipin", "视频信息:" + new Gson().toJson(list.get(proenat)));
//                Intent intent = new Intent(context, VideoPlayback.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("MONITORS", list.get(proenat));
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//            }
//        });
        return view;
    }

    private class ViewHodler {
        private TextView textView;
        private TextView mSP, mStatu;
        private RelativeLayout mLayout;

        private void cose() {
            textView.setText("");
        }
    }
}
