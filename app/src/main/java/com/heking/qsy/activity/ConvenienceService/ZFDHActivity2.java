package com.heking.qsy.activity.ConvenienceService;

import com.heking.qsy.R;
import com.heking.qsy.activity.ConvenienceService.util.BNDemoMainActivity;
import com.heking.qsy.activity.ConvenienceService.util.TB_NavigationGovernment;
import com.heking.qsy.activity.ConvenienceService.util.ZFDHAdapter2;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;

import android.annotation.SuppressLint;

import com.heking.qsy.base.BaseActivity;
import com.heking.snoa.WPConfig;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ListView;
import MyUtils.LogUtils.LogUtils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
