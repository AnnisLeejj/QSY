package com.heking.qsy.activity.Personalcenters;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ChangeNameActivity extends Activity implements OnClickListener{
	private EditText changename;
	private Button comlite;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changename);
		Intent intent=getIntent();
		String name=intent.getStringExtra("name");
		changename=(EditText)findViewById(R.id.changename_tv);
		comlite=(Button)findViewById(R.id.complite);
		changename.setText(name);
		
		comlite.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.complite){
			AppContext.ChangeMess.name=changename.getText().toString().trim();
			finish();
		}
	}
	
}
