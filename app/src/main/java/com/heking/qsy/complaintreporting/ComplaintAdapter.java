package com.heking.qsy.complaintreporting;

import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.Dialog.CustomDialog;
import com.heking.qsy.activity.FirmShow.FirmType;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.Tool;

public class ComplaintAdapter extends BaseAdapter {
    ArrayList<ComplaintReportingBean.Data> list;
    private Context context;
    int ss;
    private LayoutInflater mInflater;
    private boolean boo;
    private FirmTypeBean firmbean;

    public ComplaintAdapter(Context context, ArrayList<ComplaintReportingBean.Data> ComplaintReportinglist,
                            int ss, boolean boo, FirmTypeBean firmbean) {
        this.list = ComplaintReportinglist;
        this.ss = ss;
        this.context = context;
        this.boo = boo;
        this.firmbean = firmbean;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return boo ? list != null ? list.size() > 4 ? Integer.MAX_VALUE : list
                .size() : 0 : Integer.MAX_VALUE;
        // return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0 % list.size());
        // return list.get(arg0)
    }

    @Override
    public long getItemId(int arg0) {
        return arg0 % list.size();
    }

    @Override
    public View getView(final int porINt, View arg1, ViewGroup arg2) {
        layout layou = null;
        if (layou == null) {
            layou = new layout();
            arg1 = mInflater.inflate(R.layout.the_theme, null);
            layou.mTitle = (TextView) arg1.findViewById(R.id.title);
            layou.mContext = (TextView) arg1.findViewById(R.id.theme_context);
            layou.mManage = (TextView) arg1
                    .findViewById(R.id.theme_manage_context);
            layou.mState = (TextView) arg1.findViewById(R.id.state_text);
            layou.layout = (LinearLayout) arg1.findViewById(R.id.button_onCk);
            if (AppContext.THEME) {
//				layou.mTitle.setTextColor(Color.parseColor("#026598"));
//				layou.mContext.setTextColor(Color.parseColor("#3ca7de"));
//				layou.mManage.setTextColor(Color.parseColor("#3ca7de"));
//				layou.mState.setTextColor(Color.parseColor("#026598"));
            } else {
                layou.mContext.setTextColor(Color.parseColor("#F1AD8F"));
                layou.mManage.setTextColor(Color.parseColor("#F1AD8F"));
                layou.mState.setTextColor(Color.parseColor("#EB6024"));
                layou.mTitle.setTextColor(Color.parseColor("#EB6024"));
            }
            arg1.setTag(layou);
        } else {
            layou = (layout) arg1.getTag();
        }
        layou.cose();
        switch (ss) {
            case 10:
                layou.mTitle.setText(list.get(porINt % list.size()).getDefendant());
                layou.mContext.setText(list.get(porINt % list.size()).getContent());
                layou.mManage.setText(list.get(porINt % list.size())
                        .getTheProcessingResults());
                break;
            case 11:
                layou.mTitle.setVisibility(TextView.GONE);
                layou.mContext.setText("XXX用户的投诉/举报被药监局核实采纳,奖励XXXXXX");
                    layou.mContext.setTextColor(Color.parseColor("#666666"));
//                if (list.get(porINt % list.size()).getComplainant() == null) {
//                    layou.mContext.setText("匿名的投诉或者举报被核实或者是采纳" + (TextUtils.isEmpty(list.get(porINt % list.size())
//                            .getTheComplainantReward()) ? "" : "," + list.get(porINt % list.size()).getTheComplainantReward()));
//                } else {
//                    layou.mContext.setText(list.get(porINt % list.size()).getComplainant()
//                            + "的投诉或者举报被核实或者是采纳" + (TextUtils.isEmpty(list.get(porINt % list.size())
//                            .getTheComplainantReward()) ? "" : "," + list.get(porINt % list.size()).getTheComplainantReward()));
//                    layou.mContext.setTextColor(Color.parseColor("#666666"));
//                }
                layou.mManage.setVisibility(TextView.GONE);
                layou.mState.setVisibility(TextView.GONE);
                break;
        }
        layou.mManage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, ComplaintActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("MessageData", list.get(porINt % list.size()));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }


        });
        layou.mContext.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, ComplaintActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("MessageData", list.get(porINt % list.size()));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }


        });
        layou.mTitle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {


                String FirmName = list.get(porINt % list.size()).getDefendant();
                ArrayList<FirmTypeBean.Data> firmlist = Tool.getData(firmbean
                        .getData());
                FirmTypeBean.Data firmdatas = null;
                for (FirmTypeBean.Data firmdata : firmlist) {
                    if (firmdata.getFirmName().trim().equals(FirmName)) {
                        Intent intent = new Intent();
                        intent.setClass(context, FirmType.class);
                        firmdatas = firmdata;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("FIRMTYPE", firmdata);
                        int State = 3333;
                        if (firmdata.getFirmTypeName().trim().contains("食品经营")
                                || firmdata.getFirmTypeName().trim()
                                .contains("餐饮经营")
                                || firmdata.getFirmTypeName().trim()
                                .contains("食品生产")) {

                            State = 2222;

                        }
                        bundle.putInt("State", State);
                        intent.putExtras(bundle);
                        context.startActivity(intent);

                    }

                }
                if (firmdatas == null) {

                    CustomDialog.Builder builder = new CustomDialog.Builder(context);
                    builder.setMessage("\t\t当前企业无数据。");
                    builder.setTitle("温馨提示");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //设置你的操作事项
                        }
                    });
                    CustomDialog dailog = builder.create();
                    dailog.setCanceledOnTouchOutside(false);
                    dailog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                                return true;
                            } else {
                                return true; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
                            }
                        }
                    });
                    dailog.show();

                }

            }


        });
        return arg1;
    }

    private class layout {
        public TextView mTitle, mContext, mManage, mState;
        public LinearLayout layout;

        public void cose() {
            mTitle.setText("");
            mContext.setText("");
            mManage.setText("");
        }

    }

}