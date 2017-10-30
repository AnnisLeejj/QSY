package com.heking.qsy.activity.regulatory.tab;

import java.io.Serializable;
import java.util.ArrayList;

public class TB_BuLiangFanyingJianCe {
	
private ArrayList<data> data;

	
	public ArrayList<data> getData() {
	return data;
}


public void setData(ArrayList<data> data) {
	this.data = data;
}
	public class data implements Serializable{
		private String ID;
		private String ReportDateTime;
        private String ReporterName;
        private String ADRStatusName;
        private String AreaName;
        private String PatientName;
        private String PatientBirthDate;
        private String HospitalName;
//        private String ID;//2,
        private String ReportOrganizationType;//2,
        private String AreaID;//"511123 ",
        private String FirmID;//"F511323000000003",
//        private String PatientName;//"向亚红",
        private String PatientGender;//false,
        private String Nationality;//"",
        private String PatientContact;//"",
        private String HospitalizeNumber;//"",
        private String FamilyADR;//3,
        private String FamilyADRDescription;//"",
        private String HistoryADR;//3,
        private String HistoryADRDescription;//"",
//        private String HospitalName;//"犍为县人民医院",
        private String ADRProcedureDescription;//"皮肤起红疹，很痒",
        private String OriginalDisease;//"",
        private String SimilarADR;//true,
        private String SimilarADRDomestic;//"",
        private String SimilarADRForeign;//"",
        private String ReporterID;//"U000000000000003",
//        private String ReporterName;//"系统管理员",
        private String ReporterIDNumber;//"",
        private String ReporterCareer;//"",
        private String ReporterTitle;//"",
        private String Remark;//"",
        private String Status;//2,
        private String HappenDateTime;//"2016-09-26 16:07:48",
//        private String AreaName;//"犍为县",
        private String InfoChannel;//"口述",
        private String ADRStatus;//"一般",
        private String ADRResult;//"治愈",
        private String OriginalDiseaseEffect;//"有",
        private String ReporterEvaluation;//"肯定"
        
