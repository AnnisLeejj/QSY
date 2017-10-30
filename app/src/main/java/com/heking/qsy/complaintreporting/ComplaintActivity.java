package com.heking.qsy.complaintreporting;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.complaintreporting.ComplaintReportingBean.Data;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ComplaintActivity extends Activity {
	private Bundle bundle;
	private ComplaintReportingBean.Data data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (AppContext.THEME) {
			setTheme(R.style.SwitchTheme_1);
		} else {
			setTheme(R.style.SwitchTheme_2);
		}
		setContentView(R.layout.complaint_activity);
		bundle = getIntent().getExtras();
		data = (Data) bundle.getSerializable("MessageData");
		iniView();
		iniData();
	}

	private TextView complaint_firm, complaint_The_plaintiff, complaint_reward,
			complaint_state, complaint_situation, complaint_thmen,
			complaint_context;

	private void iniView() {
		complaint_firm = (TextView) findViewById(R.id.complaint_firm);
		complaint_The_plaintiff = (TextView) findViewById(R.id.complaint_The_plaintiff);
		complaint_reward = (TextView) findViewById(R.id.complaint_reward);
		complaint_state = (TextView) findViewById(R.id.complaint_state);
		complaint_situation = (TextView) findViewById(R.id.complaint_situation);
		complaint_thmen = (TextView) findViewById(R.id.complaint_thmen);
		complaint_context = (TextView) findViewById(R.id.complaint_context);
	}

	private void iniData() {
		if(data!=null){
			complaint_firm .setText(data.getDefendant());// (TextView) findViewById(R.id.complaint_firm);
			complaint_The_plaintiff.setText(data.getComplainant()==null?"暂无信息":data.getComplainant());// = (TextView) findViewById(R.id.complaint_The_plaintiff);
			complaint_state .setText("属实");//data.getIsPublish());// (TextView) findViewById(R.id.complaint_state);
			complaint_reward.setText(data.getTheComplainantReward());// = (TextView) findViewById(R.id.complaint_reward);
			complaint_situation.setText(data.getTheProcessingResults());// = (TextView) findViewById(R.id.complaint_situation);
			complaint_thmen .setText(data.getTile());// (TextView) findViewById(R.id.complaint_thmen);
			complaint_context .setText(data.getContent());// (TextView) findViewById(R.id.complaint_context);
		}

	}

}
