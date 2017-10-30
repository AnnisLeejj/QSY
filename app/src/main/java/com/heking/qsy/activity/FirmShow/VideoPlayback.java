package com.heking.qsy.activity.FirmShow;

import java.util.ArrayList;
import java.util.List;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.util.FirmTypeBean;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

public class VideoPlayback extends Activity {
	private static final String TAG = VideoPlayback.class.getSimpleName();
	private List<ChannelInfo> channels = null;
	private SurfaceView mVSurface1 = null;
	private FirmTypeBean.Data.Monitors monitors;
	private ArrayList<FirmTypeBean.Data.Monitors> list;
	int proenat;
	ViewPager vp;
	private final Context context=this;
	private ArrayList<View> surfaceViewList;
	MyPagerAdapter pagerAdapter;
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		   if (AppContext.THEME)  {
	           setTheme(R.style.SwitchTheme_1);  
		   }
	       else { 
	           setTheme(R.style.SwitchTheme_2);
	       }
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_play_back_activitty);
		iniView();
		iniData();
	}

	@SuppressWarnings("unchecked")
	private void iniView() {
		list=(ArrayList<FirmTypeBean.Data.Monitors>) getIntent().getExtras().getSerializable("MONITORS");
		proenat=getIntent().getExtras().getInt("proenat");
		//mVSurface1 = (SurfaceView) findViewById(R.id.video_full_surface);
		vp=(ViewPager)findViewById(R.id.vp);
		surfaceViewList=new ArrayList<View>();
		
		if(list!=null&&list.size()>0){
			SurfaceView surfaceView=new SurfaceView(context);
			surfaceViewList.add(surfaceView);
			pagerAdapter=new MyPagerAdapter(surfaceViewList);
			vp.setAdapter(pagerAdapter);
			vp.setCurrentItem(proenat);
			
			mVSurface1= (SurfaceView)surfaceViewList.get(proenat);
			monitors=list.get(proenat);
			vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int index) {
					// TODO Auto-generated method stub
					mVSurface1= (SurfaceView)surfaceViewList.get(index);
					monitors=list.get(index);
					iniData();
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		findViewById(R.id.textview).setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	private void iniData( ) {
		
		if (MediaControl.getInstance(this).initSdk() == false) {
			Log.e(TAG, "初始化sdk失败！");
			return;
		}
		Log.i(TAG, "初始化成功！");

		if (MediaControl.getInstance(this).loginAction(monitors.getIP(), Integer.parseInt(monitors.getPort()),monitors.getUserName(), monitors.getPassword()) == null) {
			Dialog dialog = new AlertDialog.Builder(this)
			.setTitle("提示信息")
			.setMessage("获取监控视频失败!")
			.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0,
								int arg1){

							VideoPlayback.this.finish();
						}
					}).show();
			dialog.setCanceledOnTouchOutside(false);
			return;
		}else{
		Log.e(TAG, "登录成功！");
		}
//		if (MediaControl.getInstance(this).loginAction(monitors.getIP(),new Integer( monitors.getPort()), monitors.getUserName(),monitors.getPassword()) == null) {
//			Dialog dialog = new AlertDialog.Builder(this)
//			.setTitle("提示信息")
//			.setMessage("获取视频失败！")
//			.setPositiveButton("确定",
//					new DialogInterface.OnClickListener() {
//
//						
//						public void onClick(DialogInterface arg0,
//								int arg1) {
//
//							VideoPlayback.this.finish();
//						}
//					}).show();
//			dialog.setCanceledOnTouchOutside(false);
//			return;
//		}
		channels = MediaControl.getInstance(this).getChannelInfo();
		if (channels == null || channels.size() <= 0) {
			Dialog dialog = new AlertDialog.Builder(this)
			.setTitle("提示信息")
			.setMessage("获取监控视频失败！")
			.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0,
								int arg1){

							VideoPlayback.this.finish();
						}
					}).show();
			dialog.setCanceledOnTouchOutside(false);
			return;
		}
		Log.e(TAG, "获取通道成功！通道数=" + channels.size());
		// onResume();
iniset();
	}

	private void iniset() {
		if (channels != null && channels.size() > 0) {
			ChannelInfo channel = null;
			for (int i = 0; i < channels.size(); i++) {
				channel = channels.get(i);
				break;
			}
			if (channel != null) {
				setSurfaceHolderCallback(mVSurface1, channel);
			}
			if (channel == null) {
				Log.d("TAG", "获取监控视频信息失败！");

				Dialog dialog = new AlertDialog.Builder(this)
						.setTitle("提示信息")
						.setMessage("获取监控视频失败！")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									
									public void onClick(DialogInterface arg0,
											int arg1) {

										VideoPlayback.this.finish();
									}
								}).show();
				dialog.setCanceledOnTouchOutside(false);
				// VideoFullActivity.this.finish();

			}
		}
	}

	

	
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}


	
	protected void onDestroy() {
		MediaControl.getInstance(VideoPlayback.this).logoutAction();
		super.onDestroy();
	}

	private void setSurfaceHolderCallback(final SurfaceView surface,
			final ChannelInfo channelInfo) {

		surface.getHolder().addCallback(new SurfaceHolder.Callback() {
			
			public void surfaceCreated(SurfaceHolder holder) {
				channelInfo.mSurfaceView = surface;
				MediaControl.getInstance(VideoPlayback.this).realPlay(
						channelInfo);

				Log.i(TAG, "surface is created" + channelInfo.miPort);
				if (-1 == channelInfo.miPort) {
					return;
				}
				Surface surface = holder.getSurface();
				if (true == surface.isValid()) {
					if (false == MediaControl.getInstance(VideoPlayback.this)
							.setVideoWindow(channelInfo.miPort, 0, holder)) {
						Log.e(TAG, "Player setVideoWindow failed!");
					}
				}
			}

			
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}

			
			public void surfaceDestroyed(SurfaceHolder holder) {
				Log.i(TAG, "Player setVideoWindow release!"
						+ channelInfo.miPort);
				if (-1 == channelInfo.miPort) {
					return;
				}
				if (true == holder.getSurface().isValid()) {
					if (false == MediaControl.getInstance(VideoPlayback.this)
							.setVideoWindow(channelInfo.miPort, 0, null)) {
						Log.e(TAG, "Player setVideoWindow failed!");
					}
				}
			}
		});
	}

}
