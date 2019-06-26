package com.halfroom.distribution.persistence.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.halfroom.distribution.core.shiro.ShiroKit;

public class UserBranchsalerChange extends Model<UserBranchsalerChange>{
    /**
	 * 
	 */
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
    private Integer oldBranchsalerId;

    /**
     * 
     */
    private Integer newBranchsalerId;

    /**
     * 管理员ID
     */
    private Integer adminId;

    /**
     * 变更类型 : 0 支付变更 1 管理员变更
     */
    private Integer type;

    /**
     * 
     */
    private Date createtime;
    
    

    public UserBranchsalerChange() {
	}
    /**
     * 
     * @param userId
     * @param oldBranchsalerId
     * @param newBranchsalerId
     */
    public UserBranchsalerChange( Integer userId,Integer oldBranchsalerId,Integer newBranchsalerId) {
    	this.userId=userId;
    	this.oldBranchsalerId=oldBranchsalerId;
    	this.newBranchsalerId=newBranchsalerId;
    	this.adminId=ShiroKit.getUser().getId();
    	this.type=1;
    	this.createtime=new Date();
    }
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
     * @return old_branchsaler_id 
     */
    public Integer getOldBranchsalerId() {
        return oldBranchsalerId;
    }

    /**
     * 
     * @param oldBranchsalerId 
     */
    public void setOldBranchsalerId(Integer oldBranchsalerId) {
        this.oldBranchsalerId = oldBranchsalerId;
    }

    /**
     * 
     * @return new_branchsaler_id 
     */
    public Integer getNewBranchsalerId() {
        return newBranchsalerId;
    }

    /**
     * 
     * @param newBranchsalerId 
     */
    public void setNewBranchsalerId(Integer newBranchsalerId) {
        this.newBranchsalerId = newBranchsalerId;
    }

    /**
     * 管理员ID
     * @return admin_id 管理员ID
     */
    public Integer getAdminId() {
        return adminId;
    }

    /**
     * 管理员ID
     * @param adminId 管理员ID
     */
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    /**
     * 变更类型 : 0 支付变更 1 管理员变更
     * @return type 变更类型 : 0 支付变更 1 管理员变更
     */
    public Integer getType() {
        return type;
    }

    /**
     * 变更类型 : 0 支付变更 1 管理员变更
     * @param type 变更类型 : 0 支付变更 1 管理员变更
     */
    public void setType(Integer type) {
        this.type = type;
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

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}
}