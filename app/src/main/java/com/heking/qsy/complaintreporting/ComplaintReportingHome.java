package com.heking.qsy.complaintreporting;

import java.io.File;
import java.util.Timer;

import com.heking.qsy.AppContext;

import com.heking.qsy.R;

import com.heking.qsy.activity.ComplaintReportings;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.JsonFile;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import MyUtils.LogUtils.LogUtils;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("NewApi")
public class ComplaintReportingHome extends Activity     {
    private TextView mTextButtonOk;
    private ListView mBoardlist, mRewardBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.complaint_reporting_home);
        iniView();
        iniData();
    }

    private void iniView() {
        Tool.endActivity(this);
        mTextButtonOk = (TextView) findViewById(R.id.ok_button_ima);
        mBoardlist = (ListView) findViewById(R.id.Boardlist);
        mRewardBar = (ListView) findViewById(R.id.RewardBar);
    }

    private void iniData() {

//		String url=Tool.getUrLString("get_ComplaintInformation", 1, this, true);
        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.GET_COMPLAINT_SUGGESTION + AppContext.Parameter.ALL).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String value) {
                String JsonData = value;
                JsonData = "{\"state\":\"true\",\"Data\":" + value + "}";
                JsonFile jsonFile = new JsonFile();
                File file;
                File file2 = null;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    file = Environment.getExternalStorageDirectory();
                    file2 = new File(file + "/PZhMessageList/");
                    if (!file2.exists()) {
                        file2.mkdirs();
                    }
                }
                File ss = new File(file2 + "/ComplaintReportingBean.dll");
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
                    LogUtils.w("data", "json:" + JsonData);
                    bean = ParsonJson.jsonToBeanObj(JsonData, ComplaintReportingBean.class);
                    boolean bool = bean == null ? false : bean.getData() == null ? false : bean.getData().size() > 1 ? true : false;
                    if (bool) {
                        //	AppContext.List.ComplaintReportinglist=bean.getData();
//		mBoardlist.setAdapter(new ComplaintAdapter(this, bean.getData(), 10, true));
//		mRewardBar.setAdapter(new ComplaintAdapter(this, bean.getData(), 11, false));
                        timer = new Timer();
                        timer2 = new Timer();
                        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET
                                + AppContext.Parameter.GET_FIRM_TYPE
                                + AppContext.Parameter.ALL).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(String json) {
                                String JsonData = json;
                                JsonData = "{\"Data\":" + json + "}";
                                JsonFile jsonFile = new JsonFile();
                                File file;
                                File file2 = null;
                                if (Environment.getExternalStorageState().equals(
                                        Environment.MEDIA_MOUNTED)) {
                                    file = Environment.getExternalStorageDirectory();
                                    file2 = new File(file + "/PZhMessageList/");
                                    if (!file2.exists()) {
                                        file2.mkdirs();
                                    }
                                }
                                File ss = new File(file2 + "/FirmTypeBean.dll");

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
                                if (JsonData.equals("连接失败")) {
                                    return;
                                }
                                FirmTypeBean firmbean = ParsonJson.jsonToBeanObj(JsonData, FirmTypeBean.class);
                                if (firmbean != null && firmbean.getData() != null) {
                                    timer.schedule(new TimeTaskScroll(mBoardlist, ComplaintReportingHome.this, bean.getData(), 10, true, firmbean), 20, 20);
                                    timer2.schedule(new TimeTaskScroll(mRewardBar, ComplaintReportingHome.this, bean.getData(), 11, false, firmbean), 20, 20);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                    }
                }
                if (JsonData == null) {
                    Toast.makeText(ComplaintReportingHome.this, "网络异常", Toast.LENGTH_SHORT).show();
                }
                Log.d("测试数据", value);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
        mTextButtonOk.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
//				Intent intent=new Intent(ComplaintReportingHome.this, ComplaintReporting.class);
//				startActivity(intent);
                Intent intent = new Intent(ComplaintReportingHome.this, ComplaintReportings.class);
                startActivity(intent);
            }
        });


//		TimerTask		task=new TimerTask() {
//			
//			@Override
//			public void run() {
//				
//				handler.post(runnable);
//				
//			}
//		};
//		timer=new Timer();
//		timer.schedule(task,0,1000);
    }

    private Timer timer;
    private Timer timer2;
    private ComplaintReportingBean bean;


}
