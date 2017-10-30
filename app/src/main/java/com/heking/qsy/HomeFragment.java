package com.heking.qsy;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.heking.qsy.activity.FoodCompanies;
import com.heking.qsy.activity.IWantToReview_baidu;
import com.heking.qsy.activity.Lighthouse;
import com.heking.qsy.activity.OpenGovernment;
import com.heking.qsy.activity.PharmaceuticalCompanies;
import com.heking.qsy.base.BaseFragment;
import com.heking.qsy.complaintreporting.ComplaintReportingHome;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.CycleViewPager;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.JsonFile;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.qsy.util.ViewPageAdapters;
import com.heking.qsy.util.WriteToSD;
import com.heking.snoa.WPConfig;
import com.zbar.lib.CaptureActivity;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment
        implements OnPageChangeListener, OnClickListener {

    private static String TAG = "LEFTTORIGHT";
    private static String LEFT = "LEFTTORIGHT";
    // private Handler handler;
    private int which = 0;
    private final static int BAIDU_READ_PHONE_STATE = 101;
    private Tool tool;

    // 公告显示栏-----》轮播图
    private CycleViewPager mViewPager;
    // 公告显示栏-----》控制点
    private LinearLayout mlayout;
    private ViewPageAdapters adapter;
    private ArrayList<Integer> list = new ArrayList<Integer>();
    private LinearLayout mSaoMa;
    private LinearLayout mComplaintReporting, mFoodCompanies, mPharmaceuticalCompanies, mIWantToReview;
    private RelativeLayout mOpenGovernment, mLighthouse;
    private String[] fileName = {"88801OpenGoverBean.dll",
            "88802OpenGoverBean.dll", "88803OpenGoverBean.dll",
            "88804OpenGoverBean.dll", "BSZNAdata.dll",
            "ComplaintReportingBean.dll", "FirmTypeBean.dll",
            "OpenGoverBean.dll", "Z10930004.dll"};
    Timer timer;
    MyTask task;
//    private AMapLocationClient mlocationClient;
//    private AMapLocationClientOption mLocationOption = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getMyTag() {
        return "HomeFragment";
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        fileData();
        iniView();
        iniData();
    }

    class MyTask extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                // UI thread
                @Override
                public void run() {
                    mViewPager.setCurrentItem(which % 5);
                    which++;
                }

            });
        }
    }

    private void fileData() {
        for (String name : fileName) {
            WriteToSD.write(getActivity(), name);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.activity_home, null);
    }

    @Override
    public void onResume() {
        super.onResume();

        // setContentView(R.layout.activity_home);
        // fileData();
        // iniView();
        // iniData();
        // login();
        timer = new Timer();
        task = new MyTask();
        timer.schedule(task, 3000, 3000);

    }

    @Override
    public void onPause() {
        super.onPause();

        timer.cancel();
        task.cancel();
        timer.purge();
        timer = null;
        task = null;
    }

    private void iniView() {
//
        mViewPager = (CycleViewPager) getView().findViewById(R.id.show_view_pager);
        mlayout = (LinearLayout) getView().findViewById(R.id.show_linear_layout);
        mSaoMa = (LinearLayout) getView().findViewById(R.id.sousuo_text_button);

        mComplaintReporting = (LinearLayout) getView().findViewById(R.id.mComplaintReporting);
        mOpenGovernment = (RelativeLayout) getView().findViewById(R.id.mOpenGovernment);
        mLighthouse = (RelativeLayout) getView().findViewById(R.id.mLighthouse);
        mFoodCompanies = (LinearLayout) getView().findViewById(R.id.mFoodCompanies);
        mPharmaceuticalCompanies = (LinearLayout) getView().findViewById(R.id.mPharmaceuticalCompanies);
        mIWantToReview = (LinearLayout) getView().findViewById(R.id.mIWantToReview);
    }

    private void iniData() {
        // String message=Tool.getUrLString("get_Message_Firm", 1, this, true);
        if (AppContext.List.FirmTapeDataList == null || AppContext.List.FirmTapeDataList.size() <= 0) {

            HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.GET_FIRM_TYPE + AppContext.Parameter.ALL)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
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
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
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
                        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    FirmTypeBean bean = ParsonJson.jsonToBeanObj(JsonData, FirmTypeBean.class);
                    AppContext.List.FirmTapeDataList = bean != null ? bean.getData() : null;
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }

        if (list.size() > 0) {
            list.clear();
        }
        list.add(R.drawable.image_1);
        list.add(R.drawable.image_2);
        list.add(R.drawable.image_3);
        list.add(R.drawable.image_4);
        list.add(R.drawable.image_5);
        // AppContext.ONE = false;
        // }
        mSaoMa.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivity(intent);
            }
        });
        adapter = new ViewPageAdapters(list, getActivity());
        mViewPager.setAdapter(adapter);

        tool = new Tool(getActivity(), mlayout, list.size());
        tool.drawsImg();

        which = 0;

        mViewPager.setOnPageChangeListener(this);

        mComplaintReporting.setOnClickListener(this);
        mOpenGovernment.setOnClickListener(this);
        mLighthouse.setOnClickListener(this);
        mFoodCompanies.setOnClickListener(this);
        mPharmaceuticalCompanies.setOnClickListener(this);
        mIWantToReview.setOnClickListener(this);
    }

    public void onPageScrollStateChanged(int arg0) {
    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    public void onPageSelected(int arg0) {
        Log.d("测试数据:", "------------->" + arg0);
        tool.setItem(arg0);
        which = arg0 + 1;

    }

    public void onClick(View arg0) {

        switch (arg0.getId()) {
            case R.id.mComplaintReporting:
                toActivity(ComplaintReportingHome.class);
                break;
            case R.id.mOpenGovernment:
                toActivity(OpenGovernment.class);
                break;
            case R.id.mLighthouse:
                toActivity(Lighthouse.class);
                break;
            case R.id.mFoodCompanies:
                toActivity(FoodCompanies.class);
                break;
            case R.id.mPharmaceuticalCompanies:
                toActivity(PharmaceuticalCompanies.class);
                break;
            case R.id.mIWantToReview:
//                // toActivity(IWantToReview.class);
                toActivity(IWantToReview_baidu.class);
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        AppContext.ONE = true;
    }

    private void toActivity(Class<?> cla) {
        Intent intent = new Intent(getActivity(), cla);
        startActivity(intent);
    }

    private Map<String, String> login = null;

    @Override
    public void onDestroy() {
        // hander.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
