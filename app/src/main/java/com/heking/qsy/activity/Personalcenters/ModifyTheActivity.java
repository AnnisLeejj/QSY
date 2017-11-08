package com.heking.qsy.activity.Personalcenters;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.Personalcenters.util.RegisteredBean;
import com.heking.qsy.providers.HttpImage;
import com.heking.qsy.providers.ImageBitmap;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.DateUntl;
import com.heking.qsy.util.Operation;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.PickerView;
import com.heking.qsy.util.PickerView.onSelectListener;
import com.heking.qsy.util.PopDialogView;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ModifyTheActivity extends Activity implements ImageBitmap,
        OnClickListener {
    private Intent intent;
    private Bundle bundle;
    private TextView mImage, mGender, mBirthday, mSubmit;
    private TextView mName, mEmail;
    private static final int INT_CAMERA = 100000;
    private static final int INT_PHOTO = 111111;
    private File filesss = null;
    private View mView;
    private TextView mXiangJI, mXiangCE, mCancel;
    private View mAgeAndGenderView;
    private static PopDialogView mDialogView;
    private static PopDialogView mDialogViewimage;
    private LinearLayout relativeLayout;
    private LinearLayout radioGroup;
    private RegisteredBean bean;
    private PickerView yyyyView, MMView, ddView, GenderPicker;
    private Bitmap bitmap;
    private String fileUrl;
    /**
     * 定义存储“年”集合范围：（1970年——3000年）；
     */
    private ArrayList<String> yyyy;

    /**
     * 定义存储“月”集合（1~12）
     */
    private ArrayList<String> MM;

    /**
     * 定义存储 “日”集合
     */
    private ArrayList<String> dd;
    /**
     * 年，月，日
     */
    private String year, month, daytime;

    private String yyyStrng, mmString, ddString;

    private TextView mZhuxiaoButton, backTv;

    private ArrayList<String> Genderliast = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.modify_the_activity);
        iniView();
        iniData();
    }

    private void iniView() {
        intent = getIntent();
        bundle = intent.getExtras();

        Tool.endActivity(this);
        Genderliast.add("男");
        Genderliast.add("女");

        intent = getIntent();
        bundle = intent.getExtras();

        mImage = (TextView) findViewById(R.id.image_view);
        mGender = (TextView) findViewById(R.id.xB_text_view);
        mBirthday = (TextView) findViewById(R.id.sR_text_View);

        mName = (TextView) findViewById(R.id.Name_edittext);
        mEmail = (TextView) findViewById(R.id.Email_edittext);

        mView = LayoutInflater.from(this).inflate(R.layout.file_view, null);
        mXiangJI = (TextView) mView.findViewById(R.id.xiangji);
        mXiangCE = (TextView) mView.findViewById(R.id.xiangce);
        mCancel = (TextView) mView.findViewById(R.id.cancel);

        mZhuxiaoButton = (TextView) findViewById(R.id.zhuxiao_button);
        backTv = (TextView) findViewById(R.id.textview);

        // 下拉框
        mDialogView = new PopDialogView(this);
        mDialogViewimage = new PopDialogView(this, true);


        mAgeAndGenderView = LayoutInflater.from(this).inflate(
                R.layout.modify_the_activity_view, null);

        relativeLayout = (LinearLayout) mAgeAndGenderView
                .findViewById(R.id.picker_view_layout);
        radioGroup = (LinearLayout) mAgeAndGenderView
                .findViewById(R.id.gender_layout);
        yyyyView = (PickerView) mAgeAndGenderView
                .findViewById(R.id.pack_biew_yyy);
        MMView = (PickerView) mAgeAndGenderView.findViewById(R.id.pack_biew_MM);
        ddView = (PickerView) mAgeAndGenderView.findViewById(R.id.pack_biew_dd);
        GenderPicker = (PickerView) mAgeAndGenderView.findViewById(R.id.pickerview_mals);
        TimeDate();
        iniViews();
    }

    private void TimeDate() {
        Date mDate = new Date();
        SimpleDateFormat dfy = new SimpleDateFormat("yyyy");
        SimpleDateFormat dfm = new SimpleDateFormat("MM");
        SimpleDateFormat dfd = new SimpleDateFormat("dd");

        // 取年月日
        year = dfy.format(mDate);
        month = dfm.format(mDate);
        daytime = dfd.format(mDate);
        yyyy = DateUntl.getYear(Integer.parseInt(year));
        MM = DateUntl.getMonth();
        // 取当前年
        yyyStrng = year;
        mmString = month;
        ddString = daytime;

        dd = DateUntl.getDaytime(year, month);

        // 定义加载年月日
        yyyyView.setData(yyyy, year);
        MMView.setData(MM, month);
        ddView.setData(dd, daytime);

        // 滚动监听
        ddView.setOnSelectListener(new onSelectListener() {

            @Override
            public void onSelect(String text) {
                ddString = text;

            }
        });
        MMView.setOnSelectListener(new onSelectListener() {

            @Override
            public void onSelect(String text) {
                mmString = text;
                dd = DateUntl.getDaytime(yyyStrng, mmString);
                ddView.setData(dd, daytime);
            }
        });
        yyyyView.setOnSelectListener(new onSelectListener() {

            @Override
            public void onSelect(String text) {
                yyyStrng = text;
            }
        });
        mZhuxiaoButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AppContext.LoginUserMessage.STATE = AppContext.LoginUserMessage.LOGIN_IN_TO;
                AppContext.LoginUserMessage.messageUse = false;
                AppContext.LoginUserMessage.bean = null;
                Tool.ZXfile();
                bundle.putSerializable("MessageData", null);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);// 返回页面1
                ModifyTheActivity.this.finish();
            }
        });
        //任务100 bug1240 退出时，保存修改信息
        backTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Tijiao(10);
                bundle.putSerializable("MessageData", null);
                bundle.putString("name", mName.getText().toString().trim());
                bundle.putString("fileUrl", fileUrl);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);// 返回页面1
                finish();
            }
        });
    }

    private void iniData() {
        Tool.toHttpGEtandPost(WPConfig.PERSONAL_CENTERS
                + AppContext.Parameter.GET_USER, "GET", null, new JSONdata() {

            @Override
            public void httpResponse(String json) {
                if (json.equals("连接失败")) {
                    Toast.makeText(ModifyTheActivity.this, "更新资料失败",
                            Toast.LENGTH_SHORT).show();
                }
                bean = ParsonJson.jsonToBeanObj(json, RegisteredBean.class);

                if (bean != null && bean.getLoginName() != null) {
                    AppContext.LoginUserMessage.bean = bean;
                }

                if (bean != null && bean.getImageID() != null) {

                    final String url = WPConfig.IMAGE_VIEW
                            + bean.getImageID();
                    new HttpImage(ModifyTheActivity.this, url, null);
                }
                boolean ioo1 = bean != null && bean.getBOD() != null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    if (ioo1) {
                        date = sdf.parse(bean.getBOD().split("T")[0]);
                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                AppContext.ChangeMess.name = bean.getFullName();
                AppContext.ChangeMess.Email = bean.getEmail();
                SimpleDateFormat dfd = new SimpleDateFormat("yyyy-MM-dd");
                boolean ioo = bean != null && bean.getGenderID() != null;

                boolean ioo2 = bean != null && bean.getFullName() != null;
                boolean ioo3 = bean != null && bean.getEmail() != null;

                if (ioo) {

                    if (bean.getGenderID().trim().equals("1")) {
                        mGender.setText("男");
                    } else if (bean.getGenderID().trim().equals("2")) {
                        mGender.setText("女");
                    } else if (bean.getGenderID().trim().equals("0")) {
                        mGender.setText("未知");
                    } else {
                        mGender.setText("未知");
                    }

                }
                if (ioo1) {
                    mBirthday.setText(dfd.format(date));
                }
                if (ioo2) {

                    mName.setText(AppContext.ChangeMess.name);
                }
                if (ioo3) {
                    mEmail.setText(AppContext.ChangeMess.Email);
                }
            }
        }, null, false);
        mName.setOnClickListener(this);
        mEmail.setOnClickListener(this);
        mImage.setOnClickListener(this);
        mXiangJI.setOnClickListener(this);
        mXiangCE.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mBirthday.setOnClickListener(this);
        mGender.setOnClickListener(this);
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

    private void DialogSet(boolean boo) {
        if (boo) {
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = mDialogView.getWindow().getAttributes();
            lp.width = (int) (display.getWidth()); // 设置宽度
        } else {
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = mDialogViewimage.getWindow().getAttributes();
            lp.width = (int) (display.getWidth()); // 设置宽度
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void toBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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
            this.bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            b.flush();
            b.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileUrl = fileString + fileName;
        Log.i("Modify3", "onActivityResult: " + fileString + fileName);
        mImage.setBackground(new BitmapDrawable(Tool.toRoundBitmap(new Operation().comp(bitmap))));
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.image_view:
                mDialogViewimage.setContentView(mView);
                DialogSet(false);
                mDialogViewimage.show();
                break;
            case R.id.xiangji:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, INT_CAMERA);
                mDialogViewimage.dismiss();
                break;
            case R.id.xiangce:
                photo();
                mDialogViewimage.dismiss();
                break;
            case R.id.cancel:
                mDialogViewimage.dismiss();
                break;
            case R.id.Name_edittext:
                Intent intentName = new Intent(this, ChangeNameActivity.class);
                intentName.putExtra("name", mName.getText());
                startActivity(intentName);
                break;
            case R.id.Email_edittext:
                Intent intentEmail = new Intent(this, ChangeEmailActivity.class);
                intentEmail.putExtra("Email", mEmail.getText());
                startActivity(intentEmail);
                break;
            case R.id.xB_text_view:
                String xbstring = ((TextView) arg0).getText().toString().trim();
                boolean ioo = xbstring.equals("男") ? true
                        : xbstring.equals("") ? true : false;
                if (ioo) {
                    GenderPicker.setData(Genderliast, "男");
                } else {
                    GenderPicker.setData(Genderliast, "女");
                }
                GenderPicker.setOnSelectListener(new onSelectListener() {

                    @Override
                    public void onSelect(String text) {
                        Gender = text.trim();
                    }
                });
                mDialogView.setContentView(mAgeAndGenderView);
                radioGroup.setVisibility(LinearLayout.VISIBLE);
                relativeLayout.setVisibility(LinearLayout.GONE);
                DialogSet();
                mDialogView.show();
                break;

            case R.id.sR_text_View:
                mDialogView.setContentView(mAgeAndGenderView);
                radioGroup.setVisibility(LinearLayout.GONE);
                relativeLayout.setVisibility(LinearLayout.VISIBLE);
                DialogSet();

                if (!TextUtils.isEmpty(mBirthday.getText())) {
                    String dattTimestr = mBirthday.getText().toString();
                    String dattTime[] = dattTimestr.split("-");
                    year = dattTime[0];
                    month = dattTime[1];
                    daytime = dattTime[2];
                    yyyyView.setSelected(year);
                    MMView.setSelected(month);
                    ddView.setSelected(daytime);
                }
                mDialogView.show();
                break;
        }
    }

    @SuppressLint("NewApi")
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

                    bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                    Operation operation = new Operation();
                    mImage.setBackground(new BitmapDrawable(operation.comp(Tool.toRoundBitmap(bitmap))));

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
                    filesss = new File(fileString + fileName);
                    fileUrl = fileString + fileName;
                    Log.i("Modify1", "onActivityResult: " + fileString + fileName);
                    Log.i("Modify", "onActivityResult: " + filesss.getName());
                    Tijiao(0);
                }

                break;
            case INT_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        return;
                    }
                    // Bundle bundle = data.getExtras();
                    // // 接收一个数据位图过来。
                    // Bitmap bitmap = (Bitmap) bundle.get("data");
                    // mBackImage.setBackground(new BitmapDrawable(bitmap));
                    Uri uri = data.getData();

                    Cursor c = getContentResolver().query(uri, null, null, null,
                            null);

                    c.moveToFirst();

                    String path = c.getString(1);
                    // 调整图片的比例
                    Options o = new BitmapFactory.Options();

                    // o.inJustDecodeBounds = false;

                    // o.inSampleSize=120;

                    bitmap = BitmapFactory.decodeFile(path, o);

                    mImage.setBackground(new BitmapDrawable(Tool.toRoundBitmap(bitmap)));


                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor actualimagecursor = managedQuery(uri, proj, null, null,
                            null);
                    int actual_image_column_index = actualimagecursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    actualimagecursor.moveToFirst();
                    String img_path = actualimagecursor
                            .getString(actual_image_column_index);
                    filesss = new File(img_path);
                    fileUrl = img_path;
                    Log.i("Modify2", "onActivityResult: " + img_path);
                    Log.i("Modify", "onActivityResult: " + filesss.getName());
                    Tijiao(0);
                }
                break;
        }
    }

    private void photo() {

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

    /**
     * onButton 年龄选择确认按钮 offButton 取消按钮 onButton1 性别选择确认按钮 offButton2 取消按钮
     */
    private TextView onButton, offButton, onButton1, offButton1;

    private OnClickListener off_oncli = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            mDialogView.dismiss();

        }
    };

    private String Gender = null;

    private void iniViews() {
        if (mGender.getText() == null || mGender.getText().equals("")) {
            Gender = "男";
        } else {
            Gender = mGender.getText().toString().trim();
        }

        onButton = (TextView) mAgeAndGenderView
                .findViewById(R.id.on_button_view);
        offButton = (TextView) mAgeAndGenderView
                .findViewById(R.id.off_button_view);

        onButton1 = (TextView) mAgeAndGenderView
                .findViewById(R.id.on_1_button_view);
        offButton1 = (TextView) mAgeAndGenderView
                .findViewById(R.id.off_1_button_view);

        offButton.setOnClickListener(off_oncli);
        offButton1.setOnClickListener(off_oncli);

        onButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mBirthday.setText(yyyStrng + "-" + mmString + "-" + ddString);
                mDialogView.dismiss();
            }
        });
        onButton1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mGender.setText(Gender);
                mDialogView.dismiss();
            }
        });

