package com.heking.qsy.activity.regulatory.Food.tab.Details;

import java.util.ArrayList;
    

    //Table("food.TB_FoodPurchasedReturnDetails")]
    public    class TB_FoodPurchasedReturnDetails
    {
    	
    	public ArrayList<Data> Data;
    	
    	public ArrayList<Data> getData() {
			return Data;
		}

		public void setData(ArrayList<Data> data) {
			Data = data;
		}

		public class Data{
        private  String  ID ;//{get; set; }

        private  String  ReturnID ;//{get; set; }

        private  String  Quantity ;//{get; set; }

        //String Length(16)]
        private  String  FoodID ;//{get; set; }

        //String Length(64)]
        private  String  BatchNumber ;//{get; set; }

        private  String  ProductionDate ;//{get; set; }

        private  String  ExpiredDate ;//{get; set; }

        private  String  SupplierID ;//{get; set; }

        //String Length(256)]
        private  String  SupplierName ;//{get; set; }

        private  String  ReturnPrice ;//{get; set; }

        private  String  Remark ;//{get; set; }

        //String Length(50)]
        private  String  MeasurementUnit ;//{get; set; }

        //String Length(20)]
        private  String  ShelfLife ;//{get; set; }

        //NotMapped]
        private  String  GenericName ;//{get; set; }

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
