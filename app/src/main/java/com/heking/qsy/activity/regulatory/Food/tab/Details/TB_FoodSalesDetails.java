package com.heking.qsy.activity.regulatory.Food.tab.Details;

import java.util.ArrayList;
    

    //[Table("food.TB_FoodSalesDetails")]
    public    class TB_FoodSalesDetails
    {
    	private ArrayList<Data> Data;
    	
    	public ArrayList<Data> getData() {
			return Data;
		}

		public void setData(ArrayList<Data> data) {
			Data = data;
		}

		public class Data{
        private  String  ID ;//{ get; set; }

        private  String  SalesID ;//{ get; set; }

        //[String Length(64)]
        private  String  FoodID ;//{ get; set; }

        private  String  Quantity ;//{ get; set; }

        //[String Length(256)]
        private  String  BatchNumber ;//{ get; set; }

        private  String  SellingPrice ;//{ get; set; }

        //[String Length(256)]
        private  String  Supplier ;//{ get; set; }

        private  String  ProductionDate ;//{ get; set; }

        private  String  ExpiredDate ;//{ get; set; }

        //[String Length(50)]
        private  String  MeasurementUnit ;//{ get; set; }

        //[String Length(20)]
        private  String  ShelfLife ;//{ get; set; }

        private  String  SupplierID ;//{ get; set; }

        //[NotMapped]
        private  String  GenericName ;//{ get; set; }

		public String getID() {
			return ID;
		}

		public void setID(String iD) {
			ID = iD;
		}

		public String getSalesID() {
			return SalesID;
		}

		public void setSalesID(String salesID) {
			SalesID = salesID;
		}

		public String getFoodID() {
			return FoodID;
		}

		public void setFoodID(String foodID) {
			FoodID = foodID;
		}

		public String getQuantity() {
			return Quantity;
		}

		public void setQuantity(String quantity) {
			Quantity = quantity;
		}

		public String getBatchNumber() {
			return BatchNumber;
		}

		public void setBatchNumber(String batchNumber) {
			BatchNumber = batchNumber;
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

		public String getSupplierID() {
			return SupplierID;
		}

		public void setSupplierID(String supplierID) {
			SupplierID = supplierID;
		}

		public String getGenericName() {
			return GenericName;
		}

		public void setGenericName(String genericName) {
			GenericName = genericName;
		}
        
    }
}
