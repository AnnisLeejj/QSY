package com.heking.qsy.activity.regulatory.Food.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_FoodLooseIncome {
	private ArrayList<Data> Data;
	
	public ArrayList<Data> getData() {
		return Data;
	}

	public void setData(ArrayList<Data> data) {
		Data = data;
	}

	public class Data implements Serializable{
		
		private  String ID ;// set; }

		private String FirmID ;// set; }

		private String UploadTime ;// set; }

		private String LocalCode ;// set; }

		private String LocalLastedTime ;// set; }

		private String LooseIncomeOrderNumber ;// set; }

		private String StoreRoomID ;// set; }

		private String StoreRoomName ;// set; }

		private String LooseIncomeTypeID ;// set; }

		private String FoodID ;// set; }

		private String GenericName ;// set; }

		private String Manufacturer ;// set; }

		private String Specification ;// set; }

		private String BatchNumber ;// set; }

		private String Quantity ;// set; }

		private String ProductionDate ;// set; }

		private String ExpiredDate ;// set; }

		private String CheckTime ;// set; }

		private String LooseIncomeReason ;// set; }

		private String MeasurementUnit ;// set; }

        private String ShelfLife ;// set; }
        
//        private String ID;//26,
//        private String FirmID;//"F511323000000002",
        private String LocalID;//0,
        private String LooseIncomeDate;//"2016-10-08T00:00:00",
        private String MaterielID;//13,
        private String MaterielName;//"苹果",
        private String UnitName;//"个",
//        private String Specification;//"700g/个",
        private String ManufactureDate;//"2016-10-08T00:00:00",
//        private String BatchNumber;//"2016100803",
        private String ValidityDate;//"2016-11-08T00:00:00",
//        private String ShelfLife;//"1个月",
        private String OldInventory;//200,
        private String FactInventory;//199,
        private String Cause;//"报损",
        private String OperUser;//"向亚红",
        private String AuditUser;//"刘文",
        private String CreateTime;//"2016-10-08T14:34:12.097",
        private String Enable;//true,
        private String Deleted;//false,
        private String Sequence;//0,
        private String MaterielInventoryID;//55,
//        private String UploadTime;//"2016-10-08T14:34:24.247",
//        private String GenericName;//"苹果",
//        private String ExpiredDate;//"2016-11-08T00:00:00",
//        private String StoreRoomName;//null,
        private String Supplier;//null,
//        private String Quantity;//200,
//        private String LooseIncomeReason;//"报损"
        
        
        

		public String getID() {
			return ID;
		}

		public String getLocalID() {
			return LocalID;
		}

		public void setLocalID(String localID) {
			LocalID = localID;
		}

		public String getLooseIncomeDate() {
			return LooseIncomeDate;
		}

		public void setLooseIncomeDate(String looseIncomeDate) {
			LooseIncomeDate = looseIncomeDate;
		}

		public String getMaterielID() {
			return MaterielID;
		}

		public void setMaterielID(String materielID) {
			MaterielID = materielID;
		}

		public String getMaterielName() {
			return MaterielName;
		}

		public void setMaterielName(String materielName) {
			MaterielName = materielName;
		}

		public String getUnitName() {
			return UnitName;
		}

		public void setUnitName(String unitName) {
			UnitName = unitName;
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

		public String getOldInventory() {
			return OldInventory;
		}

		public void setOldInventory(String oldInventory) {
			OldInventory = oldInventory;
		}

		public String getFactInventory() {
			return FactInventory;
		}

		public void setFactInventory(String factInventory) {
			FactInventory = factInventory;
		}

		public String getCause() {
			return Cause;
		}

		public void setCause(String cause) {
			Cause = cause;
		}

		public String getOperUser() {
			return OperUser;
		}

		public void setOperUser(String operUser) {
			OperUser = operUser;
		}

		public String getAuditUser() {
			return AuditUser;
		}

		public void setAuditUser(String auditUser) {
			AuditUser = auditUser;
		}

		public String getCreateTime() {
			return CreateTime;
		}

		public void setCreateTime(String createTime) {
			CreateTime = createTime;
		}

		public String getEnable() {
			return Enable;
		}

		public void setEnable(String enable) {
			Enable = enable;
		}

		public String getDeleted() {
			return Deleted;
		}

		public void setDeleted(String deleted) {
			Deleted = deleted;
		}

		public String getSequence() {
			return Sequence;
		}

		public void setSequence(String sequence) {
			Sequence = sequence;
		}

		public String getMaterielInventoryID() {
			return MaterielInventoryID;
		}

		public void setMaterielInventoryID(String materielInventoryID) {
			MaterielInventoryID = materielInventoryID;
		}

		public String getSupplier() {
			return Supplier;
		}

		public void setSupplier(String supplier) {
			Supplier = supplier;
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

		public String getLooseIncomeOrderNumber() {
			return LooseIncomeOrderNumber;
		}

		public void setLooseIncomeOrderNumber(String looseIncomeOrderNumber) {
			LooseIncomeOrderNumber = looseIncomeOrderNumber;
		}

		public String getStoreRoomID() {
			return StoreRoomID;
		}

		public void setStoreRoomID(String storeRoomID) {
			StoreRoomID = storeRoomID;
		}

		public String getStoreRoomName() {
			return StoreRoomName;
		}

		public void setStoreRoomName(String storeRoomName) {
			StoreRoomName = storeRoomName;
		}

		public String getLooseIncomeTypeID() {
			return LooseIncomeTypeID;
		}

		public void setLooseIncomeTypeID(String looseIncomeTypeID) {
			LooseIncomeTypeID = looseIncomeTypeID;
		}

		public String getFoodID() {
			return FoodID;
		}

		public void setFoodID(String foodID) {
			FoodID = foodID;
		}

		public String getGenericName() {
			return GenericName;
		}

		public void setGenericName(String genericName) {
			GenericName = genericName;
		}

		public String getManufacturer() {
			return Manufacturer;
		}

		public void setManufacturer(String manufacturer) {
			Manufacturer = manufacturer;
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

		public String getQuantity() {
			return Quantity;
		}

		public void setQuantity(String quantity) {
			Quantity = quantity;
		}

		public String getProductionDate() {
			return ProductionDate;
		}

		public void setProductionDate(String productionDate) {
			ProductionDate = productionDate;
		}

		public String getExpiredDate() {
			return ExpiredDate;
		}

		public void setExpiredDate(String expiredDate) {
			ExpiredDate = expiredDate;
		}

		public String getCheckTime() {
			return CheckTime;
		}

		public void setCheckTime(String checkTime) {
			CheckTime = checkTime;
		}

		public String getLooseIncomeReason() {
			return LooseIncomeReason;
		}

		public void setLooseIncomeReason(String looseIncomeReason) {
			LooseIncomeReason = looseIncomeReason;
		}

		public String getMeasurementUnit() {
			return MeasurementUnit;
		}

		public void setMeasurementUnit(String measurementUnit) {
			MeasurementUnit = measurementUnit;
		}

		public String getShelfLife() {
			return ShelfLife;
		}

		public void setShelfLife(String shelfLife) {
			ShelfLife = shelfLife;
		}
        
	}
 
}
