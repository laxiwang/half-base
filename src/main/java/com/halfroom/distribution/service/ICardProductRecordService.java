package com.halfroom.distribution.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.halfroom.distribution.persistence.model.CardProductRecord;

/**
 * 实体卡生产记录相关服务
 * @author tingyunjava
 *
 */
public interface ICardProductRecordService {
	
	int deleteCardProductRecord(Integer id);

	int insertCardProductRecord(CardProductRecord record);

	CardProductRecord selectCardProductRecordById(Integer id);

	int updateCardProductRecord(CardProductRecord record);
	/**
     * 范围查找生产记录
     * @param map
     * @return
     */
	List<Map<String, Object>> cardProductRecordList(String beginTime,String endTime);
	/**
	 * 增加生产记录同时产生半成品卡
	 */
	int  addcardProductRecordAndCardRaw(CardProductRecord record);
	/**
	 * 删除生产记录，同时删除cardRaw 
	 * @return
	 */
	int  delcardProductRecordAndCardRaw(Integer id);
	/**
	 * 投放生产记录----更新 投放记录状态 ，同时 创建成品卡   
	 * @param id
	 * @return
	 */
	int  puton(Integer id);
	/**
	 * 通过生产记录 --导出半成品实体卡 表格，同时更新导出时间
	 * @param id
	 */
	void exportExcel(HttpServletResponse response, ServletOutputStream outputStream,Integer id);

}
