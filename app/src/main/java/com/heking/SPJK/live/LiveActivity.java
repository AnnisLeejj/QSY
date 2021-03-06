package com.heking.SPJK.live;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.FirmShow.MyPagerAdapter;
import com.heking.qsy.activity.FirmShow.NoScrollViewPager;
import com.heking.SPJK.constants.Constants;
import com.heking.SPJK.data.TempData;
import com.heking.SPJK.resource.ResourceListAdapter;
import com.heking.SPJK.utils.DebugLog;
import com.heking.SPJK.utils.UIUtil;
import com.heking.SPJK.utils.UtilAudioPlay;
import com.heking.SPJK.utils.UtilFilePath;

import com.heking.qsy.base.BaseActivity;
import com.hik.mcrsdk.rtsp.LiveInfo;
import com.hik.mcrsdk.rtsp.RtspClient;
import com.hikvision.vmsnetsdk.CameraInfo;
import com.hikvision.vmsnetsdk.CameraInfoEx;
import com.hikvision.vmsnetsdk.RealPlayURL;
import com.hikvision.vmsnetsdk.ServInfo;
import com.hikvision.vmsnetsdk.VMSNetSDK;
import com.hikvision.vmsnetsdk.netLayer.msp.deviceInfo.DeviceInfo;

import MyUtils.LogUtils.LogUtils;
import butterknife.BindView;

/**
 * 预览
 *
 * @author xiadaidai
 * @Data 2015-06-03
 */
public class LiveActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener, Callback, LiveCallBack {
    private static final String TAG = "LiveActivity";
    /**
     *
     */
    private Button hengshu;
    /**
     * 开始播放按钮
     */
    private Button mStartBtn;

    /**
     * 停止播放按钮
     */
    private Button mStopBtn;

    /**
     * 抓拍按钮
     */
    private Button mCaptureBtn;

    /**
     * 录像按钮
     */
    private Button mRecordBtn;

    /**
     * 音频按钮
     */
    private Button mAudioBtn;

    /**
     * 码流切换
     */
    private RadioGroup mRadioGroup;

    /**
     * 码流类型
     */
    private int mStreamType = -1;

    /**
     * 通过VMSNetSDK返回的预览地址对象
     */
    private RealPlayURL mRealPlayURL;

    /**
     * 登录设备的用户名
     */
    private String mName;

    /**
     * 登录设备的密码
     */
    private String mPassword;

    /**
     * 控制层对象
     */
    private LiveControl mLiveControl;

    /**
     * 播放视频的控件对象
     */
    private SurfaceView mSurfaceView;


    /**
     * 创建消息对象
     */
    private Handler mMessageHandler = new MyHandler();

    /**
     * 音频是否开启
     */
    private boolean mIsAudioOpen = false;

    /**
     * 是否正在录像
     */
    private boolean mIsRecord;

    /**
     * 播放流量
     */
    private long mStreamRate = 0;

    /**
     * 监控点信息对象
     */
    private CameraInfo cameraInfo;

    /**
     * 云台控制界面布局区域
     */
    private RelativeLayout cloudCtrlArea;

    /**
     * 云台控制按钮
     */
    private Button startCtrlBtn;

    /**
     * 停止云台控制按钮
     */
    private Button stopCtrlBtn;

    /**
     * 云台控制对话框
     */
    private AlertDialog mDialog;

    private String mDeviceID = "";

    private VMSNetSDK mVmsNetSDK = null;


    private String mCameraID = null;

    private CameraInfoEx cameraInfoEx;

    /**
     * RTSP sdk句柄
     */
    private RtspClient mRtspHandle = null;

    /**
     * 获取监控点详情结果
     */
    private boolean getCameraDetailInfoResult = false;

    /**
     * 获取设备详情结果
     */
    private boolean getDeviceInfoResult = false;

    /**
     * 服务器校验时的token
     */
    private String mToken = null;

    private DeviceInfo deviceInfo;

