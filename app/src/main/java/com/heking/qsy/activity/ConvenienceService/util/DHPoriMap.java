package com.heking.qsy.activity.ConvenienceService.util;


import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.base.BaseActivity;
import android.os.Bundle;

@SuppressWarnings("deprecation")
public class DHPoriMap extends BaseActivity {
	 
//	 protected AMapNaviView mAMapNaviView;
//
//	 private PoiItem poiItem;
//	 private NaviLatLng latLng;
//	 private AMapNavi mAMapNavi;
//	 protected TTSController mTtsManager;
//	 private LatLonPoint latLonPoint;
//	 protected NaviLatLng mEndLatlng = new NaviLatLng(39.925846, 116.432765);
//	 protected NaviLatLng mStartLatlng = new NaviLatLng(39.925041, 116.437901);
//	 protected final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
//	 protected final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
//	 protected List<NaviLatLng> mWayPointList;
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//	super.onCreate(savedInstanceState);
//	setContentView(R.layout.dh_pori_map_activity);
//	iniView(savedInstanceState);
//}
//private void iniView(Bundle savedInstanceState) {
//	   //获取终点信息数据
//		poiItem=AppContext.BundelPoiMess.poiItem;
//	    latLonPoint=poiItem.getLatLonPoint();
//		latLng=new NaviLatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude());
//
//		sList.add(AppContext.BundelPoiMess.toLatlng);
//	    eList.add(latLng);
//
//	 //实例化语音引擎
//    mTtsManager = TTSController.getInstance(getApplicationContext());
//    mTtsManager.init();
//    mTtsManager.startSpeaking();
//
//    //初始化？AMapNavi
//    mAMapNavi = AMapNavi.getInstance(this);
//    mAMapNavi.startGPS();
//    mAMapNavi.addAMapNaviListener(this);
//    mAMapNavi.addAMapNaviListener(mTtsManager);
//
////    sList.add(mStartLatlng);
////    eList.add(mEndLatlng);
//
//	 mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
//     mAMapNaviView.onCreate(savedInstanceState);
//     mAMapNaviView.setAMapNaviViewListener(this);
//
//
//}
//
////------------------------------生命周期方法---------------------------
//@Override
//protected void onSaveInstanceState(Bundle outState) {
//    super.onSaveInstanceState(outState);
//    mAMapNaviView.onSaveInstanceState(outState);
//
//}
//
//
//@Override
//public void onResume() {
//    super.onResume();
//    mAMapNaviView.onResume();
//
//}
//
//@Override
//public void onPause() {
//    super.onPause();
//    mAMapNaviView.onPause();
////  仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
//  mTtsManager.stopSpeaking();
//}
//
//@Override
//public void onDestroy() {
//    super.onDestroy();
//    mAMapNaviView.onDestroy();
//
//    //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
//    mAMapNavi.stopNavi();
//    mAMapNavi.destroy();
//    mTtsManager.destroy();
//}
////--------------------------------------------------------------------------------------------------
//@Override
//public void onLockMap(boolean arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public boolean onNaviBackClick() {
//	// TODO Auto-generated method stub
//	return false;
//}
//@Override
//public void onNaviCancel() {
//	// TODO Auto-generated method stub
//	finish();
//
//}
//@Override
//public void onNaviMapMode(int arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onNaviSetting() {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onNaviTurnClick() {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onNaviViewLoaded() {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onNextRoadClick() {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onScanViewButtonClick() {
//	// TODO Auto-generated method stub
//
//}
//@Override
//@Deprecated
//public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//@Deprecated
//public void OnUpdateTrafficFacility(TrafficFacilityInfo arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void hideCross() {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void hideLaneInfo() {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void notifyParallelRoad(int arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onArriveDestination() {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onArrivedWayPoint(int arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onCalculateMultipleRoutesSuccess(int[] arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onCalculateRouteFailure(int arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onCalculateRouteSuccess() {
//	// TODO Auto-generated method stub
//	 mAMapNavi.startNavi(NaviType.EMULATOR);
//	 mAMapNavi.startNavi(NaviType.GPS);
//}
//@Override
//public void onEndEmulatorNavi() {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onGetNavigationText(int arg0, String arg1) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onGpsOpenStatus(boolean arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onInitNaviFailure() {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onInitNaviSuccess() {
//	 int strategy=0;
//     try {
//         strategy=mAMapNavi.strategyConvert(true, false, false, false, false);
//     } catch (Exception e) {
//         e.printStackTrace();
//     }
//     mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList,strategy);
//
//}
//@Override
//public void onLocationChange(AMapNaviLocation arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onNaviInfoUpdate(NaviInfo arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//@Deprecated
//public void onNaviInfoUpdated(AMapNaviInfo arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onReCalculateRouteForTrafficJam() {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onReCalculateRouteForYaw() {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onStartNavi(int arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void onTrafficStatusUpdate() {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void showCross(AMapNaviCross arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void showLaneInfo(AMapLaneInfo[] arg0, byte[] arg1, byte[] arg2) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo arg0) {
//	// TODO Auto-generated method stub
//
//}
//@Override
//public void updateAimlessModeStatistics(AimLessModeStat arg0) {
//	// TODO Auto-generated method stub
//
//}


}
