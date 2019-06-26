package com.halfroom.distribution.dao;

import com.halfroom.distribution.common.node.ZTreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 角色相关的dao
 *
 */
public interface RoleDao {

    /**
     * 根据条件查询角色列表
     *
     * @return
     */
    List<Map<String, Object>> selectRoles(@Param("condition") String condition);

    /**
     * 删除某个角色的所有权限
     *
     * @param roleId 角色id
     * @return
     */
    int deleteRolesById(@Param("roleId") Integer roleId);

    /**
     * 获取角色列表树
     *
     * @return
     */
    List<ZTreeNode> roleTreeList();

    /**
     * 获取角色列表树
     *
     * @return
     */
    List<ZTreeNode> roleTreeListByRoleId(String[] roleId);


}
