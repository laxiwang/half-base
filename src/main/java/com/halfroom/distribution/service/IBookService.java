package com.halfroom.distribution.service;

import java.util.List;
import java.util.Map;

/**
 * 课程相关服务
 * @author tingyunjava
 *
 */
public interface IBookService {
	//节列表
	List<Map<String, Object>> partList(String condition);
	String  getResById(Integer id);
}
