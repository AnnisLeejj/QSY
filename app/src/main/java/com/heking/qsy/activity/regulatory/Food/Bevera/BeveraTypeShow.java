package com.heking.qsy.activity.regulatory.Food.Bevera;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heking.SPJK.data.Config;
import com.heking.SPJK.data.TempData;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.FirmShow.MonitoringVideoHK;
import com.heking.qsy.activity.regulatory.JsongDataBean;
import com.heking.qsy.activity.regulatory.ToolUtil;
import com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_BeveraProcurement;
import com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_BeveraRawMaterialInventory;
import com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_FoodBeveraAdditivesUsedParamet;
import com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_FoodBeveraCleaningAndDisinfection;
import com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_FoodBeveraTryToSample;
import com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_FoodBeveraWasteDisposal;
import com.heking.qsy.activity.regulatory.Food.Bevera.tab.Details.TB_BeveraProcurementDetails;
import com.heking.qsy.activity.regulatory.details.ProcurementWarehousingActivity;
import com.heking.qsy.activity.regulatory.details.type.Tooluite;
import com.heking.qsy.activity.regulatory.tab.TB_CaiGouRuKu;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.FirmTypeBean.Data;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.snoa.WPConfig;
import com.hikvision.vmsnetsdk.VMSNetSDK;

