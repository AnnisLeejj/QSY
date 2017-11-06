package com.heking.qsy.activity.Patrol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.FirmTypeBean.Data;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.MyTextUtils;
import com.heking.snoa.WPConfig;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import MyUtils.LogUtils.LogUtils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddPatrolActivity extends Activity implements OnClickListener {
    ImageButton ibtnR;
    Spinner spEnterpriseType, spCheckList;
    Button btnNext;
    EditText etHehe;
    EditText etXt;
    AutoCompleteTextView actvEnterpriseName;
    String enterpriseId;

    ActvEnterpriseNameAdapter enterpriseNameAdapter;//AtuoText
    ArrayList<FirmTypeBean.Data> firmTypeBean;

    InspectTableAdapter inspectTableAdapter;//table's
    List<InspectTableBean> inspectTableBeansAll, inspectTableBeanstemp;
    String datatype;
    ArrayList<String> typeArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patrol);
        datatype = getIntent().getStringExtra("datatype");
        if (getIntent().getStringArrayListExtra("typeArray") != null && getIntent().getStringArrayListExtra("typeArray").size() > 0) {
            typeArray = getIntent().getStringArrayListExtra("typeArray");
        }
        LogUtils.w("launchactivity", "AddPatrolActivity   type:" + datatype);
        Log.i("launchactivity", "AddPatrolActivity 长度为" + typeArray.size());
        ibtnR = (ImageButton) findViewById(R.id.ibtnR);
        spEnterpriseType = (Spinner) findViewById(R.id.spEnterpriseType);
        spCheckList = (Spinner) findViewById(R.id.spCheckList);
        actvEnterpriseName = (AutoCompleteTextView) findViewById(R.id.actvEnterpriseName);
        etHehe = (EditText) findViewById(R.id.etHehe);
        etXt = (EditText) findViewById(R.id.et_xt);
        btnNext = (Button) findViewById(R.id.btnNext);
        //任务100 bug1191 初始化企业类型的下拉菜单
        if (typeArray.contains("全部巡检")) {
            typeArray.remove("全部巡检");
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, typeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEnterpriseType.setAdapter(adapter);
        btnNext.setOnClickListener(this);
        ibtnR.setOnClickListener(this);

        if (AppContext.LoginUserMessage.bean != null) {
            etHehe.setText(TextUtils.isEmpty(AppContext.LoginUserMessage.bean.getFullName()) ? ""
                    : AppContext.LoginUserMessage.bean.getFullName());
        }
        firmTypeBean = new ArrayList<FirmTypeBean.Data>();
        inspectTableBeanstemp = new ArrayList<>();
        //TID:100 bug 1193 【巡检》巡检】添加巡检，在表单页面内，“单位名称”未根据企业类型进行筛选
        spEnterpriseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (AppContext.List.FirmTapeDataList != null) {
                    //输入框提示
                    if (enterpriseNameAdapter != null) {
                        firmTypeBean.clear();
                        firmTypeBean.addAll(getQiYe(AppContext.List.FirmTapeDataList));
                        enterpriseNameAdapter.notifyDataSetChanged();
                    }//表格选择
                    if (inspectTableAdapter != null) {
                        inspectTableBeansAll.clear();//inspectTableBeansAll
                        inspectTableBeansAll.addAll(getTable(inspectTableBeanstemp));
                        inspectTableAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setSpinnerItemSelectedByValue(spEnterpriseType, datatype);
        enterpriseNameAdapter = new ActvEnterpriseNameAdapter(firmTypeBean);
        actvEnterpriseName.setAdapter(enterpriseNameAdapter);
        actvEnterpriseName.setThreshold(1);
        actvEnterpriseName.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirmTypeBean.Data bean = (FirmTypeBean.Data) parent.getItemAtPosition(position);
                actvEnterpriseName.setText(bean.getFirmName());
                enterpriseId = bean.getFirmID();
            }
        });
        getData();
    }

    /**
     *  * 根据值, 设置spinner默认选中:
     *  * @param spinner
     *  * @param value
     *  
     *///TID:100 bug 1233
    public static void setSpinnerItemSelectedByValue(Spinner spinner, String value) {
        SpinnerAdapter apsAdapter = spinner.getAdapter();//得到SpinnerAdapter对象
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
            if (value.equals(apsAdapter.getItem(i).toString())) {
                spinner.setSelection(i, true);// 默认选中项
                break;
            }
        }
    }

    //TID:100 bug 1194 添加巡检记录时，巡检表单内“检查表”未根据“企业类型”筛选检查表
    private List<InspectTableBean> getTable(List<InspectTableBean> firmTapeDataList) {
        if (firmTapeDataList == null) return null;
        List<InspectTableBean> temp = new ArrayList<>();
        for (InspectTableBean item : firmTapeDataList) {
            String type = spEnterpriseType.getSelectedItem().toString();
            if (MyTextUtils.isContent(item.getFirmTypeName(), spEnterpriseType.getSelectedItem().toString())) {
                temp.add(item);
            }
        }
        LogUtils.w("add", "数据长度:" + temp.size() + "         源长:" + firmTapeDataList.size());
        return temp;
    }

    //TID:100 bug 1193 【巡检》巡检】添加巡检，在表单页面内，“单位名称”未根据企业类型进行筛选
    private List<Data> getQiYe(ArrayList<Data> firmTapeDataList) {
        if (firmTapeDataList == null) return null;
        List<Data> temp = new ArrayList<>();
        for (Data data : firmTapeDataList) {
            String type = spEnterpriseType.getSelectedItem().toString();
            if (MyTextUtils.isContent(new String[]{data.getFirmTypeName1(), data.getFirmTypeName()}, "企业")) {
                if (MyTextUtils.isContent(data.getFirmTypeName(), type)) {
                    temp.add(data);
                }
            }
        }
        LogUtils.w("add", "数据长度:" + temp.size());
        return temp;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnR:
                finish();
                break;
            case R.id.btnNext:
                String enterpriseType = (String) spEnterpriseType.getSelectedItem();
                String enterpriseName;
                String xieguan;
                String xieTongRen;
                int inspectTable = -1;

                if (!TextUtils.isEmpty(actvEnterpriseName.getText().toString())) {
                    enterpriseName = actvEnterpriseName.getText().toString();
                } else {
                    Toast.makeText(AddPatrolActivity.this, "请完整填写数据~", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(etHehe.getText().toString())) {
                    xieguan = etHehe.getText().toString();
                } else {
                    Toast.makeText(AddPatrolActivity.this, "请完整填写数据~", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断协同人员是否已填写
                if (!TextUtils.isEmpty(etXt.getText().toString())) {
                    xieTongRen = etXt.getText().toString();
                } else {
                    Toast.makeText(AddPatrolActivity.this, "请完整填写数据~", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (inspectTableBeansAll != null && inspectTableBeansAll.size() > 0) {
                    inspectTable = inspectTableBeansAll.get(spCheckList.getSelectedItemPosition()).getID();
                }

                if (inspectTable == -1) {
                    Toast.makeText(AddPatrolActivity.this, "请完整填写数据~", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(this, PatrolDetailActivity.class);
                intent.putExtra("enterpriseType", enterpriseType);
                intent.putExtra("xieguan", xieguan);
                intent.putExtra("xieTongRen", xieTongRen);
                intent.putExtra("inspectTable", inspectTable + "");
                intent.putExtra("enterpriseName", enterpriseName);
                intent.putExtra("enterpriseId", enterpriseId);

                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    class ActvEnterpriseNameAdapter extends BaseAdapter implements Filterable {
        ArrayList<FirmTypeBean.Data> firmTypeBean;
        ArrayList<FirmTypeBean.Data> originalFirmTypeBean;
        private ArrayFilter arrayFilter;

        public ActvEnterpriseNameAdapter(ArrayList<Data> firmTypeBean) {
            super();
            this.firmTypeBean = firmTypeBean;
            originalFirmTypeBean = firmTypeBean;
        }

        @Override
        public int getCount() {
            //TID:100   bug:1193
            return firmTypeBean == null ? 0 : firmTypeBean.size();
        }

        @Override
        public Object getItem(int position) {
            return firmTypeBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(parent.getContext());
            txt.setTextSize(20);
            txt.setBackgroundResource(R.color.white);
            txt.setPadding(10, 20, 10, 20);
            txt.setText(firmTypeBean.get(position).getFirmName());
            convertView = txt;
            return convertView;
        }

        @Override
        public Filter getFilter() {
            if (arrayFilter == null) {
                arrayFilter = new ArrayFilter();
            }
            return arrayFilter;
        }

        private class ArrayFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                FilterResults filterResults = new FilterResults();

                if (charSequence != null && charSequence.length() > 0) {

                    Iterator<FirmTypeBean.Data> iterator = originalFirmTypeBean.iterator();
                    ArrayList<FirmTypeBean.Data> firmTypeBean2 = new ArrayList<FirmTypeBean.Data>();
                    while (iterator.hasNext()) {
                        FirmTypeBean.Data bean = iterator.next();

                        if (TextUtils.isEmpty(bean.getFirmName())) {
                            System.out.println("---> id=" + bean.getFirmID());
                            continue;
                        }
                        System.out.println("---> name=" + bean.getFirmName());

                        if (bean.getFirmName().contains(charSequence.toString())) {
                            System.out.println("---> name=" + bean.getFirmName());
                            firmTypeBean2.add(bean);
                        }
                    }
                    filterResults.values = firmTypeBean2;
                    filterResults.count = firmTypeBean2.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence arg0, FilterResults results) {
                firmTypeBean = (ArrayList<FirmTypeBean.Data>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        }
    }

    private void getData() {
        HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + "Inspection/GetInspectTables").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String value) {
                if (!TextUtils.isEmpty(value)) {
                    try {
                        JSONObject jsonObject = new JSONObject(value);
                        int code = jsonObject.getInt("code");
                        if (code == 200) {
                            JSONObject data = jsonObject.optJSONObject("data");
                            if (data != null) {
                                JSONArray InspectTables = data.optJSONArray("InspectTables");
                                if (InspectTables != null && InspectTables.length() > 0) {
                                    Gson gson = new GsonBuilder().serializeNulls().create();
                                    inspectTableBeansAll = gson.fromJson(InspectTables.toString(),
                                            new TypeToken<List<InspectTableBean>>() {
                                            }.getType());
                                    inspectTableBeanstemp = new ArrayList<>();//克隆
                                    for (InspectTableBean item : inspectTableBeansAll) {
                                        inspectTableBeanstemp.add(item);
                                    }
                                    Iterator<InspectTableBean> InspectTableBeanItr = inspectTableBeansAll
                                            .iterator();
                                    while (InspectTableBeanItr.hasNext()) {
                                        InspectTableBean inspectTableBean = (InspectTableBean) InspectTableBeanItr
                                                .next();
                                        if (inspectTableBean.isDeleted())
                                            InspectTableBeanItr.remove();
                                    }
                                    inspectTableAdapter = new InspectTableAdapter(inspectTableBeansAll);
                                    spCheckList.setAdapter(inspectTableAdapter);
                                }

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    class InspectTableAdapter extends BaseAdapter {
        List<InspectTableBean> InspectTableBeans;

        public InspectTableAdapter(List<InspectTableBean> inspectTableBeans) {
            super();
            InspectTableBeans = inspectTableBeans;
        }

        @Override
        public int getCount() {
            return InspectTableBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return InspectTableBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(parent.getContext());
            txt.setTextSize(20);

            // LayoutParams l= txt.getLayoutParams();
            txt.setPadding(10, 5, 10, 5);
            txt.setText(InspectTableBeans.get(position).getTableName());
            convertView = txt;
            return convertView;
        }
    }
}
