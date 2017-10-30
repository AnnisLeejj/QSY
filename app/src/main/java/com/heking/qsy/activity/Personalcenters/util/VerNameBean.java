package com.heking.qsy.activity.Personalcenters.util;

import java.util.ArrayList;

public class VerNameBean {
	private ArrayList<Data> Data;
	
	
	public ArrayList<Data> getData() {
		return Data;
	}


	public void setData(ArrayList<Data> data) {
		Data = data;
	}


	public class Data{
		
		private String ID;//2,
		private String Version;//2,
		private String VersionFileUrl;//"www.nini.com"
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		public String getVersion() {
			return Version;
		}
		public void setVersion(String version) {
			Version = version;
		}
		public String getVersionFileUrl() {
			return VersionFileUrl;
		}
		public void setVersionFileUrl(String versionFileUrl) {
			VersionFileUrl = versionFileUrl;
		}
		
	}

}
