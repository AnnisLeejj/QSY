package com.heking.qsy.activity.regulatory.details;

import java.util.ArrayList;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.JsongDataBean;
import com.heking.qsy.activity.regulatory.OnRefreshListener;
import com.heking.qsy.activity.regulatory.RefreshListView;
import com.heking.qsy.activity.regulatory.ToolUtil;
import com.heking.qsy.activity.regulatory.details.adapter.WenDuShiDuJianCeAdapter;
import com.heking.qsy.activity.regulatory.tab.TB_WenDuShiDuJianCe;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.FirmTypeBean.Data;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WenDuShiDuJianCeActivity extends Activity implements OnRefreshListener {

    private String url;
    private WaitDialog dialog;

    private FirmTypeBean.Data data;
    private TextView mButton;
    private EditText mEditText;
    private boolean boo = true;
    private WenDuShiDuJianCeAdapter adapter;
    private RefreshListView listView;
    private ArrayList<TB_WenDuShiDuJianCe.data> list = new ArrayList<TB_WenDuShiDuJianCe.data>();
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.drug_inventory_activity);

        iniView();
        iniData();
    }

    private void iniView() {
        Tool.endActivity(this);
        url = getIntent().getExtras().getString("URL");
        data = (Data) getIntent().getExtras().getSerializable("data");
        findViewById(R.id.view_show_totototoo).setVisibility(LinearLayout.GONE);
        listView = (RefreshListView) findViewById(R.id.List_view_drug);
        mButton = (TextView) findViewById(R.id.sousuo_chakan);
        mEditText = (EditText) findViewById(R.id.edit_data_maessage);
        ((TextView) findViewById(R.id.tabe_name)).setText(getIntent().getStringExtra("Name"));

    }

    private void iniData() {
        dialog = new WaitDialog(this);
        dialog.setContent("正在加载...");
        dialog.show();
        HttpHelper.getInstance().service.get(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String json) {
                Log.d("message", json);
                JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                TB_WenDuShiDuJianCe TB_WenDuShiDuJianCe = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_WenDuShiDuJianCe.class);
                list = TB_WenDuShiDuJianCe.getData();
                adapter = new WenDuShiDuJianCeAdapter(WenDuShiDuJianCeActivity.this, list);
                listView.setAdapter(adapter);
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                dialog.dismiss();
            }
        });

        listView.setOnRefreshListener(this);
        mButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                boo = false;
                String mes = mEditText.getText().toString().trim();
                dialog.show();
                String url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android") + ToolUtil.getToURl("get_firm_data_HumitureRecords", data.getFirmID(), page, 20, mes);

                //			    String	url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getToURl("get_firm_data_HumitureRecords",data.getFirmID());
                list.clear();
                HttpHelper.getInstance().service.get(url.trim()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                        TB_WenDuShiDuJianCe TB_WenDuShiDuJianCe = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_WenDuShiDuJianCe.class);
                        for (TB_WenDuShiDuJianCe.data datas : TB_WenDuShiDuJianCe.getData()) {
                            list.add(datas);
                        }
                        dialog.dismiss();
                        adapter.notifyDataSetChanged();
                        listView.hideFooterView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }
                });

            }
        });

    }

    @Override
    public void onDownPullRefresh() {
        if (boo) {
            HttpHelper.getInstance().service.get(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(String json) {
                    JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                    TB_WenDuShiDuJianCe TB_WenDuShiDuJianCe = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_WenDuShiDuJianCe.class);
                    list.clear();
                    for (TB_WenDuShiDuJianCe.data datas : TB_WenDuShiDuJianCe.getData()) {
                        list.add(datas);
                    }
                    adapter.notifyDataSetChanged();
                    listView.hideHeaderView();
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        } else {
            listView.hideHeaderView();
        }
    }

    @Override
    public void onLoadingMore() {
        if (boo) {
            page++;
            String url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android") + ToolUtil.getToURl("get_firm_data_HumitureRecords", data.getFirmID(), page, 20, "");
//	    String	url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getToURl("get_firm_data_HumitureRecords",data.getFirmID());
            HttpHelper.getInstance().service.get(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(String json) {
                    JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                    TB_WenDuShiDuJianCe TB_WenDuShiDuJianCe = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_WenDuShiDuJianCe.class);
                    for (TB_WenDuShiDuJianCe.data datas : TB_WenDuShiDuJianCe.getData()) {
                        list.add(datas);
                    }
                    adapter.notifyDataSetChanged();
                    listView.hideFooterView();
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        } else {
            listView.hideFooterView();
        }
    }
}
