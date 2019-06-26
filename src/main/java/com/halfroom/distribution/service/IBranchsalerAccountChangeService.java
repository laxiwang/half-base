package com.halfroom.distribution.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.halfroom.distribution.rabbitmq.MessageBean.DistributionMessageBean;

public interface IBranchsalerAccountChangeService {
	/**
	 * 查询交易记录
	 * @param condition
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	 List<Map<String, Object>> selectBranchsalerAccountChanges( String condition,Integer accountType,Integer paysource,Integer payrole ,String beginTime,  String endTime);

	/**
	 * 导出表格
	 * @param condition
	 * @param accountType
	 * @param beginTime
	 * @param endTime
	 */
	  void exportList(  HttpServletResponse response,ServletOutputStream outputStream, String condition,Integer accountType,Integer paysource,Integer payrole ,String beginTime,  String endTime);

	/**
	 * 统计账户余额
	 * @param condition
	 * @param accountType
	 * @param paysource
	 * @param payrole
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	Map<String, Object> statistics(String condition,Integer accountType,Integer paysource,Integer payrole ,String beginTime,  String endTime);
	/**
	 * 添加交易记录
	 * @param distributionMessageBean
	 */
	 void insertAccountsChange(DistributionMessageBean distributionMessageBean);
	/**
	 * 分润
	 * @param distributionMessageBean
	 */
	 void branchsalerDistribution(DistributionMessageBean distributionMessageBean);
	/**
	 * 分润结算展示--分会余额 
	 * @param condition
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	 List<Map<String, Object>> branchsalerAccountSum(String condition, String beginTime,  String endTime,String paysources);
	
	/**
	 * 结算
	 * @param beginTime
	 * @param endTime
	 */
	 int  settlement_do(String condition,String beginTime,  String endTime,String paysources);

	/**
	 * 结算记录
	 * @param condition
	 * @param bsr_status
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	 List<Map<String, Object>> settlementRecord(String condition,Integer bsr_status,Integer pay_status,String beginTime,  String endTime);
	
	 void exportsettlementRecord(HttpServletResponse response,ServletOutputStream outputStream,  String condition,Integer bsr_status,Integer pay_status, String beginTime, String endTime) ;

	/**
	 * 分会分润详情
	 * @param condition
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	 List<Map<String, Object>>  branchsalerAccountDetails(String condition,String beginTime,  String endTime);

	 void exportBranchsalerAccountDetails(HttpServletResponse response,ServletOutputStream outputStream,String condition,String beginTime,  String endTime);
}
