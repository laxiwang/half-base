package com.halfroom.distribution.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.halfroom.distribution.persistence.model.BranchsalerSettlementRecord;

public interface BranchsalerSettlementRecordDao {

    int insertSelective(BranchsalerSettlementRecord record);

    BranchsalerSettlementRecord selectByPrimaryKey(Integer id);
    
    List<Map<String, Object>>   selectSettlementRecordList(Map<String, Object> map);

    BigDecimal settlementMoney(Map<String, Object> map);
 }