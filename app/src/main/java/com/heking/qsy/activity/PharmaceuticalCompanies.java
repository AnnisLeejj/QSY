package com.heking.qsy.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.heking.SPJK.constants.Constants;
import com.heking.SPJK.data.Config;
import com.heking.SPJK.data.TempData;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.FirmShow.FiltrateMenu;
import com.heking.qsy.activity.FirmShow.FirmAdapter;
import com.heking.qsy.activity.FirmShow.MonitoringVideoHK;
import com.heking.qsy.base.BaseActivity;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.JsonFile;
import com.heking.qsy.util.MessageAddress;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;
import com.hikvision.vmsnetsdk.ServInfo;
import com.hikvision.vmsnetsdk.VMSNetSDK;

import MyUtils.LogUtils.LogUtils;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PharmaceuticalCompanies extends BaseActivity implements OnClickListener, MessageAddress {

    private RelativeLayout mArea, mFirm;

    private TextView mtArea, mtFirm, mtArea_text, mtFirm_text;

    private boolean boArea = false;
    private boolean boFirm = false;

    private boolean pop = false;
    private ScrollView mAreaView;
    private LinearLayout mFirmView, mLevelView;
    private ListView mListView;
    public ArrayList<FirmTypeBean.Data> FirmTapeDataList;
    private FirmAdapter adapter;
    private PopupWindow mPopupWindow;
    private View mView;
    private Handler mHandler = new Handler();
    private FiltrateMenu mFiltrateMenu;
    private SwipeRefreshLayout srl;
    private int onClickDate = 0;

    String firmType = "全部企业";// 企业类型
    String address = "全部区域";

    protected void onCreate(Bundle savedInstanceState) {
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pharmaceutical_companies_activity);
        iniView();
        iniData();

        getServInfo();
    }

    String url = "pzh/GetMonitoringVideo";
    String servAddr = "117.173.43.121:4443";
    String userName = "admin";
    String password = "heking03*12";
    String macAddress;
    private ServInfo servInfo = new ServInfo();

    private void getServInfo() {
        // servInfo保存
        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET_ZS_SP + url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
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
                        // 登录请求
                        boolean ret = VMSNetSDK.getInstance().login(servAddr, userName, password, macAddress, servInfo);//保存过来的数据
                        // LogUtils.w("shipin_flow", "视频保存的信息:" + new Gson().toJson(servInfo));
                        LogUtils.w("shipin_flow", "视频保存的信息:" + ret);
                        if (ret) {
                            TempData.getInstance().setLoginData(servInfo, servAddr);

                        } else {

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
                        // 登录请求
                        boolean ret = VMSNetSDK.getInstance().login(servAddr, userName, password, macAddress, servInfo);//保存过来的数据
                        // LogUtils.w("shipin_flow", "视频保存的信息:" + new Gson().toJson(servInfo));
                        LogUtils.w("shipin_flow", "视频保存的信息:" + ret);
                        if (ret) {
                            TempData.getInstance().setLoginData(servInfo, servAddr);

                        } else {

                        }
                    }
                }).start();
            }
        });

    }

    SingleObserver singleObserver = new SingleObserver<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(String json) {
            LogUtils.w("json", "json:" + json);
            srl.setRefreshing(false);
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
            File ss = new File(file2 + "/FirmTypeBean.dll");
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
            if (JsonData.equals("连接失败")) {
                showToast("网络异常");
                return;
            }

            FirmTypeBean bean = ParsonJson.jsonToBeanObj(JsonData, FirmTypeBean.class);
            if (bean != null && bean.getData() != null) {
                AppContext.List.FirmTapeDataList = bean.getData();
                FirmTapeDataList.clear();
                FirmTapeDataList.addAll(getData(AppContext.List.FirmTapeDataList));
                updateList();
            } else {
                showToast("网络异常");
            }
        }

        @Override
        public void onError(Throwable e) {

        }
    };


    private void iniView() {
        findViewById(R.id.textview).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                finish();

            }
        });

        mArea = (RelativeLayout) findViewById(R.id.area_text_view_button);
        mFirm = (RelativeLayout) findViewById(R.id.firm_text_view_button);

        mtArea = (TextView) findViewById(R.id.area_text_view_button_img);
        mtFirm = (TextView) findViewById(R.id.firm_text_view_button_img);

        mtArea_text = (TextView) findViewById(R.id.area_text_view_button_text);
        mtFirm_text = (TextView) findViewById(R.id.firm_text_view_button_text);
        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        srl.setColorScheme(R.color.tou_su_button);
        mListView = (ListView) findViewById(R.id.food_listView);

        mFiltrateMenu = new FiltrateMenu(LayoutInflater.from(this).inflate(R.layout.popwindows, null), this, this);
        mView = mFiltrateMenu.getView();
        mFiltrateMenu.setName("药品生产", "药品流通");
        mAreaView = (ScrollView) mView.findViewById(R.id.area_list_view_layout);
        mFirmView = (LinearLayout) mView.findViewById(R.id.firm_list_view);
        mLevelView = (LinearLayout) mView.findViewById(R.id.Level_list_view);

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.GET_FIRM_TYPE +
                        AppContext.Parameter.ALL).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(singleObserver);
            }
        });
    }

    private void iniData() {
        FirmTapeDataList = new ArrayList<FirmTypeBean.Data>();
        adapter = new FirmAdapter(FirmTapeDataList, PharmaceuticalCompanies.this, 3333);
        mListView.setAdapter(adapter);
        if (AppContext.List.FirmTapeDataList != null) {
            FirmTapeDataList.addAll(getData(AppContext.List.FirmTapeDataList));
            adapter.notifyDataSetChanged();
        } else {
            HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.GET_FIRM_TYPE +
                    AppContext.Parameter.ALL).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(singleObserver);
        }
        mArea.setOnClickListener(this);
        mFirm.setOnClickListener(this);
        mFiltrateMenu.setOnClickListener(listener);
    }

    /**
     * PoputWindows 菜单
     *
     * @param arg0     展开按钮
     * @param mPopView 视图View
     * @param view     更随视图
     */
    public void showPopwindow(final int arg0, View mPopView, View view) {
        switch (arg0) {
            case 99901:
                mAreaView.setVisibility(LinearLayout.VISIBLE);
                mFirmView.setVisibility(LinearLayout.GONE);
                mLevelView.setVisibility(LinearLayout.GONE);
                break;
            case 99902:
                mAreaView.setVisibility(LinearLayout.GONE);
                mFirmView.setVisibility(LinearLayout.VISIBLE);
                mLevelView.setVisibility(LinearLayout.GONE);
                break;
            case 99903:
                mAreaView.setVisibility(LinearLayout.GONE);
                mFirmView.setVisibility(LinearLayout.GONE);
                mLevelView.setVisibility(LinearLayout.VISIBLE);
                break;
        }
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            // 设置一个空白的背景
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            // 点击屏幕可消失
            mPopupWindow.setOutsideTouchable(true);
            // 获得焦点
            mPopupWindow.setFocusable(false);
            // 设置pop消失的监听事件
            mPopupWindow.setOnDismissListener(new OnDismissListener() {

                public void onDismiss() {
                    mHandler.postDelayed(new Runnable() {

                        public void run() {
                            mArea.setEnabled(true);
                            mFirm.setEnabled(true);
                            pop = false;
                            if (AppContext.THEME) {
                                switch (onClickDate) {
                                    case 1:
                                        mtArea.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_3));
                                        mtFirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_1));
                                        break;
                                    case 2:
                                        mtArea.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_1));
                                        mtFirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_3));
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                mtArea.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_2));
                                mtFirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_2));
                            }
                            boArea = false;
                            boFirm = false;
                        }
                    }, 100);
                }
            });
        }
        mPopupWindow.showAsDropDown(view);
        // //设置按钮不可以点击
        switch (arg0) {
            case 99901:
                mArea.setEnabled(false);
                break;
            case 99902:
                mFirm.setEnabled(false);
                break;
        }
    }

    protected void onPause() {
        super.onPause();
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    // 将评分最高的展示在最前
    private ArrayList<FirmTypeBean.Data> Sort(ArrayList<FirmTypeBean.Data> datalist) {
        for (int i = 0; i < datalist.size(); i++) {
            for (int j = i; j < datalist.size(); j++) {
                FirmTypeBean.Data data1 = datalist.get(i);
                FirmTypeBean.Data data2 = datalist.get(j);
                if (data1.getmRating() > data2.getmRating()) {
                    datalist.set(i, data2);
                    datalist.set(j, data1);
                }
            }
        }
        return datalist;
    }

    private ArrayList<FirmTypeBean.Data> getData(ArrayList<FirmTypeBean.Data> datalist) {
        ArrayList<FirmTypeBean.Data> datas = new ArrayList<FirmTypeBean.Data>();
        for (FirmTypeBean.Data data : datalist) {
            if (data.getFirmTypeName().trim().contains("药品经营") || data.getFirmTypeName().trim().contains("药品生产") || data.getFirmTypeName().trim().contains("医疗机构")) {
                if (data.getMonitors() != null && data.getMonitors().size() > 0) {
                    data.setIoos(true);
                } else {
                    data.setIoos(false);
                }
                if (data.getAnnualRating() != null && data.getAnnualRating().size() > 1) {
                    if (data.getAnnualRating().get(0).getRating() != null
                            && !data.getAnnualRating().get(0).getRating().trim().equals("")) {
                        if (data.getAnnualRating().get(0).getRating().trim().equals("优秀")) {
                            data.setmRating(1);
                        }
                        if (data.getAnnualRating().get(0).getRating().trim().equals("良好")) {
                            data.setmRating(2);
                        }
                        if (data.getAnnualRating().get(0).getRating().trim().equals("一般")) {
                            data.setmRating(3);
                        }
                        if (data.getAnnualRating().get(0).getRating().trim().equals("不予评级")) {
                            data.setmRating(4);
                        }
                    }
                } else {
                    data.setmRating(4);
                }
                datas.add(data);
            }
        }
        return Sort(datas);
    }

    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.area_text_view_button:
                if (pop) {
                    pop = false;
                    mPopupWindow.dismiss();
                } else {
                    pop = true;
                    if (AppContext.THEME) {
                        if (boArea) {
                            mtArea.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_3));
                            boArea = false;
                        } else {
                            boArea = true;
                            mtArea.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_2));
                        }
                    } else {
                        if (boArea) {
                            mtArea.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_2));
                            boArea = false;
                        } else {
                            boArea = true;
                            mtArea.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_2));
                        }
                    }
                    onClickDate = 1;
                    mtArea_text.setTextColor(Color.parseColor("#3cafdf"));
                    mtFirm_text.setTextColor(Color.parseColor("#808080"));
                    mtFirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_1));
                    showPopwindow(99901, mView, mArea);
                }
                break;
            case R.id.firm_text_view_button:
                if (pop) {
                    pop = false;
                    mPopupWindow.dismiss();
                } else {
                    if (AppContext.THEME) {
                        if (boFirm) {
                            mtFirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_3));
                            boFirm = false;
                        } else {
                            boFirm = true;
                            mtFirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_2));
                        }
                    } else {
                        if (boFirm) {
                            mtFirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_2));
                            boFirm = false;
                        } else {
                            boFirm = true;
                            mtFirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_2));
                        }
                    }
                    onClickDate = 2;
                    mtArea_text.setTextColor(Color.parseColor("#808080"));
                    mtFirm_text.setTextColor(Color.parseColor("#3cafdf"));
                    mtArea.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_firm_1));
                    showPopwindow(99902, mView, mFirm);
                    pop = true;
                }
                break;
        }
    }

    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            mFiltrateMenu.toview(arg0.getId());
            switch (arg0.getId()) {
                case R.id.firm_list_site_all_firm_view:
                    mtFirm_text.setText("全部企业");
                    firmType = "全部企业";
                    updateList();
                    mPopupWindow.dismiss();
                    break;
                case R.id.firm_list_site_monitoring_cy_view:
                    mtFirm_text.setText("医疗机构");
                    firmType = "医疗机构";
                    updateList();
                    mPopupWindow.dismiss();
                    break;
                case R.id.firm_list_site_monitoring_view:
                    mtFirm_text.setText("药品生产");
                    firmType = "药品生产";
                    updateList();
                    mPopupWindow.dismiss();
                    break;
                case R.id.firm_list_site_monitoring_lt_view:
                    mtFirm_text.setText("药品流通");
                    firmType = "药品经营";
                    updateList();
                    mPopupWindow.dismiss();
                    break;
            }
        }
    };

    @Override
    public void MassageData(String arg0, int ID) {
        mtArea_text.setText(arg0);
        address = arg0;
        updateList();
        mPopupWindow.dismiss();
    }

    // 列表选择过滤
    private void updateList() {
        if (AppContext.List.FirmTapeDataList == null || AppContext.List.FirmTapeDataList.size() <= 0) {
            return;
        }
        if (FirmTapeDataList != null) {
            FirmTapeDataList.clear();
            FirmTapeDataList.addAll(getData(AppContext.List.FirmTapeDataList));
            Iterator<FirmTypeBean.Data> iter = FirmTapeDataList.iterator();
            while (iter.hasNext()) {
                FirmTypeBean.Data data = iter.next();
                Log.i("FoodCompanies", "updateList: 地点为+" + data.getAreaName());
                boolean bFirmType = firmType.contains("全部企业") || data.getFirmTypeName().contains(firmType)
                        || data.getFirmTypeName1().contains(firmType);
                boolean bAddress = false;
                if (data.getAreaName() != null) {
                    bAddress = address.contains("全部区域") || data.getAreaName().contains(address) ? true : false;
                }
                // boolean bAddress = address.contains("全部区域")
                // || data.getAreaName().contains(address) ? true : false;
                if (!bFirmType || !bAddress) {
                    iter.remove();
                }
            }
            adapter.notifyDataSetChanged();
        }
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
