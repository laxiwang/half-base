package com.halfroom.distribution.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BranchsalerSettlementRecord  extends Model<BranchsalerSettlementRecord> {

    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer branchsalerId;

    /**
     * 结算金额
     */
    private BigDecimal fee;

    /**
     * 
     */
    private Date beginTime;

    /**
     * 
     */
    private Date endTime;

    /**
     * 
     */
    private Date createTime;

    private Integer status;//结算状态 0 未开票 1已开票

    private String invoiceNumber;//发票号码

    private String paysources;//结算订单类型

    private Integer payStatus;
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
     * 结算金额
     * @return fee 结算金额
     */
    public BigDecimal getFee() {
        return fee;
    }

    /**
     * 结算金额
     * @param fee 结算金额
     */
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    /**
     * 
     * @return begin_time 
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 
     * @param beginTime 
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 
     * @return end_time 
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 
     * @param endTime 
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPaysources() {
        return paysources;
    }

    public void setPaysources(String paysources) {
        String paysourcesEnd=paysources.replace("0","公众号").replace("1","ios").replace("2","android");
        this.paysources = paysourcesEnd;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }
}