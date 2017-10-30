package com.heking.qsy.activity;

import java.util.HashMap;
import java.util.Map;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.Personalcenters.util.LoginBean;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InforeportActivity extends Activity implements OnClickListener ,JSONdata {
	public Button mInfoRePortSave;
	
	private EditText DrugName,ApprovalNumber,Manufacturer,CommodityCode,Note;
	private Map<String, String> map=new HashMap<String, String>();
	
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
		setContentView(R.layout.activity_info_report);
		iniView();
		iniData();
	}
	
	
	private void iniView() {
		mInfoRePortSave =(Button) findViewById(R.id.info_report_save);
		
		DrugName=(EditText) findViewById(R.id.info_report_name);
		ApprovalNumber=(EditText) findViewById(R.id.info_report_approveCode);
		Manufacturer=(EditText) findViewById(R.id.info_report_manufactures);
		CommodityCode=(EditText) findViewById(R.id.info_report_barcode);
		Note=(EditText) findViewById(R.id.info_report_remark);
	}
	private void iniData() {
		mInfoRePortSave.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.info_report_save:
			map.put("DrugName", DrugName.getText().toString().trim());
			map.put("ApprovalNumber", ApprovalNumber.getText().toString().trim());
			map.put("Manufacturer", ApprovalNumber.getText().toString().trim());
			map.put("CommodityCode", ApprovalNumber.getText().toString().trim());
			map.put("Note", Note.getText().toString().trim());

			Tool.toHttpGEtandPost(WPConfig.URL_API_INTRANET+AppContext.Submit.SUBMIT_DRUG, "POST", map, this, null);
			break;

		default:
			break;
		}
		
	}


	@Override
	public void httpResponse(String json) {
		Log.d("测试数据", json);
		if(json.equals("连接失败")){
			Toast.makeText(this,"连接失败", 500).show();
			return;
		}
		LoginBean.RegistereBean bean = ParsonJson.jsonToBeanObj(json,
				LoginBean.RegistereBean.class);
boolean issoo=bean==null?false:bean.getState()==null?false:true;
		if(issoo){
			String message=bean.getState().equals("false")?"提交失败":"提交成功";
			Looper.prepare();
			Toast.makeText(this,message, 500).show();
			Looper.loop();
		}
	
		
		
		
		
	}


}
