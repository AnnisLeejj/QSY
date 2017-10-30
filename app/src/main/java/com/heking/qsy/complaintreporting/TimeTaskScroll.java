package com.heking.qsy.complaintreporting;

import java.util.ArrayList;
import java.util.TimerTask;
import com.heking.qsy.util.FirmTypeBean;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

public class TimeTaskScroll extends TimerTask {
	
	private ListView listView;
	
	public TimeTaskScroll(ListView listView,Context context,ArrayList<ComplaintReportingBean.Data>  list,int ss,boolean boo,FirmTypeBean firmbean){
		this.listView = listView;
		listView.setAdapter(new ComplaintAdapter(context, list,ss,boo,firmbean));
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			listView.smoothScrollBy(1, 0);
			listView.scrollTo(0, 50);
		};
	};

	@Override
	public void run() {
		Message msg = handler.obtainMessage();
		handler.sendMessage(msg);
	}
}
