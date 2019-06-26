package com.halfroom.distribution.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.util.DateUtil;
import com.halfroom.distribution.dao.GeneralUserDao;
import com.halfroom.distribution.dao.SalerInviteDao;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.model.Branchsaler;
import com.halfroom.distribution.persistence.model.SalerInvite;
import com.halfroom.distribution.persistence.model.User;
import com.halfroom.distribution.service.ISalerInviteService;

@Service
public class SalerInviteServiceImpl  implements ISalerInviteService{
	@Resource
	private SalerInviteDao salerInviteDao;
	@Resource
	private BranchsalerMapper branchsalerMapper;
	@Resource
	private GeneralUserDao generalUserDao;
	/**
	 * -1 在邀请中 或 已接收邀请  
	 * 	0   邀请失败，服务器错误 
	 *  1   邀请已发送
	 *  
	 *  
	 */
	@Override
	public int insertSalerInvite(Integer userid,String remark) {
		SalerInvite record = new SalerInvite();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return 0;
		}
		
		record.setUserId(userid);
		record.setBranchsalerId(branchsaler.getId());
		record.setCreatetime(new Date());
		record.setStatus(0);
		record.setRemark(remark);
		SalerInvite findSalerInvite= salerInviteDao.selectByUserId(record.getUserId());
		if(findSalerInvite!=null&&(findSalerInvite.getStatus()==0||findSalerInvite.getStatus()==1)){
			return -1;
			
		}else if(findSalerInvite!=null&&(findSalerInvite.getStatus()==2||findSalerInvite.getStatus()==3)){
			record.setRemark(remark);
			record.setStatus(0);
			record.setUpdatetime(new Date());
			salerInviteDao.updateUserIdSelective(record);
			return 1;
		}
		return  salerInviteDao.insertSelective(record);
	}

	@Override
	public int updateSalerInvite(SalerInvite record) {
		return salerInviteDao.updateUserIdSelective(record);
	}

	@Override
	public SalerInvite selectByUserId(Integer userid) {
		return salerInviteDao.selectByUserId(userid);
	}
	/**
	 * -1  没有邀请记录 或 已撤销    或用户信息有误 
	 *  0 撤销失败，系统内部错误，
	 *  1 撤销成功
	 */
	@Transactional
	@Override
	public int closeSalerInvite(Integer userid) {
		User user = generalUserDao.selectByid(userid);
		if(user==null){
			return  -1;
		}
		SalerInvite salerInvite = salerInviteDao.selectByUserId(userid);
		if(salerInvite==null){
			return  -1;
		}
		if(salerInvite!=null&&(salerInvite.getStatus()==2||salerInvite.getStatus()==3)){
			return  -1;
		}
		salerInvite.setUpdatetime(new Date());
		salerInvite.setStatus(3);
		user.setRole(0);
		generalUserDao.updateByPrimaryKeySelective(user);
		return salerInviteDao.updateUserIdSelective(salerInvite);
	}

	@Override
	public List<Map<String, Object>> inviteList(String condition, Integer status, String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if(branchsaler!=null&&branchsaler.getLevel()!=0){
        	map.put("branchsaler",branchsaler.getId());
        }
		map.put("condition", condition);
		if (StringUtils.isNotBlank(beginTime)) {
			map.put("beginTime", DateUtil.parseDate(beginTime));
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime", DateUtil.parseDate(endTime));
		}
		if(status!=null){
			map.put("status", status);
		}
		return salerInviteDao.selectInvites(map);
	}

}
