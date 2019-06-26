package com.halfroom.distribution.dao;

import java.util.List;
import java.util.Map;

import com.halfroom.distribution.persistence.model.CardActiveRecord;

public interface CardActiveRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(CardActiveRecord record);

    CardActiveRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CardActiveRecord record);
    
    List<Map<String, Object>>   cardActiveRecordList(Map<String, Object> map);

}