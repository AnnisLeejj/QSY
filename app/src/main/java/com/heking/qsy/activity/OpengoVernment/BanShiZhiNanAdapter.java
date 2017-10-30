package com.heking.qsy.activity.OpengoVernment;


import java.util.ArrayList;
import com.heking.qsy.R;
import com.heking.qsy.providers.HttpImage;
import com.heking.qsy.providers.ImageBitmap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Bundle;

public class BanShiZhiNanAdapter extends BaseAdapter {

	private ArrayList<OpenGoverBean.Data> list;
	private Context context;
	private LayoutInflater mInflater;
	private int state;

	public BanShiZhiNanAdapter(ArrayList<OpenGoverBean.Data> list,
			Context context, int state) {
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.state = state;
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

	@SuppressLint("NewApi")
	public View getView(final int pointear, View arg1, ViewGroup arg2) {
		Layout layout = null;
		if (layout == null) {

			arg1 = mInflater.inflate(R.layout.list_view_banshizhinan, null);
		}
		layout = (Layout) arg1.getTag();

		if (layout == null) {

			layout = new Layout();
			layout.textView = (TextView) arg1
					.findViewById(R.id.file_list_textview);
			layout.textViewico = (TextView) arg1
					.findViewById(R.id.ico_backe_img);
//			if (AppContext.THEME) {
//				layout.textView.setTextColor(Color.parseColor("#70b9e0"));
//			} else {
//				layout.textView.setTextColor(Color.parseColor("#f9986e"));
//			}
			arg1.setTag(layout);
		}
		layout.cose();
		if (list.get(pointear).getTitleImageUrl() != null
				&& !list.get(pointear).getTitleImageUrl().equals("")) {
			new HttpImage(layout, list.get(pointear).getTitleImageUrl(), null);
		}
		switch (state) {
		case 88801:
//			TextPaint tp = layout.textView.getPaint();
//			tp.setFakeBoldText(true);
			layout.textView.setText(list.get(pointear).getTitle());
			layout.textView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					toIntent(list.get(pointear).getTitle(), list.get(pointear)
							.getSource(), context, WebLayout.class);
				}
			});
			break;
		case 88802:
			layout.textView.setText(list.get(pointear).getTitle());
			layout.textView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					toIntent(list.get(pointear).getTitle(), list.get(pointear)
							.getSource(), context, WebLayout.class);
				}
			});
			break;
		case 88803:
			layout.textView.setText(list.get(pointear).getTitle());
			layout.textView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					toIntent(list.get(pointear).getTitle(), list.get(pointear)
							.getSource(), context, WebLayout.class);
				}
			});
			break;
		case 88804:
			layout.textView.setText(list.get(pointear).getTitle());
			layout.textView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					toIntent(list.get(pointear).getTitle(), list.get(pointear)
							.getSource(), context, WebLayout.class);
				}
			});
			break;
		case 88805:
//			layout.textViewico.setVisibility(LinearLayout.GONE);
			layout.textViewico.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.main_3));
			layout.textView.setText(list.get(pointear).getTitle());
			layout.textView.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					toIntent(list.get(pointear).getTitle(), list.get(pointear)
							.getSource(), context, WebLayout.class);
				}

			});
			break;
		case 88806:
			layout.textViewico.setVisibility(LinearLayout.GONE);
			layout.textView.setText(list.get(pointear).getTitle());
			layout.textView.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					toIntent(list.get(pointear).getTitle(), list.get(pointear)
							.getSource(), context, WebLayout.class);
				}

			});
			break;

		}
		return arg1;
	}

	private void layout(String titleImageUrl, Object object) {
		// TODO Auto-generated method stub

	}

	private void toIntent(String name, String TextContext, Context context,
			Class cls) {
		Intent intent = new Intent(context, cls);
		Bundle bundle = new Bundle();
		bundle.putString("Headline", name);
		bundle.putString("Context", TextContext);
		intent.putExtras(bundle);
		this.context.startActivity(intent);
	}

	private class Layout implements ImageBitmap {
		private TextView textView;
		private TextView textViewico;

		private void cose() {
			textView.setText("");
		}

		@Override
		public void toBitmap(Bitmap bitmap) {
			textViewico.setBackgroundDrawable(new BitmapDrawable(bitmap));
		}

	}
}
