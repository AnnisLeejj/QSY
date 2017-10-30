package com.heking.qsy.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.Personalcenters.util.LoginBean;
import com.heking.qsy.base.BaseActivity;
import com.heking.qsy.complaintreporting.ComplaintReportingAdapter;
import com.heking.qsy.complaintreporting.ComplaintReportingHome;
import com.heking.qsy.complaintreporting.FilesAdapter;
import com.heking.qsy.providers.ConnectionUtility;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.PopDialogView;
import com.heking.qsy.util.PickerView.onSelectListener;
import com.heking.snoa.WPConfig;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 废弃类
 * 
 * @author jhw
 * 
 */
public class ComplaintReporting extends BaseActivity implements JSONdata,
		onSelectListener, OnClickListener {

	private static final int INT_PHOTO = 111111;
	private static final int INT_CAMERA = 100000;
	private static final int INT_FILE = 222222;

	private static final int INT_STATE_FIRM = 8080001;

	private static String FirmName;
	// private static final int INT_STATE_FIRM=8080001;
	// private static final int INT_STATE_FIRM=8080001;

	private Map<String, String> mData = new HashMap<String, String>();
	private Map<String, String> mJson = new HashMap<String, String>();
	private Map<String, File> mFiles = new HashMap<String, File>();
	private int state;

	private static TextView mCategory, mFileSelect, mSubmit;
	private EditText mPhone, mPhoneName, mContent;

	private static PopDialogView mDialogView;
	private ListView pickerView;
	private LayoutInflater mInflater;

	private ArrayList<FirmTypeBean.Data> list = getData(AppContext.List.FirmTapeDataList);
	private ArrayList<File> filelist = new ArrayList<File>();

	private View mView;
	private LinearLayout mLayoutCategory, mLayoutFile;
	private ListView mLayoutFuJian;
	private FilesAdapter adapter;
	private TextView mXiangJI, mXiangCE, mFile;
	private String mCategoryData;
	private Map<String, String> map = new HashMap<String, String>();
	private Map<String, String> map3 = new HashMap<String, String>();
	private Map<String, File> map2 = new HashMap<String, File>();
	private ComplaintReportingAdapter adapter2;
	private TextView mText12331;
	private EditText mid_edittext;
	private String mSeekString = "";
	private TimerTask mSeekTask;
	private Timer mSeekTimer;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (AppContext.THEME) {
			setTheme(R.style.SwitchTheme_1);
		} else {
			setTheme(R.style.SwitchTheme_2);
		}
		setContentView(R.layout.complaint_reporting_activity);
		iniView();
		iniData();
		toRunnable();

		// ConnectionUtility connectionUtility=new
		// ConnectionUtility(AppContext.Url.TOU_SHU_JU_BAO, "POST",maps
		// ,this,map2 );

	}

	private void toRunnable() {
		mSeekTask = new TimerTask() {
			@Override
			public void run() {

				String str = mid_edittext.getText().toString().trim();
				if (mSeekString.equals(str)) {

				} else {
					mSeekString = str;
					Bundle bundle = new Bundle();
					Message mMessage = ss.obtainMessage();
					bundle.putString("data", str);
					mMessage.setData(bundle);
					ss.dispatchMessage(mMessage);
					Log.d("测试数据", str);
				}
			}

		};
		mSeekTimer = new Timer();
		mSeekTimer.schedule(mSeekTask, 0, 100);

	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			adapter2.notifyDataSetChanged();
		}
	};
	private hean ss = new hean();
	private boolean boo = true;
	private Handler handler = new Handler();

	private class hean extends Handler {
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			// 这里用于更新UI

			Bundle bundle = msg.getData();
			String mes = bundle.getString("data");
			if (msg != null) {
				Log.d("测试数据:", mes);
				list.clear();
				for (FirmTypeBean.Data data : getData(AppContext.List.FirmTapeDataList)) {
					if (data.getFirmName().contains(mes) || mes.equals("")) {
						list.add(data);
					}
					handler.post(runnable);

				}

			}
		}

	}

	private ArrayList<FirmTypeBean.Data> getData(
			ArrayList<FirmTypeBean.Data> datalist) {

		ArrayList<FirmTypeBean.Data> datas = new ArrayList<FirmTypeBean.Data>();

		for (FirmTypeBean.Data data : datalist) {
			if (data.getFirmTypeName().trim().equals("食品企业")
					|| data.getFirmTypeName().trim().equals("餐饮企业")
					|| data.getFirmTypeName().trim().equals("药品企业")) {
				if (data.getMonitors() != null && data.getMonitors().size() > 0) {

					data.setIoos(true);

				} else {

					data.setIoos(false);
				}

				if (data.getAnnualRating() != null
						&& data.getAnnualRating().size() > 0) {
					if (data.getAnnualRating().get(0).getRating() != null
							&& !data.getAnnualRating().get(0).getRating()
									.trim().equals("")) {
						if (data.getAnnualRating().get(0).getRating().trim()
								.equals("优秀")) {
							data.setmRating(1);
						}
						if (data.getAnnualRating().get(0).getRating().trim()
								.equals("良好")) {
							data.setmRating(2);
						}
						if (data.getAnnualRating().get(0).getRating().trim()
								.equals("一般")) {
							data.setmRating(3);
						}
						if (data.getAnnualRating().get(0).getRating().trim()
								.equals("不予评级")) {
							data.setmRating(4);
						}
					}

				} else {
					data.setmRating(4);
				}
				datas.add(data);
			}
		}
		return datas;

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent(ComplaintReporting.this,
					ComplaintReportingHome.class);
			startActivity(intent);
			finish();
			return false;
		}
		return false;
	}

	private void iniView() {
		((TextView) findViewById(R.id.textview))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) {
						Intent intent = new Intent(ComplaintReporting.this,
								ComplaintReportingHome.class);
						startActivity(intent);
						finish();

					}
				});

		// 选择类别按钮
		mCategory = (TextView) findViewById(R.id.mCategory);

		// 添加附件按钮
		mFileSelect = (TextView) findViewById(R.id.add_fujian_button);
		// 提交按钮
		mSubmit = (TextView) findViewById(R.id.ti_jiao_Complaint_reporting);

		mText12331 = (TextView) findViewById(R.id.Text_12331_text);
		// 电话出入框
		mPhone = (EditText) findViewById(R.id.phose_your);
		mPhoneName = (EditText) findViewById(R.id.phose_your_name);

		// 内容输入框
		mContent = (EditText) findViewById(R.id.context_complaint);

		// 附加容器
		mLayoutFuJian = (ListView) findViewById(R.id.fujian_list);

		// 下拉框
		mDialogView = new PopDialogView(this);
		mInflater = LayoutInflater.from(this);
		mView = mInflater.inflate(R.layout.dialog_view, null);

		// 企业选择
		pickerView = (ListView) mView.findViewById(R.id.piacker_view);

		mid_edittext = (EditText) mView.findViewById(R.id.id_edittext);

		// 名称和类别选择的父容器
		mLayoutCategory = (LinearLayout) mView
				.findViewById(R.id.layout_content);
		// 选择文件的父容器
		mLayoutFile = (LinearLayout) mView.findViewById(R.id.layout_file);

		mXiangJI = (TextView) mView.findViewById(R.id.xiangji);
		mXiangCE = (TextView) mView.findViewById(R.id.xiangce);
		mFile = (TextView) mView.findViewById(R.id.wenjian);

		adapter = new FilesAdapter(filelist, this);

	}

	private void iniData() {
		if (AppContext.LoginUserMessage.messageUse) {
			mPhone.setText(AppContext.LoginUserMessage.bean.getFullName());
			mPhoneName.setText(AppContext.LoginUserMessage.bean.getLoginName());
		}

		adapter2 = new ComplaintReportingAdapter(list, this);
		pickerView.setAdapter(adapter2);
		mDialogView.setContentView(mView);

		mLayoutFuJian.setAdapter(adapter);
		mXiangJI.setOnClickListener(this);
		mXiangCE.setOnClickListener(this);
		mFile.setOnClickListener(this);

		mCategory.setOnClickListener(this);
		mFileSelect.setOnClickListener(this);
		mSubmit.setOnClickListener(this);
		mText12331.setOnClickListener(this);
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

	public static void offDialoge(String name) {
		FirmName = name;
		mCategory.setText(name);
		mDialogView.dismiss();
	}

	public void httpResponse(String json) {
		if (json.equals("连接失败")) {
			Toast.makeText(this, "网络连接失败，请检查您的网络", Toast.LENGTH_SHORT).show();
			return;
		}
	}

	public void onSelect(String text) {
		Log.d("测试数据", text);
	}

	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.mCategory:
			mLayoutCategory.setVisibility(LinearLayout.VISIBLE);
			mLayoutFile.setVisibility(LinearLayout.GONE);
			DialogSet();
			mDialogView.show();
			break;
		case R.id.add_fujian_button:
			mLayoutCategory.setVisibility(LinearLayout.GONE);
			mLayoutFile.setVisibility(LinearLayout.VISIBLE);
			DialogSet();
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
		case R.id.Text_12331_text:
			// 调用拨号盘
			Intent intent12331 = new Intent(Intent.ACTION_DIAL,
					Uri.parse("tel:" + mText12331.getText().toString().trim()));
			startActivity(intent12331);
			break;
		case R.id.wenjian:
			Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
			intent2.setType("*/*");// 设置类型，我这里是任意类型，任意后缀的可以这样写。
			intent2.addCategory(Intent.CATEGORY_OPENABLE);
			startActivityForResult(intent2, INT_FILE);
			mDialogView.dismiss();
			break;

		case R.id.ti_jiao_Complaint_reporting:
			for (File file : AppContext.List.fileList) {

				map2.put(file.getName(), file);
			}
			String Phone = mPhone.getText().toString().trim();
			String mName = mPhoneName.getText().toString().trim();
			String Enterprise = FirmName;
			String content = mContent.getText().toString().trim();
			String Size = AppContext.List.fileList.size() + "";
			String Accessory = "true";

			if (content.equals("")) {
				Toast.makeText(ComplaintReporting.this, "请输入举报内容！", Toast.LENGTH_SHORT)
						.show();
				return;

			}
			map3.put("PhoneNumber", Phone);
			map3.put("Defendant", Enterprise);
			map3.put("Content", content);
			map3.put("Complainant", mName);
			map3.put("Tile", "攀枝花投诉举报");

			new ConnectionUtility(WPConfig.URL_POST_CESHI, "POST", map3,
					new JSONdata() {

						@Override
						public void httpResponse(String json) {
							Looper.prepare();
							if (json.equals("连接失败")) {
								Toast.makeText(ComplaintReporting.this, json,
										Toast.LENGTH_SHORT).show();
							} else {
								LoginBean.RegistereBean bean = ParsonJson
										.jsonToBeanObj(json,
												LoginBean.RegistereBean.class);
								boolean is = bean != null ? bean.getState()
										.equals("false") ? false : true : false;
								if (is) {

									Toast.makeText(ComplaintReporting.this,
											"举报成功", Toast.LENGTH_SHORT).show();
									finish();

								} else {
									Toast.makeText(ComplaintReporting.this,
											"举报失败", Toast.LENGTH_SHORT).show();
								}
							}
							Looper.loop();
						}
					}, map2);
			break;
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case INT_CAMERA:
			if (resultCode == Activity.RESULT_OK) {
				// 获取sd卡的路径
				String sdStatus = Environment.getExternalStorageState();

				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用

					Toast.makeText(getApplicationContext(), "sd不存在/或sd异常。请检查",
							Toast.LENGTH_LONG).show();
					return;
				}

				Bundle bundle = data.getExtras();

				Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
				// 获取相对路径
				String fileString = Environment.getExternalStorageDirectory()
						+ "/myImage/";

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
				File files = new File(fileString + fileName);
				filelist.add(files);
				adapter.notifyDataSetChanged();
			}

			break;
		case INT_PHOTO:
			if (resultCode == Activity.RESULT_OK) {
				if (data == null) {
					return;
				}

				Uri uri = data.getData();
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor actualimagecursor = managedQuery(uri, proj, null, null,
						null);
				int actual_image_column_index = actualimagecursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				actualimagecursor.moveToFirst();
				String img_path = actualimagecursor
						.getString(actual_image_column_index);
				File file = new File(img_path);
				adapter.notifyDataSetChanged();
				filelist.add(file);

			}
			break;
		case INT_FILE:
			if (data == null) {
				return;
			}
			if (resultCode == Activity.RESULT_OK) {
				Uri uris = data.getData();
				Log.d("测试数据", uris.toString());
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor actualimagecursor = managedQuery(uris, proj, null, null,
						null);
				int actual_image_column_index = actualimagecursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				actualimagecursor.moveToFirst();
				String img_path = actualimagecursor
						.getString(actual_image_column_index);
				File file = new File(img_path);
				filelist.add(file);
				adapter.notifyDataSetChanged();
			}
			break;
		}
	}

	private void photo() {

//		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//
//		intent.setType("image/*");
//
//		intent.addCategory(Intent.CATEGORY_OPENABLE);
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

}
