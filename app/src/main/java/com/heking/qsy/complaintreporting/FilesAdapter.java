package com.heking.qsy.complaintreporting;

import java.io.File;
import java.util.ArrayList;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FilesAdapter extends BaseAdapter {
private ArrayList<File> list;
private Context context;
private LayoutInflater mInflater;
	public FilesAdapter(ArrayList<File> list,Context context){
		this.list=list;
		this.context=context;
		mInflater=LayoutInflater.from(context);
	//	AppContext.List.fileList=list;
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
		
		layout.textView.setText(list.get(arg0).getName());
		layout.textView.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View arg0) {
				String name= ((TextView) arg0).getText().toString();
				
				for(int i=0;i<list.size();i++){
					
					if(list.get(i).getName().equals(name)){
						
						list.remove(i);
						notifyDataSetChanged();
					//	AppContext.List.fileList=list;
					}
					
					
				}
		
		
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
