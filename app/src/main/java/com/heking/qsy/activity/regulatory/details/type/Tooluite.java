package com.heking.qsy.activity.regulatory.details.type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.Food.tab.Details.TB_FoodPurchasedDetails;
import com.heking.qsy.activity.regulatory.Food.tab.Details.TB_FoodPurchasedReturnDetails;
import com.heking.qsy.activity.regulatory.Food.tab.Details.TB_FoodSalesDetails;
import com.heking.qsy.activity.regulatory.Food.tab.Details.TB_FoodSalesReturnDetails;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class Tooluite {
	public  View getView_jb(Context context, String name, String viual) {
		View mView = LayoutInflater.from(context).inflate(
				R.layout.firm_data_type, null);
		((TextView) mView.findViewById(R.id.name)).setText(name);
		;
		((TextView) mView.findViewById(R.id.viual)).setText(viual);
		return mView;
	}
	public  View getView_yx(Context context,TB_yaopingType.Data cl) {
		View mView = LayoutInflater.from(context).inflate(
				R.layout.yao_pin_ji_ben_xin_xi, null);
		((TextView)mView.findViewById(R.id.a)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.b)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.c)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.d)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.e)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.f)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.g)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.h)).getPaint().setFakeBoldText(true);
		
		
		((TextView)mView.findViewById(R.id.yaopinmingcheng)).setText(cl.getName());
		((TextView)mView.findViewById(R.id.leixing)).setText(cl.getIsMedicine().equals("true")?"药品":"医疗器械");
		((TextView)mView.findViewById(R.id.shuliang)).setText(cl.getQuantity());
		((TextView)mView.findViewById(R.id.pihao)).setText(cl.getBatchNumber());
		((TextView)mView.findViewById(R.id.jianguanma)).setText(cl.getElectronicSupervisionCode()==null?"":cl.getElectronicSupervisionCode());
		((TextView)mView.findViewById(R.id.gongyingshang)).setText(cl.getSupplierName());
		((TextView)mView.findViewById(R.id.shengcahnriqi)).setText(getDate(cl.getProductionDate()));
		((TextView)mView.findViewById(R.id.guoqiriqi)).setText(getDate(cl.getExpiredDate()));
		
		
		return mView;
	}
	public  View getView_yx(Context context,TB_yaopingType.Data cl,boolean s) {
		View mView = LayoutInflater.from(context).inflate(
				R.layout.yao_pin_ji_ben_xin_xi, null);
		((TextView)mView.findViewById(R.id.a)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.b)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.c)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.d)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.e)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.f)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.g)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.h)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.yaopinmingcheng)).setText(cl.getMedicineName());
		((TextView)mView.findViewById(R.id.leixing)).setText(cl.getIsMedicine().equals("true")?"药品":"医疗器械");
		((TextView)mView.findViewById(R.id.shuliang)).setText(cl.getQuantity()==null||cl.getQuantity().equals("")?0+"":cl.getQuantity());
		((TextView)mView.findViewById(R.id.pihao)).setText(cl.getBatchNumber());
		((TextView)mView.findViewById(R.id.jianguanma)).setText(cl.getElectronicSupervisionCode()==null?"":cl.getElectronicSupervisionCode());
		((TextView)mView.findViewById(R.id.gongyingshang)).setText(cl.getSupplierName());
		((TextView)mView.findViewById(R.id.shengcahnriqi)).setText(getDate(cl.getProductionDate()));
		((TextView)mView.findViewById(R.id.guoqiriqi)).setText(getDate(cl.getExpiredDate()));
		
		
		return mView;
	}
public String getDate(String arg0){
if(arg0!=null&&!arg0.equals("")){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(arg0);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return "";
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
		if(date==null){
			return "";
		}
		 String med=sdf2.format(date);
		 return med;
}else{
	return "";
}
	}
public String getDate(String arg0,String mes){
	String[] mess;
if(arg0!=null){
	 mess=arg0.split(mes);
}else{
	return "";
}
	if(arg0!=null&&!arg0.equals("")){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(mess[0]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return "";
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
		if(date==null){
			return "";
		}
		String med=sdf2.format(date);
		return med;
	}else{
		return "";
	}
}

public String getInt(String str){
	try{
	Double dou=	Double.parseDouble(str);
	int sl=dou.intValue();
	return sl+"";
	}catch(Exception e){
		return "";
	}
}

	public  View getView_yx(Context context,TB_FoodSalesDetails.Data cl) {
		View mView = LayoutInflater.from(context).inflate(
				R.layout.yao_pin_ji_ben_xin_xi, null);
		((TextView)mView.findViewById(R.id.a)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.b)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.c)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.d)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.e)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.f)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.g)).getPaint().setFakeBoldText(true);
		((TextView)mView. findViewById(R.id.h)).getPaint().setFakeBoldText(true);
		
		((TextView)mView.findViewById(R.id.yaopinmingcheng)).setText(cl.getGenericName());
	//	((TextView)mView.findViewById(R.id.leixing)).setText(cl.getIsMedicine().equals("true")?"药品":"医疗器械");
		((TextView)mView.findViewById(R.id.shuliang)).setText(cl.getQuantity());
		((TextView)mView.findViewById(R.id.pihao)).setText(cl.getBatchNumber());
		((TextView)mView.findViewById(R.id.jianguanma)).setText(cl.getSellingPrice());
