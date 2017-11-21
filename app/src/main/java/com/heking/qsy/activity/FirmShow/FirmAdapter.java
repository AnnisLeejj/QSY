package com.heking.qsy.activity.FirmShow;

import java.util.ArrayList;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.util.FirmTypeBean;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FirmAdapter extends BaseAdapter {

	private ArrayList<FirmTypeBean.Data> list;
	private Context context;
	private LayoutInflater mInflater;
	private int State;
	private String TAG = "FirmAdapter";

	public FirmAdapter(ArrayList<FirmTypeBean.Data> list, Context context, int State) {
		this.list = list;
		this.context = context;
		this.State = State;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
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
		Layout layout = null;
		if (layout == null) {
			arg1 = mInflater.inflate(R.layout.list_view_firm, null);
		}
		layout = (Layout) arg1.getTag();
		if (layout == null) {
			layout = new Layout();
			layout.textView = (TextView) arg1.findViewById(R.id.file_list_textview);
			layout.mSP = (TextView) arg1.findViewById(R.id.img_sp);
			layout.mPF = (TextView) arg1.findViewById(R.id.img_pingji);
			layout.tv_dianpin = (TextView) arg1.findViewById(R.id.tv_dianpin);
			if (AppContext.THEME) {
				layout.textView.setTextColor(Color.parseColor("#333333"));
			} else {
				layout.textView.setTextColor(Color.parseColor("#f9986e"));
			}
			arg1.setTag(layout);
		}
		layout.cose();
		layout.mPF.setVisibility(View.VISIBLE);
		switch (list.get(proenat).getmRating()) {
		case 1:
			layout.mPF.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.a));
			break;
		case 2:
			layout.mPF.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.b));
			break;
		case 3:
			layout.mPF.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.c));
			break;
		default:
//			layout.mPF.setVisibility(View.GONE);
			break;
		}if (list.get(proenat).getFirmReviewAverage() == 0) {
			layout.tv_dianpin.setText("");
//			layout.tv_dianpin.setVisibility(View.GONE);
		} else if (list.get(proenat).getFirmReviewAverage() < 5) {
			layout.tv_dianpin.setVisibility(View.VISIBLE);
			layout.tv_dianpin.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bad));
//			layout.tv_dianpin.setText("不满意");
//			layout.tv_dianpin.setTextColor(Color.parseColor("#e96c9e"));
		} else if (list.get(proenat).getFirmReviewAverage() > 6) {
			layout.tv_dianpin.setVisibility(View.VISIBLE);
			layout.tv_dianpin.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.good));
//			layout.tv_dianpin.setText("满意");
//			layout.tv_dianpin.setTextColor(Color.parseColor("#5ab7fa"));
		} else {
			layout.tv_dianpin.setVisibility(View.VISIBLE);
			layout.tv_dianpin.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ok));
//			layout.tv_dianpin.setText("一般");
//			layout.tv_dianpin.setTextColor(Color.parseColor("#93c348"));
		}
		if (AppContext.LoginUserMessage.messageUse) {
			if (AppContext.LoginUserMessage.bean != null) {
				for (int i = 0; i < AppContext.LoginUserMessage.bean.getSystemMenus().size(); i++) {
					String code = AppContext.LoginUserMessage.bean.getSystemMenus().get(i).getCode();
					if (code.equals("6")) {
						if (list.get(proenat).isIoos()) {
							layout.mSP.setVisibility(View.VISIBLE);
							if (AppContext.THEME) {
								layout.mSP.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.easy));
							} else {
								layout.mSP.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.easy_1));
							}
						}else {
							//layout.mSP.setVisibility(View.GONE);
						}
						break;
					}
				}
			}
		}
		layout.textView.setText(list.get(proenat).getFirmName());

		arg1.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				FirmTypeBean.Data data = list.get(proenat);
				Intent intent = new Intent();
				// intent.setClass(context, FirmType.class);
				intent.setClass(context, BaiDuFirmTypeActivity.class);

				Bundle bundle = new Bundle();
				bundle.putSerializable("FIRMTYPE", data);
				bundle.putInt("State", State);
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		return arg1;
	}

	private class Layout {
		private TextView textView, tv_dianpin;
		private TextView mSP, mPF;
		private void cose() {
			textView.setText("");
		}
	}
}