		public String getReportOrganizationType() {
			return ReportOrganizationType;
		}
		public void setReportOrganizationType(String reportOrganizationType) {
			ReportOrganizationType = reportOrganizationType;
		}
		public String getAreaID() {
			return AreaID;
		}
		public void setAreaID(String areaID) {
			AreaID = areaID;
		}
		public String getFirmID() {
			return FirmID;
		}
		public void setFirmID(String firmID) {
			FirmID = firmID;
		}
		public String getPatientGender() {
			return PatientGender;
		}
		public void setPatientGender(String patientGender) {
			PatientGender = patientGender;
		}
		public String getNationality() {
			return Nationality;
		}
		public void setNationality(String nationality) {
			Nationality = nationality;
		}
		public String getPatientContact() {
			return PatientContact;
		}
		public void setPatientContact(String patientContact) {
			PatientContact = patientContact;
		}
		public String getHospitalizeNumber() {
			return HospitalizeNumber;
		}
		public void setHospitalizeNumber(String hospitalizeNumber) {
			HospitalizeNumber = hospitalizeNumber;
		}
		public String getFamilyADR() {
			return FamilyADR;
		}
		public void setFamilyADR(String familyADR) {
			FamilyADR = familyADR;
		}
		public String getFamilyADRDescription() {
			return FamilyADRDescription;
		}
		public void setFamilyADRDescription(String familyADRDescription) {
			FamilyADRDescription = familyADRDescription;
		}
		public String getHistoryADR() {
			return HistoryADR;
		}
		public void setHistoryADR(String historyADR) {
			HistoryADR = historyADR;
		}
		public String getHistoryADRDescription() {
			return HistoryADRDescription;
		}
		public void setHistoryADRDescription(String historyADRDescription) {
			HistoryADRDescription = historyADRDescription;
		}
		public String getADRProcedureDescription() {
			return ADRProcedureDescription;
		}
		public void setADRProcedureDescription(String aDRProcedureDescription) {
			ADRProcedureDescription = aDRProcedureDescription;
		}
		public String getOriginalDisease() {
			return OriginalDisease;
		}
		public void setOriginalDisease(String originalDisease) {
			OriginalDisease = originalDisease;
		}
		public String getSimilarADR() {
			return SimilarADR;
		}
		public void setSimilarADR(String similarADR) {
			SimilarADR = similarADR;
		}
		public String getSimilarADRDomestic() {
			return SimilarADRDomestic;
		}
		public void setSimilarADRDomestic(String similarADRDomestic) {
			SimilarADRDomestic = similarADRDomestic;
		}
		public String getSimilarADRForeign() {
			return SimilarADRForeign;
		}
		public void setSimilarADRForeign(String similarADRForeign) {
			SimilarADRForeign = similarADRForeign;
		}
		public String getReporterID() {
			return ReporterID;
		}
		public void setReporterID(String reporterID) {
			ReporterID = reporterID;
		}
		public String getReporterIDNumber() {
			return ReporterIDNumber;
		}
		public void setReporterIDNumber(String reporterIDNumber) {
			ReporterIDNumber = reporterIDNumber;
		}
		public String getReporterCareer() {
			return ReporterCareer;
		}
		public void setReporterCareer(String reporterCareer) {
			ReporterCareer = reporterCareer;
		}
		public String getReporterTitle() {
			return ReporterTitle;
		}
		public void setReporterTitle(String reporterTitle) {
			ReporterTitle = reporterTitle;
		}
		public String getRemark() {
			return Remark;
		}
		public void setRemark(String remark) {
			Remark = remark;
		}
		public String getStatus() {
			return Status;
		}
		public void setStatus(String status) {
			Status = status;
		}
		public String getHappenDateTime() {
			return HappenDateTime;
		}
		public void setHappenDateTime(String happenDateTime) {
			HappenDateTime = happenDateTime;
		}
		public String getInfoChannel() {
			return InfoChannel;
		}
		public void setInfoChannel(String infoChannel) {
			InfoChannel = infoChannel;
		}
		public String getADRStatus() {
			return ADRStatus;
		}
		public void setADRStatus(String aDRStatus) {
			ADRStatus = aDRStatus;
		}
		public String getADRResult() {
			return ADRResult;
		}
		public void setADRResult(String aDRResult) {
			ADRResult = aDRResult;
		}
		public String getOriginalDiseaseEffect() {
			return OriginalDiseaseEffect;
		}
		public void setOriginalDiseaseEffect(String originalDiseaseEffect) {
			OriginalDiseaseEffect = originalDiseaseEffect;
		}
		public String getReporterEvaluation() {
			return ReporterEvaluation;
		}
		public void setReporterEvaluation(String reporterEvaluation) {
			ReporterEvaluation = reporterEvaluation;
		}
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		public String getReportDateTime() {
			return ReportDateTime;
		}
		public void setReportDateTime(String reportDateTime) {
			ReportDateTime = reportDateTime;
		}
		public String getReporterName() {
			return ReporterName;
		}
		public void setReporterName(String reporterName) {
			ReporterName = reporterName;
		}
		public String getADRStatusName() {
			return ADRStatusName;
		}
		public void setADRStatusName(String aDRStatusName) {
			ADRStatusName = aDRStatusName;
		}
		public String getAreaName() {
			return AreaName;
		}
		public void setAreaName(String areaName) {
			AreaName = areaName;
		}
		public String getPatientName() {
			return PatientName;
		}
		public void setPatientName(String patientName) {
			PatientName = patientName;
		}
		public String getPatientBirthDate() {
			return PatientBirthDate;
		}
		public void setPatientBirthDate(String patientBirthDate) {
			PatientBirthDate = patientBirthDate;
		}
		public String getHospitalName() {
			return HospitalName;
		}
		public void setHospitalName(String hospitalName) {
			HospitalName = hospitalName;
		}
	
	}}
