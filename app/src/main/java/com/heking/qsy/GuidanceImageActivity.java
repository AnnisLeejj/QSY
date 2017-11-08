package com.heking.qsy;

import java.util.ArrayList;

import com.heking.qsy.util.ViewPageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GuidanceImageActivity extends Activity implements OnPageChangeListener {
    /**
     * 创建读取对象
     */
    private SharedPreferences sharedPreferences;
    /**
     * 创建记录对象
     */
    private SharedPreferences.Editor editor;
    private boolean bool = false;
    private ArrayList<View> list = new ArrayList<View>();
    private ViewPager mViewPager;
    private LinearLayout layout;
    private TextView mTextView;
    private int i = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 实例化对象SharedPreferences
        sharedPreferences = getSharedPreferences("initialize", Activity.MODE_PRIVATE);
        // 实例化对象editor
        editor = sharedPreferences.edit();
        editor.putString("com.heking.qsy", "com.heking.qsy.01");
        editor.commit();
        String data = sharedPreferences.getString("com.heking.qsy", "");
        AppContext.ONE_2 = false;
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        if (data == null || data.equals("") || !data.equals("com.heking.qsy.01")) {
            setContentView(R.layout.guidance_image_activity);
            editor.putString("com.heking.qsy", "com.heking.qsy.01");
            editor.commit();
            bool = true;
        } else {
            setContentView(R.layout.guidance_image_2_activity);
            bool = false;
        }
        iniView();
        iniData();
    }



    private void iniView() {
        if (bool) {
            mViewPager = (ViewPager) findViewById(R.id.guidance_image_view);
            layout = (LinearLayout) findViewById(R.id.button_text_view_image_layout);
            mTextView = (TextView) findViewById(R.id.button_text_view_image);
        } else {
        }
    }

    private void iniData() {

        if (bool) {
            list.add(new ImageView(this));
            list.add(new ImageView(this));
            list.add(new ImageView(this));

            mViewPager.setAdapter(new ViewPageAdapter(list));
            mViewPager.setOnPageChangeListener(this);
            mTextView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    GuidanceImageActivity.this.finish();
                }
            });

        } else {
            findViewById(R.id.to_end).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    GuidanceImageActivity.this.finish();
                }
            });

            String user = sharedPreferences.getString("user", "");
            String password = sharedPreferences.getString("password", "");
            // 如果本地存有用户名则自动登录
            thread.start();

        }
    }

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            for (; i > 0; i--) {
                Log.d("测试数据:", i + "");
                if (i == 1) {
                    startActivity(new Intent(GuidanceImageActivity.this, MainActivity.class));
//                    startActivity(new Intent(GuidanceImageActivity.this, MainActivity.class));
                    GuidanceImageActivity.this.finish();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
        Log.d("测试数据:", "" + arg0);
        if (arg0 == 2) {
            layout.setVisibility(LinearLayout.VISIBLE);
        } else {
            layout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }


    //分享的
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//	@Override
//	public void httpResponse(String json) {
//		if (!json.trim().equals("连接失败")) {
//			RegisteredBean bean = ParsonJson.jsonToBeanObj(json, RegisteredBean.class);
//			if (bean != null && bean.getLoginName() != null) {
//				AppContext.LOGIN = true;
//				AppContext.LoginUserMessage.bean = bean;
//				AppContext.LoginUserMessage.messageUse = true;
//			}
//		}
//		startActivity(new Intent(GuidanceImageActivity.this, MainActivity.class));
//		GuidanceImageActivity.this.finish();
//	}
}
