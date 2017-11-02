package com.heking.qsy.activity.OpengoVernment;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.Personalcenters.SharePurposed;
import com.heking.qsy.util.JsonFile;
import com.heking.qsy.util.Tool;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import MyUtils.LogUtils.LogUtils;

public class WebLayout  extends Activity{
	private WebView webView;
	private String Context;
	private String Headline;
	private TextView textView;
int  webHeight;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		   if (AppContext.THEME)  {
	           setTheme(R.style.SwitchTheme_1);  
		   }
	       else { 
	           setTheme(R.style.SwitchTheme_2);
	       }
		setContentView(R.layout.web_view_layout);
		findViewById(R.id.textview).setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				finish();
			}
		});
		
		Bundle bundle=getIntent().getExtras();
		Headline=bundle.getString("Headline");
		Context=bundle.getString("Context");
		iniView();
		iniData();

	}
	
	private void iniView() {
		webView=(WebView) findViewById(R.id.webview);
		textView=(TextView) findViewById(R.id.textview01);
	}
	private void iniData() {
		textView.setText("详情");
		webView.loadData(Context, "text/html;charset=UTF-8", null);

        webView.getSettings().setJavaScriptEnabled(true);  
        webView.setWebChromeClient(new WebChromeClient());  
        webView.getSettings().setSupportZoom(true); 
     // 设置出现缩放工具 
        webView.getSettings().setBuiltInZoomControls(true);
     //扩大比例的缩放
     //   webView.getSettings().setUseWideViewPort(true);
     //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
    	
		findViewById(R.id.share_button).setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View arg0) {
//				 JsonFile file=new  JsonFile();
//				 try {
//					file.FileJson(Tool.getfile(Headline+".html"),Context);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}

				webView.post(new Runnable() {
					@Override
					public void run() {
						webView.measure(0, 0);
						webHeight = webView.getMeasuredHeight();
						LogUtils.w("webView", "measuredHeight=" + webHeight);

						Bitmap bitmap = webView.getDrawingCache();
						new SharePurposed(WebLayout.this,webView,webHeight,2);
					}
				});

			}
		});
	}
}
