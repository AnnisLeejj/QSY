package com.heking.qsy.activity.Patrol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.review.SwipeRefreshView;
import com.heking.snoa.WPConfig;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class TongzhiActivity extends Activity {
    SwipeRefreshView srl;
    ListView listView;

    TongzhiAdapter tongzhiAdapter;
    List<SupervisionBean> supervisionBeanList;
    int pageNumber = 1;
    String UID = "";
    ImageButton ibtnR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        supervisionBeanList = new ArrayList<SupervisionBean>();
        setContentView(R.layout.activity_xunjian_tongzhi);
        srl = (SwipeRefreshView) findViewById(R.id.srl);
        listView = (ListView) findViewById(R.id.listView);
        ibtnR = (ImageButton) findViewById(R.id.ibtnR);
        ibtnR.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
        tongzhiAdapter = new TongzhiAdapter(supervisionBeanList);
        listView.setAdapter(tongzhiAdapter);

        if (AppContext.LoginUserMessage.bean != null) {

            UID = AppContext.LoginUserMessage.bean.getUID();
        }
        getData(pageNumber, 20, UID);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 1;
                getData(pageNumber, 20, UID);
                srl.setRefreshing(false);
            }
        });
        // 设置下拉加载更多
        srl.setOnLoadListener(new SwipeRefreshView.OnLoadListener() {
            @Override
            public void onLoad() {
                srl.setLoading(false);
                pageNumber++;
                getData(pageNumber, 20, UID);
            }
        });
    }
    private void getData(int PageNumber, int PageSize, String UID) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("PageNumber", PageNumber + "");
        params.addQueryStringParameter("PageSize", PageSize + "");
        params.addQueryStringParameter("CloudUID", UID);
        HttpUtils http = new HttpUtils();
        http.send(HttpMethod.GET, WPConfig.URL_API_INTRANET + "Inspection/GetInspectionLog", params,
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (!TextUtils.isEmpty(responseInfo.result)) {
                            try {
                                JSONObject jsonObject = new JSONObject(responseInfo.result);
                                int code = jsonObject.getInt("code");
                                if (code == 200) {
                                    JSONObject data = jsonObject.optJSONObject("data");
                                    JSONArray dataArrays = data.optJSONArray("data");
                                    if (pageNumber == 1) {
                                        supervisionBeanList.clear();
                                    }
                                    if (dataArrays != null) {
                                        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy年MM月dd日").create();
                                        List<SupervisionBean> supervisionBeans = gson.fromJson(dataArrays.toString(), new TypeToken<List<SupervisionBean>>() {
                                        }.getType());
                                        supervisionBeanList.addAll(supervisionBeans);
                                        tongzhiAdapter.notifyDataSetChanged();
                                    }
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }
}
