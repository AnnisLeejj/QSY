package com.heking.qsy.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.OpengoVernment.OpenGoverBean;
import com.heking.qsy.activity.OpengoVernment.OpenGovernmentAdapter;
import com.heking.qsy.base.BaseActivity;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.JsonFile;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OpenGovernment extends BaseActivity implements OnClickListener {
    private ListView mListView;
    private RadioButton TheAnnouncementOfThePublic, RegulationOfTheDynamic, LawsAndRegulations, ThePopularScienceGarden;

    private static final int STATE_MESS_0 = 88801;
    private static final int STATE_MESS_1 = 88802;
    private static final int STATE_MESS_2 = 88803;
    private static final int STATE_MESS_3 = 88804;

    private int intstat;
    private OpenGovernmentAdapter adapter;

    private View mView;
    private TextView mToulistView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.open_overnment_activity);
        iniView();
        iniData();
    }

    private void iniView() {
        mListView = (ListView) findViewById(R.id.open_listView);
        mView = LayoutInflater.from(this).inflate(R.layout.itme_list_view, null);
        mToulistView = (TextView) mView.findViewById(R.id.sssss_tttt_qqqq);
        mListView.addHeaderView(mView);//添加头部
        TheAnnouncementOfThePublic = (RadioButton) findViewById(R.id.gong_gao_gongshi);
        RegulationOfTheDynamic = (RadioButton) findViewById(R.id.jian_guan_dong_tai);
        LawsAndRegulations = (RadioButton) findViewById(R.id.falv_fagui);
        ThePopularScienceGarden = (RadioButton) findViewById(R.id.kepu_yuandi);
        findViewById(R.id.textview).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });

    }

    private void iniData() {
        TheAnnouncementOfThePublic.setOnClickListener(this);
        RegulationOfTheDynamic.setOnClickListener(this);
        LawsAndRegulations.setOnClickListener(this);
        ThePopularScienceGarden.setOnClickListener(this);
        intstat = STATE_MESS_0;
        //	String gong_gao_gongshi=Tool.getUrLString("get_Notification", 1, this, true);
        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.ZWGK + "11111" + AppContext.Parameter.ALL).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(singleObserver);
    }

    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.gong_gao_gongshi:
                intstat = STATE_MESS_0;
                mToulistView.setBackgroundDrawable(getResources().getDrawable(R.drawable.gognshigonggao));
                TheAnnouncementOfThePublic.setTextColor(Color.parseColor("#3cafdf"));
                RegulationOfTheDynamic.setTextColor(Color.parseColor("#808080"));
                LawsAndRegulations.setTextColor(Color.parseColor("#808080"));
                ThePopularScienceGarden.setTextColor(Color.parseColor("#808080"));
//			mListView.addHeaderView(mView);
//			String gong_gao_gongshi=Tool.getUrLString("get_Notification", 1, this, true);
                HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.ZWGK + "11111" + AppContext.Parameter.ALL).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(singleObserver);
                break;
            case R.id.jian_guan_dong_tai:
                intstat = STATE_MESS_1;
                mToulistView.setBackgroundDrawable(getResources().getDrawable(R.drawable.jianguandongtai));
                TheAnnouncementOfThePublic.setTextColor(Color.parseColor("#808080"));
                RegulationOfTheDynamic.setTextColor(Color.parseColor("#3cafdf"));
                LawsAndRegulations.setTextColor(Color.parseColor("#808080"));
                ThePopularScienceGarden.setTextColor(Color.parseColor("#808080"));
//			mListView.addHeaderView(mView);
//			String jian_guan_dong_tai=Tool.getUrLString("get_The_Public_Announcement", 1, this, true);
                HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.ZWGK + "11111" + AppContext.Parameter.ALL).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(singleObserver);
                break;
            case R.id.falv_fagui:
                intstat = STATE_MESS_2;
                mToulistView.setBackgroundDrawable(getResources().getDrawable(R.drawable.falvfagui));
                TheAnnouncementOfThePublic.setTextColor(Color.parseColor("#808080"));
                RegulationOfTheDynamic.setTextColor(Color.parseColor("#808080"));
                LawsAndRegulations.setTextColor(Color.parseColor("#3cafdf"));
                ThePopularScienceGarden.setTextColor(Color.parseColor("#808080"));
