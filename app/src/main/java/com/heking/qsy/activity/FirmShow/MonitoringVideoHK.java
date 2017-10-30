package com.heking.qsy.activity.FirmShow;

import java.util.ArrayList;



public class MonitoringVideoHK{
	ArrayList<User> User;
	
	public ArrayList<User> getUser() {
		return User;
	}

	public void setUser(ArrayList<User> user) {
		User = user;
	}

	public class User{
		
		private String id;
		private String servAddr;//="117.173.43.121:4443";
		private String userName;//="admin";
		private String password;//="heking11*29";
		private String version;
		private String Deleted;
		private String state;
		
		public String getServAddr() {
			return servAddr;
		}
		public void setServAddr(String servAddr) {
			this.servAddr = servAddr;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getDeleted() {
			return Deleted;
		}
		public void setDeleted(String Deleted) {
			this.Deleted = Deleted;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		
	}
	
	
	

}