import MyUtils.SharePrefenceUtils.SPUtils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BeveraTypeShow extends Activity {
    private WaitDialog dialog;
    private LinearLayout mLayout;
    private FirmTypeBean.Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        Tool.endActivity(this);
        mLayout = (LinearLayout) findViewById(R.id.jibenlayout);
        state = getIntent().getExtras().getInt("state");
        data = (Data) getIntent().getExtras().getSerializable("Firm");

    }

    private void iniData() {
        final Tooluite tooluite = new Tooluite();
        View mView = LayoutInflater.from(this).inflate(R.layout.yp_view, null);
        TextView textView = (TextView) mView.findViewById(R.id.aa);
        textView.getPaint().setFakeBoldText(true);
        textView.setText("基本信息");
        switch (state) {
            case 4400001:
                Tool.changetitle(this, "采购入库");
                //:TODO:
                TB_BeveraProcurement.Data BPdata = (com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_BeveraProcurement.Data) getIntent().getExtras().getSerializable("data_class_message");
                //企业名称	单据号	供应商名称	供应商负责人	采购人	验收人	填报时间
                mLayout.addView(mView);
                mLayout.addView(tooluite.getView_jb(this, "企业名称：", data.getFirmName()));
                mLayout.addView(tooluite.getView_jb(this, "单据号：", BPdata.getBillNo()));
                mLayout.addView(tooluite.getView_jb(this, "供应商名称：", BPdata.getSupply()));
                mLayout.addView(tooluite.getView_jb(this, "供应商负责人：", BPdata.getSupplyMan()));
                mLayout.addView(tooluite.getView_jb(this, "采购人：", BPdata.getPurchaseMan()));
                mLayout.addView(tooluite.getView_jb(this, "验收人：", BPdata.getCheckMan()));
                mLayout.addView(tooluite.getView_jb(this, "填报时间：", tooluite.getDate(BPdata.getFillInDatetime(), "T")));
                String url = ToolUtil.DataUrl.FoodGeturl() + ToolUtil.UrlFood.GetPurchasedDetailsByFirmID + "?FirmType=3&purchasedID=" + BPdata.getID();
                HttpHelper.getInstance().service.get(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        TB_BeveraProcurementDetails tb_BeveraProcurementDetails = ParsonJson.jsonToBeanObj("{\"Data\":" + json + "}", TB_BeveraProcurementDetails.class);
                        if (tb_BeveraProcurementDetails != null && tb_BeveraProcurementDetails.getData().size() > 0) {
                            View mView = LayoutInflater.from(BeveraTypeShow.this).inflate(R.layout.yp_view, null);
                            TextView textView = (TextView) mView.findViewById(R.id.aa);
                            textView.getPaint().setFakeBoldText(true);
                            textView.setText("详细信息");
                            mLayout.addView(mView);

                            for (TB_BeveraProcurementDetails.Data data : tb_BeveraProcurementDetails.getData()) {
                                TypeView typeView = new TypeView(BeveraTypeShow.this);
                                typeView.setValue("产品名称：", data.getProductName(), "数　　量：", tooluite.getInt(data.getQuantity()) + "");
                                typeView.setValue("单　　位：", data.getUnit(), "生产日期：", tooluite.getDate(data.getProductDate(), "T"));
                                typeView.setValue("到期日期：", tooluite.getDate(data.getExpiredDate(), "T"), "采购日期：", tooluite.getDate(data.getPurchaseDate(), "T"));
                                typeView.setValue("采购对象：", data.getPurchaseObject(), "送货日期：", tooluite.getDate(data.getDeliveryDate(), "T"));
                                typeView.setValue("规　　格：", data.getSpecification(), "生产批号：", data.getProductionLicenseNumber());
                                typeView.setValue("生产企业：", data.getManufacturer(), "", "");
                                mLayout.addView(typeView.getmViewLayout());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
                break;
            case 4400002:
                Tool.changetitle(this, "食品添加剂采购入库");
                TB_BeveraProcurement.Data BPdata_01 = (com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_BeveraProcurement.Data) getIntent().getExtras().getSerializable("data_class_message");
                //企业名称	单据号	供应商名称	供应商负责人	采购人	验收人	填报时间
                mLayout.addView(mView);
                mLayout.addView(tooluite.getView_jb(this, "企业名称：", data.getFirmName()));
                mLayout.addView(tooluite.getView_jb(this, "单据号：", BPdata_01.getBillNo()));
                mLayout.addView(tooluite.getView_jb(this, "供应商名称：", BPdata_01.getSupply()));
                mLayout.addView(tooluite.getView_jb(this, "供应商负责人：", BPdata_01.getSupplyMan()));
                mLayout.addView(tooluite.getView_jb(this, "采购人：", BPdata_01.getPurchaseMan()));
                mLayout.addView(tooluite.getView_jb(this, "验收人：", BPdata_01.getCheckMan()));
                mLayout.addView(tooluite.getView_jb(this, "填报时间：", tooluite.getDate(BPdata_01.getFillInDatetime(), "T")));

                String url1 = ToolUtil.DataUrl.FoodGeturl() + ToolUtil.UrlFood.GetPurchasedDetailsByFirmID + "?FirmType=3&purchasedID=" + BPdata_01.getID();
                HttpHelper.getInstance().service.get(url1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        TB_BeveraProcurementDetails tb_BeveraProcurementDetails = ParsonJson.jsonToBeanObj("{\"Data\":" + json + "}", TB_BeveraProcurementDetails.class);
                        if (tb_BeveraProcurementDetails != null && tb_BeveraProcurementDetails.getData().size() > 0) {
                            View mView = LayoutInflater.from(BeveraTypeShow.this).inflate(R.layout.yp_view, null);
                            TextView textView = (TextView) mView.findViewById(R.id.aa);
                            textView.getPaint().setFakeBoldText(true);
                            textView.setText("详细信息");
                            mLayout.addView(mView);

                            for (TB_BeveraProcurementDetails.Data data : tb_BeveraProcurementDetails.getData()) {
                                TypeView typeView = new TypeView(BeveraTypeShow.this);
                                typeView.setValue("产品名称：", data.getProductName(), "数　　量：", tooluite.getInt(data.getQuantity()) + "");
                                typeView.setValue("单　　位：", data.getUnit(), "生产日期：", tooluite.getDate(data.getProductDate(), "T"));
                                typeView.setValue("到期日期：", tooluite.getDate(data.getExpiredDate(), "T"), "采购日期：", tooluite.getDate(data.getPurchaseDate(), "T"));
                                typeView.setValue("采购对象：", data.getPurchaseObject(), "送货日期：", tooluite.getDate(data.getDeliveryDate(), "T"));
                                typeView.setValue("规　　格：", data.getSpecification(), "生产批号：", data.getProductionLicenseNumber());
                                typeView.setValue("生产企业：", data.getManufacturer(), "", "");
                                mLayout.addView(typeView.getmViewLayout());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
                break;
            case 4400003:
                Tool.changetitle(this, "食品相关产品采购进货");
                TB_BeveraProcurement.Data BPdata_02 = (com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_BeveraProcurement.Data) getIntent().getExtras().getSerializable("data_class_message");
                //企业名称	单据号	供应商名称	供应商负责人	采购人	验收人	填报时间
                mLayout.addView(mView);
                mLayout.addView(tooluite.getView_jb(this, "企业名称：", data.getFirmName()));
                mLayout.addView(tooluite.getView_jb(this, "单据号：", BPdata_02.getBillNo()));
                mLayout.addView(tooluite.getView_jb(this, "供应商名称：", BPdata_02.getSupply()));
                mLayout.addView(tooluite.getView_jb(this, "供应商负责人：", BPdata_02.getSupplyMan()));
                mLayout.addView(tooluite.getView_jb(this, "采购人：", BPdata_02.getPurchaseMan()));
                mLayout.addView(tooluite.getView_jb(this, "验收人：", BPdata_02.getCheckMan()));
                mLayout.addView(tooluite.getView_jb(this, "填报时间：", tooluite.getDate(BPdata_02.getFillInDatetime(), "T")));
                String url2 = ToolUtil.DataUrl.FoodGeturl() + ToolUtil.UrlFood.GetPurchasedDetailsByFirmID + "?FirmType=3&purchasedID=" + BPdata_02.getID();
                HttpHelper.getInstance().service.get(url2.trim()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String json) {
                        TB_BeveraProcurementDetails tb_BeveraProcurementDetails = ParsonJson.jsonToBeanObj("{\"Data\":" + json + "}", TB_BeveraProcurementDetails.class);
                        if (tb_BeveraProcurementDetails != null && tb_BeveraProcurementDetails.getData().size() > 0) {
                            View mView = LayoutInflater.from(BeveraTypeShow.this).inflate(R.layout.yp_view, null);
                            TextView textView = (TextView) mView.findViewById(R.id.aa);
                            textView.getPaint().setFakeBoldText(true);
                            textView.setText("详细信息");
                            mLayout.addView(mView);

                            for (TB_BeveraProcurementDetails.Data data : tb_BeveraProcurementDetails.getData()) {
                                TypeView typeView = new TypeView(BeveraTypeShow.this);
                                typeView.setValue("产品名称：", data.getProductName(), "数　　量：", tooluite.getInt(data.getQuantity()) + "");
                                typeView.setValue("单　　位：", data.getUnit(), "生产日期：", tooluite.getDate(data.getProductDate(), "T"));
                                typeView.setValue("到期日期：", tooluite.getDate(data.getExpiredDate(), "T"), "采购日期：", tooluite.getDate(data.getPurchaseDate(), "T"));
                                typeView.setValue("采购对象：", data.getPurchaseObject(), "送货日期：", tooluite.getDate(data.getDeliveryDate(), "T"));
                                typeView.setValue("规　　格：", data.getSpecification(), "生产批号：", data.getProductionLicenseNumber());
                                typeView.setValue("生产企业：", data.getManufacturer(), "", "");
                                mLayout.addView(typeView.getmViewLayout());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

                break;
            case 4400004:
                Tool.changetitle(this, "食品原材料入库");
                TB_BeveraRawMaterialInventory.Data BRIData = (com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_BeveraRawMaterialInventory.Data) getIntent().getExtras().getSerializable("data_class_message");
                mLayout.addView(mView);
                //企业名称
                mLayout.addView(tooluite.getView_jb(this, "企业名称：", data.getFirmName()));
                mLayout.addView(tooluite.getView_jb(this, "原材料名称：", BRIData.getName()));
                mLayout.addView(tooluite.getView_jb(this, "入库日期：", tooluite.getDate(BRIData.getInDate(), "T")));
                mLayout.addView(tooluite.getView_jb(this, "供货商：", BRIData.getSupplierName()));
                mLayout.addView(tooluite.getView_jb(this, "数量：", BRIData.getQuantity()));
                mLayout.addView(tooluite.getView_jb(this, "单价：", BRIData.getUnitPrice()));
                mLayout.addView(tooluite.getView_jb(this, "总金额：", BRIData.getTotalAmount()));
                mLayout.addView(tooluite.getView_jb(this, "感官检查：", BRIData.getSensoryInspection()));
                mLayout.addView(tooluite.getView_jb(this, "保质期：", BRIData.getShelfLife()));
                mLayout.addView(tooluite.getView_jb(this, "采购人：", BRIData.getPurchaser()));
                mLayout.addView(tooluite.getView_jb(this, "保管人：", BRIData.getKeeper()));
                mLayout.addView(tooluite.getView_jb(this, "数据更新时间：", tooluite.getDate(BRIData.getDataUploadTime(), "T")));
                break;
            case 4400005:
                Tool.changetitle(this, "食品原材料出库");
                TB_BeveraRawMaterialInventory.Data BRIData_1 = (com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_BeveraRawMaterialInventory.Data) getIntent().getExtras().getSerializable("data_class_message");
                mLayout.addView(mView);
                //企业名称、原辅料名称、出库日期、数量、总金额、货物查验、领货人、保管人、余货、数据更新数据
                mLayout.addView(tooluite.getView_jb(this, "企业名称：", data.getFirmName()));
                mLayout.addView(tooluite.getView_jb(this, "原材料名称：", BRIData_1.getName()));
                mLayout.addView(tooluite.getView_jb(this, "出库日期：", tooluite.getDate(BRIData_1.getOutDate(), "T")));
                mLayout.addView(tooluite.getView_jb(this, "数量：", "" + tooluite.getInt(BRIData_1.getQuantity())));
                mLayout.addView(tooluite.getView_jb(this, "总金额：", tooluite.getInt(BRIData_1.getTotalAmount()) + ""));
                mLayout.addView(tooluite.getView_jb(this, "货物查验：", BRIData_1.getCheckGoods()));
                mLayout.addView(tooluite.getView_jb(this, "领货人：", BRIData_1.getRecipients()));
                mLayout.addView(tooluite.getView_jb(this, "保管人：", BRIData_1.getKeeper()));
                mLayout.addView(tooluite.getView_jb(this, "余货：", BRIData_1.getSurplusGoods()));
                mLayout.addView(tooluite.getView_jb(this, "数据更新数据：", tooluite.getDate(BRIData_1.getDataUploadTime(), "T")));
                break;
            case 4400006:
                Tool.changetitle(this, "食品添加剂使用台账");
                TB_FoodBeveraAdditivesUsedParamet.Data FBAUPData = (com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_FoodBeveraAdditivesUsedParamet.Data) getIntent().getExtras().getSerializable("data_class_message");
                //企业名称
                mLayout.addView(mView);
                mLayout.addView(tooluite.getView_jb(this, "企业名称：", data.getFirmName()));
                mLayout.addView(tooluite.getView_jb(this, "食品添加剂名称：", FBAUPData.getGenericName()));
                mLayout.addView(tooluite.getView_jb(this, "使用时间：", tooluite.getDate(FBAUPData.getUsingTime(), "T")));
                mLayout.addView(tooluite.getView_jb(this, "规格：", FBAUPData.getSpecification()));
                mLayout.addView(tooluite.getView_jb(this, "保质期：", FBAUPData.getShelfLife()));
                mLayout.addView(tooluite.getView_jb(this, "生产批号：", FBAUPData.getBatchNumber()));
                mLayout.addView(tooluite.getView_jb(this, "供应单位名称：", FBAUPData.getSupplierName()));
                mLayout.addView(tooluite.getView_jb(this, "联系方式：", FBAUPData.getSupplierContact()));
                mLayout.addView(tooluite.getView_jb(this, "采购人：", FBAUPData.getPurchaser()));
                mLayout.addView(tooluite.getView_jb(this, "进货数量：", FBAUPData.getPurchaseQuantity()));
                mLayout.addView(tooluite.getView_jb(this, "使用数量：", FBAUPData.getUsingQuantity()));
                mLayout.addView(tooluite.getView_jb(this, "库存数量：", FBAUPData.getInventoryQuantity()));
                mLayout.addView(tooluite.getView_jb(this, "单位：", FBAUPData.getUnit()));
                mLayout.addView(tooluite.getView_jb(this, "领取人：", FBAUPData.getRecipients()));
                mLayout.addView(tooluite.getView_jb(this, "保管员：", FBAUPData.getKeeper()));
                mLayout.addView(tooluite.getView_jb(this, "数据更新时间：", tooluite.getDate(FBAUPData.getDataUploadTime(), "T")));
                break;

            case 4400007:
                Tool.changetitle(this, "餐厨废弃物处理");
                TB_FoodBeveraWasteDisposal.Data FBWDData = (com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_FoodBeveraWasteDisposal.Data) getIntent().getExtras().getSerializable("data_class_message");
                mLayout.addView(mView);
                mLayout.addView(tooluite.getView_jb(this, "企业名称：", data.getFirmName()));
                mLayout.addView(tooluite.getView_jb(this, "餐厨废弃种类：", FBWDData.getSpecies()));
                mLayout.addView(tooluite.getView_jb(this, "餐厨废弃物量（kg）：", FBWDData.getWeight()));
                mLayout.addView(tooluite.getView_jb(this, "存放容器：", FBWDData.getStorageContainers()));
                mLayout.addView(tooluite.getView_jb(this, "存放地点：", FBWDData.getStorageContainers()));

                mLayout.addView(tooluite.getView_jb(this, "处置方式：", FBWDData.getMethod()));
                mLayout.addView(tooluite.getView_jb(this, "回收去向：", FBWDData.getWhereabouts()));
                mLayout.addView(tooluite.getView_jb(this, "回收用途：", FBWDData.getWhereabouts()));

                mLayout.addView(tooluite.getView_jb(this, "处理日期：", tooluite.getDate(FBWDData.getProcessingDate(), "T")));
                mLayout.addView(tooluite.getView_jb(this, "餐饮单位经手人：", FBWDData.getDiningPeople()));
                mLayout.addView(tooluite.getView_jb(this, "废弃物处理人：", FBWDData.getWasteHandlePeople()));
                mLayout.addView(tooluite.getView_jb(this, "废弃物接收人：", FBWDData.getWasteReceivePeople()));
                mLayout.addView(tooluite.getView_jb(this, "接收人电话：", FBWDData.getReceivePeoplePhone()));


                break;
            case 4400008:
                Tool.changetitle(this, "餐饮具清洗消毒");
                TB_FoodBeveraCleaningAndDisinfection.Data FDCADData = (com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_FoodBeveraCleaningAndDisinfection.Data) getIntent().getExtras().getSerializable("data_class_message");
                //企业名称	清洗消毒日期	一洗	二清	三消毒	四冲洗	是否蒸煮或药物消毒	是否入保洁柜	负责人	数据更新时间
                mLayout.addView(mView);
                mLayout.addView(tooluite.getView_jb(this, "企业名称：", data.getFirmName()));
                mLayout.addView(tooluite.getView_jb(this, "清洗消毒日期：", tooluite.getDate(FDCADData.getCleanDate(), "T")));
                mLayout.addView(tooluite.getView_jb(this, "一洗：", FDCADData.getIsWash().equals("true") ? "是" : "否"));
                mLayout.addView(tooluite.getView_jb(this, "二清：", FDCADData.getIsClear().equals("true") ? "是" : "否"));
                mLayout.addView(tooluite.getView_jb(this, "三消毒：", FDCADData.getIsDisinfection().equals("true") ? "是" : "否"));
                mLayout.addView(tooluite.getView_jb(this, "四冲洗：", FDCADData.getIsRinse().equals("true") ? "是" : "否"));
                mLayout.addView(tooluite.getView_jb(this, "是否蒸煮或药物消毒：", FDCADData.getIsCooking().equals("true") ? "是" : "否"));
                mLayout.addView(tooluite.getView_jb(this, "是否入保洁柜：", FDCADData.getIsIntoCabinet().equals("true") ? "是" : "否"));
                mLayout.addView(tooluite.getView_jb(this, "负责人：", FDCADData.getChargePeople()));
                mLayout.addView(tooluite.getView_jb(this, "数据更新时间：", tooluite.getDate(FDCADData.getDataUploadTime(), "T")));
                break;
            case 4400009:
                Tool.changetitle(this, "食品试尝留样");
                TB_FoodBeveraTryToSample.Data FBTTData = (com.heking.qsy.activity.regulatory.Food.Bevera.tab.TB_FoodBeveraTryToSample.Data) getIntent().getExtras().getSerializable("data_class_message");
                //企业名称						留样时间（小时）
                mLayout.addView(mView);
                mLayout.addView(tooluite.getView_jb(this, "企业名称：", data.getFirmName()));
                mLayout.addView(tooluite.getView_jb(this, "食品名称：", FBTTData.getFoodName()));
                mLayout.addView(tooluite.getView_jb(this, "试尝时间：", tooluite.getDate(FBTTData.getTasteTime(), "T")));
                mLayout.addView(tooluite.getView_jb(this, "试尝人：", FBTTData.getTastePeople()));
                mLayout.addView(tooluite.getView_jb(this, "试尝情况：", FBTTData.getTasteCase()));
                mLayout.addView(tooluite.getView_jb(this, "留样人：", FBTTData.getRetentionSamplesPeople()));
                mLayout.addView(tooluite.getView_jb(this, "留样时间（小时）：", FBTTData.getRetentionSamplesTime()));
                mLayout.addView(tooluite.getView_jb(this, "销毁留样时间：", tooluite.getDate(FBTTData.getDestructionRetentionTime(), "T")));
                mLayout.addView(tooluite.getView_jb(this, "数据更新时间：", tooluite.getDate(FBTTData.getDataUploadTime(), "T")));
                break;
        }
    }
}
