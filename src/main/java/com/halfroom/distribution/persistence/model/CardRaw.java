package com.halfroom.distribution.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.activerecord.Model;

public class CardRaw extends Model<CardRaw>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    private Integer cardNo;

    /**
     * 
     */
    private String cardCode;
    
    private BigDecimal fee;
    
    private Integer type; //0 实体卡 1 半月卡
    

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	/**
     * 
     * @return card_no 
     */
    public Integer getCardNo() {
        return cardNo;
    }

    /**
     * 
     * @param cardNo 
     */
    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * 
     * @return card_code 
     */
    public String getCardCode() {
        return cardCode;
    }

    /**
     * 
     * @param cardCode 
     */
    public void setCardCode(String cardCode) {
        this.cardCode = cardCode == null ? null : cardCode.trim();
    }

	

	@Override
	public String toString() {
		return "CardRaw [cardNo=" + cardNo + ", cardCode=" + cardCode + ", fee=" + fee + "]";
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}
	public CardRaw(Integer cardNo, String cardCode,BigDecimal fee,Integer type) {
		super();
		this.cardNo = cardNo;
		this.cardCode = cardCode;
		if(type!=null&&type!=0){
			this.fee=new BigDecimal("0.0");
		}else {
			this.fee=fee;
		}
		this.type=type;
	}

	public CardRaw() {
		super();
	}
    
}