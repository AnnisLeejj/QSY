package com.heking.qsy.activity;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.ConvenienceService.util.BNDemoMainActivity;
import com.heking.qsy.activity.review.BaiDuMapAdapter;
import com.heking.qsy.activity.review.WaitDialog;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class IWantToReview_baidu extends Activity implements
        OnGetPoiSearchResultListener, BDLocationListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    // 定义相关声明
    private LocationClient locationClient;
    // 自定义图标
    private LocationMode mBitmapDescriptor;
    // 是否首次定位
    private boolean isFirstloc = true;
    private View view0, view1;
    private ListView listView;
    private BaiDuMapAdapter adapter;
    private WaitDialog dialog;
    private PoiSearch mPoiSearch;
    private String TAG = "IWantToReview_baidu";
    private final int BAIDU_READ_PHONE_STATE = 10011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.i_want_to_review_baidu);
        iniView();
        //权限设置  首次进入地图界面需要定位权限
        if (Build.VERSION.SDK_INT > 22) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
            } else {
                iniData();
            }
        } else {
            iniData();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    iniData();
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private List<PoiInfo> list = new ArrayList<PoiInfo>();

    private void iniView() {

        mMapView = (MapView) findViewById(R.id.baidumap);

        button1 = (TextView) findViewById(R.id.food_firm_button);
        button2 = (TextView) findViewById(R.id.yao_ping_firm);

        view0 = findViewById(R.id.back_view_01);
        view1 = findViewById(R.id.back_view_02);

        listView = (ListView) findViewById(R.id.list_view_map);

        adapter = new BaiDuMapAdapter(this, list);
        listView.setAdapter(adapter);

        dialog = new WaitDialog(this);
        dialog.setContent("正在加载...");
        dialog.show();

        findViewById(R.id.textview).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        mBaiduMap = mMapView.getMap();

        mPoiSearch = PoiSearch.newInstance();

    }

    private void iniData() {
        // 普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启交通图
        mBaiduMap.setTrafficEnabled(true);
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mBitmapDescriptor, true, null));

        locationClient = new LocationClient(this);// 实例化location类
        locationClient.registerLocationListener(this);// 注册监听函数
        setLocationOption();
        locationClient.start();

        mBaiduMap.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                // Mark(point);
            }

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                // TODO Auto-generated method stub

                return true;
            }
        });
        mPoiSearch.setOnGetPoiSearchResultListener(this);

    }

    private TextView button1;
    private TextView button2;

    private void iniButton() {
        findViewById(R.id.food_firm_button).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        view1.setVisibility(LinearLayout.INVISIBLE);
                        view0.setVisibility(LinearLayout.VISIBLE);
                        dialog.show();
                        PoiNearbySearchOption option = new PoiNearbySearchOption();

                        option.keyword("美食");
                        option.pageCapacity(1000);
                        option.location(ll);
                        option.radius(2000);

                        mPoiSearch.searchNearby(option);
                        adapter.notifyDataSetChanged();

                        button1.setTextColor(Color.parseColor("#3cafdf"));
                        button2.setTextColor(Color.parseColor("#808080"));
                    }
                });
        findViewById(R.id.yao_ping_firm).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        view0.setVisibility(LinearLayout.INVISIBLE);
                        view1.setVisibility(LinearLayout.VISIBLE);
                        dialog.show();

                        PoiNearbySearchOption option = new PoiNearbySearchOption();

                        option.keyword("药店");
                        option.pageCapacity(1000);
                        option.location(ll);
                        option.radius(2000);

                        mPoiSearch.searchNearby(option);
                        adapter.notifyDataSetChanged();
                        button2.setTextColor(Color.parseColor("#3cafdf"));
                        button1.setTextColor(Color.parseColor("#808080"));
                    }
                });

    }

    private void setLocationOption() {
        LocationClientOption clientOption = new LocationClientOption();

        // 打开GPS
        clientOption.setOpenGps(true);

        // 设置定位模式
        clientOption
                .setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 返回的定位结果是百度经纬度，默认gcj02
        clientOption.setCoorType("bd09ll");

        // 设置发送定位请求的间隔时间

        clientOption.setScanSpan(1000);
        // 返回的定位结果包含地址信息
        clientOption.setIsNeedAddress(true);
        // 返回的定位结果包含手机机头的方向
        clientOption.setNeedDeviceDirect(true);

        locationClient.setLocOption(clientOption);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult arg0) {
        Log.d("测试数据", "onGetPoiDetailResult");
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult arg0) {
        Log.d("测试数据", "onGetPoiIndoorResult");

    }

    @Override
    public void onGetPoiResult(PoiResult arg0) {
        List<PoiInfo> lists = arg0.getAllPoi();
        if (lists != null) {
            list.clear();
            for (PoiInfo info : lists) {
                list.add(info);
            }
            adapter.notifyDataSetChanged();
            Log.d("IWantToReview", "onGetPoiResult" + lists.size());
        }
           }

    private LatLng ll;

    @Override
    public void onReceiveLocation(BDLocation location) {
        // mMapView 销毁后不再处理新接收的位置
        if (location == null || mMapView == null)
            return;
        MyLocationData data = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的反向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(data);
        dialog.dismiss();
        if (isFirstloc) {
            isFirstloc = false;
            ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 16);
            PoiNearbySearchOption option = new PoiNearbySearchOption();
            option.keyword("美食");
            option.pageCapacity(1000);
            option.location(ll);
            option.radius(2000);
            mPoiSearch.searchNearby(option);
            mBaiduMap.animateMapStatus(u);
            iniButton();
        }
    }
}