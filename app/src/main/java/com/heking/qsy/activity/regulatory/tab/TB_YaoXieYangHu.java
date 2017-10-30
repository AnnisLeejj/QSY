package com.heking.qsy.activity.regulatory.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_YaoXieYangHu {
	
private ArrayList<data> data;

	
	public ArrayList<data> getData() {
	return data;
}


public void setData(ArrayList<data> data) {
	this.data = data;
}


	public class data implements Serializable{ 
		private String FirmName;//"南充市兴平药品零售连锁有限公司蓬安第10店",
        private String ID;//2,
        private String UploadTime;//"2013-10-10 00:00:00",
        private String Name;//"照射仪",
        private String IsMedicine;//false,
        private String ExpiredDate;//"2015-05-06 00:00:00",
        private String Quantity;//20,
        private String MaintainResult;//"放在干燥的地方",
        private String MaintainTime;//"2013-09-05 00:00:00",
        private String MaintenancePeople;//"陈早",
        private String StoreRoomName;//"仓库B",
        private String PackingConditions;//"包装好",
        private String ProductionDate;//"2013-02-19 00:00:00",
        private String QualityConditions;//"一等品"
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
		public String getMaintainResult() {
			return MaintainResult;
		}
		public void setMaintainResult(String maintainResult) {
			MaintainResult = maintainResult;
		}
		public String getMaintainTime() {
			return MaintainTime;
		}
		public void setMaintainTime(String maintainTime) {
			MaintainTime = maintainTime;
		}
		public String getMaintenancePeople() {
			return MaintenancePeople;
		}
		public void setMaintenancePeople(String maintenancePeople) {
			MaintenancePeople = maintenancePeople;
		}
		public String getStoreRoomName() {
			return StoreRoomName;
		}
		public void setStoreRoomName(String storeRoomName) {
			StoreRoomName = storeRoomName;
		}
		public String getPackingConditions() {
			return PackingConditions;
		}
		public void setPackingConditions(String packingConditions) {
			PackingConditions = packingConditions;
		}
		public String getProductionDate() {
			return ProductionDate;
		}
		public void setProductionDate(String productionDate) {
			ProductionDate = productionDate;
		}
		public String getQualityConditions() {
			return QualityConditions;
		}
		public void setQualityConditions(String qualityConditions) {
			QualityConditions = qualityConditions;
		}
        
        	}}
