package com.heking.qsy.activity.regulatory.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_XiaoShuoTuiHuo {
	
private ArrayList<data> data;

	
	public ArrayList<data> getData() {
	return data;
}


public void setData(ArrayList<data> data) {
	this.data = data;
}


	public class data implements Serializable{
		private String FirmName;//"南充市兴平药品零售连锁有限公司蓬安第10店",
        private String ID;//53390,
        private String UploadTime;//"2016-07-21 10:53:42",
        private String LocalCode;//"1621",
        private String LocalLastedTime;//"2016-07-21 10:51:25",
        private String ReturnOrderNumber;//"",
        private String ClientName;//"",
        private String Operator;//"金碧珍",
        private String ReturnTime;//"2016-07-21 10:51:25"
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
		public String getReturnOrderNumber() {
			return ReturnOrderNumber;
		}
		public void setReturnOrderNumber(String returnOrderNumber) {
			ReturnOrderNumber = returnOrderNumber;
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
		public String getReturnTime() {
			return ReturnTime;
		}
		public void setReturnTime(String returnTime) {
			ReturnTime = returnTime;
		}
        
        	}}
