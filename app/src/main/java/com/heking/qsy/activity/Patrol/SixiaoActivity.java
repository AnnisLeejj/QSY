package com.heking.qsy.activity.Patrol;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.FirmShow.FirmAdapter;
import com.heking.qsy.base.BaseActivity;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.JsonFile;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 四小
 *
 * @author Administrator
 */
public class SixiaoActivity extends BaseActivity implements OnClickListener {
    ImageButton ibtnR;
    FrameLayout flSelectAddr, flSelectType;
    TextView tvSelectAddrTitle, tvSelectTypeTitle;
    SwipeRefreshLayout srl;
    ListView listView;
    public ArrayList<FirmTypeBean.Data> FirmTapeDataList;
    FirmAdapter firmAdapter;
    PopupWindow popupWindow;
    Context mContext = this;
    int selectAddr = 0;
    int selectFirmType = 0;
    String firmType = "全部企业";// 企业类型
    String address = "全部区域";

    int sType = -1;
    private String[] addrStr = {"全部区域", "玉津镇", "清溪镇", "罗城镇", "芭沟镇", "石溪镇", "新民镇", "孝姑镇", "龙孔镇", "定文镇", "敖家镇", "金石井镇",
            "泉水镇", "双溪乡", "九井乡", "同兴乡", "榨鼓乡", "铁炉乡", "大兴乡", "南阳乡", "纪家乡", "新盛乡", "寿保乡", "舞雩乡", "下渡乡", "玉屏乡", "岷东乡",
            "塘坝乡", "马庙乡", "公平乡", "伏龙乡"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixiao);
        ibtnR = (ImageButton) findViewById(R.id.ibtnR);
        ibtnR.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        flSelectAddr = (FrameLayout) findViewById(R.id.flSelectAddr);
        flSelectType = (FrameLayout) findViewById(R.id.flSelectType);
        tvSelectAddrTitle = (TextView) findViewById(R.id.tvSelectAddrTitle);
        tvSelectTypeTitle = (TextView) findViewById(R.id.tvSelectTypeTitle);
        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        listView = (ListView) findViewById(R.id.listView);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        FirmTapeDataList = new ArrayList<FirmTypeBean.Data>();
        firmAdapter = new FirmAdapter(FirmTapeDataList, SixiaoActivity.this, 2222);
        listView.setAdapter(firmAdapter);
        if (AppContext.List.FirmTapeDataList != null) {
            FirmTapeDataList.addAll(getData(AppContext.List.FirmTapeDataList));
            firmAdapter.notifyDataSetChanged();
        } else {
            getData();
        }
        tvSelectAddrTitle.setText(address);
        tvSelectTypeTitle.setText(firmType);
        flSelectAddr.setOnClickListener(this);
        flSelectType.setOnClickListener(this);
    }

    private void getData() {
        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.GET_FIRM_TYPE + AppContext.Parameter.ALL).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String json) {
                srl.setRefreshing(false);
                String JsonData = json;
                JsonData = "{\"Data\":" + json + "}";
                JsonFile jsonFile = new JsonFile();
                File file;
                File file2 = null;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    file = Environment.getExternalStorageDirectory();
                    file2 = new File(file + "/PZhMessageList/");
                    if (!file2.exists()) {
                        file2.mkdirs();
                    }
                }
                File ss = new File(file2 + "/FirmTypeBean.dll");

                if (JsonData.equals("连接失败")) {
                    try {
                        JsonData = new String(jsonFile.getFlieJson(ss));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                    try {
                        jsonFile.FileJson(JsonData, ss);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (JsonData.equals("连接失败")) {
                    showToast("网络异常");
                    return;
                }
                FirmTypeBean bean = ParsonJson.jsonToBeanObj(JsonData, FirmTypeBean.class);
                if (bean != null && bean.getData() != null) {
                    AppContext.List.FirmTapeDataList = bean.getData();
                    FirmTapeDataList.clear();
                    FirmTapeDataList.addAll(getData(AppContext.List.FirmTapeDataList));
                    updateList();
                } else {
                    showToast("网络异常");
                }
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }

    // 列表选择过滤
    private void updateList() {
        if (AppContext.List.FirmTapeDataList == null || AppContext.List.FirmTapeDataList.size() <= 0) {
            return;
        }
        if (FirmTapeDataList != null) {
            FirmTapeDataList.clear();
            FirmTapeDataList.addAll(getData(AppContext.List.FirmTapeDataList));
            Iterator<FirmTypeBean.Data> iter = FirmTapeDataList.iterator();
            while (iter.hasNext()) {
                FirmTypeBean.Data data = iter.next();
                boolean bFirmType = firmType.contains("全部企业") || data.getFirmTypeName().contains(firmType)
                        || data.getFirmTypeName1().contains(firmType) ? true : false;
                if (data.getAreaName() != null) {
                    boolean bAddress = address.contains("全部区域") || data.getAreaName().contains(address) ? true : false;
                    if (!bFirmType || !bAddress) {
                        iter.remove();
                    }
                }
            }
            firmAdapter.notifyDataSetChanged();
        }
    }

    private ArrayList<FirmTypeBean.Data> getData(ArrayList<FirmTypeBean.Data> datalist) {

        ArrayList<FirmTypeBean.Data> datas = new ArrayList<FirmTypeBean.Data>();
        for (FirmTypeBean.Data data : datalist) {
            if (data.getFirmTypeName1().trim().contains("加工小作坊") || data.getFirmTypeName1().trim().contains("小食品摊贩")
                    || data.getFirmTypeName1().trim().contains("小餐饮")
                    || data.getFirmTypeName1().trim().contains("小食品店")) {

                if (data.getMonitors() != null && data.getMonitors().size() > 0) {
                    data.setIoos(true);
                } else {
                    data.setIoos(false);
                }

                if (data.getAnnualRating() != null && data.getAnnualRating().size() > 1) {
                    if (data.getAnnualRating().get(0).getRating() != null
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
                datas.add(data);
            }
        }
        return Sort(datas);
    }

    // 将评分最高的展示在最前
    private ArrayList<FirmTypeBean.Data> Sort(ArrayList<FirmTypeBean.Data> datalist) {
        for (int i = 0; i < datalist.size(); i++) {
            for (int j = i; j < datalist.size(); j++) {
                FirmTypeBean.Data data1 = datalist.get(i);
                FirmTypeBean.Data data2 = datalist.get(j);
                if (data1.getmRating() > data2.getmRating()) {
                    datalist.set(i, data2);
                    datalist.set(j, data1);
                }
            }
        }
        return datalist;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flSelectAddr:
                sType = 1;
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                }
                View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_enterprise_type_select, null);
                ListView lvSelect = (ListView) contentView.findViewById(R.id.lvSelect);
                final SelectAdapter selectAdapter = new SelectAdapter(addrStr);
                lvSelect.setAdapter(selectAdapter);
                lvSelect.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        address = addrStr[arg2];
                        selectAddr = arg2;
                        selectAdapter.notifyDataSetChanged();
                        tvSelectAddrTitle.setText(addrStr[arg2]);
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }

                        updateList();

                    }

                });
                popupWindow = new PopupWindow(contentView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                popupWindow.showAsDropDown(flSelectAddr);
                break;
            case R.id.flSelectType:
                sType = 2;
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                }
                View contentViewFirmType = LayoutInflater.from(mContext).inflate(R.layout.layout_enterprise_type_select,
                        null);
                ListView lvSelectFirmType = (ListView) contentViewFirmType.findViewById(R.id.lvSelect);
                final SelectAdapter selectFirmTypeAdapter = new SelectAdapter(
                        getResources().getStringArray(R.array.xiao_zuo));
                lvSelectFirmType.setAdapter(selectFirmTypeAdapter);
                lvSelectFirmType.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        firmType = getResources().getStringArray(R.array.xiao_zuo)[arg2];
                        selectFirmType = arg2;
                        selectFirmTypeAdapter.notifyDataSetChanged();
                        tvSelectTypeTitle.setText(getResources().getStringArray(R.array.xiao_zuo)[arg2]);
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        updateList();
                    }

                });
                popupWindow = new PopupWindow(contentViewFirmType, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                popupWindow.showAsDropDown(flSelectType);
                break;
            default:
                break;
        }
    }

    class SelectAdapter extends BaseAdapter {
        String[] mValues;

        public SelectAdapter(String[] mValues) {
            this.mValues = mValues;
        }
        @Override
        public int getCount() {
            return mValues.length;
        }
        @Override
        public Object getItem(int position) {
            return mValues[position];
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_enterprise_type_select, null);
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            tvTitle.setText(mValues[position]);
            ImageView ivGou = (ImageView) convertView.findViewById(R.id.ivGou);
            if (sType == 1) {
                if (selectAddr == position) {
                    ivGou.setImageResource(R.drawable.marquee);
                } else {
                    ivGou.setWillNotDraw(true);
                }
            } else if (sType == 2) {
                if (selectFirmType == position) {
                    ivGou.setImageResource(R.drawable.marquee);
                } else {
                    ivGou.setWillNotDraw(true);
                }
            }
            return convertView;
        }
    }
}
