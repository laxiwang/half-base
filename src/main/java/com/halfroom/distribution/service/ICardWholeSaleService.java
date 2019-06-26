package com.halfroom.distribution.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


import com.halfroom.distribution.persistence.model.CardWholeSale;

/**
 * 划拨记录相关服务
 * @author tingyunjava
 *
 */
public interface ICardWholeSaleService {
	
	
	int insertCardWholeSale(CardWholeSale record);

	CardWholeSale selectCardWholeSaleById(Integer id);

	int updateCardWholeSale(CardWholeSale record);
	
	/**
     * 划拨记录列表
     * @param map
     * @return
     */
	List<Map<String, Object>> cardWholeSaleList(String beginTime,String endTime,Integer status,boolean formOrTo);
	
	void exportWholeList(HttpServletResponse response,ServletOutputStream outputStream,String beginTime,String endTime,Integer status,boolean formOrTo);
	/**
	 * 划拨操作
	 */
	int wholeSale(CardWholeSale record);

	/**
	 * 调拨操作
	 * @return
	 */
	int private_wholeSale(CardWholeSale record);

	/**
	 * 取消划拨--划拨记录
	 */
    int cancelWholeSale(int id);
    /**
     * 接收划拨记录
     */
    int confirmWholeSale(int id);
    
	 /**
     * 检查指定卡范围是否已经被划拨，但是还没有被接受
     * @param fromSalerId
     * @param toSalerId
     * @param status
     * @param cardNoStart
     * @param cardNoEnd
     * @return
     */
	 Boolean hasCardsInWholeSale(Integer fromSalerId,Integer toSalerId, Integer status, Integer cardNoStart, Integer cardNoEnd);
	 
	 Boolean checkHalfCard(Integer id);

	 void exportHalfExecl(Integer id,HttpServletResponse response, ServletOutputStream outputStream);
}
