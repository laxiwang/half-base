package com.halfroom.distribution.controller.card;


import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.persistence.model.CardActiveRecord;
import com.halfroom.distribution.service.ICardActiveRecordService;

/**
 * 实体卡激活记录
 */
@Controller
@RequestMapping("/card/cardActiveRecord")
public class CardActiveRecordController extends BaseController {
	@Resource
	private ICardActiveRecordService iCardActiveRecordService;
	private String prefix = "/system/card/cardactiverecord/";

	/**
	 * 跳转到激活记录首页
	 */
	@RequestMapping("")
	public String index() {
		return prefix + "cardactiverecord.html";
	}

	@RequestMapping("/list")
	@ResponseBody
	public Object list(@RequestParam(name = "beginTime", required = false, defaultValue = "") String beginTime,
			@RequestParam(name = "endTime", required = false, defaultValue = "") String endTime) {
		return iCardActiveRecordService.cardActiveRecordList(beginTime, endTime);
	}
	@RequestMapping("/exportList/{beginTime}/{endTime}")
	public void exportList(
						    HttpServletResponse response,ServletOutputStream outputStream,
						   @PathVariable String beginTime,
			               @PathVariable String endTime) {
		 iCardActiveRecordService.exportCardActiveRecordList(response,outputStream,beginTime, endTime);
	}
	/***
	 * 跳转至增加激活页面
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return prefix + "cardactiverecord_add.html";
	}
	/**
	 * 激活操作
	 * @param cardActiveRecord
	 * @return
	 */
	@RequestMapping("/_add")
	@ResponseBody
	public Object _add(@Valid CardActiveRecord cardActiveRecord) {
		return iCardActiveRecordService.insertardActiveRecord(cardActiveRecord);
	}
}
