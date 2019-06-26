package com.halfroom.distribution.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.halfroom.distribution.core.util.QRCodeUtil;
import com.halfroom.distribution.dao.CardDao;
import com.halfroom.distribution.persistence.model.*;
import com.halfroom.distribution.persistence.vo.ProductCardVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;

import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.util.Convert;
import com.halfroom.distribution.core.util.DateTimeUtil;
import com.halfroom.distribution.core.util.ExcelUtil;
import com.halfroom.distribution.dao.CardWholeSaleDao;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.enum_class.CardStatus;
import com.halfroom.distribution.persistence.enum_class.CardWholeSaleStatus;
import com.halfroom.distribution.persistence.vo.CardWholeSaleVo;
import com.halfroom.distribution.service.IBranchsalerService;
import com.halfroom.distribution.service.ICardRawService;
import com.halfroom.distribution.service.ICardService;
import com.halfroom.distribution.service.ICardWholeSaleService;

@Service
@Transactional
public class CardWholeSaleImpl implements ICardWholeSaleService {
	@Resource
	private CardWholeSaleDao cardWholeSaleDao;
	@Resource
	private BranchsalerMapper branchsalerMapper;
	@Resource
	private ICardService cardService;
	@Resource
	private IBranchsalerService iBranchsalerService;
	@Resource
	private CardDao cardDao;
	@Resource
	ICardRawService icardRawService;
	@Override
	public int insertCardWholeSale(CardWholeSale record) {
		return cardWholeSaleDao.insertSelective(record);
	}

	@Override
	public CardWholeSale selectCardWholeSaleById(Integer id) {
		return cardWholeSaleDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateCardWholeSale(CardWholeSale record) {
		return cardWholeSaleDao.updateByPrimaryKeySelective(record);
	}
	@Override
	public Boolean hasCardsInWholeSale(Integer fromSalerId, Integer toSalerId, Integer status, Integer cardNoStart,
			Integer cardNoEnd) {
		Map<String, Object> map = new HashMap<>();
		map.put("fromSalerId", fromSalerId);
		map.put("toSalerId", toSalerId);
		map.put("status", status);
		map.put("cardNoStart", cardNoStart);
		map.put("cardNoEnd", cardNoEnd);
		return cardWholeSaleDao.hasCardsInWholeSale(map);
	}
	@Override
	public List<Map<String, Object>> cardWholeSaleList(String beginTime, String endTime, Integer status,
			boolean formOrTo) {
		Map<String, Object> map = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if (formOrTo) {
			map.put("from_saler_id", banchid);
		} else {
			map.put("to_saler_id", banchid);
		}
		if (StringUtils.isNotBlank(beginTime)) {
			map.put("beginTime", DateTimeUtil.parseDate(beginTime,DateTimeUtil.DEFAULT_FORMAT_DATE));
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime",endTime+" 23:59:59");
		}
		if (status != null) {
			map.put("status", status);
		}

		return cardWholeSaleDao.cardWholeSaleList(map);
	}
	@Override
	public void exportWholeList(HttpServletResponse response, ServletOutputStream outputStream,
			String beginTime, String endTime, Integer status, boolean formOrTo) {
		Map<String, Object> map = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return;
		}
		if (formOrTo) {
			map.put("from_saler_id", banchid);
		} else {
			map.put("to_saler_id", banchid);
		}
		if (beginTime != null && !beginTime.equals("null")) {
			map.put("beginTime", beginTime);
		}
		if (endTime != null && !endTime.equals("null")) {
			map.put("endTime",endTime+" 23:59:59");
		}
		if (status != null && status!=-1) {
			map.put("status", status);
		}
		List<Map<String, Object>> list=cardWholeSaleDao.cardWholeSaleList(map);
		List<CardWholeSaleVo> listCardWholeSaleVo=new ArrayList<>();
		for (Map<String, Object> map2 : list) {
			CardWholeSaleVo cardWholeSaleVo =(CardWholeSaleVo) Convert.mapToObject(map2, CardWholeSaleVo.class);
			listCardWholeSaleVo.add(cardWholeSaleVo);
		}
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		ExcelUtil<CardWholeSaleVo> myExcel = new ExcelUtil<>();
		String[] headerColumns = {};
		String[] fieldColumns =  {};
		if(formOrTo){
			 headerColumns=new String[]{"卡号范围","划拨给的分会","划拨状态","划拨类型","划拨时间","确认时间","备注"};
			 fieldColumns=new String[]{"cardNoRange","toBname","status","type","createtime","updatetime","remark"};
		}else{
			 headerColumns=new String[]{"卡号范围","划拨分会","划拨状态","划拨类型","划拨时间","确认时间","备注"};
			 fieldColumns=new String[]{"cardNoRange","fromBname","status","type","createtime","updatetime","remark"};
		}
		try {
			HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "划拨记录", listCardWholeSaleVo, headerColumns, fieldColumns,true);
			myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.doResponse(response, outputStream, "划拨记录", hssfWorkbook);
	}
	/**
	 * -1 当前分会 不存在 -2 被划拨分会 不能与划拨分会相同 -3 被划拨分会 不存在 -4 待划拨的会员卡范围错误，会员卡不存在或已激活！ -5
	 * 划拨卡号已被划拨正等待接收分会确认，请检查划拨会员卡号！ -6接受划拨分会不是当前分会的直属分会 !
	 */
	@Override
	public int wholeSale(CardWholeSale record) {
		record.setToSalerId(record.getBranchsaler());
		// 验证被划拨的分会不是自己分会
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return -1;
		}
		//验证划拨给的分会是不是当前分会的直属分会
		if(!iBranchsalerService.branchsalerDirectlyUnder(banchid, record.getToSalerId())){
			return -6;
		}
		record.setFromSalerId(banchid);
		if (record.getToSalerId() == banchid) {
			return -2;
		}
		Branchsaler branchsaler2 = branchsalerMapper.selectById(record.getToSalerId());
		if (branchsaler2 == null) {
			return -3;
		}

