package com.heking.qsy;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.heking.qsy.R;
import com.heking.qsy.providers.ConnectionUtility;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.MessageSearch;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.SaoYiSaoXmlBean;
import com.heking.qsy.util.getXml;
import com.heking.snoa.WPConfig;
import com.zbar.lib.CaptureActivity;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SweepTheYardQuery extends Activity {
    private Bundle bundle;
    private TextView mSaoyisao;
    private TextView mWarning, mProductName, mProductManufacturerName,
            mApprovalWenHao, mProductionDate, mProductionPiCi, mYouXiaoDate,
            mBaoZhuanGuiGe, mJiXing, mZhijiguige;
    ArrayList<MessageSearch> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        HttpHelper.getInstance().service.get(WPConfig.SAO_YI_SAO_YAO_PING + bundle.getString("message")).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String json) {
                Log.d("json", json);
                if (json.equals("连接失败")) {
                    Toast.makeText(SweepTheYardQuery.this, json, 500).show();
                    finish();
                    return;
                }
                SaoYiSaoXmlBean bean = ParsonJson.jsonToBeanObj(json,
                        SaoYiSaoXmlBean.class);
                list = getXml.parseExternXML(bean.getData());
                setViewText();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
        setContentView(R.layout.sweep_the_yard_query_activity);
        iniView();
        iniData();
    }

    private void iniView() {
        mSaoyisao = (TextView) findViewById(R.id.textview_sao_yi_sao);
        findViewById(R.id.textview).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });
        mWarning = (TextView) findViewById(R.id.jing_gao_xin_xi);
        mProductName = (TextView) findViewById(R.id.yao_ping_name);
        mProductManufacturerName = (TextView) findViewById(R.id.yao_ping_chan_jia);
        mApprovalWenHao = (TextView) findViewById(R.id.approvalwenHao);
        mProductionDate = (TextView) findViewById(R.id.productiondate);
        mProductionPiCi = (TextView) findViewById(R.id.productionpici);
        mYouXiaoDate = (TextView) findViewById(R.id.youxiaodate);
        mBaoZhuanGuiGe = (TextView) findViewById(R.id.baozhuanguige);
        mJiXing = (TextView) findViewById(R.id.jixing);
        mZhijiguige = (TextView) findViewById(R.id.zijiguige);
    }

    private void iniData() {
        mSaoyisao.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(SweepTheYardQuery.this,
                        CaptureActivity.class);
                startActivity(intent);

            }
        });

    }

    // setViewText();

    private void setViewText() {
        String messagesQuerycounstring_01 = "　该监管码已被多次查询，请注意检查药品包装是否与信息符合，首次查询时间是：";
        for (MessageSearch MessageSearch : list) {
            if (MessageSearch.getKey().equals("queryCount")) {
                if (new Integer(MessageSearch.getValue()) < 20) {
                    messagesQuerycounstring_01 = "该监管码已被查询"
                            + MessageSearch.getValue()
                            + "次，请注意检查药品包装是否与信息符合，首次查询时间是：";
                }
            }
            if (MessageSearch.getKey().equals("firstQuery")) {
                mWarning.setText(messagesQuerycounstring_01.toString()
                        + MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("产品名称")) {
                mProductName.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("生产企业")) {
                mProductManufacturerName.setText(MessageSearch.getValue()
                        .toString());
            }
            if (MessageSearch.getKey().equals("批准文号")) {
                mApprovalWenHao.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("生产日期")) {
                mProductionDate.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("生产批次")) {
                mProductionPiCi.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("有效期至")) {
                mYouXiaoDate.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("包装规格")) {
                mProductionDate.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("生产日期")) {
                mBaoZhuanGuiGe.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("剂型代码")) {
                mJiXing.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("制剂规格")) {
                mZhijiguige.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("制剂规格")) {
                mZhijiguige.setText(MessageSearch.getValue().toString());
            }
        }

    }

}
