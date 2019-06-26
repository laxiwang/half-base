package com.halfroom.distribution.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.halfroom.distribution.core.util.QRCodeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.halfroom.distribution.core.util.DateTimeUtil;
import com.halfroom.distribution.core.util.ExcelUtil;
import com.halfroom.distribution.core.util.ToolUtil;
import com.halfroom.distribution.dao.CardProductRecordDao;
import com.halfroom.distribution.persistence.model.CardProductRecord;
import com.halfroom.distribution.persistence.model.CardRaw;
import com.halfroom.distribution.persistence.vo.ProductCardVo;
import com.halfroom.distribution.service.ICardProductRecordService;
import com.halfroom.distribution.service.ICardRawService;
import com.halfroom.distribution.service.ICardService;

@Service
@Transactional
public class CardProductRecordServiceImpl implements ICardProductRecordService {
	@Resource
	private CardProductRecordDao cardProductRecordDao;
	@Resource
	private ICardRawService icardRawService;
	@Resource
	private ICardService iCardService;

	@Override
	public int deleteCardProductRecord(Integer id) {
		return cardProductRecordDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insertCardProductRecord(CardProductRecord record) {
		return cardProductRecordDao.insertSelective(record);
	}

	@Override
	public CardProductRecord selectCardProductRecordById(Integer id) {
		return cardProductRecordDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateCardProductRecord(CardProductRecord record) {
		return cardProductRecordDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Map<String, Object>> cardProductRecordList(String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotBlank(beginTime)) {
			map.put("beginTime", DateTimeUtil.parseDate(beginTime,DateTimeUtil.DEFAULT_FORMAT_DATE));
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime",endTime+" 23:59:59");
		}
		return cardProductRecordDao.cardProductRecordsByTime(map);
	}

	/**
	 * -1 生产卡段错误，已经有卡号被生产
	 * 
	 * 1 成功
	 */
	@Override
	public synchronized int addcardProductRecordAndCardRaw(CardProductRecord record) {
		Map<String, Object> map = new HashMap<>();
		map.put("cardNoStart", record.getCardNoStart());
		map.put("cardNoEnd", record.getCardNoEnd());
		// 判断要生产的卡号范围是否存在
		int count = icardRawService.countCardRaw(record.getCardNoStart(),record.getCardNoEnd());
		if (count > 0) {
			return -1;
		}
		if(record.getType()!=null&&record.getType()!=0){
			record.setFee(new BigDecimal("0.0"));
		}
		record.setCreatetime(new Date());
		// 创建生产记录
		cardProductRecordDao.insertSelective(record);
		// 创建cardraw, 以1000分段创建吧
		count = record.getCardNoEnd().intValue() - record.getCardNoStart().intValue() + 1;
		// 1000的整数倍部分
		for (int index = 0; index < (count / 1000); index++) {
			List<CardRaw> cardRaws = new ArrayList<CardRaw>();
			int startNo = record.getCardNoStart() + index * 1000;
			for (int i = 0; i < 1000; i++) {
				CardRaw cardRaw = new CardRaw(startNo + i, ToolUtil.getRandomString2(12),record.getFee(),record.getType());
				cardRaws.add(cardRaw);
			}
			icardRawService.batchCreateCardRaw(cardRaws);
		}
		// 余数部分
		if (count % 1000 > 0) {
			List<CardRaw> cardRaws = new ArrayList<CardRaw>();
			int startNo = record.getCardNoStart() + (count / 1000) * 1000;
			for (int i = 0; i < count % 1000; i++) {
				CardRaw cardRaw = new CardRaw(startNo + i, ToolUtil.getRandomString2(12),record.getFee(),record.getType());
				cardRaws.add(cardRaw);
			}
			icardRawService.batchCreateCardRaw(cardRaws);
		}

		return 1;
	}

	/**
	 * -1 生产记录不存在 -2 当前记录已经投放不能删除 -3 已经有卡号被投放
	 */
	@Override
	public synchronized int delcardProductRecordAndCardRaw(Integer id) {

		CardProductRecord record = cardProductRecordDao.selectByPrimaryKey(id);
		if (record == null) {
			return -1;
		}
		if (record.getStatus() == 1) {
			return -2;
		}
		// 判断是否已经有卡号被投放
		int count = iCardService.countCardOfBranch( record.getCardNoStart(),record.getCardNoEnd(),null,null);
		if (count > 0) {
			return -3;
		}

		// 删除cardraw
		icardRawService.deleteCardRaw(record.getCardNoStart(),record.getCardNoEnd());
		// 删除记录
		cardProductRecordDao.deleteByPrimaryKey(id);
		return 1;
	}

	/**
	 * -1 生产记录不存在 -2 当前记录已投放 -3 已经有卡号被投放
	 */
	@Override
	public synchronized int puton(Integer id) {
		CardProductRecord record = cardProductRecordDao.selectByPrimaryKey(id);
		if (record == null) {
			return -1;
		}
		if (record.getStatus() == 1) {
			return -2;
		}
		// 判断是否已经有卡号被投放
		int putcount = iCardService.countCardOfBranch(record.getCardNoStart(),record.getCardNoEnd(),null,null);
		if (putcount > 0) {
			return -3;
		}
		// 在card表中创建会员卡,1000的整数倍部分
		List<CardRaw> cardRaws = icardRawService.listCardRaw(record.getCardNoStart(),record.getCardNoEnd());
		int count = cardRaws.size();
		for (int index = 0; index < (count / 1000); index++) {
			iCardService.createCard(cardRaws.subList(index * 1000, (index + 1) * 1000));
		}
		// 余数部分
		if (count % 1000 > 0) {
			iCardService.createCard(cardRaws.subList((count / 1000) * 1000, count));
		}

		// 设置生产记录的最新投放时间
		CardProductRecord update = new CardProductRecord();
		update.setId(record.getId());
		update.setStatus(1);
		update.setPutontime(new Date());
		cardProductRecordDao.updateByPrimaryKeySelective(update);
		return 1;
	}

	@Override
	public  synchronized void exportExcel(HttpServletResponse response, ServletOutputStream outputStream,Integer id) {
		List<ProductCardVo> list = new ArrayList<>();
		CardProductRecord record = cardProductRecordDao.selectByPrimaryKey(id);
		if(record != null){
			//导出为Excel
			//excel为两列，一列是卡号， 一列是url
			List<CardRaw> cardRaws = icardRawService.listCardRaw(record.getCardNoStart(),record.getCardNoEnd());
			for(CardRaw cardraw : cardRaws){
				String qrcordUrl = "";
				if(cardraw.getType()!=null&&cardraw.getType()==0){
					qrcordUrl = QRCodeUtil.CARD_URL_PAY+"?cardNumb="+cardraw.getCardNo()+"&cardCode="+cardraw.getCardCode();
				}else if(cardraw.getType()!=null&&cardraw.getType()==1){
					qrcordUrl = QRCodeUtil.CARD_URL_half+"?cardNumb="+cardraw.getCardNo()+"&cardCode="+cardraw.getCardCode();
				}
				ProductCardVo productCardVo = new ProductCardVo(cardraw.getCardNo().toString(), qrcordUrl,cardraw.getFee());
				list.add(productCardVo);
			}
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			ExcelUtil<ProductCardVo> myExcel = new ExcelUtil<>();
			String[] headerColumns = {"cardNo","fee", "url"};
			String[] fieldColumns = {"cardNo", "fee","qrcordUrl"};
			try {
				HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "卡片信息", list, headerColumns, fieldColumns,true);
				myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ExcelUtil.doResponse(response, outputStream, "卡片信息", hssfWorkbook);
			//设置生产记录的最新导出时间
			CardProductRecord update = new CardProductRecord();
			update.setId(record.getId());
			update.setExporttime(new Date());
			cardProductRecordDao.updateByPrimaryKeySelective(update);
		}
	
	
	}


}
