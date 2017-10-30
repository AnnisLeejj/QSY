package com.heking.qsy.activity.FirmShow;
import java.util.ArrayList;
import java.util.List;
import org.MediaPlayer.PlayM4.Player;
import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_CLIENTINFO;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_IPCHANINFO;
import com.hikvision.netsdk.NET_DVR_IPDEVINFO_V31;
import com.hikvision.netsdk.NET_DVR_IPPARACFG_V40;
import com.hikvision.netsdk.RealPlayCallBack;

import MyUtils.LogUtils.LogUtils;

public class MediaControl {
	private static final String TAG = MediaControl.class.getSimpleName();
	private static MediaControl _instance = null;

	private Context mContext = null;

	private Player m_oPlayerSDK;
	private DeviceInfo mDeviceInfo;
	private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30;

	public static MediaControl getInstance(Context ctx) {
		if (_instance == null) {
			_instance = new MediaControl(ctx);
		}
		return _instance;
	}

	public MediaControl(Context context) {
		mContext = context;

	}

	public boolean initSdk() {
		// init net sdk
		if (!HCNetSDK.getInstance().NET_DVR_Init()) {
			Log.e(TAG, "HCNetSDK init is failed!");
			return false;
		}
		HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/",
				true);

		// init player
		m_oPlayerSDK = Player.getInstance();
		if (m_oPlayerSDK == null) {
			Log.e(TAG, "PlayCtrl getInstance failed!");
			return false;
		}

