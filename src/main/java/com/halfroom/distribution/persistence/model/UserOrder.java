package com.halfroom.distribution.persistence.model;

import java.util.Date;

public class UserOrder {
    /**
     * 
     */
    private Integer id;

    /**
     * 订单编号 或 会员卡编号
     */
    private String orderno;

    /**
     * 
     */
    private Integer userid;

    /**
     * 
     */
    private Integer bookid;

    /**
     * 
     */
    private Double fee;

    /**
     * 支付 状态  0 未支付 1已支付
     */
    private Integer state;

    /**
     * 0 h5 1 "苹果App支付",   2 安卓App支付", 3 会员卡支付
     */
    private Integer paysource;

    /**
     * 产品类型
     */
    private Integer type;

    /**
     * 下单时间
     */
    private Date createtime;

    /**
     * 更新时间 （支付成功时间）
     */
    private Date updatetime;

    /**
     * 订单-用户角色 0自然流量 1 推广角色 2 销售角色
     */
    private Integer payrole;

    /**
     * 推广人或销售人id
     */
    private Integer introducerid;

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
     * 订单编号 或 会员卡编号
     * @return orderno 订单编号 或 会员卡编号
     */
    public String getOrderno() {
        return orderno;
    }

    /**
     * 订单编号 或 会员卡编号
     * @param orderno 订单编号 或 会员卡编号
     */
    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    /**
     * 
     * @return userid 
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * 
     * @param userid 
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * 
     * @return bookid 
     */
    public Integer getBookid() {
        return bookid;
    }

    /**
     * 
     * @param bookid 
     */
    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    /**
     * 
     * @return money 
     */
    public Double getFee() {
        return fee;
    }

    /**
     * 
     * @param money 
     */
    public void setFee(Double fee) {
        this.fee = fee;
    }

    /**
     * 支付 状态  0 未支付 1已支付
     * @return state 支付 状态  0 未支付 1已支付
     */
    public Integer getState() {
        return state;
    }

    /**
     * 支付 状态  0 未支付 1已支付
     * @param state 支付 状态  0 未支付 1已支付
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 0 h5 "苹果App支付", 1  安卓App支付", 2
     * @return paysource 0 h5 "苹果App支付", 1  安卓App支付", 2
     */
    public Integer getPaysource() {
        return paysource;
    }

    /**
     * 0 h5 "苹果App支付", 1  安卓App支付", 2
     * @param paysource 0 h5 "苹果App支付", 1  安卓App支付", 2
     */
    public void setPaysource(Integer paysource) {
        this.paysource = paysource;
    }

    /**
     * 产品类型
     * @return type 产品类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 产品类型
     * @param type 产品类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 下单时间
     * @return createtime 下单时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 下单时间
     * @param createtime 下单时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 更新时间 （支付成功时间）
     * @return updatetime 更新时间 （支付成功时间）
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 更新时间 （支付成功时间）
     * @param updatetime 更新时间 （支付成功时间）
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 订单-用户角色 0自然流量 1 推广角色 2 销售角色
     * @return payrole 订单-用户角色 0自然流量 1 推广角色 2 销售角色
     */
    public Integer getPayrole() {
        return payrole;
    }

    /**
     * 订单-用户角色 0 推广角色 1 销售角色 2自然流量
     * @param payrole 订单-用户角色 0 推广角色 1 销售角色 2自然流量
     */
    public void setPayrole(Integer payrole) {
        this.payrole = payrole;
    }

    /**
     * 推广人或销售人id
     * @return introducerid 推广人或销售人id
     */
    public Integer getIntroducerid() {
        return introducerid;
    }

    /**
     * 推广人或销售人id
     * @param introducerid 推广人或销售人id
     */
    public void setIntroducerid(Integer introducerid) {
        this.introducerid = introducerid;
    }
}