//		((TextView)mView.findViewById(R.id.gongyingshang)).setText(cl.getSupplierName());
		((TextView)mView.findViewById(R.id.shengcahnriqi)).setText(getDate(cl.getProductionDate()));
		((TextView)mView.findViewById(R.id.guoqiriqi)).setText(getDate(cl.getExpiredDate()));
		
		
		return mView;
}
	public  View getView_yx(Context context,TB_FoodSalesReturnDetails.Data cl) {
		View mView = LayoutInflater.from(context).inflate(
				R.layout.yao_pin_ji_ben_xin_xi, null);
		((TextView)mView.findViewById(R.id.a)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.b)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.c)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.d)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.e)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.f)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.g)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.h)).getPaint().setFakeBoldText(true);
//		((TextView)mView.findViewById(R.id.yaopinmingcheng)).setText(cl.getName());
//		((TextView)mView.findViewById(R.id.leixing)).setText(cl.getIsMedicine().equals("true")?"药品":"医疗器械");
		((TextView)mView.findViewById(R.id.shuliang)).setText(cl.getQuantity());
		((TextView)mView.findViewById(R.id.pihao)).setText(cl.getBatchNumber());
//		((TextView)mView.findViewById(R.id.jianguanma)).setText(cl.getElectronicSupervisionCode()==null?"":cl.getElectronicSupervisionCode());
		((TextView)mView.findViewById(R.id.gongyingshang)).setText(cl.getSupplierName());
		((TextView)mView.findViewById(R.id.shengcahnriqi)).setText(getDate(cl.getProductionDate()));
		((TextView)mView.findViewById(R.id.guoqiriqi)).setText(getDate(cl.getExpiredDate()));
		
		
		return mView;
	}
	public  View getView_yx(Context context,TB_FoodPurchasedDetails.Data cl) {
		View mView = LayoutInflater.from(context).inflate(
				R.layout.yao_pin_ji_ben_xin_xi, null);
		((TextView)mView.findViewById(R.id.a)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.b)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.c)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.d)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.e)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.f)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.g)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.h)).getPaint().setFakeBoldText(true);
		
		((TextView)mView.findViewById(R.id.a)).setText("");
		((TextView)mView.findViewById(R.id.b)).setText("");
		((TextView)mView.findViewById(R.id.c)).setText("");
		((TextView)mView.findViewById(R.id.d)).setText("");
		((TextView)mView.findViewById(R.id.e)).setText("");
		((TextView)mView.findViewById(R.id.f)).setText("");
		((TextView)mView.findViewById(R.id.g)).setText("");
		((TextView)mView.findViewById(R.id.h)).setText("");
		
		
		
//		((TextView)mView.findViewById(R.id.yaopinmingcheng)).setText(cl.getName());
//		((TextView)mView.findViewById(R.id.leixing)).setText(cl.getIsMedicine().equals("true")?"药品":"医疗器械");
		((TextView)mView.findViewById(R.id.shuliang)).setText(cl.getQuantity());
		((TextView)mView.findViewById(R.id.pihao)).setText(cl.getBatchNumber());
//		((TextView)mView.findViewById(R.id.jianguanma)).setText(cl.getElectronicSupervisionCode()==null?"":cl.getElectronicSupervisionCode());
		((TextView)mView.findViewById(R.id.gongyingshang)).setText(cl.getSupplierName());
		((TextView)mView.findViewById(R.id.shengcahnriqi)).setText(getDate(cl.getProductionDate()));
//		((TextView)mView.findViewById(R.id.guoqiriqi)).setText(getDate(cl.getExpiredDate()));
		
		
		return mView;
	}
	public  View getView_yx(Context context,TB_FoodPurchasedReturnDetails.Data cl) {
		View mView = LayoutInflater.from(context).inflate(
				R.layout.yao_pin_ji_ben_xin_xi, null);
		((TextView)mView.findViewById(R.id.a)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.b)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.c)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.d)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.e)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.f)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.g)).getPaint().setFakeBoldText(true);
		((TextView)mView.findViewById(R.id.h)).getPaint().setFakeBoldText(true);
//		((TextView)mView.findViewById(R.id.yaopinmingcheng)).setText(cl.getName());
//		((TextView)mView.findViewById(R.id.leixing)).setText(cl.getIsMedicine().equals("true")?"药品":"医疗器械");
		((TextView)mView.findViewById(R.id.shuliang)).setText(cl.getQuantity());
		((TextView)mView.findViewById(R.id.pihao)).setText(cl.getBatchNumber());
//		((TextView)mView.findViewById(R.id.jianguanma)).setText(cl.getElectronicSupervisionCode()==null?"":cl.getElectronicSupervisionCode());
		((TextView)mView.findViewById(R.id.gongyingshang)).setText(cl.getSupplierName());
		((TextView)mView.findViewById(R.id.shengcahnriqi)).setText(getDate(cl.getProductionDate()));
		((TextView)mView.findViewById(R.id.guoqiriqi)).setText(getDate(cl.getExpiredDate()));
		return mView;
	}


}
