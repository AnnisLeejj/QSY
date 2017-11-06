package com.heking.qsy.activity.ConvenienceService.util;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.navisdk.adapter.BNOuterLogUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;
import com.google.gson.Gson;
import com.heking.qsy.R;
import com.heking.qsy.base.BaseActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import MyUtils.LogUtils.LogUtils;

//TID:100 地图更换成百度地图
public class BNDemoMainActivity extends BaseActivity {
    public static List<Activity> activityList = new LinkedList();
    /**
     * App在SD卡中的目录名
     */
    private static final String APP_FOLDER_NAME = "intvehapp";
    /**
     * SD卡的路径
     */
    private String mSDCardPath = null;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    public static final String SHOW_CUSTOM_ITEM = "showCustomItem";
    public static final String RESET_END_NODE = "resetEndNode";
    public static final String VOID_MODE = "voidMode";
    Address endAddress;//导航的目的地  {"Address":"学府街83号","Delete":"false","ID":"2","Name":"犍为县食品药品监督管理局"}
    private ImageView icon;
    private TextView content;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityList.add(this);
        setContentView(R.layout.activitiy_guide_view);
        if (Build.VERSION.SDK_INT > 22) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show();
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                ActivityCompat.requestPermissions(BNDemoMainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
            } else {
                init();
            }
        } else {
            init();
        }
    }

    public void init() {
        endAddress = (Address) getIntent().getSerializableExtra(ROUTE_PLAN_NODE);
        if (this.endAddress == null) {
            throw new NullPointerException("请传入 BNDemoMainActivity.Address.class 对象");
        } else if ((endAddress.latitude == 0 || endAddress.longitude == 0) && TextUtils.isEmpty(endAddress.address)) {
            throw new RuntimeException("请传入 经纬度 或者 地址");
        }

        LogUtils.w("map", "准备导航:" + new Gson().toJson(endAddress));

        showDialog("正在计算...");
        BNOuterLogUtil.setLogSwitcher(true);
        /**
         * 初始化按钮监听函数
         */
        if (initDirs()) {
            /**
             * 使用SDK前，先进行百度服务授权和引擎初始化。
             */
            LogUtils.w("map","111111111111111111111");
            initNavi();
        }else{
            LogUtils.w("map","11111111111222222222222");
        }
    }

    private void setAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this,
                R.anim.loading_animation);
        this.icon.startAnimation(animation);
    }

    void showDialog(String message) {
        this.icon = ((ImageView) findViewById(R.id.dialog_wait_icon));
        this.content = ((TextView) findViewById(R.id.dialog_wait_content));
        setAnimation();
        this.content.setText(message);
    }

    /**
     * 获取当前位置
     */
    private static final int BAIDU_READ_PHONE_STATE = 100;
    LocationClient mLocationClient = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void getLocal() {
        // BDAbstractLocationListener myListener = new MyLocationListener();
        //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口，原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                LogUtils.w("map local", "onReceiveLocation: 获取了位置:" + new Gson().toJson(bdLocation));
                mLocationClient.stop();
                sNode = new BNRoutePlanNode(bdLocation.getLongitude(), bdLocation.getLatitude(), bdLocation.getAddress().address, null, CoordinateType.BD09LL);
                //开始导航
                // routeplanToNavi(CoordinateType.BD09LL);
                // 结束位置
                getEnd();
            }
        });
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        int span = 1000;
        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);

        mLocationClient.setLocOption(option);
        LogUtils.w("map local", "开始 获取位置");
        mLocationClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    init();
                } else {
                    // 没有获取到权限，做特殊处理
//                    finish();
                    finishActivity();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取目的地
     */
    PoiSearch mPoiSearch;

    protected void getEnd() {
        if (endAddress.longitude != 0 && endAddress.latitude != 0) {//传过来的数据够详细,就不去搜索
            LogUtils.w("map", "有经纬度,不搜索");
            eNode = new BNRoutePlanNode(endAddress.longitude, endAddress.latitude, "", endAddress.address, CoordinateType.BD09LL);
            routeplanToNavi();
            return;
        }
        LogUtils.w("map", "开始 搜索地址");
        //   第一步，创建POI检索实例

        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();


        //第二步，创建POI检索监听者；
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {

            /**
             * 获取POI搜索结果，包括searchInCity，searchNearby，searchInBound返回的搜索结果
             * @param result
             */
            public void onGetPoiResult(PoiResult result) {
                //获取POI检索结果
                LogUtils.w("map search", "onGetPoiResult :" + new Gson().toJson(result));
                //第五步，释放POI检索实例；
                mPoiSearch.destroy();
                if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    //showToast("检索不出目的地");
                    return;
                }

                if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
                    // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
                    String strInfo = "在";
                    for (CityInfo cityInfo : result.getSuggestCityList()) {
                        strInfo += cityInfo.city;
                        strInfo += ",";
                    }
                    strInfo += "找到结果";
                   // showToast(strInfo);
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) { //没有错误可以操作
                    LogUtils.w("map", "搜索地址成功");
                    if (result.getAllPoi() != null && result.getAllPoi().size() > 0) {
                        eNode = new BNRoutePlanNode(result.getAllPoi().get(0).location.longitude, result.getAllPoi().get(0).location.latitude,
                                result.getAllPoi().get(0).address, null, CoordinateType.BD09LL);
                        routeplanToNavi();
                    }
                    return;
                } else {
                    showToast("获取目的地失败");
//                    finish();
                    finishActivity();
                }
            }

            /**
             * 获取POI详情搜索结果，得到searchPoiDetail返回的搜索结果
             * @param result
             */
            public void onGetPoiDetailResult(PoiDetailResult result) {
                //获取Place详情页检索结果
                LogUtils.w("map search", "onGetPoiDetailResult :" + new Gson().toJson(result));
                //第五步，释放POI检索实例；
                mPoiSearch.destroy();
                if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                    showToast("抱歉，未找到结果");
                } else {
                    showToast(result.getName() + ": " + result.getAddress());
                }
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
                LogUtils.w("map search", "onGetPoiIndoorResult :" + new Gson().toJson(poiIndoorResult));
                //第五步，释放POI检索实例；
                mPoiSearch.destroy();
            }
        };
        OnGetSuggestionResultListener suggestionResultListener = new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                LogUtils.w("map search", "onGetSuggestionResult :" + new Gson().toJson(suggestionResult));
            }
        };
        //第三步，设置POI检索监听者；
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        //第四步，发起检索请求；
        //周边搜索
