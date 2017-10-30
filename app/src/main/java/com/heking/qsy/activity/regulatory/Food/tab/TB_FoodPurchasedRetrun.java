package com.heking.qsy.activity.regulatory.Food.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_FoodPurchasedRetrun {
	private ArrayList<Data> Data;
	
	public ArrayList<Data> getData() {
		return Data;
	}

	public void setData(ArrayList<Data> data) {
		Data = data;
	}

	public class Data implements Serializable{
		

        private  String  ID ;//{ get; set; }

        		private String  FirmID ;//{ get; set; }

        		private String  UploadTime ;//{ get; set; }

        		private String  LocalCode ;//{ get; set; }

        		private String  LocalLastedTime ;//{ get; set; }

        		private String  ReturnNumber ;//{ get; set; }

        		private String  SupplierID ;//{ get; set; }

        		private String  Supplier ;//{ get; set; }

        		private String  Comment ;//{ get; set; }

        		private String  ReturnTime ;//{ get; set; }

        		private String  Operator ;//{ get; set; }

				public String getID() {
					return ID;
				}

				public void setID(String iD) {
					ID = iD;
				}

				public String getFirmID() {
					return FirmID;
				}

				public void setFirmID(String firmID) {
					FirmID = firmID;
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

				public String getReturnNumber() {
					return ReturnNumber;
				}

				public void setReturnNumber(String returnNumber) {
					ReturnNumber = returnNumber;
				}

				public String getSupplierID() {
					return SupplierID;
				}

				public void setSupplierID(String supplierID) {
					SupplierID = supplierID;
				}

				public String getSupplier() {
					return Supplier;
				}

				public void setSupplier(String supplier) {
					Supplier = supplier;
				}

				public String getComment() {
					return Comment;
				}

				public void setComment(String comment) {
					Comment = comment;
				}

				public String getReturnTime() {
					return ReturnTime;
				}

				public void setReturnTime(String returnTime) {
					ReturnTime = returnTime;
				}

				public String getOperator() {
					return Operator;
				}

				public void setOperator(String operator) {
					Operator = operator;
				}
        		
		
	}
 
}