		int cardSum = record.getCardNoEnd() - record.getCardNoStart() + 1;
		// 检查划拨的卡号范围是否存在，并且归属当前分会，并且是未激活得
		Integer count = cardService.countCardOfBranch(record.getCardNoStart(),record.getCardNoEnd(),banchid,CardStatus.UN_ACTIVATE.getIndex());
		if (count != cardSum) {
			return -4;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("fromSalerId", banchid);
		map.put("status", CardWholeSaleStatus.WAIT_CONFIRM.getIndex());
		map.put("cardNoStart", record.getCardNoStart());
		map.put("cardNoEnd", record.getCardNoEnd());
		// 检查会员卡范围是否已经被当前分会划拨，但是分会未确认
		if (hasCardsInWholeSale(banchid,null,CardWholeSaleStatus.WAIT_CONFIRM.getIndex(),record.getCardNoStart(), record.getCardNoEnd()))
			return -5;
		// 生成划拨记录
		record.setStatus(CardWholeSaleStatus.WAIT_CONFIRM.getIndex());
		record.setCreatetime(new Date());
		cardWholeSaleDao.insertSelective(record);
		return 1;
	}

	/**
	 * -1.区间有激活的卡不让划拨.
	 * -2.区间在两个或者两个以上的分会不让划拨.
	 * -3.被划拨分会,与卡的原分会 是否在同一个省 不在不允许划拨.
	 *  -4 区间有待接收的卡, 不允许划拨
	 * @param record
	 * @return
	 */
	@Override
	@Transactional
	public int private_wholeSale(CardWholeSale record) {
		record.setToSalerId(record.getBranchsaler());
		record.setFromSalerId(1);
		Card card =cardDao.selectByCardNo(record.getCardNoStart());

		int cardSum = record.getCardNoEnd() - record.getCardNoStart() + 1;
		// 检查划拨的卡号范围是否存在，并且是未激活得
		Integer count1 = cardService.countCardOfBranch(record.getCardNoStart(),record.getCardNoEnd(),null,CardStatus.UN_ACTIVATE.getIndex());
		if (count1 != cardSum)
			return -1;
		//检查会员卡范围是否已经划拨，但是分会未确认
		if (hasCardsInWholeSale(null,null,CardWholeSaleStatus.WAIT_CONFIRM.getIndex(),record.getCardNoStart(), record.getCardNoEnd()))
			return -2;

		//检查卡所在的分会有几个。
		Integer count2=cardService.countBranchSalerOfCard(record.getCardNoStart(),record.getCardNoEnd());
		if(count2>=2)
			return -3;

		//检查卡的分会是否与被划拨分会相同
		if(card.getBranchsalerId()==record.getToSalerId())
			return -4;

		//检查卡的分会与被划拨分会是否一个省或者市

		if(!iBranchsalerService.branchsalerOnTeam(card.getBranchsalerId(), record.getToSalerId()))
			return -5;



		// 生成划拨记录
		record.setStatus(CardWholeSaleStatus.WAIT_CONFIRM.getIndex());
		record.setCreatetime(new Date());
		record.setType(1);
		cardWholeSaleDao.insertSelective(record);
		return 1;
	}

