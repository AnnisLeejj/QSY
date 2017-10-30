package com.heking.qsy.activity.regulatory.details;

import java.util.ArrayList;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.JsongDataBean;
import com.heking.qsy.activity.regulatory.OnRefreshListener;
import com.heking.qsy.activity.regulatory.RefreshListView;
import com.heking.qsy.activity.regulatory.ToolUtil;
import com.heking.qsy.activity.regulatory.details.adapter.SalesReturnAdapter;
import com.heking.qsy.activity.regulatory.tab.TB_XiaoShouChuKu;
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

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 销售出库
 *
 * @author jhw
 */
public class SalesReturnActivity extends Activity implements OnRefreshListener {

    private String url;
    private WaitDialog dialog;

    private FirmTypeBean.Data data;
    private TextView mButton;
    private EditText mEditText;
    private String State;
    private boolean ewb = false;
    private boolean boo = true;
    private SalesReturnAdapter adapter;
    private RefreshListView listView;
    private ArrayList<TB_XiaoShouChuKu.data> list = new ArrayList<TB_XiaoShouChuKu.data>();
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

        State = getIntent().getExtras().getString("state");
        ewb = State != null && !State.equals("") ? true : false;

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
                TB_XiaoShouChuKu TB_XiaoShouChuKu = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_XiaoShouChuKu.class);
                list = TB_XiaoShouChuKu.getData();
                adapter = new SalesReturnAdapter(SalesReturnActivity.this, list);
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
                String url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android") + ToolUtil.getToURl("get_firm_data_sellOutStock", "F000000000000002", page, 20, mes);

                //			    String	url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getToURl("get_firm_data_sellOutStock",data.getFirmID());
                list.clear();
              HttpHelper.getInstance().service.get(url.trim()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                  @Override
                  public void onSubscribe(Disposable d) {

                  }

                  @Override
                  public void onSuccess(String json) {
                      JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                      TB_XiaoShouChuKu TB_XiaoShouChuKu = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_XiaoShouChuKu.class);
                      for (TB_XiaoShouChuKu.data datas : TB_XiaoShouChuKu.getData()) {
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
                    TB_XiaoShouChuKu TB_XiaoShouChuKu = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_XiaoShouChuKu.class);
                    list.clear();
                    for (TB_XiaoShouChuKu.data datas : TB_XiaoShouChuKu.getData()) {
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
            String url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android") + ToolUtil.getToURl("get_firm_data_sellOutStock", "F000000000000002", page, 20, "");
//	    String	url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getToURl("get_firm_data_sellOutStock",data.getFirmID());

            if (ewb) {
                String httpUrl = "{\"RequestMethod\":\"get_firm_data_sellOutStock\",\"RequestParams\":\"{\\\"MedicineID\\\":\\\"" + State + "\\\",\\\"count\\\":20,\\\"currentPage\\\":" + page + ",\\\"firmID\\\":\\\"" + data.getFirmID() + "\\\",\\\"searchFilter\\\":\\\"\\\"}\"}";
//			String	url="{\"RequestMethod\":\"get_firm_data_purchase_inStock\",\"RequestParams\":\"{\\\"MedicineID\\\":\\\""+tb_YaoPinKuCunType.getMedicineID()+"\\\",\\\"count\\\":20,\\\"currentPage\\\":1,\\\"firmID\\\":\\\""+data.getFirmID()+"\\\",\\\"searchFilter\\\":\\\"\\\"}\"}";
                url = ToolUtil.DataUrl.getUrl(AppContext.Parameter.GET_FIRM_DATA) + ToolUtil.getSouSuoURL(httpUrl);
            }

            HttpHelper.getInstance().service.get(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(String json) {
                    JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                    TB_XiaoShouChuKu TB_XiaoShouChuKu = ParsonJson.jsonToBeanObj("{\"data\":" + bean.getJsonData() + "}", TB_XiaoShouChuKu.class);
                    for (TB_XiaoShouChuKu.data datas : TB_XiaoShouChuKu.getData()) {
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
