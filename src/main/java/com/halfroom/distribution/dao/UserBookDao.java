package com.halfroom.distribution.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.halfroom.distribution.persistence.model.UserBook;


public interface UserBookDao {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(UserBook record);

    UserBook selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBook record);

    int updateByPrimaryKey(UserBook record);
    
    UserBook selectByUseridAndBookid(@Param("userid")Integer userid,@Param("bookid")Integer bookid);
    
    List<Map<String, Object>> myBooks(Integer userid);
    
    Integer selectUseBookSumbyBookid(Map<String, Object> map);
}