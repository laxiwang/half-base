package com.halfroom.distribution.core.shiro;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 */
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    public Integer id;

    /**
     * 账号
     */
    public String account;

    /**
     *姓名
      */
    public String name;

    /**
     * 分会id
     */
    public Integer branchsalerId;

    /**
     * 角色集
     */
    public List<Integer> roleList;

    /**
     * 分会名称
     */
    public String banchsalerName;

    /**
     * 角色名称集
     */
    public List<String> roleNames;

    public String secret;
    
    public boolean isAgen=false;  //代理登陆
    
		
	public Integer folderId; // 文件目录层级id

    public String  fileUrl="文件管理 》";  //路径

    public Integer getFolderId() {
        return folderId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public boolean isAgen() {
		return isAgen;
	}

	public void setAgen(boolean isAgen) {
		this.isAgen = isAgen;
	}

	public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getBranchsalerId() {
		return branchsalerId;
	}

	public void setBranchsalerId(Integer branchsalerId) {
		this.branchsalerId = branchsalerId;
	}

	public String getBanchsalerName() {
		return banchsalerName;
	}

	public void setBanchsalerName(String ranchsalerName) {
		this.banchsalerName = ranchsalerName;
	}

	public List<Integer> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Integer> roleList) {
        this.roleList = roleList;
    }


    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }

    @Override
    public String toString() {
        return "ShiroUser{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", branchsalerId=" + branchsalerId +
                ", roleList=" + roleList +
                ", banchsalerName='" + banchsalerName + '\'' +
                ", roleNames=" + roleNames +
                ", secret='" + secret + '\'' +
                ", isAgen=" + isAgen +
                ", folderId=" + folderId +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
