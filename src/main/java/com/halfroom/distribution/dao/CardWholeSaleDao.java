package com.halfroom.distribution.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.halfroom.distribution.persistence.model.CardWholeSale;

public interface CardWholeSaleDao {
	int insertSelective(CardWholeSale record);

	CardWholeSale selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CardWholeSale record);
	
	List<Map<String, Object>> cardWholeSaleList(Map<String, Object> map);
	
	 /**
     * 检查指定卡范围是否已经被划拨，但是还没有被接受
     * @param fromSalerId
     * @param toSalerId
     * @param status
     * @param cardNoStart
     * @param cardNoEnd
     * @return
     */
	 Boolean hasCardsInWholeSale(Map<String, Object> map);
}
