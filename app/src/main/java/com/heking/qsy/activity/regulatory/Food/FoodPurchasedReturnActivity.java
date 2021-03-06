package com.heking.qsy.activity.regulatory.Food;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.JsongDataBean;
import com.heking.qsy.activity.regulatory.OnRefreshListener;
import com.heking.qsy.activity.regulatory.RefreshListView;
import com.heking.qsy.activity.regulatory.RegulatoryUTil;
import com.heking.qsy.activity.regulatory.ToolUtil;
import com.heking.qsy.activity.regulatory.Food.adapter.FoodInventoryAdapter;
import com.heking.qsy.activity.regulatory.Food.adapter.FoodPurchasedReturnAdapter;
import com.heking.qsy.activity.regulatory.Food.tab.TB_FoodPurchasedRetrun;
import com.heking.qsy.activity.regulatory.details.adapter.DrugInventoryAdapter;
import com.heking.qsy.activity.regulatory.details.adapter.QiTaLXingChuKuAdapter;
import com.heking.qsy.activity.regulatory.tab.TB_qitaleixingchuku;
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
import android.widget.Toast;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 库存
 *
 * @author jhw
 */
public class FoodPurchasedReturnActivity extends Activity implements OnRefreshListener {
    private String url;
    private WaitDialog dialog;
    private FirmTypeBean.Data data;
    private EditText mEditText;
    private boolean boo = true;
    private RefreshListView listView;
    private ArrayList<TB_FoodPurchasedRetrun.Data> list;
    private RegulatoryUTil uTil;
    private FoodPurchasedReturnAdapter adapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        ((TextView) findViewById(R.id.tabe_name)).setText(getIntent().getExtras().getString("Name"));
        uTil = (RegulatoryUTil) getIntent().getExtras().getSerializable("RegulatoryUTil");

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
                TB_FoodPurchasedRetrun TB_FoodPurchasedRetrun = ParsonJson.jsonToBeanObj("{\"Data\":" + json + "}", TB_FoodPurchasedRetrun.class);
                if (TB_FoodPurchasedRetrun != null) {
                    list = TB_FoodPurchasedRetrun.getData();
                    adapter = new FoodPurchasedReturnAdapter(FoodPurchasedReturnActivity.this, list, data, uTil);
                    listView.setAdapter(adapter);
                }
                dialog.dismiss();
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

            HttpHelper.getInstance().service.get(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(String json) {
                    JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                }

                @Override
                public void onError(Throwable e) {
                    dialog.dismiss();
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
            HttpHelper.getInstance().service.get(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(String json) {
                    JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
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
