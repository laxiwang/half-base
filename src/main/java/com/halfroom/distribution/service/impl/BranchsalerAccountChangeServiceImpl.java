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

import com.halfroom.distribution.persistence.vo.BranchSalerAccountDetailsVo;
import com.halfroom.distribution.persistence.vo.BranchSalerAccountVo;
import com.halfroom.distribution.persistence.vo.UserOrderVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.halfroom.distribution.common.constant.factory.ConstantFactory;
import com.halfroom.distribution.common.exception.BizExceptionEnum;
import com.halfroom.distribution.common.exception.BussinessException;
import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.util.Convert;
import com.halfroom.distribution.core.util.DateTimeUtil;
import com.halfroom.distribution.core.util.ExcelUtil;
import com.halfroom.distribution.core.util.ToolUtil;
import com.halfroom.distribution.dao.BranchsalerAccountChangeDao;
import com.halfroom.distribution.dao.BranchsalerDao;
import com.halfroom.distribution.dao.BranchsalerSettlementRecordDao;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.model.Branchsaler;
import com.halfroom.distribution.persistence.model.BranchsalerAccountChange;
import com.halfroom.distribution.persistence.model.BranchsalerAmount;
import com.halfroom.distribution.persistence.model.BranchsalerSettlementRecord;
import com.halfroom.distribution.persistence.vo.SettlementRecordVo;
import com.halfroom.distribution.rabbitmq.MessageBean.DistributionMessageBean;
import com.halfroom.distribution.service.IBranchsalerAccountChangeService;

/**************************************************************************
 *
 *
 * @author along
 * @date Nov 28, 2018 10:15:36 PM
 *************************************************************************/

@Transactional
@Service
public class BranchsalerAccountChangeServiceImpl implements IBranchsalerAccountChangeService {
	@Resource
	private BranchsalerMapper branchsalerMapper;
	@Resource
	private BranchsalerAccountChangeDao branchsalerAccountChangeDao;
	@Resource
	private BranchsalerSettlementRecordDao branchsalerSettlementRecordDao;
	@Resource
	private BranchsalerDao branchsalerDao;



	@Override
	public List<Map<String, Object>> selectBranchsalerAccountChanges( String condition,Integer accountType,Integer paysource,Integer payrole ,String beginTime,  String endTime) {
		Map<String, Object> map = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if(StringUtils.isNotBlank(condition)&&!"null".equals(condition)){
			map.put("condition", condition);
		}

		map.put("branchsaler", branchsaler.getId());


		if(accountType!=null&&-1!=accountType){
			map.put("accountType", accountType);
		}
		if(StringUtils.isNotBlank(beginTime)&&!"null".equals(beginTime)){
			map.put("beginTime", beginTime);
		}
		if(StringUtils.isNotBlank(endTime)&&!"null".equals(endTime)){
			map.put("endTime", endTime+" 23:59:59");
		}
		if(paysource!=null&&-1!=paysource){
			map.put("paysource", paysource);
		}
		if(payrole!=null&&-1!=payrole){
			map.put("payrole", payrole);
		}
		return branchsalerAccountChangeDao.selectBranchsalerAccountChanges(map);
	}

	@Override
	public void exportList(  HttpServletResponse response,ServletOutputStream outputStream, String condition,Integer accountType,Integer paysource,Integer payrole ,String beginTime,  String endTime) {
		List<Map<String, Object>> listMap=selectBranchsalerAccountChanges(condition,accountType,paysource,payrole,beginTime,endTime);
		List<BranchSalerAccountVo> listBr = new ArrayList<>();
		for (Map<String, Object> map2 : listMap) {
			BranchSalerAccountVo branchSalerAccountVo = (BranchSalerAccountVo)Convert.mapToObject( map2,BranchSalerAccountVo.class);
			listBr.add(branchSalerAccountVo);
		}
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		ExcelUtil<BranchSalerAccountVo> myExcel = new ExcelUtil<>();
		String[] headerColumns = {"订单号","订单来源","分润金额","分润时间","订单支付渠道","订单支付方式"};
		String[] fieldColumns =  {"orderno","orderBranchsaler","changeAmount","addTime","paySource","payRole"};
		try {
			HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "分润信息", listBr, headerColumns, fieldColumns,true);
			myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.doResponse(response, outputStream, "分润信息", hssfWorkbook);

	}