//			mListView.addHeaderView(mView);
//			String falv_fagui=Tool.getUrLString("get_Laws_and_regulations", 1, this, true);
                HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.ZWGK + "11111" + AppContext.Parameter.ALL).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(singleObserver);
                break;
            case R.id.kepu_yuandi:
                intstat = STATE_MESS_3;
                mToulistView.setBackgroundDrawable(getResources().getDrawable(R.drawable.kepuyuandi));
                TheAnnouncementOfThePublic.setTextColor(Color.parseColor("#808080"));
                RegulationOfTheDynamic.setTextColor(Color.parseColor("#808080"));
                LawsAndRegulations.setTextColor(Color.parseColor("#808080"));
                ThePopularScienceGarden.setTextColor(Color.parseColor("#3cafdf"));
//			mListView.addHeaderView(mView);
//			String kepu_yuandi=Tool.getUrLString("get_The_Popular_Science_Garden", 1, this, true);
                HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.ZWGK + "11111" + AppContext.Parameter.ALL).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(singleObserver);
                break;
        }
    }

    SingleObserver singleObserver = new SingleObserver<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(String json) {
            String JsonData = json;
            JsonData = "{\"state\":\"true\",\"MaxPage\":1,\"Data\":" + json + "}";
            //	JsonData="连接失败";
            JsonFile jsonFile = new JsonFile();
            File file;
            File file2 = null;
            File ss = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                file = Environment.getExternalStorageDirectory();
                file2 = new File(file + "/PZhMessageList/");
                if (!file2.exists()) {
                    file2.mkdirs();
                }
            }
            switch (intstat) {
                case STATE_MESS_0:
                    ss = new File(file2 + "/" + STATE_MESS_0 + "OpenGoverBean.dll");
                    break;
                case STATE_MESS_1:
                    ss = new File(file2 + "/" + STATE_MESS_1 + "OpenGoverBean.dll");
                    break;
                case STATE_MESS_2:
                    ss = new File(file2 + "/" + STATE_MESS_2 + "OpenGoverBean.dll");
                    break;
                case STATE_MESS_3:
                    ss = new File(file2 + "/" + STATE_MESS_3 + "OpenGoverBean.dll");
                    break;
            }

            if (JsonData.equals("连接失败")) {
                try {
                    JsonData = new String(jsonFile.getFlieJson(ss));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                try {
                    jsonFile.FileJson(JsonData, ss);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (TextUtils.isEmpty(JsonData)) {
                Looper.prepare();
                showToast("网络异常");
                Looper.loop();
            }

            OpenGoverBean bean = ParsonJson.jsonToBeanObj(JsonData, OpenGoverBean.class);
            ArrayList<OpenGoverBean.Data> datas = null;
            if (bean != null && bean.getData() != null) {
                AppContext.List.OpenGoverlist = bean.getData();
                datas = AppContext.List.OpenGoverlist;
            }//TID:100 bug:  1227 集合排序
            Collections.sort(datas, new Comparator<OpenGoverBean.Data>() {
                @Override
                public int compare(OpenGoverBean.Data o1, OpenGoverBean.Data o2) {
                    return o2.getPublishTime().compareTo(o1.getPublishTime());
                }
            });
            switch (intstat) {
                case STATE_MESS_0:
                    adapter = new OpenGovernmentAdapter(datas, OpenGovernment.this, STATE_MESS_0);
                    mListView.setAdapter(adapter);
                    break;
                case STATE_MESS_1:
                    adapter = new OpenGovernmentAdapter(datas, OpenGovernment.this, STATE_MESS_1);
                    mListView.setAdapter(adapter);
                    break;
                case STATE_MESS_2:
                    adapter = new OpenGovernmentAdapter(datas, OpenGovernment.this, STATE_MESS_2);
                    mListView.setAdapter(adapter);
                    break;
                case STATE_MESS_3:
                    adapter = new OpenGovernmentAdapter(datas, OpenGovernment.this, STATE_MESS_3);
                    mListView.setAdapter(adapter);
                    break;
            }

        }

        @Override
        public void onError(Throwable e) {
        }
    };

}
