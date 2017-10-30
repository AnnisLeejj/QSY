package com.heking.qsy.activity.regulatory.Food.Bevera.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_FoodBeveraCleaningAndDisinfection {

	private ArrayList<Data> Data;
	
	public ArrayList<Data> getData() {
		return Data;
	}

	public void setData(ArrayList<Data> data) {
		Data = data;
	}

	public class Data implements Serializable{
        private String ID;//10,
        private String CleanDate;//"2015-05-05T00:00:00",
        private String IsWash;//true,
        private String IsClear;//false,
        private String IsDisinfection;//true,
        private String IsRinse;//true,
        private String IsCooking;//true,
        private String IsIntoCabinet;//true,
        private String ChargePeople;//"黄师傅",
        private String FirmID;//"F000000000000001",
        private String DataUploadTime;//"2015-06-03T11:15:55",
        private String Status;//true
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		public String getCleanDate() {
			return CleanDate;
		}
		public void setCleanDate(String cleanDate) {
			CleanDate = cleanDate;
		}
		public String getIsWash() {
			return IsWash;
		}
		public void setIsWash(String isWash) {
			IsWash = isWash;
		}
		public String getIsClear() {
			return IsClear;
		}
		public void setIsClear(String isClear) {
			IsClear = isClear;
		}
		public String getIsDisinfection() {
			return IsDisinfection;
		}
		public void setIsDisinfection(String isDisinfection) {
			IsDisinfection = isDisinfection;
		}
		public String getIsRinse() {
			return IsRinse;
		}
		public void setIsRinse(String isRinse) {
			IsRinse = isRinse;
		}
		public String getIsCooking() {
			return IsCooking;
		}
		public void setIsCooking(String isCooking) {
			IsCooking = isCooking;
		}
		public String getIsIntoCabinet() {
			return IsIntoCabinet;
		}
		public void setIsIntoCabinet(String isIntoCabinet) {
			IsIntoCabinet = isIntoCabinet;
		}
		public String getChargePeople() {
			return ChargePeople;
		}
		public void setChargePeople(String chargePeople) {
			ChargePeople = chargePeople;
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
