package com.halfroom.distribution.persistence.vo;


import java.math.BigDecimal;

public class GeneralUserVo {
	
	/**
	 * 
	 */
	private String uName;
	private String uRole;
	private String bName;
	private String uPhone;
	private String address;
	private String uCreateTime;
	private String uStatus;
	private long integral;
	private String partlog;
	private String remark;
	private BigDecimal playCount;
	private String playSum;
	private String inviterInfo;
	private String  tempSimplename;

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuRole() {
        return uRole;
    }

    public void setuRole(String uRole) {
        this.uRole = uRole;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getaddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getuCreateTime() {
        return uCreateTime;
    }

    public void setuCreateTime(String uCreateTime) {
        this.uCreateTime = uCreateTime;
    }

    public String getuStatus() {
        return uStatus;
    }

    public void setuStatus(String uStatus) {
        this.uStatus = uStatus;
    }

    public long getintegral() {
        return integral;
    }

    public void setIntegral(long integral) {
        this.integral = integral;
    }

    public String getpartlog() {
        return partlog;
    }

    public void setPartlog(String partlog) {
        this.partlog = partlog;
    }

    public String getrRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getplayCount() {
        return playCount;
    }

    public void setPlayCount(BigDecimal playCount) {
        this.playCount = playCount;
    }

    public String getplaySum() {
        return playSum;
    }

    public void setPlaySum(String playSum) {
        this.playSum = playSum;
    }

    public String getinviterInfo() {
        return inviterInfo;
    }

    public void setInviterInfo(String inviterInfo) {
        this.inviterInfo = inviterInfo;
    }

    public String gettempSimplename() {
        return tempSimplename;
    }

    public void setTempSimplename(String tempSimplename) {
        this.tempSimplename = tempSimplename;
    }
}
