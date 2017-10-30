package com.heking.qsy.activity;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.Dialog.CustomDialog;
import com.heking.qsy.activity.Personalcenters.AboutActivity;
import com.heking.qsy.activity.Personalcenters.FeedbackActivity;
import com.heking.qsy.activity.Personalcenters.LoginActivity;
import com.heking.qsy.activity.Personalcenters.ModifyTheActivity;
import com.heking.qsy.activity.Personalcenters.SharePurposed;
import com.heking.qsy.activity.Personalcenters.util.DataCleanManager;
import com.heking.qsy.activity.Personalcenters.util.RegisteredBean;
import com.heking.qsy.activity.Personalcenters.util.VerNameBean;
import com.heking.qsy.base.BaseFragment;
import com.heking.qsy.providers.HttpImage;
import com.heking.qsy.providers.ImageBitmap;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Personalcenter extends BaseFragment implements OnClickListener, JSONdata, ImageBitmap {
    private TextView mLoginButton, mShareButton, mRemoveButton, mUpdateButton, mScoreButton, mFeedbackButton,
            mAboutButton, mPasswrodServer;
    private final static int INT_DATA_STATE = 11;
    private final static int INT_DATA_STATE_VA = 22;
    private static int INT_DATA = INT_DATA_STATE;
    private RegisteredBean bean;
    private TextView mTouxiang;
    private Bitmap bitmap;
    private String huancun;
    private TextView huc;
    private TextView mVersions;
    private TextView exit_button;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private LinearLayout exit_layout;
    // 昵称处理handler；
    private Handler handler = new Handler();
    // 昵称处理子线程
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final String url = WPConfig.IMAGE_VIEW + bean.getImageID();
            if (bean.getFullName() != null && !bean.getFullName().trim().equals("")) {
                mLoginButton.setText(bean.getFullName());
                mLoginButton.setBackgroundColor(Color.parseColor("#00000000"));
            } else {
                mLoginButton.setText(bean.getLoginName());
                mLoginButton.setBackgroundColor(Color.parseColor("#00000000"));
            }
        }
    };

    @Override
    public String getMyTag() {
        return "Personalcenter";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.personal_center_activity, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preferences = getActivity().getSharedPreferences("initialize",
                Activity.MODE_PRIVATE);
        editor = preferences.edit();
        iniView();
        iniData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void iniView() {
        if (AppContext.LoginUserMessage.messageUse) {
            HttpHelper.getInstance().service.get(WPConfig.PERSONAL_CENTERS + AppContext.Parameter.GET_USER).
                    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(String json) {
                    if (json.equals("连接失败")) {
                        Toast.makeText(getActivity(), "更新资料失败", Toast.LENGTH_SHORT).show();
                    }
                    if (json.startsWith("<!DOCTYPE html>")) {
                        return;
                    }
                    bean = ParsonJson.jsonToBeanObj(json, RegisteredBean.class);
                    if (bean != null && bean.getLoginName() != null) {
                        AppContext.LoginUserMessage.bean = bean;
                    }
                    if (bean != null) {
                        handler.post(runnable);
                        final String url = WPConfig.IMAGE_VIEW + bean.getImageID();
                        new HttpImage(Personalcenter.this, url, null);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }

        // getView().findViewById(R.id.zhu_ti).setOnClickListener(new OnClickListener()
        // {
        //
        // @Override
        // public void onClick(View arg0) {
        // if (AppContext.THEME) {
        // AppContext.THEME = false;
        // } else {
        // AppContext.THEME = true;
        // }
        // if (AppContext.THEME) {
        // setTheme(R.style.SwitchTheme_1);
        // } else {
        // setTheme(R.style.SwitchTheme_2);
        // }
        //
        // setContentView(R.layout.personal_center_activity);
        // iniView();
        // iniData();
        //
        // }
        // });
        // findViewById(R.id.pw_Sever).setVisibility(LinearLayout.GONE);
        // 缓存大小显示
        huc = (TextView) getView().findViewById(R.id.hucun_button);
        // 版本号显示
        mVersions = (TextView) getView().findViewById(R.id.getVerName_button);

        try {
            huancun = DataCleanManager.getTotalCacheSize(getActivity());
            huc.setText(huancun);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
        }

        mLoginButton = (TextView) getView().findViewById(R.id.login_button);
        mShareButton = (TextView) getView().findViewById(R.id.share_button);
        mRemoveButton = (TextView) getView().findViewById(R.id.remove_button);

        mUpdateButton = (TextView) getView().findViewById(R.id.ban_ben_gen_xin);
        mScoreButton = (TextView) getView().findViewById(R.id.score_button);
        mFeedbackButton = (TextView) getView().findViewById(R.id.feedback_button);

        mAboutButton = (TextView) getView().findViewById(R.id.about_button);

        mTouxiang = (TextView) getView().findViewById(R.id.image_back);

        mPasswrodServer = (TextView) getView().findViewById(R.id.pa_button);

        exit_button = (TextView) getView().findViewById(R.id.exit_button);
        exit_layout = (LinearLayout) getView().findViewById(R.id.exit_layout);
        //
        // if(AppContext.LOGIN== true){
        // exit_layout.setVisibility(View.VISIBLE);
        // }else {
        // exit_layout.setVisibility(View.GONE);
        // }
        //
    }

    private void iniData() {

        if (!mLoginButton.getText().toString().trim().equals("登录") && !AppContext.LoginUserMessage.messageUse) {
            mLoginButton.setText("登 录");
            CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
            builder.setMessage("请重新登录你的账号");
            builder.setTitle("温馨提示");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    AppContext.LoginUserMessage.STATE = AppContext.LoginUserMessage.LOGIN_IN_TO;
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    Bundle bundle = new Bundle();
                    intent.putExtras(bundle);
                    startActivityForResult(intent, INT_DATA_STATE);
                }
            });

            builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
        mVersions.setText(Tool.getVerName(getActivity()));
        //任务100 bug1201  判断是否已登录，若已登录则加载姓名和图片
        if (preferences.getBoolean("isLogin", false) && !TextUtils.isEmpty(preferences.getString("jsonData", ""))) {
            String json = preferences.getString("jsonData", "");
            RegisteredBean bean1 = ParsonJson.jsonToBeanObj(json,
                    RegisteredBean.class);
            AppContext.LOGIN = true;
            AppContext.LoginUserMessage.bean = bean1;
            AppContext.LoginUserMessage.messageUse = true;
            if (bean1 != null) {
                if (bean1.getFullName() != null && !bean1.getFullName().trim().equals("")) {
                    mLoginButton.setText(bean1.getFullName());
                } else {
                    mLoginButton.setText(bean1.getLoginName());
                }
                final String url = WPConfig.IMAGE_VIEW + bean1.getImageID();
                new HttpImage(this, url, null);
            }
        } else {
            mLoginButton.setText("登录");
            mTouxiang.setBackgroundResource(R.drawable.touxiang_2);
        }

        mLoginButton.setOnClickListener(this);
        mShareButton.setOnClickListener(this);
        mRemoveButton.setOnClickListener(this);

        mUpdateButton.setOnClickListener(this);
        mScoreButton.setOnClickListener(this);
        mFeedbackButton.setOnClickListener(this);

        mAboutButton.setOnClickListener(this);
        exit_button.setOnClickListener(this);
//        mPasswrodServer.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                if (AppContext.LoginUserMessage.messageUse) {
//                    toIntent(ForgetPassword.class);
//                } else {
//                    showToast("请登录后使用该项功能");
//                }
//            }
//        });
        getView().findViewById(R.id.return_button).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getActivity().finish();
            }
        });
    }

    private void toIntent(Class las) {
        Intent intent = new Intent(getActivity(), las);
        startActivity(intent);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.login_button:
                boolean isboo = ((TextView) arg0).getText().toString().trim().equals("登录");
                String str = ((TextView) arg0).getText().toString().trim();
                if (isboo) {
                    AppContext.LoginUserMessage.STATE = AppContext.LoginUserMessage.LOGIN_IN_TO;
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    Bundle bundle = new Bundle();
                    intent.putExtras(bundle);
                    startActivityForResult(intent, INT_DATA_STATE);

                } else {
                    Intent intent = new Intent(getActivity(), ModifyTheActivity.class);
                    Bundle bundle = new Bundle();
                    intent.putExtras(bundle);
                    startActivityForResult(intent, INT_DATA_STATE);
                }
                break;
            case R.id.share_button:
                new SharePurposed(getActivity(), 0);
                break;
            case R.id.remove_button:
                CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                builder.setMessage("\t\t确定清除缓存？");
                builder.setTitle("清除缓存");

                builder.setNegativeButton("确定", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DataCleanManager.clearAllCache(getActivity());
                        showToast("缓存已清理");
                        try {
                            huancun = DataCleanManager.getTotalCacheSize(getActivity());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        huc.setText(huancun);
                    }
                });

                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        // 设置你的操作事项
                    }
                });
                builder.create().show();

                break;
            case R.id.ban_ben_gen_xin:
                INT_DATA = INT_DATA_STATE_VA;

                HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.GET_VERSION).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        String jsons = "{\"Data\":" + json + "}";
                        if (!json.trim().equals("连接失败")) {
                            final VerNameBean beans = ParsonJson.jsonToBeanObj(jsons, VerNameBean.class);
                            String code = Tool.getVerCode(getActivity()) + "";
                            if (beans != null && beans.getData() != null && beans.getData().size() > 0) {
                                CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                                builder.setMessage("\t\t您需要更新么?");
                                builder.setTitle("温馨提示");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent();
                                        intent.setAction("android.intent.action.VIEW");
                                        Uri content_url = Uri.parse(beans.getData().get(0).getVersionFileUrl());
                                        intent.setData(content_url);
                                        startActivity(intent);
                                    }
                                });
                                builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.create().show();
                            } else {
                                CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                                builder.setMessage("\t\t您当前的版本是最新版本，不需要更新");
                                builder.setTitle("温馨提示");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        // 设置你的操作事项
                                    }
                                });
                                // builder.setNegativeButton(
                                // "取消",
                                // new android.content.DialogInterface.OnClickListener() {
                                // public void onClick(DialogInterface dialog,
                                // int which) {
                                // dialog.dismiss();
                                // }
                                // });
                                builder.create().show();
                            }
                        } else {//TiD:100 检查更新提示不完整
                            showToast("连接失败,稍后重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
                break;
            case R.id.feedback_button:
                toIntent(FeedbackActivity.class);
                break;
            case R.id.about_button:
                toIntent(AboutActivity.class);
                break;
            case R.id.exit_button:
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity()).setTitle("退出账户").setMessage("将要退出账户")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppContext.LOGIN = false;
                                AppContext.LoginUserMessage.bean = null;
                                AppContext.LoginUserMessage.messageUse = false;
                                SharedPreferences preferences = getActivity().getSharedPreferences("initialize",
                                        Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.remove("user");
                                editor.remove("password");
                                editor.commit();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();

                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                dialog.create();
                dialog.show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        Bundle bundle = data.getExtras();
        if (requestCode == INT_DATA_STATE && resultCode == Activity.RESULT_OK) {
            bean = (RegisteredBean) bundle.get("MessageData");
            if (bean != null) {
                if (bean.getFullName() != null && !bean.getFullName().trim().equals("")) {
                    mLoginButton.setText(bean.getFullName());
                } else {
                    mLoginButton.setText(bean.getLoginName());
                }
                final String url = WPConfig.IMAGE_VIEW + bean.getImageID();
                new HttpImage(this, url, null);
            } else {
                //任务100，bug1240 个人信息修改完成后，重新加载姓名和头像
                if (AppContext.LoginUserMessage.messageUse == true) {
                    String name = bundle.getString("name");
                    String fileUrl = bundle.getString("fileUrl");
                    if (name != null) {
                        mLoginButton.setText(name);
                    }
                    if (fileUrl != null) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        Bitmap mBitmap = BitmapFactory.decodeFile(fileUrl, options);
                        mTouxiang.setBackground(new BitmapDrawable(Tool.toRoundBitmap(mBitmap)));
                    }
                } else {
                    editor.putBoolean("isLogin", false);
                    editor.commit();
                    mLoginButton.setText("登录");
                    mTouxiang.setBackgroundResource(R.drawable.touxiang_2);
                }
//				mTouxiang.setBackground(getResources().getDrawable(R.drawable.touxiang_2));
            }
        }
    }

    @Override
    public void httpResponse(String json) {
        switch (INT_DATA) {
            case INT_DATA_STATE_VA:

                break;
        }

    }

    // 头像处理子线程
    private Runnable runnable2 = new Runnable() {

        @SuppressLint("NewApi")
        @Override
        public void run() {
            mTouxiang.setBackground(new BitmapDrawable(Tool.toRoundBitmap(bitmap)));
        }
    };

    // 头像处理handler；
    private Handler handler2 = new Handler();

    @SuppressLint("NewApi")
    @Override
    public void toBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        handler2.post(runnable2);

    }

}