	@Override
	public Map<String, Object> statistics(String condition,Integer accountType,Integer paysource,Integer payrole ,String beginTime,  String endTime) {
		Map<String, Object> map = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if(StringUtils.isNotBlank(condition)&&!"null".equals(condition)){
			map.put("condition", condition);
		}

		map.put("branchsaler", branchsaler.getId());


		if(accountType!=null&&-1!=accountType){
			map.put("accountType", accountType);
		}
		if(StringUtils.isNotBlank(beginTime)&&!"null".equals(beginTime)){
			map.put("beginTime", beginTime);
		}
		if(StringUtils.isNotBlank(endTime)&&!"null".equals(endTime)){
			map.put("endTime", endTime+" 23:59:59");
		}
		if(paysource!=null&&-1!=paysource){
			map.put("paysource", paysource);
		}
		if(payrole!=null&&-1!=payrole){
			map.put("payrole", payrole);
		}
		Map<String, Object> result = new HashMap<>();
		BigDecimal all = branchsalerAccountChangeDao.selectSumNohasOrder(map).setScale(2, BigDecimal.ROUND_HALF_UP);
/*		map.put("type", 0);
		BigDecimal in = branchsalerAccountChangeDao.selectSumNohasOrder(map).setScale(2, BigDecimal.ROUND_HALF_UP);
		map.put("type", 1);
		BigDecimal out = branchsalerAccountChangeDao.selectSumNohasOrder(map).setScale(2, BigDecimal.ROUND_HALF_UP);
		result.put("all", all);
		result.put("in", in);
		result.put("out", out);*/
		result.put("all", all);
		return result;
	}

	// 提现结算
	@Override
	public void insertAccountsChange(DistributionMessageBean distributionMessageBean) {
		BranchsalerAccountChange branchsalerAccountChange = new BranchsalerAccountChange();
		// 获取当前协会账户
		int branchsalerId = distributionMessageBean.getBranchsalerId();
		BranchsalerAmount branchsalerAmount = new BranchsalerAmount();

		EntityWrapper<BranchsalerAmount> wrapper = new EntityWrapper<BranchsalerAmount>();
		wrapper.eq("branchsaler_id", branchsalerId);
		branchsalerAmount = branchsalerAmount.selectOne(wrapper);

		if (distributionMessageBean.getType().equals("1")
				&& (ToolUtil.isEmpty(branchsalerAmount) || distributionMessageBean.getAmount()
						.doubleValue() > branchsalerAmount.getAvaibleAmount().doubleValue())) {
			throw new BussinessException(BizExceptionEnum.ACCOUNT_ILLEGAL);
		}

		BranchsalerAmount branchsalerAmountToInsert = new BranchsalerAmount();

		branchsalerAmountToInsert.setId(branchsalerAmount.getId());
		branchsalerAmountToInsert.setBranchsalerId(branchsalerAmount.getBranchsalerId());
		// branchsalerAmountToInsert.setBranchsalerName(branchsalerAmount.getBranchsalerName());
		if (distributionMessageBean.getType().equals("1")) {
			// 提现
			branchsalerAmountToInsert
					.setTotalAmount(branchsalerAmount.getTotalAmount().subtract(distributionMessageBean.getAmount()));
			branchsalerAmountToInsert.setAvaibleAmount(
					branchsalerAmount.getAvaibleAmount().subtract(distributionMessageBean.getAmount()));
		} else {
			// 收入
			branchsalerAmountToInsert
					.setTotalAmount(branchsalerAmount.getTotalAmount().add(distributionMessageBean.getAmount()));
			branchsalerAmountToInsert
					.setAvaibleAmount(branchsalerAmount.getAvaibleAmount().add(distributionMessageBean.getAmount()));
		}
		// branchsalerAmountToInsert.setFrozenAmount(branchsalerAmount.getFrozenAmount());
		// branchsalerAmountToInsert.setAddTime(branchsalerAmount.getAddTime());
		branchsalerAmountToInsert.setUpdateTime(distributionMessageBean.getCreatetime());
		// branchsalerAmountToInsert.setAmountStatus(branchsalerAmount.getAmountStatus());
		branchsalerAmountToInsert.updateById();

		branchsalerAccountChange.setBranchsalerId(distributionMessageBean.getBranchsalerId());
		branchsalerAccountChange.setBeforeChangeAmount(branchsalerAmount.getTotalAmount());
		branchsalerAccountChange.setAfterChangeAmount(branchsalerAmountToInsert.getTotalAmount());
		branchsalerAccountChange.setType(distributionMessageBean.getType());
		branchsalerAccountChange.setChangeAmount(distributionMessageBean.getAmount());
		branchsalerAccountChange.setOrderno(distributionMessageBean.getOrderno());
		branchsalerAccountChange.setAddTime(distributionMessageBean.getCreatetime());
		System.out.println(" insert 之前  ======amount:" + distributionMessageBean.getAmount());
		branchsalerAccountChange.insert();
	}

