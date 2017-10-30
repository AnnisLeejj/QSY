package com.heking.qsy.activity.regulatory.Food.tab.Details;

import java.util.ArrayList;

public class TB_FoodPurchasedDetails {
    private ArrayList<Data> Data;


    public ArrayList<Data> getData() {
        return Data;
    }


    public void setData(ArrayList<Data> data) {
        Data = data;
    }


    public class Data {
        private String ID;//{ get; set; }

        private String PurchasedID;//{ get; set; }

        private String TicketNumber;//{ get; set; }

        private String FoodID;//{ get; set; }

        private String Quantity;//{ get; set; }

        private String WarehousingPrice;//{ get; set; }

        private String SalesPrice;//{ get; set; }

        private String CargoSpace;//{ get; set; }

        private String BatchNumber;//{ get; set; }

        private String ProductionDate;//{ get; set; }

        private String EffectiveDate;//{ get; set; }

        private String AcceptancePeople;//{ get; set; }

        private String Buyer;//{ get; set; }

        private String Certificate;//{ get; set; }

        private String AppearanceQualit;//{ get; set; }

        private String AcceptanceConclusion;//{ get; set; }

        private String Remark;//{ get; set; }

        private String MeasurementUnit;//{ get; set; }

        private String ShelfLife;//{ get; set; }

        private String SupplierID;//{ get; set; }

        private String SupplierName;//{ get; set; }

        private String GenericName;//{ get; set; }

        private String getID() {
            return ID;
        }

        public void setID(String iD) {
            ID = iD;
        }

        public String getPurchasedID() {
            return PurchasedID;
        }

        public void setPurchasedID(String purchasedID) {
            PurchasedID = purchasedID;
        }

        public String getTicketNumber() {
            return TicketNumber;
        }

        public void setTicketNumber(String ticketNumber) {
            TicketNumber = ticketNumber;
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

        public String getWarehousingPrice() {
            return WarehousingPrice;
        }

        public void setWarehousingPrice(String warehousingPrice) {
            WarehousingPrice = warehousingPrice;
        }

        public String getSalesPrice() {
            return SalesPrice;
        }

        public void setSalesPrice(String salesPrice) {
            SalesPrice = salesPrice;
        }

        public String getCargoSpace() {
            return CargoSpace;
        }

        public void setCargoSpace(String cargoSpace) {
            CargoSpace = cargoSpace;
        }

        public String getBatchNumber() {
            return BatchNumber;
        }

        public void setBatchNumber(String batchNumber) {
            BatchNumber = batchNumber;
        }

        public String getProductionDate() {
            return ProductionDate;
        }

        public void setProductionDate(String productionDate) {
            ProductionDate = productionDate;
        }

        public String getEffectiveDate() {
            return EffectiveDate;
        }

        public void setEffectiveDate(String effectiveDate) {
            EffectiveDate = effectiveDate;
        }

        public String getAcceptancePeople() {
            return AcceptancePeople;
        }

        public void setAcceptancePeople(String acceptancePeople) {
            AcceptancePeople = acceptancePeople;
        }

        public String getBuyer() {
            return Buyer;
        }

        public void setBuyer(String buyer) {
            Buyer = buyer;
        }

        public String getCertificate() {
            return Certificate;
        }

        public void setCertificate(String certificate) {
            Certificate = certificate;
        }

        public String getAppearanceQualit() {
            return AppearanceQualit;
        }

        public void setAppearanceQualit(String appearanceQualit) {
            AppearanceQualit = appearanceQualit;
        }

        public String getAcceptanceConclusion() {
            return AcceptanceConclusion;
        }

        public void setAcceptanceConclusion(String acceptanceConclusion) {
            AcceptanceConclusion = acceptanceConclusion;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
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

        public String getSupplierName() {
            return SupplierName;
        }

        public void setSupplierName(String supplierName) {
            SupplierName = supplierName;
        }

        public String getGenericName() {
            return GenericName;
        }

        public void setGenericName(String genericName) {
            GenericName = genericName;
        }

    }
}
