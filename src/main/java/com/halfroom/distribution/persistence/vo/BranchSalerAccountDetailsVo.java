package com.halfroom.distribution.persistence.vo;

import java.math.BigDecimal;

public class BranchSalerAccountDetailsVo {
	private String orderBranchSaler;
	private String orderLevelName;
	private BigDecimal orderFee;
	private String orderNo;
	private String paySource;
	private String payRole;
	private String orderCreateTime;
	private String xqChangeAmount;
	private String quChangeAmount;
	private String shiChangeAmount;
	private String shengChangeAmount;
	private String zongChangeAmount;

	public String getOrderBranchSaler() {
		return orderBranchSaler;
	}

	public void setOrderBranchSaler(String orderBranchSaler) {
		this.orderBranchSaler = orderBranchSaler;
	}

	public String getOrderLevelName() {
		return orderLevelName;
	}

	public void setOrderLevelName(String orderLevelName) {
		this.orderLevelName = orderLevelName;
	}

	public BigDecimal getOrderFee() {
		return orderFee;
	}

	public void setOrderFee(BigDecimal orderFee) {
		this.orderFee = orderFee;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getXqChangeAmount() {
		return xqChangeAmount;
	}

	public void setXqChangeAmount(String xqChangeAmount) {
		this.xqChangeAmount = xqChangeAmount;
	}

	public String getQuChangeAmount() {
		return quChangeAmount;
	}

	public void setQuChangeAmount(String quChangeAmount) {
		this.quChangeAmount = quChangeAmount;
	}

	public String getShiChangeAmount() {
		return shiChangeAmount;
	}

	public void setShiChangeAmount(String shiChangeAmount) {
		this.shiChangeAmount = shiChangeAmount;
	}

	public String getShengChangeAmount() {
		return shengChangeAmount;
	}

	public void setShengChangeAmount(String shengChangeAmount) {
		this.shengChangeAmount = shengChangeAmount;
	}

	public String getZongChangeAmount() {
		return zongChangeAmount;
	}

	public void setZongChangeAmount(String zongChangeAmount) {
		this.zongChangeAmount = zongChangeAmount;
	}
}
