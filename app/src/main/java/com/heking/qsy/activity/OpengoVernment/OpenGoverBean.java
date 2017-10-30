package com.heking.qsy.activity.OpengoVernment;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class OpenGoverBean {
//state":"true","MaxPage":1,"Data":[{"Title":"关于2014年政府部门政务公开概述","Summary
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

public class Data    {
	
	private String Title;
	private String Summary;
	private String Source;
	private String TitleImageUrl;
	private String PublishTime;

	public String getPublishTime() {
		return PublishTime;
	}

	public void setPublishTime(String publishTime) {
		PublishTime = publishTime;
	}

	public String getTitleImageUrl() {
		return TitleImageUrl;
	}
	public void setTitleImageUrl(String titleImageUrl) {
		TitleImageUrl = titleImageUrl;
	}
	public String getSource() {
		return Source;
	}
	public void setSource(String Source) {
		this.Source = Source;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getSummary() {
		return Summary;
	}
	public void setSummary(String Summary) {
		this.Summary = Summary;
	}


}
}
