package com.heking.qsy;

import com.google.gson.Gson;
import com.heking.SPJK.data.Config;
import com.heking.SPJK.data.TempData;
import com.heking.qsy.Dialog.CustomDialog;
import com.heking.qsy.Model.AllCameraInfo;
import com.heking.qsy.activity.FirmShow.MonitoringVideoHK;
import com.heking.qsy.activity.ForTheConvenienceOfService;
import com.heking.qsy.activity.Personalcenter;
import com.heking.qsy.activity.Patrol.PatrolFragment;
import com.heking.qsy.activity.Personalcenters.util.RegisteredBean;
import com.heking.qsy.activity.Personalcenters.util.VerNameBean;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;
import com.hikvision.vmsnetsdk.ServInfo;
import com.hikvision.vmsnetsdk.VMSNetSDK;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import MyUtils.LogUtils.LogUtils;
import MyUtils.SharePrefenceUtils.SPUtils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener {
    RadioGroup rg;
    RadioButton rbHome, rbMy, rbbianmin;
    HomeFragment homeFragment;
    ForTheConvenienceOfService convenienceOfService;
    Personalcenter personalcenter;
    PatrolFragment patrolFragment;//巡检
    int mIndex = -1;
    Fragment[] mFragments;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction ft;

    private Context context = this;
    private ProgressDialog mProgress;
    private long refernece;
    SharedPreferences preference;
    public static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
    RadioButton rbxunjian;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT > 22) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "没有权限,请手动开启存储权限", Toast.LENGTH_SHORT).show();
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        rbxunjian = (RadioButton) findViewById(R.id.rbxunjian);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        if (convenienceOfService == null) {
            convenienceOfService = new ForTheConvenienceOfService();
        }
        if (patrolFragment == null) {
            patrolFragment = new PatrolFragment();
        }
        if (personalcenter == null) {
            personalcenter = new Personalcenter();
        }
        mFragments = new Fragment[]{homeFragment, convenienceOfService, patrolFragment, personalcenter};

        iniView();
        iniData();
        setCamerS();
    }

    String servAddr = "117.173.43.121:4443";
    String userName = "admin";
    String password = "heking03*12";
    String url = "pzh/GetMonitoringVideo";
    String macAddress;
    private ServInfo servInfo = new ServInfo();

    private void setCamerS() {
        //如果有先拿出来
        String myCamer = SPUtils.init(getApplication()).getString("all_camer");
        if (!TextUtils.isEmpty(myCamer)) {
            AppContext.all_camer = new Gson().fromJson(myCamer, AllCameraInfo.class);
        }
        //就算有也要在获取,以防数据更新
        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET_ZS_SP + "Firm/GetMo?page=0&rows=999999").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String json) {
                if (TextUtils.isEmpty(json) || json.equals("连接失败")) return;
                LogUtils.w(json);
                AllCameraInfo all_camer = new Gson().fromJson(json, AllCameraInfo.class);
                if (all_camer != null) {
                    AppContext.all_camer = all_camer;
                    SPUtils.init(getApplication()).put("all_camer", json);
                    LogUtils.w("shipin", json);
                }
            }

            @Override
            public void onError(Throwable e) {
            }
        });
        //获取权限
        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET_ZS_SP + url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String json) {
                Log.w("shipin_flow", "LogInHk 构造方法 toHttpGEtandPost  网路数据:" + json);
                if (!TextUtils.isEmpty(json) && !json.equals("连接失败")) {
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
                            SPUtils.init(MainActivity.this).put("quanxian", servInfo);
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
            }
        });
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

    private void iniView() {
        rg = (RadioGroup) findViewById(R.id.rg);
        rbHome = (RadioButton) findViewById(R.id.rbHome);
        rbMy = (RadioButton) findViewById(R.id.rbMy);
        rbbianmin = (RadioButton) findViewById(R.id.rbbianmin);
        rg.setOnCheckedChangeListener(this);
        rg.check(R.id.rbHome);
        preference = getSharedPreferences("initialize", Activity.MODE_PRIVATE);
    }

    private void iniData() {
        //任务100 bug1201 判定是否已登录
        if (!TextUtils.isEmpty(preference.getString("user", "")) && !TextUtils.isEmpty(preference.getString("password", ""))) {
            if (preference.getBoolean("isLogin", false)) {
                Map<String, String> datamap = new HashMap<String, String>();
                String user = preference.getString("user", "");
                String password = preference.getString("password", "");
                datamap.put("LoginName", user);
                datamap.put("Password", password);
                datamap.put("ProductKey", "F9EC1B7E-3153-4157-83EC-DDB00AB31666");
                datamap.put("LoginStyle", "MOBILE");
                datamap.put("AreaCode", AppContext.BundelPoiMess.Address);
                datamap.put("LoginDevice", android.os.Build.MODEL);
                Tool.toHttpGEtandPost(WPConfig.PERSONAL_CENTERS
                                + AppContext.Submit.LOGIN, "POST", datamap, new JSONdata() {
                            @Override
                            public void httpResponse(String json) {
                                Log.i(TAG, "httpResponse: " + "我运行到这来了！");
                            }
                        }, null,
                        true);
            }
        }
        if (preference.getBoolean("isLogin", false) && !TextUtils.isEmpty(preference.getString("jsonData", ""))) {
            String json = preference.getString("jsonData", "");
            RegisteredBean bean1 = ParsonJson.jsonToBeanObj(json,
                    RegisteredBean.class);
            AppContext.LOGIN = true;
            AppContext.LoginUserMessage.bean = bean1;
            AppContext.LoginUserMessage.messageUse = true;
        }
        // 检查更新
        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.GET_VERSION).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String json) {
                String jsons = "{\"Data\":" + json + "}";
                if (!json.trim().equals("连接失败")) {
                    final VerNameBean beans = ParsonJson.jsonToBeanObj(jsons, VerNameBean.class);
                    String code = Tool.getVerCode(MainActivity.this) + "";
                    if (beans != null && beans.getData() != null && beans.getData().size() > 0) {
                        CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
                        builder.setMessage("\t\t您需要更新么?");
                        builder.setTitle("温馨提示");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                downNewApk(beans.getData().get(0).getVersionFileUrl());
                            }
                        });
                        builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean showXunjian = false;
        if (preference.getBoolean("isLogin", false)) {
            if (AppContext.LoginUserMessage.messageUse) {
                if (AppContext.LoginUserMessage.bean != null) {
                    for (int i = 0; i < AppContext.LoginUserMessage.bean.getSystemMenus().size(); i++) {
                        String code = AppContext.LoginUserMessage.bean.getSystemMenus().get(i).getCode();
                        if (code.equals("7")) {
                            showXunjian = true;
                            break;
                        }
                    }
                }
            }
        }
        rbxunjian.setVisibility(showXunjian ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbHome:
                setIndexSelected(0);
                break;
            case R.id.rbbianmin:
                setIndexSelected(1);
                break;
            case R.id.rbxunjian:
                setIndexSelected(2);
                break;
            case R.id.rbMy:
                setIndexSelected(3);
                break;
            default:
                break;
        }
    }

    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        ft = fragmentManager.beginTransaction();
        //任务100 bug1237 【巡检】登录APP，进入巡检后点击首页，页面布局错乱
        for (int i = 0; i < mFragments.length; i++) {
            if (!mFragments[i].isAdded()) {
                ft.add(R.id.main_layout, mFragments[i]);
            }
            ft.hide(mFragments[i]);
        }
