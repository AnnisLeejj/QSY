package com.heking.qsy.activity;

import java.util.ArrayList;

import com.heking.qsy.AppContext;
import com.heking.qsy.Dialog.CustomDialog;
import com.heking.qsy.R;
import com.heking.qsy.activity.ConvenienceService.util.ZhouBianYaodian;
import com.heking.qsy.activity.Personalcenters.LoginActivity;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.base.BaseActivity;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.DrugAdapter;
import com.heking.qsy.util.DrugBean;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.MessageSearch;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.SaoYiSaoXmlBean;
import com.heking.qsy.util.Tool;
import com.heking.qsy.util.getXml;
import com.heking.qsy.yuying.YUMessageData;
import com.heking.qsy.yuying.YYshow;
import com.heking.snoa.WPConfig;
import com.zbar.lib.CaptureActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;

import MyUtils.LogUtils.LogUtils;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SweepTheYardActivity extends BaseActivity implements OnClickListener, YUMessageData {
    private Bundle bundle;
    private EditText mEditText;
    private String mCode;
    private TextView mSearch;
    private ArrayList<MessageSearch> list;
    private WaitDialog dialog;
    private TextView mWarning,
            mProductName,
            mProductManufacturerName,
            mApprovalWenHao,
            mProductionDate,
            mProductionPiCi,
            mYouXiaoDate,
            mBaoZhuanGuiGe,
            mJiXing,
            mDrugCategory,
            mZhijiguige;
    private ImageView mSaoyisao;
    private LinearLayout relativeLayout;
    private LinearLayout linearLayout;

    private TextView mYaoPinQy, mYaoPinLX;
    private Bundle bundledata;
    private TextView mYaoPinXiangQing;
    private TextView mCountryButton;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.w("startactivity", "SweepTheYardActivity");
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        bundle = getIntent().getExtras();
        if (bundle != null) {
            mCode = bundle.getString("message");
        }
        setContentView(R.layout.monitor_code);
        iniView();
        iniData();

        if (mCode != null) {
            if (mCode.length() == 20) {
                HttpHelper.getInstance().service.get(WPConfig.SAO_YI_SAO_YAO_PING + mCode).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(singleObserver);
            } else {
                relativeLayout.setVisibility(RelativeLayout.GONE);
                listView.setVisibility(ListView.VISIBLE);
                HttpHelper.getInstance().service.get("http://117.173.38.55:84/YZTQW/SJSPZHAPI/api/Medicine/GetMedicintBarCode/" + mCode).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        String JsonData = "{\"Data\":" + json + "}";
                        DrugBean bean = ParsonJson.jsonToBean(JsonData, DrugBean.class);
                        if (bean != null && bean.getData().size() > 0) {
                            DrugAdapter adapter = new DrugAdapter(SweepTheYardActivity.this, bean.getData());
                            listView.setAdapter(adapter);
                        } else {
                            searchNothingToHint();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
            }
        }
    }

    private void iniView() {
        bundledata = new Bundle();
        bundledata.putString("mcode", mCode);
        Tool.endActivity(this);

        listView = (ListView) findViewById(R.id.list_drug);

        mEditText = (EditText) findViewById(R.id.monitor_searchtextbox);
        mCountryButton = (TextView) findViewById(R.id.country_button);
        mSearch = (TextView) findViewById(R.id.btn_search_barnum);


        relativeLayout = (LinearLayout) findViewById(R.id.layout_guide);
        linearLayout = (LinearLayout) findViewById(R.id.layout_guide_2);

        mWarning = (TextView) findViewById(R.id.jing_gao_xin_xi);
        mProductName = (TextView) findViewById(R.id.yao_ping_name);
        mProductManufacturerName = (TextView) findViewById(R.id.yao_ping_chan_jia);
        mApprovalWenHao = (TextView) findViewById(R.id.approvalwenHao);

        mProductionDate = (TextView) findViewById(R.id.productiondate);

        mProductionPiCi = (TextView) findViewById(R.id.productionpici);
        mYouXiaoDate = (TextView) findViewById(R.id.youxiaodate);
        mBaoZhuanGuiGe = (TextView) findViewById(R.id.baozhuanguige);
        mJiXing = (TextView) findViewById(R.id.jixing);
        mZhijiguige = (TextView) findViewById(R.id.zijiguige);

        mSaoyisao = (ImageView) findViewById(R.id.btn_scan_barnum);

        mYaoPinQy = (TextView) findViewById(R.id.zhou_bian_yao_dian);
        mYaoPinLX = (TextView) findViewById(R.id.yao_ping_liu_xiang);
        mYaoPinXiangQing = (TextView) findViewById(R.id.yao_pin_ji_ben_xin_xi);
        mDrugCategory = (TextView) findViewById(R.id.mDrugCategory);
        new YYshow(this, this, this);
        dialog = new WaitDialog(this);
        dialog.setContent("正在加载...");
    }

    private boolean boo = false;

    private void iniData() {
        if (mCode != null) {
            mEditText.setText(mCode);
        }
        mSearch.setOnClickListener(this);
        mSaoyisao.setOnClickListener(this);
        mYaoPinQy.setOnClickListener(this);
        mYaoPinLX.setOnClickListener(this);
        mYaoPinXiangQing.setOnClickListener(this);

        if (!AppContext.THEME) {
            //mCountryButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hong_shang));
        }

        mCountryButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (boo) {

                    if (AppContext.THEME) {

                        mCountryButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.shang));

                    } else {

                        //mCountryButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hong_shang));
                    }

                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                    boo = false;
                } else {
                    boo = true;

                    pupiwindow();

                    if (AppContext.THEME) {
                        mCountryButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.xia));
                    } else {

                        //mCountryButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hong_xia));

                    }
                }
            }
        });
    }

    private PopupWindow popupWindow;
    private View mView;
    private TextView mButton_1;
    private TextView mButton_2;
    private boolean isboo = true;

    private void pupiwindow() {
        if (popupWindow == null) {
            mView = LayoutInflater.from(this).inflate(R.layout.popupwindow_on_01, null);
            mButton_1 = (TextView) mView.findViewById(R.id.guo_jia);
            mButton_2 = (TextView) mView.findViewById(R.id.ben_di);
            mButton_2.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    if (popupWindow != null) {
                        isboo = false;
                        popupWindow.dismiss();
                    }
                }
            });
            mButton_1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (popupWindow != null) {
                        isboo = true;
                        popupWindow.dismiss();
                    }
                }
            });
            popupWindow = new PopupWindow(mView, LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            // 设置一个空白的背景
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            // 点击屏幕可消失
            popupWindow.setOutsideTouchable(true);
            // 获得焦点
            popupWindow.setFocusable(false);
            //设置pop消失的监听事件
            popupWindow.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //设置按钮可以点击
//						mText.setEnabled(true);
                            if (AppContext.THEME) {
                                mCountryButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.shang));
                            } else {
                                //mCountryButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hong_shang));
                            }
                            boo = false;
                        }
                    }, 200);
                }
            });
        }
        popupWindow.showAsDropDown(findViewById(R.id.search_input_layout));
    /*(findViewById(R.id.search_input_layout),0,0);*/
    }

    private Handler mHandler = new Handler();

    private void setViewText() {
        String messagesQuerycounstring_01 = "　该监管码已被多次查询，请注意检查药品包装是否与信息符合，首次查询时间是：";
        for (MessageSearch MessageSearch : list) {
            if (MessageSearch.getKey().equals("queryCount")) {
                if (new Integer(MessageSearch.getValue()) < 20) {
                    messagesQuerycounstring_01 = "该监管码已被查询" + MessageSearch.getValue() + "次，请注意检查药品包装是否与信息符合，首次查询时间是：";
                }
            }
            if (MessageSearch.getKey().equals("message")) {
                if (MessageSearch.getValue().toString().equals("监管码查询失败")) {
                    Toast.makeText(this, "请输入正确的药品电子监管码", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            relativeLayout.setVisibility(RelativeLayout.GONE);
            linearLayout.setVisibility(LinearLayout.VISIBLE);

            if (MessageSearch.getKey().equals("firstQuery")) {
                mWarning.setText(messagesQuerycounstring_01.toString() + MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("产品名称")) {
                mProductName.setText(MessageSearch.getValue().toString());
                bundledata.putString("Name", mProductName.getText().toString().trim());
            }
            if (MessageSearch.getKey().equals("生产企业")) {
                mProductManufacturerName.setText(MessageSearch.getValue().toString());
                bundledata.putString("QY", MessageSearch.getValue());
            }
            if (MessageSearch.getKey().equals("批准文号")) {
                mApprovalWenHao.setText(MessageSearch.getValue().toString());
                bundledata.putString("pzwh", mApprovalWenHao.getText().toString().trim());
            }
            if (MessageSearch.getKey().equals("生产日期")) {
                mProductionDate.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("生产批次")) {
                bundledata.putString("scpc", MessageSearch.getValue().toString());
                mProductionPiCi.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("有效期至")) {//预设格式"　20121212",括号内
                String str = MessageSearch.getValue().toString().trim().replace("　", "");
                LogUtils.w("youxiaoqi", str + "-length:" + str.length());
                if (str.length() == 8) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("　");
                    sb.append(str.substring(0, 4));
                    sb.append("年");
                    sb.append(str.substring(4, 6));
                    sb.append("月");
                    sb.append(str.substring(6, 8));
                    sb.append("日");
                    mYouXiaoDate.setText(sb);
                } else {
                    mYouXiaoDate.setText(str);
                }
            }
            if (MessageSearch.getKey().equals("包装规格")) {
                mBaoZhuanGuiGe.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("剂型代码")) {
                mJiXing.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("制剂规格")) {
                mZhijiguige.setText(MessageSearch.getValue().toString());
            }
            if (MessageSearch.getKey().equals("药品当前的状态信息")) {
                bundledata.putString("LiuXiang", MessageSearch.getValue());
            }
            if (MessageSearch.getKey().equals("药品类别")) {
                mDrugCategory.setText(MessageSearch.getValue().toString());
            }
        }
    }

    private ArrayList<DrugBean.Data> datas = new ArrayList<DrugBean.Data>();
    private DrugAdapter adapter;
    SingleObserver singleObserver = new SingleObserver<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(String json) {
            if (json.equals("连接失败")) {
                showToast(json);
                finish();
                return;
            }
            SaoYiSaoXmlBean bean = ParsonJson.jsonToBeanObj(json, SaoYiSaoXmlBean.class);
            list = getXml.parseExternXML(bean.getData());
            setViewText();
            dialog.dismiss();
        }

        @Override
        public void onError(Throwable e) {
            dialog.dismiss();
        }
    };

    @Override
    public void onClick(View arg0) {

        switch (arg0.getId()) {
            case R.id.btn_search_barnum:
                dialog.show();
                if (isboo) {
                    HttpHelper.getInstance().service.get(WPConfig.SAO_YI_SAO_YAO_PING + mEditText.getText().toString().trim()).
                            subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(singleObserver);
                } else {

                    relativeLayout.setVisibility(RelativeLayout.GONE);
                    listView.setVisibility(ListView.VISIBLE);
                    if (mEditText.getText().toString().trim().equals("")) {
                        Toast.makeText(SweepTheYardActivity.this, "请输入需要查询的数据", Toast.LENGTH_SHORT).show();
                    }


                    Tool.toHttpGEtandPost("http://117.173.38.55:84/YZTQW/SJSPZHAPI/api/Medicine/GetMedicintBarCode/" + mCode, "GET", null, new JSONdata() {

                        @Override
                        public void httpResponse(String json) {
//                            String JsonData = "{\"Data\":" + json + "}";
//                            DrugBean bean = ParsonJson.jsonToBean(JsonData, DrugBean.class);
//                            if (bean != null && bean.getData().size() > 0) {
//                                DrugAdapter adapter = new DrugAdapter(SweepTheYardActivity.this, bean.getData());
//                                listView.setAdapter(adapter);
//                            } else {
//                                searchNothingToHint();
//                            }


                            dialog.dismiss();
                            String JsonData = "{\"Data\":" + json + "}";
                            DrugBean bean = ParsonJson.jsonToBean(JsonData, DrugBean.class);
                            if (bean != null && bean.getData().size() > 0) {
                                datas.clear();
                                for (DrugBean.Data ee : bean.getData()) {
                                    datas.add(ee);
                                }
                                if (adapter == null) {
                                    adapter = new DrugAdapter(SweepTheYardActivity.this, datas);
                                    listView.setAdapter(adapter);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }
                            } else {
                                datas.clear();
                                Toast.makeText(SweepTheYardActivity.this, "暂无该条数据记录", Toast.LENGTH_SHORT).show();
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                }
                            }

                        }
                    }, null);


//                    HttpHelper.getInstance().service.get("http://117.173.38.55:84/YZTQW/SJSPZHAPI/api/Medicine/GetMedicintBarCode/" + mEditText.getText().toString().trim()).
//                            subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onSuccess(String json) {
//                            dialog.dismiss();
//                            String JsonData = "{\"Data\":" + json + "}";
//                            DrugBean bean = ParsonJson.jsonToBean(JsonData, DrugBean.class);
//                            if (bean != null && bean.getData().size() > 0) {
//                                datas.clear();
//                                for (DrugBean.Data ee : bean.getData()) {
//                                    datas.add(ee);
//                                }
//                                if (adapter == null) {
//                                    adapter = new DrugAdapter(SweepTheYardActivity.this, datas);
//                                    listView.setAdapter(adapter);
//                                } else {
//                                    adapter.notifyDataSetChanged();
//                                }
//                            } else {
//                                datas.clear();
//                                Toast.makeText(SweepTheYardActivity.this, "暂无该条数据记录", Toast.LENGTH_SHORT).show();
//                                if (adapter != null) {
//                                    adapter.notifyDataSetChanged();
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            dialog.dismiss();
//                        }
//                    });

                }

                break;
            case R.id.btn_scan_barnum:
                toIntext(CaptureActivity.class);
                break;
            case R.id.zhou_bian_yao_dian:
                toIntext(ZhouBianYaodian.class);
                break;
            case R.id.yao_ping_liu_xiang:
                toIntext(FlowDirection.class, bundledata);
                break;
            case R.id.yao_pin_ji_ben_xin_xi:
                Log.d("测试数据", "---------------------------------------");
                toIntext(DrugInformationActivity.class, bundledata);
                break;
        }

    }

    private void toIntext(Class cla) {
        Intent intent = new Intent(this, cla);
        startActivity(intent);
    }

    private void toIntext(Class cla, Bundle bundle) {
        Intent intent = new Intent(this, cla);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onYYdata(String str) {
        if (!str.equals("。")) {
            String sc = mEditText.getText().toString().trim();
            mEditText.setText(sc + str);
        }
    }

    private void searchNothingToHint() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("该条形码暂无数据");
        builder.setTitle("温馨提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.create().show();
    }
}
