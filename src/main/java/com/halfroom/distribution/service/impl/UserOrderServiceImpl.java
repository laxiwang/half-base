package com.halfroom.distribution.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.halfroom.distribution.persistence.vo.UserRechargeRecordVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.util.Convert;
import com.halfroom.distribution.core.util.DateTimeUtil;
import com.halfroom.distribution.core.util.ExcelUtil;
import com.halfroom.distribution.dao.GeneralUserDao;
import com.halfroom.distribution.dao.UserOrderDao;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.model.Branchsaler;
import com.halfroom.distribution.persistence.model.User;
import com.halfroom.distribution.persistence.vo.UserOrderVo;
import com.halfroom.distribution.service.IUserOrderService;

@Service
public class UserOrderServiceImpl implements IUserOrderService {
	@Resource
	private UserOrderDao userOrderDao;
	@Resource
	private BranchsalerMapper branchsalerMapper;
	@Resource
	private GeneralUserDao generalUserDao;
	public List<Map<String, Object>> orderList(String condition,Integer inUserid,Integer paysource,Integer payrole, String beginTime, String endTime) {
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
			map.put("beginTime",beginTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime", endTime+" 23:59:59");
		}
		if(paysource!=null){
			map.put("paysource", paysource);
		}
		if(payrole!=null){
			map.put("payrole", payrole);
		}
		if(inUserid!=null){
			map.put("inUserid", inUserid);
		}
		List<Map<String, Object>> restMap= userOrderDao.selectOrders(map);
		return restMap;
	}

	@Override
	public Map<String, Object> orderStatistics(Integer paysource, Integer payrole) {
		Map<String, Object> map = new HashMap<String, Object>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if(branchsaler!=null&&branchsaler.getLevel()!=0){
        	map.put("branchsaler",branchsaler.getId());
        }
		Map<String,Object> resMap= new HashMap<>();
		
		if(paysource==null&&payrole==null){
			map.put("flag", false);
			//h5 自然流量 
			map.put("paysource",0);
			map.put("payrole",2);
			int h51 = userOrderDao.selectCount(map);
			
			// 推广
			map.put("paysource",null);
			map.put("payrole",0);
			int tuiguang = userOrderDao.selectCount(map);
			
			// 销售
			map.put("paysource",null);
			map.put("payrole",1);
			int xiaoshou = userOrderDao.selectCount(map);
			//知识送礼
			map.put("paysource",null);
			map.put("payrole",3);
			int give = userOrderDao.selectCount(map);

			//ios支付  --以下-自然流量
			map.put("payrole",2);
			map.put("paysource",1);
			int ios = userOrderDao.selectCount(map);
			//android支付  
			map.put("paysource",2);
			int android = userOrderDao.selectCount(map);
			//实体卡支付
			map.put("paysource",3);
			int card = userOrderDao.selectCount(map);
			resMap.put("h51", h51);
			resMap.put("tuiguang", tuiguang);
			resMap.put("xiaoshou", xiaoshou);
			resMap.put("ios", ios);
			resMap.put("android", android);
			resMap.put("card", card);
			resMap.put("give", give);
			resMap.put("sum", h51+tuiguang+xiaoshou+ios+android+card+give);
		}
	
		return resMap;
	}

	@Override
	public void exportOrderList(HttpServletResponse response, ServletOutputStream outputStream, String condition,
			Integer inUserid, Integer paysource,Integer payrole, String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return;
		}
		if(branchsaler!=null&&branchsaler.getLevel()!=0){
        	map.put("branchsaler",branchsaler.getId());
        }
		if (StringUtils.isNotBlank(condition) && !condition.equals("null")) {
			map.put("condition", condition);
		}
		if (StringUtils.isNotBlank(beginTime)  && !beginTime.equals("null")) {
			map.put("beginTime", beginTime);
		}
		if (StringUtils.isNotBlank(beginTime) && !endTime.equals("null")) {
			map.put("endTime", endTime+" 23:59:59");
		}
		if (paysource != null && paysource != -1) {
			map.put("paysource", paysource);
		}
		if (payrole != null && payrole != -1) {
			map.put("payrole", payrole);
		}
		if(inUserid!=null){
			map.put("inUserid", inUserid);
		}
		
		List<Map<String, Object>> list=userOrderDao.selectOrders(map);
		List<UserOrderVo> listUserOrderVo = new ArrayList<>();
		for (Map<String, Object> map2 : list) {
			UserOrderVo userOrderVo = (UserOrderVo)Convert.mapToObject( map2,UserOrderVo.class);
			listUserOrderVo.add(userOrderVo);
		}
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		ExcelUtil<UserOrderVo> myExcel = new ExcelUtil<>();
		String[] headerColumns = {"省级分会","订单分会","分会级别","姓名","手机号","课程名称","课程价格","支付渠道","支付方式","推广/销售人","付款时间"};
		String[] fieldColumns =  {"tempSimplename","branchsalerName","levelName","userName","phone","bookName","orderFee","paySource","payRole","inName","payTime"};
		try {
			HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "订单信息", listUserOrderVo, headerColumns, fieldColumns,true);
			myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.doResponse(response, outputStream, "订单信息", hssfWorkbook);
	}

	@Override
	public List<Map<String, Object>> userRechargeRecordList(String condition, String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if(branchsaler!=null&&branchsaler.getLevel()!=0){
			map.put("branchsaler",branchsaler.getId());
		}
		if (StringUtils.isNotBlank(condition) && !condition.equals("null")) {
			map.put("condition", condition);
		}
		if (StringUtils.isNotBlank(beginTime)  && !beginTime.equals("null")) {
			map.put("beginTime", beginTime);
		}
		if (StringUtils.isNotBlank(endTime) && !endTime.equals("null")) {
			map.put("endTime", endTime+" 23:59:59");
		}
		return userOrderDao.userRechargeRecordList(map);
	}

	@Override
	public void exportUserRechargeRecord(HttpServletResponse response, ServletOutputStream outputStream, String condition, String beginTime, String endTime) {
		List<Map<String, Object>> list=userRechargeRecordList(condition,beginTime,endTime);
		List<UserRechargeRecordVo> recordVoList = new ArrayList<>();
		for (Map<String, Object> map2 : list) {
			UserRechargeRecordVo rechargeRecordVo = (UserRechargeRecordVo)Convert.mapToObject( map2,UserRechargeRecordVo.class);
			recordVoList.add(rechargeRecordVo);
		}
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		ExcelUtil<UserRechargeRecordVo> myExcel = new ExcelUtil<>();
		String[] headerColumns = {"用户名","手机号","分会","充值金额","充值时间"};
		String[] fieldColumns =  {"userName","userPhone","branchSalerName","rechargePoint","creatTime"};
		try {
			HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "充值记录", recordVoList, headerColumns, fieldColumns,true);
			myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.doResponse(response, outputStream, "充值记录", hssfWorkbook);
	}
}
