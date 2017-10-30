package com.heking.qsy.activity.Personalcenters;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.util.Tool;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity {
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
	
	setContentView(R.layout.aboutus);
	((TextView)findViewById(R.id.aboutus_version)).setText("版本号："+Tool.getVerName(this));
	Tool.endActivity(this);
}
}
