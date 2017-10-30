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
import com.heking.qsy.providers.ConnectionUtility;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.MyTextUtils;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.PopDialogView;
import com.heking.qsy.util.Tool;
import com.heking.qsy.yuying.SoftKeyBoardListener;
import com.heking.qsy.yuying.YUMessageData;
import com.heking.qsy.yuying.YuYIng;
import com.heking.snoa.WPConfig;
import com.zbar.lib.CaptureActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;

public class ComplaintReportings extends BaseActivity implements OnClickListener, YUMessageData {

    private static final int INT_PHOTO = 111111;
    private static final int INT_CAMERA = 100000;
    private Map<String, String> map3 = new HashMap<String, String>();
    private Map<String, File> map2 = new HashMap<String, File>();
    private ArrayList<FirmTypeBean.Data> list;
    private ComplaintReportingAdapter adapter;
    private TextView mBoHao;
    private static int STATE = 0;
    private LinearLayout mFu_layout_01;
    private LinearLayout mFu_layout_02;
    private LinearLayout mFu_layout_03;
    private TextView mZi_button_01;
    private TextView mZi_button_02;
    private TextView mZi_button_03;
    private static TextView mXuanZeFirm;
    private TextView mNamess;
    private TextView mDianhua;
    private TextView mTiJiao;
    private static PopDialogView mDialogView;
    private LayoutInflater mInflater;
    private View mView;
    private LinearLayout mLayoutCategory, mLayoutFile;
    private ListView mLayoutFuJian;
    private TextView mXiangJI, mXiangCE, mFile;
    private String mCategoryData;
    private ListView pickerView;
    private ArrayList<File> filelist = new ArrayList<File>();
    private static String FirmName;
    private TextView Button_OK;
    private TextView Button_OFF;
    private EditText mBiaoTi;
    private EditText mid_edittext;
    private EditText mContent;
    private static EditText mTouShuFirm;
    private static PopupWindow mPopupWindow;
    private View mViewLsit;
    private static final int CAMERA_OK=1011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.complaint_reporting_activity_2);
        iniView();
        iniData();
