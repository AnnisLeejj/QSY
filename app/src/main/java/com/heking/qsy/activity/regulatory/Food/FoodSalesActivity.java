package com.heking.qsy.activity.regulatory.Food;

import java.util.ArrayList;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.JsongDataBean;
import com.heking.qsy.activity.regulatory.OnRefreshListener;
import com.heking.qsy.activity.regulatory.RefreshListView;
import com.heking.qsy.activity.regulatory.RegulatoryUTil;
import com.heking.qsy.activity.regulatory.ToolUtil;
import com.heking.qsy.activity.regulatory.Food.adapter.FoodSalesAdapter;
import com.heking.qsy.activity.regulatory.Food.tab.TB_FoodSalses;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 库存
 *
 * @author jhw
 */
public class FoodSalesActivity extends Activity implements  OnRefreshListener {
    private String url;
    private WaitDialog dialog;
    private FirmTypeBean.Data data;
    private EditText mEditText;
    private boolean boo = true;
    private RefreshListView listView;
    private ArrayList<TB_FoodSalses.Data> list;
    private RegulatoryUTil uTil;
    private FoodSalesAdapter adapter;
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
        uTil = (RegulatoryUTil) getIntent().getExtras().getSerializable("RegulatoryUTil");

        ((TextView) findViewById(R.id.tabe_name)).setText(getIntent().getExtras().getString("Name"));
        findViewById(R.id.view_show_totototoo).setVisibility(LinearLayout.GONE);
        listView = (RefreshListView) findViewById(R.id.List_view_drug);
        mEditText = (EditText) findViewById(R.id.edit_data_maessage);


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
                TB_FoodSalses TB_FoodSalses = ParsonJson.jsonToBeanObj("{\"Data\":" + json + "}", TB_FoodSalses.class);
                if (TB_FoodSalses != null) {
                    list = TB_FoodSalses.getData();
                    adapter = new FoodSalesAdapter(FoodSalesActivity.this, list, data, uTil);
                    listView.setAdapter(adapter);

                }  dialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                dialog.dismiss();
            }
        });
        listView.setOnRefreshListener(this);

    }

    @Override
    public void onDownPullRefresh() {
        if (boo) {
            HttpHelper.getInstance().service.get(url).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(String value) {
                    JsongDataBean bean = ParsonJson.jsonToBeanObj(value, JsongDataBean.class);
                    listView.hideFooterView();
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
            String url = ToolUtil.DataUrl.getUrl("http://111.9.170.48/Android") + ToolUtil.getToURl("get_firm_data_other_outStock", "F000000000000002", 1, 20, "");
//	    String	url=ToolUtil.DataUrl.getUrl("http://111.9.170.48/Android")+ToolUtil.getKuCun(data.getFirmID());

            HttpHelper.getInstance().service.get(url).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(String value) {
                    JsongDataBean bean = ParsonJson.jsonToBeanObj(value, JsongDataBean.class);
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
