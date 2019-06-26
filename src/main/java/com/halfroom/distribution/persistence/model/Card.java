package com.halfroom.distribution.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;

public class Card  extends Model<Card>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 顺序编号
     */
    private Integer cardNo;

    /**
     * 卡密
     */
    private String cardCode;
    private BigDecimal fee;
    
    private Integer type;
    /**
     * 
     */
    private Integer branchsalerId;

    /**
     * 
     */
    private Date createtime;

    /**
     * 
     */
    private Date updatetime;

    /**
     * 0-未激活,1-已激活, 2-已使用
     */
    private Integer status;

    /**
     * 顺序编号
     * @return card_no 顺序编号
     */
    public Integer getCardNo() {
        return cardNo;
    }

    /**
     * 顺序编号
     * @param cardNo 顺序编号
     */
    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * 卡密
     * @return card_code 卡密
     */
    public String getCardCode() {
        return cardCode;
    }

    /**
     * 卡密
     * @param cardCode 卡密
     */
    public void setCardCode(String cardCode) {
        this.cardCode = cardCode == null ? null : cardCode.trim();
    }
  
    
    public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	/**
     * 
     * @return branchsaler_id 
     */
    public Integer getBranchsalerId() {
        return branchsalerId;
    }

    /**
     * 
     * @param branchsalerId 
     */
    public void setBranchsalerId(Integer branchsalerId) {
        this.branchsalerId = branchsalerId;
    }

    /**
     * 
     * @return createtime 
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 
     * @param createtime 
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 
     * @return updatetime 
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 
     * @param updatetime 
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 0-未激活,1-已激活, 2-已使用
     * @return status 0-未激活,1-已激活, 2-已使用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0-未激活,1-已激活, 2-已使用
     * @param status 0-未激活,1-已激活, 2-已使用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Card [cardNo=" + cardNo + ", cardCode=" + cardCode + ", fee=" + fee + ", branchsalerId=" + branchsalerId
				+ ", createtime=" + createtime + ", updatetime=" + updatetime + ", status=" + status + "]";
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}