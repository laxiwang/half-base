package com.halfroom.distribution.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;


public interface IUserOrderService {
	/**
	 * 订单列表
	 * @param condition  姓名 手机号  推广人姓名
	 * @param inUserid   推广人id
	 * @param paysource  
	 * @param payrole    支付角色  0 推广 1 销售 2 自然流量
	 * @param beginTime  开始时间
	 * @param endTime    结束时间
	 * @return
	 */
	 List<Map<String, Object>> orderList(String condition,Integer inUserid,Integer paysource,Integer payrole, String beginTime, String endTime);
	
	
	 Map<String, Object> orderStatistics(Integer paysource,Integer payrole);
	
	void exportOrderList(HttpServletResponse response,ServletOutputStream outputStream,String condition,Integer inUserid,Integer paysource,Integer payrole, String beginTime, String endTime);

	/**
	 * 充值记录
	 * @param condition
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<Map<String, Object>> userRechargeRecordList (String condition, String beginTime, String endTime);

	/**
	 * 导出充值记录
	 * @param response
	 * @param outputStream
	 * @param condition
	 * @param beginTime
	 * @param endTime
	 */
	void exportUserRechargeRecord (HttpServletResponse response,ServletOutputStream outputStream,String condition, String beginTime, String endTime);
}
