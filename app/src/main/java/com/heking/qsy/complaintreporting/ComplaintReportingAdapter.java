package com.heking.qsy.complaintreporting;

import java.util.ArrayList;
import com.heking.qsy.R;
import com.heking.qsy.activity.ComplaintReportings;
import com.heking.qsy.util.FirmTypeBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ComplaintReportingAdapter extends BaseAdapter {

	private ArrayList<FirmTypeBean.Data> list;
	private Context context;
	private LayoutInflater mInflater;
		public ComplaintReportingAdapter(ArrayList<FirmTypeBean.Data> list,Context context){
			this.list=list;
			this.context=context;
			mInflater=LayoutInflater.from(context);
			
		}
		
		
		public int getCount() {
			// TODO Auto-generated method stub
			return list!=null?list.size():0;
		}

		
		public Object getItem(int arg0) {
			
			return list.get(arg0);
		}

		
		public long getItemId(int arg0) {
			return arg0;
		}

		
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			Layout layout=null;
			if(layout==null){
				
				arg1=mInflater.inflate(R.layout.list_view_file, null);
			}
			layout=	(Layout) arg1.getTag();	
			
			if(layout==null){
				
				layout =new Layout();
				layout.textView=(TextView) arg1.findViewById(R.id.file_list_textview);
				arg1.setTag(layout);
			}
			layout.cose();
			
			layout.textView.setText(list.get(arg0).getFirmName());
			layout.textView.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View arg0) {
					TextView ag=(TextView) arg0;
					ComplaintReportings.offDialoge(ag.getText().toString().trim());
				}
			});
			return arg1;
		}
	private class Layout{
	private TextView textView;
		
	private void cose(){
			textView.setText("");	
			
		}
		
	}
	}
