package com.heking.qsy.activity.regulatory.Food.Bevera.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_FoodBeveraAdditivesUsedParamet {

	private ArrayList<Data> Data;
	
	public ArrayList<Data> getData() {
		return Data;
	}

	public void setData(ArrayList<Data> data) {
		Data = data;
	}

	public class Data implements Serializable{
        private String ID;//2,
        private String GenericName;//"1",
        private String FoodAdditiveID;//" ",
        private String Specification;//"",
        private String ShelfLife;//"",
        private String BatchNumber;//"",
        private String SupplierName;//"",
        private String SupplierContact;//"",
        private String Purchaser;//"",
        private String UsingTime;//"2014-04-15T15:25:06",
        private String PurchaseQuantity;//null,
        private String UsingQuantity;//null,
        private String InventoryQuantity;//null,
        private String Unit;//"",
        private String Recipients;//"",
        private String Keeper;//"",
        private String Remark;//"",
        private String FirmID;//"F000000000000001",
        private String DataUploadTime;//"2014-04-03T15:25:08.253",
        private String Status;//true
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		public String getGenericName() {
			return GenericName;
		}
		public void setGenericName(String genericName) {
			GenericName = genericName;
		}
		public String getFoodAdditiveID() {
			return FoodAdditiveID;
		}
		public void setFoodAdditiveID(String foodAdditiveID) {
			FoodAdditiveID = foodAdditiveID;
		}
		public String getSpecification() {
			return Specification;
		}
		public void setSpecification(String specification) {
			Specification = specification;
		}
		public String getShelfLife() {
			return ShelfLife;
		}
		public void setShelfLife(String shelfLife) {
			ShelfLife = shelfLife;
		}
		public String getBatchNumber() {
			return BatchNumber;
		}
		public void setBatchNumber(String batchNumber) {
			BatchNumber = batchNumber;
		}
		public String getSupplierName() {
			return SupplierName;
		}
		public void setSupplierName(String supplierName) {
			SupplierName = supplierName;
		}
		public String getSupplierContact() {
			return SupplierContact;
		}
		public void setSupplierContact(String supplierContact) {
			SupplierContact = supplierContact;
		}
		public String getPurchaser() {
			return Purchaser;
		}
		public void setPurchaser(String purchaser) {
			Purchaser = purchaser;
		}
		public String getUsingTime() {
			return UsingTime;
		}
		public void setUsingTime(String usingTime) {
			UsingTime = usingTime;
		}
		public String getPurchaseQuantity() {
			return PurchaseQuantity;
		}
		public void setPurchaseQuantity(String purchaseQuantity) {
			PurchaseQuantity = purchaseQuantity;
		}
		public String getUsingQuantity() {
			return UsingQuantity;
		}
		public void setUsingQuantity(String usingQuantity) {
			UsingQuantity = usingQuantity;
		}
		public String getInventoryQuantity() {
			return InventoryQuantity;
		}
		public void setInventoryQuantity(String inventoryQuantity) {
			InventoryQuantity = inventoryQuantity;
		}
		public String getUnit() {
			return Unit;
		}
		public void setUnit(String unit) {
			Unit = unit;
		}
		public String getRecipients() {
			return Recipients;
		}
		public void setRecipients(String recipients) {
			Recipients = recipients;
		}
		public String getKeeper() {
			return Keeper;
		}
		public void setKeeper(String keeper) {
			Keeper = keeper;
		}
		public String getRemark() {
			return Remark;
		}
		public void setRemark(String remark) {
			Remark = remark;
		}
		public String getFirmID() {
			return FirmID;
		}
		public void setFirmID(String firmID) {
			FirmID = firmID;
		}
		public String getDataUploadTime() {
			return DataUploadTime;
		}
		public void setDataUploadTime(String dataUploadTime) {
			DataUploadTime = dataUploadTime;
		}
		public String getStatus() {
			return Status;
		}
		public void setStatus(String status) {
			Status = status;
		}
        
	}
}
