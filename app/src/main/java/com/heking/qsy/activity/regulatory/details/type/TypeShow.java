package com.heking.qsy.activity.regulatory.details.type;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.JsongDataBean;
import com.heking.qsy.activity.regulatory.tab.TB_BaoShunBaoYin;
import com.heking.qsy.activity.regulatory.tab.TB_BuLiangFanyingJianCe;
import com.heking.qsy.activity.regulatory.tab.TB_Chunfangjilu;
import com.heking.qsy.activity.regulatory.tab.TB_WenDuShiDuJianCe;
import com.heking.qsy.activity.regulatory.tab.TB_YaoXieYangHu;
import com.heking.qsy.activity.regulatory.tab.TB_ZhiLiangYanShou;
import com.heking.qsy.activity.regulatory.tab.TB_YaoXieYangHu.data;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.base.BaseActivity;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TypeShow extends BaseActivity {
    private WaitDialog dialog;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.type_show);

        iniView();
        iniData();
    }

    private int state;

    private void iniView() {
        mLayout = (LinearLayout) findViewById(R.id.jibenlayout);
        state = getIntent().getExtras().getInt("state");

    }

    private void iniData() {

        switch (state) {
            case 99081:
                TB_YaoXieYangHu.data data_1 = (data) getIntent().getExtras().getSerializable("data_class_message");
                Tooluite tooluite_1 = new Tooluite();
                Tool.changetitle(this, "养护详情");
                mLayout.addView(tooluite_1.getView_jb(this, "药械名称：", data_1.getName()));
                mLayout.addView(tooluite_1.getView_jb(this, "药械类型：", ((data_1.getIsMedicine().equals("true")) ? "药品" : "医疗器械")));
                mLayout.addView(tooluite_1.getView_jb(this, "企业名称：", data_1.getFirmName()));
                mLayout.addView(tooluite_1.getView_jb(this, "数　　量：", data_1.getQuantity()));
                mLayout.addView(tooluite_1.getView_jb(this, "仓库名称：", data_1.getStoreRoomName()));
                mLayout.addView(tooluite_1.getView_jb(this, "养  护  人：", data_1.getMaintenancePeople()));
                mLayout.addView(tooluite_1.getView_jb(this, "养护结果：", data_1.getMaintainResult()));
                mLayout.addView(tooluite_1.getView_jb(this, "包装情况：", data_1.getPackingConditions()));
                mLayout.addView(tooluite_1.getView_jb(this, "质量情况：", data_1.getQualityConditions()));
                mLayout.addView(tooluite_1.getView_jb(this, "有效期至：", new Tooluite().getDate(data_1.getExpiredDate(), "T")));
                mLayout.addView(tooluite_1.getView_jb(this, "养护时间：", new Tooluite().getDate(data_1.getMaintainTime(), "T")));
                mLayout.addView(tooluite_1.getView_jb(this, "包装时间：", new Tooluite().getDate(data_1.getProductionDate(), "T")));
                mLayout.addView(tooluite_1.getView_jb(this, "上传时间：", new Tooluite().getDate(data_1.getUploadTime(), "T")));
                break;
            case 99082:
                TB_ZhiLiangYanShou.data data_2 = (TB_ZhiLiangYanShou.data) getIntent().getExtras().getSerializable("data_class_message");
                Tooluite tooluite_2 = new Tooluite();
                Tool.changetitle(this, "质量验收");
                mLayout.addView(tooluite_2.getView_jb(this, "药械名称：", data_2.getName()));
                mLayout.addView(tooluite_2.getView_jb(this, "药械类型：", ((data_2.getIsMedicine().equals("true")) ? "药品" : "医疗器械")));
                mLayout.addView(tooluite_2.getView_jb(this, "企业名称：", data_2.getFirmName()));
                mLayout.addView(tooluite_2.getView_jb(this, "规格型号：", data_2.getSpecification()));
                mLayout.addView(tooluite_2.getView_jb(this, "批次: ", data_2.getBatchNumber()));
                mLayout.addView(tooluite_2.getView_jb(this, "有效期至：", new Tooluite().getDate(data_2.getExpiredDate(), "T")));
                mLayout.addView(tooluite_2.getView_jb(this, "数　　量：", data_2.getQuantity()));
                mLayout.addView(tooluite_2.getView_jb(this, "质量情况：", data_2.getQualityConditions()));
                mLayout.addView(tooluite_2.getView_jb(this, "验收结论：", data_2.getAcceptanceResult()));
                mLayout.addView(tooluite_2.getView_jb(this, "验收时间：", new Tooluite().getDate(data_2.getAcceptanceTime(), "T")));
                mLayout.addView(tooluite_2.getView_jb(this, "验收人：", data_2.getAccepter()));
                mLayout.addView(tooluite_2.getView_jb(this, "仓库名称：", data_2.getStoreRoomName()));
                mLayout.addView(tooluite_2.getView_jb(this, "包装情况：", data_2.getPackingConditions()));
                mLayout.addView(tooluite_2.getView_jb(this, "包装时间：", new Tooluite().getDate(data_2.getProductionDate(), "T")));
                mLayout.addView(tooluite_2.getView_jb(this, "上传时间：", new Tooluite().getDate(data_2.getUploadTime(), "T")));
                break;
            case 99083:
                TB_BaoShunBaoYin.data data_3 = (TB_BaoShunBaoYin.data) getIntent().getExtras().getSerializable("data_class_message");
                Tooluite tooluite_3 = new Tooluite();
                Tool.changetitle(this, "损溢记录");
                mLayout.addView(tooluite_3.getView_jb(this, "药械名称：", data_3.getName()));
                mLayout.addView(tooluite_3.getView_jb(this, "报损类型：", data_3.getLooseIncomeTypeID().equals("1") ? "报损" : "报溢"));
                mLayout.addView(tooluite_3.getView_jb(this, "药械类型：", ((data_3.getIsMedicine().equals("true")) ? "药品" : "医疗器械")));
                mLayout.addView(tooluite_3.getView_jb(this, "企业名称：", data_3.getFirmName()));
                mLayout.addView(tooluite_3.getView_jb(this, "规格型号：", data_3.getSpecification()));


                mLayout.addView(tooluite_3.getView_jb(this, "批次：", data_3.getBatchNumber()));
                mLayout.addView(tooluite_3.getView_jb(this, "有效期至：", new Tooluite().getDate(data_3.getExpiredDate(), "T")));
                mLayout.addView(tooluite_3.getView_jb(this, "数量：", data_3.getQuantity()));
                mLayout.addView(tooluite_3.getView_jb(this, "剂型：", data_3.getFormulationType()));

                mLayout.addView(tooluite_3.getView_jb(this, "上报时间：", new Tooluite().getDate(data_3.getUploadTime(), "T")));
                mLayout.addView(tooluite_3.getView_jb(this, "生产单位：", data_3.getManufacturer()));
                mLayout.addView(tooluite_3.getView_jb(this, "生产时间：", new Tooluite().getDate(data_3.getProductionDate(), "T")));
                mLayout.addView(tooluite_3.getView_jb(this, "仓库名称：", data_3.getStoreRoomName()));
                mLayout.addView(tooluite_3.getView_jb(this, "上传时间：", new Tooluite().getDate(data_3.getUploadTime(), "T")));
                break;
            case 99084:
                String url = getIntent().getExtras().getString("Dataurl");
                TB_Chunfangjilu.data data_4 = (TB_Chunfangjilu.data) getIntent().getExtras().getSerializable("data_class_message");
                Tooluite tooluite_4 = new Tooluite();
                Tool.changetitle(this, "处方记录");
                mLayout.addView(tooluite_4.getView_jb(this, "企业名称：", data_4.getFirmName()));
                mLayout.addView(tooluite_4.getView_jb(this, "医师：", data_4.getDoctorName()));
                mLayout.addView(tooluite_4.getView_jb(this, "处方编号：", data_4.getPrescriptionID()));
                mLayout.addView(tooluite_4.getView_jb(this, "病人诊断号：", data_4.getPrescriptionID()));
                mLayout.addView(tooluite_4.getView_jb(this, "病人姓名：", data_4.getPatientName()));
                mLayout.addView(tooluite_4.getView_jb(this, "病人性别：", data_4.getPatientGender()));
                mLayout.addView(tooluite_4.getView_jb(this, "年龄：", data_4.getPatientAge()));
                mLayout.addView(tooluite_4.getView_jb(this, "科室：", data_4.getDepartment()));
                mLayout.addView(tooluite_4.getView_jb(this, "费别：", data_4.getChargeType()));
                mLayout.addView(tooluite_4.getView_jb(this, "审核、调配（医师）：", data_4.getMedicineMan()));
                mLayout.addView(tooluite_4.getView_jb(this, "核对、发药（医师）：", data_4.getReviewedBy()));
                mLayout.addView(tooluite_4.getView_jb(this, "开方时间：", new Tooluite().getDate(data_4.getPrescriptionTime(), "T")));
                mLayout.addView(tooluite_4.getView_jb(this, "临床诊断：", data_4.getDiagnosticResult()));
//				mLayout.addView(tooluite_4.getView_jb(this, "客户本地数据时间：", data_4.getLocalLastedTime()));
                mLayout.addView(tooluite_4.getView_jb(this, "客户上传时间：", new Tooluite().getDate(data_4.getUploadTime(), "T")));
                dialog = new WaitDialog(this);
                dialog.setContent("正在加载...");
                dialog.show();
                HttpHelper.getInstance().service.get(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        dialog.dismiss();
                        if (json.equals("连接失败")) {
                            showToast("网络连接失败，请稍后重试!");
                        } else {
                            JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                            TB_yaopingType tb_yaopingType = ParsonJson.jsonToBeanObj("{\"Data\":" + bean.getJsonData() + "}", TB_yaopingType.class);
                            Tooluite tooluite = new Tooluite();
                            View mView = LayoutInflater.from(TypeShow.this).inflate(R.layout.yp_view, null);
                            ((TextView) mView.findViewById(R.id.aa)).getPaint().setFakeBoldText(true);
                            mLayout.addView(mView);
                            for (TB_yaopingType.Data data : tb_yaopingType.getData()) {
                                mLayout.addView(tooluite.getView_yx(TypeShow.this, data, false));
                            }
                        }
                        Log.d("sss", json);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }
                });
                break;
            case 99085:
                TB_WenDuShiDuJianCe.data data_5 = (com.heking.qsy.activity.regulatory.tab.TB_WenDuShiDuJianCe.data) getIntent().getExtras().getSerializable("data_class_message");
                Tooluite tooluite_5 = new Tooluite();
                Tool.changetitle(this, "温度湿度检测");
                mLayout.addView(tooluite_5.getView_jb(this, "企业名称：", data_5.getFirmName()));
                mLayout.addView(tooluite_5.getView_jb(this, "仓　　库：", data_5.getStoreRoomName()));
                mLayout.addView(tooluite_5.getView_jb(this, "监测位置：", data_5.getAddress()));
                mLayout.addView(tooluite_5.getView_jb(this, "监测时间：", tooluite_5.getDate(data_5.getExtractTime())));
                mLayout.addView(tooluite_5.getView_jb(this, "监测温度：", tooluite_5.getInt(data_5.getTemperature()).equals("") ? "" : tooluite_5.getInt(data_5.getTemperature()) + "℃"));
                mLayout.addView(tooluite_5.getView_jb(this, "温度上限：", tooluite_5.getInt(data_5.getTemperatureHigh()).equals("") ? "" : tooluite_5.getInt(data_5.getTemperatureHigh()) + "℃"));
                mLayout.addView(tooluite_5.getView_jb(this, "温度下限：", tooluite_5.getInt(data_5.getTemperatureLow()).equals("") ? "" : tooluite_5.getInt(data_5.getTemperatureLow()) + "℃"));
                mLayout.addView(tooluite_5.getView_jb(this, "监测湿度：", tooluite_5.getInt(data_5.getHumidity()).equals("") ? "" : tooluite_5.getInt(data_5.getHumidity()) + "RH"));
                mLayout.addView(tooluite_5.getView_jb(this, "湿度上限：", tooluite_5.getInt(data_5.getHumidityHigh()).equals("") ? "" : tooluite_5.getInt(data_5.getHumidityHigh()) + "RH"));
                mLayout.addView(tooluite_5.getView_jb(this, "湿度下限：", tooluite_5.getInt(data_5.getHumidityLow()).equals("") ? "" : tooluite_5.getInt(data_5.getHumidityLow()) + "RH"));
                break;
            case 99086:
                TB_BuLiangFanyingJianCe.data blfydata = (com.heking.qsy.activity.regulatory.tab.TB_BuLiangFanyingJianCe.data) getIntent().getExtras().getSerializable("data_class_message");
                String blfyjc_url = getIntent().getExtras().getString("Dataurl");
                Tooluite tooluite_6 = new Tooluite();
                HttpHelper.getInstance().service.get(blfyjc_url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        Log.d("json", json);
                        JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                        TB_BuLiangFanyingJianCe.data blfydata = ParsonJson.jsonToBeanObj(bean.getJsonData(), TB_BuLiangFanyingJianCe.data.class);
                        Tool.changetitle(TypeShow.this, "患者信息");
                        Tooluite tooluite_6 = new Tooluite();
                        if (blfydata != null) {
                            //mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "记录人：", blfydata.getReporterName()));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "区域：", blfydata.getAreaName()));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "就医编号：", blfydata.getHospitalizeNumber()));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "患者姓名：", blfydata.getPatientName()));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "患者性别：", blfydata.getPatientGender().equals("true") ? "男" : "女"));//.equals(false)?"女":"男"
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "出生日期：", new Tooluite().getDate(blfydata.getNationality())));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "联系方式：", blfydata.getPatientContact()));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "有无家族ADR情况：", blfydata.getFamilyADR().equals("3") ? "不详" : blfydata.getFamilyADR().equals(2) ? "有" : "无"));//
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "有无既往ADR情况：", blfydata.getHistoryADR().equals("3") ? "不详" : blfydata.getFamilyADR().equals(2) ? "有" : "无"));//
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "家住ADR说明：", blfydata.getFamilyADRDescription()));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "既往ADR说明：", blfydata.getHistoryADRDescription()));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "机构名称：", blfydata.getHospitalName()));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "发生时间：", new Tooluite().getDate(blfydata.getHappenDateTime(), "T")));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "信息渠道：", blfydata.getInfoChannel()));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "不良状态：", blfydata.getADRStatus()));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "过程描述：", blfydata.getADRProcedureDescription()));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "严重程度：", blfydata.getRemark()));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "不良结果：", blfydata.getADRResult()));
                            mLayout.addView(tooluite_6.getView_jb(TypeShow.this, "报告人：", blfydata.getReporterName()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

                break;
        }
    }
}