//        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword("犍为县食品药品监督管理局 学府街83号").
//                sortType(PoiSortType.distance_from_near_to_far).location(new LatLng(myBDLocation.getLongitude(), myBDLocation.getLatitude()))
//                .radius(10000000).pageNum(10);
//        mPoiSearch.searchNearby(nearbySearchOption);
        //城市内搜索
        mPoiSearch.searchInCity((new PoiCitySearchOption()).city(TextUtils.isEmpty(endAddress.city) ? "成都" : endAddress.city).keyword(endAddress.address).pageNum(0));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化SD卡，在SD卡路径下新建文件夹：App目录名，文件中包含了很多东西，比如log、cache等等
     *
     * @return
     */
    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    String authinfo = null;

    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    showToastMsg("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    showToastMsg("Handler : TTS play end");
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
//            showToastMsg("TTSPlayStateListener : TTS play end");
        }

        @Override
        public void playStart() {
//            showToastMsg("TTSPlayStateListener : TTS play start");
        }
    };

    public void showToastMsg(final String msg) {
        BNDemoMainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BNDemoMainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 使用SDK前，先进行百度服务授权和引擎初始化
     */
    private void initNavi() {
        LogUtils.w("map", "百度导航引擎初始化");
        BNOuterTTSPlayerCallback ttsCallback = null;

        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                BNDemoMainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // Toast.makeText(BNDemoMainActivity.this, authinfo, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @SuppressLint("NewApi")
            public void initSuccess() {
                //  Toast.makeText(BNDemoMainActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                //获取当前位置,搜索目的地
                LogUtils.w("map", "百度导航引擎初始化成功");
                getLocal();
            }

            public void initStart() {
                //  Toast.makeText(BNDemoMainActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
                LogUtils.w("map", "百度导航引擎初始化开始");
            }

            public void initFailed() {
                //Toast.makeText(BNDemoMainActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
                LogUtils.w("map", "百度导航引擎初始化失败");
            }


        }, null, ttsHandler, null);

    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    /**
     * 算路设置起、终点，算路偏好，是否模拟导航等参数，然后在回调函数中设置跳转至诱导。
     *
     * @param coType
     */
    BNRoutePlanNode eNode;
    BNRoutePlanNode sNode;

    private void routeplanToNavi() {
//        CoordinateType.WGS84;//国际经纬度坐标
//        CoordinateType.GCJ02; //国测局坐标
//        CoordinateType.BD09_MC;//百度墨卡托坐标
//        CoordinateType.BD09LL;//百度经纬度坐标
        if (eNode == null || sNode == null) {//本地定位成功,搜索目的地成功   才去导航
            LogUtils.w("map", "起止点为空,不导航");
            return;
        }
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            /**
             * 发起算路操作并在算路成功后通过回调监听器进入导航过程,返回是否执行成功
             */
            LogUtils.w("map", "发起导航");
            BaiduNaviManager
                    .getInstance()
                    .launchNavigator(
                            this,                           //建议是应用的主Activity
                            list,                           //传入的算路节点，顺序是起点、途经点、终点，其中途经点最多三个
                            1,                              //算路偏好 1:推荐 8:少收费 2:高速优先 4:少走高速 16:躲避拥堵
                            true,                           //true表示真实GPS导航，false表示模拟导航
                            new DemoRoutePlanListener(sNode)//开始导航回调监听器，在该监听器里一般是进入导航过程页面
                    );
        }
    }


    /**
     * 导航回调监听器
     */
    public class DemoRoutePlanListener implements RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            /*
             * 设置途径点以及resetEndNode会回调该接口
             */

            for (Activity ac : activityList) {
                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {
                    return;
                }
            }
            /**
             * 导航activity
             */
            finishActivity();
            //            finish();
            LogUtils.w("map", "跳入导航");
            Intent intent = new Intent(BNDemoMainActivity.this, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        public void onRoutePlanFailed() {
            LogUtils.w("map", "算路失败");
            Toast.makeText(BNDemoMainActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
            //finish();
            finishActivity();
        }
    }

    /**
     * 导航设置管理器
     */
    private void initSetting() {
        /**
         * 日夜模式 1：自动模式 2：白天模式 3：夜间模式
         */
        BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        /**
         * 设置全程路况显示
         */
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        /**
         * 设置语音播报模式
         */
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        /**
         * 设置省电模式
         */
        BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        /**
         * 设置实时路况条
         */
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }

    private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {

        @Override
        public void stopTTS() {
            Log.e("test_TTS", "stopTTS");
        }

        @Override
        public void resumeTTS() {
            Log.e("test_TTS", "resumeTTS");
        }

        @Override
        public void releaseTTSPlayer() {
            Log.e("test_TTS", "releaseTTSPlayer");
        }

        @Override
        public int playTTSText(String speech, int bPreempt) {
            Log.e("test_TTS", "playTTSText" + "_" + speech + "_" + bPreempt);

            return 1;
        }

        @Override
        public void phoneHangUp() {
            Log.e("test_TTS", "phoneHangUp");
        }

        @Override
        public void phoneCalling() {
            Log.e("test_TTS", "phoneCalling");
        }

        @Override
        public void pauseTTS() {
            Log.e("test_TTS", "pauseTTS");
        }

        @Override
        public void initTTSPlayer() {
            Log.e("test_TTS", "initTTSPlayer");
        }

        @Override
        public int getTTSState() {
            Log.e("test_TTS", "getTTSState");
            return 1;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            finishActivity();
        }
        return super.onKeyDown(keyCode, event);
    }

    public static class Address implements Serializable {
        double longitude;
        double latitude;
        String city;
        String address;

        public Address(double longitude, double latitude, String city, String address) {
            this.address = address;
            this.city = city;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    public void finishActivity() {
        if (mPoiSearch != null) mPoiSearch.destroy();
        finish();
    }
}
