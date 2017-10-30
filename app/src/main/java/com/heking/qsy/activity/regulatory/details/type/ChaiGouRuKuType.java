package com.heking.qsy.activity.regulatory.details.type;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.JsongDataBean;
import com.heking.qsy.activity.regulatory.tab.TB_CaiGouRuKu;
import com.heking.qsy.activity.regulatory.tab.TB_CaiGouTuiHuo;
import com.heking.qsy.activity.regulatory.tab.TB_QiTaLeiXinRuKu;
import com.heking.qsy.activity.regulatory.tab.TB_TeShuYaoPinXiaoShou;
import com.heking.qsy.activity.regulatory.tab.TB_XiaoShouChuKu;
import com.heking.qsy.activity.regulatory.tab.TB_XiaoShuoTuiHuo;
import com.heking.qsy.activity.regulatory.tab.TB_qitaleixingchuku;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.providers.JSONdata;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.qsy.util.FirmTypeBean.Data;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChaiGouRuKuType extends Activity {
    private String url;
    private WaitDialog dialog;
    private FirmTypeBean.Data data;
    private String name;
    private LinearLayout layout_jb, layout_yx;
    private int statess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.chai_gou_ru_ku_type);

        iniView();
        iniData();
    }

    private void iniView() {
        Tool.endActivity(this);
        statess = getIntent().getExtras().getInt("statess");

        url = getIntent().getStringExtra("keyUrl");
        name = getIntent().getStringExtra("cococo");

        data = (Data) getIntent().getExtras().getSerializable("data");

        layout_jb = (LinearLayout) findViewById(R.id.jibenlayout);
        layout_yx = (LinearLayout) findViewById(R.id.yaoxielayout);
        ((TextView) findViewById(R.id.aa)).getPaint().setFakeBoldText(true);
        ((TextView) findViewById(R.id.bb)).getPaint().setFakeBoldText(true);
    }

    private void iniData() {
        HttpHelper.getInstance().service.get(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                if (json.equals("连接失败")) {
                    Toast.makeText(ChaiGouRuKuType.this, "网络连接失败，请稍后重试!", 500).show();
                } else {
                    JsongDataBean bean = ParsonJson.jsonToBeanObj(json, JsongDataBean.class);
                    TB_yaopingType tb_yaopingType = ParsonJson.jsonToBeanObj("{\"Data\":" + bean.getJsonData() + "}", TB_yaopingType.class);
                    Tooluite tooluite = new Tooluite();
                    for (TB_yaopingType.Data data : tb_yaopingType.getData()) {
                        layout_yx.addView(tooluite.getView_yx(ChaiGouRuKuType.this, data));
                    }
                }
                Log.d("sss", json);
            }

            @Override
            public void onError(Throwable e) {
                dialog.dismiss();
            }
        });
        dialog = new WaitDialog(this);
        dialog.setContent("正在加载...");
        dialog.show();
        switch (statess) {
            case 1310031:
                TB_CaiGouRuKu.data jc_data = (TB_CaiGouRuKu.data) getIntent().getExtras().getSerializable("MESSAGE");
                Tooluite tooluite = new Tooluite();
                Tool.changetitle(this, "入库信息");
                layout_jb.addView(tooluite.getView_jb(this, "企业名称：", jc_data.getFirmName()));
                layout_jb.addView(tooluite.getView_jb(this, "数据上传时间：", tooluite.getDate(jc_data.getUploadTime())));
                layout_jb.addView(tooluite.getView_jb(this, "采购编号：", jc_data.getPurchasedID()));
                layout_jb.addView(tooluite.getView_jb(this, "供应商：", jc_data.getSupplier()));
                layout_jb.addView(tooluite.getView_jb(this, "采购时间：", tooluite.getDate(jc_data.getPurchasedTime())));
                layout_jb.addView(tooluite.getView_jb(this, "经办人：", jc_data.getOperator()));
                break;
            case 1310032:
                TB_CaiGouTuiHuo.data th_data = (TB_CaiGouTuiHuo.data) getIntent().getExtras().getSerializable("MESSAGE");
                Tooluite tol = new Tooluite();
                Tool.changetitle(this, "采购退货信息");
                layout_jb.addView(tol.getView_jb(this, "企业名称：", th_data.getFirmName()));
                layout_jb.addView(tol.getView_jb(this, "数据上传时间：", tol.getDate(th_data.getUploadTime())));
                layout_jb.addView(tol.getView_jb(this, "退货记录编号：", th_data.getReturnNumber()));
                layout_jb.addView(tol.getView_jb(this, "供应商：", th_data.getSupplier()));
                layout_jb.addView(tol.getView_jb(this, "退货原因：", th_data.getReason()));
                layout_jb.addView(tol.getView_jb(this, "退货时间：", tol.getDate(th_data.getReturnTime())));
                layout_jb.addView(tol.getView_jb(this, "经办人：", th_data.getOperator()));
                break;
            case 1310033:
                TB_XiaoShouChuKu.data xsck_data = (com.heking.qsy.activity.regulatory.tab.TB_XiaoShouChuKu.data) getIntent().getExtras().getSerializable("MESSAGE");
                Tooluite tol_xsck = new Tooluite();
                Tool.changetitle(this, "出库信息");
                layout_jb.addView(tol_xsck.getView_jb(this, "企业名称：", xsck_data.getFirmName()));
                layout_jb.addView(tol_xsck.getView_jb(this, "数据上传时间：", tol_xsck.getDate(xsck_data.getUploadTime())));
                layout_jb.addView(tol_xsck.getView_jb(this, "客户：", xsck_data.getClientName()));
                layout_jb.addView(tol_xsck.getView_jb(this, "销售时间：", tol_xsck.getDate(xsck_data.getSalesTime())));
                layout_jb.addView(tol_xsck.getView_jb(this, "经办人：", xsck_data.getOperator()));
                break;
            case 1310034:
                TB_XiaoShuoTuiHuo.data xsth_data = (TB_XiaoShuoTuiHuo.data) getIntent().getExtras().getSerializable("MESSAGE");
                Tooluite tol_xsth = new Tooluite();
                Tool.changetitle(this, "退货信息");
                layout_jb.addView(tol_xsth.getView_jb(this, "企业名称：", xsth_data.getFirmName()));
                layout_jb.addView(tol_xsth.getView_jb(this, "数据上传时间：", tol_xsth.getDate(xsth_data.getUploadTime())));
                layout_jb.addView(tol_xsth.getView_jb(this, "销售单号：", xsth_data.getReturnOrderNumber()));
                layout_jb.addView(tol_xsth.getView_jb(this, "客户：", xsth_data.getClientName()));
                layout_jb.addView(tol_xsth.getView_jb(this, "退货时间：", tol_xsth.getDate(xsth_data.getReturnTime())));
                layout_jb.addView(tol_xsth.getView_jb(this, "经办人：", xsth_data.getOperator()));

                break;
            case 1310035:
                TB_qitaleixingchuku.data xsth_data_1 = (TB_qitaleixingchuku.data) getIntent().getExtras().getSerializable("MESSAGE");
                Tooluite tol_xsth_1 = new Tooluite();
                Tool.changetitle(this, "出库信息");
                layout_jb.addView(tol_xsth_1.getView_jb(this, "企业名称：", xsth_data_1.getFirmName()));
                layout_jb.addView(tol_xsth_1.getView_jb(this, "数据上传时间：", tol_xsth_1.getDate(xsth_data_1.getUploadTime())));
                layout_jb.addView(tol_xsth_1.getView_jb(this, "出库类型：", xsth_data_1.getOutTypeName()));
                layout_jb.addView(tol_xsth_1.getView_jb(this, "出库编号：", xsth_data_1.getOutID()));
                layout_jb.addView(tol_xsth_1.getView_jb(this, "出库时间：", tol_xsth_1.getDate(xsth_data_1.getOutTime(), "T")));
                layout_jb.addView(tol_xsth_1.getView_jb(this, "客户：", xsth_data_1.getClientName()));
                layout_jb.addView(tol_xsth_1.getView_jb(this, "经办人：", xsth_data_1.getOperator()));
                break;
            case 1310036:
                TB_TeShuYaoPinXiaoShou.data txyp_data_1 = (TB_TeShuYaoPinXiaoShou.data) getIntent().getExtras().getSerializable("MESSAGE");
                Tooluite txyptooluite = new Tooluite();
//					
                Tool.changetitle(this, "特殊药品销售信息");
                layout_jb.addView(txyptooluite.getView_jb(this, "企业名称：", txyp_data_1.getFirmName()));
                layout_jb.addView(txyptooluite.getView_jb(this, "销售单号：", txyp_data_1.getSaleOrderNumber()));
                layout_jb.addView(txyptooluite.getView_jb(this, "总数量：", txyp_data_1.getTotalQuantity()));
                layout_jb.addView(txyptooluite.getView_jb(this, "销售时间：", txyptooluite.getDate(txyp_data_1.getSaleTime())));
                layout_jb.addView(txyptooluite.getView_jb(this, "购药人姓名：", txyp_data_1.getPurchaseMan()));
                layout_jb.addView(txyptooluite.getView_jb(this, "购药人性别：", txyp_data_1.getGender()));
                layout_jb.addView(txyptooluite.getView_jb(this, "购药人身份证：", txyp_data_1.getPurchaseIdentity()));
                layout_jb.addView(txyptooluite.getView_jb(this, "年龄：", txyp_data_1.getAge()));
                layout_jb.addView(txyptooluite.getView_jb(this, "电话：", txyp_data_1.getPhoneNumber()));
                layout_jb.addView(txyptooluite.getView_jb(this, "地址：", txyp_data_1.getAddress()));
                layout_jb.addView(txyptooluite.getView_jb(this, "病情：", txyp_data_1.getIll()));
                layout_jb.addView(txyptooluite.getView_jb(this, "诊断：", txyp_data_1.getDiagnose()));
                layout_jb.addView(txyptooluite.getView_jb(this, "购药用途：", txyp_data_1.getHowToUsage()));
                layout_jb.addView(txyptooluite.getView_jb(this, "执业医师：", txyp_data_1.getLicensed()));
//	layout_jb.addView(tol_xsth_1.getView_jb(this, "操作人员：", txyp_data_1.getClientName()));

                break;
            case 1310037:
                TB_QiTaLeiXinRuKu.data qtlxrk_data = (com.heking.qsy.activity.regulatory.tab.TB_QiTaLeiXinRuKu.data) getIntent().getExtras().getSerializable("MESSAGE");
                Tooluite qtlxrk_tool = new Tooluite();
                Tool.changetitle(this, "其他类型入库信息");
                layout_jb.addView(qtlxrk_tool.getView_jb(this, "企业名称：", qtlxrk_data.getFirmName()));
                layout_jb.addView(qtlxrk_tool.getView_jb(this, "数据上传时间：", qtlxrk_tool.getDate(qtlxrk_data.getUploadTime())));
                layout_jb.addView(qtlxrk_tool.getView_jb(this, "入库类型：", qtlxrk_data.getIncomingTypeName()));
                layout_jb.addView(qtlxrk_tool.getView_jb(this, "入库编号：", qtlxrk_data.getIncomingID()));
                layout_jb.addView(qtlxrk_tool.getView_jb(this, "入库时间：", qtlxrk_tool.getDate(qtlxrk_data.getIncomingTime())));
                layout_jb.addView(qtlxrk_tool.getView_jb(this, "供应商：", qtlxrk_data.getSupplier()));
                layout_jb.addView(qtlxrk_tool.getView_jb(this, "经办人：", qtlxrk_data.getOperator()));

                break;
        }
    }
}
