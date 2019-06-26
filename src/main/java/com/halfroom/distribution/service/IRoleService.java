package com.halfroom.distribution.service;

/**
 * 角色相关业务
 */
public interface IRoleService {

    /**
     * 设置某个的权限
     *
     * @param roleId 角色id
     * @param ids    权限的id
     */
    void setAuthority(Integer roleId, String ids);

    /**
     * 删除角色
     */
    void delRoleById(Integer roleId);
}
