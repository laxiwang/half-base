package com.halfroom.distribution.dao;

import java.util.List;
import java.util.Map;

import com.halfroom.distribution.persistence.model.SalerInvite;
/**
 * 邀请销售大使 记录
 * @author tingyunjava
 *
 */
public interface SalerInviteDao {
    int insertSelective(SalerInvite record);
    int updateUserIdSelective(SalerInvite record);
    SalerInvite   selectByUserId(Integer userid);
    
    List<Map<String, Object>>  selectInvites(Map<String, Object> map);
}