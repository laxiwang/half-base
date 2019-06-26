package com.halfroom.distribution.service.impl;

import com.halfroom.distribution.core.shiro.ShiroDbRealm;
import com.halfroom.distribution.core.util.Convert;
import com.halfroom.distribution.dao.RoleDao;
import com.halfroom.distribution.service.IRoleService;
import com.halfroom.distribution.persistence.dao.RelationMapper;
import com.halfroom.distribution.persistence.dao.RoleMapper;
import com.halfroom.distribution.persistence.model.Relation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleDao roleDao;

    @Resource
    private RelationMapper relationMapper;

    @Override
    @Transactional(readOnly = false)
    public void setAuthority(Integer roleId, String ids) {

        // 删除该角色所有的权限
        this.roleDao.deleteRolesById(roleId);

        // 添加新的权限
        for (Integer id : Convert.toIntArray(ids)) {
            Relation relation = new Relation();
            relation.setRoleid(roleId);
            relation.setMenuid(id);
            this.relationMapper.insert(relation);
        }
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        ShiroDbRealm realm = (ShiroDbRealm)rsm.getRealms().iterator().next();
        realm.clearCachedAuthorization();
    }

    @Override
    @Transactional(readOnly = false)
    public void delRoleById(Integer roleId) {
        //删除角色
        this.roleMapper.deleteById(roleId);

        // 删除该角色所有的权限
        this.roleDao.deleteRolesById(roleId);
    }

}
