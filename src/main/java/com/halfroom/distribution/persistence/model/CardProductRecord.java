package com.halfroom.distribution.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;

public class CardProductRecord extends Model<CardProductRecord>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    private Integer id;

    /**
     * 编号开始
     */
    private Integer cardNoStart;

    /**
     * 编号结束
     */
    private Integer cardNoEnd;
    
    private BigDecimal fee;

    
    private Integer type;
    
    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 投放时间
     */
    private Date putontime;

    /**
     * 导出时间
     */
    private Date exporttime;

    /**
     * 0-待投放    1-已投放 
     */
    private Integer status;

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
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 创建时间
     * @return createtime 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 创建时间
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 投放时间
     * @return putontime 投放时间
     */
    public Date getPutontime() {
        return putontime;
    }

    /**
     * 投放时间
     * @param putontime 投放时间
     */
    public void setPutontime(Date putontime) {
        this.putontime = putontime;
    }

    /**
     * 导出时间
     * @return exporttime 导出时间
     */
    public Date getExporttime() {
        return exporttime;
    }

    /**
     * 导出时间
     * @param exporttime 导出时间
     */
    public void setExporttime(Date exporttime) {
        this.exporttime = exporttime;
    }

    /**
     * 0-待投放   1-已投放 
     * @return status 0-待投放   1-已投放 
     */
    public Integer getStatus() {
        return status;
    }
    
    public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	/**
     * 0-待投放   1-已投放 
     * @param status 0-待投放   1-已投放 
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
		return "CardProductRecord [id=" + id + ", cardNoStart=" + cardNoStart + ", cardNoEnd=" + cardNoEnd + ", fee="
				+ fee + ", remark=" + remark + ", createtime=" + createtime + ", putontime=" + putontime
				+ ", exporttime=" + exporttime + ", status=" + status + "]";
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}