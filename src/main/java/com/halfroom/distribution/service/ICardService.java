package com.halfroom.distribution.service;

import java.util.List;
import java.util.Map;

import com.halfroom.distribution.common.page.PageInfoBT;
import com.halfroom.distribution.persistence.model.CardRaw;


/**
 * 成品卡相关服务
 * @author tingyunjava
 *
 */

public interface ICardService {
	/**
	 * 会员卡查询操作
	 * @param cardNoStart
	 * @param cardNoEnd
	 * @param branchSalerId
	 * @param status
	 * @return
	 */
	Integer countCardOfBranch(Integer cardNoStart,Integer cardNoEnd,Integer branchSalerId,Integer status);

	/**
	 * 检查会员卡在多少个分会
	 * @return
	 */
	Integer countBranchSalerOfCard(Integer cardNoStart,Integer cardNoEnd);

	//批量创建会员卡
    Integer createCard(List<CardRaw> cardRaws);
    /**
     * 批量设置会员卡的归属分会
     * @param cardNoStart
     * @param cardNoEnd
     * @param branchSalerId
     * @return
     */
  	Integer batchSetBranchOfCard(Integer cardNoStart,Integer cardNoEnd,Integer branchSalerId);
  	/**
  	 * 将会员卡号批量设置未已激活
  	 * @param cardNoStart
  	 * @param cardNoEnd
  	 * @param status
  	 * @return
  	 */
  	Integer batchSetStatusOfCard(Integer cardNoStart,Integer cardNoEnd,Integer status);
  	
  	PageInfoBT<Map<String, Object>>  cardList(Integer offset,Integer limit,String sort,String order,String cardNoStart, String cardNoEnd,Integer status);
  	/**
  	 * 实体卡使用记录
  	 * @param cardNoStart
  	 * @param cardNoEnd
  	 * @return
  	 */
  	List<Map<String, Object>> cardUseRecordList(String cardNoStart, String cardNoEnd,String phone);
}
