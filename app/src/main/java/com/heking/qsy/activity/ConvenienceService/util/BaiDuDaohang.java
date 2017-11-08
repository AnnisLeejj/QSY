package com.heking.qsy.activity.ConvenienceService.util;

import java.io.File;

import android.Manifest;
import android.annotation.SuppressLint;
import com.heking.qsy.base.BaseActivity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class BaiDuDaohang extends BaseActivity {


//    private static final String APP_FOLDER_NAME = "BNSDKSimpleDemo";
//
//    private String mSDCardPath = null;
//
//    public static final String ROUTE_PLAN_NODE = "routePlanNode";
//    public static final String SHOW_CUSTOM_ITEM = "showCustomItem";
//    public static final String RESET_END_NODE = "resetEndNode";
//    public static final String VOID_MODE = "voidMode";
//
//    private final static String authBaseArr[] = { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION };
//    private final static String authComArr[] = { Manifest.permission.READ_PHONE_STATE };
//    private final static int authBaseRequestCode = 1;
//    private final static int authComRequestCode = 2;
//
//    private boolean hasInitSuccess = false;
//    private boolean hasRequestComAuth = false;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        BNOuterLogUtil.setLogSwitcher(true);
//        if (initDirs()) {
//            initNavi();
//        }
//
//        // BNOuterLogUtil.setLogUtils.witcher(true);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//
//    private boolean initDirs() {
//        mSDCardPath = getSdcardDir();
//        if (mSDCardPath == null) {
//            return false;
//        }
//        File f = new File(mSDCardPath, APP_FOLDER_NAME);
//        if (!f.exists()) {
//            try {
//                f.mkdir();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//        return true;
//    }
//
//    String authinfo = null;
//
//    /**
//     * 内部TTS播报状态回传handler
//     */
//    private Handler ttsHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            int type = msg.what;
//            switch (type) {
//                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
//                    // showToastMsg("Handler : TTS play start");
//                    break;
//                }
//                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
//                    // showToastMsg("Handler : TTS play end");
//                    break;
//                }
//                default:
//                    break;
//            }
//        }
//    };
//
//    /**
//     * 内部TTS播报状态回调接口
//     */
//    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {
//
//        @Override
//        public void playEnd() {
//            // showToastMsg("TTSPlayStateListener : TTS play end");
//        }
//
//        @Override
//        public void playStart() {
//            // showToastMsg("TTSPlayStateListener : TTS play start");
//        }
//    };
//
//    public void showToastMsg(final String msg) {
//        BaiDuDaohang.this.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                Toast.makeText(BaiDuDaohang.this, msg, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private boolean hasBasePhoneAuth() {
//
//        PackageManager pm = this.getPackageManager();
//        for (String auth : authBaseArr) {
//            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean hasCompletePhoneAuth() {
//
//        PackageManager pm = this.getPackageManager();
//        for (String auth : authComArr) {
//            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @SuppressLint("NewApi") private void initNavi() {
//
//        BNOuterTTSPlayerCallback ttsCallback = null;
//
//        // 申请权限
//        if (android.os.Build.VERSION.SDK_INT >= 23) {
//
//            if (!hasBasePhoneAuth()) {
//
//            	  this.requestPermissions(authBaseArr, authBaseRequestCode);
//
//                return;
//
//            }
//        }
//
//        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new NaviInitListener() {
//            @Override
//            public void onAuthResult(int status, String msg) {
//                if (0 == status) {
//                    authinfo = "key校验成功!";
//                } else {
//                    authinfo = "key校验失败, " + msg;
//                }
//                BaiDuDaohang.this.runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Toast.makeText(BaiDuDaohang.this, authinfo, Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//
//            public void initSuccess() {
//                Toast.makeText(BaiDuDaohang.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
//                hasInitSuccess = true;
//
//                initSetting();
//                routeplanToNavi(CoordinateType.BD09_MC);
//            }
//
//            public void initStart() {
//                Toast.makeText(BaiDuDaohang.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
//            }
//
//            public void initFailed() {
//                Toast.makeText(BaiDuDaohang.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
//            }
//
//        }, null, ttsHandler, ttsPlayStateListener);
//
//    }
//
//    private String getSdcardDir() {
//        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
//            return Environment.getExternalStorageDirectory().toString();
//        }
//        return null;
//    }
//
//    private CoordinateType mCoordinateType = null;
//
//    @SuppressLint("NewApi") private void routeplanToNavi(CoordinateType coType) {
//        mCoordinateType = coType;
//        if (!hasInitSuccess) {
//            Toast.makeText(BaiDuDaohang.this, "还未初始化!", Toast.LENGTH_SHORT).show();
//        }
//        // 权限申请
//        if (android.os.Build.VERSION.SDK_INT >= 23) {
//            // 保证导航功能完备
//            if (!hasCompletePhoneAuth()) {
//                if (!hasRequestComAuth) {
//                    hasRequestComAuth = true;
//                    this.requestPermissions(authComArr, authComRequestCode);
//                    return;
//                } else {
//                    Toast.makeText(BaiDuDaohang.this, "没有完备的权限!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        }
//        BNRoutePlanNode sNode = null;
//        BNRoutePlanNode eNode = null;
//        switch (coType) {
//            case GCJ02: {
//                sNode = new BNRoutePlanNode(116.30142, 40.05087, "百度大厦", null, coType);
//                eNode = new BNRoutePlanNode(116.39750, 39.90882, "北京天安门", null, coType);
//                break;
//            }
//            case WGS84: {
//                sNode = new BNRoutePlanNode(116.300821, 40.050969, "百度大厦", null, coType);
//                eNode = new BNRoutePlanNode(116.397491, 39.908749, "北京天安门", null, coType);
//                break;
//            }
//            case BD09_MC: {
//                sNode = new BNRoutePlanNode(12947471, 4846474, "百度大厦", null, coType);
//                eNode = new BNRoutePlanNode(12958160, 4825947, "北京天安门", null, coType);
//                break;
//            }
//            case BD09LL: {
//                sNode = new BNRoutePlanNode(116.30784537597782, 40.057009624099436, "百度大厦", null, coType);
//                eNode = new BNRoutePlanNode(116.40386525193937, 39.915160800132085, "北京天安门", null, coType);
//                break;
//            }
//            default:
//                ;
//        }
//        if (sNode != null && eNode != null) {
//            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
//            list.add(sNode);
//            list.add(eNode);
//            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
//        }
//    }
//
//    public class DemoRoutePlanListener implements RoutePlanListener {
//
//        private BNRoutePlanNode mBNRoutePlanNode = null;
//
//        public DemoRoutePlanListener(BNRoutePlanNode node) {
//            mBNRoutePlanNode = node;
//        }
//
//        @Override
//        public void onJumpToNavigator() {
//            /*
//             * 设置途径点以及resetEndNode会回调该接口
//             */
//
//
//        }
//
//        @Override
//        public void onRoutePlanFailed() {
//            Toast.makeText(BaiDuDaohang.this, "算路失败", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void initSetting() {
//        // BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
//        BNaviSettingManager
//                .setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
//        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
//        // BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
//        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
//    }
//
//    private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {
//
//        @Override
//        public void stopTTS() {
//            Log.e("test_TTS", "stopTTS");
//        }
//
//        @Override
//        public void resumeTTS() {
//            Log.e("test_TTS", "resumeTTS");
//        }
//
//        @Override
//        public void releaseTTSPlayer() {
//            Log.e("test_TTS", "releaseTTSPlayer");
//        }
//
//        @Override
//        public int playTTSText(String speech, int bPreempt) {
//            Log.e("test_TTS", "playTTSText" + "_" + speech + "_" + bPreempt);
//
//            return 1;
//        }
//
//        @Override
//        public void phoneHangUp() {
//            Log.e("test_TTS", "phoneHangUp");
//        }
//
//        @Override
//        public void phoneCalling() {
//            Log.e("test_TTS", "phoneCalling");
//        }
//
//        @Override
//        public void pauseTTS() {
//            Log.e("test_TTS", "pauseTTS");
//        }
//
//        @Override
//        public void initTTSPlayer() {
//            Log.e("test_TTS", "initTTSPlayer");
//        }
//
//        @Override
//        public int getTTSState() {
//            Log.e("test_TTS", "getTTSState");
//            return 1;
//        }
//    };
//
//    @SuppressLint("NewApi") @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == authBaseRequestCode) {
//            for (int ret : grantResults) {
//                if (ret == 0) {
//                    continue;
//                } else {
//                    Toast.makeText(BaiDuDaohang.this, "缺少导航基本的权限!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
//            initNavi();
//        } else if (requestCode == authComRequestCode) {
//            for (int ret : grantResults) {
//                if (ret == 0) {
//                    continue;
//                }
//            }
//            routeplanToNavi(mCoordinateType);
//        }
//
//    }
}
