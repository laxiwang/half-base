package com.halfroom.distribution.factory;

import com.halfroom.distribution.core.util.RadomUtil;
import com.halfroom.distribution.transfer.UserDto;
import com.halfroom.distribution.persistence.model.AdminUser;
import org.springframework.beans.BeanUtils;

/**
 * 用户创建工厂
 */
public class AdminUserFactory {

    public static AdminUser createUser(UserDto userDto){
        if(userDto == null){
            return null;
        }else{
        	AdminUser user = new AdminUser();
            BeanUtils.copyProperties(userDto,user);
            user.setSecret(RadomUtil.getRandomSalt(20));
            return user;
        }
    }
}
