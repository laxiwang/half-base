package com.halfroom.distribution.persistence.vo;

import java.math.BigDecimal;

public class SalerVo {
	private String uName;
	private String uPhone;
	private String inviteTime;
	private String updatetime;
	private String remark;
	private BigDecimal fee;
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuPhone() {
		return uPhone;
	}
	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}
	public String getinviteTime() {
		return inviteTime;
	}
	public void setInviteTime(String inviteTime) {
		this.inviteTime = inviteTime;
	}
	public String getupdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getremark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getfee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	
}
