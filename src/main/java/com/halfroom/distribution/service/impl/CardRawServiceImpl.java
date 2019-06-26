package com.halfroom.distribution.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.halfroom.distribution.dao.CardRawDao;
import com.halfroom.distribution.persistence.model.CardRaw;
import com.halfroom.distribution.service.ICardRawService;

@Service
@Transactional
public class CardRawServiceImpl implements ICardRawService {
	@Resource
	private CardRawDao cardRawDao;
	@Override
	public Integer batchCreateCardRaw(List<CardRaw> cardRaws) {
		return cardRawDao.insertListSelective(cardRaws);
	}

	@Override
	public List<CardRaw> listCardRaw(Integer cardNoStart,Integer cardNoEnd) {
		Map<String, Object> map = new HashMap<>();
		map.put("cardNoStart", cardNoStart);
		map.put("cardNoEnd", cardNoEnd);
		return cardRawDao.cardRawListRange(map);
	}

	@Override
	public Integer deleteCardRaw(Integer cardNoStart,Integer cardNoEnd) {
		Map<String, Object> map = new HashMap<>();
		map.put("cardNoStart", cardNoStart);
		map.put("cardNoEnd", cardNoEnd);
		return cardRawDao.deleteCardRawRange(map);
	}

	@Override
	public Integer countCardRaw(Integer cardNoStart,Integer cardNoEnd) {
		Map<String, Object> map = new HashMap<>();
		map.put("cardNoStart", cardNoStart);
		map.put("cardNoEnd", cardNoEnd);
		return cardRawDao.countCardRaw(map);
	}

}
