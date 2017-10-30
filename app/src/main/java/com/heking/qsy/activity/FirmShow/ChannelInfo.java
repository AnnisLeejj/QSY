package com.heking.qsy.activity.FirmShow;

import java.io.Serializable;

import android.view.SurfaceView;

public class ChannelInfo implements Serializable {


	private static final long serialVersionUID = 4080437048025026775L;
	
	public int 				miPlayID = -1;				// return by NET_DVR_RealPlay_V30
	public int				miPlaybackID = -1;				// return by NET_DVR_PlayBackByTime	
	public int				miPort = -1;				// play port
	
	public int		mChannelId = -1;
	public int 		mEnable = 0;
	public String 	mIpAddr = null;
	
	public SurfaceView mSurfaceView;
}
