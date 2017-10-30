package com.heking.qsy.activity.FirmShow;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.heking.qsy.AppContext;
import com.heking.qsy.activity.FirmShow.MonitoringVideoHK.User;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.SPJK.constants.Constants;
import com.heking.SPJK.data.Config;
import com.heking.SPJK.data.TempData;
import com.heking.SPJK.resource.ResourceListActivity;
import com.heking.snoa.WPConfig;
import com.hikvision.vmsnetsdk.ServInfo;
import com.hikvision.vmsnetsdk.VMSNetSDK;

import MyUtils.LogUtils.LogUtils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LogInHk {
    private String servAddr = "117.173.43.121:4443";
    private String userName = "admin";
    //	private String password="Hik12345";
/*	private String password="heking11*29";*/
    private String password = "heking03*12";
    private String macAddress = "";
    private ServInfo servInfo = new ServInfo();
    private Context context;
    private Bundle bundle;

    private OnViewData onViewData;
    private FirmTypeBean.Data Firmdata;
    private String url = "pzh/GetMonitoringVideo";

    @SuppressWarnings("deprecation")
    private LocalActivityManager mLocalActivityManager;

    Handler handler = new

            Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case Constants.Login.LOGIN_SUCCESS:
                            // 登录成功
                            onViewData.setOnView(onLoginSuccess());
                            break;
                        case Constants.Login.LOGIN_FAILED:
                            // 登录成功
                            Toast.makeText(context, "视频获取失败，请检查网络后重试", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                }
            };

    public LogInHk(final Context context, final Bundle bundle, final FirmTypeBean.Data Firmdata) {
        Log.w("shipin_flow", "LogInHk 构造方法");
        this.Firmdata = Firmdata;
        this.bundle = bundle;
        this.context = context;
        this.onViewData = (OnViewData) context;
        mLocalActivityManager = new LocalActivityManager((Activity) context, true);
        mLocalActivityManager.dispatchCreate(bundle);

        if (TempData.getInstance().getLoginData() != null) {
            handler.sendEmptyMessage(Constants.Login.LOGIN_SUCCESS);
        } else {
            HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
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
                            User user = monit.getUser().get(0);
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
                            handler.sendEmptyMessage(Constants.Login.SHOW_LOGIN_PROGRESS);
                            // 登录请求
                            boolean ret = VMSNetSDK.getInstance().login(servAddr, userName, password, macAddress, servInfo);//保存过来的数据
                            // LogUtils.w("shipin_flow", "视频保存的信息:" + new Gson().toJson(servInfo));
                            LogUtils.w("shipin_flow", "视频保存的信息:" + ret);
                            if (ret) {
                                TempData.getInstance().setLoginData(servInfo, servAddr);
                                handler.sendEmptyMessage(Constants.Login.LOGIN_SUCCESS);
                            } else {
                                handler.sendEmptyMessage(Constants.Login.LOGIN_FAILED);
                            }
                        }
                    }).start();
                }

                @Override
                public void onError(Throwable e) {
                }
            });
        }
    }
    //删除可抽取代码

    /**
     * 登录成功
     */

    public View onLoginSuccess() {
        // 跳转到获取控制中心列表界面
        // gotoResourceListActivity();
        Intent intent = new Intent(context, ResourceListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("FirmType", Firmdata);
        intent.putExtras(bundle);
        return mLocalActivityManager.startActivity("ResourceListActivity", intent).getDecorView();
    }

    /**
     * 获取登录设备mac地址
     *
     * @return
     */
    protected String getMacAddr() {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String mac = wm.getConnectionInfo().getMacAddress();
        return mac == null ? "" : mac;
    }


}