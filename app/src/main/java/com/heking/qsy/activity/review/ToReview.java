package com.heking.qsy.activity.review;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.Personalcenters.util.LoginBean;
import com.heking.qsy.base.BaseActivity;
import com.heking.qsy.providers.ConnectionUtility;
import com.heking.qsy.providers.HttpImage;
import com.heking.qsy.providers.ImageBitmap;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.PopDialogView;
import com.heking.qsy.util.Tool;
import com.heking.qsy.util.ViewPageAdapter;
import com.heking.qsy.yuying.YUMessageData;
import com.heking.qsy.yuying.YYshow;
import com.heking.snoa.WPConfig;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ToReview extends BaseActivity implements JSONdata, ImageBitmap,
		OnClickListener, YUMessageData {
	private static final int INT_CAMERA = 100000;
	private static final int INT_PHOTO = 111111;
	private Object poiItem;
	// private RelativeLayout mBackImage;
	private TextView mAddIamge;
	private ViewPager mViewPager;
	private TextView mName;
	private TextView mContactAddress;
	private TextView mContactPhoneNumber;
	// private LinearLayout mlayoutDial;
	// private TextView mDial;
	private RatingBar mBar;
	private EditText mContext;
	private TextView mTiJiao;
	// private ImageView mImageView;
	private Map<String, String> mapstring = new HashMap<String, String>();
	private Map<String, String> mapjson = new HashMap<String, String>();
	private Map<String, File> mapfile = new HashMap<String, File>();
	private File file;
	private View mView;
	private TextView mXiangJI, mXiangCE;

	private static PopDialogView mDialogView;
	private ViewPageAdapter mAdapter;
	private ArrayList<View> mBItmapList = new ArrayList<View>();

	private int mbarss;
	private TextView mTNUmber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (AppContext.THEME) {
			setTheme(R.style.SwitchTheme_1);
		} else {
			setTheme(R.style.SwitchTheme_2);
		}
		setContentView(R.layout.to_review_layout);

		iniView();

		iniData();

	}

	@SuppressLint("NewApi")
	private void iniView() {

		//poiItem = AppContext.BundelPoiMess.poiItem;

		// String url = Tool.getUrLString("get_Review_and_Comments", 1, this,
		// true);

//		Tool.toHttpGEtandPost(AppContext.Url.URL_API_INTRANET
//				+ AppContext.Parameter.Get_REVIEW + "/" + poiItem.getPoiId(),
//				"GET", null, this, null);

		findViewById(R.id.textview).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		new YYshow(this, this, this);
//		((TextView) findViewById(R.id.textview01)).setText(poiItem.getTitle());
		mAddIamge = (TextView) findViewById(R.id.add_image_view);
		// mImageView = (ImageView) findViewById(R.id.image_back_view);
		// mBackImage = (RelativeLayout) findViewById(R.id.layout_back_image);
		mViewPager = (ViewPager) findViewById(R.id.guidance_image_view);
		mName = (TextView) findViewById(R.id.text_view_name);
		mContactAddress = (TextView) findViewById(R.id.Contact_address);
		mContactPhoneNumber = (TextView) findViewById(R.id.Contact_phone_number);
		// mlayoutDial = (LinearLayout) findViewById(R.id.layout_dial);
		// mDial = (TextView) findViewById(R.id.text_dial);
		mBar = (RatingBar) findViewById(R.id.ratinbar_score);
		mContext = (EditText) findViewById(R.id.edittext_context);
		mTiJiao = (TextView) findViewById(R.id.Send_ti_jiao);

		mTNUmber = (TextView) findViewById(R.id.contatct_image);
		mView = LayoutInflater.from(this).inflate(R.layout.file_view, null);
		mXiangJI = (TextView) mView.findViewById(R.id.xiangji);
		mXiangCE = (TextView) mView.findViewById(R.id.xiangce);

		// 下拉框
		mDialogView = new PopDialogView(this);
		//back_img_firm_remview
		mAdapter = new ViewPageAdapter(mBItmapList);
		mImageViewText = (TextView) findViewById(R.id.image_back_ssss);
	}

	private TextView mImageViewText;
	private boolean bo = false;
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (bo) {
				// 调用拨号盘
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ mContactPhoneNumber.getText().toString().trim()));
				if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				startActivity(intent);
			}
		}
	};

	private void iniData() {
		mBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
				mbarss = (int) (arg1 * 2);
				Log.d("测试数据", "float>" + mbarss);
			}
		});
