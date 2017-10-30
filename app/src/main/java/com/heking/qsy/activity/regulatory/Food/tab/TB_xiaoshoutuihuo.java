package com.heking.qsy.activity.regulatory.Food.tab;

import java.util.ArrayList;

public class TB_xiaoshoutuihuo {
private ArrayList<data> data;

public ArrayList<data> getData() {
	return data;
}

public void setData(ArrayList<data> data) {
	this.data = data;
}

public class data{
    private String ID;//1,
    private String ReturnID;//1,
    private String Quantity;//16,
    private String FoodID;//"FD00000000000001",
    private String BatchNumber;//"0200400000000",
    private String ProductionDate;//"2015-04-02T14:45:48",
    private String ExpiredDate;//"2016-04-02T14:45:48",
    private String SupplierID;//null,
    private String SupplierName;//"旺旺集团 ",
    private String ReturnPrice;//16,
    private String Remark;//"12个月",
    private String MeasurementUnit;//"盒",
    private String ShelfLife;//"12个月",
    private String GenericName;//"有机饼干"
    private String SalesID;//1,
    private String SellingPrice;//16,
    private String Supplier;//"旺旺集团 ",
    
	public String getSalesID() {
		return SalesID;
	}
	public void setSalesID(String salesID) {
		SalesID = salesID;
	}
	public String getSellingPrice() {
		return SellingPrice;
	}
	public void setSellingPrice(String sellingPrice) {
		SellingPrice = sellingPrice;
	}
	public String getSupplier() {
		return Supplier;
	}
	public void setSupplier(String supplier) {
		Supplier = supplier;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getReturnID() {
		return ReturnID;
	}
	public void setReturnID(String returnID) {
		ReturnID = returnID;
	}
	public String getQuantity() {
		return Quantity;
	}
	public void setQuantity(String quantity) {
		Quantity = quantity;
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
	public String getSupplierID() {
		return SupplierID;
	}
	public void setSupplierID(String supplierID) {
		SupplierID = supplierID;
	}
	public String getSupplierName() {
		return SupplierName;
	}
	public void setSupplierName(String supplierName) {
		SupplierName = supplierName;
	}
	public String getReturnPrice() {
		return ReturnPrice;
	}
	public void setReturnPrice(String returnPrice) {
		ReturnPrice = returnPrice;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
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
    
}
}
