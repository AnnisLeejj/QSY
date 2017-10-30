package com.heking.qsy.activity.regulatory.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_XiaoShouChuKu {
	
private ArrayList<data> data;

	
	public ArrayList<data> getData() {
	return data;
}


public void setData(ArrayList<data> data) {
	this.data = data;
}


	public class data implements  Serializable{
		private String FirmName;//"南充市兴平药品零售连锁有限公司蓬安第10店",
        private String ID;//4145089,
        private String UploadTime;//"2016-10-12 11:40:13",
        private String LocalCode;//"2210",
        private String LocalLastedTime;//"2016-10-12 11:38:49",
        private String SaleOrderNumber;//"",
        private String ClientName;//"",
        private String Operator;//"何爱东",
        private String SalesTime;//"2016-10-12 11:38:49"
        private String ProductName;//止咳立效胶囊
        private String SaleDate;//销售时间  2016-10-10T00:00:00
        
		public String getSaleDate() {
			return SaleDate;
		}
		public void setSaleDate(String saleDate) {
			SaleDate = saleDate;
		}
		public String getProductName() {
			return ProductName;
		}
		public void setProductName(String productName) {
			ProductName = productName;
		}
		public String getFirmName() {
			return FirmName;
		}
		public void setFirmName(String firmName) {
			FirmName = firmName;
		}
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		public String getUploadTime() {
			return UploadTime;
		}
		public void setUploadTime(String uploadTime) {
			UploadTime = uploadTime;
		}
		public String getLocalCode() {
			return LocalCode;
		}
		public void setLocalCode(String localCode) {
			LocalCode = localCode;
		}
		public String getLocalLastedTime() {
			return LocalLastedTime;
		}
		public void setLocalLastedTime(String localLastedTime) {
			LocalLastedTime = localLastedTime;
		}
		public String getSaleOrderNumber() {
			return SaleOrderNumber;
		}
		public void setSaleOrderNumber(String saleOrderNumber) {
			SaleOrderNumber = saleOrderNumber;
		}
		public String getClientName() {
			return ClientName;
		}
		public void setClientName(String clientName) {
			ClientName = clientName;
		}
		public String getOperator() {
			return Operator;
		}
		public void setOperator(String operator) {
			Operator = operator;
		}
		public String getSalesTime() {
			return SalesTime;
		}
		public void setSalesTime(String salesTime) {
			SalesTime = salesTime;
		}
        
        }}
