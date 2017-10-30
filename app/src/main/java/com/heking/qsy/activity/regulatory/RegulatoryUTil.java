package com.heking.qsy.activity.regulatory;

import java.io.Serializable;
/**
 * 参数对象类，
 * @variable name 名称
 * @variable Nametype 名称说明
 * @variable State 状态
 * @author jhw
 *
 */
public class RegulatoryUTil implements Serializable{
	  private String Name;
	  private String Nametype;
	  private int State;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getNametype() {
		return Nametype;
	}
	public void setNametype(String nametype) {
		Nametype = nametype;
	}
	public int getState() {
		return State;
	}
	public void setState(int state) {
		State = state;
	}
	  
	   
	   
 }