package com.halfroom.distribution.persistence.vo;

import java.math.BigDecimal;

public class UserOrderVo {
	String branchsalerName;
	String levelName;
	String userName;
	String phone;
	String bookName;
	BigDecimal orderFee;
	String paySource;
	String payRole;
	String inName;
	String payTime;
	String tempSimplename;

	public String getTempSimplename() {
		return tempSimplename;
	}

	public void setTempSimplename(String tempSimplename) {
		this.tempSimplename = tempSimplename;
	}

	public String getBranchsalerName() {
		return branchsalerName;
	}
	public void setBranchsalerName(String branchsalerName) {
		this.branchsalerName = branchsalerName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public BigDecimal getOrderFee() {
		return orderFee;
	}
	public void setOrderFee(BigDecimal orderFee) {
		this.orderFee = orderFee;
	}
	public String getPaySource() {
		return paySource;
	}
	public void setPaySource(String paySource) {
		this.paySource = paySource;
	}
	public String getPayRole() {
		return payRole;
	}
	public void setPayRole(String payRole) {
		this.payRole = payRole;
	}
	public String getInName() {
		return inName;
	}
	public void setInName(String inName) {
		this.inName = inName;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
}
