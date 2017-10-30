package com.heking.qsy.activity.ConvenienceService;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.navisdk.adapter.BNOuterLogUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;
import com.google.gson.Gson;
import com.heking.qsy.AppContext;
import com.heking.qsy.Model.Zhengfudaohang;
import com.heking.qsy.R;
import com.heking.qsy.activity.ConvenienceService.util.BNDemoGuideActivity;
import com.heking.qsy.activity.ConvenienceService.util.BNDemoMainActivity;
import com.heking.qsy.activity.ConvenienceService.util.BNGuideActivity;
import com.heking.qsy.activity.ConvenienceService.util.TB_NavigationGovernment;
import com.heking.qsy.activity.ConvenienceService.util.ZFDHAdapter2;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;

import android.Manifest;
import android.annotation.SuppressLint;

import com.heking.qsy.base.BaseActivity;
import com.heking.snoa.WPConfig;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import MyUtils.LogUtils.LogUtils;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZFDHActivity2 extends BaseActivity {
    private ListView mListView;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    private final static int authComRequestCode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zfdh_activity);
        iniView();
        iniData();
    }

    private void iniView() {
        Tool.endActivity(this);
        mListView = (ListView) findViewById(R.id.zfdh_list_view);
    }

    private void iniData() {

        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + "pzh/getNavigationGovernment")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.w("http", "onSubscribe");
                    }

                    @Override
                    public void onSuccess(String value) {
                        String Jsondata = "{\"JsonData\":" + value + "}";
                        TB_NavigationGovernment government = ParsonJson.jsonToBean(Jsondata, TB_NavigationGovernment.class);
                        if (government != null && government.getJsonData().size() > 0) {
                            ZFDHAdapter2 adapter2 = new ZFDHAdapter2(ZFDHActivity2.this, government.getJsonData());
                            mListView.setAdapter(adapter2);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.w("http", "onError:"+e.getMessage());
                    }
                });
    }


    @SuppressLint("NewApi")
    public void LocationSearch(@NonNull BNDemoMainActivity.Address addstr) {
        // 点击跳转  进入导航
        Intent intent = new Intent(ZFDHActivity2.this, BNDemoMainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ROUTE_PLAN_NODE, addstr);//  TB_NavigationGovernment.JsonData  的json数据  {"Address":"学府街83号","Delete":"false","ID":"2","Name":"犍为县食品药品监督管理局"}
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
