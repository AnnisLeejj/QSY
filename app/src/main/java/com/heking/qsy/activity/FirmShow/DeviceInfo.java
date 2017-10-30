package com.heking.qsy.activity.FirmShow;

import java.util.List;

public class DeviceInfo {

	public static final String TAG = "ChannelInfo";
	
	public String 				mIpAddr;
	public int	  				mPort;
	public String 				mStrUser;
	public String 				mStrPW;

	public int 				miFirstChannelNo = -1;
	public int 				miLogID = -1;				// return by NET_DVR_Login_v30
	
	public List<ChannelInfo> 	mChannels;
}
