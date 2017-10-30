package com.heking.qsy;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.util.DisplayMetrics;

import com.baidu.mapapi.SDKInitializer;
import com.heking.qsy.Model.AllCameraInfo;
import com.heking.qsy.activity.OpengoVernment.OpenGoverBean;
import com.heking.qsy.activity.Personalcenters.util.RegisteredBean;
import com.heking.qsy.complaintreporting.ComplaintReportingBean;
import com.heking.qsy.util.AppUtils.AppUtils;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.MessageSearch;
import com.heking.qsy.util.Tool;
import com.hik.mcrsdk.MCRSDK;
import com.baidu.mapapi.search.core.PoiInfo;
import com.hik.mcrsdk.rtsp.RtspClient;
import com.hikvision.vmsnetsdk.CameraInfo;
import com.hikvision.vmsnetsdk.VMSNetSDK;
import com.mob.MobApplication;

public class AppContext extends MobApplication {
    private static AppContext ins;
    private static int code;
    public static ArrayList<PoiInfo> listmap = new ArrayList<PoiInfo>();
    public static ArrayList<CameraInfo> shipins_toshow;
    public static ArrayList<CameraInfo> shipins = new ArrayList<>();
    public static AllCameraInfo all_camer;
    public static   Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        code = Tool.getVerCode(getApplicationContext());
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        ScrnParameter.screenWidth = metrics.widthPixels;
        ScrnParameter.screenHenight = metrics.heightPixels;
        ScrnParameter.displayoupixels = ScrnParameter.screenWidth
                * ScrnParameter.screenHenight;
        ScrnParameter.scale = ScrnParameter.screenWidth / 480.0;
        ScrnParameter.density = metrics.density;
        ScrnParameter.densityDpi = metrics.densityDpi;
        ins = this;
        System.loadLibrary("gnustl_shared");
        MCRSDK.init();
        RtspClient.initLib();
        MCRSDK.setPrint(1, null);
        VMSNetSDK.getInstance().openLog(true);
        SDKInitializer.initialize(this);
        //分享
//        MobSDK.init(this, "217b53b948c70", "130caf072b952f12f604504687018627");
//        MobSDK.init(this);
//       // MobLink.initSDK(this, "");
        //MobLink.initSDK(this, "217b53b948c70");
        AppUtils.init(this);
    }

    public static AppContext getIns() {
        return ins;
    }

    /**
     * 地址请求参数类
     *
     * @author jhw
     */
    public static class Parameter {

        /**
         * 企业数据地址
         */
        public static String GET_FIRM_DATA = "http://117.173.38.55:84/Android";

        /**
         * 获取用户信息
         */
        public static String GET_USER = "Account/GetUser";
        /**
         * 获取最性版本
         */
        public static String GET_VERSION = "Version/getVersion/" + code;
        /**
         * 判断手机号是否存在
         */
        public static String GET_CHECK_MOBILE_NUMBER = "Account/CheckMobileNumber/";

        /**
         * 获取企业数据
         */
        public static String GET_FIRM_TYPE = "Firm/getFirm";

        /**
         * 获取我要点评
         */
        public static String Get_REVIEW = "PZH/getFirmReview";
        /**
         * 请求全部数据
         */
        public static String ALL = "?page=0&rows=999999";

        /**
         * 政务公开和曝光台请求参地址段
         */
        public static String ZWGK = "Doncument/getDoncument/";
        /**
         * 获取投诉举报Home页显示内容参数地址段
         */
        public static String GET_COMPLAINT_SUGGESTION = "/ComplaintSuggestion/GetComplaintsuggestion";

    }

    /**
     * 数据提交参数类
     *
     * @author jhw
     */
    public static class Submit {
        /**
         * 点评提交
         */
        public static String SUBMIT_TEVIEW = "PZH/SaveFirmReview";

        /**
         * 药品信息添加
         */
        public static String SUBMIT_DRUG = "PZH/SaveDrugAdd";

        /**
         * 注册
         */
        public static String REGISTERED = "Account/AccountRegister";

        /**
         * 登录
         */
        public static String LOGIN = "Account/Login";

        /**
         * 修改头像
         */
        public static String UPDATE_USER_IMAGE = "Account/UpdateUserImage";
        /**
         * 修改资料
         */
        public static String UPDATE_USER_FOR_PZH = "Account/UpdateUserForPZH";

        /**
         * 修改密码
         */
        public static String CHANGE_JASSWOED = "Account/ChangePassword";
        public static String FEEDBACK = "pzh/SaveFeedback";
    }

    public static class Message {
        public static ArrayList<MessageSearch> list;

    }

    public static class ChangeMess {
        public static String name = "";
        public static String Email = "";
    }

    public static class List {
        public static ArrayList<File> fileList;
        public static ArrayList<FirmTypeBean.Data> FirmTapeDataList;
        public static ArrayList<OpenGoverBean.Data> OpenGoverlist;
        public static ArrayList<ComplaintReportingBean.Data> ComplaintReportinglist;
    }

    public static class BundelPoiMess {
        //public static PoiItem poiItem;
        public static PoiInfo poiInfo;
        //public static NaviLatLng toLatlng;
        public static String Address;
    }

    public static class LoginUserMessage {
        public static RegisteredBean bean;
        public static boolean messageUse = false;
        public static final int I_WANT_TO_REVIEW = 808099301;
        public static final int FOR_THE_CONVENIENCE_OF_SERVICE = 808099302;
        public static final int LOGIN_IN_TO = 808099303;
        public static int STATE = 0;
    }

    public static class Cookie {
        public static String Cookie = "";
    }

    /**
     * 设备标识
     */
    public static String DEVICE = android.os.Build.MODEL;

    /**
     * 主题更换
     */
    public static boolean THEME = true;
    public static boolean LOGIN = false;
    public static int BUTTON_TU = 1;
    public static boolean boos = true;
    /**
     * 是否是第一次执行
     */
    public static boolean ONE = true;
    public static boolean ONE_1 = true;
    public static boolean ONE_2 = true;

    /**
     * 屏幕参数
     *
     * @author jhw
     */
    public static class ScrnParameter {
        public static int screenWidth;
        public static int screenHenight;
        public static int displayoupixels;
        public static int densityDpi;
        public static double scale;
        public static float density;
    }
}
