package com.halfroom.distribution.rabbitmq.MessageBean;

import java.math.BigDecimal;
import java.util.Date;


public class DistributionMessageBean {
	
	private int userId; //用户ID
	
	private int branchsalerId; //分会ID
	
	private BigDecimal amount; //变动金额
	
	private String type; //变动类型: 0 收入，1，支出（提现）
	
	private String salerType; //推广类型: 0 推广大使，1，销售大使
	
	private Date createtime;
	
	private String orderno; //订单编号
	
	public DistributionMessageBean() {
	}

	public DistributionMessageBean(int userId, int branchsalerId, BigDecimal amount, String type,String salerType,String orderno,Date date) {
		this.userId = userId;
		this.branchsalerId = branchsalerId;
		this.amount = amount;
		this.type = type;
		this.salerType = salerType;
		this.createtime = date;
		this.orderno=orderno;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBranchsalerId() {
		return branchsalerId;
	}

	public void setBranchsalerId(int branchsalerId) {
		this.branchsalerId = branchsalerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSalerType() {
		return salerType;
	}

	public void setSalerType(String salerType) {
		this.salerType = salerType;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	@Override
	public String toString() {
		return "DistributionMessageBean [userId=" + userId + ", branchsalerId=" + branchsalerId + ", amount=" + amount
				+ ", type=" + type + ", salerType=" + salerType + ",orderno="+ orderno+" createtime=" +createtime + "]";
	}
}
