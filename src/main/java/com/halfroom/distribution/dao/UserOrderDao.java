package com.halfroom.distribution.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderDao {
	 List<Map<String, Object>> selectOrders(Map<String,Object>  map);
	 
	 //城市订单数
	 Integer selectOrderNumBycity(Map<String,Object>  map);
	 
	  //订单分布统计市   
	 List<Map<String, Object>> selectCityBybid (Map<String, Object> map);
	 //支付方式统计单数  flag 是否统计包含下级  true 包含 false 不包含
	 Integer  selectCount(Map<String, Object> map);

	//支付方式统计人数   flag 是否统计包含下级  true 包含 false 不包含
	List<Map<String, Object>>  selectCountUser(Map<String, Object> map);
	//充值记录
	List<Map<String, Object>> userRechargeRecordList (Map<String, Object> map);
}
