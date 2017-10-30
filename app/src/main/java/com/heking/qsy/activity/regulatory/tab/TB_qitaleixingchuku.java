package com.heking.qsy.activity.regulatory.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_qitaleixingchuku {
	
private ArrayList<data> data;

	
	public ArrayList<data> getData() {
	return data;
}


public void setData(ArrayList<data> data) {
	this.data = data;
}


	public class data implements Serializable{
		  private String ID;// 14011,
	      private String LocalCode;// "21",
	      private String LocalLastedTime;// "2016-08-08 00:00:00",
	      private String Operator;// "严振耘",
	      private String ClientName;// "科创9店",
	      private String OutID;// "CK201608080001",
	      private String OutTime;// "2016-08-08 00:00:00",
	      private String OutTypeID;// 12,
	      private String OutTypeName;// "其他原因出库",
	      private String FirmID;// "F000000000000002",
	      private String FirmName;// "蓬安科创大药房连锁有限公司第9店",
	      private String UploadTime;// "2016-08-08 13:25:01"
	      private String SalesTime;
	      
		public String getSalesTime() {
			return SalesTime;
		}
		public void setSalesTime(String salesTime) {
			SalesTime = salesTime;
		}
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
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
		public String getOperator() {
			return Operator;
		}
		public void setOperator(String operator) {
			Operator = operator;
		}
		public String getClientName() {
			return ClientName;
		}
		public void setClientName(String clientName) {
			ClientName = clientName;
		}
		public String getOutID() {
			return OutID;
		}
		public void setOutID(String outID) {
			OutID = outID;
		}
		public String getOutTime() {
			return OutTime;
		}
		public void setOutTime(String outTime) {
			OutTime = outTime;
		}
		public String getOutTypeID() {
			return OutTypeID;
		}
		public void setOutTypeID(String outTypeID) {
			OutTypeID = outTypeID;
		}
		public String getOutTypeName() {
			return OutTypeName;
		}
		public void setOutTypeName(String outTypeName) {
			OutTypeName = outTypeName;
		}
		public String getFirmID() {
			return FirmID;
		}
		public void setFirmID(String firmID) {
			FirmID = firmID;
		}
		public String getFirmName() {
			return FirmName;
		}
		public void setFirmName(String firmName) {
			FirmName = firmName;
		}
		public String getUploadTime() {
			return UploadTime;
		}
		public void setUploadTime(String uploadTime) {
			UploadTime = uploadTime;
		}
	      
        
	}}
