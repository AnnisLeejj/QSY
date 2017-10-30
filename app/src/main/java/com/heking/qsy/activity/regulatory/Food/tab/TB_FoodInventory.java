package com.heking.qsy.activity.regulatory.Food.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_FoodInventory {
	private ArrayList<Data> Data;
	
	public ArrayList<Data> getData() {
		return Data;
	}

	public void setData(ArrayList<Data> data) {
		Data = data;
	}

	public class Data implements Serializable{
	    private String ID;//17899,
	    private String FirmID;//"F000000000000001",
	    private String UploadTime;//"2015-08-12T13:40:53.527",
	    private String FoodID;//"FD00000000000020",
	    private String BatchNumber;//"0100100000006",
	    private String Quantity;//537,
	    private String StoreRoomName;//"配送总仓",
	    private String ProductionDate;//"2015-04-02T14:45:48",// 原辅料不可用
	    private String ExpiredDate;//"2015-06-01T14:45:48",
	    private String Specification;//规格型号 500g/盒
	    private String Supplier;//"伊利",
	    private String Unit;//盒
	    private String MeasurementUnit;//"盒", 原辅料不可用
	    private String ShelfLife;//null,
	    private String GenericName;//"伊利牛奶255ml"
	    private String ManufactureDate;//生产日期"2015-04-02T14:45:48",
	    private String MaterielName;//原辅料名称 阿莫西林
	    

		public String getMaterielName() {
			return MaterielName;
		}
		public void setMaterielName(String materielName) {
			MaterielName = materielName;
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
		public String getFoodID() {
			return FoodID;
		}
		public void setFoodID(String foodID) {
			FoodID = foodID;
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
		public String getStoreRoomName() {
			return StoreRoomName;
		}
		public void setStoreRoomName(String storeRoomName) {
			StoreRoomName = storeRoomName;
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
		public String getSupplier() {
			return Supplier;
		}
		public void setSupplier(String supplier) {
			Supplier = supplier;
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
		public String getGenericName() {
			return GenericName;
		}
		public void setGenericName(String genericName) {
			GenericName = genericName;
		}
		public String getSpecification() {
			return Specification;
		}
		public void setSpecification(String specification) {
			Specification = specification;
		}
		public String getUnit() {
			return Unit;
		}
		public void setUnit(String unit) {
			Unit = unit;
		}
		public String getManufactureDate() {
			return ManufactureDate;
		}
		public void setManufactureDate(String manufactureDate) {
			ManufactureDate = manufactureDate;
		}
		
	    
	}
 
}