//		mName.setText(poiItem.getTitle());
//		mContactAddress.setText(poiItem.getSnippet());
//		mViewPager.setAdapter(mAdapter);
//		if (!poiItem.getTel().equals("") || poiItem.getTel() != null) {
//			String cll = poiItem.getTel();
//			int inx = cll.indexOf(";");
//			String cllstr = inx > 0 ? cll.substring(0, inx)
//					: cll.length() > 4 ? cll : null;
//
//			if (cllstr != null) {
//				mContactPhoneNumber.setText(cllstr);
//				// mTNUmber.setVisibility(LinearLayout.VISIBLE);
//				bo = true;
//				// mlayoutDial.setVisibility(LinearLayout.VISIBLE);
//				// mDial.setOnClickListener(new OnClickListener() {
//				//
//				// @Override
//				// public void onClick(View arg0) {
//				// // 调用拨号盘
//				// Intent intent = new Intent(Intent.ACTION_CALL, Uri
//				// .parse("tel:"
//				// + mContactPhoneNumber.getText()
//				// .toString().trim()));
//				// startActivity(intent);
//				// }
//				// });
//			} else {
//				bo = false;
//				// mTNUmber.setVisibility(LinearLayout.GONE);
//				mContactPhoneNumber.setText("暂无电话");
//
//				// mlayoutDial.setVisibility(LinearLayout.GONE);
//			}
//			mContactPhoneNumber.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					if (!(mContactPhoneNumber.getText().equals("暂无电话"))) {
//						// 调用拨号盘
//						Intent intent12331 = new Intent(Intent.ACTION_CALL, Uri
//								.parse("tel:"
//										+ mContactPhoneNumber.getText()
//												.toString().trim()
//												.replace("-", "")));
//						startActivity(intent12331);
//					}
//					// TODO Auto-generated method stub
//				}
//			});
//		}
//		mTiJiao.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				if (mContext.getText().toString().trim() == null
//						|| mContext.getText().toString().trim().equals("")) {
//					Toast.makeText(ToReview.this, "评价内容不能为空，请重新输入", 1000)
//							.show();
//				} else {
//					mapjson.put("FirmName", poiItem.getAdName());
//					mapjson.put("FirmID", "false");
//					mapjson.put("ReviewContent", mContext.getText().toString()
//							.trim());
//					mapjson.put("AddressUID", poiItem.getPoiId());
//					mapjson.put("Meid", android.os.Build.MODEL);
//					mapjson.put("Score", mbarss + "");
//					// mapjson.put("State", "Review_and_Comments");
//					// mapstring.put("JsonData", ParsonJson.mapToJson(mapjson));
//
//					if (file != null) {
//
//						mapfile.put(file.getName(), file);
//
//					} else {
//
//						mapfile = null;
//
//					}
//
//					new ConnectionUtility(AppContext.Url.URL_API_INTRANET
//							+ AppContext.Submit.SUBMIT_TEVIEW, "POST", mapjson,
//							new JSONdata() {
//
//								@Override
//								public void httpResponse(String json) {
//
//									Log.d("测试数据", json);
//									if (json.equals("连接失败")) {
//										return;
//									}
//									LoginBean.RegistereBean bean = ParsonJson
//											.jsonToBeanObj(
//													json,
//													LoginBean.RegistereBean.class);
//									boolean issoo = bean == null ? false : bean
//											.getState() == null ? false : true;
//									if (issoo) {
//										String message = bean.getState()
//												.equals("false") ? "提交失败"
//												: "提交成功";
//										Looper.prepare();
//										Toast.makeText(ToReview.this, message,
//												500).show();
//										Looper.loop();
//									}
//									// TODO:编程为未完成
//								}
//							}, mapfile);
//				}
//
//			}
//		});
//
//		dolgData();

	}

	private void dolgData() {
		mDialogView.setContentView(mView);
		DialogSet();

		mXiangJI.setOnClickListener(this);
		mXiangCE.setOnClickListener(this);
		mAddIamge.setOnClickListener(this);

	}

	/**
	 * 设置mDialogView铺满横屏
	 */
	private void DialogSet() {
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = mDialogView.getWindow().getAttributes();
		lp.width = (int) (display.getWidth()); // 设置宽度

	}

	@SuppressLint("NewApi")
	@Override
	public void httpResponse(String json) {
		json = "{\"state\":\"true\",\"MaxPage\":1,\"Data\":" + json + "}";
		if (!json.equals("连接失败")) {
			ReviewMapbean mMapbean = ParsonJson.jsonToBeanObj(json,
					ReviewMapbean.class);
			if (mMapbean != null) {
				ArrayList<ReviewMapbean.Data> str = mMapbean.getData();
				int max = 0;
				if (str != null && str.size() > 0) {
					for (ReviewMapbean.Data data : str) {
						max = max + new Integer(data.getScore());
						final String url = WPConfig.IMAGE_VIEW_01
								+ data.getFileID();

						new HttpImage(this, url, null);
					}
					mBar.setRating((max / str.size()) / 2);
					// TODO :
					
					
				}
			}
		}
		Log.d("测试数据", json);
	}

	@SuppressLint("NewApi")
	@Override
	public void toBitmap(Bitmap bitmap) {
		// mBackImage.setBackground(new BitmapDrawable(bitmap));
		if(bitmap!=null){
			mImageViewText.setVisibility(LinearLayout.GONE);
		ImageView imageView = new ImageView(this);
		imageView.setBackground(new BitmapDrawable(bitmap));
		mBItmapList.add(imageView);
		mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.add_image_view:
			mDialogView.show();
			break;
		case R.id.xiangji:
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, INT_CAMERA);
			mDialogView.dismiss();
			break;
		case R.id.xiangce:
			photo();
			mDialogView.dismiss();
			break;
		}

		// TODO Auto-generated method stub

	}

	@SuppressLint("NewApi")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case INT_CAMERA:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					// 获取sd卡的路径
					String sdStatus = Environment.getExternalStorageState();

					if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用

						Toast.makeText(getApplicationContext(),
								"sd不存在/或sd异常。请检查", Toast.LENGTH_LONG).show();
						return;
					}

					Bundle bundle = data.getExtras();

					Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
					ImageView imageView = new ImageView(this);
					imageView.setBackground(new BitmapDrawable(bitmap));
					mBItmapList.clear();
					mImageViewText.setVisibility(LinearLayout.GONE);
					mBItmapList.add(imageView);
					mAdapter.notifyDataSetChanged();
					// mBackImage.setBackground(new BitmapDrawable(bitmap));

					// 获取相对路径
					String fileString = Environment
							.getExternalStorageDirectory() + "/myImage/";

					File file = new File(fileString);
					if (!file.exists()) {
						file.mkdirs();// 创建文件夹
					}

					// System.currentTimeMillis() 获取系统当前时间的毫秒值
					String fileName = System.currentTimeMillis() + ".png";

					try {
						// 是列化FileOutputStream
						FileOutputStream b = new FileOutputStream(fileString
								+ fileName);
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
						b.flush();
						b.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					file = new File(fileString + fileName);
				}
			}

			break;
		case INT_PHOTO:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {

					// Bundle bundle = data.getExtras();
					// // 接收一个数据位图过来。
					// Bitmap bitmap = (Bitmap) bundle.get("data");
					// mBackImage.setBackground(new BitmapDrawable(bitmap));
					Uri uri = data.getData();

					Cursor c = getContentResolver().query(uri, null, null,
							null, null);

					c.moveToFirst();

					String path = c.getString(1);
					// 调整图片的比例
					Options o = new BitmapFactory.Options();

					o.inJustDecodeBounds = false;

					o.inSampleSize = 10;

					Bitmap bitmap = BitmapFactory.decodeFile(path, o);

					// mBackImage.setBackground(new BitmapDrawable(bitmap));
					ImageView imageView = new ImageView(this);
					imageView.setBackground(new BitmapDrawable(bitmap));
					mBItmapList.clear();
					mImageViewText.setVisibility(LinearLayout.GONE);
					mBItmapList.add(imageView);
					mAdapter.notifyDataSetChanged();
					String[] proj = { MediaStore.Images.Media.DATA };
					Cursor actualimagecursor = managedQuery(uri, proj, null,
							null, null);
					int actual_image_column_index = actualimagecursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					actualimagecursor.moveToFirst();
					String img_path = actualimagecursor
							.getString(actual_image_column_index);
					file = new File(img_path);

				}
			}
			break;
		}
	}

	private void photo() {

	//	Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

		//intent.setType("image/*");

		//intent.addCategory(Intent.CATEGORY_OPENABLE);
		   Intent intent = new Intent(Intent.ACTION_PICK,
	                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		/***************************************************************/
		// 剪切代码。如果不剪切。则不需要以下代码
		// intent.putExtra("crop", "true");
		//
		// intent.putExtra("aspectX", 1);
		//
		// intent.putExtra("aspectY", 1);
		//
		// intent.putExtra("outputX", 80);
		//
		// intent.putExtra("outputY", 80);
		//
		// intent.putExtra("return-data", true);
		/******************************************************************/
		startActivityForResult(intent, INT_PHOTO);
	}

	@Override
	public void onYYdata(String str) {
		if (!str.equals("。")) {
			String con = mContext.getText().toString().trim();
			mContext.setText(con + str);
		}
	}
	
}
