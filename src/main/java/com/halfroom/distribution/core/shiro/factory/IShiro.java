package com.halfroom.distribution.core.shiro.factory;

import com.halfroom.distribution.persistence.model.AdminUser;
import com.halfroom.distribution.core.shiro.ShiroUser;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

import java.util.List;

/**
 * 定义shirorealm所需数据的接口
 *
 */
public interface IShiro {

    /**
     * 根据账号获取登录用户
     *
     * @param account 账号
     */
    AdminUser admin_user(String account);

    /**
     * 根据系统用户获取Shiro的用户
     *
     * @param user 系统用户
     */
    ShiroUser shiroUser(AdminUser admin_user);

    /**
     * 获取权限列表通过角色id
     *
     * @param roleId 角色id
     */
    List<String> findPermissionsByRoleId(Integer roleId);

    /**
     * 根据角色id获取角色名称
     *
     * @param roleId 角色id
     */
    String findRoleNameByRoleId(Integer roleId);

    /**
     * 获取shiro的认证信息
     */
    SimpleAuthenticationInfo info(ShiroUser shiroUser, AdminUser admin_user, String realmName);

}
