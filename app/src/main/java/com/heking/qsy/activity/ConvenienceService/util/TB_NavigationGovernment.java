package com.heking.qsy.activity.ConvenienceService.util;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_NavigationGovernment implements Serializable {

    private ArrayList<JsonData> JsonData;

    public ArrayList<JsonData> getJsonData() {
        return JsonData;
    }

    public void setJsonData(ArrayList<JsonData> jsonData) {
        JsonData = jsonData;
    }

    public class JsonData {
        private String ID;
        private String Name;
        private String Address;
        private String Phone;
        private String Delete;
        double longitude;
        double latitude;
        String city;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public String getDelete() {
            return Delete;
        }

        public void setDelete(String delete) {
            Delete = delete;
        }

    }

}
