package com.heking.qsy.activity.Patrol;

import java.util.List;

public class InspectTableBean {

	private int ID;
	private String TableName;
	private String FirmID;
	private String FirmType;
	private String FirmName;
	private String InspectPeople;
	private String CheckResultContent;
	private String InspectOpinion;
	private String FirmSign;
	private String InspectSign;
	private String FirmTypeName;
	private String ItemID;
	private boolean Deleted;// ": false,
	private String InspectResultTable;

	private List<PatrolTableGroup> Projects;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTableName() {
		return TableName;
	}

	public String getFirmTypeName() {
		return FirmTypeName;
	}

	public void setFirmTypeName(String firmTypeName) {
		FirmTypeName = firmTypeName;
	}

	public void setTableName(String tableName) {
		TableName = tableName;
	}

	public String getItemID() {
		return ItemID;
	}

	public void setItemID(String itemID) {
		ItemID = itemID;
	}

	public boolean isDeleted() {
		return Deleted;
	}

	public void setDeleted(boolean deleted) {
		Deleted = deleted;
	}

	public List<PatrolTableGroup> getProjects() {
		return Projects;
	}

	public void setProjects(List<PatrolTableGroup> projects) {
		Projects = projects;
	}

	public String getFirmID() {
		return FirmID;
	}

	public void setFirmID(String firmID) {
		FirmID = firmID;
	}

	public String getFirmType() {
		return FirmType;
	}

	public void setFirmType(String firmType) {
		FirmType = firmType;
	}

	public String getFirmName() {
		return FirmName;
	}

	public void setFirmName(String firmName) {
		FirmName = firmName;
	}

	public String getInspectPeople() {
		return InspectPeople;
	}

	public void setInspectPeople(String inspectPeople) {
		InspectPeople = inspectPeople;
	}

	public String getCheckResultContent() {
		return CheckResultContent;
	}

	public void setCheckResultContent(String checkResultContent) {
		CheckResultContent = checkResultContent;
	}

	public String getInspectOpinion() {
		return InspectOpinion;
	}

	public void setInspectOpinion(String inspectOpinion) {
		InspectOpinion = inspectOpinion;
	}

	public String getFirmSign() {
		return FirmSign;
	}

	public void setFirmSign(String firmSign) {
		FirmSign = firmSign;
	}

	public String getInspectSign() {
		return InspectSign;
	}

	public void setInspectSign(String inspectSign) {
		InspectSign = inspectSign;
	}

	public String getInspectResultTable() {
		return InspectResultTable;
	}

	public void setInspectResultTable(String inspectResultTable) {
		InspectResultTable = inspectResultTable;
	}

}
