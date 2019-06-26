package com.halfroom.distribution.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface NoticeDao {

    List<Map<String, Object>> list(@Param("condition") String condition);
}
