package com.heking.qsy.activity.Personalcenters;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.ForTheConvenienceOfService;
import com.heking.qsy.activity.IWantToReview;
import com.heking.qsy.activity.Personalcenters.util.RegisteredBean;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.JsonFile;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener,
        JSONdata {
    private TextView mRegistered, mLoginButton;
    private EditText mUser, mPassword;
    private CheckBox mCheckBox;
    private ImageView passwordImg;
    private String user, password;
    private WaitDialog dialog;
    private Builder builder;
    private Builder builder2;
    private Builder builder3;
    // private TextView mWangjimima;

    private Bundle bundle;
    private Intent intent;
    //	chrome://inspect
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Boolean isDisplay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.login_activity);
        preferences = getSharedPreferences("initialize",
                Activity.MODE_PRIVATE);
        editor = preferences.edit();
        iniView();
        iniData();

    }

    private void iniView() {
        Tool.endActivity(this);
        intent = getIntent();
        bundle = intent.getExtras();

        mRegistered = (TextView) findViewById(R.id.zu_che);
        mUser = (EditText) findViewById(R.id.user_edit);
        mPassword = (EditText) findViewById(R.id.password_edit);
        passwordImg = findViewById(R.id.password_img);
        if (!TextUtils.isEmpty(preferences.getString("user", ""))) {
            mUser.setText(preferences.getString("user", ""));
        }
        //增加选择框已决定是否记住密码
        mCheckBox = (CheckBox) findViewById(R.id.password_check);
        mCheckBox.setChecked(preferences.getBoolean("isCheck", false));
        if (!TextUtils.isEmpty(preferences.getString("password", "")) && mCheckBox.isChecked()) {
            mPassword.setText(preferences.getString("password", ""));
        }
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    editor.putBoolean("isCheck", true);
                } else {
                    editor.putBoolean("isCheck", false);
                }
            }
        });

