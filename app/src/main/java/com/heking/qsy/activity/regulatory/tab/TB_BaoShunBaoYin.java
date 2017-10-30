package com.heking.qsy.activity.regulatory.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_BaoShunBaoYin {
	
private ArrayList<data> data;

	
	public ArrayList<data> getData() {
	return data;
}


public void setData(ArrayList<data> data) {
	this.data = data;
}


	public class data implements Serializable{ 
		private String FirmName;//"南充市兴平药品零售连锁有限公司蓬安第10店",
        private String ID;//4137,
        private String UploadTime;//"2014-05-17 12:47:05",
        private String Name;//"银黄颗粒",
        private String IsMedicine;//true,
        private String ExpiredDate;//"2016-12-01 00:00:00",
        private String Quantity;//4,
        private String Specification;//"10袋",
        private String BatchNumber;//"4401041",
        private String CheckTime;//"2014-05-17 12:44:57",
        private String FormulationType;//"冲剂",
        private String StoreRoomName;//"g1",
        private String Manufacturer;//"河北国金药业有限责任公司",
        private String ProductionDate;//"2014-01-04 00:00:00",
        private String LooseIncomeTypeID;//2
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
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public String getIsMedicine() {
			return IsMedicine;
		}
		public void setIsMedicine(String isMedicine) {
			IsMedicine = isMedicine;
		}
		public String getExpiredDate() {
			return ExpiredDate;
		}
		public void setExpiredDate(String expiredDate) {
			ExpiredDate = expiredDate;
		}
		public String getQuantity() {
			return Quantity;
		}
		public void setQuantity(String quantity) {
			Quantity = quantity;
		}
		public String getSpecification() {
			return Specification;
		}
		public void setSpecification(String specification) {
			Specification = specification;
		}
		public String getBatchNumber() {
			return BatchNumber;
		}
		public void setBatchNumber(String batchNumber) {
			BatchNumber = batchNumber;
		}
		public String getCheckTime() {
			return CheckTime;
		}
		public void setCheckTime(String checkTime) {
			CheckTime = checkTime;
		}
		public String getFormulationType() {
			return FormulationType;
		}
		public void setFormulationType(String formulationType) {
			FormulationType = formulationType;
		}
		public String getStoreRoomName() {
			return StoreRoomName;
		}
		public void setStoreRoomName(String storeRoomName) {
			StoreRoomName = storeRoomName;
		}
		public String getManufacturer() {
			return Manufacturer;
		}
		public void setManufacturer(String manufacturer) {
			Manufacturer = manufacturer;
		}
		public String getProductionDate() {
			return ProductionDate;
		}
		public void setProductionDate(String productionDate) {
			ProductionDate = productionDate;
		}
		public String getLooseIncomeTypeID() {
			return LooseIncomeTypeID;
		}
		public void setLooseIncomeTypeID(String looseIncomeTypeID) {
			LooseIncomeTypeID = looseIncomeTypeID;
		}
        
        }}
