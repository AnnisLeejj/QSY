package com.heking.qsy.activity.FirmShow;

import java.util.ArrayList;
 

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.snoa.WPConfig;
import com.lidroid.xutils.BitmapUtils;

public class FirmTypeAdapter extends BaseAdapter {
	private ArrayList<FirmTypeBean.Data.FirmLicenseData> list;
	private LayoutInflater mInflater;
	Layout layout = null;
	String TAG = "FirmTypeAdapter";

	public FirmTypeAdapter(ArrayList<FirmTypeBean.Data.FirmLicenseData> list,
			Context context) {
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list != null ? list.size() : 0;
	}

	public Object getItem(int arg0) {

		return list.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressWarnings("deprecation")
	public View getView(final int proenat, View arg1, ViewGroup arg2) {

		if (arg1 == null) {
			layout = new Layout();
			arg1 = mInflater.inflate(R.layout.list_view_firm2, null);
			layout.textViews2 = (TextView) arg1
					.findViewById(R.id.file_list_textviews2);
			layout.textViews = (TextView) arg1
					.findViewById(R.id.file_list_textviews);
			layout.iv = (ImageView) arg1.findViewById(R.id.iv);
			arg1.setTag(layout);
		}
		layout = (Layout) arg1.getTag();
		
		layout.textViews.setText(list.get(proenat).getName());
		layout.textViews2.setText(list.get(proenat).getLicenseCode());

		if (!TextUtils.isEmpty(list.get(proenat).getFileID())) {
			layout.iv.setVisibility(View.VISIBLE);

			BitmapUtils bitmapUtils = new BitmapUtils(arg2.getContext());
			// 加载网络图片
			bitmapUtils.display(layout.iv, WPConfig.IMAGE_VIEW_01
					+ list.get(proenat).getFileID());
//			 x.image().bind(layout.iv, WPConfig.IMAGE_VIEW_01
//						+ list.get(proenat).getFileID());

		}else {
			layout.iv.setVisibility(View.GONE);
		}

		Log.i(TAG, list.get(proenat).getName() + ":"
				+ WPConfig.IMAGE_VIEW_01 + list.get(proenat).getFileID());
		return arg1;
	}

	private class Layout {

		private TextView textViews;

		private TextView textViews2;
		private ImageView iv;

		

	}
}
