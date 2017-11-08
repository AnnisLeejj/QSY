package com.heking.qsy.activity.FirmShow;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.heking.SPJK.constants.Constants;
import com.heking.SPJK.data.Config;
import com.heking.SPJK.data.TempData;
import com.heking.SPJK.live.LiveActivity;
import com.heking.qsy.AppContext;
import com.heking.qsy.Model.AllCameraInfo;
import com.heking.qsy.R;
import com.heking.qsy.activity.ConvenienceService.util.BNDemoMainActivity;
import com.heking.qsy.activity.Patrol.PatrolActivity;
import com.heking.qsy.activity.regulatory.RegulatoryActivity;
import com.heking.qsy.base.BaseActivity;
import com.heking.qsy.providers.HttpImage;
import com.heking.qsy.providers.ImageBitmap;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.FirmTypeBean.Data;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.snoa.WPConfig;
import com.hikvision.vmsnetsdk.CameraInfo;
import com.hikvision.vmsnetsdk.ServInfo;
import com.hikvision.vmsnetsdk.VMSNetSDK;
import com.hikvision.vmsnetsdk.netLayer.msp.cameraList.Camera;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import MyUtils.LogUtils.LogUtils;
import MyUtils.SharePrefenceUtils.SPUtils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BaiDuFirmTypeActivity extends BaseActivity implements ImageBitmap, OnViewData {
    private Bundle bundle;

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
    private LinearLayout mListLicense;
    private TextView TextNavigation;
    private static TextView mFiryTypes, mXunJianData;
    private ImageView iv_qcode;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    ScrollView sv;
    //传入的数据
    private int intS;
    private FirmTypeBean.Data data;
    MonitorsAdapter adapter;
    ArrayList<CameraInfo> cameraInfos;

    // private int mRating;
    // private boolean ioos;
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.firm_type);
        findViewById(R.id.textview).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });
        bundle = getIntent().getExtras();
        data = (Data) bundle.getSerializable("FIRMTYPE");
        intS = bundle.getInt("State");
        Monitors = (LinearLayout) findViewById(R.id.monitoring);

        iniView();
        if (data.getMonitors() != null && data.getMonitors().size() > 0) {//有摄像头,去获取
            //正常渠道 逐级获取
//            if (AppContext.LoginUserMessage.messageUse) {
//                if (AppContext.LoginUserMessage.bean != null) {
//                    for (int i = 0; i < AppContext.LoginUserMessage.bean.getSystemMenus().size(); i++) {
//                        String code = AppContext.LoginUserMessage.bean.getSystemMenus().get(i).getCode();
//                        if (code.equals("6")) {
////                        if (data.getMonitors() != null) {
////                            if (data.getMonitors().size() > 0) {
////                                Monitors.setVisibility(View.VISIBLE);
////                                Monitorslayout.setVisibility(View.VISIBLE);
////                                TextMonitors.setText("视频监控");
////                            } else {
////                                Monitors.setVisibility(View.GONE);
////                            }
////                        }
//                            new LogInHk(this, savedInstanceState, data);
//                            break;
//                        }
//                    }
//                }
//            }
        } else {
            setNoCarmers();
        }
    }

    int getChildCount = 0;

    private void setNoCarmers() {
        Monitors.setVisibility(View.VISIBLE);
        if (getChildCount != 0) return;
        getChildCount = 1;
        TextView textView = new TextView(this);
        textView.setText("该企业没有摄像头");
        textView.setGravity(Gravity.CENTER);
        Monitors.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    String servAddr = "117.173.43.121:4443";
    String userName = "admin";
    String password = "heking03*12";
    String url = "pzh/GetMonitoringVideo";
    String macAddress;
    private ServInfo servInfo = new ServInfo();

    /**
     * 在主页获取,这里发现没有继续获取
     * 获取看视频的权限
     */
    private void getPremision() {
        if (TempData.getInstance().getLoginData() == null) {//没有权限数据,才进一步获取
            String json = SPUtils.init(this).getString("quanxian");
            if (!TextUtils.isEmpty(json)) {//Share里面没有,就网络请求
                TempData.getInstance().setLoginData(new Gson().fromJson(json, com.hikvision.vmsnetsdk.ServInfo.class), servAddr);
            }
            if (TempData.getInstance().getLoginData() == null) {
                HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET_ZS_SP + url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        Log.w("shipin_flow", "LogInHk 构造方法 toHttpGEtandPost  网路数据:" + json);
                        if (json != null && !json.equals("") && !json.equals("连接失败")) {
                            String message = "{\"User\":" + json + "}";
                            MonitoringVideoHK monit = ParsonJson.jsonToBeanObj(message, MonitoringVideoHK.class);
                            if (monit != null && monit.getUser().size() > 0) {
                                MonitoringVideoHK.User user = monit.getUser().get(0);
                                servAddr = user.getServAddr();
                                userName = user.getUserName();
                                password = user.getPassword();
                            }
                        }
                        Config.getIns().setServerAddr(servAddr);
                        macAddress = getMacAddr();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // handler.sendEmptyMessage(Constants.Login.SHOW_LOGIN_PROGRESS);
                                // 登录请求
                                boolean ret = VMSNetSDK.getInstance().login(servAddr, userName, password, macAddress, servInfo);//保存过来的数据
                                // LogUtils.w("shipin_flow", "视频保存的信息:" + new Gson().toJson(servInfo));
                                LogUtils.w("shipin_flow", "视频保存的信息:" + ret);
                                if (servInfo != null) {
                                    SPUtils.init(BaiDuFirmTypeActivity.this).put("quanxian", servInfo);
                                }
                                if (ret) {
                                    TempData.getInstance().setLoginData(servInfo, servAddr);
                                    //  handler.sendEmptyMessage(Constants.Login.LOGIN_SUCCESS);
                                } else {
                                    // handler.sendEmptyMessage(Constants.Login.LOGIN_FAILED);
                                }
                            }
                        }).start();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Config.getIns().setServerAddr(servAddr);
                        macAddress = getMacAddr();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // handler.sendEmptyMessage(Constants.Login.SHOW_LOGIN_PROGRESS);
                                // 登录请求
                                boolean ret = VMSNetSDK.getInstance().login(servAddr, userName, password, macAddress, servInfo);//保存过来的数据
                                // LogUtils.w("shipin_flow", "视频保存的信息:" + new Gson().toJson(servInfo));
                                LogUtils.w("shipin_flow", "视频保存的信息:" + ret);
                                if (servInfo != null) {
                                    SPUtils.init(BaiDuFirmTypeActivity.this).put("quanxian", servInfo);
                                }
                                if (ret) {
                                    TempData.getInstance().setLoginData(servInfo, servAddr);
                                    //  handler.sendEmptyMessage(Constants.Login.LOGIN_SUCCESS);
                                } else {
                                    // handler.sendEmptyMessage(Constants.Login.LOGIN_FAILED);
                                }
                            }
                        }).start();
                    }
                });
            }
        }
    }

    private void setAdpter() {//查看提前获取的所有摄像头是否存在,不存在就再加载
        String myCamer = SPUtils.init(getApplication()).getString("all_camer_daxian");
        if (AppContext.cameraInfos_hk != null) {//海康的数据获取到了就不去 对比大仙的数据
            //显示前数据处理()
            //AppContext.all_camer_daxian
            //ResourceListAdapter.mList;
            cameraInfos = new ArrayList<>();
            CameraInfo cameraInfo;
            CameraInfo toAdd = null;
            List<Integer> capability = new ArrayList<>();
            capability.add(1);
            capability.add(2);
            capability.add(3);
            capability.add(4);
            List<Integer> recordP = new ArrayList<>();
            capability.add(2);
            LogUtils.w("shipin_adpter", AppContext.cameraInfos_hk);
            for (Data.Monitors item : data.getMonitors()) {//获取的企业信息  只要这里有就要添加进列表
                for (CameraInfo item2 : AppContext.cameraInfos_hk) {//之前获取的所有摄像头
                    if (item.getModel().equals(item2.getId())) {
                        if (item2.getId().equals(item.getModel())) {
                            toAdd = item2;
                            toAdd.setName(item.getName());
                        }
                    }
                }

//                cameraInfo = new CameraInfo();
//                //实际获取到的值
//                cameraInfo.setId(toAdd == null ? null : toAdd.getId());
//                cameraInfo.setName(item.getName());
//                //自己拼接的
//                cameraInfo.setUserCapability(capability);
//                cameraInfo.setGroupID(1);
//                cameraInfo.setPTZControl(true);
//                cameraInfo.setRecordPos(recordP);
////                LogUtils.w("shipin_add", cameraInfo);
                cameraInfos.add(toAdd);
                toAdd = null;
            }
            cameraInfo = null;
            AppContext.shipins_toshow = cameraInfos;
            LogUtils.w("shipin_adpter", AppContext.shipins_toshow);
            if (cameraInfos.size() > 0) {
                Monitors.setVisibility(View.VISIBLE);
                Monitorslayout.setVisibility(View.VISIBLE);
                TextMonitors.setText("视频监控");
                adapter = new MonitorsAdapter(cameraInfos, this, true);//CameraInfo
                Monitorslayout.setAdapter(adapter);
                Monitorslayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        CameraInfo info = (CameraInfo) cameraInfos.get(position);
                        if (info == null || info.getId() == null) {
                            Log.e(Constants.LOG_TAG, "gotoLive():: fail");
                            showToast("摄像头配置不正确");
                            return;
                        }
                        Intent intent = new Intent(BaiDuFirmTypeActivity.this, LiveActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.IntentKey.CAMERA_ID, info.getId());
                        bundle.putInt("position", position);
                        bundle.putString("serAddr", Config.getIns().getServerAddr());
                        bundle.putString("mServInfo", new Gson().toJson(TempData.getIns().getLoginData()));
                        intent.putExtras(bundle);
                        TempData.getIns().setCameraInfo(info);
                        startActivity(intent);
                    }
                });
            } else {
                setNoCarmers();
            }
        } else {//对比大仙的数据
            if (AppContext.all_camer_daxian != null) {//设置 adpter
                //显示前数据处理()
                //AppContext.all_camer_daxian
                //ResourceListAdapter.mList;
                cameraInfos = new ArrayList<>();
                CameraInfo cameraInfo;
                AllCameraInfo.RowsBean toAdd = null;
                List<Integer> capability = new ArrayList<>();
                capability.add(1);
                capability.add(2);
                capability.add(3);
                capability.add(4);
                List<Integer> recordP = new ArrayList<>();
                capability.add(2);
                LogUtils.w("shipin_adpter", AppContext.all_camer_daxian);

                for (Data.Monitors item : data.getMonitors()) {//获取的企业信息  只要这里有就要添加进列表
                    for (AllCameraInfo.RowsBean item2 : AppContext.all_camer_daxian.getRows()) {//之前获取的所有摄像头
                        if (item.getModel().equals(item2.getModel())) {
                            if (item2.getModel().equals(item.getModel())) {
                                toAdd = item2;
                            }
                        }
                    }
                    cameraInfo = new CameraInfo();
                    //实际获取到的值
                    cameraInfo.setId(toAdd == null ? null : toAdd.getModel());
                    cameraInfo.setName(item.getName());
                    //自己拼接的
                    cameraInfo.setUserCapability(capability);
                    cameraInfo.setGroupID(1);
                    cameraInfo.setPTZControl(true);
                    cameraInfo.setOnline(true);
                    cameraInfo.setRecordPos(recordP);
                    LogUtils.w("shipin_add", cameraInfo);
                    cameraInfos.add(cameraInfo);
                    toAdd = null;
                }
                cameraInfo = null;
                AppContext.shipins_toshow = cameraInfos;

                if (cameraInfos.size() > 0) {
                    LogUtils.w("shipin_adpter", AppContext.shipins_toshow);
                    Monitors.setVisibility(View.VISIBLE);
                    Monitorslayout.setVisibility(View.VISIBLE);
                    TextMonitors.setText("视频监控");
                    adapter = new MonitorsAdapter(cameraInfos, this, false);//CameraInfo
                    Monitorslayout.setAdapter(adapter);
                    Monitorslayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            CameraInfo info = (CameraInfo) cameraInfos.get(position);
                            if (info == null || info.getId() == null) {
                                Log.e(Constants.LOG_TAG, "gotoLive():: fail");
                                showToast("摄像头配置不正确");
                                return;
                            }
                            Intent intent = new Intent(BaiDuFirmTypeActivity.this, LiveActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.IntentKey.CAMERA_ID, info.getId());
                            bundle.putInt("position", position);
                            bundle.putString("serAddr", Config.getIns().getServerAddr());
                            bundle.putString("mServInfo", new Gson().toJson(TempData.getIns().getLoginData()));
                            intent.putExtras(bundle);
                            TempData.getIns().setCameraInfo(info);
                            startActivity(intent);
                        }
                    });
                } else {
                    setNoCarmers();
                }
            } else {
                if (!TextUtils.isEmpty(myCamer)) {
                    AllCameraInfo info = new Gson().fromJson(myCamer, AllCameraInfo.class);
                    if (info != null) {
                        AppContext.all_camer_daxian = info;
                        //重来
                        setAdpter();
                    } else {
                        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET_ZS_SP + "Firm/GetMo?page=0&rows=999999").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(String json) {
                                AllCameraInfo all_camer = new Gson().fromJson(json, AllCameraInfo.class);
                                if (all_camer != null) {
                                    AppContext.all_camer_daxian = all_camer;
                                    SPUtils.init(getApplication()).put("all_camer_daxian", json);
                                    //重来
                                    setAdpter();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                setNoCarmers();
                            }
                        });
                    }
                }
            }
        }

    }

    Bundle savedInstanceState;
    Thread getShipin = new Thread(new Runnable() {
        @Override
        public void run() {
            new LogInHk(BaiDuFirmTypeActivity.this, savedInstanceState, data);
        }
    });

    private void getShipin() {
        getShipin.start();
    }

    private void LocationSearch() {
        TextNavigation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (TextAddress.getText().toString().trim() == null
                        || TextAddress.getText().toString().trim().equals("")) {
                    Toast.makeText(BaiDuFirmTypeActivity.this, "该企业未提供地址，尚不能提供导航", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(BaiDuFirmTypeActivity.this, BNDemoMainActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable(ROUTE_PLAN_NODE, new BNDemoMainActivity.Address(data.getLongitude(), data.getLatitude(), data.getCity(), data.getAddress()));
                    intent.putExtras(bundle1);
                    startActivity(intent);
                }
            }
        });
    }

    private void iniView() {
        sv = (ScrollView) findViewById(R.id.sv);
        sv.smoothScrollTo(0, 0);
        TextNavigation = (TextView) findViewById(R.id.Text_Navigation);
        iv_qcode = (ImageView) findViewById(R.id.iv_qcode);
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
        mXunJianData = (TextView) findViewById(R.id.firm_xunjian_message);
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
        mListLicense = (LinearLayout) findViewById(R.id.zhengzhao_layout);
        switch (data.getmRating()) {
            case 1:
                TextAnnualRating.setBackground(ContextCompat.getDrawable(BaiDuFirmTypeActivity.this, R.drawable.a));

                break;
            case 2:
                TextAnnualRating.setBackground(ContextCompat.getDrawable(BaiDuFirmTypeActivity.this, R.drawable.b));

                break;
            case 3:
                TextAnnualRating.setBackground(ContextCompat.getDrawable(BaiDuFirmTypeActivity.this, R.drawable.c));
                break;
        }
        iniData();
        LocationSearch();
        //视频查看权限
        getPremision();
        //设置adpter ,检查摄像头信息
        setAdpter();
    }

    private void iniData() {
        if (AppContext.LoginUserMessage.messageUse) {
            if (AppContext.LoginUserMessage.bean != null) {
                for (int i = 0; i < AppContext.LoginUserMessage.bean.getSystemMenus().size(); i++) {
                    String code = AppContext.LoginUserMessage.bean.getSystemMenus().get(i).getCode();
                    Log.i("BaiDuFirmType", "=-------------------------------->" + code + ":" + AppContext.LoginUserMessage.bean.getSystemMenus().get(i).getName());
                    if (code.equals("1") && data.getFirmTypeName().equals("药品经营")) {
                        mFiryTypes.setVisibility(View.VISIBLE);
                    } else if (code.equals("2") && data.getFirmTypeName().equals("食品经营")) {
                        mFiryTypes.setVisibility(View.VISIBLE);
                    } else if (code.equals("3") && data.getFirmTypeName().equals("餐饮经营")) {
                        mFiryTypes.setVisibility(View.VISIBLE);
                    } else if (code.equals("4") && data.getFirmTypeName().equals("食品生产")) {
                        mFiryTypes.setVisibility(View.VISIBLE);
                    } else if (code.equals("5") && data.getFirmTypeName().equals("药品生产")) {
                        mFiryTypes.setVisibility(View.VISIBLE);
                        //任务100 bug1276 用户登录，选择一个企业，查看企业详情，在详情页，不能查看企业巡检数据了
                    } else if (code.equals("8") && data.getFirmTypeName().equals("医疗机构")) {
                        mFiryTypes.setVisibility(View.VISIBLE);
                    }
                    if (code.equals("7")) {
                        mXunJianData.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                mFiryTypes.setVisibility(View.GONE);
                mXunJianData.setVisibility(View.GONE);
            }
        }
        mFiryTypes.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // if(AppContext.LoginUserMessage.messageUse){
                Intent intent = new Intent(BaiDuFirmTypeActivity.this, RegulatoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data);
                bundle.putInt("State", intS);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mXunJianData.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(BaiDuFirmTypeActivity.this, PatrolActivity.class);
                intent.putExtra("firmID", data.getFirmID());
                intent.putExtra("type", PatrolActivity.Type.q);
                startActivity(intent);
            }
        });

        for (int i = 0; i < data.getFirmLicenseData().size(); i++) {
            //TID:100 bug:1229 null exception
            if (data.getFirmLicenseData() == null || data.getFirmLicenseData().get(i) == null || TextUtils.isEmpty(data.getFirmLicenseData().get(i).getLicenseType()))
                continue;
            if (data.getFirmLicenseData().get(i).getLicenseType().contains("二维码")
                    || data.getFirmLicenseData().get(i).getLicenseType().contains("barcode")) {
                new HttpImage(BaiDuFirmTypeActivity.this,
                        WPConfig.IMAGE_VIEW_01 + data.getFirmLicenseData().get(i).getFileID(), null);
                data.getFirmLicenseData().remove(i);
            }
        }
        if (data.getFirmLicenseData() != null) {
            /*
             * if (data.getFirmLicenseData().size() > 0) { FirmTypeAdapter
			 * adapter = new FirmTypeAdapter( data.getFirmLicenseData(), this);
			 * mListLicense.setAdapter(adapter); }
			 */
            // else{
            // License.setVisibility(LinearLayout.GONE);
            // }

            // 证照信息列表
            mListLicense.removeAllViews();
            for (int i = 0; i < data.getFirmLicenseData().size(); i++) {
                View v = getLayoutInflater().inflate(R.layout.list_view_firm2, null);

                TextView textViews2 = (TextView) v.findViewById(R.id.file_list_textviews2);
                TextView textViews = (TextView) v.findViewById(R.id.file_list_textviews);
                ImageView iv = (ImageView) v.findViewById(R.id.iv);

                textViews.setText(data.getFirmLicenseData().get(i).getName());
                textViews2.setText(data.getFirmLicenseData().get(i).getLicenseCode());

                if (!TextUtils.isEmpty(data.getFirmLicenseData().get(i).getFileID())) {
                    iv.setVisibility(View.VISIBLE);

                    BitmapUtils bitmapUtils = new BitmapUtils(BaiDuFirmTypeActivity.this);
//					x.image().bind(iv, AppContext.Url.IMAGE_VIEW_01 + data.getFirmLicenseData().get(i).getFileID());
                    // 加载网络图片
                    bitmapUtils.display(iv,
                            WPConfig.IMAGE_VIEW_01 + data.getFirmLicenseData().get(i).getFileID());
                } else {
                    iv.setVisibility(View.GONE);
                }
                mListLicense.addView(v);
            }
        }
    }

    @Override
    public void setOnView(View view) {
        //视频信息获取后 加入视图
        //Monitorslayout.setVisibility(View.GONE);
        //   Monitors.addView(view);
    }

    @Override
    public void toBitmap(Bitmap bitmap) {
        iv_qcode.setImageBitmap(bitmap);
    }

    /**
     * 获取登录设备mac地址
     *
     * @return
     */
    protected String getMacAddr() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String mac = wm.getConnectionInfo().getMacAddress();
        return mac == null ? "" : mac;
    }
}
