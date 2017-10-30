package com.heking.qsy.activity.ConvenienceService;

import java.io.File;
import java.util.ArrayList;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.OpengoVernment.BanShiZhiNanAdapter;
import com.heking.qsy.activity.OpengoVernment.EnterpriseClassAdapter;
import com.heking.qsy.activity.OpengoVernment.OpenGoverBean;
import com.heking.qsy.activity.OpengoVernment.OpenGoverBean.Data;
import com.heking.qsy.activity.review.SwipeRefreshView;
import com.heking.qsy.base.BaseActivity;
import com.heking.qsy.base.BaseActivity;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.JsonFile;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BSZNActivity extends BaseActivity implements OnClickListener {
    private ListView mListView;
    GridView gvClass;
    EnterpriseClassAdapter enterpriseClassAdapter;
    SwipeRefreshView srl;
    int page = 1;
    int type = 1;
    private ArrayList<Data> data;
    BanShiZhiNanAdapter adapter;
    int loadType = 2;
    String TAG = "BSZNActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.bszn_activity);
        iniView();
        iniData();
    }

    private void iniView() {
        Tool.endActivity(this);
        mListView = (ListView) findViewById(R.id.bszn_list_view);
        gvClass = (GridView) findViewById(R.id.gv_Class);
        srl = (SwipeRefreshView) findViewById(R.id.srl);
        srl.setColorScheme(R.color.tou_su_button);

        // String url=Tool.getUrLString("get_Guide_to", 1, this, true);
        enterpriseClassAdapter = new EnterpriseClassAdapter(this);
        gvClass.setAdapter(enterpriseClassAdapter);
        gvClass.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                enterpriseClassAdapter.setSeletor(position);

                HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.ZWGK + "66666" + (position + 1) + "?page=1&rows=30")
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        httpResponse(json);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
                page = 1;
                type = position + 1;
                loadType = 2;
            }
        });

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.ZWGK + "66666" + type + "?page=1&rows=30")
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        httpResponse(json);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
                page = 1;
                loadType = 0;
                srl.setRefreshing(false);
            }
        });

        // 设置下拉加载更多
        srl.setOnLoadListener(new SwipeRefreshView.OnLoadListener() {
            @Override
            public void onLoad() {
                // 加载完数据设置为不加载状态，将加载进度收起来
                HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.ZWGK + "66666" + type + "?page=" + page + "&rows=30")
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        httpResponse(json);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
                Log.i("BSZNActivity", WPConfig.URL_API_INTRANET
                        + AppContext.Parameter.ZWGK + "66666" + type + "?page="
                        + page + "&rows=30");
                loadType = 1;

            }
        });

    }

    private void iniData() {
        data = new ArrayList<OpenGoverBean.Data>();
        adapter = new BanShiZhiNanAdapter(data, this, 88806);
        mListView.setAdapter(adapter);

        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.ZWGK + "666661?page=1&rows=30")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String json) {
                httpResponse(json);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void httpResponse(String json) {
        if (loadType == 0) {
            srl.setRefreshing(false);
        } else if (loadType == 1) {
            srl.setLoading(false);
        }
        String JsonData = json;
        JsonFile jsonFile = new JsonFile();
        JsonData = "{\"state\":\"true\",\"MaxPage\":1,\"Data\":" + json + "}";
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

        File ss = new File(file2 + "/BSZNAdata.dll");
        if (json.equals("连接失败")) {
            try {
                JsonData = new String(jsonFile.getFlieJson(ss));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            try {
                jsonFile.FileJson(JsonData, ss);
            } catch (Exception e) {

            }

            if (TextUtils.isEmpty(JsonData)) {
                Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
            }

            OpenGoverBean bean = ParsonJson.jsonToBeanObj(JsonData,
                    OpenGoverBean.class);
            if (loadType == 0) {
                if (bean.getData() != null && bean.getData().size() > 0) {
                    data.clear();
                    data.addAll(bean.getData());
                    adapter.notifyDataSetChanged();
                    page += 1;
                }
            } else if (loadType == 1) {
                if (bean.getData() != null && bean.getData().size() > 0) {
                    data.addAll(bean.getData());
                    adapter.notifyDataSetChanged();
                    page += 1;

                }

            } else {
                if (bean.getData() != null && bean.getData().size() > 0) {
                    data.clear();
                    data.addAll(bean.getData());
                    adapter.notifyDataSetChanged();
                    page += 1;
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

}
