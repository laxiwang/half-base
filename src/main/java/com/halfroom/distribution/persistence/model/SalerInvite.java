package com.halfroom.distribution.persistence.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;

public class SalerInvite extends Model<SalerInvite> {
	 private static final long serialVersionUID = 1L;
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer userId;

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
     * 0默认表示邀请中、1：接受邀请、2：拒绝邀请、3：邀请撤销
     */
    private Integer status;

    /**
     * 备注
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
     * @return user_id 
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId 
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
     *  0默认表示邀请中、1：接受邀请、2：拒绝邀请、3：邀请撤销
     * @return status  0默认表示邀请中、1：接受邀请、2：拒绝邀请、3：邀请撤销
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0默认表示邀请中、1：接受邀请、2：拒绝邀请、3：邀请撤销
     * @param status  0默认表示邀请中、1：接受邀请、2：拒绝邀请、3：邀请撤销
     */
    public void setStatus(Integer status) {
        this.status = status;
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

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "SalerInvite [id=" + id + ", userId=" + userId + ", branchsalerId=" + branchsalerId + ", createtime="
				+ createtime + ", updatetime=" + updatetime + ", status=" + status + ", remark=" + remark + "]";
	}
	
}