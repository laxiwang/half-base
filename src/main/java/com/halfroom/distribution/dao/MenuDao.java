package com.halfroom.distribution.dao;

import org.apache.ibatis.annotations.Param;

import com.halfroom.distribution.common.node.MenuNode;
import com.halfroom.distribution.common.node.ZTreeNode;

import java.util.List;
import java.util.Map;

public interface MenuDao {

    /**
     * 根据条件查询菜单
     *
     * @return
     */
    List<Map<String, Object>> selectMenus(@Param("condition") String condition,@Param("level") String level);

    /**
     * 根据条件查询菜单
     *
     * @return
     */
    List<Integer> getMenuIdsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取菜单列表树
     *
     * @return
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     *
     * @return
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Integer> menuIds);

    /**
     * 删除menu关联的relation
     *
     * @param menuId
     * @return
     */
    int deleteRelationByMenu(Integer menuId);

    /**
     * 获取资源url通过角色id
     *
     * @param roleId
     * @return
     */
    List<String> getResUrlsByRoleId(Integer roleId);

    /**
     * 根据角色获取菜单
     *
     * @param roleIds
     * @return
     */
    List<MenuNode> getMenusByRoleIds(List<Integer> roleIds);


}
