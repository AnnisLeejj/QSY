package com.heking.qsy.activity.regulatory.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_Chunfangjilu {
	
private ArrayList<data> data;

	
	public ArrayList<data> getData() {
	return data;
}


public void setData(ArrayList<data> data) {
	this.data = data;
}


	public class data implements Serializable{
		private String FirmName;//"南充市兴平药品零售连锁有限公司蓬安第10店",
        private String ID;//1,
        private String UploadTime;//"2014-01-25 11:04:17",
        private String DoctorName;//"张山",
        private String LocalCode;//"19",
        private String PrescriptionID;//"CF201308260001",
        private String PatientName;//"王勇",
        private String PatientAge;//"24",
        private String PatientGender;//"男",
        private String Department;//"蓬安县人民医院",
        private String ChargeType;//"",
        private String MedicineMan;//"管理员",
        private String ReviewedBy;//"管理员",
        private String DiagnosticResult;//"感冒",
        private String LocalLastedTime;//"2013-08-26 00:00:00",
        private String PrescriptionTime;//"2013-08-26 00:00:00"
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
		public String getDoctorName() {
			return DoctorName;
		}
		public void setDoctorName(String doctorName) {
			DoctorName = doctorName;
		}
		public String getLocalCode() {
			return LocalCode;
		}
		public void setLocalCode(String localCode) {
			LocalCode = localCode;
		}
		public String getPrescriptionID() {
			return PrescriptionID;
		}
		public void setPrescriptionID(String prescriptionID) {
			PrescriptionID = prescriptionID;
		}
		public String getPatientName() {
			return PatientName;
		}
		public void setPatientName(String patientName) {
			PatientName = patientName;
		}
		public String getPatientAge() {
			return PatientAge;
		}
		public void setPatientAge(String patientAge) {
			PatientAge = patientAge;
		}
		public String getPatientGender() {
			return PatientGender;
		}
		public void setPatientGender(String patientGender) {
			PatientGender = patientGender;
		}
		public String getDepartment() {
			return Department;
		}
		public void setDepartment(String department) {
			Department = department;
		}
		public String getChargeType() {
			return ChargeType;
		}
		public void setChargeType(String chargeType) {
			ChargeType = chargeType;
		}
		public String getMedicineMan() {
			return MedicineMan;
		}
		public void setMedicineMan(String medicineMan) {
			MedicineMan = medicineMan;
		}
		public String getReviewedBy() {
			return ReviewedBy;
		}
		public void setReviewedBy(String reviewedBy) {
			ReviewedBy = reviewedBy;
		}
		public String getDiagnosticResult() {
			return DiagnosticResult;
		}
		public void setDiagnosticResult(String diagnosticResult) {
			DiagnosticResult = diagnosticResult;
		}
		public String getLocalLastedTime() {
			return LocalLastedTime;
		}
		public void setLocalLastedTime(String localLastedTime) {
			LocalLastedTime = localLastedTime;
		}
		public String getPrescriptionTime() {
			return PrescriptionTime;
		}
		public void setPrescriptionTime(String prescriptionTime) {
			PrescriptionTime = prescriptionTime;
		}
        
	}}
