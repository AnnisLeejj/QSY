package com.heking.qsy.complaintreporting;

import java.io.Serializable;
import java.util.ArrayList;

public class ComplaintReportingBean {

private String state;
private ArrayList<Data> Data;

public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}


public ArrayList<Data> getData() {
	return Data;
}

public void setData(ArrayList<Data> data) {
	Data = data;
}

public class Data implements Serializable{
	private String ID;// 1,
    private String Tile;// "投诉企业",
    private String Content;// "药品过期",
    private String Complainant;// "小红",
    private String Address;// null,
    private String CredentialsNumber;// null,
    private String ComplainantTime;// "2016-08-02T17:13:16.59",
    private String CredentialsType;// null,
    private String Email;// null,
    private String PhoneNumber;// "15723374727",
    private String PostalCode;// null,
    private String Status;// null,
    private String Defendant;// "投诉企业",
    private String DefendantAddress;// null,
    private String ComplaintSuggestionType;// null,
    private String IsPublish;// true,
    private String TheComplainantReward;// "奖励500元人民币",
    private String TheProcessingResults;// "在2016年8月31日前回收处理药品，并销毁",
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getTile() {
		return Tile;
	}
	public void setTile(String tile) {
		Tile = tile;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getComplainant() {
		return Complainant;
	}
	public void setComplainant(String complainant) {
		Complainant = complainant;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getCredentialsNumber() {
		return CredentialsNumber;
	}
	public void setCredentialsNumber(String credentialsNumber) {
		CredentialsNumber = credentialsNumber;
	}
	public String getComplainantTime() {
		return ComplainantTime;
	}
	public void setComplainantTime(String complainantTime) {
		ComplainantTime = complainantTime;
	}
	public String getCredentialsType() {
		return CredentialsType;
	}
	public void setCredentialsType(String credentialsType) {
		CredentialsType = credentialsType;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getPostalCode() {
		return PostalCode;
	}
	public void setPostalCode(String postalCode) {
		PostalCode = postalCode;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getDefendant() {
		return Defendant;
	}
	public void setDefendant(String defendant) {
		Defendant = defendant;
	}
	public String getDefendantAddress() {
		return DefendantAddress;
	}
	public void setDefendantAddress(String defendantAddress) {
		DefendantAddress = defendantAddress;
	}
	public String getComplaintSuggestionType() {
		return ComplaintSuggestionType;
	}
	public void setComplaintSuggestionType(String complaintSuggestionType) {
		ComplaintSuggestionType = complaintSuggestionType;
	}
	public String getIsPublish() {
		return IsPublish;
	}
	public void setIsPublish(String isPublish) {
		IsPublish = isPublish;
	}
	public String getTheComplainantReward() {
		return TheComplainantReward;
	}
	public void setTheComplainantReward(String theComplainantReward) {
		TheComplainantReward = theComplainantReward;
	}
	public String getTheProcessingResults() {
		return TheProcessingResults;
	}
	public void setTheProcessingResults(String theProcessingResults) {
		TheProcessingResults = theProcessingResults;
	}
	
}
 
}