	// 分润
	@Override
	public void branchsalerDistribution(DistributionMessageBean distributionMessageBean) {
		int branchsalerId = distributionMessageBean.getBranchsalerId();

		List<Integer> listParentsID = ConstantFactory.me().getParentBranchsalerIds(branchsalerId);

		Map<Integer, BigDecimal> distributionMap = new HashMap<>();

		BigDecimal amount = distributionMessageBean.getAmount();
		Branchsaler branchsaler = new Branchsaler();
		branchsaler = branchsaler.selectById(distributionMessageBean.getBranchsalerId());
		int level = branchsaler.getLevel();

		if (level == 4) {// 用户属于小渠道
			branchsalerDistributionWithOtherChannel(distributionMessageBean);
		} else {
			double distributionPercent;

			if (distributionMessageBean.getSalerType().equals("0")) {// 推广大使
				distributionPercent = 0.3;// 区级
			} else {// 销售大使
				distributionPercent = 0.35;// 区级
			}

			if (listParentsID.size() == 1) {// 只有总会，没有省级和以下的分会

				distributionMap.put(distributionMessageBean.getBranchsalerId(), BigDecimal.valueOf(1));

			} else if (listParentsID.size() == 2) {
				if (level == 1) {// 用户直属省级分会 总会----省级
					distributionMap.put(distributionMessageBean.getBranchsalerId(),
							BigDecimal.valueOf(distributionPercent + 0.2));
					distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent - 0.2));
				} else if (level == 2) { // 没有省级分会，总会----市级分会
					distributionMap.put(distributionMessageBean.getBranchsalerId(),
							BigDecimal.valueOf(distributionPercent + 0.1));
					distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent - 0.1));
				} else if (level == 3) {// 没有省级和市级分会，总会----区级分会
					distributionMap.put(distributionMessageBean.getBranchsalerId(),
							BigDecimal.valueOf(distributionPercent));
					distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent));
				} else {
					ThrowDistributionException(distributionMessageBean);
					// throw new
					// BussinessException(BizExceptionEnum.ILLEGAL_INFO);
				}
			} else if (listParentsID.size() == 3) {
				if (level == 2) { // 总会--省--市 三级
					distributionMap.put(distributionMessageBean.getBranchsalerId(),
							BigDecimal.valueOf(distributionPercent + 0.1));
					distributionMap.put(listParentsID.get(2), BigDecimal.valueOf(0.1));
					distributionMap.put(listParentsID.get(1),
							BigDecimal.valueOf(1.0 - distributionPercent - 0.1 - 0.1));
				} else if (level == 3) {
					Branchsaler branchsalerTemp = new Branchsaler();
					branchsalerTemp = branchsalerTemp.selectById(listParentsID.get(2));
					int levelTemp = branchsalerTemp.getLevel();
					if (levelTemp == 1) {// 总会----省---区 三级
						distributionMap.put(distributionMessageBean.getBranchsalerId(),
								BigDecimal.valueOf(distributionPercent));
						distributionMap.put(listParentsID.get(2), BigDecimal.valueOf(0.2));
						distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent - 0.2));
					} else if (levelTemp == 2) {// 总会----市-----区 三级
						distributionMap.put(distributionMessageBean.getBranchsalerId(),
								BigDecimal.valueOf(distributionPercent));
						distributionMap.put(listParentsID.get(2), BigDecimal.valueOf(0.1));
						distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent - 0.1));
					} else {
						ThrowDistributionException(distributionMessageBean);
						// throw new
						// BussinessException(BizExceptionEnum.ILLEGAL_INFO);
					}

				} else {
					ThrowDistributionException(distributionMessageBean);
					// throw new
					// BussinessException(BizExceptionEnum.ILLEGAL_INFO);
				}
			} else if (listParentsID.size() == 4) {// 总会---省---市---区 四级
				distributionMap.put(distributionMessageBean.getBranchsalerId(),
						BigDecimal.valueOf(distributionPercent));
				distributionMap.put(listParentsID.get(3), BigDecimal.valueOf(0.1));
				distributionMap.put(listParentsID.get(2), BigDecimal.valueOf(0.1));
				distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent - 0.2));
			} else {
				ThrowDistributionException(distributionMessageBean);
				// throw new BussinessException(BizExceptionEnum.ILLEGAL_INFO);
			}
			for (Map.Entry<Integer, BigDecimal> entry : distributionMap.entrySet()) {
				distributionMessageBean
						.setAmount(entry.getValue().multiply(amount).setScale(2, BigDecimal.ROUND_HALF_UP));
				distributionMessageBean.setBranchsalerId(entry.getKey());
				insertAccountsChange(distributionMessageBean);
			}
		}
	}

	public void branchsalerDistributionWithOtherChannel(DistributionMessageBean distributionMessageBean) {
		int branchsalerId = distributionMessageBean.getBranchsalerId();

		List<Integer> listParentsID = ConstantFactory.me().getParentBranchsalerIds(branchsalerId);

		Map<Integer, BigDecimal> distributionMap = new HashMap<>();

		BigDecimal amount = distributionMessageBean.getAmount();

		double distributionPercent;

		if (distributionMessageBean.getSalerType().equals("0")) {// 推广大使
			distributionPercent = 0.25;// 小渠道
		} else {// 销售大使
			distributionPercent = 0.3;// 小渠道
		}

		if (listParentsID.size() == 2) {// 总会--小渠道
			distributionMap.put(distributionMessageBean.getBranchsalerId(), BigDecimal.valueOf(distributionPercent));
			distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent));
		} else if (listParentsID.size() == 3) {
			int branchsalerTempId = listParentsID.get(2);
			Branchsaler branchsalerTemp = new Branchsaler();
			branchsalerTemp = branchsalerTemp.selectById(branchsalerTempId);
			int levelTemp = branchsalerTemp.getLevel();

			if (levelTemp == 1) {// 总会---省---小渠道
				distributionMap.put(distributionMessageBean.getBranchsalerId(),
						BigDecimal.valueOf(distributionPercent));
				distributionMap.put(listParentsID.get(2), BigDecimal.valueOf(0.25));
				distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent - 0.25));
			} else if (levelTemp == 2) {// 总会---市---小渠道
				distributionMap.put(distributionMessageBean.getBranchsalerId(),
						BigDecimal.valueOf(distributionPercent));
				distributionMap.put(listParentsID.get(2), BigDecimal.valueOf(0.15));
				distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent - 0.15));
			} else if (levelTemp == 3) {// 总会---区---小渠道
				distributionMap.put(distributionMessageBean.getBranchsalerId(),
						BigDecimal.valueOf(distributionPercent));
				distributionMap.put(listParentsID.get(2), BigDecimal.valueOf(0.05));
				distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent - 0.05));
			} else {// 异常
				ThrowDistributionException(distributionMessageBean);
				// throw new BussinessException(BizExceptionEnum.ILLEGAL_INFO);
			}
		} else if (listParentsID.size() == 4) {
			int branchsalerTempId = listParentsID.get(3);
			Branchsaler branchsalerTemp = new Branchsaler();
			branchsalerTemp = branchsalerTemp.selectById(branchsalerTempId);
			int levelTemp = branchsalerTemp.getLevel();
			if (levelTemp == 2) {// 总会---省----市---小渠道
				distributionMap.put(distributionMessageBean.getBranchsalerId(),
						BigDecimal.valueOf(distributionPercent));
				distributionMap.put(listParentsID.get(3), BigDecimal.valueOf(0.15));
				distributionMap.put(listParentsID.get(2), BigDecimal.valueOf(0.1));
				distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent - 0.25));
			} else if (levelTemp == 3) {
				int btd = listParentsID.get(2);
				Branchsaler bt = new Branchsaler();
				bt = bt.selectById(btd);
				int lt = bt.getLevel();
				if (lt == 1) {// 总会---省---区---小渠道
					distributionMap.put(distributionMessageBean.getBranchsalerId(),
							BigDecimal.valueOf(distributionPercent));
					distributionMap.put(listParentsID.get(3), BigDecimal.valueOf(0.05));
					distributionMap.put(listParentsID.get(2), BigDecimal.valueOf(0.2));
					distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent - 0.25));
				} else if (lt == 2) {// 总会---市---区---小渠道
					distributionMap.put(distributionMessageBean.getBranchsalerId(),
							BigDecimal.valueOf(distributionPercent));
					distributionMap.put(listParentsID.get(3), BigDecimal.valueOf(0.05));
					distributionMap.put(listParentsID.get(2), BigDecimal.valueOf(0.1));
					distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent - 0.15));
				} else {// 异常
					ThrowDistributionException(distributionMessageBean);
					// throw new
					// BussinessException(BizExceptionEnum.ILLEGAL_INFO);
				}

			} else {
				ThrowDistributionException(distributionMessageBean);
				// throw new BussinessException(BizExceptionEnum.ILLEGAL_INFO);
			}

		} else if ((listParentsID.size() == 5)) {
			distributionMap.put(distributionMessageBean.getBranchsalerId(), BigDecimal.valueOf(distributionPercent));
			distributionMap.put(listParentsID.get(4), BigDecimal.valueOf(0.05));
			distributionMap.put(listParentsID.get(3), BigDecimal.valueOf(0.1));
			distributionMap.put(listParentsID.get(2), BigDecimal.valueOf(0.1));
			distributionMap.put(listParentsID.get(1), BigDecimal.valueOf(1.0 - distributionPercent - 0.25));
		} else {
			ThrowDistributionException(distributionMessageBean);
			// throw new BussinessException(BizExceptionEnum.ILLEGAL_INFO);
		}
		for (Map.Entry<Integer, BigDecimal> entry : distributionMap.entrySet()) {
			distributionMessageBean.setAmount(entry.getValue().multiply(amount).setScale(2, BigDecimal.ROUND_HALF_UP));
			distributionMessageBean.setBranchsalerId(entry.getKey());
			insertAccountsChange(distributionMessageBean);
		}
	}

	private void ThrowDistributionException(DistributionMessageBean distributionMessageBean) {
		// 记录结算失败的 orderno
		throw new BussinessException(BizExceptionEnum.ILLEGAL_INFO);
	}

	@Override
	public List<Map<String, Object>> branchsalerAccountSum(String condition, String beginTime, String endTime,String paysources) {

		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(condition)) {
			params.put("condition", condition);
		}
		if (StringUtils.isNotBlank(beginTime)) {
			params.put("beginTime", beginTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			params.put("endTime", endTime+" 23:59:59");
		}
        if (StringUtils.isNotBlank(paysources)) {
            params.put("paysources", paysources);
        }
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotBlank(beginTime) && (StringUtils.isNotBlank(endTime))) {
			list = branchsalerAccountChangeDao.selectBranchsalerAccountSum(params);
			for (Map<String, Object> map: list) {
				int id = (Integer) map.get("branchsalerId");
				params.put("branchsaler",id);
				params.put("accountType",0);
				BigDecimal xiaoshou=branchsalerAccountChangeDao.selectAccountByAccountType(params).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				params.put("accountType",1);
				BigDecimal guanli=branchsalerAccountChangeDao.selectAccountByAccountType(params).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				map.put("xiaoshou",xiaoshou);
				map.put("guanli",guanli);
				params.put("accountType",null);
			}
		} else {
			list = branchsalerAccountChangeDao.branchsalerList(params);
			for (Map<String, Object> map : list) {
				int id = (Integer) map.get("branchsalerId");
				Map<String, Object> selectMap = new HashMap<>();
				selectMap.put("branchsaler", id);
				selectMap.put("type", 0);
				if (StringUtils.isNotBlank(paysources)) {
					selectMap.put("paysources", paysources);
				}
				BigDecimal changeAmount = branchsalerAccountChangeDao.selectSumNohasOrder(selectMap).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				map.put("changeAmount", changeAmount);
				params.put("branchsaler",id);
				params.put("accountType",0);
				BigDecimal xiaoshou=branchsalerAccountChangeDao.selectAccountByAccountType(params).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				params.put("accountType",1);
				BigDecimal guanli=branchsalerAccountChangeDao.selectAccountByAccountType(params).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				map.put("xiaoshou",xiaoshou);
				map.put("guanli",guanli);
				params.put("accountType",null);
			}
		}
		return list;
	}

	@Override
	@Transactional
	public synchronized int settlement_do(String condition, String beginTime, String endTime,String paysources) {
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(condition)) {
			params.put("condition", condition);
		}
		if (StringUtils.isNotBlank(beginTime)) {
			params.put("beginTime", beginTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			params.put("endTime", endTime+" 23:59:59");
		}
		if (StringUtils.isNotBlank(paysources)) {
			params.put("paysources", paysources);
		}
		List<Map<String, Object>> list = branchsalerAccountChangeDao.selectBranchsalerAccountSum(params);
		if (list.size() == 0) {
			return 0;
		}
		for (Map<String, Object> map : list) {
			// 分会id
			Integer branchsalerId = 0;
			if (map.get("branchsalerId") == null) {
				System.out.println("map.get(branchsalerId) == null");
				throw new BussinessException(BizExceptionEnum.SERVER_ERROR);

			}
			branchsalerId = (Integer) map.get("branchsalerId");
			// 结算金额
			BigDecimal b = new BigDecimal("0");
			if ( map.get("changeAmount") == null) {
				System.out.println(" map.get(changeAmount) == null");
				throw new BussinessException(BizExceptionEnum.SERVER_ERROR);

			}
			b=(BigDecimal) map.get("changeAmount");
			if ( b.intValue() > 0 && branchsalerId != 1) {
				params.put("branchsaler", branchsalerId);
				params.put("type", 0);

				// 设置时间段内的收入 设置为结算
				int upRes = branchsalerAccountChangeDao.batchUpdateBranchsalerAccountType(params);
				if (upRes == 0) {
					System.out.println("upRes == 0");
					throw new BussinessException(BizExceptionEnum.SERVER_ERROR);
				}
				// 总账户扣除 结算金额
				// 查询分会总收入
				EntityWrapper<BranchsalerAmount> wrapper = new EntityWrapper<BranchsalerAmount>();
				wrapper.eq("branchsaler_id", branchsalerId);
				BranchsalerAmount branchsalerAmount = new BranchsalerAmount();
				branchsalerAmount = branchsalerAmount.selectOne(wrapper);
				if (branchsalerAmount == null) {
					System.out.println("branchsalerAmount == null");
					throw new BussinessException(BizExceptionEnum.SERVER_ERROR);
				}
				BigDecimal all = branchsalerAmount.getTotalAmount();

				BigDecimal yu = new BigDecimal(all.doubleValue() - b.doubleValue()).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				if (yu == null) {
					System.out.println("yu == null ");
					throw new BussinessException(BizExceptionEnum.SERVER_ERROR);
				}
				if (yu != null && yu.intValue() < 0) {
					System.out.println("yu != null && yu <0");
					throw new BussinessException(BizExceptionEnum.SERVER_ERROR);
				}
				branchsalerAmount.setTotalAmount(yu);
				branchsalerAmount.setAvaibleAmount(yu);
				branchsalerAmount.setUpdateTime(new Date());
				boolean flag = branchsalerAmount.updateById();
				if (!flag) {
					System.out.println("账户更新失败");
					throw new BussinessException(BizExceptionEnum.SERVER_ERROR);
				}

				// 添加结算记录
				BranchsalerSettlementRecord branchsalerSettlementRecord = new BranchsalerSettlementRecord();
				branchsalerSettlementRecord.setBranchsalerId(branchsalerId);
				branchsalerSettlementRecord.setFee(b);
				branchsalerSettlementRecord
						.setBeginTime(DateTimeUtil.parseDate(beginTime, DateTimeUtil.DEFAULT_FORMAT_DATE));
				branchsalerSettlementRecord
						.setEndTime(DateTimeUtil.parseDate(endTime+" 23:59:59",DateTimeUtil.DEFAULT_FORMAT_DATE_TIME));
				branchsalerSettlementRecord.setCreateTime(new Date());
				branchsalerSettlementRecord.setPaysources(paysources);
				int seInsertRes = branchsalerSettlementRecordDao.insertSelective(branchsalerSettlementRecord);
				if (seInsertRes == 0) {
					System.out.println("结算记录添加失败");
					throw new BussinessException(BizExceptionEnum.SERVER_ERROR);
				}
			}

		}
		return 1;
	}

	@Override
	public List<Map<String, Object>> settlementRecord(String condition, Integer bsr_status,Integer pay_status,String beginTime, String endTime) {
		Map<String, Object> params = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			params.put("branchsaler", branchsaler.getId());
		}
		if (StringUtils.isNotBlank(condition)  && !condition.equals("null")) {
			params.put("condition", condition);
		}
		if (StringUtils.isNotBlank(beginTime) && !beginTime.equals("null")) {
			params.put("beginTime", beginTime);
		}
		if (StringUtils.isNotBlank(endTime) && !endTime.equals("null")) {
			params.put("endTime", endTime+" 23:59:59");
		}
		if (bsr_status != null && bsr_status!=-1) {
			params.put("bsr_status", bsr_status);
		}
		if (pay_status != null && pay_status!=-1) {
			params.put("pay_status", pay_status);
		}
		return branchsalerSettlementRecordDao.selectSettlementRecordList(params);
	}

	@Override
	public void exportsettlementRecord(HttpServletResponse response, ServletOutputStream outputStream, String condition,Integer bsr_status,Integer pay_status,
			String beginTime, String endTime) {

		List<Map<String, Object>> list =settlementRecord(condition,bsr_status,pay_status,beginTime,endTime);
		List<SettlementRecordVo> listvo = new ArrayList<>();
		for (Map<String, Object> map2 : list) {
			SettlementRecordVo sRecordVo = new SettlementRecordVo();
			sRecordVo = (SettlementRecordVo) Convert.mapToObject(map2, SettlementRecordVo.class);
			listvo.add(sRecordVo);
		}
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		ExcelUtil<SettlementRecordVo> myExcel = new ExcelUtil<>();
		String[] headerColumns = { "分会名称", "分会等级", "结算金额", "结算时间段", "结算时间","结算订单类型","打款状态","开票状态" ,"发票号码"};
		String[] fieldColumns = { "fullName", "levelName", "fee", "settlementTime", "createTime","paysources","payStatus","status" ,"invoiceNumber"};
		try {
			HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "结算记录", listvo, headerColumns,
					fieldColumns, true);
			myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.doResponse(response, outputStream, "结算记录", hssfWorkbook);
	}
	@Override
	public List<Map<String, Object>> branchsalerAccountDetails(String condition, String beginTime, String endTime) {
		Map<String, Object> params = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			params.put("branchsaler", branchsaler.getId());
		}
		if (StringUtils.isNotBlank(condition)  && !condition.equals("null")) {
			params.put("condition", condition);
		}
		if (StringUtils.isNotBlank(beginTime) && !beginTime.equals("null")) {
			params.put("beginTime", beginTime);
		}
		if (StringUtils.isNotBlank(endTime) && !endTime.equals("null")) {
			params.put("endTime", endTime+" 23:59:59");
		}
		return branchsalerAccountChangeDao.selectBranchsalerAccountDetails(params);
	}

	@Override
	public void exportBranchsalerAccountDetails(HttpServletResponse response, ServletOutputStream outputStream, String condition, String beginTime, String endTime) {
		List<Map<String, Object>> list =branchsalerAccountDetails(condition,beginTime,endTime);
		List<BranchSalerAccountDetailsVo> listvo = new ArrayList<>();
		for (Map<String, Object> map2 : list) {
			BranchSalerAccountDetailsVo vo = new BranchSalerAccountDetailsVo();
			vo = (BranchSalerAccountDetailsVo) Convert.mapToObject(map2, BranchSalerAccountDetailsVo.class);
			listvo.add(vo);
		}
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		ExcelUtil<BranchSalerAccountDetailsVo> myExcel = new ExcelUtil<>();
		String[] headerColumns = { "订单分会", "分会级别", "订单金额", "订单编号","支付渠道","支付方式","分润时间" ,"小渠道","区级","市级","省级","总会"};
		String[] fieldColumns = { "orderBranchSaler", "orderLevelName", "orderFee","orderNo", "paySource", "payRole","orderCreateTime","xqChangeAmount","quChangeAmount" ,"shiChangeAmount","shengChangeAmount","zongChangeAmount"};
		try {
			HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "分润详情", listvo, headerColumns,
					fieldColumns, true);
			myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.doResponse(response, outputStream, "分润详情", hssfWorkbook);
	}

}
