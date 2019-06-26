package com.halfroom.distribution.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class BranchsalerAmount extends Model<BranchsalerAmount> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 分会标示id
	 */
	private Integer branchsalerId;
	/**
	 * 分会名称
	 */
	private String branchsalerName;
	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;
	/**
	 * 冻结金额
	 */
	private BigDecimal frozenAmount;
	/**
	 * 可用金额
	 */
	private BigDecimal avaibleAmount;
	/**
	 * 添加时间
	 */
	private Date addTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 状态（0正常 1冻结）
	 */
	private String amountStatus;
	
	public  BranchsalerAmount(){
		
	}
	public BranchsalerAmount(Integer branchsalerId, String branchsalerName, BigDecimal totalAmount,
			BigDecimal frozenAmount, BigDecimal avaibleAmount) {
		this.branchsalerId = branchsalerId;
		this.branchsalerName = branchsalerName;
		this.totalAmount = totalAmount;
		this.frozenAmount = frozenAmount;
		this.avaibleAmount = avaibleAmount;
		this.addTime=new Date();
		this.amountStatus="0";
	}
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBranchsalerId() {
		return branchsalerId;
	}

	public void setBranchsalerId(Integer branchsalerId) {
		this.branchsalerId = branchsalerId;
	}

	public String getBranchsalerName() {
		return branchsalerName;
	}

	public void setBranchsalerName(String branchsalerName) {
		this.branchsalerName = branchsalerName;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public BigDecimal getAvaibleAmount() {
		return avaibleAmount;
	}

	public void setAvaibleAmount(BigDecimal avaibleAmount) {
		this.avaibleAmount = avaibleAmount;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAmountStatus() {
		return amountStatus;
	}

	public void setAmountStatus(String amountStatus) {
		this.amountStatus = amountStatus;
	}

	@Override
	public String toString() {
		return "BranchsalerAmount [id=" + id + ", branchsalerId=" + branchsalerId + ", branchsalerName="
				+ branchsalerName + ", totalAmount=" + totalAmount + ", frozenAmount=" + frozenAmount
				+ ", avaibleAmount=" + avaibleAmount + ", addTime=" + addTime + ", updateTime=" + updateTime
				+ ", amountStatus=" + amountStatus + "]";
	}

}
