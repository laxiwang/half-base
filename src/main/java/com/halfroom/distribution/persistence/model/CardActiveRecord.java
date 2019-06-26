package com.halfroom.distribution.persistence.model;

import java.util.Date;

public class CardActiveRecord {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer branchsalerId;

    /**
     * 
     */
    private Integer cardNoStart;

    /**
     * 
     */
    private Integer cardNoEnd;

    /**
     * 
     */
    private Date createtime;

    /**
     * 
     */
    private String remark;

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
     * @return card_no_start 
     */
    public Integer getCardNoStart() {
        return cardNoStart;
    }

    /**
     * 
     * @param cardNoStart 
     */
    public void setCardNoStart(Integer cardNoStart) {
        this.cardNoStart = cardNoStart;
    }

    /**
     * 
     * @return card_no_end 
     */
    public Integer getCardNoEnd() {
        return cardNoEnd;
    }

    /**
     * 
     * @param cardNoEnd 
     */
    public void setCardNoEnd(Integer cardNoEnd) {
        this.cardNoEnd = cardNoEnd;
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
     * @return remark 
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * @param remark 
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}