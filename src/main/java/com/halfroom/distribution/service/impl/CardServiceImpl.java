package com.halfroom.distribution.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.halfroom.distribution.common.page.PageInfoBT;
import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.dao.CardDao;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.model.Branchsaler;
import com.halfroom.distribution.persistence.model.CardRaw;
import com.halfroom.distribution.service.ICardService;

@Service
@Transactional
public class CardServiceImpl implements ICardService {
	@Resource
	private CardDao cardDao;
	@Resource
	private BranchsalerMapper branchsalerMapper;

	@Override
	public Integer countCardOfBranch(Integer cardNoStart, Integer cardNoEnd, Integer branchSalerId, Integer status) {
		Map<String, Object> map = new HashMap<>();
		map.put("cardNoStart", cardNoStart);
		map.put("cardNoEnd", cardNoEnd);
		map.put("branchsaler", branchSalerId);
		map.put("status", status);
		return cardDao.countCardOfBranch(map);
	}

	@Override
	public Integer countBranchSalerOfCard(Integer cardNoStart, Integer cardNoEnd) {
		Map<String, Object> map = new HashMap<>();
		map.put("cardNoStart", cardNoStart);
		map.put("cardNoEnd", cardNoEnd);
		return cardDao.countBranchSalerOfCard(map).size();
	}

	@Override
	public Integer createCard(List<CardRaw> cardRaws) {
		return cardDao.insertListSelective(cardRaws);
	}

	@Override
	public Integer batchSetBranchOfCard(Integer cardNoStart, Integer cardNoEnd, Integer branchSalerId) {
		List<Integer> cardNos = new ArrayList<Integer>();
		for (int i = cardNoStart; i <= cardNoEnd; i++) {
			cardNos.add(i);
		}
		return cardDao.batchUpdateCardBranch(cardNos, branchSalerId);
	}

	@Override
	public Integer batchSetStatusOfCard(Integer cardNoStart, Integer cardNoEnd, Integer status) {
		List<Integer> cardNos = new ArrayList<Integer>();
		for (int i = cardNoStart; i <= cardNoEnd; i++) {
			cardNos.add(i);
		}
		return cardDao.batchUpdateCardStatus(cardNos, status);
	}

	@Override
	public PageInfoBT cardList(Integer offset, Integer limit, String sort, String order, String cardNoStart,
			String cardNoEnd, Integer status) {
		Map<String, Object> map = new HashMap<>();
		map.put("cardNoStart", cardNoStart);
		map.put("cardNoEnd", cardNoEnd);
		if (status != null) {
			map.put("status", status);
		}
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			map.put("branchsaler", branchsaler.getId());
		}
		if (StringUtils.isNotBlank(sort)) {
			map.put("sort", sort);
		}
		if (StringUtils.isNotBlank(order)) {
			map.put("order", order);
		}
		Page page = new Page<>(offset/limit+1,limit);
		List<Map<String, Object>> list = cardDao.cardList(map,page);
		page.setRecords(list);
		PageInfoBT resPage = new PageInfoBT(page);
		return resPage;
	}

	@Override
	public List<Map<String, Object>> cardUseRecordList(String cardNoStart, String cardNoEnd,String phone) {
		Map<String, Object> map = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			map.put("branchsaler", branchsaler.getId());
		}
		if (StringUtils.isNotBlank(cardNoStart)) {
			map.put("cardNoStart", cardNoStart);
		}
		if (StringUtils.isNotBlank(cardNoEnd)) {
			map.put("cardNoEnd", cardNoEnd);
		}
		if (StringUtils.isNotBlank(phone)) {
			map.put("phone", phone);
		}
		return cardDao.selectCardUseRecordList(map);
	}

}
