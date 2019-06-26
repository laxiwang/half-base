package com.halfroom.distribution.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;

public class CardWholeSale extends Model<CardWholeSale>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    private Integer id;

    /**
     * 划拨发起分会
     */
    private Integer fromSalerId;

    /**
     * 接受划拨分会
     */
    private Integer toSalerId;

    /**
     * 编号开始
     */
    private Integer cardNoStart;

    /**
     * 编号结束
     */
    private Integer cardNoEnd;

    /**
     * 划拨价格
     */
    private BigDecimal cardPrice;

    /**
     * 
     */
    private Date createtime;

    /**
     * 
     */
    private Date updatetime;

    /**
     * 
     */
    private String remark;

    /**
     * 0 待确认  1已确认 2 已取消
     */
    private Integer status;

    private Integer type;

    //此字段不存在数据结构中 为了接受 分会tree 使用
    private Integer branchsaler;
    
    public Integer getBranchsaler() {
		return branchsaler;
	}

	public void setBranchsaler(Integer branchsaler) {
		this.branchsaler = branchsaler;
	}

	/**
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 划拨发起分会
     * @return from_saler_id 划拨发起分会
     */
    public Integer getFromSalerId() {
        return fromSalerId;
    }

    /**
     * 划拨发起分会
     * @param fromSalerId 划拨发起分会
     */
    public void setFromSalerId(Integer fromSalerId) {
        this.fromSalerId = fromSalerId;
    }

    /**
     * 接受划拨分会
     * @return to_saler_id 接受划拨分会
     */
    public Integer getToSalerId() {
        return toSalerId;
    }

    /**
     * 接受划拨分会
     * @param toSalerId 接受划拨分会
     */
    public void setToSalerId(Integer toSalerId) {
        this.toSalerId = toSalerId;
    }

    /**
     * 编号开始
     * @return card_no_start 编号开始
     */
    public Integer getCardNoStart() {
        return cardNoStart;
    }

    /**
     * 编号开始
     * @param cardNoStart 编号开始
     */
    public void setCardNoStart(Integer cardNoStart) {
        this.cardNoStart = cardNoStart;
    }

    /**
     * 编号结束
     * @return card_no_end 编号结束
     */
    public Integer getCardNoEnd() {
        return cardNoEnd;
    }

    /**
     * 编号结束
     * @param cardNoEnd 编号结束
     */
    public void setCardNoEnd(Integer cardNoEnd) {
        this.cardNoEnd = cardNoEnd;
    }

    /**
     * 划拨价格
     * @return card_price 划拨价格
     */
    public BigDecimal getCardPrice() {
        return cardPrice;
    }

    /**
     * 划拨价格
     * @param cardPrice 划拨价格
     */
    public void setCardPrice(BigDecimal cardPrice) {
        this.cardPrice = cardPrice;
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

    /**
     * 0 待确认  1已确认 2 已取消
     * @return status 0 待确认  1已确认 2 已取消
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0 待确认  1已确认 2 已取消
     * @param status 0 待确认  1已确认 2 已取消
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
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "CardWholeSale [id=" + id + ", fromSalerId=" + fromSalerId + ", toSalerId=" + toSalerId
				+ ", cardNoStart=" + cardNoStart + ", cardNoEnd=" + cardNoEnd + ", cardPrice=" + cardPrice
				+ ", createtime=" + createtime + ", updatetime=" + updatetime + ", remark=" + remark + ", status="
				+ status + "]";
	}
	
	
}