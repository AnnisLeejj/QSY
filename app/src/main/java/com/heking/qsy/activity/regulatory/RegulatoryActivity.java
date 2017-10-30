package com.heking.qsy.activity.regulatory;
import java.util.ArrayList;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.FirmTypeBean.Data;
import com.heking.qsy.util.Tool;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
public class RegulatoryActivity extends Activity {
	
	private String FIRM_REGULATORY_INFORMATION = "药品库存,查看企业药品库存情况&医疗器械库存,查看企业医疗器械库存情况&采购入库,查看企业药品医疗器械采购入库记录&采购退货,查看企业药品医疗器械退货记录&销售出库,查看企业药品医疗器械销售使用出库记录&销售退货,查看企业药品医疗器械销售使用退货记录&药械养护,查看企业药品医疗器械养护记录&质量验收,查看企业库房药品医疗器械验收记录&报损报溢记录,查看企业报损报溢记录&处方记录,查看企业开具处方记录&不良反应监测,查看不良反应监测记录&温度湿度监测,查看企业库房温度湿度记录&特殊药品销售,查看企业特殊药品销售记录&其他类型入库,查看企业采购外的入库记录&其他类型出库,查看企业销售外的出库记录";
	private String FOOD_COMPANY_DETAILS = "食品库存,查看企业食品库存情况&采购入库,查看企业食品采购入库记录&采购退货,查看企业食品退货记录&销售出库,查看企业食品销售出库记录&销售退货,查看企业食品销售退货记录&报损报溢记录,查看企业报损报溢记录";
	private String FOOD_COMPANY_DETAILS_SC = "食品库存,查看企业食品库存情况&原辅料库存,查看企业原辅料库存情况&采购入库,查看企业食品采购入库情况&销售出库,查看企业食品的销售情况&报损报溢记录,查看企业报损报溢情况";//&销售退货,查看企业的销售退货记录
	private String DRUG_COMPANY_DETAILS_SC = "药品库存,查看企业药品库存情况&原辅料库存,查看企业原辅料库存情况&采购入库,查看企业药品采购入库情况&销售出库,查看企业药品的销售情况&报损报溢记录,查看企业报损报溢情况";//&销售退货,查看企业的销售退货记录";
	private String FOOD_BEVERAGE = "食品采购,查看企业食品采购情况&食品添加剂采购,查看企业食品添加剂采购情况&食品相关产品采购,查看企业食品相关产品采购情况&食品原材料入库,查看企业食品原材料入库情况&食品原材料出库,查看企业食品原材料出库情况&食品添加剂使用台账,查看企业食品添加剂使用台账情况&餐厨废弃物处理,查看餐厨废弃物处理情况&餐饮具清洗消毒,查看餐饮具清洗消毒情况&食品试尝留样,查看食品尝试留样情况";

	private ListView listView;
	private RegulatoryAdapter adapter;
	private ArrayList<RegulatoryUTil> list;
	private FirmTypeBean.Data data;
	private int STATE;
	private boolean IntLT = false;
	private boolean IntSC = false;
	private boolean IntCY = false;
	private RadioButton LTbutton;
	private RadioButton CYbutton;
	private RadioButton SCbutton;
	private RadioGroup mGroup;
	private int INT_to = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (AppContext.THEME) {
			setTheme(R.style.SwitchTheme_1);
		} else {
			setTheme(R.style.SwitchTheme_2);
		}
		setContentView(R.layout.regulatory_activity);
		iniView();
		iniData();
	}
	private void iniView() {
		Tool.endActivity(this);
		data = (Data) getIntent().getExtras().getSerializable("data");
		listView = (ListView) findViewById(R.id.regulatory_list_view);
		STATE = getIntent().getExtras().getInt("State");
		((TextView) findViewById(R.id.name_firm)).setText(data.getFirmName());

		LTbutton = (RadioButton) findViewById(R.id.spjy);
		SCbutton = (RadioButton) findViewById(R.id.splt);
		CYbutton = (RadioButton) findViewById(R.id.cyjy);
		mGroup = (RadioGroup) findViewById(R.id.layout_RadioGroup);
		if (data.getFirmTypeName().contains("食品经营")) {
			
			LTbutton.setVisibility(LinearLayout.VISIBLE);
			IntLT = true;
			INT_to++;
		}
		if (data.getFirmTypeName().contains("食品生产")) {
			
			SCbutton.setVisibility(LinearLayout.VISIBLE);
			IntSC = true;
			INT_to++;
		}
		if (data.getFirmTypeName().contains("餐饮经营")) {
			
			CYbutton.setVisibility(LinearLayout.VISIBLE);
			IntCY = true;
			INT_to++;
		}
		switch (STATE) {
		case 2222:
			list = setData(FOOD_COMPANY_DETAILS, 2);
			if (INT_to < 2) {
				if (IntCY) {
					list = setData(FOOD_BEVERAGE, 3);
				}
				if (IntSC) {
					list = setData(FOOD_COMPANY_DETAILS_SC, 4);
				}
				if (IntLT) {
					list = setData(FOOD_COMPANY_DETAILS, 2);
				}
				mGroup.setVisibility(LinearLayout.GONE);
			}
			break;
		case 3333:
			list = setData(FIRM_REGULATORY_INFORMATION, 111303);
			if(data.getFirmTypeName().contains("药品生产")){
				list=setData(DRUG_COMPANY_DETAILS_SC, 5);
				STATE=2222;
			}
			mGroup.setVisibility(LinearLayout.GONE);
			break;

		}
		adapter = new RegulatoryAdapter(this, list, STATE, data);
	}
	private void iniData() {
		listView.setAdapter(adapter);
		LTbutton.setOnClickListener(ckile);
		SCbutton.setOnClickListener(ckile);
		CYbutton.setOnClickListener(ckile);
	}

	private ArrayList<RegulatoryUTil> setData(String str, int state) {
		ArrayList<RegulatoryUTil> datas = new ArrayList<RegulatoryUTil>();
		if (str != null) {
			String[] strname = str.split("&");
			int i = 0;
			for (String ms : strname) {
				RegulatoryUTil uTil = new RegulatoryUTil();
				String[] typr = ms.split(",");
				uTil.setName(typr[0]);
				uTil.setNametype(typr[1]);
				uTil.setState(state);
				datas.add(uTil);
				i++;
			}

		}
		return datas;
	}
	private onCkile ckile = new onCkile();
	private class onCkile implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.spjy:
				list.clear();
				for (RegulatoryUTil uTil : setData(FOOD_COMPANY_DETAILS, 2)) {
					list.add(uTil);
				}
				adapter.notifyDataSetChanged();
				break;
			case R.id.splt:
				list.clear();
				for (RegulatoryUTil uTil : setData(FOOD_COMPANY_DETAILS, 4)) {
					list.add(uTil);
				}
				adapter.notifyDataSetChanged();
				break;
			case R.id.cyjy:
				list.clear();
				for (RegulatoryUTil uTil : setData(FOOD_BEVERAGE, 3)) {
					list.add(uTil);
				}
				adapter.notifyDataSetChanged();
				break;
			}
		}
	}
}