    /**
     * 用户能力集，1--预览 2--回放 3--地理位置矫正 4--云台控制
     */
    private List<Integer> mUserCap;

    /**
     * 云台控制
     */
    private static final int PTZ_CONTROL = 4;
    NoScrollViewPager vp;
    private final Context context = this;
    private ArrayList<View> surfaceViewList;
    MyPagerAdapter pagerAdapter;

    //数据信息
    ArrayList mList;
    int position;
    String serAddr;
    private ServInfo mServInfo;
    String sessionid;
    TextView textview, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_activity_hkws);
        mList = AppContext.shipins_toshow;//ResourceListAdapter.mList
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("position", 0);
        serAddr = extras.getString("serAddr");
        mServInfo = new Gson().fromJson(extras.getString("mServInfo"), com.hikvision.vmsnetsdk.ServInfo.class);
        sessionid = mServInfo.getSessionID();
        cameraInfo = (CameraInfo) mList.get(position);
        mCameraID = cameraInfo.getId();
        showWaitDialog();

        initUI();
        initData();
        startBtnOnClick();
    }

    /**
     * 初始化网络库和控制层对象
     *
     * @since V1.0
     */
    private void initData() {
        LogUtils.w("shipin_one", "摄像头信息:", cameraInfo);
        mRealPlayURL = new RealPlayURL();
        mLiveControl = new LiveControl();
        mLiveControl.setLiveCallBack(this);
        // cameraInfo = TempData.getIns().getCameraInfo();
        cameraInfoEx = new CameraInfoEx();
        cameraInfoEx.setId(mCameraID);
        mVmsNetSDK = VMSNetSDK.getInstance();
        if (mVmsNetSDK == null) {
            Log.e(Constants.LOG_TAG, "mVmsNetSDK is null");
            return;
        }
        getCameraDetailInfo(serAddr, sessionid);
        // liveCameraInfo.setParams(cameraInfoEx);
        // RTSP SDK
        mRtspHandle = RtspClient.getInstance();
        if (null == mRtspHandle) {
            Log.e(Constants.LOG_TAG, "initialize:" + "RealPlay mRtspHandle is null!");
            return;
        }
    }

    /**
     * 获取监控点详情方法
     *
     * @param serAddr   服务器地址
     * @param sessionid 会话ID
     */
    private void getCameraDetailInfo(final String serAddr, final String sessionid) {
        LogUtils.w("shipin", "获取监控点详情方法 ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                getCameraDetailInfoResult = LiveActivity.this.mVmsNetSDK.getCameraInfoEx(serAddr, sessionid, mCameraID, cameraInfoEx);
                Log.i(Constants.LOG_TAG, "result is :" + getCameraDetailInfoResult);

                mDeviceID = cameraInfoEx.getDeviceId();
                Log.i(Constants.LOG_TAG, "mDeviceID is :" + mDeviceID);
                deviceInfo = new DeviceInfo();

                // 获取设备信息
                getDeviceInfoResult = LiveActivity.this.mVmsNetSDK.getDeviceInfo(serAddr, sessionid, mDeviceID, deviceInfo);
                if (!getDeviceInfoResult || null == deviceInfo || TextUtils.isEmpty(deviceInfo.getLoginName()) || TextUtils.isEmpty(deviceInfo.getLoginPsw())) {
                    deviceInfo.setLoginName("admin");
                    deviceInfo.setLoginPsw("12345");
                }
                mName = deviceInfo.getLoginName();
                mPassword = deviceInfo.getLoginPsw();
                LogUtils.w(Constants.LOG_TAG, "shipin:" + getDeviceInfoResult + "-------"
                        + deviceInfo.getDeviceName() + "------" + "deviceLoginName is " + mName + "-----"
                        + "deviceLoginPassword is " + mPassword + "-----" + "deviceID is " + mDeviceID);
            }
        }).start();
    }

    /**
     * 初始化控件
     *
     * @since V1.0
     */
    @SuppressLint("NewApi")
    private void initUI() {
        title = (TextView) findViewById(R.id.live_activity_title);
        title.setText(TextUtils.isEmpty(cameraInfo.getName()) ? "" : cameraInfo.getName());
        textview = (TextView) findViewById(R.id.textview);
        textview.setOnClickListener(this);
        mStartBtn = (Button) findViewById(R.id.liveStartBtn);
        mStartBtn.setOnClickListener(this);
        mStopBtn = (Button) findViewById(R.id.liveStopBtn);
        mStopBtn.setOnClickListener(this);
        mCaptureBtn = (Button) findViewById(R.id.liveCaptureBtn);
        mCaptureBtn.setOnClickListener(this);

        mRecordBtn = (Button) findViewById(R.id.liveRecordBtn);
        mRecordBtn.setOnClickListener(this);

        mAudioBtn = (Button) findViewById(R.id.liveAudioBtn);
        mAudioBtn.setOnClickListener(this);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroup.check(R.id.subRadio);
        mStreamType = ConstantLive.SUB_STREAM;

        vp = (NoScrollViewPager) findViewById(R.id.vp);
        surfaceViewList = new ArrayList<View>();

        if (mList != null && mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                SurfaceView surfaceView = new SurfaceView(context);
                surfaceView.setZOrderOnTop(true);
                surfaceViewList.add(surfaceView);
            }
            LogUtils.w("shipin", "视频集合长度:" + mList.size());
            pagerAdapter = new MyPagerAdapter(surfaceViewList);
            vp.setAdapter(pagerAdapter);
            vp.setCurrentItem(position);
            mSurfaceView = (SurfaceView) surfaceViewList.get(position);
            mSurfaceView.getHolder().addCallback(this);
            vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int index) {
                    showWaitDialog();
                    mLiveControl.stop();
                    mSurfaceView = (SurfaceView) surfaceViewList.get(index);
                    mSurfaceView.getHolder().addCallback(LiveActivity.this);

                    cameraInfo = (CameraInfo) mList.get(index);
                    mCameraID = cameraInfo.getId();
                    title.setText(cameraInfo.getName());
                    TempData.getIns().setCameraInfo(cameraInfo);
                    LogUtils.w("shipin_huadong", "onPageSelected:" + index, cameraInfo);

                    initData();
                    startBtnOnClick();
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                    //    LogUtils.w("shipin_huadong", "onPageSelected:" + arg0 +"  "+arg1+"   "+arg2);
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                    LogUtils.w("shipin_huadong", "onPageScrollStateChanged:" + arg0);
                    boolean flag = false;
                    switch (arg0) {
                        case ViewPager.SCROLL_STATE_DRAGGING:
                            flag = false;
                            break;
                        case ViewPager.SCROLL_STATE_SETTLING:
                            flag = true;
                            break;
                        case ViewPager.SCROLL_STATE_IDLE:
                            if (mList.size() == 1) {
                                showToast("只有一个摄像头可查看");
                            } else {
                                if (vp.getCurrentItem() == vp.getAdapter().getCount() - 1 && !flag) {
                                    showToast("已经是最后一页");
                                } else if (vp.getCurrentItem() == 0 && !flag) {
                                    showToast("已经是第一页");
                                }
                            }
                            flag = true;
                            break;
                    }
                }
            });
        }
        // int width=AppContext.ScrnParameter.screenWidth;
        // int height=AppContext.ScrnParameter.screenHenight;
        // LayoutParams params=new LayoutParams(((width-130)/3)*4, width-130);
        // mSurfaceView.setLayoutParams(params);
        cloudCtrlArea = (RelativeLayout) findViewById(R.id.cloud_area);
        // 云台控制需要根据权限来显示
        mUserCap = new ArrayList<Integer>();
        mUserCap = cameraInfo.getUserCapability();
        if (mUserCap.contains(PTZ_CONTROL)) {
            cloudCtrlArea.setVisibility(View.VISIBLE);
            startCtrlBtn = (Button) findViewById(R.id.start_ctrl);
            stopCtrlBtn = (Button) findViewById(R.id.stop_ctrl);
            startCtrlBtn.setOnClickListener(this);
            stopCtrlBtn.setOnClickListener(this);
        } else {
            cloudCtrlArea.setVisibility(View.GONE);
        }
    }

    private void setColor(int id) {
        RadioButton button = (RadioButton) findViewById(id);
        button.setTextColor(Color.parseColor("#3cafdf"));
    }

    private void setColor(int id, boolean fasle) {
        RadioButton button = (RadioButton) findViewById(id);
        button.setTextColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.radioGroup) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.mainRadio:
                    setColor(R.id.mainRadio);
                    setColor(R.id.subRadio, false);
                    setColor(R.id.magRadio, false);
                    mStreamType = ConstantLive.MAIN_STREAM;
                    break;
                case R.id.subRadio:
                    setColor(R.id.subRadio);
                    setColor(R.id.mainRadio, false);
                    setColor(R.id.magRadio, false);
                    mStreamType = ConstantLive.SUB_STREAM;
                    break;
                case R.id.magRadio:
                    setColor(R.id.magRadio);
                    setColor(R.id.subRadio, false);
                    setColor(R.id.mainRadio, false);
                    mStreamType = ConstantLive.MAG;
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview:
                finish();
                break;
            case R.id.liveStartBtn:
                startBtnOnClick();
                break;
            case R.id.liveStopBtn:
                stopBtnOnClick();
                break;
            case R.id.liveCaptureBtn:
                captureBtnOnClick();
                break;
            case R.id.liveRecordBtn:
                recordBtnOnClick();
                break;
            case R.id.liveAudioBtn:
                audioBtnOnClick();
                break;
            case R.id.start_ctrl:
                startCloudCtrl();
                break;
            case R.id.stop_ctrl:
                stopCloudCtrl();
                break;
            default:
                break;
        }
    }

    /**
     * 开始云台控制，弹出控制界面
     */
    private void startCloudCtrl() {

        final int[] gestureIDs = {1, 2, 3, 4, 11, 12, 13, 14, 7, 8, 9, 10};
        String[] datas = {"云台转上", "云台转下", "云台转左", "云台转右", "云台左上", "云台右上",
                "云台左下", "云台右下", "镜头拉近", "镜头拉远", "镜头近焦", "镜头远焦"};
        mDialog = new AlertDialog.Builder(this).setSingleChoiceItems(datas, 0,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.dismiss();
                        sendCtrlCmd(gestureIDs[which]);
                    }
                }).create();
        mDialog.show();
    }

    /**
     * 发送云台控制命令
     *
     * @param gestureID 1-云台转上 、2-云台转下 、3-云台转左 、4-云台转右、 11-云台左上 、12-云台右上 13-云台左下
     *                  、14-云台右下、7-镜头拉近、8-镜头拉远、9-镜头近焦、10-镜头远焦
     */
    private void sendCtrlCmd(final int gestureID) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String sessionID = mServInfo.getSessionID();
                // 云台控制速度 取值范围(1-10)
                int speed = 5;
                Log.i(Constants.LOG_TAG, "ip:" + cameraInfoEx.getAcsIP() + ",port:" + cameraInfoEx.getAcsPort() + ",isPTZControl:" + mUserCap.contains(PTZ_CONTROL));
                // 发送控制命令
                boolean ret = LiveActivity.this.mVmsNetSDK.sendStartPTZCmd(cameraInfoEx.getAcsIP(), cameraInfoEx.getAcsPort(),
                        sessionID, mCameraID, gestureID, speed, 600, 0 + "");
                Log.i(Constants.LOG_TAG, "sendStartPTZCmd ret:" + ret);
            }
        }).start();
    }

    /**
     * 停止云台控制
     */
    private void stopCloudCtrl() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String sessionID = mServInfo.getSessionID();
                boolean ret = LiveActivity.this.mVmsNetSDK.sendStopPTZCmd(cameraInfoEx.getAcsIP(), cameraInfoEx.getAcsPort(), sessionID, mCameraID, "0");
                Log.i(Constants.LOG_TAG, "stopPtzCmd sent,ret:" + ret);
            }
        }).start();
    }

    /**
     * 启动播放 void
     *
     * @since V1.0
     */
    private void startBtnOnClick() {
        vp.setNoScroll(true);
        new Thread() {
            @Override
            public void run() {
                super.run();
                mLiveControl.setLiveParams(getPlayUrl(mStreamType), mName, mPassword);

                if (mLiveControl.LIVE_PLAY == mLiveControl.getLiveState()) {
                    mLiveControl.stop();
                }
                if (mLiveControl.LIVE_INIT == mLiveControl.getLiveState()) {
                    mLiveControl.startLive(mSurfaceView);
                }
            }
        }.start();
    }

    /**
     * 该方法是获取播放地址的，当mStreamType=2时，获取的是MAG，当mStreamType =1时获取的子码流，当mStreamType =
     * 0时获取的是主码流 由于该方法中部分参数是监控点的属性，所以需要先获取监控点信息，具体获取监控点信息的方法见resourceActivity。
     *
     * @param streamType 2、表示MAG取流方式；1、表示子码流取流方式；0、表示主码流取流方式；
     * @return String 播放地址 ：2、表示返回的是MAG的播放地址;1、表示返回的是子码流的播放地址；0、表示返回的是主码流的播放地址。
     * @since V1.0
     */
    private String getPlayUrl(int streamType) {
        String url = "";

        if (mRealPlayURL == null) {
            return null;
        }

        // 获取播放Token
        mToken = LiveActivity.this.mVmsNetSDK.getPlayToken(mServInfo.getSessionID());
        DebugLog.info(Constants.LOG_TAG, "mToken is :" + mToken);

        LogUtils.w("shipin_one", "generateLiveUrl MagStreamSerAddr:" + mServInfo.getMagServer().getMagStreamSerAddr());
        LogUtils.w("shipin_one", "generateLiveUrl MagStreamSerPort:" + mServInfo.getMagServer().getMagStreamSerPort());
        LogUtils.w("shipin_one", "generateLiveUrl cameraId:" + cameraInfoEx.getId());
        LogUtils.w("shipin_one", "generateLiveUrl token:" + mToken);
        LogUtils.w("shipin_one", "generateLiveUrl streamType:" + streamType);
        LogUtils.w("shipin_one", "generateLiveUrl appNetId:" + mServInfo.getAppNetId());
        LogUtils.w("shipin_one", "generateLiveUrl deviceNetID:" + cameraInfoEx.getDeviceNetId());
        LogUtils.w("shipin_one", "generateLiveUrl userAuthority:" + mServInfo.getUserAuthority());
        LogUtils.w("shipin_one", "generateLiveUrl cascadeFlag:" + cameraInfoEx.getCascadeFlag());
        LogUtils.w("shipin_one", "generateLiveUrl internet:" + mServInfo.isInternet());

        LiveInfo liveInfo = new LiveInfo();
        liveInfo.setMagIp(mServInfo.getMagServer().getMagStreamSerAddr());
        liveInfo.setMagPort(mServInfo.getMagServer().getMagStreamSerPort());
        liveInfo.setCameraIndexCode(cameraInfoEx.getId());
        liveInfo.setToken(mToken);
        // 转码不区分主子码流
        liveInfo.setStreamType(streamType);
        liveInfo.setMcuNetID(mServInfo.getAppNetId());
        liveInfo.setDeviceNetID(cameraInfoEx.getDeviceNetId());
        liveInfo.setiPriority(mServInfo.getUserAuthority());
        liveInfo.setCascadeFlag(cameraInfoEx.getCascadeFlag());
        LogUtils.w("shipin_one", "播放的视频:" + new Gson().toJson(liveInfo));
        LogUtils.w("shipin_one", "----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        if (deviceInfo != null) {
            if (cameraInfoEx.getCascadeFlag() == LiveInfo.CASCADE_TYPE_YES) {
                deviceInfo.setLoginName("admin");
                deviceInfo.setLoginPsw("12345");
            }
        }

        if (mServInfo.isInternet()) {
            liveInfo.setIsInternet(LiveInfo.NETWORK_TYPE_INTERNET);
            // 获取不转码地址
            liveInfo.setbTranscode(false);
            mRealPlayURL.setUrl1(mRtspHandle.generateLiveUrl(liveInfo));

            // 获取转码地址
            // 使用默认转码参数cif 128 15 h264 ps
            liveInfo.setbTranscode(true);
            mRealPlayURL.setUrl2(mRtspHandle.generateLiveUrl(liveInfo));
        } else {
            liveInfo.setIsInternet(LiveInfo.NETWORK_TYPE_LOCAL);
            liveInfo.setbTranscode(false);
            // 内网不转码
            mRealPlayURL.setUrl1(mRtspHandle.generateLiveUrl(liveInfo));
            mRealPlayURL.setUrl2("");
        }

        LogUtils.w(Constants.LOG_TAG, "url1:" + mRealPlayURL.getUrl1());
        LogUtils.w(Constants.LOG_TAG, "url2:" + mRealPlayURL.getUrl2());

        url = mRealPlayURL.getUrl1();
        if (streamType == 2 && mRealPlayURL.getUrl2() != null && mRealPlayURL.getUrl2().length() > 0) {
            url = mRealPlayURL.getUrl2();
        }
        Log.i(Constants.LOG_TAG, "mRTSPUrl" + url);

        return url;
    }

    /**
     * 停止播放 void
     *
     * @since V1.0
     */
    private void stopBtnOnClick() {
        if (null != mLiveControl) {
            mLiveControl.stop();
        }
    }

    /**
     * 抓拍 void
     *
     * @since V1.0
     */
    private void captureBtnOnClick() {
        if (null != mLiveControl) {
            // 随即生成一个1到10000的数字，用于抓拍图片名称的一部分，区分图片，开发者可以根据实际情况修改区分图片名称的方法
            int recordIndex = new Random().nextInt(10000);
            boolean ret = mLiveControl.capture(UtilFilePath.getPictureDirPath()
                    .getAbsolutePath(), "Picture" + recordIndex + ".jpg");
            if (ret) {
                UIUtil.showToast(LiveActivity.this, "抓拍成功");
                UtilAudioPlay.playAudioFile(LiveActivity.this, R.raw.paizhao);
            } else {
                UIUtil.showToast(LiveActivity.this, "抓拍失败");
                DebugLog.error(TAG, "captureBtnOnClick():: 抓拍失败");
            }
        }
    }

    /**
     * 录像 void
     *
     * @since V1.0
     */
    private void recordBtnOnClick() {
        if (null != mLiveControl) {
            if (!mIsRecord) {
                // 随即生成一个1到10000的数字，用于录像名称的一部分，区分图片，开发者可以根据实际情况修改区分录像名称的方法
                int recordIndex = new Random().nextInt(10000);
                boolean ret = mLiveControl.startRecord(UtilFilePath
                        .getVideoDirPath().getAbsolutePath(), "Video"
                        + recordIndex + ".mp4");
                if (ret) {
                    UIUtil.showToast(LiveActivity.this, "启动录像成功");
                    mIsRecord = true;
                    mRecordBtn.setText("停止录像");
                } else {
                    UIUtil.showToast(LiveActivity.this, "启动录像失败");
                    DebugLog.error(Constants.LOG_TAG,
                            "recordBtnOnClick():: 启动录像失败");
                }
            } else {
                mLiveControl.stopRecord();
                mIsRecord = false;
                UIUtil.showToast(LiveActivity.this, "停止录像成功");
                mRecordBtn.setText("开始录像");
            }
        }
    }

    /**
     * 音频 void
     *
     * @since V1.0
     */
    private void audioBtnOnClick() {
        if (null != mLiveControl) {
            if (mIsAudioOpen) {
                mLiveControl.stopAudio();
                mIsAudioOpen = false;
                UIUtil.showToast(LiveActivity.this, "关闭音频");
                mAudioBtn.setText("开启音频");
            } else {
                boolean ret = mLiveControl.startAudio();
                if (!ret) {
                    mIsAudioOpen = false;
                    UIUtil.showToast(LiveActivity.this, "开启音频失败");
                    mAudioBtn.setText("音频");
                } else {
                    mIsAudioOpen = true;
                    // 开启音频成功，并不代表一定有声音，需要设备开启声音。
                    UIUtil.showToast(LiveActivity.this, "开启音频成功");
                    mAudioBtn.setText("关闭音频");
                }
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != mLiveControl) {
            if (mIsRecord) {
                mRecordBtn.setText("开始录像");
                mLiveControl.stopRecord();
                mIsRecord = false;
            }
            mLiveControl.stop();
        }
    }

    @Override
    public void onMessageCallback(int messageID) {
        sendMessageCase(messageID);
    }

    /**
     * 返回已经播放的流量 void
     *
     * @return long
     * @since V1.0
     */
    public long getStreamRate() {
        return mStreamRate;
    }

    /**
     * 发送消息
     *
     * @param i void
     * @since V1.0
     */
    private void sendMessageCase(int i) {
        if (null != mMessageHandler) {
            Message msg = Message.obtain();
            msg.arg1 = i;
            mMessageHandler.sendMessage(msg);
        }
    }

    /**
     * 消息类
     *
     * @author huangweifeng
     * @Data 2013-10-23
     */
    @SuppressLint("HandlerLeak")
    private final class MyHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case ConstantLive.RTSP_SUCCESS:
                    // UIUtil.showToast(LiveActivity.this, "启动取流成功");
                    break;
                case ConstantLive.STOP_SUCCESS:
                    UIUtil.showToast(LiveActivity.this, "停止成功");
                    break;
                case ConstantLive.START_OPEN_FAILED:
                    Log.i(Constants.LOG_TAG, "开启播放库失败");
                    // UIUtil.showToast(LiveActivity.this, "开启播放库失败");
                    dismissWaitDialog();
                    vp.setNoScroll(false);
                    break;
                case ConstantLive.PLAY_DISPLAY_SUCCESS:
                    UIUtil.showToast(LiveActivity.this, "播放成功");
                    dismissWaitDialog();
                    vp.setNoScroll(false);
                    break;
                case ConstantLive.RTSP_FAIL:
                    dismissWaitDialog();
                    vp.setNoScroll(false);
                    if (null != mLiveControl) {
                        mLiveControl.stop();
                        if (cameraInfo.isOnline()) {
                            UIUtil.showToast(LiveActivity.this, "网络状态不好，请重新加载！");
                        } else {
                            UIUtil.showToast(LiveActivity.this, "当前设备未开启！");
                        }
                    }
                    break;
                case ConstantLive.GET_OSD_TIME_FAIL:
                    UIUtil.showToast(LiveActivity.this, "获取OSD时间失败");
                    break;

                case ConstantLive.SD_CARD_UN_USEABLE:
                    UIUtil.showToast(LiveActivity.this, "SD卡不可用");
                    break;
                case ConstantLive.SD_CARD_SIZE_NOT_ENOUGH:
                    UIUtil.showToast(LiveActivity.this, "SD卡空间不足");
                    break;
                case ConstantLive.CAPTURE_FAILED_NPLAY_STATE:
                    UIUtil.showToast(LiveActivity.this, "非播放状态不能抓拍");
                    break;
                case ConstantLive.RECORD_FAILED_NPLAY_STATE:
                    UIUtil.showToast(LiveActivity.this, "非播放状态不能录像");
                    break;
                case ConstantLive.AUDIO_START_FAILED_NPLAY_STATE:
                    UIUtil.showToast(LiveActivity.this, "非播放状态不能开启音频");
                    break;
            }
        }
    }
}