//        if (!mFragments[index].isAdded())
//            ft.add(R.id.main_layout, mFragments[index]);
//        else {
//            ft.show(mFragments[index]);
//        }
        ft.show(mFragments[index]);
        ft.commit();
        // 再次赋值
        mIndex = index;
    }

    public void downNewApk(String url) {

        DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        // 设置下载路径和文件名
        int idx = url.lastIndexOf("/");
        String apkName = url.substring(idx + 1);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, apkName);
        request.setDescription("新版本下载");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("application/vnd.android.package-archive");
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        // 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        refernece = dManager.enqueue(request);

        // 把当前下载的ID保存起来
        SharedPreferences sPreferences = context.getSharedPreferences("downloadcomplete", 0);
        sPreferences.edit().putLong("refernece", refernece).commit();

        DownloadChangeObserver observer = new DownloadChangeObserver(new Handler());
        context.getContentResolver().registerContentObserver(CONTENT_URI, true, observer);
    }

    // 用于显示下载进度
    class DownloadChangeObserver extends ContentObserver {

        public DownloadChangeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(refernece);
            DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            final Cursor cursor = dManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                final int fileSizeIdx = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                final int bytesDLIdx = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                new Handler().post(new Runnable() {
                    public void run() {
                        Log.i("text", "" + cursor.getInt(bytesDLIdx) + "/" + cursor.getInt(fileSizeIdx));
                        UpdateDownloadProgress(cursor.getInt(bytesDLIdx), cursor.getInt(fileSizeIdx));
                    }
                });
            }
        }
    }

    private void UpdateDownloadProgress(int cur, int total) {
        if (mProgress == null) {
            mProgress = new ProgressDialog(context);
            mProgress.setIcon(R.drawable.logo_yaozhentong_qw);
            mProgress.setTitle("更新。。。");
            mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgress.setCancelable(false);
            mProgress.setCanceledOnTouchOutside(false);
            mProgress.setProgressNumberFormat("%dM/%dM");
            mProgress.show();
        }
        mProgress.setMax(total / 1024 / 1024);
        mProgress.setProgress(cur / 1024 / 1024);
    }
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.d(TAG, "onAttachFragment：" + fragment.toString() + "还没有销毁掉");

        if (homeFragment == null && fragment instanceof HomeFragment) {
            homeFragment = (HomeFragment) fragment;
        } else if (convenienceOfService == null && fragment instanceof ForTheConvenienceOfService) {
            convenienceOfService = (ForTheConvenienceOfService) fragment;
        } else if (patrolFragment == null && fragment instanceof PatrolFragment) {
            patrolFragment = (PatrolFragment) fragment;
        } else if (personalcenter == null && fragment instanceof Personalcenter) {
            personalcenter = (Personalcenter) fragment;
        }
    }

    long timeRecord;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long curent = System.currentTimeMillis();
            if (curent - timeRecord < 3000) {
                finish();
            } else {
                Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            }
            timeRecord = curent;
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}