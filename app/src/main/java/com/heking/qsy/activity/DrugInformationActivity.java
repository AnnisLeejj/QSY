package com.heking.qsy.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.DrugBean;
import com.heking.qsy.util.DrugBean.Data;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.JsonFile;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import MyUtils.LogUtils.LogUtils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DrugInformationActivity extends Activity {
    private Bundle bundle;
    private String ApprovedBy;
    private LinearLayout linearLayout2;
    private ScrollView msScrollView;
    //private ListView mListView;
    private WaitDialog dialog;
    private TextView mTextMyAdd;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.drug_information_activity_xiang_qing);
        bundle = getIntent().getExtras();
        iniVeiw();
    }

    private void iniVeiw() {
        Tool.endActivity(this);
//	mListView=(ListView) findViewById(R.id.yao_ping_list_view);
        linearLayout2 = (LinearLayout) findViewById(R.id.add_monitor);
        msScrollView = (ScrollView) findViewById(R.id.scroll_view);
        mTextMyAdd = (TextView) findViewById(R.id.search_button);
        String url_b = getUrl(bundle.getString("Name").trim(), bundle.getString("pzwh").trim());
        LogUtils.w("yaopinxinxi", "pzwh:" + bundle.getString("pzwh"));
        LogUtils.w("yaopinxinxi", "url_b:" + url_b);
        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET_ZS_SP + url_b)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String json) {
                LogUtils.w("yaopinxinxi", json);
                String JsonData = json;
                JsonData = "{\"Data\":" + json + "}";
                JsonFile jsonFile = new JsonFile();
                File file;
                File file2 = null;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    file = Environment.getExternalStorageDirectory();
                    file2 = new File(file + "/PZhMessageList/");
                    if (!file2.exists()) {
                        file2.mkdirs();
                    }
                }
                File ss = new File(file2 + "/" + bundle.getString("Name").trim().replace("　", "") + ".dll");
                if (JsonData.equals("连接失败")) {
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
                if (JsonData != null && JsonData.equals("连接失败")) {
                    msScrollView.setVisibility(ScrollView.GONE);
                    linearLayout2.setVisibility(LinearLayout.VISIBLE);
                    dialog.dismiss();
                }

                DrugBean bean = ParsonJson.jsonToBean(JsonData, DrugBean.class);
                if (bean != null && bean.getData().size() > 0) {
                    data = bean.getData().get(0);
                    //information=new DrugInformation(this, myYaoPinShuoMingShu.getData());
                    //mListView.setAdapter(information);
                    iniData();
                    dialog.dismiss();
                } else {
                    msScrollView.setVisibility(ScrollView.GONE);
                    linearLayout2.setVisibility(LinearLayout.VISIBLE);
                    dialog.dismiss();
                    return;
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        dialog = new WaitDialog(this);
        dialog.setContent("正在加载...");
        dialog.show();
        mTextMyAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(DrugInformationActivity.this, InforeportActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getUrl(String GenericName, String ApprovalNumber) {
        String Generic = null;
        String Approval = null;
        try {
            Generic = URLEncoder.encode(GenericName.replace("　", "").replace(" ", ""), "UTF-8");
            Approval = URLEncoder.encode(ApprovalNumber.replace("　", "").replace(" ", ""), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LogUtils.w("yaopinxinxi", "Approval:" + Approval);
        return "Medicine/GetMedicine?GenericName=" + Generic + "&ApprovalNumber=" + Approval;
    }


    private void iniData() {
        ((TextView) findViewById(R.id.drug_view_chengfeng)).setText(data == null || data.getComposition() == null ||
                TextUtils.isEmpty(data.getComposition()) ? "尚不明确" : Html.fromHtml(data.getComposition()));//08003c;

        ((TextView) findViewById(R.id.drug_view_tongyongming)).setText(data == null || data.getComposition() == null ||
                TextUtils.isEmpty(data.getComposition()) ? "尚不明确" : Html.fromHtml(data.getGenericName()));//08003b;

        ((TextView) findViewById(R.id.drug_view_xing_zhuang)).setText(data == null || data.getDescription() == null ||
                TextUtils.isEmpty(data.getDescription()) ? "尚不明确" : Html.fromHtml(data.getDescription()));//08003d;
        ((TextView) findViewById(R.id.drug_view_yongfayongliang)).setText(data == null || data.getDosage() == null ||
                TextUtils.isEmpty(data.getDosage()) ? "尚不明确" : Html.fromHtml(data.getDosage()));//08003d;

        ((TextView) findViewById(R.id.drug_view_zhuzhigongneng)).setText(data == null || data.getIndications() == null ||
                TextUtils.isEmpty(data.getIndications()) ? "尚不明确" : Html.fromHtml(data.getIndications()));//080040;

        ((TextView) findViewById(R.id.drug_view_guige)).setText(data == null || data.getSpecification() == null ||
                TextUtils.isEmpty(data.getSpecification()) ? "尚不明确" : Html.fromHtml(data.getSpecification()));//080040;

        ((TextView) findViewById(R.id.drug_view_buliangfanying)).setText(data == null || data.getAdverseReaction() == null ||
                TextUtils.isEmpty(data.getAdverseReaction()) ? "尚不明确" : Html.fromHtml(data.getAdverseReaction()));//080040;

        ((TextView) findViewById(R.id.drug_view_jinji)).setText(data == null || data.getContraindication() == null ||
                TextUtils.isEmpty(data.getContraindication()) ? "尚不明确" : Html.fromHtml(data.getContraindication()));//080040;

        ((TextView) findViewById(R.id.drug_view_zhuyishixiang)).setText(data == null || data.getCaution() == null ||
                TextUtils.isEmpty(data.getCaution()) ? "尚不明确" : Html.fromHtml(data.getCaution()));//080040;

        ((TextView) findViewById(R.id.drug_view_zhucang)).setText(data == null || data.getStorage() == null ||
                TextUtils.isEmpty(data.getStorage()) ? "尚不明确" : Html.fromHtml(data.getStorage()));//080040;

        ((TextView) findViewById(R.id.drug_view_pizhunwenhao)).setText(bundle.getString("pzwh").trim());//080040;

        ((TextView) findViewById(R.id.drug_view_shengchanqiye)).setText(data == null || data.getManufacturer() == null ||
                TextUtils.isEmpty(data.getManufacturer()) ? "尚不明确" : data.getManufacturer());//080040;
    }
}
