package com.halfroom.distribution.dao;

import java.util.List;
import java.util.Map;
import com.halfroom.distribution.persistence.model.CardProductRecord;

public interface CardProductRecordDao {
	
    int deleteByPrimaryKey(Integer id);

    int insertSelective(CardProductRecord record);

    CardProductRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CardProductRecord record);
	
    /**
     * 范围查找生产记录
     * @param map
     * @return
     */
	List<Map<String, Object>> cardProductRecordsByTime(Map<String, Object> map);
}
