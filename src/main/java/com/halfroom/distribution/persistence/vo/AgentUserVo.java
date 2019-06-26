package com.halfroom.distribution.persistence.vo;

import java.math.BigDecimal;

public class AgentUserVo {
    private  String simplename;
    private String fullname;
    private  String levelName;
    private Integer count;
    private Integer fullmembers;
    private Integer sup;
    private Integer suped;
    private Integer cardGiving;
    private Integer paid;
    private Integer cardPay;
    private Integer exchange;
    private Integer giftGiving;

    public String getSimplename() {
        return simplename;
    }

    public void setSimplename(String simplename) {
        this.simplename = simplename;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFullmembers() {
        return fullmembers;
    }

    public void setFullmembers(Integer fullmembers) {
        this.fullmembers = fullmembers;
    }

    public Integer getSup() {
        return sup;
    }

    public void setSup(Integer sup) {
        this.sup = sup;
    }

    public Integer getSuped() {
        return suped;
    }

    public void setSuped(Integer suped) {
        this.suped = suped;
    }

    public Integer getCardGiving() {
        return cardGiving;
    }

    public void setCardGiving(Integer cardGiving) {
        this.cardGiving = cardGiving;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getCardPay() {
        return cardPay;
    }

    public void setCardPay(Integer cardPay) {
        this.cardPay = cardPay;
    }

    public Integer getExchange() {
        return exchange;
    }

    public void setExchange(Integer exchange) {
        this.exchange = exchange;
    }

    public Integer getGiftGiving() {
        return giftGiving;
    }

    public void setGiftGiving(Integer giftGiving) {
        this.giftGiving = giftGiving;
    }
}
