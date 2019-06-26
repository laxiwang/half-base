package com.halfroom.distribution.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 序号
     */
	private Integer num;
    /**
     * 父角色id
     */
	private Integer pid;
    /**
     * 角色名称
     */
	private String name;
    /**
     * 分会名称
     */
	private Integer branchsalerid;
    /**
     * 提示
     */
	private String tips;
    /**
     * 保留字段(暂时没用）
     */
	private Integer version;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer getBranchsalerid() {
		return branchsalerid;
	}

	public void setBranchsalerid(Integer branchsalerid) {
		this.branchsalerid = branchsalerid;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Role{" +
			"id=" + id +
			", num=" + num +
			", pid=" + pid +
			", name=" + name +
			", deptid=" + branchsalerid +
			", tips=" + tips +
			", version=" + version +
			"}";
	}
}
