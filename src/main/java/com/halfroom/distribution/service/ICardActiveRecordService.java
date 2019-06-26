package com.halfroom.distribution.service;
/**
 * 实体卡激活相关服务
 * @author tingyunjava
 *
 */

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.halfroom.distribution.persistence.model.CardActiveRecord;


public interface ICardActiveRecordService {
	/**
	 * 激活记录列表
	 * @param map
	 * @return
	 */
	List<Map<String, Object>>   cardActiveRecordList(String beginTime,String endTime);
	
	/**
	 * 新增激活记录
	 */
	int  insertardActiveRecord(CardActiveRecord record);
	
	void exportCardActiveRecordList(HttpServletResponse response,ServletOutputStream outputStream,String beginTime,String endTime); 
}
