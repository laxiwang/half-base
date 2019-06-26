package com.halfroom.distribution.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.util.Convert;
import com.halfroom.distribution.core.util.DateTimeUtil;
import com.halfroom.distribution.core.util.ExcelUtil;
import com.halfroom.distribution.dao.CardActiveRecordDao;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.enum_class.CardStatus;
import com.halfroom.distribution.persistence.enum_class.CardWholeSaleStatus;
import com.halfroom.distribution.persistence.model.Branchsaler;
import com.halfroom.distribution.persistence.model.CardActiveRecord;
import com.halfroom.distribution.persistence.vo.CardActiveRecordVo;
import com.halfroom.distribution.persistence.vo.GeneralUserVo;
import com.halfroom.distribution.service.ICardActiveRecordService;
import com.halfroom.distribution.service.ICardService;
import com.halfroom.distribution.service.ICardWholeSaleService;

@Service
@Transactional
public class CardActiveRecordImpl implements ICardActiveRecordService {
	@Resource
	private  BranchsalerMapper branchsalerMapper;
	@Resource
	private CardActiveRecordDao cardActiveRecordDao;
	@Resource
	private ICardService iCardService;
	@Resource
	private ICardWholeSaleService iCardWholeSaleService;

	@Override
	public List<Map<String, Object>> cardActiveRecordList(String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		map.put("branchSaler", banchid);
		if (StringUtils.isNotBlank(beginTime)&&!"null".equals(beginTime)) {
			map.put("beginTime", DateTimeUtil.parseDate(beginTime,DateTimeUtil.DEFAULT_FORMAT_DATE));
		}
		if (StringUtils.isNotBlank(endTime)&&!"beginTime".equals(beginTime)) {
			map.put("endTime",endTime+" 23:59:59");
		}
		return cardActiveRecordDao.cardActiveRecordList(map);
	}
	@Override
	public void exportCardActiveRecordList(HttpServletResponse response, ServletOutputStream outputStream,
			String beginTime, String endTime) {

		List<Map<String, Object>> list= cardActiveRecordList(beginTime,endTime);
		List<CardActiveRecordVo> listcardActiveRecordVo = new ArrayList<>();
		for (Map<String, Object> map2 : list) {
			CardActiveRecordVo cardActiveRecordVo = (CardActiveRecordVo)Convert.mapToObject(map2, CardActiveRecordVo.class);
			listcardActiveRecordVo.add(cardActiveRecordVo);
		}
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		ExcelUtil<CardActiveRecordVo> myExcel = new ExcelUtil<>();
		String[] headerColumns = {"卡号范围" ,  "激活时间" , "备注" };
		String[] fieldColumns =  {"cardNoRange", "createtime","remark"};
		try {
			HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "激活记录", listcardActiveRecordVo, headerColumns, fieldColumns,true);
			myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.doResponse(response, outputStream, "激活记录", hssfWorkbook);
	}

	/**
	 * -1  会员卡号已激活或者已使用，请检查会员卡号!  -2 激活卡号不属于您的分会，请检查会员卡号 !
	 * -3 划拨卡号已被划拨正等待接收分会确认，请检查激活会员卡号!
	 */
	@Override
	public int insertardActiveRecord(CardActiveRecord record) {
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return -1;
		}
		int cardSum = record.getCardNoEnd() - record.getCardNoStart() + 1;
		
		// 1.检查会员卡号是否都是未激活状态
		Integer count = iCardService.countCardOfBranch(record.getCardNoStart(), record.getCardNoEnd(), banchid,
				CardStatus.UN_ACTIVATE.getIndex());
		if (count != cardSum) {
			return -1;
		}
		// 2.检查激活会员卡是否属于该分会
		count=iCardService.countCardOfBranch(record.getCardNoStart(), record.getCardNoEnd(), null,
				CardWholeSaleStatus.WAIT_CONFIRM.getIndex());
		if (count != cardSum) {
			return -2;
		}
	
		// 3.检查会员卡是否已经被划拨但是还没有接受的记录
		if (iCardWholeSaleService.hasCardsInWholeSale(banchid, null, CardWholeSaleStatus.WAIT_CONFIRM.getIndex(),
				record.getCardNoStart(), record.getCardNoEnd())) {
			return -3;
		}

		// ok，将会员卡号批量设置未已激活
		iCardService.batchSetStatusOfCard(record.getCardNoStart(), record.getCardNoEnd(),
				CardStatus.ACTIVATE.getIndex());
		// 创建激活记录
		record.setBranchsalerId(banchid);
		record.setCreatetime(new Date());
		cardActiveRecordDao.insertSelective(record);
		return 1;
	}
}
