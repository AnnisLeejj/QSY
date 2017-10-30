package com.heking.qsy.activity.regulatory.details.type;

import java.util.ArrayList;

public class TB_yaopingType {
	private ArrayList<Data> Data;
	public ArrayList<Data> getData() {
		return Data;
	}
	public void setData(ArrayList<Data> data) {
		Data = data;
	}
	public class Data{
	  private String IsMedicine;// true,
      private String ID;// "M000000000002864",
      private String Name;// "奥美拉唑肠溶胶囊",
      private String Quantity;// 20,
      private String vUnit;// "盒",
      private String mUnit;// "",
      private String BatchNumber;// "14360544",
      private String SupplierName;// "四川省世康药业有限公司",
      private String ProductionDate;// "2016-05-01 00:00:00",
      private String ExpiredDate;// "2018-01-31 00:00:00",
      private String ElectronicSupervisionCode;
      private String Remark;// ""
      private String PrescriptionID;// 3191,
      private String IsSplittedValuation;// false,
      private String MedicineID;// "M000000000000056",
      private String MedicineName;// "琥乙红霉素片"
      
	public String getPrescriptionID() {
		return PrescriptionID;
	}
	public void setPrescriptionID(String prescriptionID) {
		PrescriptionID = prescriptionID;
	}
	public String getIsSplittedValuation() {
		return IsSplittedValuation;
	}
	public void setIsSplittedValuation(String isSplittedValuation) {
		IsSplittedValuation = isSplittedValuation;
	}
	public String getMedicineID() {
		return MedicineID;
	}
	public void setMedicineID(String medicineID) {
		MedicineID = medicineID;
	}
	public String getMedicineName() {
		return MedicineName;
	}
	public void setMedicineName(String medicineName) {
		MedicineName = medicineName;
	}
	public String getElectronicSupervisionCode() {
		return ElectronicSupervisionCode;
	}
	public void setElectronicSupervisionCode(String electronicSupervisionCode) {
		ElectronicSupervisionCode = electronicSupervisionCode;
	}
	public String getIsMedicine() {
		return IsMedicine;
	}
	public void setIsMedicine(String isMedicine) {
		IsMedicine = isMedicine;
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
	public String getQuantity() {
		return Quantity;
	}
	public void setQuantity(String quantity) {
		Quantity = quantity;
	}
	public String getvUnit() {
		return vUnit;
	}
	public void setvUnit(String vUnit) {
		this.vUnit = vUnit;
	}
	public String getmUnit() {
		return mUnit;
	}
	public void setmUnit(String mUnit) {
		this.mUnit = mUnit;
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
	public String getProductionDate() {
		return ProductionDate;
	}
	public void setProductionDate(String productionDate) {
		ProductionDate = productionDate;
	}
	public String getExpiredDate() {
		return ExpiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		ExpiredDate = expiredDate;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
      
}}
