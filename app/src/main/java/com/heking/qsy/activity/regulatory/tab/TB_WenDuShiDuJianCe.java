package com.heking.qsy.activity.regulatory.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_WenDuShiDuJianCe {
	
private ArrayList<data> data;

	
	public ArrayList<data> getData() {
	return data;
}


public void setData(ArrayList<data> data) {
	this.data = data;
}


	public class data implements Serializable{
		private String FirmName;//"南充市兴平药品零售连锁有限公司蓬安第10店",
        private String ID;//42194,
        private String UploadTime;//"2016-10-02 18:51:38",
        private String StoreRoomName;//"柜台1",
        private String Address;//"蓬安县政府街 政府门口右手旁（71-73号）",
        private String ExtractTime;//"2016-10-02 17:57:23",
        private String Temperature;//24,
        private String Humidity;//56
        private String TemperatureHigh;// 30,
        private String TemperatureLow;// 0,
        private String HumidityHigh;// 75,
        private String HumidityLow;// 35
     
        
        
		public String getTemperatureHigh() {
			return TemperatureHigh;
		}
		public void setTemperatureHigh(String temperatureHigh) {
			TemperatureHigh = temperatureHigh;
		}
		public String getTemperatureLow() {
			return TemperatureLow;
		}
		public void setTemperatureLow(String temperatureLow) {
			TemperatureLow = temperatureLow;
		}
		public String getHumidityHigh() {
			return HumidityHigh;
		}
		public void setHumidityHigh(String humidityHigh) {
			HumidityHigh = humidityHigh;
		}
		public String getHumidityLow() {
			return HumidityLow;
		}
		public void setHumidityLow(String humidityLow) {
			HumidityLow = humidityLow;
		}
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
		public String getStoreRoomName() {
			return StoreRoomName;
		}
		public void setStoreRoomName(String storeRoomName) {
			StoreRoomName = storeRoomName;
		}
		public String getAddress() {
			return Address;
		}
		public void setAddress(String address) {
			Address = address;
		}
		public String getExtractTime() {
			return ExtractTime;
		}
		public void setExtractTime(String extractTime) {
			ExtractTime = extractTime;
		}
		public String getTemperature() {
			return Temperature;
		}
		public void setTemperature(String temperature) {
			Temperature = temperature;
		}
		public String getHumidity() {
			return Humidity;
		}
		public void setHumidity(String humidity) {
			Humidity = humidity;
		}
        
	}}
