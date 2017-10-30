package com.heking.qsy.activity.Patrol;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.Patrol.PatrolDetailActivity.ContentType;
import com.heking.qsy.activity.review.SwipeRefreshView;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.snoa.WPConfig;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import MyUtils.LogUtils.LogUtils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PatrolActivity extends Activity implements OnClickListener {
    ImageButton ibtnAdd, ibtnR;
    FrameLayout flSelect;
    TextView tvSelectTitle;
    ImageView ivSelectArrows;
    SwipeRefreshView srl;
    ListView lv;

    Context mContext = this;
    int select = 0;
    String selectText = "全部巡检";
    PopupWindow popupWindow;
    int pageNum = 1;
    List<PatrolBean> patrolBeanList;
    PatrolAdapter patrolAdapter;
    Type t = Type.t;
    ProgressDialog progressDialog;
    String FirmID;
    private static final String TAG = "PatrolActivity";
    ArrayList<String> typeArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol);
        ibtnAdd = (ImageButton) findViewById(R.id.ibtnAdd);
        ibtnR = (ImageButton) findViewById(R.id.ibtnR);
        ibtnR.setOnClickListener(this);
        flSelect = (FrameLayout) findViewById(R.id.flSelect);
        tvSelectTitle = (TextView) findViewById(R.id.tvSelectTitle);
        ivSelectArrows = (ImageView) findViewById(R.id.ivSelectArrows);
        srl = (SwipeRefreshView) findViewById(R.id.srl);
        lv = (ListView) findViewById(R.id.bszn_list_view);
        ibtnAdd.setOnClickListener(this);
        flSelect.setOnClickListener(this);
        srl.setColorScheme(R.color.tou_su_button);
        //任务100  bug1190 初始化TypeArray
        typeArray.add(selectText);
        if (AppContext.LoginUserMessage.messageUse) {
            if (AppContext.LoginUserMessage.bean != null) {
                for (int i = 0; i < AppContext.LoginUserMessage.bean.getSystemMenus().size(); i++) {
                    String TypeName = AppContext.LoginUserMessage.bean.getSystemMenus().get(i).getName().trim();
                    if (TypeName.length() == 4) {
                        typeArray.add(TypeName);
                    }
                }
            }
        }
        tvSelectTitle.setText(selectText);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                if (t == Type.q) {
                    getData(pageNum, null, FirmID);
                } else {
                    getData(pageNum, typeArray.get(select), null);
                }

                srl.setRefreshing(false);
            }
        });
        // 设置下拉加载更多
        srl.setOnLoadListener(new SwipeRefreshView.OnLoadListener() {
            @Override
            public void onLoad() {
                srl.setLoading(false);
                pageNum++;
                if (t == Type.q) {
                    getData(pageNum, null, FirmID);
                } else {
                    getData(pageNum, typeArray.get(select), null);
                }

            }

        });

        patrolBeanList = new ArrayList<PatrolBean>();
        patrolAdapter = new PatrolAdapter(patrolBeanList);
        lv.setAdapter(patrolAdapter);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PatrolActivity.this, PatrolDetailActivity.class);
                intent.putExtra("contentType", ContentType.update);
                intent.putExtra("InspectRecoredID", patrolBeanList.get(position).getID() + "");
                startActivity(intent);
            }
        });

        if (getIntent().getSerializableExtra("type") != null) {
            t = (Type) getIntent().getSerializableExtra("type");
        }

        if (t == Type.q) {
            flSelect.setVisibility(View.GONE);
            ibtnAdd.setVisibility(View.GONE);
            FirmID = getIntent().getStringExtra("firmID");
            // getData(pageNum, null, FirmID);
        } else {
            // getData(pageNum, typeArray.get(select), null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNum = 1;
        if (t == Type.q) {
            getData(pageNum, null, FirmID);
        } else {
            getData(pageNum, typeArray.get(select), null);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnAdd:
                Intent intent = new Intent(mContext, AddPatrolActivity.class);
                intent.putExtra("datatype", tvSelectTitle.getText().toString());
                intent.putStringArrayListExtra("typeArray", typeArray);
                LogUtils.w(typeArray);
                startActivity(intent);
                break;
            case R.id.ibtnR:
                finish();
                break;
            case R.id.flSelect:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                }
                View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_enterprise_type_select, null);
                ListView lvSelect = (ListView) contentView.findViewById(R.id.lvSelect);

                final SelectAdapter selectAdapter = new SelectAdapter(typeArray);

                lvSelect.setAdapter(selectAdapter);
                lvSelect.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        pageNum = 1;
                        select = arg2;
                        getData(pageNum, typeArray.get(select), null);

                        tvSelectTitle.setText(typeArray.get(select));
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }

                });
                popupWindow = new PopupWindow(contentView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                popupWindow.showAsDropDown(flSelect);
                break;
            default:
                break;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    class PatrolAdapter extends BaseAdapter {
        ViewHolder viewHolder;
        List<PatrolBean> mValues;

        public PatrolAdapter(List<PatrolBean> mValues) {
            this.mValues = mValues;
        }

        @Override
        public int getCount() {
            return mValues.size();
            // return mValues.size();
        }

        @Override
        public Object getItem(int position) {

            return mValues.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_patrol, null);
                viewHolder = new ViewHolder();
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

                viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);

                viewHolder.tvCoordinators = (TextView) convertView.findViewById(R.id.tvCoordinators);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            PatrolBean patrolBean = mValues.get(position);
            viewHolder.tvTitle.setText(patrolBean.getFirmName());

            viewHolder.tvTime.setText(patrolBean.getInspectTime().substring(0, 10));

            viewHolder.tvCoordinators.setText(patrolBean.getInspectPeople());
            return convertView;
        }

        class ViewHolder {
            TextView tvTitle, tvTime, tvCoordinators;
        }

    }

    class SelectAdapter extends BaseAdapter {

        List<String> stringList;


        public SelectAdapter(List<String> typeArray) {
            this.stringList = typeArray;
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_enterprise_type_select, null);
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            tvTitle.setText(stringList.get(position));
            ImageView ivGou = (ImageView) convertView.findViewById(R.id.ivGou);
            if (select == position) {
                ivGou.setImageResource(R.drawable.marquee);
            } else {
                ivGou.setWillNotDraw(true);
            }
            return convertView;
        }
    }

    private void getData(int page, final String FirmTypeName, String FirmID) {
        LogUtils.w("xunjian", "页码:" + page, "全部巡检".equals(FirmTypeName) ? "" : FirmTypeName, FirmID);
        Map<String, String> params1 = new HashMap<>();
        params1.put("PageNumber", page + "");
        params1.put("FirmTypeName", "全部巡检".equals(FirmTypeName) ? "" : FirmTypeName);
        params1.put("FirmID", TextUtils.isEmpty(FirmID) ? "" : FirmID);

        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + "Inspection/GetInspectionRecords")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.w("http", "onSubscribe");
                    }

                    @Override
                    public void onSuccess(String response) {
                        LogUtils.w("xunjian", "succeedResponse:" + response);
                        if (!TextUtils.isEmpty(response)) {
                            LogUtils.w(response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 200) {
                                    JSONObject data = jsonObject.optJSONObject("data");
                                    if (data != null) {
                                        JSONArray data2 = data.optJSONArray("Data");
                                        if (pageNum == 1) {
                                            patrolBeanList.clear();
                                        }
                                        Gson gson = new GsonBuilder().serializeNulls().create();
                                        List<PatrolBean> patrolBeans = gson.fromJson(data2.toString(), new TypeToken<List<PatrolBean>>() {
                                        }.getType());
                                        Log.i(TAG, "onSuccess:我的类型是： " + new TypeToken<List<PatrolBean>>() {
                                        }.getType());
                                        //任务100 bug1190 根据选择类型 显示相应类型的企业
                                        if (FirmTypeName == null || FirmTypeName.equals(selectText)) {
                                            patrolBeanList.addAll(patrolBeans);
                                        } else {
                                            for (PatrolBean patrolBean : patrolBeans) {
                                                if (patrolBean.getFirmTypeName().equals(FirmTypeName)) {
                                                    patrolBeanList.add(patrolBean);
                                                }
                                            }
                                        }
                                        //patrolBeanList.addAll(patrolBeans);
                                        //List<PatrolBean>
                                        patrolAdapter.notifyDataSetChanged();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.w("http", "onError:"+e.getMessage());
                    }
                });
    }

    public enum Type {
        q,
        t
    }

    public void showProgress() {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = ProgressDialog.show(this, "提示", "正在加载中。。。");
        }
    }

    public void cancelProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
