package com.heking.qsy.activity.Patrol;

import java.util.List;

public class PatrolTableChild {
	int ID;// ": 1,
	String InspectContent;// ": "食品生产许可证、营业执照是否齐全、是否在有效期内,许可生产范围和实际生产范围是否一致。",
	String ItemCode;
	int Rate;// ": 0,
	String Remark;// ": null,
	List<InspectAttachmentsBean> InspectAttachments;
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getInspectContent() {
		return InspectContent;
	}
	public void setInspectContent(String inspectContent) {
		InspectContent = inspectContent;
	}
	public String getItemCode() {
		return ItemCode;
	}
	public void setItemCode(String itemCode) {
		ItemCode = itemCode;
	}
	public int getRate() {
		return Rate;
	}
	public void setRate(int rate) {
		Rate = rate;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public List<InspectAttachmentsBean> getInspectAttachments() {
		return InspectAttachments;
	}
	public void setInspectAttachments(List<InspectAttachmentsBean> inspectAttachments) {
		InspectAttachments = inspectAttachments;
	}
	

}
