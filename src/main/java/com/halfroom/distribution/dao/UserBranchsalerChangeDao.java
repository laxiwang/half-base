package com.halfroom.distribution.dao;

import java.util.List;
import java.util.Map;

import com.halfroom.distribution.persistence.model.UserBranchsalerChange;

public interface UserBranchsalerChangeDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(UserBranchsalerChange record);

    UserBranchsalerChange selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBranchsalerChange record);
    
    List<Map<String, Object>>  selectLists(Map<String, Object> params); 

}