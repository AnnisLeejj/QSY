package com.heking.qsy.activity.ConvenienceService.util;

import java.io.Console;
import java.util.List;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
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
import com.heking.qsy.activity.review.BaiDuMapAdapter;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.qsy.base.BaseActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ZhouBianYaodian extends BaseActivity implements OnGetPoiSearchResultListener,BDLocationListener {
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	
	private Marker marker;
	//定义相关声明
	private LocationClient locationClient;
	//自定义图标
	private LocationMode mBitmapDescriptor;
	//是否首次定位
	private boolean isFirstloc = true;
	
	private Console console;
	
	private double Latitude , Longitude;
	
	
	private BaiDuMapAdapter adapter;
	private WaitDialog dialog;
	private PoiSearch mPoiSearch;
	private BitmapDescriptor drawable;
	
	 private InfoWindow mInfoWindow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if (AppContext.THEME) {
			setTheme(R.style.SwitchTheme_1);
		} else {
			setTheme(R.style.SwitchTheme_2);
		}
		
		setContentView(R.layout.zhou_bian_yao_dian_activity);
		
		iniView();
		iniData();
	}
	private void iniView() {
		
		mMapView=(MapView) findViewById(R.id.baidumap_zhou_bian);
		
		drawable = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
		
//		dialog = new WaitDialog(this);
//		dialog.setContent("正在加载...");
//		dialog.show();
		
	findViewById(R.id.textview).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		mBaiduMap = mMapView.getMap(); 
		
		mPoiSearch = PoiSearch.newInstance();
		
	}
	
	private void toActivity(Class<?> cla ){
		Intent intent=new Intent(this,cla);
		startActivity(intent);
	}
	
	
	private void ico(LatLng lla){
  MarkerOptions ooA = new MarkerOptions().position(lla).icon(drawable).zIndex(9).draggable(true);
//  ooA.animateType(MarkerAnimateType.drop);
	
  marker= (Marker) (mBaiduMap.addOverlay(ooA));
  
	}
	
	private void iniData() {
//		layout.addView(mMapView);
		
		//普通地图  
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
	
	
		//开启交通图   
		mBaiduMap.setTrafficEnabled(true);
		
		mBaiduMap.setMyLocationEnabled(true);
		
	
		 mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mBitmapDescriptor, true, null));
		 
	        locationClient = new LocationClient(this);//实例化location类
	        locationClient.registerLocationListener(this);//注册监听函数
	        setLocationOption();
	     	locationClient.start();
	        mBaiduMap.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public void onMapClick(LatLng point) {
					// TODO Auto-generated method stub
					//Mark(point);
					Log.d("数据测试", "");
					
				}

				@Override
				public boolean onMapPoiClick(final MapPoi mapoi) {
					String name = mapoi.getName();
					String cust = "http://map.baidu.com/detail?qt=ninf&uid="+mapoi.getUid();
					HttpHelper.getInstance().service.get(cust).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
						@Override
						public void onSubscribe(Disposable d) {

						}

						@Override
						public void onSuccess(String json) {
							View mView = LayoutInflater.from(ZhouBianYaodian.this).inflate(R.layout.map_massage_data, null);
							TextView dataname=(TextView) findViewById(R.id.txt_map_name);
							TextView address=(TextView) findViewById(R.id.txt_map_address);
							ImageView imageView=(ImageView) findViewById(R.id.map_image_call);

							TB_firm_map firm_map =ParsonJson.jsonToBeanObj(json, TB_firm_map.class);
							TB_firm_map.content content =ParsonJson.jsonToBeanObj(firm_map.getContent(), TB_firm_map.content.class);


							if(content!=null){
								dataname.setText(content.getName());
								address.setText(content.getAddr());
								if(content!=null&&!content.equals("")){
									imageView.setVisibility(ImageView.VISIBLE);
								}
							}
							mInfoWindow= new InfoWindow(mView, mapoi.getPosition(), -47);
							mBaiduMap.showInfoWindow(mInfoWindow);

						}

						@Override
						public void onError(Throwable e) {

						}
					});
					Log.d("数据测试", cust);
					return true;
				}
			});
	    	mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				
				@Override
				public boolean onMarkerClick(Marker arg0) {
					// TODO Auto-generated method stub
					
					for(final PoiInfo poiInfo:AppContext.listmap){
						if(poiInfo.location.latitude==arg0.getPosition().latitude&&poiInfo.location.longitude==arg0.getPosition().longitude){
							
							View mView = LayoutInflater.from(ZhouBianYaodian.this).inflate(R.layout.map_massage_data, null);
							TextView dataname=(TextView) mView.findViewById(R.id.txt_map_name);
							TextView address=(TextView)mView. findViewById(R.id.txt_map_address);
							ImageView imageView=(ImageView)mView. findViewById(R.id.map_image_call); 
							
								dataname.setText(poiInfo.name==null?"":poiInfo.name);
								address.setText(poiInfo.address==null?"":poiInfo.address);
								if(poiInfo.phoneNum!=null&&!poiInfo.phoneNum.equals("")){
									imageView.setVisibility(ImageView.VISIBLE);
									imageView.setOnClickListener(new OnClickListener() {
										
										@Override
										public void onClick(View v) {
											Intent intent = new Intent(Intent.ACTION_DIAL, Uri
													.parse("tel:" + poiInfo.phoneNum));
											Log.d("测试数据", poiInfo.phoneNum);
											startActivity(intent);
										}
									});
								}
							
							mInfoWindow= new InfoWindow(mView,arg0.getPosition(), -47);
							mBaiduMap.showInfoWindow(mInfoWindow);
						
						
					}
					
					
				}
					return false;
				}
			});
	        mPoiSearch.setOnGetPoiSearchResultListener(this);
	}
	
   private void setLocationOption(){
   	LocationClientOption clientOption = new LocationClientOption();
   	
   	//打开GPS
   	clientOption.setOpenGps(true);
   	
   	//设置定位模式
   	clientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
   	//返回的定位结果是百度经纬度，默认gcj02
   	clientOption.setCoorType("bd09ll");
   	
   	//设置发送定位请求的间隔时间
   
   	clientOption.setScanSpan(1000);
   	//返回的定位结果包含地址信息
   	clientOption.setIsNeedAddress(true);
   	//返回的定位结果包含手机机头的方向
   	clientOption.setNeedDeviceDirect(true);
   	
   	locationClient.setLocOption(clientOption);
  
   }
	
	  @Override  
	    protected void onDestroy() {  
	        super.onDestroy();  
	        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
	        mMapView.onDestroy();  
	    }  
	    @Override  
	    protected void onResume() {  
	        super.onResume();  
	        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
	        mMapView.onResume();  
	        }  
	    @Override  
	    protected void onPause() {  
	        super.onPause();  
	        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
	        mMapView.onPause();  
	        }
	    
		@Override
		public void onGetPoiDetailResult(PoiDetailResult arg0) {
			// TODO Auto-generated method stub
			Log.d("测试数据", "");
		}
		@Override
		public void onGetPoiIndoorResult(PoiIndoorResult arg0) {
			// TODO Auto-generated method stub
			Log.d("测试数据", "");
			
		}
		
		@Override
		public void onGetPoiResult(PoiResult arg0) {
			// TODO Auto-generated method stub
			List<PoiInfo> lists=arg0.getAllPoi();
			if(lists!=null){
			for(PoiInfo poiInfo : lists){
				AppContext.listmap.add(poiInfo);
				ico(poiInfo.location);
				}
			}
	     
			Log.d("测试数据", "");
			
		}
		private LatLng ll;
		
		@Override
		public void onReceiveLocation(BDLocation location) {
			// mMapView 销毁后不再处理新接收的位置
			if (location == null || mMapView == null)
				return;
			
			MyLocationData data = new MyLocationData.Builder()
			.accuracy(location.getRadius())
			//此处设置开发者获取到的反向信息，顺时针0-360
			.direction(100).latitude(location.getLatitude())
			.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(data);
			
//			dialog.dismiss();
			
			//获取经度
			Longitude = location.getLongitude();
			//获取纬度
			Latitude = location.getLatitude();
			
			if (isFirstloc) {
				isFirstloc = false;
				
				 ll = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 16);
		        PoiNearbySearchOption option=  new PoiNearbySearchOption();
		        
		        option.keyword("药店");
		        
		        option.	pageCapacity(1000);
		        option.location(ll);
		        option.radius(2000);
		        
		        mPoiSearch.searchNearby(option);
				mBaiduMap.animateMapStatus(u);
			}
			
		}
	
}