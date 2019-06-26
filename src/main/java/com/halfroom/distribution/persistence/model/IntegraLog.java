package com.halfroom.distribution.persistence.model;

import java.util.Date;

public class IntegraLog {
    /**
     * 
     */
    private Integer id;

    /**
     * 积分用户id
     */
    private Integer userid;

    /**
     * 被积分用id
     */
    private Integer inUserid;

    /**
     * 积分数
     */
    private Integer integral;

    /**
     *  0 分享增积分  1 购买增积分 2 
     */
    private Integer type;

    /**
     * 积分时间
     */
    private Date createtime;
    public IntegraLog(){
    	
    }
    public IntegraLog(Integer userid,Integer inUserid, Integer integral, Integer type){
    	this.userid=userid;
    	this.inUserid=inUserid;
    	this.integral=integral;
    	this.type=type;
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
     * 积分用户id
     * @return userid 积分用户id
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * 积分用户id
     * @param userid 积分用户id
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * 被积分用id
     * @return in_userid 被积分用id
     */
    public Integer getInUserid() {
        return inUserid;
    }

    /**
     * 被积分用id
     * @param inUserid 被积分用id
     */
    public void setInUserid(Integer inUserid) {
        this.inUserid = inUserid;
    }

    /**
     * 积分数
     * @return integral 积分数
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * 积分数
     * @param integral 积分数
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     *  0 分享增积分  1 购买增积分 
     * @return type  0 分享增积分  1 购买增积分 
     */
    public Integer getType() {
        return type;
    }

    /**
     *  0 分享增积分  1 购买增积分 
     * @param type  0 分享增积分  1 购买增积分 
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 积分时间
     * @return createtime 积分时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 积分时间
     * @param createtime 积分时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}