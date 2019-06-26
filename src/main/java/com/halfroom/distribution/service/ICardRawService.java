package com.halfroom.distribution.service;

import java.util.List;

import com.halfroom.distribution.persistence.model.CardRaw;

/**
 * 半成品卡相关服务
 * @author tingyunjava
 *
 */
public interface ICardRawService {
	
	//批量创建
	Integer batchCreateCardRaw(List<CardRaw> cardRaws);
	//按照卡号范围获取
	List<CardRaw> listCardRaw(Integer cardNoStart,Integer cardNoEnd);
	//删除操作
	Integer deleteCardRaw(Integer cardNoStart,Integer cardNoEnd);
	//获取指定卡号范围的实际数量
	Integer countCardRaw(Integer cardNoStart,Integer cardNoEnd);
}
