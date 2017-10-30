package com.heking.qsy.activity.regulatory.details;

import java.util.ArrayList;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.JsongDataBean;
import com.heking.qsy.activity.regulatory.OnRefreshListener;
import com.heking.qsy.activity.regulatory.RefreshListView;
import com.heking.qsy.activity.regulatory.ToolUtil;
import com.heking.qsy.activity.regulatory.details.adapter.ProcurementWarehousingAdapter;
import com.heking.qsy.activity.regulatory.tab.TB_CaiGouRuKu;
import com.heking.qsy.activity.regulatory.tab.TB_TeShuYaoPinXiaoShou;
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
 * 采购入库
 *
 * @author jhw
 */
public class ProcurementWarehousingActivity extends Activity implements  OnRefreshListener {

    private String url;
    private WaitDialog dialog;

    private FirmTypeBean.Data data;
    private TextView mButton;
    private EditText mEditText;
    private String State;
    private boolean ewb = false;
    private boolean boo = true;
    private ProcurementWarehousingAdapter adapter;
    private RefreshListView listView;
    private ArrayList<TB_CaiGouRuKu.data> list = new ArrayList<TB_CaiGouRuKu.data>();
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
        findViewById(R.id.view_show_totototoo).setVisibility(LinearLayout.GONE);
        url = getIntent().getExtras().getString("URL");
        data = (Data) getIntent().getExtras().getSerializable("data");
        State = getIntent().getExtras().getString("state");
        ewb = State != null && !State.equals("") ? true : false;
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
                dialog.dismiss();
                if (!json.equals("连接失败")) {
                    Log.d("message", json);
                    JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                    TB_CaiGouRuKu caiGouRuKu = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_CaiGouRuKu.class);
                    list = caiGouRuKu.getData();
                    adapter = new ProcurementWarehousingAdapter(ProcurementWarehousingActivity.this, list, data);
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(ProcurementWarehousingActivity.this, "网络异常，请稍后重试！", 500).show();
                }
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
                String url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android") + ToolUtil.getcaiGou(data.getFirmID(), page, 20, mes);

                //			    String	url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getcaiGou(data.getFirmID());
                list.clear();
                HttpHelper.getInstance().service.get(url.trim()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        if (!json.equals("连接失败")) {
                            JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                            TB_CaiGouRuKu TB_CaiGouRuKu = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_CaiGouRuKu.class);
                            for (TB_CaiGouRuKu.data datas : TB_CaiGouRuKu.getData()) {
                                list.add(datas);
                            }
                            dialog.dismiss();
                            adapter.notifyDataSetChanged();
                            listView.hideFooterView();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(ProcurementWarehousingActivity.this, "网络异常，请稍后重试！", 500).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

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
                public void onSuccess(String json) { dialog.dismiss();
                    if (!json.equals("连接失败")) {
                        JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                        TB_CaiGouRuKu TB_CaiGouRuKu = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_CaiGouRuKu.class);
                        list.clear();
                        for (TB_CaiGouRuKu.data datas : TB_CaiGouRuKu.getData()) {
                            list.add(datas);
                        }
                        adapter.notifyDataSetChanged();

                        listView.hideHeaderView();
                    } else {

                        Toast.makeText(ProcurementWarehousingActivity.this, "网络异常，请稍后重试！", Toast.LENGTH_SHORT).show();
                    }
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
            String url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android") + ToolUtil.getcaiGou(data.getFirmID(), page, 20, "");
            if (ewb) {
                String httpUrl = "{\"RequestMethod\":\"get_firm_data_purchase_inStock\",\"RequestParams\":\"{\\\"MedicineID\\\":\\\"" + State + "\\\",\\\"count\\\":20,\\\"currentPage\\\":" + page + ",\\\"firmID\\\":\\\"" + data.getFirmID() + "\\\",\\\"searchFilter\\\":\\\"\\\"}\"}";
//			String	url="{\"RequestMethod\":\"get_firm_data_purchase_inStock\",\"RequestParams\":\"{\\\"MedicineID\\\":\\\""+tb_YaoPinKuCunType.getMedicineID()+"\\\",\\\"count\\\":20,\\\"currentPage\\\":1,\\\"firmID\\\":\\\""+data.getFirmID()+"\\\",\\\"searchFilter\\\":\\\"\\\"}\"}";
                url = ToolUtil.DataUrl.getUrl(AppContext.Parameter.GET_FIRM_DATA) + ToolUtil.getSouSuoURL(httpUrl);
            }
//	    String	url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getcaiGou(data.getFirmID());
            HttpHelper.getInstance().service.get(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(String json) { dialog.dismiss();
                    if (!json.equals("连接失败")) {
                        page--;
                        JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                        TB_CaiGouRuKu TB_CaiGouRuKu = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_CaiGouRuKu.class);
                        for (TB_CaiGouRuKu.data datas : TB_CaiGouRuKu.getData()) {
                            list.add(datas);
                        }
                        adapter.notifyDataSetChanged();
                        listView.hideFooterView();

                    } else {
                        Toast.makeText(ProcurementWarehousingActivity.this, "网络异常，请稍后重试！", 500).show();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    dialog.dismiss();
                }
            });

        } else {
            listView.hideFooterView();
        }
    }
}
