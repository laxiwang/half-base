package com.halfroom.distribution.persistence.vo;

import java.math.BigDecimal;

public class BranchSalerAccountVo {
    private String orderno;
    private String orderBranchsaler;
    private BigDecimal changeAmount;
    private String addTime;
    private String paySource;
    private String payRole;

    public String getPayRole() {
        return payRole;
    }

    public void setPayRole(String payRole) {
        this.payRole = payRole;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOrderBranchsaler() {
        return orderBranchsaler;
    }

    public void setOrderBranchsaler(String orderBranchsaler) {
        this.orderBranchsaler = orderBranchsaler;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getPaySource() {
        return paySource;
    }

    public void setPaySource(String paySource) {
        this.paySource = paySource;
    }
}
