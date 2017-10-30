package com.heking.qsy.activity.ConvenienceService.util;

import android.content.Context;
import android.os.Bundle;


import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

/**
 * 语音播报组件
 */
public class TTSController implements SynthesizerListener {

    public static TTSController ttsManager;
    boolean isfinish = true;
    private Context mContext;
    // 合成对象.
    private SpeechSynthesizer mSpeechSynthesizer;

    TTSController(Context context) {
        mContext = context;
        SpeechUtility.createUtility(context, SpeechConstant.APPID +"=57fda704");   
    }

    public static TTSController getInstance(Context context) {
        if (ttsManager == null) {
            ttsManager = new TTSController(context);
        }
        return ttsManager;
    }

    public void init() {
        // 初始化合成对象.
        mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(mContext,new InitListener() {
			
			@Override
			public void onInit(int arg0) {
				// TODO Auto-generated method stub
				
			}
		} );
        initSpeechSynthesizer();
    }

    /**
     * 使用SpeechSynthesizer合成语音，不弹出合成Dialog.
     *
     * @param
     */
    public void playText(String playText) {
        if (!isfinish) {
            return;
        }
        if (null == mSpeechSynthesizer) {
            // 创建合成对象.
            mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(mContext, null);
            initSpeechSynthesizer();
        }
        // 进行语音合成.
        mSpeechSynthesizer.startSpeaking(playText, this);

    }

    public void stopSpeaking() {
        if (mSpeechSynthesizer != null)
            mSpeechSynthesizer.stopSpeaking();
    }

    public void startSpeaking() {
        isfinish = true;
    }

    private void initSpeechSynthesizer() {
//        // 设置发音人
//        mSpeechSynthesizer.setParameter(SpeechConstant.VOICE_NAME,
//                mContext.getString(R.string.preference_default_tts_role));
//        // 设置语速
//        mSpeechSynthesizer.setParameter(SpeechConstant.SPEED,
//                "" + mContext.getString(R.string.preference_key_tts_speed));
//        // 设置音量
//        mSpeechSynthesizer.setParameter(SpeechConstant.VOLUME,
//                "" + mContext.getString(R.string.preference_key_tts_volume));
//        // 设置语调
//        mSpeechSynthesizer.setParameter(SpeechConstant.PITCH,
//                "" + mContext.getString(R.string.preference_key_tts_pitch));
    	//2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类  
    	mSpeechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人  
    	mSpeechSynthesizer.setParameter(SpeechConstant.SPEED, "50");//设置语速  
    	mSpeechSynthesizer.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100  
    	mSpeechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端  
    }

    @Override
    public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {


    }

    @Override
    public void onCompleted(SpeechError arg0) {

        isfinish = true;
    }

    @Override
    public void onSpeakBegin() {

        isfinish = false;

    }

    @Override
    public void onSpeakPaused() {


    }

    @Override
    public void onSpeakProgress(int arg0, int arg1, int arg2) {


    }

    @Override
    public void onSpeakResumed() {


    }

    public void destroy() {
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.stopSpeaking();
        }
    }

//    @Override
//    public void onArriveDestination() {
//
//        this.playText("到达目的地");
//    }
//
//    @Override
//    public void onArrivedWayPoint(int arg0) {
//
//    }
//
//    @Override
//    public void onCalculateRouteFailure(int arg0) {
//        this.playText("路径计算失败，请检查网络");
//    }
//
//    @Override
//    public void onCalculateRouteSuccess() {
//        String calculateResult = "路径计算就绪";
//
//        this.playText(calculateResult);
//    }
//
//    @Override
//    public void onEndEmulatorNavi() {
//        this.playText("导航结束");
//
//    }
//
//    @Override
//    public void onGetNavigationText(int arg0, String arg1) {
//
//        this.playText(arg1);
//    }
//
//    @Override
//    public void onInitNaviFailure() {
//
//
//    }
//
//    @Override
//    public void onInitNaviSuccess() {
//
//
//    }
//
//    @Override
//    public void onLocationChange(AMapNaviLocation arg0) {
//
//
//    }
//
//    @Override
//    public void onReCalculateRouteForTrafficJam() {
//
//        this.playText("前方路线拥堵，路线重新规划");
//    }
//
//    @Override
//    public void onReCalculateRouteForYaw() {
//
//        this.playText("您已偏航");
//    }
//
//    @Override
//    public void onStartNavi(int arg0) {
//
//
//    }
//
//    @Override
//    public void onTrafficStatusUpdate() {
//
//
//    }
//
//    @Override
//    public void onGpsOpenStatus(boolean arg0) {
//
//
//    }
//
//    @Override
//    public void onNaviInfoUpdated(AMapNaviInfo arg0) {
//
//
//    }
//
//    @Override
//    public void onNaviInfoUpdate(NaviInfo arg0) {
//
//
//
//    }
//
//    @Override
//    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
//
//    }
//
//    @Override
//    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
//
//    }
//
//    @Override
//    public void showCross(AMapNaviCross aMapNaviCross) {
//
//    }
//
//    @Override
//    public void hideCross() {
//
//    }
//
//    @Override
//    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {
//
//    }
//
//    @Override
//    public void hideLaneInfo() {
//
//    }
//
//    @Override
//    public void onCalculateMultipleRoutesSuccess(int[] ints) {
//
//    }
//
//    @Override
//    public void notifyParallelRoad(int i) {
//
//    }
//
//    @Override
//    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
//
//    }
//
//    @Override
//    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
//
//    }
//    @Override
//    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
//
//    }

	@Override
	public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
		// TODO Auto-generated method stub
		
	}
}
