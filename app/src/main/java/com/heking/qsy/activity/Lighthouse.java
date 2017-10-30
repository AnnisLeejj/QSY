package com.heking.qsy.activity;

import java.io.File;
import java.util.ArrayList;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.OpengoVernment.OpenGoverBean;
import com.heking.qsy.activity.OpengoVernment.OpenGovernmentAdapter;
import com.heking.qsy.base.BaseActivity;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.DrugAdapter;
import com.heking.qsy.util.DrugBean;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.JsonFile;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Lighthouse extends BaseActivity {

    private ListView listView;
    private OpenGovernmentAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);

        }
        setContentView(R.layout.lighthouse_activity);
        findViewById(R.id.textview).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });

        iniView();
        iniData();
    }

    private void iniView() {
        listView = (ListView) findViewById(R.id.lighthouse_listView);
    }

    private void iniData() {
        // String gong_gao_gongshi=Tool.getUrLString("get_lighthouse", 1, this, true);

        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.ZWGK + "55555").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String json) {
                String JsonData = json;
                JsonFile jsonFile = new JsonFile();
                JsonData = "{\"state\":\"true\",\"MaxPage\":1,\"Data\":" + json + "}";
                File file;
                File file2 = null;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    file = Environment.getExternalStorageDirectory();
                    file2 = new File(file + "/PZhMessageList/");
                    if (!file2.exists()) {
                        file2.mkdirs();
                    }
                }
                File ss = new File(file2 + "/OpenGoverBean.dll");
                if (json.equals("连接失败")) {
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
                    showToast("网络异常");
                }
                OpenGoverBean bean = ParsonJson.jsonToBeanObj(JsonData, OpenGoverBean.class);

                AppContext.List.OpenGoverlist = bean.getData();
                ArrayList<OpenGoverBean.Data> datas = AppContext.List.OpenGoverlist;
                adapter = new OpenGovernmentAdapter(datas, Lighthouse.this, 88805);
                listView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