//		findViewById(R.id.Save_button).setOnClickListener(
//				new OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//						Map<String, String> mapdata = new HashMap<String, String>();
//						Map<String, File> mapfile = new HashMap<String, File>();
//						String nkey = "[a-zA-Z0-9_\\-\\.]+@[a-zA-Z0-9]+(\\.(com|cn|org|edu|hk))";
//						if (!mEmail.getText().toString().trim().equals("")) {
//							if (!mEmail.getText().toString().trim()
//									.matches(nkey)) {
//								Toast.makeText(ModifyTheActivity.this,
//										"请输入正确邮箱地址", 500).show();
//								return;
//							}
//						}
//						mapdata.put("FullName", mName.getText().toString()
//								.trim());
//						mapdata.put(
//								"GenderID",
//								mGender.getText().toString().trim().equals("男") ? 1 + ""
//										: mGender.getText().toString().trim()
//												.equals("女") ? 2 + "" : 0 + "");
//						mapdata.put("BOD", mBirthday.getText().toString()
//								.trim());
//						mapdata.put("Email", mEmail.getText().toString().trim());
//
//						mapdata.put("LoginStyle", bean.getLoginName());
//						mapdata.put("LoginDevice", android.os.Build.MODEL);
//						mapdata.put("ProductKey",
//								"F9EC1B7E-3153-4157-83EC-DDB00AB31666");
//
//						if (filesss != null) {
//							mapfile.put(filesss.getName(), filesss);
//						} else {
//							mapfile = null;
//						}
//
//						Tool.toHttpGEtandPost(AppContext.Url.PERSONAL_CENTERS
//								+ AppContext.Submit.UPDATE_USER_FOR_PZH,
//								"POST", mapdata, ModifyTheActivity.this,
//								mapfile, false);
//					}
//				});
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mName.setText(AppContext.ChangeMess.name);
        mEmail.setText(AppContext.ChangeMess.Email);
    }

    public void Tijiao(final int i) {
        Map<String, String> mapdata = new HashMap<String, String>();
        Map<String, File> mapfile = new HashMap<String, File>();
        String nkey = "[a-zA-Z0-9_\\-\\.]+@[a-zA-Z0-9]+(\\.(com|cn|org|edu|hk))";
        if (!mEmail.getText().toString().trim().equals("")) {
            if (!mEmail.getText().toString().trim()
                    .matches(nkey)) {
                Toast.makeText(ModifyTheActivity.this,
                        "请输入正确邮箱地址", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        mapdata.put("FullName", mName.getText().toString()
                .trim());
        mapdata.put(
                "GenderID",
                mGender.getText().toString().trim().equals("男") ? 1 + ""
                        : mGender.getText().toString().trim()
                        .equals("女") ? 2 + "" : 0 + "");
        mapdata.put("BOD", mBirthday.getText().toString()
                .trim());
        mapdata.put("Email", mEmail.getText().toString().trim());

        mapdata.put("LoginStyle", bean.getLoginName());
        mapdata.put("LoginDevice", android.os.Build.MODEL);
        mapdata.put("ProductKey",
                "F9EC1B7E-3153-4157-83EC-DDB00AB31666");
        if (filesss != null) {
            mapfile.put(filesss.getName(), filesss);
        } else {
            mapfile = null;
        }

        Tool.toHttpGEtandPost(WPConfig.PERSONAL_CENTERS + AppContext.Submit.UPDATE_USER_FOR_PZH,
                "POST", mapdata, new JSONdata() {
                    @Override
                    public void httpResponse(String json) {
                        Looper.prepare();
                        Log.d("测试数据", json);
                        if (json.equals("连接失败")) {
                            Toast.makeText(ModifyTheActivity.this, "连接失败请检查网络！", Toast.LENGTH_SHORT).show();
                        } else {
                            RegisteredBean bean = ParsonJson.jsonToBeanObj(json,
                                    RegisteredBean.class);

                            if (i != 10)
                                if (bean != null && bean.getMessage() != null
                                        && bean.getMessage().equals("True")) {
                                    Toast.makeText(ModifyTheActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    //ModifyTheActivity.this.finish();
                                } else {
                                    Toast.makeText(ModifyTheActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                }
                        }
                        Looper.loop();
                    }
                },
                mapfile, false);
    }

}
