package com.heking.qsy.activity.regulatory.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_CaiGouRuKu {
	
private ArrayList<data> data;

	
	public ArrayList<data> getData() {
	return data;
}


public void setData(ArrayList<data> data) {
	this.data = data;
}


	public class data implements Serializable{
		private String FirmName;//"蓬安科创大药房连锁有限公司第9店",
        private String ID;//212858,
        private String UploadTime;//"2016-10-07 22:10:16",
        private String LocalCode;//"1100",
        private String LocalLastedTime;//"2016-10-07 13:13:55",
        private String PurchasedTime;//"2016-10-07 13:13:55",
        private String Supplier;//"四川南充科伦医药贸易有限公司",
        private String Operator;//"严振耘",
        private String PurchasedID;//"RK201610070001"
        private String BatchNumber;
        private String ProductName;//苦杏仁
        private String ManufactureDate;//生产日期2016-10-07 13:13:55
        //"ID":16,
//        "LocalID":131,
//        "FirmID":"F511323000000001",
//        "BillNo":"CG20161017000038",
//        "PurchaseDate":"2016-10-17T00:00:00",
//        "ProductName":"苦杏仁",
//        "BatchNumber":"CP201601701",
//        "ManufactureDate":"2016-10-17T00:00:00",
//        "Specifications":"0.03g/剂",
//        "Quantity":"200",
//        "SupplierName":"四川蜀中制药有限公司",
//        "Contact":"万忠",
//        "ContactPhone":"13854621031",
//        "ProofFilePath":null,
//        "Operator":"xyh ",
//        "Sequence":0,
//        "Enable":true,
//        "UploadTime":"2016-10-17T09:59:38.8",
//        "ValidityDate":"2018-10-17T00:00:00",
//        "ShelfLife":"24个月",
//        "TraceCode":null
        
		public String getBatchNumber() {
			return BatchNumber;
		}
		public String getProductName() {
			return ProductName;
		}
		public void setProductName(String productName) {
			ProductName = productName;
		}
		public String getManufactureDate() {
			return ManufactureDate;
		}
		public void setManufactureDate(String manufactureDate) {
			ManufactureDate = manufactureDate;
		}
		public void setBatchNumber(String batchNumber) {
			BatchNumber = batchNumber;
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
		public String getPurchasedTime() {
			return PurchasedTime;
		}
		public void setPurchasedTime(String purchasedTime) {
			PurchasedTime = purchasedTime;
		}
		public String getSupplier() {
			return Supplier;
		}
		public void setSupplier(String supplier) {
			Supplier = supplier;
		}
		public String getOperator() {
			return Operator;
		}
		public void setOperator(String operator) {
			Operator = operator;
		}
		public String getPurchasedID() {
			return PurchasedID;
		}
		public void setPurchasedID(String purchasedID) {
			PurchasedID = purchasedID;
		}
        
	}}
