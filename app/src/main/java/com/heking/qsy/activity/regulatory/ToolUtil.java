package com.heking.qsy.activity.regulatory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ToolUtil {
	/**
	 * 详细信息
	 * @param state
	 * @param ID
	 * @return
	 */
	public static String getType(String state,String ID){
		String url="{\"RequestMethod\":\""+state+"\",\"RequestParams\":\"{\\\"ID\\\":\\\""+ID+"\\\"}\"}";
		return getSouSuoURL(url);
	}
	/**
	 * 药品库存请求参数
	 * @param Firmid
	 * @return
	 */
public static String getKuCun(String Firmid,int page,int coun,String mes){
	String json="{\"RequestMethod\":\"get_firm_data_stock\",\"RequestParams\":\"{\\\"count\\\":"+coun+",\\\"currentPage\\\":"+page+",\\\"firmID\\\":\\\""+Firmid+"\\\",\\\"isMedicine\\\":true,\\\"searchFilter\\\":\\\""+mes+"\\\"}\"}";
	            //{"RequestMethod":"get_firm_data_stock","RequestParams":"{\"count\":20,\"currentPage\":1,\"firmID\":\"F000000000000001\",\"isMedicine\":true,\"searchFilter\":\"阿莫西林\"}"}
				//{"RequestMethod":"get_firm_data_stock","RequestParams":"{\"count\":20,\"currentPage\":1,\"firmID\":\"F000000000000001\",\"isMedicine\":true,\"searchFilter\":\"阿莫西林\"}"}
				//{"RequestMethod":"get_firm_data_stock","RequestParams":"{\"count\":20,\"currentPage\":1,\"firmID\":\"F000000000000001\",\"isMedicine\":false,\"searchFilter\":\"\"}"}
	try {
		json=URLEncoder.encode(json, "utf8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return json;
}

/**
 * 医疗器械库存请求参数
 * @param Firmid
 * @return
 */
public static String getKuCun(String Firmid,int page,int coun,String mes,boolean boo){
	String json="{\"RequestMethod\":\"get_firm_data_stock\",\"RequestParams\":\"{\\\"count\\\":"+coun+",\\\"currentPage\\\":"+page+",\\\"firmID\\\":\\\""+Firmid+"\\\",\\\"isMedicine\\\":"+boo+",\\\"searchFilter\\\":\\\""+mes+"\\\"}\"}";
	//{"RequestMethod":"get_firm_data_stock","RequestParams":"{\"count\":20,\"currentPage\":1,\"firmID\":\"F000000000000001\",\"isMedicine\":true,\"searchFilter\":\"阿莫西林\"}"}
	//{"RequestMethod":"get_firm_data_stock","RequestParams":"{\"count\":20,\"currentPage\":1,\"firmID\":\"F000000000000001\",\"isMedicine\":true,\"searchFilter\":\"阿莫西林\"}"}
	//{"RequestMethod":"get_firm_data_stock","RequestParams":"{\"count\":20,\"currentPage\":1,\"firmID\":\"F000000000000001\",\"isMedicine\":false,\"searchFilter\":\"\"}"}
	try {
		json=URLEncoder.encode(json, "utf8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return json;
}
/**
 *采购入库
 * @param Firmid
 * @return
 */
public static String getcaiGou(String Firmid,int page,int coun,String mes){
	String json="{\"RequestMethod\":\"get_firm_data_purchase_inStock\",\"RequestParams\":\"{\\\"count\\\":"+coun+",\\\"currentPage\\\":"+page+",\\\"firmID\\\":\\\""+Firmid+"\\\",\\\"searchFilter\\\":\\\""+mes+"\\\"}\"}";
			   //{"RequestMethod":"get_firm_data_purchase_returnStock","RequestParams":"{\"count\":20,\"currentPage\":1,\"firmID\":\"F000000000000001\",\"searchFilter\":\"\"}"}
	try {
		json=URLEncoder.encode(json, "utf8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return json;
}
/**
 *采购退货
 * @param Firmid
 * @return
 */
public static String getcaiGoutuihuo(String Firmid,int page,int coun,String mes){
	String json="{\"RequestMethod\":\"get_firm_data_purchase_returnStock\",\"RequestParams\":\"{\\\"count\\\":"+coun+",\\\"currentPage\\\":"+page+",\\\"firmID\\\":\\\""+Firmid+"\\\",\\\"searchFilter\\\":\\\""+mes+"\\\"}\"}";
	//{"RequestMethod":"get_firm_data_purchase_returnStock","RequestParams":"{\"count\":20,\"currentPage\":1,\"firmID\":\"F000000000000001\",\"searchFilter\":\"\"}"}
	try {
		json=URLEncoder.encode(json, "utf8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return json;
}
/**
 *通用方法
 * @param Firmid
 * @return
 */
public static String getToURl(String key,String Firmid,int page,int coun,String mes){
	String json="{\"RequestMethod\":\""+key+"\",\"RequestParams\":\"{\\\"count\\\":"+coun+",\\\"currentPage\\\":"+page+",\\\"firmID\\\":\\\""+Firmid+"\\\",\\\"searchFilter\\\":\\\""+mes+"\\\"}\"}";
	//{"RequestMethod":"get_firm_data_purchase_returnStock","RequestParams":"{\"count\":20,\"currentPage\":1,\"firmID\":\"F000000000000001\",\"searchFilter\":\"\"}"}
	try {
		json=URLEncoder.encode(json, "utf8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return json;
}
/**
 * URLEncoder转换
 * @param mes
 * @return
 */
public static String getSouSuoURL(String mes){
	
	String json = mes;
	try {
		json=URLEncoder.encode(json, "utf8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//{"RequestMethod":"get_firm_data_stock_by_id","RequestParams":"{\"InventoryID\":\"45177321\",\"IsMedicine\":true}"}
	
	return json;
}

public static class DataUrl{
	/**
	 *药品请求头请求头地址拼接
	 * @param urls 如： http://111.9.170.48/Android
	 * @return
	 */
	public static String   getUrl(String urls){
		String str_url=urls+"/ServiceForDESM.ashx?request=";
		//http://111.9.170.48/Android/ServiceForDESM.ashx?request=
		return str_url;
	}
	/**
	 * 食品请求头地址
	 */
	public static String FoodGeturl(){
		
		return "http://117.173.38.55:84/YZTQW/SJSPZHAPI/api/";
	}
}
public static class UrlFood {

	/**
	 * 库存查询
	 */
	public static String GetInventoryByFirmID = "Business/GetInventoryByFirmID";
	/**
	 * 报损报溢
	 */
	public static String GetLooseIncomeByFirmID = "Business/GetLooseIncomeByFirmID";
	/**
	 * 采购
	 */
	public static String GetPurchasedByFirmID = "Business/GetPurchasedByFirmID";
	/**
	 * 采购明细
	 */
	public static String GetPurchasedDetailsByFirmID = "Business/GetPurchasedDetailsByFirmID";
	/**
	 * 采购退货
	 */
	public static String GetPurchasedReturnByFirmID = "Business/GetPurchasedReturnByFirmID";
	/**
	 * 采购退货明细
	 */
	public static String GetPurchasedReturnDetailsByFirmID = "Business/GetPurchasedReturnDetailsByFirmID";
	/**
	 * 销售
	 */
	public static String GetSalesByFirmID = "Business/GetSalesByFirmID";
	/**
	 * 销售明细
	 */
	public static String GetSalesDetailsByFirmID = "Business/GetSalesDetailsByFirmID";
	/**
	 * 销售退货
	 */
	public static String GetSalesReturnByFirmID = "Business/GetSalesReturnByFirmID";
	/**
	 * 销售退货明细
	 */
	public static String GetSalesReturnDetailsByFirmID = "Business/GetSalesReturnDetailsByFirmID";


	/**
	 * 食品采购
	 */
	public static String GetPurchasedByFirmID_1="Business/GetPurchasedByFirmID?FirmType=3&purchasedtype=1";
	/**
	 * 食品添加剂采购
	 */
	public static String GetPurchasedByFirmID_2="Business/GetPurchasedByFirmID?FirmType=3&purchasedtype=2";
	/**
	 * 食品相关产品采购
	 */
	public static String GetPurchasedByFirmID_3="Business/GetPurchasedByFirmID?FirmType=3&purchasedtype=3";
	/**
	 * 食品原材料入库
	 */
	public static String GetFoodMaterialsInByPage="Business/GetFoodMaterialsInByPage";
	/**
	 * 食品原材料出库
	 */
	public static String GetFoodMaterialsOutByPage="Business/GetFoodMaterialsOutByPage";
	/**
	 * 食品添加剂使用台账
	 */
	public static String GetFoodAdditiveUsingByPage="Business/GetFoodAdditiveUsingByPage";
	/**
	 * 餐厨废弃物处理
	 */
	public static String GetKitchenWasteRecyclingRecord="Business/GetKitchenWasteRecyclingRecord";
	/**
	 * 餐饮具清洗消毒
	 */
	public static String GetTablewareCleaningByPage="Business/GetTablewareCleaningByPage";
	/**
	 * 食品试尝留样
	 */
	public static String GetFoodTasteSampleByPage="Business/GetFoodTasteSampleByPage";

}

}