//		fu_layout//
    }

    private void iniView() {
        if (AppContext.List.FirmTapeDataList != null) {
            list = getData(AppContext.List.FirmTapeDataList);
        } else {
            list = new ArrayList<FirmTypeBean.Data>();
        }
        Tool.endActivity(this);
        SoftKeyBoardListener.setListener(this,
                new SoftKeyBoardListener
                        .OnSoftKeyBoardChangeListener() {
                    @Override
                    public void keyBoardShow(int height) {
                        mShowPopwindow(height);
                    }

                    @Override
                    public void keyBoardHide(int height) {
                        if (mYuying != null) {
                            mYuying.dismiss();
                        }
                    }
                });
        // 药监局拨号
        mBoHao = (TextView) findViewById(R.id.bo_hao_);
        // 投诉内容
        mContent = (EditText) findViewById(R.id.context_edit);
        // 附件子容器
        mFu_layout_01 = (LinearLayout) findViewById(R.id.fu_jian_layout_01);
        mFu_layout_02 = (LinearLayout) findViewById(R.id.fu_jian_layout_02);
        mFu_layout_03 = (LinearLayout) findViewById(R.id.fu_jian_layout_03);
        // 附件添加子按钮
        mZi_button_01 = (TextView) findViewById(R.id.fu_jian_image_01);
        mZi_button_02 = (TextView) findViewById(R.id.fu_jian_image_02);
        mZi_button_03 = (TextView) findViewById(R.id.fu_jian_image_03);

        mBiaoTi = (EditText) findViewById(R.id.context_edit_biaoti);
        // 选择企业按钮
        mXuanZeFirm = (TextView) findViewById(R.id.xuan_zhe_qi_ye);
        // 姓名 mNamess mDianhua
        mNamess = (TextView) findViewById(R.id.xing_ming_);
        // 电话
        mDianhua = (TextView) findViewById(R.id.dian_hua_hao_ma);
        // 提交按钮
        mTiJiao = (TextView) findViewById(R.id.ti_jiao_an_niu);
        // 下拉框
        mDialogView = new PopDialogView(this, true);
        mInflater = LayoutInflater.from(this);
        mView = mInflater.inflate(R.layout.dialog_view, null);
        // 企业选择
        pickerView = (ListView) mView.findViewById(R.id.piacker_view);
        mid_edittext = (EditText) mView.findViewById(R.id.id_edittext);
        Button_OK = (TextView) mView.findViewById(R.id.BUTTON_OK);
        Button_OFF = (TextView) mView.findViewById(R.id.BUTTON_OFF);
        // 名称和类别选择的父容器
        mLayoutCategory = (LinearLayout) mView
                .findViewById(R.id.layout_content);
        // 选择文件的父容器
        mLayoutFile = (LinearLayout) mView.findViewById(R.id.layout_file);

        mXiangJI = (TextView) mView.findViewById(R.id.xiangji);
        mXiangCE = (TextView) mView.findViewById(R.id.xiangce);
        mFile = (TextView) mView.findViewById(R.id.wenjian);
        //企业名称
        mTouShuFirm = (EditText) findViewById(R.id.tousuqiye_edit_biaoti);
        mViewLsit = LayoutInflater.from(this).inflate(R.layout.firm_list_layout, null);
        listView = (ListView) mViewLsit.findViewById(R.id.frim_list_tab);
        layout = (LinearLayout) findViewById(R.id.fu_layout);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private LinearLayout layout;
    private ListView listView;
    private Handler mHandler = new Handler();
    private boolean boos = false;

    public void showPopwindow() {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mViewLsit, LayoutParams.MATCH_PARENT, dip2px(150));
            // 设置一个空白的背景
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            // 点击屏幕可消失
            mPopupWindow.setOutsideTouchable(false);
            // 获得焦点
            mPopupWindow.setFocusable(false);
            //设置pop消失的监听事件
            mPopupWindow.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //设置按钮可以点击
//							mText.setEnabled(true);
                        }
                    }, 200);
                }
            });
        }
        mPopupWindow.showAsDropDown(findViewById(R.id.layou_rousuqiye_edit));
        ////设置按钮不可以点击
//		mText.setEnabled(false);
    }

    private PopupWindow mYuying;
    private View yyView;
    private TextView mYYButton;

    private YuYIng YUyingUlit = new YuYIng();

    /**
     * TODO:语音输入功能
     *
     * @param xINt
     */
    public void mShowPopwindow(int xINt) {
        yyView = LayoutInflater.from(this).inflate(R.layout.yuying, null);
        mYYButton = (TextView) yyView.findViewById(R.id.text);
        YUyingUlit.setYymssage(this);

        if (AppContext.THEME) {
            mYYButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.yuyin_01));
        } else {
            mYYButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.yuyin_02));
        }

        mYYButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                YUyingUlit.setYuYing(ComplaintReportings.this);
            }
        });
        if (mYuying == null) {
            mYuying = new PopupWindow(yyView, LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            // 设置一个空白的背景
            mYuying.setBackgroundDrawable(new BitmapDrawable());
            // 点击屏幕可消失
            mYuying.setOutsideTouchable(false);
            // 获得焦点
            mYuying.setFocusable(false);
            //设置pop消失的监听事件
            mYuying.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss() {
                    mHandler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            //设置按钮可以点击
//							mText.setEnabled(true);
                        }
                    }, 200);
                }
            });

        }
        mYuying.showAtLocation(LayoutInflater.from(this).inflate(R.layout.complaint_reporting_activity_2, null), Gravity.CENTER, xINt, 0);
        ////设置按钮不可以点击
