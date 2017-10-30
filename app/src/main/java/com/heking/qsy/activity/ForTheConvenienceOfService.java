package com.heking.qsy.activity;


import com.heking.qsy.R;
import com.heking.qsy.activity.ConvenienceService.BSZNActivity;
import com.heking.qsy.activity.ConvenienceService.MCLZActivity;
import com.heking.qsy.activity.ConvenienceService.SCXCActivity;
import com.heking.qsy.activity.ConvenienceService.SPLTActivity;
import com.heking.qsy.activity.ConvenienceService.SPSCActivity;
import com.heking.qsy.activity.ConvenienceService.YLJGActivity;
import com.heking.qsy.activity.ConvenienceService.YPLTActivity;
import com.heking.qsy.activity.ConvenienceService.ZFDHActivity2;
import com.heking.qsy.base.BaseFragment;
import com.heking.qsy.util.Tool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ForTheConvenienceOfService extends BaseFragment implements
        OnClickListener {
    private TextView mZFDHview, mBSZNview, mYPZWview, mSCXCview, mMCLZview,
            mYPLTview, mYLJGview, mSPSCview, mSPLTview;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iniView();
        iniData();
    }

    @Override
    public String getMyTag() {
        return "ForTheConvenienceOfService";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.for_the_convenience_of_service_activity, null);
    }

    private void toActivity(Class<?> cla) {
        Intent intent = new Intent(getActivity(), cla);
        startActivity(intent);
    }

    private void iniView() {
        Tool.endActivity(getActivity());
        mZFDHview = (TextView) getView().findViewById(R.id.zfdh_view);
        mBSZNview = (TextView) getView().findViewById(R.id.bszn_view);
        mYPZWview = (TextView) getView().findViewById(R.id.ypzw_view);
        mSCXCview = (TextView) getView().findViewById(R.id.scxc_view);
        mMCLZview = (TextView) getView().findViewById(R.id.mclz_view);
        mYPLTview = (TextView) getView().findViewById(R.id.yplt_view);// 药品流通
        mYLJGview = (TextView) getView().findViewById(R.id.yljg_view);// 医疗机构
        mSPSCview = (TextView) getView().findViewById(R.id.spsc_view);// 食品生产
        mSPLTview = (TextView) getView().findViewById(R.id.splt_view);// 食品流通
    }

    private void iniData() {
        mZFDHview.setOnClickListener(this);
        mBSZNview.setOnClickListener(this);
        mYPZWview.setOnClickListener(this);
        mSCXCview.setOnClickListener(this);
        mMCLZview.setOnClickListener(this);
        mYPLTview.setOnClickListener(this);
        mYLJGview.setOnClickListener(this);
        mSPSCview.setOnClickListener(this);
        mSPLTview.setOnClickListener(this);
    }

    private void toIntent(Class cla) {
        Intent intent = new Intent(getActivity(), cla);
        startActivity(intent);
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.zfdh_view:
                toIntent(ZFDHActivity2.class);
                break;
            case R.id.bszn_view:
                toIntent(BSZNActivity.class);
                break;
            case R.id.ypzw_view:
                toIntent(SweepTheYardActivity.class);
                break;
            case R.id.scxc_view:
                toIntent(SCXCActivity.class);
                break;
            case R.id.mclz_view:
                toIntent(MCLZActivity.class);
                break;
            case R.id.yplt_view:
                toIntent(YPLTActivity.class);
                break;
            case R.id.yljg_view:
                toIntent(YLJGActivity.class);
                break;
            case R.id.spsc_view:
                toIntent(SPSCActivity.class);
                break;
            case R.id.splt_view:
                try {
                    toIntent(SPLTActivity.class);
                } catch (Exception e) {
                }
                break;
        }
    }
}
