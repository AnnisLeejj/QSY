package com.heking.qsy.activity;


import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.heking.qsy.Dialog.CustomDialog;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.Personalcenters.LoginActivity;
import com.heking.qsy.activity.review.MapAdapter;
import com.heking.qsy.activity.review.WaitDialog;

public class IWantToReview extends Activity {
	
	private MapView mMapView;
//	private AMap mAMap;
//	private AMapLocationClientOption mLocationOption = null;
//	private AMapLocationClient	mlocationClient ;
//	private OnLocationChangedListener mListener;
//	private AMapLocation aLocation;
	private boolean isFirst = true;
//	private LatLonPoint mlatLonPoint;
	
	private PoiSearch poiSearch;
	private View view0,view1;
	
	private ListView listView;
	private MapAdapter adapter;
	private WaitDialog dialog;
	
	private BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		   if (AppContext.THEME)  {
	           setTheme(R.style.SwitchTheme_1);  
		   }
	       else { 
	           setTheme(R.style.SwitchTheme_2);
	       }
		setContentView(R.layout.i_want_to_review); 
		mMapView=(MapView) findViewById(R.id.bmapView);
//		mMapView.onCreate(savedInstanceState);
			iniView();
			iniData();
			iniButton();
//			booLogin();
	}
	private void toActivity(Class<?> cla ){
		Intent intent=new Intent(this,cla);
		startActivity(intent);
	}
	private void booLogin() {
		if(!AppContext.LoginUserMessage.messageUse){
			CustomDialog.Builder builder = new CustomDialog.Builder(this);  
	        builder.setMessage("\t\t您尚未登录，请您登录后使用该项功能。");  
	        builder.setTitle("温馨提示"); 
	        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	            public void onClick(DialogInterface dialog, int which) {  
	                dialog.dismiss();
	                AppContext.LoginUserMessage.STATE=AppContext.LoginUserMessage.I_WANT_TO_REVIEW;
					toActivity(LoginActivity.class);
					IWantToReview.this.finish();
	                //设置你的操作事项  
	            }  
	        });  
	  
	        builder.setNegativeButton("取消",  
	                new android.content.DialogInterface.OnClickListener() {  
	                    public void onClick(DialogInterface dialog, int which) {  
	                    	IWantToReview.this.finish();
	                    }  
	                });  
	        CustomDialog dailog=builder.create();
	        dailog.setCanceledOnTouchOutside(false);
	        dailog.setOnKeyListener(new DialogInterface.OnKeyListener() {
         	   @Override
         	   public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
         	   {
         	   if (keyCode == KeyEvent.KEYCODE_SEARCH)
         	    {
         	     return true;
         	    }
         	    else
         	    {
         	     return true; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
         	    }
         	   }
         	  });
	        dailog.show();
	        } 
			
	}

	private void iniView() {
		
//		mAMap=mMapView.getMap();	// TODO Auto-generated method stub
		
		view0=findViewById(R.id.back_view_01);
		view1=findViewById(R.id.back_view_02);
		
		listView=(ListView) findViewById(R.id.list_view_map);
		
		dialog = new WaitDialog(this);
		dialog.setContent("正在加载...");
		dialog.show();
		findViewById(R.id.textview).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void iniData() {
//		mAMap.setLocationSource(this );// 设置定位监听
//		mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
//		mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//		mAMap.setTrafficEnabled(true);
//		mAMap.showBuildings(false);
//
//		// 设置定位的类型为定位模式，参见类AMap。
//	//	mAMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
//	mlocationClient = new AMapLocationClient(this);
//		//初始化定位参数
//		mLocationOption = new AMapLocationClientOption();
//		//设置定位监听
//		mlocationClient.setLocationListener(this);
//		//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//		mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
//		//设置定位间隔,单位毫秒,默认为2000ms
//		mLocationOption.setInterval(2000);
//		//设置定位参数
//		mlocationClient.setLocationOption(mLocationOption);
//		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
//		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
//		// 在定位结束后，在合适的生命周期调用onDestroy()方法
//		// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//		//启动定位
//		mlocationClient.startLocation();
//		dialog.show();
		
	}
	
	private void iniButton() {
//		 findViewById(R.id.food_firm_button).setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					view1.setVisibility(LinearLayout.INVISIBLE);
//					view0.setVisibility(LinearLayout.VISIBLE);
//					dialog.show();
//					Query	query = new PoiSearch.Query("餐饮", "餐饮服务", null);
//					// keyWord表示搜索字符串，第二个参数表示POI搜索类型，默认为：生活服务、餐饮服务、商务住宅
//					//共分为以下20种：汽车服务|汽车销售|
//					//汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
//					//住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
//					//金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
//					//cityCode表示POI搜索区域，（这里可以传空字符串，空字符串代表全国在全国范围内进行搜索）
//					query.setPageSize(100);// 设置每页最多返回多少条poiitem
//					poiSearch= new PoiSearch(IWantToReview.this,query);
//					poiSearch.setBound(new SearchBound(mlatLonPoint, 3000));//设置周边搜索的中心点以及区域
//					poiSearch.setOnPoiSearchListener(IWantToReview.this);//设置数据返回的监听器
//					poiSearch.searchPOIAsyn();//开始搜索
//					adapter.notifyDataSetChanged();
//
//				}
//			});
//	     findViewById(R.id.yao_ping_firm).setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					view0.setVisibility(LinearLayout.INVISIBLE);
//					view1.setVisibility(LinearLayout.VISIBLE);
//					dialog.show();
//					Query	query = new PoiSearch.Query("药店", "医疗保健服务", null);
//					// keyWord表示搜索字符串，第二个参数表示POI搜索类型，默认为：生活服务、餐饮服务、商务住宅
//					//共分为以下20种：汽车服务|汽车销售|
//					//汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
//					//住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
//					//金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
//					//cityCode表示POI搜索区域，（这里可以传空字符串，空字符串代表全国在全国范围内进行搜索）
//					query.setPageSize(100);// 设置每页最多返回多少条poiitem
//					poiSearch= new PoiSearch(IWantToReview.this,query);
//					poiSearch.setBound(new SearchBound(mlatLonPoint, 3000));//设置周边搜索的中心点以及区域
//					poiSearch.setOnPoiSearchListener(IWantToReview.this);//设置数据返回的监听器
//					poiSearch.searchPOIAsyn();//开始搜索
//					adapter.notifyDataSetChanged();
//				}
//			});
		
	}
	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

//	@Override
//	public void activate(OnLocationChangedListener listener) {
//		// TODO Auto-generated method stub
//		mListener = listener;
//
//	}
//
//	@Override
//	public void deactivate() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onLocationChanged(AMapLocation aLocation) {
//		// TODO Auto-generated method stub
//		if(aLocation!=null){
//
//			this.aLocation = aLocation;
//			if (mListener != null)
//				mListener.onLocationChanged(aLocation);// 显示系统小蓝点
//			if(isFirst){
//				mAMap.moveCamera(CameraUpdateFactory.zoomBy(8));
//					isFirst=false;
//					//设置经纬度
//					 mlatLonPoint=new LatLonPoint(aLocation.getLatitude(),aLocation.getLongitude());
//					 AppContext.BundelPoiMess.toLatlng=new NaviLatLng(aLocation.getLatitude(),aLocation.getLongitude());
//					Query	query = new PoiSearch.Query("餐饮", "餐饮服务", null);
//					// keyWord表示搜索字符串，第二个参数表示POI搜索类型，默认为：生活服务、餐饮服务、商务住宅
//					//共分为以下20种：汽车服务|汽车销售|
//					//汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
//					//住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
//					//金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
//					//cityCode表示POI搜索区域，（这里可以传空字符串，空字符串代表全国在全国范围内进行搜索）
//					query.setPageSize(100);// 设置每页最多返回多少条poiitem
//					poiSearch = new PoiSearch(this,query);
//					poiSearch.setBound(new SearchBound(mlatLonPoint, 3000));//设置周边搜索的中心点以及区域
//					poiSearch.setOnPoiSearchListener(this);//设置数据返回的监听器
//					poiSearch.searchPOIAsyn();//开始搜索
//				}
//
//		}
//	}
//public void toaddMarker(LatLng arg0){
//
//	mAMap.clear(true);
//	MarkerOptions markerOptions=new MarkerOptions();
//	markerOptions.icon(descriptor);
//	markerOptions.position(arg0);
//	mAMap.addMarker(markerOptions);
//	Log.d("数据测试", "..................>toaddmarker");
//}
//	@Override
//	public void onPoiItemSearched(PoiItem arg0, int arg1) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onPoiSearched(PoiResult result, int rCode) {
//		// TODO Auto-generated method stub
//		ArrayList<PoiItem> list=result.getPois();
//		adapter=new MapAdapter(this, list);
//		listView.setAdapter(adapter);
//		adapter.notifyDataSetChanged();
//		dialog.dismiss();
//	}
//
//

}