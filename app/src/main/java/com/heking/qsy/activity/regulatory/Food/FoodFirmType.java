package com.heking.qsy.activity.regulatory.Food;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.RegulatoryUTil;
import com.heking.qsy.activity.regulatory.ToolUtil;
import com.heking.qsy.activity.regulatory.Food.Bevera.TypeView;
import com.heking.qsy.activity.regulatory.Food.Bevera.tab.Details.TB_BeveraProcurementDetails;
import com.heking.qsy.activity.regulatory.Food.Bevera.tab.Details.TB_ReturnPurchased;
import com.heking.qsy.activity.regulatory.Food.tab.TB_FoodInventory;
import com.heking.qsy.activity.regulatory.Food.tab.TB_FoodLooseIncome;
import com.heking.qsy.activity.regulatory.Food.tab.TB_FoodPurchased;
import com.heking.qsy.activity.regulatory.Food.tab.TB_FoodPurchasedRetrun;
import com.heking.qsy.activity.regulatory.Food.tab.TB_FoodSalses;
import com.heking.qsy.activity.regulatory.Food.tab.TB_FoodSalsesReturn;
import com.heking.qsy.activity.regulatory.Food.tab.TB_xiaoshoutuihuo;
import com.heking.qsy.activity.regulatory.details.type.Tooluite;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.FirmTypeBean.Data;
import com.heking.qsy.util.Tool;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FoodFirmType extends Activity {
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
    private RegulatoryUTil uTil;
    private String Name;

    private void iniView() {
        Tool.endActivity(this);
        mLayout = (LinearLayout) findViewById(R.id.jibenlayout);
        state = getIntent().getExtras().getInt("state");
        data = (Data) getIntent().getExtras().getSerializable("Firm");
//		Name=getIntent().getExtras().getString("Name");
        uTil = (RegulatoryUTil) getIntent().getExtras().getSerializable(
                "RegulatoryUTil");

    }

    private void iniData() {
        final Tooluite tooluite = new Tooluite();
        View mView = LayoutInflater.from(this).inflate(R.layout.yp_view, null);
        TextView textView = (TextView) mView.findViewById(R.id.aa);
        textView.getPaint().setFakeBoldText(true);

        /**
         * @1 药品经营
         * @2 食品经营
         * @3 餐饮经营
         * @4 食品生产
         * @5 药品生产
         */
        switch (uTil.getState()) {
            case 5:
                switch (state) {
                    case 2300691:
                        Tool.changetitle(this, "库存详细信息");
                        textView.setText("原辅料库存信息");
                        TB_FoodInventory.Data data_1 = (TB_FoodInventory.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "原辅料名称：",
                                data_1.getMaterielName()));
                        mLayout.addView(tooluite.getView_jb(this, "规格型号：",
                                data_1.getSpecification()));
                        mLayout.addView(tooluite.getView_jb(this, "库存数量：",
                                tooluite.getInt(data_1.getQuantity()) + ""));
                        mLayout.addView(tooluite.getView_jb(this, "生产日期：",
                                tooluite.getDate(data_1.getManufactureDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "批         号：",
                                data_1.getBatchNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "有效期至：",
                                tooluite.getDate(data_1.getExpiredDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "保  质  期：",
                                data_1.getShelfLife()));
                        mLayout.addView(tooluite.getView_jb(this, "仓库名称：",
                                data_1.getStoreRoomName()));

                        // mLayout.addView(tooluite.getView_jb(this, "上传时间：",
                        // tooluite.getDate(data_1.getUploadTime(), "T")));
                        break;
                    case 2300692:
                        Tool.changetitle(this, "报溢记录");
                        TB_FoodLooseIncome.Data data_2 = (TB_FoodLooseIncome.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        textView.setVisibility(LinearLayout.GONE);
                        // 食品名称 单位 ） 数据更新时间
                        mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "损溢日期",
                                tooluite.getDate(data_2.getLooseIncomeDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "药品名称：",
                                data_2.getGenericName()));
                        mLayout.addView(tooluite.getView_jb(this, "包装规格：",
                                data_2.getSpecification()));
                        mLayout.addView(tooluite.getView_jb(this, "生产日期：",
                                tooluite.getDate(data_2.getManufactureDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "批　　次：",
                                data_2.getBatchNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "保质期：",
                                data_2.getShelfLife()));
                        mLayout.addView(tooluite.getView_jb(this, "原库存：",
                                data_2.getOldInventory()));
                        mLayout.addView(tooluite.getView_jb(this, "实际库存：",
                                data_2.getFactInventory()));
                        mLayout.addView(tooluite.getView_jb(this, "损溢原因：",
                                data_2.getLooseIncomeReason()));
                        mLayout.addView(tooluite.getView_jb(this, "操作人：",
                                data_2.getOperUser()));
                        mLayout.addView(tooluite.getView_jb(this, "审核人：",
                                data_2.getAuditUser()));
                        break;
                    case 2300693:
                        Tool.changetitle(this, "入库信息");
                        TB_FoodPurchased.Data data_3 = (TB_FoodPurchased.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        // 数据上传时间
                        View mView1 = LayoutInflater.from(this).inflate(
                                R.layout.yp_view, null);
                        TextView textView1 = (TextView) mView1.findViewById(R.id.aa);
                        textView1.getPaint().setFakeBoldText(true);
                        textView1.setText("基本信息");
                        mLayout.addView(mView1);
                        mLayout.addView(tooluite.getView_jb(this, "进货日期：",
                                tooluite.getDate(data_3.getPurchaseDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "货物名称：",
                                data_3.getProductName()));
                        mLayout.addView(tooluite.getView_jb(this, "经手人：",
                                data_3.getOperator()));
                        mLayout.addView(tooluite.getView_jb(this, "上传时间：",
                                tooluite.getDate(data_3.getUploadTime(), "T")));
                        View mView2 = LayoutInflater.from(this).inflate(
                                R.layout.yp_view, null);
                        TextView textView2 = (TextView) mView2.findViewById(R.id.aa);
                        textView2.getPaint().setFakeBoldText(true);
                        textView2.setText("原辅料信息");
                        // 食品名称 单位 ） 数据更新时间
                        mLayout.addView(mView2);
                        mLayout.addView(tooluite.getView_jb(this, "原辅料名称：",
                                data_3.getProductName()));
                        mLayout.addView(tooluite.getView_jb(this, "生产日期：",
                                tooluite.getDate(data_3.getManufactureDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "批号：",
                                data_3.getBatchNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "有效期：",
                                tooluite.getDate(data_3.getValidityDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "保质期：",
                                data_3.getShelfLife()));
                        mLayout.addView(tooluite.getView_jb(this, "规格型号：",
                                data_3.getSpecifications()));
                        mLayout.addView(tooluite.getView_jb(this, "数量：",
                                data_3.getQuantity()));
                        // mLayout.addView(tooluite.getView_jb(this, "追溯码：",
                        // data_3.getShelfLife()));
                        View mView3 = LayoutInflater.from(this).inflate(
                                R.layout.yp_view, null);
                        TextView textView3 = (TextView) mView3.findViewById(R.id.aa);
                        textView3.getPaint().setFakeBoldText(true);
                        textView3.setText("原辅料来源");
                        // 食品名称 单位 ） 数据更新时间
                        mLayout.addView(mView3);
                        mLayout.addView(tooluite.getView_jb(this, "生产单位（或销售单位）名称：",
                                data_3.getSupplierName()));
                        mLayout.addView(tooluite.getView_jb(this, "联系人：",
                                data_3.getContact()));
                        mLayout.addView(tooluite.getView_jb(this, "联系电话：",
                                data_3.getContactPhone()));
                        // mLayout.addView(tooluite.getView_jb(this, "：",
                        // data_3.getContact()));
                        // mLayout.addView(tooluite.getView_jb(this, "联系人：",
                        // data_3.getContact()));
                        // mLayout.addView(tooluite.getView_jb(this, "规格型号：",
                        // tooluite.getDate(data_3.getUploadTime(), "T")));
                        // mLayout.addView(tooluite.getView_jb(this, "规格型号：",
                        // tooluite.getDate(data_3.getUploadTime(), "T")));
                        // mLayout.addView(tooluite.getView_jb(this, "规格型号：",
                        // tooluite.getDate(data_3.getUploadTime(), "T")));

                        break;
                    case 2300694:
                        TB_FoodPurchasedRetrun.Data data_4 = (TB_FoodPurchasedRetrun.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        textView.setText("基本信息");
                        // 食品名称 单位 ） 数据更新时间
                        mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "企业名称：",
                                data != null ? data.getFirmName() : ""));
                        mLayout.addView(tooluite.getView_jb(this, "退货记录编号：",
                                data_4.getReturnNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "供应商：",
                                data_4.getSupplier()));
                        mLayout.addView(tooluite.getView_jb(this, "退货原因：",
                                data_4.getComment()));
                        mLayout.addView(tooluite.getView_jb(this, "采购退货时间：",
                                tooluite.getDate(data_4.getReturnTime(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "操作员：",
                                data_4.getOperator()));
                        // textView.setText("产品信息");

                        break;
                    case 2300695:
                        Tool.changetitle(this, "出库信息");
                        TB_FoodSalses.Data data_5 = (TB_FoodSalses.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        textView.setText("出库信息");
                        // 食品名称 单位 ） 数据更新时间
                        mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "产品名称：",
                                data_5.getProductName()));
                        mLayout.addView(tooluite.getView_jb(this, "生产日期：",
                                tooluite.getDate(data_5.getManufactureDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "有效期至：",
                                tooluite.getDate(data_5.getValidityDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "批号：",
                                data_5.getBillNo()));
                        mLayout.addView(tooluite.getView_jb(this, "规格：",
                                data_5.getSpecifications()));
                        mLayout.addView(tooluite.getView_jb(this, "追溯码：",
                                data_5.getESCode()));
                        mLayout.addView(tooluite.getView_jb(this, "购货者名称：",
                                data_5.getCustomer()));
                        mLayout.addView(tooluite.getView_jb(this, "销售数量：",
                                data_5.getQuantity()));
                        mLayout.addView(tooluite.getView_jb(this, "联系人：",
                                data_5.getContact()));
                        mLayout.addView(tooluite.getView_jb(this, "联系人电话：",
                                data_5.getContactPhone()));
                        break;
                    case 2300696:
                        TB_FoodSalsesReturn.Data data_6 = (TB_FoodSalsesReturn.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        break;
                    case 2300697:
                        // TODO:药品生产库存界面
                        Tool.changetitle(this, "库存详细信息");
                        textView.setText("药品库存信息");
                        TB_FoodInventory.Data data_7 = (TB_FoodInventory.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "产品名称：",
                                data_7.getGenericName()));
                        mLayout.addView(tooluite.getView_jb(this, "规格型号：",
                                data_7.getSpecification()));
                        mLayout.addView(tooluite.getView_jb(this, "库存数量：",
                                data_7.getQuantity()));
                        mLayout.addView(tooluite.getView_jb(this, "计算单位：",
                                data_7.getUnit()));
                        mLayout.addView(tooluite.getView_jb(this, "生产时间：",
                                tooluite.getDate(data_7.getManufactureDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "批号：",
                                data_7.getBatchNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "有效期：",
                                tooluite.getDate(data_7.getExpiredDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "保质期：",
                                data_7.getShelfLife()));
                        mLayout.addView(tooluite.getView_jb(this, "仓库：",
                                data_7.getStoreRoomName()));
                        break;
                }
                break;
            case 2:
                switch (state) {
                    case 2300691:
                        Tool.changetitle(this, "库存详细信息");
                        TB_FoodInventory.Data data_1 = (TB_FoodInventory.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "食品名称：",
                                data_1.getGenericName()));
                        mLayout.addView(tooluite.getView_jb(this, "企业名称：",
                                data_1.getSupplier()));
                        // mLayout.addView(tooluite.getView_jb(this, "上传时间：",
                        // tooluite.getDate(data_1.getUploadTime(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "仓库名称：",
                                data_1.getStoreRoomName()));
                        mLayout.addView(tooluite.getView_jb(this, "生产日期：",
                                tooluite.getDate(data_1.getProductionDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "批次：",
                                data_1.getBatchNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "有效期：",
                                tooluite.getDate(data_1.getExpiredDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "数　　量：",
                                tooluite.getInt(data_1.getQuantity()) + ""));
                        mLayout.addView(tooluite.getView_jb(this, "单　　位：",
                                data_1.getMeasurementUnit()));
                        break;
                    case 2300692:
                        Tool.changetitle(this, "损溢记录");
                        TB_FoodLooseIncome.Data data_2 = (TB_FoodLooseIncome.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        textView.setVisibility(LinearLayout.GONE);
                        // 食品名称 单位 ） 数据更新时间
                        mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "食品名称：",
                                data_2.getGenericName()));
                        mLayout.addView(tooluite.getView_jb(this, "损溢类型：",
                                data_2.getLooseIncomeReason()));
                        mLayout.addView(tooluite.getView_jb(this, "企业名称：",
                                data.getFirmName()));
                        mLayout.addView(tooluite.getView_jb(this, "产品规格：",
                                data_2.getSpecification()));
                        mLayout.addView(tooluite.getView_jb(this, "批　　次：",
                                data_2.getBatchNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "数　　量：", ""
                                + tooluite.getInt(data_2.getQuantity())));
                        mLayout.addView(tooluite.getView_jb(this, "单　　位：",
                                data_2.getMeasurementUnit()));
                        mLayout.addView(tooluite.getView_jb(this, "上报时间：",
                                tooluite.getDate(data_2.getCheckTime(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "生产单位：",
                                data_2.getManufacturer()));
                        mLayout.addView(tooluite.getView_jb(this, "保质期：",
                                data_2.getShelfLife()));
                        mLayout.addView(tooluite.getView_jb(this, "生产日期：",
                                tooluite.getDate(data_2.getProductionDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "有效期(至)：",
                                tooluite.getDate(data_2.getExpiredDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "仓库/货架：",
                                data_2.getStoreRoomName()));
                        mLayout.addView(tooluite.getView_jb(this, "数据更新时间：",
                                tooluite.getDate(data_2.getLocalLastedTime(), "T")));
                        break;
                    case 2300693:
                        Tool.changetitle(this, "采购入库");
                        TB_FoodPurchased.Data data_3 = (TB_FoodPurchased.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        // 企业名称 发票编号 发票时间 供应商 采购时间 入库人员 数据上传时间
                        View mView1 = LayoutInflater.from(this).inflate(
                                R.layout.yp_view, null);
                        TextView textView1 = (TextView) mView1.findViewById(R.id.aa);
                        textView1.getPaint().setFakeBoldText(true);
                        textView1.setText("基本信息");
                        mLayout.addView(mView1);
                        mLayout.addView(tooluite.getView_jb(this, "企业名称：",
                                data.getFirmName()));
                        mLayout.addView(tooluite.getView_jb(this, "发票编号：",
                                data_3.getInvoiceNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "发票时间：",
                                tooluite.getDate(data_3.getInvoiceTime(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "供应商：",
                                data_3.getSupplier()));
                        mLayout.addView(tooluite.getView_jb(this, "采购时间：",
                                tooluite.getDate(data_3.getPurchasedTime(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "入库人员：",
                                data_3.getOperator()));
                        mLayout.addView(tooluite.getView_jb(this, "数据上传时间：",
                                tooluite.getDate(data_3.getUploadTime(), "T")));
                        String url = ToolUtil.DataUrl.FoodGeturl()
                                + ToolUtil.UrlFood.GetPurchasedDetailsByFirmID
                                + "?FirmType=2&purchasedID=" + data_3.getID()
                                + "&FirmID=" + data.getFirmID();
                        HttpHelper.getInstance().service.get(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(String json) {
                                TB_BeveraProcurementDetails tb_BeveraProcurementDetails = ParsonJson
                                        .jsonToBeanObj("{\"Data\":" + json + "}",
                                                TB_BeveraProcurementDetails.class);
                                if (tb_BeveraProcurementDetails != null
                                        && tb_BeveraProcurementDetails.getData().size() > 0) {
                                    View mView = LayoutInflater.from(FoodFirmType.this)
                                            .inflate(R.layout.yp_view, null);
                                    TextView textView = (TextView) mView
                                            .findViewById(R.id.aa);
                                    textView.getPaint().setFakeBoldText(true);
                                    textView.setText("详细信息");
                                    mLayout.addView(mView);
                                    // 票据编号 食品名称 采购数量 单位 规格 批号 生产日期 保质期 过期日期 采购人员
                                    for (TB_BeveraProcurementDetails.Data data : tb_BeveraProcurementDetails
                                            .getData()) {
                                        TypeView typeView = new TypeView(FoodFirmType.this);
                                        typeView.setValue("票据编号：", data.getTicketNumber(),
                                                "食品名称：", data.getGenericName());
                                        typeView.setValue("采购数量：", "" + tooluite.getInt(data.getQuantity()),
                                                "单　　位：", data.getMeasurementUnit());
                                        // typeView.setValue("规　　格：",data.getSpecification(),
                                        // "批　　号：", data.getBatchNumber());
                                        // typeView.setValue("规　　格：","", "批　　号：",
                                        // data.getBatchNumber());
                                        typeView.setValue("批　　号：",
                                                data.getBatchNumber());
                                        typeView.setValue("生产日期：", tooluite.getDate(data.getProductionDate(), "T"), "保 质 期 ：", data.getShelfLife());
                                        typeView.setValue("过期日期：", tooluite.getDate(data.getEffectiveDate(), "T"),
                                                "采购人员：", data.getBuyer());
                                        mLayout.addView(typeView.getmViewLayout());
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        });

                        break;
                    case 2300694:
                        Tool.changetitle(this, "采购退货");
                        TB_FoodPurchasedRetrun.Data data_4 = (TB_FoodPurchasedRetrun.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        textView.setText("基本信息");
                        // 食品名称 单位 ） 数据更新时间
                        mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "企业名称：",
                                data != null ? data.getFirmName() : ""));
                        mLayout.addView(tooluite.getView_jb(this, "退货记录编号：",
                                data_4.getReturnNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "供应商：",
                                data_4.getSupplier()));
                        mLayout.addView(tooluite.getView_jb(this, "退货原因：",
                                data_4.getComment()));
                        mLayout.addView(tooluite.getView_jb(this, "采购退货时间：",
                                tooluite.getDate(data_4.getReturnTime(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "经办人：",
                                data_4.getOperator()));

                        String urlreturn = ToolUtil.DataUrl.FoodGeturl()
                                + ToolUtil.UrlFood.GetPurchasedReturnDetailsByFirmID
                                + "?FirmType=2&returnID=" + data_4.getID() + "&FirmID="
                                + data.getFirmID();
                        HttpHelper.getInstance().service.get(urlreturn).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(String json) {
                                TB_ReturnPurchased tb_BeveraProcurementDetails = ParsonJson.jsonToBeanObj("{\"Data\":" + json + "}", TB_ReturnPurchased.class);
                                if (tb_BeveraProcurementDetails != null && tb_BeveraProcurementDetails.getData().size() > 0) {
                                    View mView = LayoutInflater.from(FoodFirmType.this).inflate(R.layout.yp_view, null);
                                    TextView textView = (TextView) mView.findViewById(R.id.aa);
                                    textView.getPaint().setFakeBoldText(true);
                                    textView.setText("详细信息");
                                    mLayout.addView(mView);
                                    // 食品名称 退货数量 单位 批号 供应商 生产日期 保质期 过期日期
                                    for (TB_ReturnPurchased.Data data : tb_BeveraProcurementDetails.getData()) {
                                        TypeView typeView = new TypeView(FoodFirmType.this);
                                        typeView.setValue("食品名称：", data.getGenericName(), "退货数量：", tooluite.getInt(data.getQuantity()) + "");
                                        typeView.setValue("单　　位：", data.getMeasurementUnit(), "批　　号：", data.getBatchNumber());
                                        typeView.setValue("供 应 商：", data.getSupplierName(), "生产日期：", tooluite.getDate(data.getProductionDate(), "T"));
                                        typeView.setValue("保 质 期：", data.getShelfLife(), "过期日期：", tooluite.getDate(data.getExpiredDate(), "T"));
                                        mLayout.addView(typeView.getmViewLayout());
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        });
                        break;
                    case 2300695:
                        Tool.changetitle(this, "销售出库");
                        TB_FoodSalses.Data data_5 = (TB_FoodSalses.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        textView.setText("基本信息");
                        // 食品名称 单位 ） 数据更新时间
                        mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "企业名称：",
                                data.getFirmName()));
                        mLayout.addView(tooluite.getView_jb(this, "销售单号：",
                                data_5.getLocalCode()));
                        mLayout.addView(tooluite.getView_jb(this, "销售时间：",
                                tooluite.getDate(data_5.getSaleTime(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "销售人员：",
                                data_5.getSalesStaff()));
                        mLayout.addView(tooluite.getView_jb(this, "数据上传时间：",
                                tooluite.getDate(data_5.getUploadTime(), "T")));
                        String url_5 = ToolUtil.DataUrl.FoodGeturl()
                                + ToolUtil.UrlFood.GetSalesDetailsByFirmID
                                + "?FirmType=2&salesID=" + data_5.getID() + "&FirmID="
                                + data.getFirmID();
                        HttpHelper.getInstance().service.get(url_5).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(String json) {
                                Log.d("json", json);
                                json = "{\"data\":" + json + "}";
                                TB_xiaoshoutuihuo tb_xiaoshoutuihuo = ParsonJson
                                        .jsonToBean(json, TB_xiaoshoutuihuo.class);
                                if (tb_xiaoshoutuihuo != null
                                        && tb_xiaoshoutuihuo.getData().size() > 0) {
                                    View mView = LayoutInflater.from(FoodFirmType.this)
                                            .inflate(R.layout.yp_view, null);
                                    TextView textView = (TextView) mView
                                            .findViewById(R.id.aa);
                                    textView.getPaint().setFakeBoldText(true);
                                    textView.setText("详细信息");
                                    mLayout.addView(mView);
                                    for (TB_xiaoshoutuihuo.data data : tb_xiaoshoutuihuo
                                            .getData()) {
                                        TypeView typeView = new TypeView(
                                                FoodFirmType.this);
                                        typeView.setValue(
                                                "食品名称：",
                                                data.getGenericName(),
                                                "数量：",
                                                ""
                                                        + tooluite.getInt(data
                                                        .getQuantity()));
                                        typeView.setValue("单　　位：",
                                                data.getMeasurementUnit());
                                        typeView.setValue(
                                                "批　　号：",
                                                data.getBatchNumber(),
                                                "生产日期",
                                                tooluite.getDate(
                                                        data.getProductionDate(), "T"));
                                        typeView.setValue("保 质 期 ：", data
                                                .getShelfLife(), "有效期", tooluite
                                                .getDate(data.getExpiredDate(), "T"));
                                        typeView.setValue("供 应 商 ：", data.getSupplier());
                                        mLayout.addView(typeView.getmViewLayout());
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        });
                        break;
                    case 2300696:
                        TB_FoodSalsesReturn.Data data_6 = (TB_FoodSalsesReturn.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        Tooluite fsrtooluite = new Tooluite();
                        Tool.changetitle(this, "退货信息");
                        textView.setText("基本信息");
                        // 食品名称 单位 ） 数据更新时间
                        mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "食品名称：",
                                data.getFirmName()));
                        mLayout.addView(tooluite.getView_jb(this, "销售退货单号：",
                                data_6.getReturnOrderNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "客户：",
                                data_6.getClientName()));
                        mLayout.addView(tooluite.getView_jb(this, "销售退货时间：",
                                fsrtooluite.getDate(data_6.getReturnTime(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "经办人：",
                                data_6.getComment()));
                        mLayout.addView(tooluite.getView_jb(this, "数据上传时间：",
                                fsrtooluite.getDate(data_6.getUploadTime(), "T")));
                        String url_6 = ToolUtil.DataUrl.FoodGeturl()
                                + ToolUtil.UrlFood.GetSalesReturnDetailsByFirmID
                                + "?FirmType=2&returnID=" + data_6.getID() + "&FirmID="
                                + data.getFirmID();
                        HttpHelper.getInstance().service.get(url_6).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(String json) {
                                json = "{\"data\":" + json + "}";
                                TB_xiaoshoutuihuo tb_xiaoshoutuihuo = ParsonJson.jsonToBean(json, TB_xiaoshoutuihuo.class);
                                if (tb_xiaoshoutuihuo != null && tb_xiaoshoutuihuo.getData().size() > 0) {
                                    View mView = LayoutInflater.from(FoodFirmType.this).inflate(R.layout.yp_view, null);
                                    TextView textView = (TextView) mView.findViewById(R.id.aa);
                                    textView.getPaint().setFakeBoldText(true);
                                    textView.setText("详细信息");
                                    mLayout.addView(mView);
                                    for (TB_xiaoshoutuihuo.data data : tb_xiaoshoutuihuo.getData()) {
                                        TypeView typeView = new TypeView(
                                                FoodFirmType.this);
                                        typeView.setValue("食品名称：", data.getGenericName(), "数量：", "" + tooluite.getInt(data.getQuantity()));
                                        typeView.setValue("单　　位：", data.getMeasurementUnit(), "批　　号：", data.getBatchNumber());
                                        typeView.setValue(" 供 应 商 ：", data.getSupplierName(), "生产日期", tooluite.getDate(data.getProductionDate(), "T"));
                                        typeView.setValue(" 保 质 期 ：", data.getShelfLife(), "过期日期", tooluite.getDate(data.getExpiredDate(), "T"));
                                        mLayout.addView(typeView.getmViewLayout());
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        });

                        break;
                    case 2300697:
                        // TODO:食品流通库存界面
                        Tool.changetitle(this, "库存详细信息");
                        textView.setText("食品库存信息");
                        TB_FoodInventory.Data data_7 = (TB_FoodInventory.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        mLayout.addView(mView);

                        mLayout.addView(tooluite.getView_jb(this, "企业名称：",
                                data != null ? data.getFirmName() : ""));
                        mLayout.addView(tooluite.getView_jb(this, "食品名称：",
                                data_7.getGenericName()));
                        mLayout.addView(tooluite.getView_jb(this, "数　　量：",
                                tooluite.getInt(data_7.getQuantity()) + ""));
                        mLayout.addView(tooluite.getView_jb(this, "单　　位：",
                                data_7.getMeasurementUnit()));
                        mLayout.addView(tooluite.getView_jb(this, "规格型号：",
                                data_7.getSpecification()));
                        mLayout.addView(tooluite.getView_jb(this, "批　　次：",
                                data_7.getBatchNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "供   应  商：",
                                data_7.getSupplier()));
                        // mLayout.addView(tooluite.getView_jb(this, "上传时间：",
                        // tooluite.getDate(data_1.getUploadTime(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "仓库名称：",
                                data_7.getStoreRoomName()));
                        mLayout.addView(tooluite.getView_jb(this, "生产日期：",
                                tooluite.getDate(data_7.getProductionDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "保  质   期：",
                                data_7.getShelfLife()));
                        mLayout.addView(tooluite.getView_jb(this, "有效期至：",
                                tooluite.getDate(data_7.getExpiredDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "数据更新时间：",
                                tooluite.getDate(data_7.getUploadTime(), "T")));
                        break;
                }

                break;
            case 4:
                switch (state) {
                    case 2300691:
                        textView.setText("原辅料库存信息");
                        Tool.changetitle(this, "库存详细信息");
                        TB_FoodInventory.Data data_1 = (TB_FoodInventory.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "原辅料名称：",
                                data_1.getGenericName()));
                        mLayout.addView(tooluite.getView_jb(this, "规格型号：",
                                data_1.getSpecification()));
                        mLayout.addView(tooluite.getView_jb(this, "库存数量：",
                                tooluite.getInt(data_1.getQuantity()) + ""));
                        mLayout.addView(tooluite.getView_jb(this, "计量单位：",
                                data_1.getUnit()));
                        mLayout.addView(tooluite.getView_jb(this, "生产时间：",
                                tooluite.getDate(data_1.getManufactureDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "批号：",
                                data_1.getBatchNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "有效期至：",
                                tooluite.getDate(data_1.getExpiredDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "保质期：",
                                data_1.getShelfLife()));
                        mLayout.addView(tooluite.getView_jb(this, "仓库名称：",
                                data_1.getStoreRoomName()));

                        break;
                    case 2300692:
                        Tool.changetitle(this, "损溢记录");
                        TB_FoodLooseIncome.Data data_2 = (TB_FoodLooseIncome.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        textView.setText("基本信息");
                        // 食品名称 单位 ） 数据更新时间
                        // mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "损溢日期：",
                                tooluite.getDate(data_2.getLooseIncomeDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "原辅料名称：",
                                data_2.getGenericName()));
                        mLayout.addView(tooluite.getView_jb(this, "包装规格：",
                                data_2.getSpecification()));
                        mLayout.addView(tooluite.getView_jb(this, "生产日期：",
                                tooluite.getDate(data_2.getManufactureDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "批　　次：",
                                data_2.getBatchNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "有效期至：",
                                tooluite.getDate(data_2.getExpiredDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "保质期：",
                                data_2.getShelfLife()));
                        mLayout.addView(tooluite.getView_jb(this, "原库存：",
                                data_2.getOldInventory()));
                        mLayout.addView(tooluite.getView_jb(this, "实际库存：",
                                data_2.getFactInventory()));
                        mLayout.addView(tooluite.getView_jb(this, "损溢原因：",
                                data_2.getLooseIncomeReason()));
                        mLayout.addView(tooluite.getView_jb(this, "操作人：",
                                data_2.getOperUser()));
                        mLayout.addView(tooluite.getView_jb(this, "审核人：",
                                data_2.getAuditUser()));

                        break;
                    case 2300693:

                        Tool.changetitle(this, "入库信息");
                        TB_FoodPurchased.Data data_3 = (TB_FoodPurchased.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        // 数据上传时间
                        View mView1 = LayoutInflater.from(this).inflate(
                                R.layout.yp_view, null);
                        TextView textView1 = (TextView) mView1.findViewById(R.id.aa);
                        textView1.getPaint().setFakeBoldText(true);
                        textView1.setText("基本信息");
                        mLayout.addView(mView1);
                        mLayout.addView(tooluite.getView_jb(this, "进货日期：",
                                tooluite.getDate(data_3.getPurchaseDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "货物名称：",
                                data_3.getProductName()));
                        mLayout.addView(tooluite.getView_jb(this, "经手人：",
                                data_3.getOperator()));
                        mLayout.addView(tooluite.getView_jb(this, "上传时间",
                                tooluite.getDate(data_3.getUploadTime(), "T")));
                        // mLayout.addView(tooluite.getView_jb(this,
                        // "上传时间：",tooluite.getDate(data_3.getUploadTime(), "T")));
                        View mView2 = LayoutInflater.from(this).inflate(
                                R.layout.yp_view, null);
                        TextView textView2 = (TextView) mView2.findViewById(R.id.aa);
                        textView2.getPaint().setFakeBoldText(true);
                        textView2.setText("原辅料信息");
                        // 食品名称 单位 ） 数据更新时间
                        mLayout.addView(mView2);
                        mLayout.addView(tooluite.getView_jb(this, "原辅料名称：",
                                data_3.getProductName()));
                        mLayout.addView(tooluite.getView_jb(this, "生产日期：",
                                tooluite.getDate(data_3.getManufactureDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "批号：",
                                data_3.getBatchNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "有效期至：",
                                tooluite.getDate(data_3.getValidityDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "保质期：",
                                data_3.getShelfLife()));
                        mLayout.addView(tooluite.getView_jb(this, "规格型号：",
                                data_3.getSpecifications()));
                        mLayout.addView(tooluite.getView_jb(this, "数量：",
                                data_3.getQuantity()));
                        // mLayout.addView(tooluite.getView_jb(this, "追溯码：",
                        // data_3.getTraceCode()));
                        View mView3 = LayoutInflater.from(this).inflate(
                                R.layout.yp_view, null);
                        TextView textView3 = (TextView) mView3.findViewById(R.id.aa);
                        textView3.getPaint().setFakeBoldText(true);
                        textView3.setText("原辅料来源");
                        // 食品名称 单位 ） 数据更新时间
                        mLayout.addView(mView3);
                        mLayout.addView(tooluite.getView_jb(this, "生产单位：",
                                data_3.getSupplierName()));
                        mLayout.addView(tooluite.getView_jb(this, "联系人：",
                                data_3.getContact()));
                        mLayout.addView(tooluite.getView_jb(this, "联系电话：",
                                data_3.getContactPhone()));

                        break;
                    case 2300694:
                        TB_FoodPurchasedRetrun.Data data_4 = (TB_FoodPurchasedRetrun.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        textView.setText("基本信息");
                        // 食品名称 单位 ） 数据更新时间
                        mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "企业名称：",
                                data != null ? data.getFirmName() : ""));
                        mLayout.addView(tooluite.getView_jb(this, "退货记录编号：",
                                data_4.getReturnNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "供应商：",
                                data_4.getSupplier()));
                        mLayout.addView(tooluite.getView_jb(this, "退货原因：",
                                data_4.getComment()));
                        mLayout.addView(tooluite.getView_jb(this, "采购退货时间：",
                                tooluite.getDate(data_4.getReturnTime(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "操作员：",
                                data_4.getOperator()));
                        // textView.setText("产品信息");

                        break;
                    case 2300695:
                        Tool.changetitle(this, "出库信息");
                        TB_FoodSalses.Data data_5 = (TB_FoodSalses.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        textView.setText("出库信息");
                        // 食品名称 单位 ） 数据更新时间
                        mLayout.addView(mView);
                        mLayout.addView(tooluite.getView_jb(this, "产品名称：",
                                data_5.getProductName()));
                        mLayout.addView(tooluite.getView_jb(this, "生产日期：",
                                tooluite.getDate(data_5.getManufactureDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "有效期至：",
                                tooluite.getDate(data_5.getValidityDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "批号：",
                                data_5.getBillNo()));
                        mLayout.addView(tooluite.getView_jb(this, "规格：",
                                data_5.getSpecifications()));
                        mLayout.addView(tooluite.getView_jb(this, "追溯码：",
                                data_5.getESCode()));
                        mLayout.addView(tooluite.getView_jb(this, "购货者名称：",
                                data_5.getCustomer()));
                        mLayout.addView(tooluite.getView_jb(this, "销售数量：",
                                data_5.getQuantity()));
                        mLayout.addView(tooluite.getView_jb(this, "联系人：",
                                data_5.getContact()));
                        mLayout.addView(tooluite.getView_jb(this, "联系人电话：",
                                data_5.getContactPhone()));
                        String url_6 = ToolUtil.DataUrl.FoodGeturl()
                                + ToolUtil.UrlFood.GetSalesDetailsByFirmID
                                + "?FirmType=4&returnID=" + data_5.getID() + "&FirmID="
                                + data.getFirmID();
                        HttpHelper.getInstance().service.get(url_6).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(String json) {
                                json = "{\"data\":" + json + "}";
                                TB_xiaoshoutuihuo tb_xiaoshoutuihuo = ParsonJson
                                        .jsonToBean(json, TB_xiaoshoutuihuo.class);
                                if (tb_xiaoshoutuihuo != null
                                        && tb_xiaoshoutuihuo.getData().size() > 0) {
                                    View mView = LayoutInflater.from(FoodFirmType.this)
                                            .inflate(R.layout.yp_view, null);
                                    TextView textView = (TextView) mView
                                            .findViewById(R.id.aa);
                                    textView.getPaint().setFakeBoldText(true);
                                    textView.setText("详细信息");
                                    mLayout.addView(mView);
                                    for (TB_xiaoshoutuihuo.data data : tb_xiaoshoutuihuo
                                            .getData()) {
                                        TypeView typeView = new TypeView(FoodFirmType.this);
                                        typeView.setValue("食品名称：", data.getGenericName(), "数量：", "" +
                                                tooluite.getInt(data.getQuantity()));
                                        typeView.setValue("单　　位：", data.getMeasurementUnit(), "批　　号：",
                                                data.getBatchNumber());
                                        typeView.setValue(" 供 应 商 ：", data.getSupplierName(), "生产日期",
                                                tooluite.getDate(data.getProductionDate(), "T"));
                                        typeView.setValue(" 保 质 期 ：", data.getShelfLife(), "过期日期",
                                                tooluite.getDate(data.getExpiredDate(), "T"));
                                        mLayout.addView(typeView.getmViewLayout());
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        });
                        break;
                    case 2300697:
                        // TODO:食品生产库存界面
                        TB_FoodInventory.Data data_7 = (TB_FoodInventory.Data) getIntent()
                                .getExtras().getSerializable("data_class_message");
                        mLayout.addView(mView);
                        Tool.changetitle(this, "库存详细信息");
                        textView.setText("食品库存信息");
                        mLayout.addView(tooluite.getView_jb(this, "产品名称：",
                                data_7.getGenericName()));
                        mLayout.addView(tooluite.getView_jb(this, "规格型号：",
                                data_7.getSpecification()));
                        mLayout.addView(tooluite.getView_jb(this, "库存数量：",
                                data_7.getQuantity()));
                        mLayout.addView(tooluite.getView_jb(this, "计算单位：",
                                data_7.getUnit()));
                        mLayout.addView(tooluite.getView_jb(this, "生产时间：",
                                tooluite.getDate(data_7.getManufactureDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "批号：",
                                data_7.getBatchNumber()));
                        mLayout.addView(tooluite.getView_jb(this, "有效期：",
                                tooluite.getDate(data_7.getExpiredDate(), "T")));
                        mLayout.addView(tooluite.getView_jb(this, "保质期：",
                                data_7.getShelfLife()));
                        mLayout.addView(tooluite.getView_jb(this, "仓库：",
                                data_7.getStoreRoomName()));
                        break;
                }
                break;

        }

    }
}
