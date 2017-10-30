package com.heking.qsy.activity.regulatory;

import java.util.ArrayList;

import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.Food.FoodBeveraAdditivesActivity;
import com.heking.qsy.activity.regulatory.Food.FoodBeveraAdditivesUsedParameterActivity;
import com.heking.qsy.activity.regulatory.Food.FoodBeveraCleaningAndDisinfectionActivity;
import com.heking.qsy.activity.regulatory.Food.FoodBeveraProcurementActivity;
import com.heking.qsy.activity.regulatory.Food.FoodBeveraRawMaterialInventoryActivity;
import com.heking.qsy.activity.regulatory.Food.FoodBeveraRawMaterialInventoryReturnActivity;
import com.heking.qsy.activity.regulatory.Food.FoodBeveraRelatedProductsActivity;
import com.heking.qsy.activity.regulatory.Food.FoodBeveraTryToSampleActivity;
import com.heking.qsy.activity.regulatory.Food.FoodBeveraWasteDisposalActivity;
import com.heking.qsy.activity.regulatory.Food.FoodInventoryActivity;
import com.heking.qsy.activity.regulatory.Food.KuChunActivity;
import com.heking.qsy.activity.regulatory.Food.FoodLooseIncomeActivity;
import com.heking.qsy.activity.regulatory.Food.FoodPurchasedActivity;
import com.heking.qsy.activity.regulatory.Food.FoodPurchasedReturnActivity;
import com.heking.qsy.activity.regulatory.Food.FoodSalesActivity;
import com.heking.qsy.activity.regulatory.Food.FoodSalesReturnActivity;
import com.heking.qsy.activity.regulatory.details.BaoShunBaoYiActivity;
import com.heking.qsy.activity.regulatory.details.BuLiangFanYinJianCeActivity;
import com.heking.qsy.activity.regulatory.details.ChuFangJiLuActivity;
import com.heking.qsy.activity.regulatory.details.DrugInventoryActivity;
import com.heking.qsy.activity.regulatory.details.MedicalEquipmentInventoryActivity;
import com.heking.qsy.activity.regulatory.details.ProcurementWarehousingActivity;
import com.heking.qsy.activity.regulatory.details.PurchaseReturnActivity;
import com.heking.qsy.activity.regulatory.details.QiTaLXingChuKuActivity;
import com.heking.qsy.activity.regulatory.details.QiTaLeiXingRuKuActivity;
import com.heking.qsy.activity.regulatory.details.SalesOfOutboundActivity;
import com.heking.qsy.activity.regulatory.details.SalesReturnActivity;
import com.heking.qsy.activity.regulatory.details.TeShuYaoPinXiaoShouActivity;
import com.heking.qsy.activity.regulatory.details.WenDuShiDuJianCeActivity;
import com.heking.qsy.activity.regulatory.details.YaoXieMaintenanceActivity;
import com.heking.qsy.activity.regulatory.details.ZhiLiangYanShouActivity;
import com.heking.qsy.util.FirmTypeBean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RegulatoryAdapter extends BaseAdapter {

	private ArrayList<RegulatoryUTil> list;
	private Context context;
	private LayoutInflater mInflater;
	private int state;
	private FirmTypeBean.Data data;

	public RegulatoryAdapter(Context context, ArrayList<RegulatoryUTil> list,
			int state, FirmTypeBean.Data data) {
		this.list = list;
		this.context = context;
		this.state = state;
		this.data = data;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list != null ? list.size() : 0;
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int pronts, View arg1, ViewGroup arg2) {
		Layout layout = null;
		if (layout == null) {

			arg1 = mInflater.inflate(R.layout.view_show, null);
		}
		layout = (Layout) arg1.getTag();

		if (layout == null) {
			layout = new Layout();
			layout.Name = (TextView) arg1.findViewById(R.id.name_biaoti);
			layout.Nmaetype = (TextView) arg1
					.findViewById(R.id.name_biaoti_type);
			layout.button = (LinearLayout) arg1
					.findViewById(R.id.button_layout);

			arg1.setTag(layout);
		}

		layout.cose();
		layout.Name.setText(list.get(pronts).getName());
		layout.Nmaetype.setText(list.get(pronts).getNametype());
		layout.button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				switch (state) {
				case 2222:
					FoodtoActivity(list.get(pronts).getName(), list.get(pronts)
							.getState(), list.get(pronts));
					break;
				case 3333:
					toActivity(list.get(pronts).getName());
					break;
				}
			}
		});
		return arg1;
	}

	/**
	 * 医疗机构 药品流通
	 * 
	 * @param name
	 *            类型名称
	 */
	private void toActivity(String name) {
		String url;
		Bundle bundle = new Bundle();
		Intent intent = new Intent();
		intent.putExtra("Name", name);
		if (name.equals("药品库存")) {
			intent.setClass(context, DrugInventoryActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getKuCun(data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("医疗器械库存")) {
			intent.setClass(context, MedicalEquipmentInventoryActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getKuCun(data.getFirmID(), 1, 20, "", false);
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);

		}
		if (name.equals("采购入库")) {
			intent.setClass(context, ProcurementWarehousingActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getcaiGou(data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);

		}
		if (name.equals("采购退货")) {
			intent.setClass(context, PurchaseReturnActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getcaiGoutuihuo(data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("销售出库")) {
			intent.setClass(context, SalesReturnActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getToURl("get_firm_data_sellOutStock",
							data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("销售退货")) {
			intent.setClass(context, SalesOfOutboundActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getToURl("get_firm_data_sellReturnStock",
							data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("药械养护")) {
			intent.setClass(context, YaoXieMaintenanceActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getToURl("get_firm_data_maintainInfo",
							data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("质量验收")) {
			intent.setClass(context, ZhiLiangYanShouActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getToURl("get_firm_data_qualityChecks",
							data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("报损报溢记录")) {
			intent.setClass(context, BaoShunBaoYiActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getToURl("get_firm_data_decreaseOverflows",
							data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("处方记录")) {
			intent.setClass(context, ChuFangJiLuActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getToURl("get_firm_data_PrescribeRecords",
							data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("不良反应监测")) {
			intent.setClass(context, BuLiangFanYinJianCeActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getToURl("get_firm_data_AdverseReactions",
							data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("温度湿度监测")) {
			intent.setClass(context, WenDuShiDuJianCeActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getToURl("get_firm_data_HumitureRecords",
							data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("特殊药品销售")) {
			intent.setClass(context, TeShuYaoPinXiaoShouActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getToURl("get_firm_data_special_sellOutStock",
							data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("其他类型入库")) {
			intent.setClass(context, QiTaLeiXingRuKuActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getToURl("get_firm_data_other_inStock",
							data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("其他类型出库")) {
			intent.setClass(context, QiTaLXingChuKuActivity.class);
			url = ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")
					+ ToolUtil.getToURl("get_firm_data_other_outStock",
							data.getFirmID(), 1, 20, "");
			// url=ToolUtil.DataUrl.getUrl("http://117.173.38.55:84/Android")+ToolUtil.getKuCun(data.getFirmID());
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	/**
	 * 药品生产，食品生产，食品流通
	 * 
	 * @param name
	 *            类型名称
	 * @param stats
	 *            请求类型【
	 * @1 药品经营
	 * @2 食品经营
	 * @3 餐饮经营
	 * @4 食品生产
	 * @5 药品生产 】
	 * @param uTil
	 *            RegulatoryUTil 类对象
	 */
	private void FoodtoActivity(String name, int stats, RegulatoryUTil uTil) {
		if (stats != 3) {
			String url;
			Bundle bundle = new Bundle();
			Intent intent = new Intent();
			bundle.putSerializable("RegulatoryUTil", uTil);
			bundle.putString("Name", name);
			// TODO://////
			if (name.equals("食品库存")) {
				// url=ToolUtil.DataUrl.FoodGeturl()+ToolUtil.UrlFood.GetInventoryByFirmID+"?"+"firmtype=2"+"FirmID="+data.getFirmID();
				url = ToolUtil.DataUrl.FoodGeturl()
						+ ToolUtil.UrlFood.GetInventoryByFirmID + "?"
						+ "firmtype=" + stats + "&productType=1&FirmID="
						+ data.getFirmID();
				intent.setClass(context, KuChunActivity.class);
				bundle.putString("URL", url);
				bundle.putSerializable("data", data);
				intent.putExtras(bundle);
			}
			if (name.equals("药品库存")) {
				// url=ToolUtil.DataUrl.FoodGeturl()+ToolUtil.UrlFood.GetInventoryByFirmID+"?"+"firmtype=2"+"FirmID="+data.getFirmID();
				url = ToolUtil.DataUrl.FoodGeturl()
						+ ToolUtil.UrlFood.GetInventoryByFirmID + "?"
						+ "firmtype=" + stats + "&productType=1&FirmID="
						+ data.getFirmID();
				intent.setClass(context, KuChunActivity.class);
				bundle.putString("URL", url);
				bundle.putSerializable("data", data);
				intent.putExtras(bundle);
			}
			if (name.equals("原辅料库存")) {
				// url=ToolUtil.DataUrl.FoodGeturl()+ToolUtil.UrlFood.GetInventoryByFirmID+"?"+"firmtype=2"+"FirmID="+data.getFirmID();
				url = ToolUtil.DataUrl.FoodGeturl()
						+ ToolUtil.UrlFood.GetInventoryByFirmID + "?"
						+ "firmtype=" + stats + "&FirmID=" + data.getFirmID();
				intent.setClass(context, FoodInventoryActivity.class);
				bundle.putString("URL", url);
				bundle.putSerializable("data", data);
				intent.putExtras(bundle);
			}
			if (name.equals("报损报溢记录")) {
				// url=ToolUtil.DataUrl.FoodGeturl()+ToolUtil.UrlFood.GetInventoryByFirmID+"?"+"firmtype=2"+"FirmID="+data.getFirmID();
				url = ToolUtil.DataUrl.FoodGeturl()
						+ ToolUtil.UrlFood.GetLooseIncomeByFirmID + "?"
						+ "firmtype=" + stats + "&FirmID=" + data.getFirmID();
				intent.setClass(context, FoodLooseIncomeActivity.class);
				bundle.putString("URL", url);
				bundle.putSerializable("data", data);
				intent.putExtras(bundle);
			}
			if (name.equals("采购入库")) {
				// url=ToolUtil.DataUrl.FoodGeturl()+ToolUtil.UrlFood.GetInventoryByFirmID+"?"+"firmtype=2"+"FirmID="+data.getFirmID();
				url = ToolUtil.DataUrl.FoodGeturl()
						+ ToolUtil.UrlFood.GetPurchasedByFirmID + "?"
						+ "firmtype=" + stats + "&FirmID=" + data.getFirmID();
				intent.setClass(context, FoodPurchasedActivity.class);
				bundle.putString("URL", url);
				bundle.putSerializable("data", data);
				intent.putExtras(bundle);
			}
			if (name.equals("采购退货")) {
				// url=ToolUtil.DataUrl.FoodGeturl()+ToolUtil.UrlFood.GetInventoryByFirmID+"?"+"firmtype=2"+"FirmID="+data.getFirmID();
				url = ToolUtil.DataUrl.FoodGeturl()
						+ ToolUtil.UrlFood.GetPurchasedReturnByFirmID + "?"
						+ "firmtype=" + stats + "&FirmID=" + data.getFirmID();
				intent.setClass(context, FoodPurchasedReturnActivity.class);
				bundle.putString("URL", url);
				bundle.putSerializable("data", data);
				intent.putExtras(bundle);
			}
			if (name.equals("销售出库")) {
				// url=ToolUtil.DataUrl.FoodGeturl()+ToolUtil.UrlFood.GetInventoryByFirmID+"?"+"firmtype=2"+"FirmID="+data.getFirmID();
				url = ToolUtil.DataUrl.FoodGeturl()
						+ ToolUtil.UrlFood.GetSalesByFirmID + "?" + "firmtype="
						+ stats + "&FirmID=" + data.getFirmID();
				intent.setClass(context, FoodSalesActivity.class);
				bundle.putString("URL", url);
				bundle.putSerializable("data", data);
				intent.putExtras(bundle);
			}
			if (name.equals("销售退货")) {
				// url=ToolUtil.DataUrl.FoodGeturl()+ToolUtil.UrlFood.GetInventoryByFirmID+"?"+"firmtype=2"+"FirmID="+data.getFirmID();
				url = ToolUtil.DataUrl.FoodGeturl()
						+ ToolUtil.UrlFood.GetSalesReturnByFirmID + "?"
						+ "firmtype=" + stats + "&FirmID=" + data.getFirmID();
				intent.setClass(context, FoodSalesReturnActivity.class);
				bundle.putString("URL", url);
				bundle.putSerializable("data", data);
				intent.putExtras(bundle);
			}
			context.startActivity(intent);
		} else {
			FoodBeveraGeactivity(name, stats);
		}
	}

	private void FoodBeveraGeactivity(String name, int stats) {

		String url;
		Bundle bundle = new Bundle();
		Intent intent = new Intent();
		bundle.putString("Name", name);
		if (name.equals("食品采购")) {
			url = ToolUtil.DataUrl.FoodGeturl()
					+ ToolUtil.UrlFood.GetPurchasedByFirmID_1 + "&FirmID="
					+ data.getFirmID();
			;
			intent.setClass(context, FoodBeveraProcurementActivity.class);
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("食品添加剂采购")) {
			url = ToolUtil.DataUrl.FoodGeturl()
					+ ToolUtil.UrlFood.GetPurchasedByFirmID_2 + "&FirmID="
					+ data.getFirmID();
			;
			intent.setClass(context, FoodBeveraAdditivesActivity.class);
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("食品相关产品采购")) {
			url = ToolUtil.DataUrl.FoodGeturl()
					+ ToolUtil.UrlFood.GetPurchasedByFirmID_3 + "&FirmID="
					+ data.getFirmID();
			;
			intent.setClass(context, FoodBeveraRelatedProductsActivity.class);
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}

		if (name.equals("食品原材料入库")) {
			url = ToolUtil.DataUrl.FoodGeturl()
					+ ToolUtil.UrlFood.GetFoodMaterialsInByPage + "?"
					+ "FirmID=" + data.getFirmID();
			;
			intent.setClass(context,
					FoodBeveraRawMaterialInventoryActivity.class);
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}

		if (name.equals("食品原材料出库")) {
			url = ToolUtil.DataUrl.FoodGeturl()
					+ ToolUtil.UrlFood.GetFoodMaterialsOutByPage + "?"
					+ "FirmID=" + data.getFirmID();
			;
			intent.setClass(context,
					FoodBeveraRawMaterialInventoryReturnActivity.class);
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}

		if (name.equals("食品添加剂使用台账")) {
			url = ToolUtil.DataUrl.FoodGeturl()
					+ ToolUtil.UrlFood.GetFoodAdditiveUsingByPage + "?"
					+ "FirmID=" + data.getFirmID();
			;
			intent.setClass(context,
					FoodBeveraAdditivesUsedParameterActivity.class);
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("餐厨废弃物处理")) {
			url = ToolUtil.DataUrl.FoodGeturl()
					+ ToolUtil.UrlFood.GetKitchenWasteRecyclingRecord + "?"
					+ "FirmID=" + data.getFirmID();
			intent.setClass(context, FoodBeveraWasteDisposalActivity.class);
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("餐饮具清洗消毒")) {
			url = ToolUtil.DataUrl.FoodGeturl()
					+ ToolUtil.UrlFood.GetTablewareCleaningByPage + "?"
					+ "FirmID=" + data.getFirmID();
			intent.setClass(context,
					FoodBeveraCleaningAndDisinfectionActivity.class);
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		if (name.equals("食品试尝留样")) {
			url = ToolUtil.DataUrl.FoodGeturl()
					+ ToolUtil.UrlFood.GetFoodTasteSampleByPage + "?"
					+ "FirmID=" + data.getFirmID();
			intent.setClass(context, FoodBeveraTryToSampleActivity.class);
			bundle.putString("URL", url);
			bundle.putSerializable("data", data);
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	private class Layout {
		private TextView Name;
		private TextView Nmaetype;
		private LinearLayout button;

		private void cose() {
			Name.setText("");
			Nmaetype.setText("");
		}
	}
}