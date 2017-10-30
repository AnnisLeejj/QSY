package com.heking.qsy.activity.regulatory.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_CaiGouTuiHuo {
	
private ArrayList<data> data;

	
	public ArrayList<data> getData() {
	return data;
}


public void setData(ArrayList<data> data) {
	this.data = data;
}


	public class data implements Serializable{
		private String FirmName;//"南充市兴平药品零售连锁有限公司蓬安第10店",
        private String ID;//3316,
        private String UploadTime;//"2016-09-08 21:00:02",
        private String LocalCode;//"1982",
        private String LocalLastedTime;//"2016-09-08 20:57:18",
        private String ReturnTime;//"2016-09-08 20:57:18",
        private String Supplier;//"四川科欣医药贸易有限公司",
        private String SupplierID;//1447,
        private String Operator;//"余世莉",
        private String ReturnNumber;//"1982",
        private String Reason;//""
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
		public String getReturnTime() {
			return ReturnTime;
		}
		public void setReturnTime(String returnTime) {
			ReturnTime = returnTime;
		}
		public String getSupplier() {
			return Supplier;
		}
		public void setSupplier(String supplier) {
			Supplier = supplier;
		}
		public String getSupplierID() {
			return SupplierID;
		}
		public void setSupplierID(String supplierID) {
			SupplierID = supplierID;
		}
		public String getOperator() {
			return Operator;
		}
		public void setOperator(String operator) {
			Operator = operator;
		}
		public String getReturnNumber() {
			return ReturnNumber;
		}
		public void setReturnNumber(String returnNumber) {
			ReturnNumber = returnNumber;
		}
		public String getReason() {
			return Reason;
		}
		public void setReason(String reason) {
			Reason = reason;
		}
        
	}}
