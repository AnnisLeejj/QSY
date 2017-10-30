package com.heking.SPJK.data;

import com.hikvision.vmsnetsdk.CameraInfo;
import com.hikvision.vmsnetsdk.CameraInfoEx;
import com.hikvision.vmsnetsdk.ServInfo;

public class TempData {
    private static TempData ins = new TempData();

    public static TempData getIns() {
        return ins;
    }

    private static String servAddr;
    /**
     * 登录返回的数据
     */
    private static ServInfo loginData;

    /**
     * 监控点信息，用作临时传递数据用
     */
    private CameraInfo cameraInfo;

    private CameraInfoEx cameraInfoEx;

    public static TempData getInstance() {
        return ins;
    }

    /**
     * 设置登录成功返回的信息
     *
     * @param loginData
     * @since V1.0
     */
    public void setLoginData(ServInfo loginData, String servAddr) {
        this.loginData = loginData;
        this.servAddr = servAddr;
    }

    /**
     * 获取登录成功返回的信息
     *
     * @return
     * @since V1.0
     */
    public ServInfo getLoginData() {
        return loginData;
    }

    public static String getServAddr() {
        return servAddr;
    }

    public static void setServAddr(String servAddr) {
        TempData.servAddr = servAddr;
    }

    /**
     * 保存监控点信息
     *
     * @param cameraInfo
     * @since V1.0
     */
    public void setCameraInfo(CameraInfo cameraInfo) {
        this.cameraInfo = cameraInfo;
    }

    /**
     * 获取监控点信息
     *
     * @return
     * @since V1.0
     */
    public CameraInfo getCameraInfo() {
        return cameraInfo;
    }


    public void setCameraInfoEx(CameraInfoEx cameraInfoEx) {
        this.cameraInfoEx = cameraInfoEx;
    }

    public CameraInfoEx getCameraInfoEx() {
        return cameraInfoEx;
    }

}
