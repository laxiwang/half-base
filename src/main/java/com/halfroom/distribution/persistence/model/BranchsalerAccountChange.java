package com.halfroom.distribution.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;

public class BranchsalerAccountChange extends Model<BranchsalerAccountChange>{
	
	private static final long serialVersionUID = 1L;
    /**
     * 
     */
    private Integer id;

    /**
     * 分会id
     */
    private int branchsalerId;

    /**
     * 变动之前余额
     */
    private BigDecimal beforeChangeAmount;

    /**
     * 变动之后余额
     */
    private BigDecimal afterChangeAmount;

    /**
     * (0:收入,1:支出)
     */
    private String type;

    /**
     * 账户变动金额
     */
    private BigDecimal changeAmount;

    /**
     * 具体变动描述
     */
    private String describe;

    /**
     * 添加时间
     */
    private Date addTime;
    
    
    private String orderno;
    
    public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	/**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 分会id
     * @return branchsaler_id 分会id
     */
    public int getBranchsalerId() {
        return branchsalerId;
    }

    /**
     * 分会id
     * @param branchsalerId 分会id
     */
    public void setBranchsalerId(int branchsalerId) {
        this.branchsalerId = branchsalerId;
    }

    /**
     * 变动之前余额
     * @return before_change_amount 变动之前余额
     */
    public BigDecimal getBeforeChangeAmount() {
        return beforeChangeAmount;
    }

    /**
     * 变动之前余额
     * @param beforeChangeAmount 变动之前余额
     */
    public void setBeforeChangeAmount(BigDecimal beforeChangeAmount) {
        this.beforeChangeAmount = beforeChangeAmount;
    }

    /**
     * 变动之后余额
     * @return after_change_amount 变动之后余额
     */
    public BigDecimal getAfterChangeAmount() {
        return afterChangeAmount;
    }

    /**
     * 变动之后余额
     * @param afterChangeAmount 变动之后余额
     */
    public void setAfterChangeAmount(BigDecimal afterChangeAmount) {
        this.afterChangeAmount = afterChangeAmount;
    }

    /**
     * (0:收入,1:支出)
     * @return type (0:收入,1:支出)
     */
    public String getType() {
        return type;
    }

    /**
     * (0:收入,1:支出)
     * @param type (0:收入,1:支出)
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 账户变动金额
     * @return change_amount 账户变动金额
     */
    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    /**
     * 账户变动金额
     * @param changeAmount 账户变动金额
     */
    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    /**
     * 具体变动描述
     * @return describe 具体变动描述
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * 具体变动描述
     * @param describe 具体变动描述
     */
    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    /**
     * 添加时间
     * @return add_time 添加时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     * @param addTime 添加时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime ;
    }

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}