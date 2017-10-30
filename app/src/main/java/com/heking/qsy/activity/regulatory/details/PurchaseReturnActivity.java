package com.heking.qsy.activity.regulatory.details;

import java.util.ArrayList;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.JsongDataBean;
import com.heking.qsy.activity.regulatory.OnRefreshListener;
import com.heking.qsy.activity.regulatory.RefreshListView;
import com.heking.qsy.activity.regulatory.ToolUtil;
import com.heking.qsy.activity.regulatory.details.adapter.PurchaseReturnAdapter;
import com.heking.qsy.activity.regulatory.tab.TB_CaiGouTuiHuo;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.FirmTypeBean.Data;
import com.heking.qsy.util.HttpHelper.HttpApi;
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

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.heking.qsy.util.HttpHelper.HttpHelper.*;

/**
 * 采购退货
 *
 * @author jhw
 */
public class PurchaseReturnActivity extends Activity implements OnRefreshListener {

    private String url;
    private WaitDialog dialog;

    private FirmTypeBean.Data data;
    private TextView mButton;
    private EditText mEditText;
    private boolean boo = true;
    private PurchaseReturnAdapter adapter;
    private RefreshListView listView;
    private ArrayList<TB_CaiGouTuiHuo.data> list = new ArrayList<TB_CaiGouTuiHuo.data>();
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
                TB_CaiGouTuiHuo TB_CaiGouTuiHuo = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_CaiGouTuiHuo.class);
                list = TB_CaiGouTuiHuo.getData();
                adapter = new PurchaseReturnAdapter(PurchaseReturnActivity.this, list);
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
                String url = ToolUtil.DataUrl.getUrl("http://111.9.170.48/Android") + ToolUtil.getcaiGoutuihuo(data.getFirmID(), page, 20, mes);

                //			    String	url=ToolUtil.DataUrl.getUrl("http://111.9.170.48/Android")+ToolUtil.getcaiGoutuihuo(data.getFirmID());
                list.clear();
                HttpHelper.getInstance().service.get(url.trim()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                        TB_CaiGouTuiHuo TB_CaiGouTuiHuo = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_CaiGouTuiHuo.class);
                        for (TB_CaiGouTuiHuo.data datas : TB_CaiGouTuiHuo.getData()) {
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
                    TB_CaiGouTuiHuo TB_CaiGouTuiHuo = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_CaiGouTuiHuo.class);
                    list.clear();
                    for (TB_CaiGouTuiHuo.data datas : TB_CaiGouTuiHuo.getData()) {
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
            String url = ToolUtil.DataUrl.getUrl("http://111.9.170.48/Android") + ToolUtil.getcaiGoutuihuo(data.getFirmID(), page, 20, "");
//	    String	url=ToolUtil.DataUrl.getUrl("http://111.9.170.48/Android")+ToolUtil.getcaiGoutuihuo(data.getFirmID());

            HttpHelper.getInstance().service.get(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(String json) {
                    JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                    TB_CaiGouTuiHuo TB_CaiGouTuiHuo = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_CaiGouTuiHuo.class);
                    for (TB_CaiGouTuiHuo.data datas : TB_CaiGouTuiHuo.getData()) {
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
