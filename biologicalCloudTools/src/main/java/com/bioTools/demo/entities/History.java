package com.bioTools.demo.entities;

public class History {
	
	private String historyid;
	private String datapath;
	private String dataname;
	private String picname;
	private String picpath;
	private String email;
	private String model;
	private String toolfunction;
	
	public String getHistoryid() {
		return historyid;
	}
	public void setHistoryid(String historyid) {
		this.historyid = historyid;
	}
	public String getDatapath() {
		return datapath;
	}
	public void setDatapath(String datapath) {
		this.datapath = datapath;
	}
	public String getDataname() {
		return dataname;
	}
	public void setDataname(String dataname) {
		this.dataname = dataname;
	}
	public void addDataname(String dataname) {
		if(this.dataname == null || this.dataname.equalsIgnoreCase(new String("null"))) {
			this.dataname = dataname;
		}else {
			this.dataname += "," + dataname;
		}
	}
	public String getPicname() {
		return picname;
	}
	public void setPicname(String picname) {
		this.picname = picname;
	}
	public void addPicname(String picname) {
		if(this.picname == null || this.picname.equalsIgnoreCase(new String("null"))) {
			this.picname = picname;
		}else {
			this.picname += "," + picname;
		}
	}
	public String getPicpath() {
		return picpath;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getToolfunction() {
		return toolfunction;
	}
	public void setToolfunction(String toolfunction) {
		this.toolfunction = toolfunction;
	}
	@Override
	public String toString() {
		return "History [historyid=" + historyid + ", datapath=" + datapath + ", dataname=" + dataname + ", picname="
				+ picname + ", picpath=" + picpath + ", email=" + email + ", model=" + model + ", toolfunction="
				+ toolfunction + "]";
	}
	
	
	
	

}
