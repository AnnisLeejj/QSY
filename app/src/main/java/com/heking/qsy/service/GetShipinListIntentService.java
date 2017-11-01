package com.heking.qsy.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heking.SPJK.callback.MsgIds;
import com.heking.SPJK.constants.Constants;
import com.heking.SPJK.data.Config;
import com.heking.SPJK.data.TempData;
import com.heking.SPJK.live.LiveActivity;
import com.heking.qsy.AppContext;
import com.heking.qsy.MainActivity;
import com.heking.qsy.Model.AllCameraInfo;
import com.heking.qsy.activity.FirmShow.MonitoringVideoHK;
import com.heking.qsy.activity.regulatory.JsongDataBean;
import com.heking.qsy.activity.regulatory.details.ProcurementWarehousingActivity;
import com.heking.qsy.activity.regulatory.tab.TB_CaiGouRuKu;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;
import com.hikvision.vmsnetsdk.CameraInfo;
import com.hikvision.vmsnetsdk.ControlUnitInfo;
import com.hikvision.vmsnetsdk.RegionInfo;
import com.hikvision.vmsnetsdk.ServInfo;
import com.hikvision.vmsnetsdk.VMSNetSDK;

import java.util.ArrayList;
import java.util.List;

import MyUtils.LogUtils.LogUtils;
import MyUtils.SharePrefenceUtils.SPUtils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetShipinListIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.heking.qsy.service.action.FOO";
    private static final String ACTION_BAZ = "com.heking.qsy.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.heking.qsy.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.heking.qsy.service.extra.PARAM2";

    public GetShipinListIntentService() {
        super("GetShipinListIntentService");
    }

    private String url = "pzh/GetMonitoringVideo";
    private String url_all_camer = "Firm/GetMo?page=0&rows=999999";
    private String servAddr = "117.173.43.121:4443";
    private String userName = "admin";
    private String password = "heking03*12";
    private String macAddress = "";
    private ServInfo servInfo = new ServInfo();//HK的登录信息

    @Override
    protected void onHandleIntent(Intent intent) {

        //如果有先拿出来
        String myCamer = SPUtils.init(getApplication()).getString("all_camer");
        if (!TextUtils.isEmpty(myCamer)) {
            AppContext.all_camer = new Gson().fromJson(myCamer, AllCameraInfo.class);
        }
        //有没有都继续获取

        //海康数据
        //SPUtils.init(this).put("level_shipin", 0);
        logHK();//登录HK
        //大仙的后台数据
        getAllCamerInfo();
    }

    private void getAllCamerInfo() {
        //就算有也要在获取,以防数据更新
        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET_ZS_SP + url_all_camer).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String json) {
                if (TextUtils.isEmpty(json) || json.equals("连接失败")) return;
                AllCameraInfo all_camer = new Gson().fromJson(json, AllCameraInfo.class);
                if (all_camer != null) {
                    AppContext.all_camer = all_camer;
                    SPUtils.init(getApplication()).put("all_camer", json);
                    // LogUtils.w("shipin_add", "getAllCamerInfo:"+json);
                }
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }

    private void logHK() {

        //获取权限
        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET_ZS_SP + url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(String json) {
                LogUtils.w("shipin_add", "LogInHk 构造方法 toHttpGEtandPost  网路数据:" + json);
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
                        // 登录请求
                        boolean ret = VMSNetSDK.getInstance().login(servAddr, userName, password, macAddress, servInfo);//保存过来的数据
                        SPUtils.init(getApplication()).put("shipinInfo", servInfo);
                        // LogUtils.w("shipin_add", "视频保存的信息:" + ret + "   " + SPUtils.init(getApplication()).getString("shipinInfo"));/// new Gson().toJson(servInfo)
                        if (servInfo != null) {
                            SPUtils.init(getApplication()).put("quanxian", servInfo);
                        }
                        if (ret) {
                            TempData.getInstance().setLoginData(servInfo, servAddr);
                            startToGetInfo();
                            // 设置视图,并开始了后面的请求
                            //登录成功
                        } else {
                            //登录失败
                        }
                    }
                }).start();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.w("shipin_add", "LogInHk onError"  );
            }
        });
    }

    private void startToGetInfo() {
        /**
         * 请求资源列表
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtils.w("shipin_add", "HK 开始获取数据");

                int pId = 0;
                if (Constants.Resource.TYPE_CTRL_UNIT == pResType) {
                    //  pId = pCtrlUnitId;
                } else if (Constants.Resource.TYPE_REGION == pResType) {
                    //  pId = pRegionId;
                }
                reqResList(pResType, pId, 0);
            }
        }).start();
    }

    /**
     * 第一次请求资源列表
     */
    private void requestFirstList(int level) {
        //首要获取这两个值
        String servAddr = Config.getIns().getServerAddr();
        ServInfo loginData = TempData.getIns().getLoginData();

        if (loginData == null) {
            Log.i(Constants.LOG_TAG, "requestFirstList loginData:" + loginData);
            return;
        }
        String sessionID = loginData.getSessionID();
        int controlUnitID = 0;// 首次获取数据，表示根目录
        int numPerPage = 10000;// 此处传10000，由于实际不可能有那么多，表示获取所有数据
        int curPage = 1;
        List<ControlUnitInfo> ctrlUnitList = new ArrayList<ControlUnitInfo>();
        // 获取控制中心列表
        boolean ret = VMSNetSDK.getInstance().getControlUnitList(servAddr, sessionID, String.valueOf(controlUnitID), numPerPage,
                curPage, ctrlUnitList);
        // Log.i(Constants.LOG_TAG, "getControlUnitList ret:" + ret);
        if (ctrlUnitList != null && !ctrlUnitList.isEmpty()) {
            for (ControlUnitInfo info : ctrlUnitList) {
                //Log.i(Constants.LOG_TAG, "name:" + info.getName() + ",controlUnitID:" + info.getControlUnitID() + ",parentID:"+ info.getParentID());
            }
        }
        //  Log.i(Constants.LOG_TAG, "allData size is " + ctrlUnitList.size());
        if (!ret) {
            //Log.e(Constants.LOG_TAG, "Invoke VMSNetSDK.getControlUnitList failed:" + errDesc());
        }
        onInfoResponse(ret ? MsgIds.GET_C_F_NONE_SUC : MsgIds.GET_C_F_NONE_FAIL, ctrlUnitList, level);
    }

    public void onInfoResponse(int msgId, Object data, int level) {
        switch (msgId) {
            // 获取控制中心列表成功
            case MsgIds.GET_C_F_NONE_SUC:
                // 从控制中心获取下级资源列表成功
            case MsgIds.GET_SUB_F_C_SUC:
                // 从区域获取下级列表成功
            case MsgIds.GET_SUB_F_R_SUC:
                refreshResList((ArrayList) data, level);
                break;
            // 获取控制中心列表失败
            case MsgIds.GET_C_F_NONE_FAIL:
                // 调用getControlUnitList失败
            case MsgIds.GET_CU_F_CU_FAIL:
                // 调用getRegionListFromCtrlUnit失败
            case MsgIds.GET_R_F_C_FAIL:
                // 调用getCameraListFromCtrlUnit失败
            case MsgIds.GET_C_F_C_FAIL:
                // 从控制中心获取下级资源列表成失败
            case MsgIds.GET_SUB_F_C_FAIL:
                // 调用getRegionListFromRegion失败
            case MsgIds.GET_R_F_R_FAIL:
                // 调用getCameraListFromRegion失败
            case MsgIds.GET_C_F_R_FAIL:
                // 从区域获取下级列表失败
            case MsgIds.GET_SUB_F_R_FAILED:
                // onGetResListFailed();
                break;
            case GOTO_LIVE_OR_PLAYBACK:
                CameraInfo cInfo = (CameraInfo) data;
                gotoLive(cInfo);
                break;
            default:
                break;
        }

    }

    /**
     * 实时预览
     *
     * @param info
     */
    private void gotoLive(CameraInfo info) {
        if (info == null) {
            Log.e(Constants.LOG_TAG, "gotoLive():: fail");
            return;
        }
        Intent intent = new Intent(this, LiveActivity.class);
        intent.putExtra(Constants.IntentKey.CAMERA_ID, info.getId());
        TempData.getIns().setCameraInfo(info);
        this.startActivity(intent);
    }

    private static final int GOTO_LIVE_OR_PLAYBACK = 0x0b;
    private boolean isName = false;
    /**
     * 父节点资源类型，TYPE_UNKNOWN表示首次获取资源列表
     */
    private int pResType = Constants.Resource.TYPE_UNKNOWN;
    /**
     * 企业分类
     */
    private String Firm_FL;
    /**
     * 企业名称
     */
    private String FirmName;
    /**
     * 企业类型
     */
    private String FirmType;
    private String FirmNames;
    private FirmTypeBean.Data Firmdata;
    private boolean isReturn = true;
    private ArrayList Objectdatalist = new ArrayList<Object>();//数据保存的位置

    private ArrayList<Object> Savelist = new ArrayList<Object>(); //查看下一级


    /**
     * 获取数据成功后刷新列表
     *
     * @param data
     */
    // TODO
    @SuppressWarnings("unchecked")
    private void refreshResList(ArrayList data, int level) {
        String json = new Gson().toJson(data);
        // LogUtils.w("shipin", "level:" + level + "    刷新的数据:" + json);
        if (data == null || data.isEmpty()) {
            Log.w("shipin", "没有获取到企业摄像头数据:");
            //没有获取到企业摄像头数据
            return;
        }

        //  if (false) {
        if (!isName) {
            for (int i = 0; i < data.size(); i++) {
                final Object itemData = data.get(i);

                if (itemData instanceof CameraInfo) {//CameraInfo extends Camera     获取到了摄像头的信息,结束dialog
                    AppContext.shipins.add((CameraInfo) itemData);
                    LogUtils.w("shipin", "itemData instanceof CameraInfo");
                    CameraInfo info = (CameraInfo) itemData;
                }
                if (itemData instanceof ControlUnitInfo) {// ControlUnitInfo extends ControlUnit
                    //  LogUtils.w("shipin", "itemData instanceof ControlUnitInfo");
                    ControlUnitInfo info = (ControlUnitInfo) itemData;
                    if (info.getName().equals("主控中心")) {
                        // LogUtils.w("shipin", "info.getName().equals(\"主控中心\")");
                        //第一次请求
                        getInfo(itemData, level);
                        return;
                    } else {
                        getInfo(itemData, level);
                    }
//                    if (info.getName().equals(Firm_FL)) {
//                        LogUtils.w("shipin", "2222222222222222" + "   level:" + level);
//                        //第二次循环
//                        getInfo(itemData);
//                        return;
//                    }
//                    if (info.getName().equals(Firmdata.getAreaName())) {
//                        LogUtils.w("shipin", "333333333333333" + "   level:" + level);
//                        //第二次循环
//                        getInfo(itemData);
//                        return;
//                    }
//                    if (info.getName().equals(FirmName)) {
//                        LogUtils.w("shipin", "444444444444444" + "   level:" + level);
//                        isName = true;
//                        getInfo(itemData);
//                        return;
//                    }
//                    if (info.getName().equals(FirmType)) {
//                        LogUtils.w("shipin", "5555555555555" + "   level:" + level);
//                        getInfo(itemData);
//                        return;
//                    }
//                    if (Firm_FL.equals("食品企业")) {
//                        LogUtils.w("shipin", "666666666666666" + "   level:" + level);
//                        if (info.getName().contains(FirmNames)) {
//                            getInfo(itemData);
//                            return;
//                        }
//                    }
//                    if (Firm_FL.equals("药品企业") && !FirmType.equals("生产企业")
//                            && !FirmType.equals("医疗机构")) {
//                        LogUtils.w("shipin", "77777777777777777" + "   level:" + level);
//                        if (info.getName().contains(FirmNames)) {
//                            getInfo(itemData);
//                            return;
//                        }
//                    }
                }
            }
        }
        LogUtils.w("shipin", "我保存的信息:" + AppContext.shipins.size());
    }

    private void getInfo(final Object itemData, final int level) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (itemData instanceof CameraInfo) {
                    CameraInfo info = (CameraInfo) itemData;
                    // 得到摄像头，进行预览或者回放
                    Log.i(Constants.LOG_TAG, "get Camera:" + info.getName() + "---"
                            + info.getDeviceID());
                    onInfoResponse(GOTO_LIVE_OR_PLAYBACK, info, level);
                } else {
                    int pId = 0;
                    if (itemData instanceof ControlUnitInfo) {
                        ControlUnitInfo info = (ControlUnitInfo) itemData;
                        pResType = Constants.Resource.TYPE_CTRL_UNIT;
                        pId = Integer.parseInt(info.getControlUnitID());
                    }

                    if (itemData instanceof RegionInfo) {
                        RegionInfo info = (RegionInfo) itemData;
                        pResType = Constants.Resource.TYPE_REGION;
                        pId = Integer.parseInt(info.getRegionID());
                    }
                    reqResList(pResType, pId, level);
                }
            }
        }).start();
    }

    /**
     * 请求下一级列表资源
     *
     * @param pType 父节点资源类型 <li>TYPE_UNKNOWN-首次获取资源，父节点默认id为0</li> <li>TYPE_CTRL_UNIT-父节点为控制中心，父节点资源id传控制中心id即可</li> <li>
     *              TYPE_REGION-父节点为区域，父资源id传区域id即可</li>
     * @param pId   父节点资源id
     */

    public void reqResList(int pType, int pId, int level) {
        switch (pType) {
            case Constants.Resource.TYPE_UNKNOWN:// 第一次请求资源列表
                LogUtils.w("shipin_add", "Constants.Resource.TYPE_UNKNOWN" + "       pId:" + pId);
                requestFirstList(level);
                break;
            case Constants.Resource.TYPE_CTRL_UNIT:// 从控制中心获取子资源列表
                LogUtils.w("shipin_add", "Constants.Resource.TYPE_CTRL_UNIT" + "       pId:" + pId);
                requestSubResFromCtrlUnit(pId, level);
                break;
            case Constants.Resource.TYPE_REGION:// 从区域获取子资源列表
                LogUtils.w("shipin_add", "Constants.Resource.TYPE_REGION" + "       pId:" + pId);
                requestSubResFromRegion(pId, level);
                break;
            default:
                break;
        }
    }

    /**
     * 从控制中心获取下一级资源列表
     *
     * @param pId 父控制中心id
     */
    private void requestSubResFromCtrlUnit(int pId, int level) {
        boolean responseFlag = false;
        String servAddr = Config.getIns().getServerAddr();
        ServInfo loginData = TempData.getIns().getLoginData();
        if (loginData == null) {
            Log.i(Constants.LOG_TAG, "requestSubResFromCtrlUnit loginData:" + loginData);
            return;
        }
        String sessionID = loginData.getSessionID();
        int controlUnitID = pId;// 控制中心id
        int numPerPage = 10000;// 此处取10000，表示每页获取的数量，这个数值可以根据实际情况进行修改
        int curPage = 1;// 当前获取的数据是第几页
        List<ControlUnitInfo> ctrlUnitList = new ArrayList<ControlUnitInfo>();

        // 1.从控制中心获取控制中心
        boolean ret = VMSNetSDK.getInstance().getControlUnitList(servAddr, sessionID, String.valueOf(controlUnitID), numPerPage,
                curPage, ctrlUnitList);
        Log.i(Constants.LOG_TAG, "getControlUnitList ret:" + ret);
        responseFlag = responseFlag || ret;
        if (!ret) {
            //  Log.e(Constants.LOG_TAG, "Invoke VMSNetSDK.getControlUnitList failed:" + errDesc());
        }
        List<RegionInfo> regionList = new ArrayList<RegionInfo>();
        // 2.从控制中心获取区域列表
        ret = VMSNetSDK.getInstance().getRegionListFromCtrlUnit(servAddr, sessionID, String.valueOf(controlUnitID), numPerPage,
                curPage, regionList);
        Log.i(Constants.LOG_TAG, "getRegionListFromCtrlUnit ret:" + ret);
        responseFlag = responseFlag || ret;
        if (!ret) {
            // Log.e(Constants.LOG_TAG, "Invoke VMSNetSDK.getRegionListFromCtrlUnit failed:" + errDesc());
        }
        List<CameraInfo> cameraList = new ArrayList<CameraInfo>();
        // 3.从控制中心获取摄像头列表
        ret = VMSNetSDK.getInstance().getCameraListFromCtrlUnit(servAddr, sessionID, String.valueOf(controlUnitID), numPerPage,
                curPage, cameraList);
        Log.i(Constants.LOG_TAG, "getCameraListFromCtrlUnit ret:" + ret);
        responseFlag = responseFlag || ret;
        if (!ret) {
            //   Log.e(Constants.LOG_TAG, "Invoke VMSNetSDK.getCameraListFromCtrlUnit failed:" + errDesc());
        }
        List<Object> allData = new ArrayList<Object>();
        allData.addAll(ctrlUnitList);
        allData.addAll(regionList);
        allData.addAll(cameraList);

        Log.i(Constants.LOG_TAG, "allData size is " + allData.size());
        onInfoResponse(responseFlag ? MsgIds.GET_SUB_F_C_SUC : MsgIds.GET_SUB_F_C_FAIL, allData, level);

    }

    /**
     * 从区域获取下一级资源列表
     *
     * @param pId 父区域id
     */
    private void requestSubResFromRegion(int pId, int level) {

        boolean responseFlag = false;

        String servAddr = Config.getIns().getServerAddr();
        ServInfo loginData = TempData.getIns().getLoginData();
        if (loginData == null) {
            Log.i(Constants.LOG_TAG, "getRegionListFromRegion loginData : " + loginData);
            return;
        }
        int numPerPage = 10000;
        int curPage = 1;

        List<RegionInfo> regionList = new ArrayList<RegionInfo>();
        // 1.从区域获取区域列表
        boolean ret = VMSNetSDK.getInstance().getRegionListFromRegion(servAddr, loginData.getSessionID()
                , String.valueOf(pId), numPerPage, curPage, regionList);
        Log.i(Constants.LOG_TAG, "getRegionListFromRegion ret : " + ret);
        responseFlag = responseFlag || ret;
        if (!ret) {
            // Log.e(Constants.LOG_TAG, "Invoke VMSNetSDK.getRegionListFromRegion failed:" + errDesc());
        }

        List<CameraInfo> cameraList = new ArrayList<CameraInfo>();
        // 2.从区域获取监控点（摄像头）列表
        ret = VMSNetSDK.getInstance().getCameraListFromRegion(servAddr, loginData.getSessionID(), String.valueOf(pId), numPerPage, curPage,
                cameraList);
        Log.i(Constants.LOG_TAG, "getCameraListFromRegion ret : " + ret);
        responseFlag = responseFlag || ret;
        if (!ret) {
            //  Log.e(Constants.LOG_TAG, "Invoke VMSNetSDK.getCameraListFromRegion failed:" + errDesc());
        }

        List<Object> allData = new ArrayList<Object>();
        allData.addAll(regionList);
        allData.addAll(cameraList);
        LogUtils.w("shipin_add", "regionList:" + regionList.size() + "         cameraList:" + cameraList.size());
        onInfoResponse(responseFlag ? MsgIds.GET_SUB_F_R_SUC : MsgIds.GET_SUB_F_R_FAILED, allData, level);
    }

    /**
     * 获取登录设备mac地址
     *
     * @return
     */
    protected String getMacAddr() {
        WifiManager wm = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        String mac = wm.getConnectionInfo().getMacAddress();
        return mac == null ? "" : mac;
    }

}