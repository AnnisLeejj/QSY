package com.heking.qsy.activity.regulatory.tab;

import java.io.Serializable;
import java.util.ArrayList;


public class TB_TeShuYaoPinXiaoShou {
	
private ArrayList<data> data;

	
	public ArrayList<data> getData() {
	return data;
}


public void setData(ArrayList<data> data) {
	this.data = data;
}


	public class data implements Serializable{ 
        private String FirmName;//"犍为县人民医院",
        private String ID;//1,
        private String PurchaseMan;//"向亚红",
        private String Address;//"犍为石溪镇",
        private String Age;//20,
        private String Diagnose;//"传染性呼吸疾病",
        private String Gender;//"男",
        private String HowToUsage;//"饮用",
        private String Ill;//"感冒",
        private String Licensed;//"刘丹",
        private String PhoneNumber;//"15723374727",
        private String PurchaseIdentity;//"500238199210255781",
        private String SaleTime;//"2016-05-04 15:59:44",
        private String TotalQuantity;//1,
        private String SaleOrderNumber;//"201605040125401"
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
		public String getPurchaseMan() {
			return PurchaseMan;
		}
		public void setPurchaseMan(String purchaseMan) {
			PurchaseMan = purchaseMan;
		}
		public String getAddress() {
			return Address;
		}
		public void setAddress(String address) {
			Address = address;
		}
		public String getAge() {
			return Age;
		}
		public void setAge(String age) {
			Age = age;
		}
		public String getDiagnose() {
			return Diagnose;
		}
		public void setDiagnose(String diagnose) {
			Diagnose = diagnose;
		}
		public String getGender() {
			return Gender;
		}
		public void setGender(String gender) {
			Gender = gender;
		}
		public String getHowToUsage() {
			return HowToUsage;
		}
		public void setHowToUsage(String howToUsage) {
			HowToUsage = howToUsage;
		}
		public String getIll() {
			return Ill;
		}
		public void setIll(String ill) {
			Ill = ill;
		}
		public String getLicensed() {
			return Licensed;
		}
		public void setLicensed(String licensed) {
			Licensed = licensed;
		}
		public String getPhoneNumber() {
			return PhoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			PhoneNumber = phoneNumber;
		}
		public String getPurchaseIdentity() {
			return PurchaseIdentity;
		}
		public void setPurchaseIdentity(String purchaseIdentity) {
			PurchaseIdentity = purchaseIdentity;
		}
		public String getSaleTime() {
			return SaleTime;
		}
		public void setSaleTime(String saleTime) {
			SaleTime = saleTime;
		}
		public String getTotalQuantity() {
			return TotalQuantity;
		}
		public void setTotalQuantity(String totalQuantity) {
			TotalQuantity = totalQuantity;
		}
		public String getSaleOrderNumber() {
			return SaleOrderNumber;
		}
		public void setSaleOrderNumber(String saleOrderNumber) {
			SaleOrderNumber = saleOrderNumber;
		}
		
		
	}}
