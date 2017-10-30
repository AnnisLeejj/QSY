package com.heking.qsy.activity.regulatory.Food.Bevera.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_FoodBeveraWasteDisposal {

	private ArrayList<Data> Data;
	
	public ArrayList<Data> getData() {
		return Data;
	}

	public void setData(ArrayList<Data> data) {
		Data = data;
	}

	public class Data implements Serializable{
        private String ID;//5,
        private String Species;//"地沟油",
        private String Weight;//100000,
        private String ProcessingDate;//"2013-12-18T00:00:00",
        private String StorageContainers;//"垃圾桶",
        private String StorageLocation;//"厨房",
        private String Method;//"回收",
        private String UseFor;//"卖钱",
        private String Whereabouts;//"餐馆",
        private String DiningPeople;//"张杰",
        private String WasteReceivePeople;//"陈晓",
        private String WasteHandlePeople;//"陈晓",
        private String ReceivePeoplePhone;//"1520333968",
        private String Status;//true,
        private String FirmID;//"F000000000000069"
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		public String getSpecies() {
			return Species;
		}
		public void setSpecies(String species) {
			Species = species;
		}
		public String getWeight() {
			return Weight;
		}
		public void setWeight(String weight) {
			Weight = weight;
		}
		public String getProcessingDate() {
			return ProcessingDate;
		}
		public void setProcessingDate(String processingDate) {
			ProcessingDate = processingDate;
		}
		public String getStorageContainers() {
			return StorageContainers;
		}
		public void setStorageContainers(String storageContainers) {
			StorageContainers = storageContainers;
		}
		public String getStorageLocation() {
			return StorageLocation;
		}
		public void setStorageLocation(String storageLocation) {
			StorageLocation = storageLocation;
		}
		public String getMethod() {
			return Method;
		}
		public void setMethod(String method) {
			Method = method;
		}
		public String getUseFor() {
			return UseFor;
		}
		public void setUseFor(String useFor) {
			UseFor = useFor;
		}
		public String getWhereabouts() {
			return Whereabouts;
		}
		public void setWhereabouts(String whereabouts) {
			Whereabouts = whereabouts;
		}
		public String getDiningPeople() {
			return DiningPeople;
		}
		public void setDiningPeople(String diningPeople) {
			DiningPeople = diningPeople;
		}
		public String getWasteReceivePeople() {
			return WasteReceivePeople;
		}
		public void setWasteReceivePeople(String wasteReceivePeople) {
			WasteReceivePeople = wasteReceivePeople;
		}
		public String getWasteHandlePeople() {
			return WasteHandlePeople;
		}
		public void setWasteHandlePeople(String wasteHandlePeople) {
			WasteHandlePeople = wasteHandlePeople;
		}
		public String getReceivePeoplePhone() {
			return ReceivePeoplePhone;
		}
		public void setReceivePeoplePhone(String receivePeoplePhone) {
			ReceivePeoplePhone = receivePeoplePhone;
		}
		public String getStatus() {
			return Status;
		}
		public void setStatus(String status) {
			Status = status;
		}
		public String getFirmID() {
			return FirmID;
		}
		public void setFirmID(String firmID) {
			FirmID = firmID;
		}
        
        
        	
	}
}
