package com.heking.qsy.activity.regulatory.Food.Bevera.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_BeveraProcurement {

	private ArrayList<Data> Data;
	
	public ArrayList<Data> getData() {
		return Data;
	}

	public void setData(ArrayList<Data> data) {
		Data = data;
	}

	public class Data implements Serializable{
		private String ID;//":75,
	    private String PurchaseTypeID;//":1,
	    private String Supply;//":"我是供货商",
	    private String SupplyMan;//":"亚竹",
	    private String PurchaseMan;//":"风信子",
	    private String CheckMan;//":"阳光帅帅",
	    private String FirmID;//":"F000000000000001",
	    private String FillInDatetime;//":"2015-05-05T00:00:00",
	    private String BillNo;//":"007 ",
	    private String Status;//":1,
	    private String PurchaseSource;//":"1234",
	    private String SupplierContact;//":"123"
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		public String getPurchaseTypeID() {
			return PurchaseTypeID;
		}
		public void setPurchaseTypeID(String purchaseTypeID) {
			PurchaseTypeID = purchaseTypeID;
		}
		public String getSupply() {
			return Supply;
		}
		public void setSupply(String supply) {
			Supply = supply;
		}
		public String getSupplyMan() {
			return SupplyMan;
		}
		public void setSupplyMan(String supplyMan) {
			SupplyMan = supplyMan;
		}
		public String getPurchaseMan() {
			return PurchaseMan;
		}
		public void setPurchaseMan(String purchaseMan) {
			PurchaseMan = purchaseMan;
		}
		public String getCheckMan() {
			return CheckMan;
		}
		public void setCheckMan(String checkMan) {
			CheckMan = checkMan;
		}
		public String getFirmID() {
			return FirmID;
		}
		public void setFirmID(String firmID) {
			FirmID = firmID;
		}
		public String getFillInDatetime() {
			return FillInDatetime;
		}
		public void setFillInDatetime(String fillInDatetime) {
			FillInDatetime = fillInDatetime;
		}
		public String getBillNo() {
			return BillNo;
		}
		public void setBillNo(String billNo) {
			BillNo = billNo;
		}
		public String getStatus() {
			return Status;
		}
		public void setStatus(String status) {
			Status = status;
		}
		public String getPurchaseSource() {
			return PurchaseSource;
		}
		public void setPurchaseSource(String purchaseSource) {
			PurchaseSource = purchaseSource;
		}
		public String getSupplierContact() {
			return SupplierContact;
		}
		public void setSupplierContact(String supplierContact) {
			SupplierContact = supplierContact;
		}
	    
	}
}
