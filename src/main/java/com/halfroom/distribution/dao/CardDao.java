package com.halfroom.distribution.dao;

import java.util.List;
import java.util.Map;

import com.halfroom.distribution.persistence.model.Card;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.halfroom.distribution.persistence.model.CardRaw;


public interface CardDao {
	Card selectByCardNo(Integer cardNo);
	List<Map<String, Object>>  cardList(Map<String, Object> map,@Param("page")Page page);
	
	Integer countCardOfBranch(Map<String, Object> map);

	List<Map<String, Object>>  countBranchSalerOfCard(Map<String, Object> map);

	
	Integer insertListSelective(@Param("cardRaws") List<CardRaw> cardRaws);
	//批量更新卡分会
	Integer batchUpdateCardBranch(@Param("cardNos") List<Integer> cardNos, @Param("branchsaler") Integer branchsaler);
	//批量更新卡状态
	Integer batchUpdateCardStatus(@Param("cardNos") List<Integer> cardNos, @Param("status") Integer status);
	
	List<Map<String, Object>> selectCardUseRecordList(Map<String, Object> map);
}