//		mText.setEnabled(false);
    }

    public static void offDialoge(String name) {
        FirmName = name;
        mXuanZeFirm.setText(name);
        mTouShuFirm.setText(name);
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
        mDialogView.dismiss();
    }

    private void iniData() {
        mDialogView.setContentView(mView);
        mZi_button_01.setOnClickListener(this);
        mZi_button_02.setOnClickListener(this);
        mZi_button_03.setOnClickListener(this);
        Button_OK.setOnClickListener(this);
        Button_OFF.setOnClickListener(this);

        if (AppContext.LoginUserMessage.messageUse) {
            mDianhua.setText(AppContext.LoginUserMessage.bean.getLoginName());
            mNamess.setText(AppContext.LoginUserMessage.bean.getFullName());
        }

        adapter = new ComplaintReportingAdapter(list, this);
        pickerView.setAdapter(adapter);
        listView.setAdapter(adapter);
        mBoHao.setOnClickListener(this);

        mXiangJI.setOnClickListener(this);
        mXiangCE.setOnClickListener(this);
        mFile.setOnClickListener(this);
        mXuanZeFirm.setOnClickListener(this);
        mTiJiao.setOnClickListener(this);
        toRunnable();

        mTouShuFirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {//获得焦点
                    if (boos) {
                        state = 0;
                        showPopwindow();
                    }
                } else {//失去焦点
                    if (mPopupWindow != null) {
                        mPopupWindow.dismiss();
                    }
                }
            }
        });
        mBiaoTi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {//获得焦点
                    state = 1;
                }
            }
        });
        mDianhua.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {//获得焦点
                    state = 4;
                }
            }
        });
        mNamess.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {//获得焦点
                    state = 3;
                }
            }
        });
        mContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {//获得焦点
                    state = 2;
                }
            }
        });
        mDianhua.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                state = 4;
            }
        });
        mContent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                state = 2;
            }
        });
        mNamess.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                state = 3;
            }
        });
        mBiaoTi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                state = 1;
            }
        });
        mContent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                state = 2;
            }
        });
        mTouShuFirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showPopwindow();
                boos = true;
            }
        });
    }

    private int state = 0;

    @Override
    protected void onPause() {
        super.onPause();
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
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
                    toView(bitmap);
                    // 获取相对路径
                    String fileString = Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/myImage/";
                    Log.i("localtion", "onActivityResult: "+fileString);
                    // System.currentTimeMillis() 获取系统当前时间的毫秒值
                    String fileName = System.currentTimeMillis() + ".png";
                    File file = new File(fileString);
                    if (!file.exists()) {
                        file.mkdirs();// 创建文件夹
                    }


                    try {
                        // 是列化FileOutputStream
                        File file1=new File(fileString+fileName);
                        FileOutputStream b = new FileOutputStream(file1);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                        b.flush();
                        b.close();
                        //保存图片后发送广播通知更新数据库
                        Uri uri = Uri.fromFile(file1);
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                        filelist.add(file1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //	File files = new File(fileString + fileName);
                    // filelist.add(files);
                    // adapter.notifyDataSetChanged();
                    //	toView(bitmap);
                    //filelist.add(file);
                }

                break;
            case INT_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        return;
                    }
                    try {
                        Uri uri = data.getData();
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
                        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        actualimagecursor.moveToFirst();
                        String img_path = actualimagecursor
                                .getString(actual_image_column_index);
                        File file = new File(img_path);
                        // adapter.notifyDataSetChanged();

                        Cursor c = getContentResolver().query(uri, null, null, null,
                                null);

                        c.moveToFirst();
                        String path = c.getString(1);
                        // 调整图片的比例
                        Options o = new BitmapFactory.Options();

                        o.inJustDecodeBounds = false;
                        o.inSampleSize = 10;

                        Bitmap bitmap = BitmapFactory.decodeFile(path, o);
                        toView(bitmap);
                        filelist.add(file);
                    } catch (Exception ex) {

                        Uri uri = geturi(data);
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
                        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        actualimagecursor.moveToFirst();
                        String img_path = actualimagecursor
                                .getString(actual_image_column_index);
                        File file = new File(img_path);
                        // adapter.notifyDataSetChanged();

                        Cursor c = getContentResolver().query(uri, null, null, null,
                                null);

                        c.moveToFirst();
                        String path = c.getString(1);
                        // 调整图片的比例
                        Options o = new BitmapFactory.Options();

                        o.inJustDecodeBounds = false;

                        o.inSampleSize = 10;

                        Bitmap bitmap = BitmapFactory.decodeFile(path, o);
                        toView(bitmap);
                        filelist.add(file);
                    }
                }
                break;
        }
    }

    /**
     * 解决小米手机上获取图片路径为null的情况
     *
     * @param intent
     * @return
     */
    public Uri geturi(android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    private ArrayList<FirmTypeBean.Data> getData(ArrayList<FirmTypeBean.Data> datalist) {
        ArrayList<FirmTypeBean.Data> datas = new ArrayList<FirmTypeBean.Data>();
        for (FirmTypeBean.Data data : datalist) {
            if (data.getFirmTypeName().trim().contains("食品经营")
                    || data.getFirmTypeName().trim().contains("餐饮经营")
                    || data.getFirmTypeName().trim().contains("药品经营")
                    || data.getFirmTypeName().trim().contains("药品生产")
                    || data.getFirmTypeName().trim().contains("食品生产")
                    || data.getFirmTypeName().trim().contains("医疗机构")) {
                if (data.getMonitors() != null && data.getMonitors().size() > 0) {
                    data.setIoos(true);
                } else {
                    data.setIoos(false);
                }

                if (data.getAnnualRating() != null
                        && data.getAnnualRating().size() > 0) {
                    //优化 , 抽取代码
                    String rating = data.getAnnualRating().get(0).getRating();
                    if (MyTextUtils.isEmpty(rating)) {
                        if (rating.equals("优秀")) {
                            data.setmRating(1);
                        }
                        if (rating.equals("良好")) {
                            data.setmRating(2);
                        }
                        if (rating.equals("一般")) {
                            data.setmRating(3);
                        }
                        if (rating.equals("不予评级")) {
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

    @SuppressLint("NewApi")
    private void toView(Bitmap bitmap) {
        switch (STATE) {
            case 0:
                mZi_button_01.setBackground(new BitmapDrawable(bitmap));
                mFu_layout_02.setVisibility(LinearLayout.VISIBLE);
                break;
            case 1:
                mZi_button_02.setBackground(new BitmapDrawable(bitmap));
                mFu_layout_03.setVisibility(LinearLayout.VISIBLE);
                break;
            case 2:
                mZi_button_03.setBackground(new BitmapDrawable(bitmap));
                break;
        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.BUTTON_OK:

                String mes = mid_edittext.getText().toString().trim();
                list.clear();

                for (FirmTypeBean.Data data : getData(AppContext.List.FirmTapeDataList)) {
                    if (data.getFirmName().contains(mes) || mes.equals("")) {
                        list.add(data);
                    }
                }
                adapter.notifyDataSetChanged();

                break;
            case R.id.BUTTON_OFF:
                mid_edittext.setText("");
                list.clear();
                for (FirmTypeBean.Data data : getData(AppContext.List.FirmTapeDataList)) {
                    list.add(data);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.fu_jian_image_01:
                //任务100 bug1250 动态获取摄像头权限
                if (Build.VERSION.SDK_INT>22) {
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //先判断有没有权限 ，没有就在这里进行权限的申请
                        ActivityCompat.requestPermissions(this,
                                new String[]{android.Manifest.permission.CAMERA}, CAMERA_OK);
                    }
                }
                mLayoutCategory.setVisibility(LinearLayout.GONE);
                mLayoutFile.setVisibility(LinearLayout.VISIBLE);
                DialogSet();
                mDialogView.show();
                STATE = 0;
                break;
            case R.id.fu_jian_image_02:
                mLayoutCategory.setVisibility(LinearLayout.GONE);
                mLayoutFile.setVisibility(LinearLayout.VISIBLE);
                DialogSet();
                mDialogView.show();
                STATE = 1;
                break;
            case R.id.fu_jian_image_03:
                mLayoutCategory.setVisibility(LinearLayout.GONE);
                mLayoutFile.setVisibility(LinearLayout.VISIBLE);
                DialogSet();
                mDialogView.show();
                STATE = 2;
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
            case R.id.wenjian:
                mDialogView.dismiss();
                break;
            case R.id.bo_hao_:
                // 调用拨号盘
                Intent intent12331 = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:12331"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent12331);
                break;
            case R.id.xuan_zhe_qi_ye:
                mLayoutCategory.setVisibility(LinearLayout.VISIBLE);
                mLayoutFile.setVisibility(LinearLayout.GONE);
                DialogSet();
                mDialogView.show();
                break;
            case R.id.ti_jiao_an_niu:
                AppContext.List.fileList = filelist;
                for (File file : AppContext.List.fileList) {
                    map2.put(file.getName(), file);
                }
                final String Phone = mDianhua.getText().toString().trim();
                String mName = mNamess.getText().toString().trim();

                String Enterprise = FirmName;
                String tousuBiaoti = mBiaoTi.getText().toString().trim();
                String tousuFirm = mTouShuFirm.getText().toString().trim();
                String content = mContent.getText().toString().trim();
                String Size = AppContext.List.fileList.size() + "";
                String Accessory = "true";

                if (content.equals("")) {
                    showToast("请输入举报内容！");
                    return;

                }
			if (tousuFirm.equals("")) {
				showToast("请输入企业名称");
				return;

			}
                if (tousuBiaoti.equals("")) {
                    showToast("请输入被投诉标题！");
                    return;

                }
                if (Phone.equals("")) {
                    showToast("请输入手机号码");
                    return;
                }
                if (Phone.length() != 11) {
                    showToast("请输入11位的手机号码");
                    return;
                }
                map3.put("PhoneNumber", Phone);
                map3.put("Defendant", Enterprise);
                map3.put("Content", content);
                map3.put("Complainant", mName);
                map3.put("Tile", mBiaoTi.getText().toString().trim());
                showWaitDialog("正在提交举报数据");
                new ConnectionUtility( WPConfig.URL_POST_CESHI, "POST", map3,
                        new JSONdata() {

                            @Override
                            public void httpResponse(String json) {
                                Looper.prepare();
                                if (json.equals("连接失败")) {
                                    showToast(json);
                                } else {
                                    LoginBean.RegistereBean bean = ParsonJson
                                            .jsonToBeanObj(json,
                                                    LoginBean.RegistereBean.class);
                                    boolean is;
//								boolean is = bean != null ? bean.getState()
//										.equals("false") ? false : true : false;
                                    if (bean != null && bean.getState().equals("true") && Phone.length() == 11) {
                                        is = true;
                                    } else {
                                        is = false;
                                    }
                                    if (is) {
                                        showToast("举报成功");
                                        finish();
                                    } else {
                                        showToast("举报失败");
                                        dismissWaitDialog();
                                    }
                                }
                                Looper.loop();
                            }
                        }, map2);
                break;
        }
    }

    private TimerTask mSeekTask;
    private String mSeekString = "";
    private Timer mSeekTimer;
    private int Ints = 0;

    private void toRunnable() {
        mSeekTask = new TimerTask() {
            @Override
            public void run() {

                String str = mTouShuFirm.getText().toString().trim();
                if (!mSeekString.equals(str)) {
                    mSeekString = str;
                    Bundle bundle = new Bundle();
                    Message mMessage = ss.obtainMessage();
                    bundle.putString("data", str);
                    mMessage.setData(bundle);
                    ss.dispatchMessage(mMessage);
                    Log.d("测试数据", str);
                }
                Ints++;
            }

        };
        mSeekTimer = new Timer();
        mSeekTimer.schedule(mSeekTask, 0, 100);
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            //刷新list表
            adapter.notifyDataSetChanged();
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
                }
                handler.post(runnable);
            }
        }
    }

    @Override
    public void onYYdata(String str) {

        Log.d("语音输入文字", str);
        if (!str.equals("。")) {
            switch (state) {
                case 0:
                    String toushufirm = mTouShuFirm.getText().toString().trim();
                    mTouShuFirm.setText(toushufirm + str);
                    break;
                case 1:
                    String BiaoTi = mBiaoTi.getText().toString().trim();
                    mBiaoTi.setText(BiaoTi + str);
                    break;
                case 2:
                    String content = mContent.getText().toString().trim();
                    mContent.setText(content + str);
                    break;
                case 3:
                    String name = mNamess.getText().toString().trim();
                    mNamess.setText(name + str);
                    break;
                case 4:
                    String pw = mDianhua.getText().toString().trim();
                    mDianhua.setText(pw + str);
                    break;
            }
        }
    }
}
