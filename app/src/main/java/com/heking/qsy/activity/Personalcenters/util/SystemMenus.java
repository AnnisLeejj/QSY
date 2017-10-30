package com.heking.qsy.activity.Personalcenters.util;

import java.io.Serializable;

public class SystemMenus  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID ;
    private int ParentID ;
    private String ProductKey ;
    private String Code ;
    private String Name ;
    private String Description ;
    private String Url ;
    private String Target ;
    private String IconClassName ;
    private int SortOrder ;
    private boolean Visible ;
    private boolean Deleted ;
    
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getParentID() {
		return ParentID;
	}
	public void setParentID(int parentID) {
		ParentID = parentID;
	}
	public String getProductKey() {
		return ProductKey;
	}
	public void setProductKey(String productKey) {
		ProductKey = productKey;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public String getTarget() {
		return Target;
	}
	public void setTarget(String target) {
		Target = target;
	}
	public String getIconClassName() {
		return IconClassName;
	}
	public void setIconClassName(String iconClassName) {
		IconClassName = iconClassName;
	}
	public int getSortOrder() {
		return SortOrder;
	}
	public void setSortOrder(int sortOrder) {
		SortOrder = sortOrder;
	}
	public boolean isVisible() {
		return Visible;
	}
	public void setVisible(boolean visible) {
		Visible = visible;
	}
	public boolean isDeleted() {
		return Deleted;
	}
	public void setDeleted(boolean deleted) {
		Deleted = deleted;
	}
    
    

}
