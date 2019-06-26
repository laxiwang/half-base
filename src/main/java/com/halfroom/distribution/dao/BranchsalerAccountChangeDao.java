package com.halfroom.distribution.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BranchsalerAccountChangeDao {
	List<Map<String, Object>> selectBranchsalerAccountChanges(Map<String,Object>  map);
	
	BigDecimal selectSum(Map<String,Object>  map);
	
	BigDecimal selectSumNohasOrder(Map<String,Object>  map);
	
	List<Map<String, Object>> branchsalerList(Map<String, Object> map);
	
	List<Map<String, Object>> selectBranchsalerAccountSum(Map<String,Object>  map);
	
	int batchUpdateBranchsalerAccountType(Map<String,Object>  map);

	BigDecimal selectAccountByAccountType(Map<String,Object>  map);

	List<Map<String, Object>> selectBranchsalerAccountDetails(Map<String,Object>  map);
}
