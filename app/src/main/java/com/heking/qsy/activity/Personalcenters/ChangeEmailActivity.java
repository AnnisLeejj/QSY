package com.heking.qsy.activity.Personalcenters;

import java.util.regex.Pattern;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeEmailActivity extends Activity implements OnClickListener{
	private EditText changename;
	private Button comlite;
	 /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
 private Context context=this;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changemail);
		Intent intent=getIntent();
		String name=intent.getStringExtra("Email");
		changename=(EditText)findViewById(R.id.changename_tv);
		comlite=(Button)findViewById(R.id.complite);
		changename.setText(name);	
		comlite.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.complite){
			//正则表达式验证邮箱
			if(isEmail(changename.getText().toString().trim())){
				AppContext.ChangeMess.Email=changename.getText().toString().trim();
				finish();
			}else {
				Toast.makeText(context, "邮箱地址格式不正确！", Toast.LENGTH_SHORT).show();
			}
			
		}
	}
	 public static boolean isEmail(String email) {
	        return Pattern.matches(REGEX_EMAIL, email);
	    }
}
