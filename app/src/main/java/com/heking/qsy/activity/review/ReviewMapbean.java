package com.heking.qsy.activity.review;

import java.util.ArrayList;

public class ReviewMapbean {
/**
 * {"state;//"true",
private String MaxPage;//1,
private String Data;//[{
private String sID;//2,
private String FirmID;//"false",
private String FileURL;//"ImageFile/Review/1468289727991.jpg",
private String ReviewContent;//"不错",
private String ReviewTime;//"2016-07-12 10:15:28",
private String AddressUID;//"B0FFG0BB5P",
private String Meid;//"A0000038DB5CF2D"},
private String Score;//"0"
 */
	
	
	private String state;
	private String MaxPage;
	private ArrayList<Data> Data;
	
public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMaxPage() {
		return MaxPage;
	}

	public void setMaxPage(String maxPage) {
		MaxPage = maxPage;
	}

	public ArrayList<Data> getData() {
		return Data;
	}

	public void setData(ArrayList<Data> data) {
		Data = data;
	}

public class Data{
	 private String ID;// 6,
      private String FirmID;// "false",
      private String FileName;// "",
      private String ReviewContent;// "",
      private String ReviewTime;// "2016-08-04T09:56:22.903",
      private String AddressUID;// "B0FFH0EYHY",
      private String Meid;// "A0000038DB5CF2D",
      private String FileID;// null,
      private String Score;// 7,
      private String stream;// null
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getFirmID() {
		return FirmID;
	}
	public void setFirmID(String firmID) {
		FirmID = firmID;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getReviewContent() {
		return ReviewContent;
	}
	public void setReviewContent(String reviewContent) {
		ReviewContent = reviewContent;
	}
	public String getReviewTime() {
		return ReviewTime;
	}
	public void setReviewTime(String reviewTime) {
		ReviewTime = reviewTime;
	}
	public String getAddressUID() {
		return AddressUID;
	}
	public void setAddressUID(String addressUID) {
		AddressUID = addressUID;
	}
	public String getMeid() {
		return Meid;
	}
	public void setMeid(String meid) {
		Meid = meid;
	}
	public String getFileID() {
		return FileID;
	}
	public void setFileID(String fileID) {
		FileID = fileID;
	}
	public String getScore() {
		return Score;
	}
	public void setScore(String score) {
		Score = score;
	}
	public String getStream() {
		return stream;
	}
	public void setStream(String stream) {
		this.stream = stream;
	}
	
}
}
