package com.halfroom.distribution.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.halfroom.distribution.dao.GeneralUserDao;
import com.halfroom.distribution.persistence.model.Book;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.halfroom.distribution.core.util.QiniuUtil;
import com.halfroom.distribution.dao.BookDao;
import com.halfroom.distribution.service.IBookService;
@Service
@SuppressWarnings("all")
public class BookServiceImpl implements IBookService {
	@Resource
	private BookDao bookDao;
	@Resource
	private GeneralUserDao generalUserDao;
	@Override
	public List<Map<String, Object>> partList(String condition) {
		Map<String, Object> params = new HashMap<>();
		if(StringUtils.isNotBlank(condition)){
			params.put("condition", condition);
		}
		List<Map<String, Object>> list = bookDao.selectPartVideos(params);
		//查询以上架课程
		List<Book> books=generalUserDao.selectOnSelveBooks();
		for(int i=0;i<books.size();i++){
			Map<String, Object> bookMap = new HashMap<>();
			bookMap.put("id",books.get(i).getId());
			bookMap.put("bName",books.get(i).getName());
			list.add(bookMap);
		}
		return list;
	}

	@Override
	public String getResById(Integer id) {
			return bookDao.selectResById(id);
	}
}
