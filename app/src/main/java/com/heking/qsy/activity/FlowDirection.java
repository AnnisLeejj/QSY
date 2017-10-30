package com.heking.qsy.activity;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.util.Tool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FlowDirection extends Activity {
	
	private Intent intent;
	private Bundle bundle;
	
	
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
	setContentView(R.layout.moniter_flow_code_detail);
	intent=getIntent();
	bundle=intent.getExtras();
	Tool.endActivity(this);
	((TextView)findViewById(R.id.flow_title)).setText(bundle.getString("Name"));
	((TextView)findViewById(R.id.flow_node_1)).setText(bundle.getString("QY"));
	((TextView)findViewById(R.id.flow_node_2)).setText(bundle.getString("LiuXiang"));
	
}
 
}