//     bug682  没有做记住账号密码的选择，默认是自动记录账号密码的，安全性不好
//		if (!TextUtils.isEmpty(preferences.getString("user", ""))) {
//			mUser.setText(preferences.getString("user", ""));
//		}
//		if (!TextUtils.isEmpty(preferences.getString("password", ""))) {
//			mPassword.setText(preferences.getString("password", ""));
//		}

        mLoginButton = (TextView) findViewById(R.id.login_button);
        // mWangjimima=(TextView) findViewById(R.id.wangjimiam);
        ((TextView) findViewById(R.id.version_name)).setText("版本号："
                + Tool.getVerName(this));
        //任务100 bug1244  密码显式显式和隐式显式的切换;
        passwordImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDisplay) {
                    mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isDisplay = false;
                } else {
                    mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isDisplay = true;
                }
            }
        });
    }

    private void iniData() {
        mRegistered.setVisibility(TextView.GONE);
        mRegistered.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        // mWangjimima.setOnClickListener(this);
        dialog = new WaitDialog(this);
        dialog.setContent("登录中，请稍后");
        builder = new Builder(this);
        builder2 = new Builder(this);

        builder2.setTitle("提示");
        builder2.setMessage("登录失败，请检查您的账号，和密码");
        builder.setTitle("提示");
        builder.setMessage("连接失败，请检查您的网络。");
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
//            case R.id.zu_che:
//                Intent intent = new Intent(this, RegisteredActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("Message", "注册");
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
            case R.id.login_button:
                user = mUser.getText().toString().trim();
                password = mPassword.getText().toString().trim();

                if (user.equals("") || password.equals("")) {
                    Toast.makeText(this, "请输入账号或者密码", Toast.LENGTH_LONG).show();
                    return;
                }

                Map<String, String> datamap = new HashMap<String, String>();
                datamap.put("LoginName", user);
                datamap.put("Password", password);
                datamap.put("ProductKey", "F9EC1B7E-3153-4157-83EC-DDB00AB31666");
                datamap.put("LoginStyle", "MOBILE");
                datamap.put("AreaCode", AppContext.BundelPoiMess.Address);
                datamap.put("LoginDevice", android.os.Build.MODEL);
                String jsonlogin = ParsonJson.mapToJson(datamap);

                JsonFile jsonFile = new JsonFile();
                File file;
                File file2 = null;
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    file = Environment.getExternalStorageDirectory();
                    file2 = new File(file + "/PZhMessageList/");
                    if (!file2.exists()) {
                        file2.mkdirs();
                    }
                }
                File ss = new File(file2 + "/LoginData.dll");
                try {
                    jsonFile.FileJson(jsonlogin, ss);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.show();

                Tool.toHttpGEtandPost(WPConfig.PERSONAL_CENTERS
                                + AppContext.Submit.LOGIN, "POST", datamap, this, null,
                        true);
                break;
            // case R.id.wangjimiam:
            // Intent intent2=new Intent(this, ForgetPassword.class);
            // Bundle bundle2=new Bundle();
            // startActivity(intent2);
            // break;
        }
    }

    @Override
    public void httpResponse(String json) {
        if (json.trim().equals("连接失败")) {
            Looper.prepare();
            dialog.dismiss();
            builder.show();
            Looper.loop();

        }
        // {"ID":71,"UID":"760a206d-2aed-4e79-b4aa-8fa0e00a3667","SourceTypeID":null,"SocialTypeID":null,"FullName":"13883969835","NickName":null,"GenderID":null,"BOD":null,"IDCardType":null,"IDCardNo":null,"AreaCode":null,"Address":null,"PostalCode":null,"PhoneNumber":"13883969835","Email":null,"MobilePhoneNumber":"13883969835","QQ":null,"EducationLevel":null,"Industry":null,"Occupation":null,"IncomeLevel":null,"ImageID":null,"ChangedTime":"2016-08-26T09:06:57.42","Accounts":[{"ID":68,"AccountID":68,"UID":71,"LoginName":"13883969835","Password":"","Status":1,"Rank":null,"Score":null,"Balance":null,"ChangedTime":"2016-08-26T09:06:57.42","User":null,"StatusName":"正常","ProductKey":null,"AreaCode":null,"LoginStyle":null,"LoginDevice":null}],"LoginName":"13883969835","Password":null,"ValidCode":null}
        RegisteredBean bean = ParsonJson.jsonToBeanObj(json,
                RegisteredBean.class);

        if (bean != null && bean.getLoginName() != null) {
            AppContext.LOGIN = true;
            switch (AppContext.LoginUserMessage.STATE) {
                case AppContext.LoginUserMessage.LOGIN_IN_TO:
                    editor.putString("user", user);
                    editor.putString("password", password);
                    //任务100  bug1201 增加一个变量来保存登录状态信息
                    editor.putBoolean("isLogin", AppContext.LOGIN);
                    editor.putString("jsonData", json.trim());
                    editor.commit();
                    AppContext.LoginUserMessage.bean = bean;
                    AppContext.LoginUserMessage.messageUse = true;
                    bundle.putSerializable("MessageData", bean);
                    intent.putExtras(bundle);
                    setResult(Activity.RESULT_OK, intent);// 返回页面1
                    LoginActivity.this.finish();
                    break;
                case AppContext.LoginUserMessage.I_WANT_TO_REVIEW:
                    AppContext.LoginUserMessage.bean = bean;
                    AppContext.LoginUserMessage.messageUse = true;
                    Tool.toActivity(LoginActivity.this, IWantToReview.class);
                    LoginActivity.this.finish();
                    break;
                case AppContext.LoginUserMessage.FOR_THE_CONVENIENCE_OF_SERVICE:
                    AppContext.LoginUserMessage.bean = bean;
                    AppContext.LoginUserMessage.messageUse = true;
                    Tool.toActivity(LoginActivity.this,
                            ForTheConvenienceOfService.class);
                    LoginActivity.this.finish();
                    break;
            }
        } else {
            Looper.prepare();
            builder2.show();
            dialog.dismiss();
            Looper.loop();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.cancel();
        }
    }

}
