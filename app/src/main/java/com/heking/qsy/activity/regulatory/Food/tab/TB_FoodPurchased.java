package com.heking.qsy.activity.regulatory.Food.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_FoodPurchased {
	private ArrayList<Data> Data;

	public ArrayList<Data> getData() {
		return Data;
	}

	public void setData(ArrayList<Data> data) {
		Data = data;
	}

	public class Data implements Serializable {
		private String PurchasedID;// "RK201406170003",
		private String SupplierID;// 64663,
		private String BillNo;// null,
		private String SystemName;// null,
		private String SystemVersion;// null,
		private String VersionNumber;// null,
		private String InfoSource;// null
		private String ID;// { get; set; }
		private String FirmID;// { get; set; }
		private String UploadTime;// { get; set; }
		private String LocalCode;// { get; set; }
		private String LocalLastedTime;// { get; set; }
		private String InvoiceNumber;// { get; set; }
		private String InvoiceTime;// { get; set; }
		private String Supplier;// { get; set; }
		private String PurchasedTime;// { get; set; }
		private String Amount;// { get; set; }
		private String Operator;
        private String LocalID;//83,
        private String PurchaseDate;//"2016-10-11T00:00:00",
        private String ProductName;//"牛奶",
        private String BatchNumber;//"YFL2016101101",
        private String ManufactureDate;//"2016-10-11T00:00:00",
        private String Specifications;//"500g",
        private String Quantity;//"1000",
        private String SupplierName;//"孝姑食品批发部",
        private String Contact;//"董柳",
        private String ContactPhone;//"18751202410",
        private String ProofFilePath;//null,
        private String Sequence;//0,
        private String Enable;//true,
        private String ValidityDate;//"2017-04-11T00:00:00",
        private String ShelfLife;//"6个月",
        private String TraceCode;//null
        private String TicketNumber;
        
		public String getTicketNumber() {
			return TicketNumber;
		}

		public void setTicketNumber(String ticketNumber) {
			TicketNumber = ticketNumber;
		}

		public String getLocalID() {
			return LocalID;
		}

		public void setLocalID(String localID) {
			LocalID = localID;
		}

		public String getPurchaseDate() {
			return PurchaseDate;
		}

		public void setPurchaseDate(String purchaseDate) {
			PurchaseDate = purchaseDate;
		}

		public String getProductName() {
			return ProductName;
		}

		public void setProductName(String productName) {
			ProductName = productName;
		}

		public String getBatchNumber() {
			return BatchNumber;
		}

		public void setBatchNumber(String batchNumber) {
			BatchNumber = batchNumber;
		}

		public String getManufactureDate() {
			return ManufactureDate;
		}

		public void setManufactureDate(String manufactureDate) {
			ManufactureDate = manufactureDate;
		}

		public String getSpecifications() {
			return Specifications;
		}

		public void setSpecifications(String specifications) {
			Specifications = specifications;
		}

		public String getQuantity() {
			return Quantity;
		}

		public void setQuantity(String quantity) {
			Quantity = quantity;
		}

		public String getSupplierName() {
			return SupplierName;
		}

		public void setSupplierName(String supplierName) {
			SupplierName = supplierName;
		}

		public String getContact() {
			return Contact;
		}

		public void setContact(String contact) {
			Contact = contact;
		}

		public String getContactPhone() {
			return ContactPhone;
		}

		public void setContactPhone(String contactPhone) {
			ContactPhone = contactPhone;
		}

		public String getProofFilePath() {
			return ProofFilePath;
		}

		public void setProofFilePath(String proofFilePath) {
			ProofFilePath = proofFilePath;
		}

		public String getSequence() {
			return Sequence;
		}

		public void setSequence(String sequence) {
			Sequence = sequence;
		}

		public String getEnable() {
			return Enable;
		}

		public void setEnable(String enable) {
			Enable = enable;
		}

		public String getValidityDate() {
			return ValidityDate;
		}

		public void setValidityDate(String validityDate) {
			ValidityDate = validityDate;
		}

		public String getShelfLife() {
			return ShelfLife;
		}

		public void setShelfLife(String shelfLife) {
			ShelfLife = shelfLife;
		}

		public String getTraceCode() {
			return TraceCode;
		}

		public void setTraceCode(String traceCode) {
			TraceCode = traceCode;
		}

		public String getPurchasedID() {
			return PurchasedID;
		}

		public void setPurchasedID(String purchasedID) {
			PurchasedID = purchasedID;
		}

		public String getSupplierID() {
			return SupplierID;
		}

		public void setSupplierID(String supplierID) {
			SupplierID = supplierID;
		}

		public String getBillNo() {
			return BillNo;
		}

		public void setBillNo(String billNo) {
			BillNo = billNo;
		}

		public String getSystemName() {
			return SystemName;
		}

		public void setSystemName(String systemName) {
			SystemName = systemName;
		}

		public String getSystemVersion() {
			return SystemVersion;
		}

		public void setSystemVersion(String systemVersion) {
			SystemVersion = systemVersion;
		}

		public String getVersionNumber() {
			return VersionNumber;
		}

		public void setVersionNumber(String versionNumber) {
			VersionNumber = versionNumber;
		}

		public String getInfoSource() {
			return InfoSource;
		}

		public void setInfoSource(String infoSource) {
			InfoSource = infoSource;
		}

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

		public String getInvoiceNumber() {
			return InvoiceNumber;
		}

		public void setInvoiceNumber(String invoiceNumber) {
			InvoiceNumber = invoiceNumber;
		}

		public String getInvoiceTime() {
			return InvoiceTime;
		}

		public void setInvoiceTime(String invoiceTime) {
			InvoiceTime = invoiceTime;
		}

		public String getSupplier() {
			return Supplier;
		}

		public void setSupplier(String supplier) {
			Supplier = supplier;
		}

		public String getPurchasedTime() {
			return PurchasedTime;
		}

		public void setPurchasedTime(String purchasedTime) {
			PurchasedTime = purchasedTime;
		}

		public String getAmount() {
			return Amount;
		}

		public void setAmount(String amount) {
			Amount = amount;
		}

		public String getOperator() {
			return Operator;
		}

		public void setOperator(String operator) {
			Operator = operator;
		}// { get; set; }

	}

}
