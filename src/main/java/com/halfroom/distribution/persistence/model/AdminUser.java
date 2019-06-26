package com.halfroom.distribution.persistence.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class AdminUser extends Model<AdminUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 头像
     */
	private String avatar;
    /**
     * 账号
     */
	private String account;
    /**
     * 密码
     */
	private String password;
    /**
     * md5密码盐
     */
	private String salt;
    /**
     * 名字
     */
	private String name;
    /**
     * 生日
     */
	private Date birthday;
    /**
     * 性别（1：男 2：女）
     */
	private Integer sex;
    /**
     * 电子邮件
     */
	private String email;
    /**
     * 电话
     */
	private String phone;
    /**
     * 角色id
     */
	private String roleid;
    /**
     * 分会id
     */
	private Integer branchsalerid;
    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
	private Integer status;
    /**
     * 创建时间
     */
	private Date createtime;
    /**
     * 保留字段
     */
	private Integer version;
    /**
     * 分销商key
     */
	private String secret;
    /**
     * 上级key
     */
	private String superaccount;
    /**
     * 全路径
     */
	private String fullindex;
    /**
     * 等级
     */
	private String level;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}


	public Integer getBranchsalerid() {
		return branchsalerid;
	}

	public void setBranchsalerid(Integer branchsalerid) {
		this.branchsalerid = branchsalerid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getSuperaccount() {
		return superaccount;
	}

	public void setSuperaccount(String superaccount) {
		this.superaccount = superaccount;
	}

	public String getFullindex() {
		return fullindex;
	}

	public void setFullindex(String fullindex) {
		this.fullindex = fullindex;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AdminUser{" +
			"id=" + id +
			", avatar=" + avatar +
			", account=" + account +
			", password=" + password +
			", salt=" + salt +
			", name=" + name +
			", birthday=" + birthday +
			", sex=" + sex +
			", email=" + email +
			", phone=" + phone +
			", roleid=" + roleid +
			", branchsalerid=" + branchsalerid +
			", status=" + status +
			", createtime=" + createtime +
			", version=" + version +
			", secret=" + secret +
			", superaccount=" + superaccount +
			", fullindex=" + fullindex +
			", level=" + level +
			"}";
	}
}
