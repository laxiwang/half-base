package com.halfroom.distribution.persistence.model;

import java.util.Date;

/**
 * 普通用户
 * @author tingyunjava
 * 
 */
public class User {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String phone;

    /**
     * 性别  
     */
    private String gender;

    /**
     * 头像
     */
    private String headimage;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 县
     */
    private String county;

    /**
     * 详细地址带省市县
     */
    private String address;

    /**
     * 注册来源  0-h5 1安卓 2ios  3 销售   4   推广人 
     */
    private Integer registerentry;

    /**
     * 如果是通过推广大使或者阅读大使的二维码注册的，则表示推广大使或阅读大使的会员id
     */
    private Long registerintroducer;

    /**
     * 积分 推荐人（推广大使、阅读大使）获得积分，只有通过推荐人体验码注册这儿才有积分
     */
    private Long integral;

    /**
     * 0 体验 1 正式（只做标记 不入库）
     */
    private Integer memberstatus;

    /**
     * 分会ID
     */
    private Integer branchsaler;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 0表示可用，1表示不可用
     */
    private Integer status;
     
    /**
     * 用户角色 0 推广 1销售
     */
    private Integer role;
    private String token;
    
    
    private Integer pid;
    
    private String remark;
    
    
    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
     * @return name 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * @return phone 
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone 
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 性别
     * @return gender 性别
     */
    public String getGender() {
        return gender;
    }

    /**
     * 性别
     * @param gender 性别
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * 头像
     * @return headImage 头像
     */
    public String getHeadimage() {
        return headimage;
    }

    /**
     * 头像
     * @param headimage 头像
     */
    public void setHeadimage(String headimage) {
        this.headimage = headimage == null ? null : headimage.trim();
    }

    /**
     * 出生日期
     * @return birthday 出生日期
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 出生日期
     * @param birthday 出生日期
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday ;
    }

    /**
     * 
     * @return country 
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country 
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    /**
     * 省
     * @return province 省
     */
    public String getProvince() {
        return province;
    }

    /**
     * 省
     * @param province 省
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 市
     * @return city 市
     */
    public String getCity() {
        return city;
    }

    /**
     * 市
     * @param city 市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 县
     * @return county 县
     */
    public String getCounty() {
        return county;
    }

    /**
     * 县
     * @param county 县
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * 详细地址带省市县
     * @return address 详细地址带省市县
     */
    public String getAddress() {
        return address;
    }

    /**
     * 详细地址带省市县
     * @param address 详细地址带省市县
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 注册来源  0-h5 1安卓 2ios  3 销售   4   推广人 
     * @return registerEntry 注册来源  0-h5 1安卓 2ios  3 销售   4   推广人 
     */
    public Integer getRegisterentry() {
        return registerentry;
    }

    /**
     * 注册来源  0-h5 1安卓 2ios  3 销售   4   推广人 
     * @param registerentry 注册来源  0-h5 1安卓 2ios  3 销售   4   推广人 
     */
    public void setRegisterentry(Integer registerentry) {
        this.registerentry = registerentry;
    }

    /**
     * 如果是通过推广大使或者阅读大使的二维码注册的，则表示推广大使或阅读大使的会员id
     * @return registerIntroducer 如果是通过推广大使或者阅读大使的二维码注册的，则表示推广大使或阅读大使的会员id
     */
    public Long getRegisterintroducer() {
        return registerintroducer;
    }

    /**
     * 如果是通过推广大使或者阅读大使的二维码注册的，则表示推广大使或阅读大使的会员id
     * @param registerintroducer 如果是通过推广大使或者阅读大使的二维码注册的，则表示推广大使或阅读大使的会员id
     */
    public void setRegisterintroducer(Long registerintroducer) {
        this.registerintroducer = registerintroducer;
    }

    /**
     * 积分 推荐人（推广大使、阅读大使）获得积分，只有通过推荐人体验码注册这儿才有积分
     * @return integral 积分 推荐人（推广大使、阅读大使）获得积分，只有通过推荐人体验码注册这儿才有积分
     */
    public Long getIntegral() {
        return integral;
    }

    /**
     * 积分 推荐人（推广大使、阅读大使）获得积分，只有通过推荐人体验码注册这儿才有积分
     * @param integral 积分 推荐人（推广大使、阅读大使）获得积分，只有通过推荐人体验码注册这儿才有积分
     */
    public void setIntegral(Long integral) {
        this.integral = integral;
    }

 
    public Integer getMemberstatus() {
		return memberstatus;
	}

	public void setMemberstatus(Integer memberstatus) {
		this.memberstatus = memberstatus;
	}

	/**
     * 分会ID
     * @return branchSaler 分会ID
     */
    public Integer getBranchsaler() {
        return branchsaler;
    }

    /**
     * 分会ID
     * @param branchsaler 分会ID
     */
    public void setBranchsaler(Integer branchsaler) {
        this.branchsaler = branchsaler;
    }

    /**
     * 更新时间
     * @return updateTime 更新时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 更新时间
     * @param updatetime 更新时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 创建时间
     * @return createTime 创建时间
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
     * 0表示可用，1表示不可用
     * @return status 0表示可用，1表示不可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0表示可用，1表示不可用
     * @param status 0表示可用，1表示不可用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 用户角色 0 推广 1销售
     * @return role 用户角色 0 推广 1销售
     */
    public Integer getRole() {
        return role;
    }

    /**
     * 用户角色 0 推广 1销售
     * @param role 用户角色 0 推广 1销售
     */
    public void setRole(Integer role) {
        this.role = role;
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phone=" + phone + ", gender=" + gender + ", headimage="
				+ headimage + ", birthday=" + birthday + ", country=" + country + ", province=" + province + ", city="
				+ city + ", county=" + county + ", address=" + address + ", registerentry=" + registerentry
				+ ", registerintroducer=" + registerintroducer + ", integral=" + integral + ", memberstatus="
				+ memberstatus + ", branchsaler=" + branchsaler + ", updatetime=" + updatetime + ", createtime="
				+ createtime + ", status=" + status + ", role=" + role + ", token=" + token + ", pid=" + pid
				+ ", remark=" + remark + "]";
	}
    
}