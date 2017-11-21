package com.heking.qsy.activity.FirmShow;

import java.util.ArrayList;

import com.baidu.mapapi.search.poi.PoiSearch;
import com.heking.SPJK.resource.ResourceListAdapter;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.RegulatoryActivity;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.FirmTypeBean.Data;
import com.hikvision.vmsnetsdk.CameraInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FirmType extends Activity implements OnViewData {
    private Bundle bundle;
    private FirmTypeBean.Data data;
    private LinearLayout Monitors;
    private ListView Monitorslayout;
    private TextView TextFirmName;
    private TextView TextAddress;
    private TextView TextEmail;
    private TextView TextLegalRepresentative;
    private TextView TextLegalRepresentativePhones;
    private TextView TextManager;
    private TextView TextManagerPhones;
    private TextView TextHeadOfQuality;
    private TextView TextQualityHeadPhones;
    private TextView TextFirmTypeName;
    private TextView TextAnnualRating;
    private TextView TextMonitors;
    private ListView mListLicense;
    private TextView TextNavigation;
    private PoiSearch poiSearch;
    //	private AMapLocationClient mlocationClient;
//	private AMapLocationClientOption mLocationOption = null;
    private static TextView mFiryTypes;
    private static TextView firm_xunjian_message;
    private int intS;
    private static WaitDialog dialog;

    // private int mRating;
    // private boolean ioos;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        bundle = getIntent().getExtras();
        data = (Data) bundle.getSerializable("FIRMTYPE");
        intS = bundle.getInt("State");
        setContentView(R.layout.firm_type);
        findViewById(R.id.textview).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });
        dialog = new WaitDialog(this);
        dialog.setContent("正在加载...");
        iniView();
        iniData();
        Log.i("FirmType", "---------sssss---------------------------");
        //   new LogInHk(this, savedInstanceState, data);
    }

    public static void enddialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    private void iniView() {

        TextNavigation = (TextView) findViewById(R.id.Text_Navigation);

        Monitors = (LinearLayout) findViewById(R.id.monitoring);
        Monitorslayout = (ListView) findViewById(R.id.monitoring_layout);

        TextFirmName = (TextView) findViewById(R.id.firm_name_message);
        TextAddress = (TextView) findViewById(R.id.firm_address_message);
        TextEmail = (TextView) findViewById(R.id.firm_email_message);

        TextLegalRepresentative = (TextView) findViewById(R.id.Legal_Representative_message);
        TextLegalRepresentativePhones = (TextView) findViewById(R.id.Legal_Representative_phones_message);

        TextManager = (TextView) findViewById(R.id.Manager_message);
        TextManagerPhones = (TextView) findViewById(R.id.Manager_Phones_message);
        TextHeadOfQuality = (TextView) findViewById(R.id.Head_Of_Quality_message);

        TextQualityHeadPhones = (TextView) findViewById(R.id.Head_Quality_Phones_message);
        TextFirmTypeName = (TextView) findViewById(R.id.firm_annual_rating_message);

        TextAnnualRating = (TextView) findViewById(R.id.ping_ji_message);
        TextMonitors = (TextView) findViewById(R.id.monitoring_name);

        mFiryTypes = (TextView) findViewById(R.id.firm_data_message);
        firm_xunjian_message = (TextView) findViewById(R.id.firm_xunjian_message);

        TextFirmName.setText(data.getFirmName());
        TextAddress.setText(data.getAddress());
        TextEmail.setText(data.getEmail());

        TextLegalRepresentative.setText(data.getLegalRepresentative());
        TextLegalRepresentativePhones.setText(data.getLegalRepresentativePhones());

        TextManager.setText(data.getManager());
        TextManagerPhones.setText(data.getManagerPhones());

        TextHeadOfQuality.setText(data.getHeadOfQuality());
        TextQualityHeadPhones.setText(data.getQualityHeadPhones());
        TextFirmTypeName.setText(data.getFirmTypeName1());
//
//		mListLicense = (ListView) findViewById(R.id.zhengzhao_layout);

        switch (data.getmRating()) {
            case 1:
                TextAnnualRating.setBackgroundDrawable(getResources().getDrawable(R.drawable.a));

                break;
            case 2:
                TextAnnualRating.setBackgroundDrawable(getResources().getDrawable(R.drawable.b));

                break;
            case 3:
                TextAnnualRating.setBackgroundDrawable(getResources().getDrawable(R.drawable.c));
                break;
        }
    }

    ArrayList<CameraInfo> cameraInfos;

    private void iniData() {
        // if (!AppContext.LoginUserMessage.messageUse) {
        // mFiryTypes.setVisibility(LinearLayout.GONE);
        // }
        mFiryTypes.setVisibility(LinearLayout.GONE);

        if (AppContext.LoginUserMessage.bean != null && AppContext.LoginUserMessage.bean.getSystemMenus() != null) {
            for (int i = 0; i < AppContext.LoginUserMessage.bean.getSystemMenus().size(); i++) {
                if (AppContext.LoginUserMessage.bean.getSystemMenus().get(i).getCode().equals("1")) {
                    mFiryTypes.setVisibility(View.VISIBLE);
                    firm_xunjian_message.setVisibility(View.VISIBLE);
                }
            }
        }
        mFiryTypes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // if(AppContext.LoginUserMessage.messageUse){
                Intent intent = new Intent(FirmType.this, RegulatoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data);
                bundle.putInt("State", intS);
                intent.putExtras(bundle);
                FirmType.this.startActivity(intent);
                // }
            }
        });

        if (data.getMonitors() != null) {
            if (data.getMonitors().size() > 0) {
                if (data.getFirmTypeName().equals("餐饮企业") || data.getFirmTypeName().equals("食品企业")) {
                    TextMonitors.setText("视频");
                    mFiryTypes.setVisibility(LinearLayout.GONE);
                } else {
                    TextMonitors.setText("视频");
                }
                cameraInfos = new ArrayList<CameraInfo>();
                if (AppContext.cameraInfos_hk != null) {
                    for (Data.Monitors item : data.getMonitors()) {
                        for (com.hikvision.vmsnetsdk.CameraInfo item1 : AppContext.cameraInfos_hk) {
                            if (item.getName().equals(item1.getName())) {
                                cameraInfos.add(item1);
                                continue;
                            }
                        }
                    }
                    ResourceListAdapter.mList = cameraInfos;
                }
                MonitorsAdapter adapter = new MonitorsAdapter(cameraInfos, this, true);
                Monitorslayout.setAdapter(adapter);
                dialog.show();
            } else {
                Monitors.setVisibility(LinearLayout.GONE);

            }
        }
        if (data.getFirmLicenseData() != null) {
            if (data.getFirmLicenseData().size() > 0) {
                FirmTypeAdapter adapter = new FirmTypeAdapter(data.getFirmLicenseData(), this);
                mListLicense.setAdapter(adapter);
            }
            // else{
            // License.setVisibility(LinearLayout.GONE);
            // }
        }
        enddialog();
    }

    @Override
    public void setOnView(View view) {
        Monitors.addView(view);
    }

}
