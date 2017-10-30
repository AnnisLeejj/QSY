package com.heking.qsy.activity.Personalcenters;

import java.util.HashMap;
import java.util.Map;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeedbackActivity extends Activity {
	private EditText mEditText;
	private Button mButton;
	private WaitDialog dialog;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	   if (AppContext.THEME)  {
           setTheme(R.style.SwitchTheme_1);  
	   }
       else { 
           setTheme(R.style.SwitchTheme_2);
       }
	setContentView(R.layout.layout_opinion);
	Tool.endActivity(this);
	
	iniView();
	iniData();
}

private void iniData() {
	mButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			dialog = new WaitDialog(FeedbackActivity.this);
			dialog.setContent("请稍后");
			dialog.show();
			String string=mEditText.getText().toString().trim();
			Map<String, String> map=new HashMap<String, String>();
			map.put("Context", string);
			map.put("Name",AppContext.LoginUserMessage.bean!=null?AppContext.LoginUserMessage.bean.getFullName():"匿名者");
			HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET+AppContext.Submit.FEEDBACK).
					subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
				@Override
				public void onSubscribe(Disposable d) {

				}

				@Override
				public void onSuccess(String json) {
					Looper.prepare();
					if(json.equals("连接失败")){
						Toast.makeText(FeedbackActivity.this, "连接失败", Toast.LENGTH_LONG).show();
					}else{

						dialog.dismiss();
						Toast.makeText(FeedbackActivity.this, "感谢您的反馈", Toast.LENGTH_LONG).show();
						FeedbackActivity.this.finish();

					}
					Looper.loop();
				}

				@Override
				public void onError(Throwable e) {

				}
			});

		}
	});
	
}

private void iniView() {
	
	mEditText=(EditText) findViewById(R.id.opinion_content);
	mButton=(Button) findViewById(R.id.opinion_btnSave);
//	((TextView)findViewById(R.id.version_name)).setText("版本号："+Tool.getVerName(this));
	
}
}
