package com.halfroom.distribution.common.constant.factory;

import com.halfroom.distribution.common.constant.cache.Cache;
import com.halfroom.distribution.common.constant.cache.CacheKey;
import com.halfroom.distribution.persistence.model.Dict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 常量生产工厂的接口
 */
public interface IConstantFactory {

    /**
     * 根据用户id获取用户名称
     */
    String getUserNameById(Integer userId);

    /**
     * 根据用户id获取用户账号
     */
    String getUserAccountById(Integer userId);

    /**
     * 通过角色ids获取角色名称
     */
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.ROLES_NAME + "'+#roleIds")
    String getRoleName(String roleIds);

    /**
     * 通过角色id获取角色名称
     */
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_NAME + "'+#roleId")
    String getSingleRoleName(Integer roleId);

    /**
     * 通过角色id获取角色英文名称
     */
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_TIP + "'+#roleId")
    String getSingleRoleTip(Integer roleId);

    /**
     * 获取部门名称
     */
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.DEPT_NAME + "'+#branchsalerId")
    String getDeptName(Integer branchsalerId);

    /**
     * 获取菜单的名称们(多个)
     */
    String getMenuNames(String menuIds);

    /**
     * 获取菜单名称
     */
    String getMenuName(Integer menuId);

    /**
     * 获取菜单名称通过编号
     */
    String getMenuNameByCode(String code);

    /**
     * 获取字典名称
     */
    String getDictName(Integer dictId);

    /**
     * 获取通知标题
     */
    String getNoticeTitle(Integer dictId);

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    String getDictsByName(String name, Integer val);

    /**
     * 获取性别名称
     */
    @Cacheable(value = Cache.DEFAULTCACHE , key = "'" + CacheKey.SEX + "'+#sex")
    String getSexName(Integer sex);

    /**
     * 获取用户登录状态
     */
    String getStatusName(Integer status);

    /**
     * 获取菜单状态
     */
    String getMenuStatusName(Integer status);

    /**
     * 查询字典
     */
    List<Dict> findInDict(Integer id);
    

    /**
     * 获取被缓存的对象(用户删除业务)
     */
    String getCacheObject(String para);

    /**
     * 根据字典类型查询字典信息
     * @param typeNo
     * @return
     */
//    List<SysDic> findDicList(String typeNo);
    
    /**
     * 获取子部门id
     */
    List<Integer> getSubBranchsalerId(Integer deptid);

    /**
     * 获取所有父部门id
     */
    List<Integer> getParentBranchsalerIds(Integer deptid);

}
