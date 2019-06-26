package com.halfroom.distribution.persistence.vo;

import java.math.BigDecimal;

public class ProductCardVo {
	private String cardNo;
	private String qrcordUrl;
    private BigDecimal fee;
    
	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getQrcordUrl() {
		return qrcordUrl;
	}

	public void setQrcordUrl(String qrcordUrl) {
		this.qrcordUrl = qrcordUrl;
	}

	public ProductCardVo(String cardNo, String qrcordUrl,BigDecimal fee) {
		super();
		this.cardNo = cardNo;
		this.qrcordUrl = qrcordUrl;
		this.fee=fee;
	}

	public ProductCardVo() {
		super();
	}
}
