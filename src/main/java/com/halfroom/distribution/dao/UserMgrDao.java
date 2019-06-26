package com.halfroom.distribution.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.halfroom.distribution.persistence.model.AdminUser;

public interface UserMgrDao {

    /**
     * 修改用户状态
     *
     * @param user
     */
    int setStatus(@Param("userId") Integer userId, @Param("status") int status);

    /**
     * 修改密码
     *
     * @param userId
     * @param pwd
     */
    int changePwd(@Param("userId") Integer userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     *
     * @return
     */
    List<Map<String, Object>> selectUsers(@Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("branchsalerid") Integer branchsalerid, @Param("superAccount") String superAccount);

    /**
     * 设置用户的角色
     *
     * @return
     */
    int setRoles(@Param("userId") Integer userId, @Param("roleIds") String roleIds);

    /**
     * 通过账号获取用户
     *
     * @param account
     * @return
     */
    AdminUser getByAccount(@Param("account") String account);


    /**
     * 通过id获取用户
     *
     * @param id
     * @return
     */
    AdminUser getById(@Param("id") Integer id);
}