		return true;
	}

	public DeviceInfo loginAction(String ipAddr, int port, String strUser,
			String strPw) {
		if (mDeviceInfo == null) {
			mDeviceInfo = new DeviceInfo();
		}
		mDeviceInfo.mIpAddr = ipAddr;
		mDeviceInfo.mPort = port;
		mDeviceInfo.mStrUser = strUser;
		mDeviceInfo.mStrPW = strPw;

		try {
			// get instance
			m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
			if (null == m_oNetDvrDeviceInfoV30) {
				Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
				return null;
			}

			// call NET_DVR_Login_v30 to login on, port 8000 as default
			mDeviceInfo.miLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(
					mDeviceInfo.mIpAddr, mDeviceInfo.mPort,
					mDeviceInfo.mStrUser, mDeviceInfo.mStrPW,
					m_oNetDvrDeviceInfoV30);
			if (mDeviceInfo.miLogID < 0) {
				Log.e(TAG, "NET_DVR_Login is failed!Err:"
						+ HCNetSDK.getInstance().NET_DVR_GetLastError());
				return null;
			}

			Log.i(TAG, "NET_DVR_Login is Successful!");
			Log.i(TAG, "Device serial number is "
					+ new String(
							getValidByte(m_oNetDvrDeviceInfoV30.sSerialNumber)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mDeviceInfo;
	}
	
	public void logoutAction() {
		if(mDeviceInfo != null && mDeviceInfo.miLogID > 0) {
			if (!HCNetSDK.getInstance().NET_DVR_Logout_V30(mDeviceInfo.miLogID))
			{
				Log.e(TAG, " NET_DVR_Logout is failed!");
			}
		}
	}

	public List<ChannelInfo> getChannelInfo() {
		if (mDeviceInfo == null || mDeviceInfo.miLogID < 0) {
			LogUtils.w(TAG, "you must Login this Device first!");
			return null;
		}

		if (mDeviceInfo.mChannels == null) {
			NET_DVR_IPPARACFG_V40 struIPPara = new NET_DVR_IPPARACFG_V40();
			HCNetSDK.getInstance().NET_DVR_GetDVRConfig(mDeviceInfo.miLogID,
					HCNetSDK.NET_DVR_GET_IPPARACFG_V40, 0, struIPPara);

			if (struIPPara.dwAChanNum > 0) {
				mDeviceInfo.miFirstChannelNo = 1;
			} else {
				mDeviceInfo.miFirstChannelNo = struIPPara.dwStartDChan;
			}

			if (mDeviceInfo.miFirstChannelNo <= 0) {
				mDeviceInfo.miFirstChannelNo = 1;
			}

			mDeviceInfo.mChannels = new ArrayList<ChannelInfo>();
			int i = 0;
			while (true) {
				if (i == struIPPara.struIPChanInfo.length) {
					break;
				}

				NET_DVR_IPCHANINFO ipchaninfo = struIPPara.struIPChanInfo[i];
				NET_DVR_IPDEVINFO_V31 ipdevinfo = struIPPara.struIPDevInfo[i];
				i++;

				if (ipchaninfo.byEnable != 1) {
					continue;
				}

				ChannelInfo item = new ChannelInfo();

				item.mChannelId = ipchaninfo.byIPID;
				item.mEnable = ipchaninfo.byEnable;
				item.mIpAddr = new String(getValidByte(ipdevinfo.struIP.sIpV4));

				mDeviceInfo.mChannels.add(item);
			}

			Log.i(TAG, "iFirstChannelNo:" + mDeviceInfo.miFirstChannelNo);
		}

		return mDeviceInfo.mChannels;
	}
	
	public boolean setVideoWindow(int port, int regionNum, SurfaceHolder holder) {
		return Player.getInstance().setVideoWindow(port, 0, holder);
	}

	public void realPlay(ChannelInfo channelInfo) {
		if (mDeviceInfo == null || mDeviceInfo.miLogID < 0) {
			LogUtils.w(TAG, "you must Login this Device first!");
			return;
		}
		if (channelInfo == null || channelInfo.miPlayID < 0) {
			if (channelInfo.miPlaybackID >= 0) {
				Log.i(TAG, "Please stop palyback first");
				return;
			}
			RealPlayCallBack fRealDataCallBack = new PlayCallBack(channelInfo);
			if (fRealDataCallBack == null) {
				Log.e(TAG, "fRealDataCallBack object is failed!");
				return;
			}
			NET_DVR_IPPARACFG_V40 struIPPara = new NET_DVR_IPPARACFG_V40();
			HCNetSDK.getInstance().NET_DVR_GetDVRConfig(mDeviceInfo.miLogID, HCNetSDK.NET_DVR_GET_IPPARACFG_V40, 0, struIPPara);
			

			int iFirstChannelNo = -1;		
			if(struIPPara.dwAChanNum > 0)
			{
				iFirstChannelNo = 1;
			}
			else
			{
				iFirstChannelNo = struIPPara.dwStartDChan;
			}
			
			if(iFirstChannelNo <= 0)
			{
				iFirstChannelNo = 1;
			}
			
			Log.i(TAG, "iFirstChannelNo:" +iFirstChannelNo);
			
			NET_DVR_CLIENTINFO ClientInfo = new NET_DVR_CLIENTINFO();
	        ClientInfo.lChannel =  iFirstChannelNo + channelInfo.mChannelId -1; 	// start channel no + preview channel
	        ClientInfo.lLinkMode = 1<<31;  			// bit 31 -- 0,main stream;1,sub stream
	        										// bit 0~30 -- link type,0-TCP;1-UDP;2-multicast;3-RTP 
	        ClientInfo.sMultiCastIP = null;

			// net sdk start preview
			channelInfo.miPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V30(
					mDeviceInfo.miLogID, ClientInfo, fRealDataCallBack, true);
			if (channelInfo.miPlayID < 0) {
				Log.e(TAG, "NET_DVR_RealPlay is failed!Err:"
						+ HCNetSDK.getInstance().NET_DVR_GetLastError());
				return;
			}

			Log.i(TAG,
					"NetSdk Play sucess ***********************3***************************");
		}
	}
	
	public synchronized void stopPlay(ChannelInfo channelInfo) {
		if (channelInfo.miPlayID < 0) {
			Log.d(TAG, "已经停止？");
			return;
		}
		// 停止网络播放
		if (HCNetSDK.getInstance().NET_DVR_StopRealPlay(channelInfo.miPlayID)) {
			Log.i(TAG, "停止实时播放成功！");
		} else {
			Log.e(TAG,
					"停止实时播放失败！");
			return;
		}
		// 停止本地播放
		if (Player.getInstance().stop(channelInfo.miPort)) {
			Log.i(TAG, "停止本地播放成功！");
		} else {
			Log.e(TAG, "停止本地播放失败！");
			return;
		}
		// 关闭视频流
		if (Player.getInstance().closeStream(channelInfo.miPort)) {
			Log.i(TAG, "关闭视频流成功！");
		} else {
			Log.e(TAG, "关闭视频流失败！");
			return;
		}
		// 释放播放端口
		if (Player.getInstance().freePort(channelInfo.miPort)) {
			Log.i(TAG, "释放播放端口成功！");
		} else {
			Log.e(TAG, "释放播放端口失败！");
			return;
		}
		// 播放端口复位
		channelInfo.miPort = -1;
		// 正在播放标记复位
		channelInfo.miPlayID = -1;
		Log.i(TAG, "停止播放成功！");
	}

	private class PlayCallBack implements RealPlayCallBack {
		private ChannelInfo channelInfo;

		public PlayCallBack(ChannelInfo channelInfo) {
			this.channelInfo = channelInfo;
		}

		
		public void fRealDataCallBack(int iRealHandle, int iDataType,
				byte[] pDataBuffer, int iDataSize) {
			processRealData(channelInfo, 1, iDataType, pDataBuffer, iDataSize,
					Player.STREAM_REALTIME);
		}

	}

	public void processRealData(ChannelInfo channelInfo, int iPlayViewNo,
			int iDataType, byte[] pDataBuffer, int iDataSize, int iStreamMode) {
		// Log.i(TAG, "iPlayViewNo:" + iPlayViewNo + ",iDataType:" + iDataType +
		// ",iDataSize:" + iDataSize);
		if (HCNetSDK.NET_DVR_SYSHEAD == iDataType) {
			if (channelInfo.miPort >= 0) {
				return;
			}
			channelInfo.miPort = m_oPlayerSDK.getPort();
			if (channelInfo.miPort == -1) {
				Log.e(TAG,
						"getPort is failed with: "
								+ m_oPlayerSDK.getLastError(channelInfo.miPort));
				return;
			}
			Log.i(TAG, "getPort succ with: " + channelInfo.miPort);
			if (iDataSize > 0) {
				if (!m_oPlayerSDK.setStreamOpenMode(channelInfo.miPort,
						iStreamMode)) // set
				// stream
				// mode
				{
					Log.e(TAG, "setStreamOpenMode failed");
					return;
				}
				if (!m_oPlayerSDK.setSecretKey(channelInfo.miPort, 1,
						"ge_security_3477".getBytes(), 128)) {
					Log.e(TAG, "setSecretKey failed");
					return;
				}
				if (!m_oPlayerSDK.openStream(channelInfo.miPort, pDataBuffer,
						iDataSize, 2 * 1024 * 1024)) // open stream
				{
					Log.e(TAG, "openStream failed");
					return;
				}
				if(channelInfo.mSurfaceView != null && channelInfo.mSurfaceView.getHolder().getSurface().isValid())
				if (!m_oPlayerSDK.play(channelInfo.miPort,
						channelInfo.mSurfaceView.getHolder())) {
					Log.e(TAG, "play failed");
					return;
				}
			}
		} else {
			if (!m_oPlayerSDK.inputData(channelInfo.miPort, pDataBuffer,
					iDataSize)) {
				Log.e(TAG,
						"inputData failed with: "
								+ m_oPlayerSDK.getLastError(channelInfo.miPort));
			}
		}
	}

	private byte[] getValidByte(byte[] params) {
		int i = 0;
		for (; i < params.length; i++) {
			if (params[i] == 0) {
				break;
			}
		}
		byte[] result = new byte[i];
		System.arraycopy(params, 0, result, 0, i);
		return result;
	}

}
