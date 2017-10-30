package com.heking.qsy.activity.regulatory.Food.Bevera.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_BeveraRawMaterialInventory {

	private ArrayList<Data> Data;
	
	public ArrayList<Data> getData() {
		return Data;
	}

	public void setData(ArrayList<Data> data) {
		Data = data;
	}

	public class Data implements Serializable{
        private String ID;//;//17,
        private String Name;//;//"平菇",
        private String MaterialID;//;//null,
        private String InDate;//;//"2015-05-05T00:00:00",
        private String SupplierName;//;//"南充华友食品批发有限公司 ",
        private String Quantity;//;//50,
        private String UnitPrice;//;//12,
        private String TotalAmount;//;//12,
        private String SensoryInspection;//;//"正常",
        private String ShelfLife;//;//"15天",
        private String CableCardMaterial;//;//"",
        private String Purchaser;//;//"李涛",
        private String Keeper;//;//"张佳",
        private String FirmID;//;//"F000000000000001",
        private String DataUploadTime;//;//"2015-06-03T11:15:55",
        private String Status;//;//true
        private String OutDate;//"2016-10-24T00:00:00",
        private String CheckGoods;//"完好无损",
        private String Recipients;//"向亚红",
        private String SurplusGoods;//"2",
        
		public String getOutDate() {
			return OutDate;
		}
		public void setOutDate(String outDate) {
			OutDate = outDate;
		}
		public String getCheckGoods() {
			return CheckGoods;
		}
		public void setCheckGoods(String checkGoods) {
			CheckGoods = checkGoods;
		}
		public String getRecipients() {
			return Recipients;
		}
		public void setRecipients(String recipients) {
			Recipients = recipients;
		}
		public String getSurplusGoods() {
			return SurplusGoods;
		}
		public void setSurplusGoods(String surplusGoods) {
			SurplusGoods = surplusGoods;
		}
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public String getMaterialID() {
			return MaterialID;
		}
		public void setMaterialID(String materialID) {
			MaterialID = materialID;
		}
		public String getInDate() {
			return InDate;
		}
		public void setInDate(String inDate) {
			InDate = inDate;
		}
		public String getSupplierName() {
			return SupplierName;
		}
		public void setSupplierName(String supplierName) {
			SupplierName = supplierName;
		}
		public String getQuantity() {
			return Quantity;
		}
		public void setQuantity(String quantity) {
			Quantity = quantity;
		}
		public String getUnitPrice() {
			return UnitPrice;
		}
		public void setUnitPrice(String unitPrice) {
			UnitPrice = unitPrice;
		}
		public String getTotalAmount() {
			return TotalAmount;
		}
		public void setTotalAmount(String totalAmount) {
			TotalAmount = totalAmount;
		}
		public String getSensoryInspection() {
			return SensoryInspection;
		}
		public void setSensoryInspection(String sensoryInspection) {
			SensoryInspection = sensoryInspection;
		}
		public String getShelfLife() {
			return ShelfLife;
		}
		public void setShelfLife(String shelfLife) {
			ShelfLife = shelfLife;
		}
		public String getCableCardMaterial() {
			return CableCardMaterial;
		}
		public void setCableCardMaterial(String cableCardMaterial) {
			CableCardMaterial = cableCardMaterial;
		}
		public String getPurchaser() {
			return Purchaser;
		}
		public void setPurchaser(String purchaser) {
			Purchaser = purchaser;
		}
		public String getKeeper() {
			return Keeper;
		}
		public void setKeeper(String keeper) {
			Keeper = keeper;
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