	/**
	 * -1 划拨记录不存在! -2 划拨记录已被确认接受，请与划拨给的分会联系处理!
	 * 
	 */
	@Override
	public int cancelWholeSale(int id) {
		CardWholeSale wholeSale = cardWholeSaleDao.selectByPrimaryKey(id);
		if (wholeSale == null) {
			return -1;
		}
		if (wholeSale.getStatus() == CardWholeSaleStatus.CONFIRMED.getIndex()) {
			return -2;
		}
		if (wholeSale.getStatus() == CardWholeSaleStatus.CANCELED.getIndex()) {
			return 1;
		}
		// 修改划拨记录状态
		CardWholeSale update = new CardWholeSale();
		update.setId(wholeSale.getId());
		update.setStatus(CardWholeSaleStatus.CANCELED.getIndex());
		cardWholeSaleDao.updateByPrimaryKeySelective(update);
		return 1;
	}

	/**
	 * -1 划拨记录不存在 -2 划拨记录已被划拨方取消，请与划拨分会联系! -3 登录人分会 与 接收划拨记录分会 不一致!
	 */
	@Override
	public int confirmWholeSale(int id) {
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return -1;
		}

		CardWholeSale wholeSale = cardWholeSaleDao.selectByPrimaryKey(id);
		if (wholeSale == null) {
			return -1;
		}
		if (wholeSale.getStatus() == CardWholeSaleStatus.CANCELED.getIndex()) {
			return -2;
		}
		if (wholeSale.getToSalerId() != banchid) {
			return -3;
		}
		if (wholeSale.getStatus() == CardWholeSaleStatus.CONFIRMED.getIndex()) {
			return -4;
		}
		// 1、确认划拨后，需要修改会员卡的归属渠道
		cardService.batchSetBranchOfCard(wholeSale.getCardNoStart(),wholeSale.getCardNoEnd(),banchid);

		// 2、修改划拨记录的状态
		CardWholeSale update = new CardWholeSale();
		update.setId(wholeSale.getId());
		update.setStatus(CardWholeSaleStatus.CONFIRMED.getIndex());
		update.setUpdatetime(new Date());
		cardWholeSaleDao.updateByPrimaryKeySelective(update);
		return 1;
	}

	@Override
	public Boolean checkHalfCard(Integer id) {
		CardWholeSale record = cardWholeSaleDao.selectByPrimaryKey(id);
		List<CardRaw> cardRaws = icardRawService.listCardRaw(record.getCardNoStart(),record.getCardNoEnd());
		boolean flag=false;
		for (CardRaw cardRaw : cardRaws) {
			if(cardRaw.getType()==0)
				flag=true;
				break;
		}
		return flag;
	}

	@Override
	public void exportHalfExecl(Integer id, HttpServletResponse response, ServletOutputStream outputStream) {
		List<ProductCardVo> list = new ArrayList<>();
		CardWholeSale record = cardWholeSaleDao.selectByPrimaryKey(id);

		if(record != null){
			//导出为Excel
			//excel为两列，一列是卡号， 一列是url
			List<CardRaw> cardRaws = icardRawService.listCardRaw(record.getCardNoStart(),record.getCardNoEnd());
			for(CardRaw cardraw : cardRaws){
				if(cardraw.getType()!=null&&cardraw.getType()==0)
					return ;
				String qrcordUrl = "";
				if(cardraw.getType()!=null&&cardraw.getType()==1)
					qrcordUrl = QRCodeUtil.CARD_URL_half+"?cardNumb="+cardraw.getCardNo()+"&cardCode="+cardraw.getCardCode();
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
			ExcelUtil.doResponse(response, outputStream, "半月卡信息", hssfWorkbook);
		}
	}

}
