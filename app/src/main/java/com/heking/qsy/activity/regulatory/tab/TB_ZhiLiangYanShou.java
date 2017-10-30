package com.heking.qsy.activity.regulatory.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_ZhiLiangYanShou {
	
private ArrayList<data> data;

	
	public ArrayList<data> getData() {
	return data;
}


public void setData(ArrayList<data> data) {
	this.data = data;
}


	public class data implements Serializable{
        private String FirmName;//"南充市兴平药品零售连锁有限公司蓬安第10店",
        private String ID;//174465,
        private String UploadTime;//"2016-02-26 20:04:06",
        private String Name;//"辛芩颗粒",
        private String IsMedicine;//true,
        private String ExpiredDate;//"2018-10-31 00:00:01",
        private String Quantity;//5,
        private String Specification;//"10g*20袋",
        private String BatchNumber;//"151110",
        private String AcceptanceResult;//"",
        private String AcceptanceTime;//"2016-02-26 20:09:20",
        private String StoreRoomName;//"中药库",
        private String PackingConditions;//"",
        private String ProductionDate;//"2015-10-31 00:00:01",
        private String QualityConditions;//"",
        private String Accepter;//"余世莉"
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
		public String getAcceptanceResult() {
			return AcceptanceResult;
		}
		public void setAcceptanceResult(String acceptanceResult) {
			AcceptanceResult = acceptanceResult;
		}
		public String getAcceptanceTime() {
			return AcceptanceTime;
		}
		public void setAcceptanceTime(String acceptanceTime) {
			AcceptanceTime = acceptanceTime;
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
		public String getAccepter() {
			return Accepter;
		}
		public void setAccepter(String accepter) {
			Accepter = accepter;
		}
        
        	}}
