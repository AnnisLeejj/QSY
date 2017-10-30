package com.heking.qsy.activity.regulatory.Food.Bevera.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_FoodBeveraTryToSample {

	private ArrayList<Data> Data;
	
	public ArrayList<Data> getData() {
		return Data;
	}

	public void setData(ArrayList<Data> data) {
		Data = data;
	}

	public class Data implements Serializable{
        private String ID;//7,
        private String FoodName;//"玉米粥",
        private String FoodID;//null,
        private String TasteTime;//"2015-05-05T00:00:00",
        private String TastePeople;//"大发明家",
        private String TasteCase;//"德玛西亚",
        private String RetentionSamplesPeople;//"虚空恐惧",
        private String RetentionSamplesTime;//1101,
        private String DestructionRetentionTime;//"2015-05-05T00:00:00",
        private String FirmID;//"F000000000000001",
        private String DataUploadTime;//"2015-06-03T11:15:55",
        private String Status;//true
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		public String getFoodName() {
			return FoodName;
		}
		public void setFoodName(String foodName) {
			FoodName = foodName;
		}
		public String getFoodID() {
			return FoodID;
		}
		public void setFoodID(String foodID) {
			FoodID = foodID;
		}
		public String getTasteTime() {
			return TasteTime;
		}
		public void setTasteTime(String tasteTime) {
			TasteTime = tasteTime;
		}
		public String getTastePeople() {
			return TastePeople;
		}
		public void setTastePeople(String tastePeople) {
			TastePeople = tastePeople;
		}
		public String getTasteCase() {
			return TasteCase;
		}
		public void setTasteCase(String tasteCase) {
			TasteCase = tasteCase;
		}
		public String getRetentionSamplesPeople() {
			return RetentionSamplesPeople;
		}
		public void setRetentionSamplesPeople(String retentionSamplesPeople) {
			RetentionSamplesPeople = retentionSamplesPeople;
		}
		public String getRetentionSamplesTime() {
			return RetentionSamplesTime;
		}
		public void setRetentionSamplesTime(String retentionSamplesTime) {
			RetentionSamplesTime = retentionSamplesTime;
		}
		public String getDestructionRetentionTime() {
			return DestructionRetentionTime;
		}
		public void setDestructionRetentionTime(String destructionRetentionTime) {
			DestructionRetentionTime = destructionRetentionTime;
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
