package com.heking.qsy.activity.regulatory.Food.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_FoodSalses {
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

        private String  SalesStaff ;//{ get; set; }

        private String  SalesTotalPrice ;//{ get; set; }

        private String  SaleTime ;//{ get; set; }

        private String  UploadTime ;//{ get; set; }

        private String  LocalCode ;//{ get; set; }

        private String LocalID;//55,
        private String BillNo;//"XS20161012000002",
        private String SaleDate;//"2016-10-12T00:00:00",
        private String productID;//35,
        private String ProductName;//"阿尔卑斯糖（苹果味）",
        private String BatchNumber;//"CP2016101201",
        private String ManufactureDate;//"2016-10-12T00:00:00",
        private String ValidityDate;//"2016-10-12T00:00:00",
        private String ShelfLife;//"12个月",
        private String Specifications;//"200g/包",
        private String Quantity;//"1",
        private String Customer;//"新世纪百货超市",
        private String Contact;//"张奎",
        private String ContactPhone;//"13548310160",
        private String Operator;//"系统管理员",
        private String ESCode;//"123456789012161012153641362676",
        private String Remark;//null,
        private String Sequence;//0,
        private String Enable;//true,
        
		public String getLocalID() {
			return LocalID;
		}

		public void setLocalID(String localID) {
			LocalID = localID;
		}

		public String getBillNo() {
			return BillNo;
		}

		public void setBillNo(String billNo) {
			BillNo = billNo;
		}

		public String getSaleDate() {
			return SaleDate;
		}

		public void setSaleDate(String saleDate) {
			SaleDate = saleDate;
		}

		public String getProductID() {
			return productID;
		}

		public void setProductID(String productID) {
			this.productID = productID;
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

		public String getCustomer() {
			return Customer;
		}

		public void setCustomer(String customer) {
			Customer = customer;
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

		public String getOperator() {
			return Operator;
		}

		public void setOperator(String operator) {
			Operator = operator;
		}

		public String getESCode() {
			return ESCode;
		}

		public void setESCode(String eSCode) {
			ESCode = eSCode;
		}

		public String getRemark() {
			return Remark;
		}

		public void setRemark(String remark) {
			Remark = remark;
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

		public String getSalesStaff() {
			return SalesStaff;
		}

		public void setSalesStaff(String salesStaff) {
			SalesStaff = salesStaff;
		}

		public String getSalesTotalPrice() {
			return SalesTotalPrice;
		}

		public void setSalesTotalPrice(String salesTotalPrice) {
			SalesTotalPrice = salesTotalPrice;
		}

		public String getSaleTime() {
			return SaleTime;
		}

		public void setSaleTime(String saleTime) {
			SaleTime = saleTime;
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
		
	}
 
}
