package com.halfroom.distribution.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.halfroom.distribution.persistence.model.CardRaw;

public interface CardRawDao {
	
	Integer insertListSelective(@Param("cardRaws") List<CardRaw> cardRaws);
	
	Integer countCardRaw(Map<String, Object> map);
	
	Integer deleteCardRawRange(Map<String, Object> map);
	
	List<CardRaw> cardRawListRange(Map<String, Object> map);
}
