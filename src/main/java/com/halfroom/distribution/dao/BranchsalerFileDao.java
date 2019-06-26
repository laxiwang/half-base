package com.halfroom.distribution.dao;


import java.util.List;
import java.util.Map;

public interface BranchsalerFileDao {
    List<Map<String,Object>> list(Map<String,Object> map);
}