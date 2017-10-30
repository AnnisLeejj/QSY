package com.heking.qsy.activity.regulatory.details.type;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.JsongDataBean;
import com.heking.qsy.activity.regulatory.ToolUtil;
import com.heking.qsy.activity.regulatory.details.ProcurementWarehousingActivity;
import com.heking.qsy.activity.regulatory.details.SalesReturnActivity;
import com.heking.qsy.activity.regulatory.tab.TB_YaoPinKuCunType;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.FirmTypeBean.Data;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class YaoPingKuCunShow extends Activity {
    private String url;
    private WaitDialog dialog;
    private FirmTypeBean.Data data;
    private String name;
    private TextView kucunxinxidata, yaopinmigncheng, shengchanchangjia, guige, jixing, pizhunwenhao, shangpintiaoma, gongyingshang, youxiaoqi, genxinriqi, cangkumingcheng, danwei, shuliang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.yao_ping_ku_cun_show);
        url = getIntent().getStringExtra("keyUrl");
        name = getIntent().getStringExtra("cococo");

        data = (Data) getIntent().getExtras().getSerializable("data");
        iniView();
        iniData();
    }

    private Button chakancaigouxinxi, chakanxiaoshouxinxi;

    private void iniView() {
        Tool.endActivity(this);
        Tool.changetitle(this, "库存信息");
        yaopinmigncheng = (TextView) findViewById(R.id.viual);
        shengchanchangjia = (TextView) findViewById(R.id.shengchanchangjia);
        guige = (TextView) findViewById(R.id.guige);
        jixing = (TextView) findViewById(R.id.jixing);
        pizhunwenhao = (TextView) findViewById(R.id.pizhunwenhao);
        shangpintiaoma = (TextView) findViewById(R.id.shangpintiaoma);
        gongyingshang = (TextView) findViewById(R.id.gongyingshang);
        youxiaoqi = (TextView) findViewById(R.id.youxiaoqi);
        genxinriqi = (TextView) findViewById(R.id.gengxinriqi);
        cangkumingcheng = (TextView) findViewById(R.id.cangkumingcheng);
        danwei = (TextView) findViewById(R.id.danwei);
        shuliang = (TextView) findViewById(R.id.shuliang);

        kucunxinxidata = (TextView) findViewById(R.id.kucunxinxidata);

        chakancaigouxinxi = (Button) findViewById(R.id.chakancaigouxinxi);
        chakanxiaoshouxinxi = (Button) findViewById(R.id.chakanxiaoshouxinxi);


    }

    private TB_YaoPinKuCunType tb_YaoPinKuCunType;

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
                if (json.equals("连接失败")) {
                    dialog.dismiss();
                    Toast.makeText(YaoPingKuCunShow.this, "网络连接失败，请稍后重试！", 500).show();
                } else {
                    JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                    tb_YaoPinKuCunType = ParsonJson.jsonToBeanObj(bean.getJsonData(), TB_YaoPinKuCunType.class);

                    if (tb_YaoPinKuCunType != null) {
                        yaopinmigncheng.setText(tb_YaoPinKuCunType.getName());//(TextView) findViewById(R.id.yaopingmingcheng);
                        shengchanchangjia.setText(tb_YaoPinKuCunType.getManufacturer());//(TextView) findViewById(R.id.shengchanchangjia);
                        guige.setText(tb_YaoPinKuCunType.getSpecification());//(TextView) findViewById(R.id.guige);
                        jixing.setText(tb_YaoPinKuCunType.getFormulation());//(TextView) findViewById(R.id.jixing);
                        pizhunwenhao.setText(tb_YaoPinKuCunType.getApprovalNumber());//(TextView) findViewById(R.id.pizhunwenhao);
                        shangpintiaoma.setText(tb_YaoPinKuCunType.getBarCode());//(TextView) findViewById(R.id.shangpintiaoma);
                        gongyingshang.setText(tb_YaoPinKuCunType.getSupplier());//(TextView) findViewById(R.id.gongyingshang);
                        youxiaoqi.setText(new Tooluite().getDate(tb_YaoPinKuCunType.getExpiredDate(), "T"));//(TextView) findViewById(R.id.youxiaoqi);
                        genxinriqi.setText(new Tooluite().getDate(tb_YaoPinKuCunType.getUploadTime(), "T"));//(TextView) findViewById(R.id.gengxinriqi);
                        cangkumingcheng.setText(tb_YaoPinKuCunType.getStoreRoomName());//(TextView) findViewById(R.id.cangkumingcheng);
                        danwei.setText(tb_YaoPinKuCunType.getUnit());//(TextView) findViewById(R.id.danwei);
                        Double dou = Double.parseDouble(tb_YaoPinKuCunType.getQuantity());
                        int sl = dou.intValue();
                        shuliang.setText(sl + "");//(TextView) findViewById(R.id.shuliang);
                        dialog.dismiss();

                        chakancaigouxinxi.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                String url = "{\"RequestMethod\":\"get_firm_data_purchase_inStock\",\"RequestParams\":\"{\\\"MedicineID\\\":\\\"" + tb_YaoPinKuCunType.getMedicineID() + "\\\",\\\"count\\\":20,\\\"currentPage\\\":1,\\\"firmID\\\":\\\"" + data.getFirmID() + "\\\",\\\"searchFilter\\\":\\\"\\\"}\"}";
//				String	url="{\"RequestMethod\":\"get_firm_data_purchase_inStock\",\"RequestParams\":\"{\\\"MedicineID\\\":\\\""+tb_YaoPinKuCunType.getMedicineID()+"\\\",\\\"count\\\":20,\\\"currentPage\\\":1,\\\"firmID\\\":\\\""+data.getFirmID()+"\\\",\\\"searchFilter\\\":\\\"\\\"}\"}";
                                String httpUrl = ToolUtil.DataUrl.getUrl(AppContext.Parameter.GET_FIRM_DATA) + ToolUtil.getSouSuoURL(url);
                                Intent intent = new Intent();
                                intent.setClass(YaoPingKuCunShow.this, ProcurementWarehousingActivity.class);
//				url=ToolUtil.DataUrl.getUrl("http://111.9.170.48/Android")+ToolUtil.getKuCun(data.getFirmID());
                                Bundle bundle = new Bundle();
                                bundle.putString("URL", httpUrl);
                                intent.putExtra("Name", "查看采购信息");
                                bundle.putString("state", tb_YaoPinKuCunType.getMedicineID());
                                bundle.putSerializable("data", data);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        chakanxiaoshouxinxi.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                String url = "{\"RequestMethod\":\"get_firm_data_sellOutStock\",\"RequestParams\":\"{\\\"MedicineID\\\":\\\"" + tb_YaoPinKuCunType.getMedicineID() + "\\\",\\\"count\\\":20,\\\"currentPage\\\":1,\\\"firmID\\\":\\\"" + data.getFirmID() + "\\\",\\\"searchFilter\\\":\\\"\\\"}\"}";
                                String httpUrl = ToolUtil.DataUrl.getUrl(AppContext.Parameter.GET_FIRM_DATA) + ToolUtil.getSouSuoURL(url);
                                Intent intent = new Intent();
                                intent.setClass(YaoPingKuCunShow.this, SalesReturnActivity.class);
//					url=ToolUtil.DataUrl.getUrl("http://111.9.170.48/Android")+ToolUtil.getKuCun(data.getFirmID());
                                Bundle bundle = new Bundle();
                                bundle.putString("URL", httpUrl);
                                bundle.putString("state", tb_YaoPinKuCunType.getMedicineID());
                                bundle.putSerializable("data", data);
                                intent.putExtra("Name", "查看销售信息");
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                dialog.dismiss();
            }
        });
        kucunxinxidata.setText(name);
        if (name.equals("医疗器械库存信息")) {
            findViewById(R.id.button_shwo_layout).setVisibility(LinearLayout.GONE);
            findViewById(R.id.yao_ji_layout).setVisibility(LinearLayout.GONE);
            Tool.changetitle(this, "器械库存信息");
        }

        //初始化数据
        yaopinmigncheng.setText(" ");//(TextView) findViewById(R.id.yaopingmingcheng);
        shengchanchangjia.setText(" ");//(TextView) findViewById(R.id.shengchanchangjia);
        guige.setText(" ");//(TextView) findViewById(R.id.guige);
        jixing.setText(" ");//(TextView) findViewById(R.id.jixing);
        pizhunwenhao.setText(" ");//(TextView) findViewById(R.id.pizhunwenhao);
        shangpintiaoma.setText(" ");//(TextView) findViewById(R.id.shangpintiaoma);
        gongyingshang.setText(" ");//(TextView) findViewById(R.id.gongyingshang);
        youxiaoqi.setText(" ");//(TextView) findViewById(R.id.youxiaoqi);
        genxinriqi.setText(" ");//(TextView) findViewById(R.id.gengxinriqi);
        cangkumingcheng.setText(" ");//(TextView) findViewById(R.id.cangkumingcheng);
        danwei.setText(" ");//(TextView) findViewById(R.id.danwei);
        shuliang.setText(" ");//(TextView) findViewById(R.id.shuliang);
    }

}

