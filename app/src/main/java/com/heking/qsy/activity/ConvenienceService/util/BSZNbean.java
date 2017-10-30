package com.heking.qsy.activity.ConvenienceService.util;

import java.util.ArrayList;

public class BSZNbean {
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
	private String Title;
	private String ComputerContent;
	private String PublishTime;
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getComputerContent() {
		return ComputerContent;
	}
	public void setComputerContent(String computerContent) {
		ComputerContent = computerContent;
	}
	public String getPublishTime() {
		return PublishTime;
	}
	public void setPublishTime(String publishTime) {
		PublishTime = publishTime;
	}
	
}
}
