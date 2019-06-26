package com.halfroom.distribution.service;

import java.util.List;
import java.util.Map;


import com.halfroom.distribution.persistence.model.SalerInvite;

/**
 * 邀请销售人相关接口
 * @author tingyunjava
 *
 */
public interface ISalerInviteService {
	  int insertSalerInvite(Integer userid,String remark);
	  
	  int updateSalerInvite(SalerInvite record);
	  
	  SalerInvite   selectByUserId(Integer userid);
	  
	  int   closeSalerInvite(Integer userid);
	  
	  List<Map<String, Object>> inviteList(String condition,Integer status, String beginTime, String endTime);
}
