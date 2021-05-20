package com.bioTools.demo.entities;


public class InformationSheet {
	private String orderNO;
	private String name;
	private String workUnit;
	private String telephone;
	private String shunFengNO;
	private String weChat;
	private String state;
	private String time;
	private String reponsibleMan;
	private String isFree;
	public String getOrderNO() {
		return orderNO;
	}
	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getShunFengNO() {
		return shunFengNO;
	}
	public void setShunFengNO(String shunFengNO) {
		this.shunFengNO = shunFengNO;
	}
	public String getWeChat() {
		return weChat;
	}
	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getReponsibleMan() {
		return reponsibleMan;
	}
	public void setReponsibleMan(String reponsibleMan) {
		this.reponsibleMan = reponsibleMan;
	}
	public String getIsFree() {
		return isFree;
	}
	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}
	@Override
	public String toString() {
		return "InformationSheet [orderNO=" + orderNO + ", name=" + name + ", workUnit=" + workUnit + ", telephone="
				+ telephone + ", shunFengNO=" + shunFengNO + ", weChat=" + weChat + ", state=" + state + ", time="
				+ time + ", reponsibleMan=" + reponsibleMan + ", isFree=" + isFree + "]";
	}
	
	

}
