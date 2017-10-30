package com.heking.qsy.activity.Patrol;

import com.heking.qsy.R;
import com.heking.qsy.base.BaseFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PatrolFragment extends BaseFragment implements OnClickListener {

    @Override
    public String getMyTag() {
        return "PatrolFragment";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_patrol, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView btnXunjian = (ImageView) getView().findViewById(R.id.ivXunjian);
        btnXunjian.setOnClickListener(this);
        ImageView btnBaBayan = (ImageView) getView().findViewById(R.id.ivBaBayan);
        btnBaBayan.setOnClickListener(this);
        ImageView btnSixiao = (ImageView) getView().findViewById(R.id.ivSixiao);
        btnSixiao.setOnClickListener(this);
        ImageView ivTz = (ImageView) getView().findViewById(R.id.ivTz);
        ivTz.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.ivXunjian://巡检
                startActivity(new Intent(getActivity(), PatrolActivity.class));
                break;
            case R.id.ivBaBayan:
                startActivity(new Intent(getActivity(), BaBaYanActivity.class));
                break;
            case R.id.ivSixiao:
                startActivity(new Intent(getActivity(), SixiaoActivity.class));
                break;
            case R.id.ivTz:
                startActivity(new Intent(getActivity(), TongzhiActivity.class));
                break;
            default:
                break;
        }
    }
}
