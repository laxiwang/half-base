package com.halfroom.distribution.persistence.vo;

public class UserRechargeRecordVo {
    private String userName;
    private String userPhone;
    private String branchSalerName;
    private Integer rechargePoint;
    private String creatTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getBranchSalerName() {
        return branchSalerName;
    }

    public void setBranchSalerName(String branchSalerName) {
        this.branchSalerName = branchSalerName;
    }

    public Integer getRechargePoint() {
        return rechargePoint;
    }

    public void setRechargePoint(Integer rechargePoint) {
        this.rechargePoint = rechargePoint;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }
}
