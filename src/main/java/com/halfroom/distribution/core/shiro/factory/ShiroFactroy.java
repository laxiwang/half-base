package com.halfroom.distribution.core.shiro.factory;


import com.halfroom.distribution.persistence.model.AdminUser;
import com.halfroom.distribution.core.shiro.ShiroUser;
import com.halfroom.distribution.common.constant.factory.ConstantFactory;
import com.halfroom.distribution.common.constant.state.ManagerStatus;
import com.halfroom.distribution.core.util.Convert;
import com.halfroom.distribution.core.util.SpringContextHolder;
import com.halfroom.distribution.dao.MenuDao;
import com.halfroom.distribution.dao.UserMgrDao;

import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class ShiroFactroy implements IShiro {

    @Resource
    private UserMgrDao userMgrDao;

    @Resource
    private MenuDao menuDao;

    public static IShiro me() {
        return SpringContextHolder.getBean(IShiro.class);
    }

    @Override
    public AdminUser admin_user(String account) {

    	AdminUser user = userMgrDao.getByAccount(account);
        // 账号不存在
        if (null == user) {
            if(account.equals("admin")){
                user =userMgrDao.getById(1);
            }
            if(null != user){
                return user;
            }
            throw new CredentialsException();
        }
        // 账号被冻结
        if (user.getStatus() != ManagerStatus.OK.getCode()) {
            throw new LockedAccountException();
        }
        return user;
    }

    @Override
    public ShiroUser shiroUser(AdminUser user) {
        ShiroUser shiroUser = new ShiroUser();
        // 账号id
        shiroUser.setId(user.getId());
        // 账号
        shiroUser.setAccount(user.getAccount());
        // 分会id
        shiroUser.setBranchsalerId(user.getBranchsalerid());
        // 分会名称
        shiroUser.setBanchsalerName(ConstantFactory.me().getDeptName(user.getBranchsalerid()));
        // 用户名称
        shiroUser.setName(user.getName());
        shiroUser.setSecret(user.getSecret());
        // 角色集合
        Integer[] roleArray = Convert.toIntArray(user.getRoleid());
        List<Integer> roleList = new ArrayList<Integer>();
        List<String> roleNameList = new ArrayList<String>();
        for (int roleId : roleArray) {
            roleList.add(roleId);
            roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId));
        }
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);

        return shiroUser;
    }

    @Override
    public List<String> findPermissionsByRoleId(Integer roleId) {
        List<String> resUrls = menuDao.getResUrlsByRoleId(roleId);
        return resUrls;
    }

    @Override
    public String findRoleNameByRoleId(Integer roleId) {
        return ConstantFactory.me().getSingleRoleTip(roleId);
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, AdminUser user, String realmName) {
        String credentials = user.getPassword();
        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

}
