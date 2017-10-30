package com.heking.qsy.activity.ConvenienceService;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.ConvenienceService.util.MonitoringAdapter;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.Tool;

import com.heking.qsy.base.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import MyUtils.LogUtils.LogUtils;

public class SCXCActivity extends BaseActivity {
    private ListView mListView;
    private ArrayList<FirmTypeBean.Data> FirmTapeDataList;
    private ArrayList<FirmTypeBean.Data> firmlist = new ArrayList<FirmTypeBean.Data>();
    private MonitoringAdapter adapter;
    private EditText mEditText;
    private TextView mTextView;

    private TimerTask mSeekTask;
    private Timer mSeekTimer;
    private String mSeekString = "";
    private boolean ioos = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.scxc_activity);
        iniView();
        iniData();
    }

    private void iniView() {
//        setTitle("药品生产", true);
        Tool.endActivity(this);
        Tool.changetitle(this, "药品生产");
        mListView = (ListView) findViewById(R.id.scxc_list_view);
        mEditText = (EditText) findViewById(R.id.shou_shuo);
        mTextView = (TextView) findViewById(R.id.textView_remove);

        FirmTapeDataList = AppContext.List.FirmTapeDataList;
        listData();
        adapter = new MonitoringAdapter(firmlist, this, 3333);
    }

    private void iniData() {
        mListView.setAdapter(adapter);
        mTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                boolean boo = firmlist != null && firmlist.size() > 0 && !mEditText.getText().toString().trim().equals("") ? true : false;
                if (ioos && boo) {
                    mTextView.setText("清除");
                    String strname = mEditText.getText().toString().trim();
                    for (int i = 0; i < firmlist.size(); i++) {
                        if (!firmlist.get(i).getFirmName().contains(strname)) {
                            firmlist.remove(i);
                            i--;
                        }
                    }
                    ioos = false;
                    if (firmlist.size() == 0) {
                        findViewById(R.id.null_data).setVisibility(TextView.VISIBLE);
                    } else {

                        findViewById(R.id.null_data).setVisibility(TextView.GONE);
                    }
                    adapter.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                } else {
                    ioos = true;
                    mEditText.setText("");
                    mTextView.setText("搜索");
                    firmlist.clear();
                    FirmTapeDataList = AppContext.List.FirmTapeDataList;
                    listData();
                    findViewById(R.id.null_data).setVisibility(TextView.GONE);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void listData() {
        if (FirmTapeDataList != null) {
            for (FirmTypeBean.Data data : FirmTapeDataList) {
                if (data.getFirmTypeName().contains("药品生产")) {
                    if (data.getMonitors() != null && data.getMonitors().size() > 0) {
                        data.setIoos(true);
                    } else {
                        data.setIoos(false);
                    }
                    if (data.getAnnualRating() != null && data.getAnnualRating().size() > 0) {
                        if (data.getAnnualRating().get(0) != null && data.getAnnualRating().get(0) != null && data.getAnnualRating().get(0).getRating() != null
                                && !data.getAnnualRating().get(0).getRating().trim().equals("")) {
                            if (data.getAnnualRating().get(0).getRating().trim().equals("优秀")) {
                                data.setmRating(1);
                            }
                            if (data.getAnnualRating().get(0).getRating().trim().equals("良好")) {
                                data.setmRating(2);
                            }
                            if (data.getAnnualRating().get(0).getRating().trim().equals("一般")) {
                                data.setmRating(3);
                            }
                            if (data.getAnnualRating().get(0).getRating().trim().equals("不予评级")) {
                                data.setmRating(4);
                            }
                        }

                    } else {
                        data.setmRating(4);
                    }
                    firmlist.add(data);
                }
                LogUtils.w("data", "数据长度:" + firmlist.size());
            }
            //TID:100 bug :1231   【便民服务】“药品生产”“药品流通”“医疗机构”获取的数据与【药品企业】中获取的数据不一致。
            //没有监控的不显示
            for (int i = 0; i < firmlist.size(); i++) {
                if (!firmlist.get(i).isIoos()) {
                    firmlist.remove(i);
                    i--;
                }
            }
            LogUtils.w("data", "数据长度:" + firmlist.size());
        }
    }

    private HandelMess handel = new HandelMess();

    private void toRunnable() {
        mSeekTask = new TimerTask() {
            @Override
            public void run() {

                String str = mEditText.getText().toString().trim();
                if (mSeekString.equals(str)) {

                } else {
                    mSeekString = str;
                    Log.d("测试数据", str);
                    Bundle bundle = new Bundle();
                    Message mMessage = handel.obtainMessage();
                    bundle.putString("data", str);
                    mMessage.setData(bundle);
                    handel.sendMessage(mMessage);
                }
            }
        };
        mSeekTimer = new Timer();
        mSeekTimer.schedule(mSeekTask, 0, 100);


    }

    private class HandelMess extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            mTextView.setText(msg.getData().getString("data"));
        }


    }
}
