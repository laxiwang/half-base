package com.halfroom.distribution.controller.card;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.halfroom.distribution.service.IZipService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.service.ICardService;

/**
 * 实体卡
 */
@Controller
@RequestMapping("/card")
public class CardController extends BaseController {
	private String prefix = "/system/card/";
    @Resource
    private ICardService iCardService;
    @Resource
	private IZipService iZipService;
	/**
	 * 跳转到实体卡记录首页
	 */
	@RequestMapping("")
	public String index() {
		return prefix + "card.html";
	}

	@RequestMapping("/list")
	@ResponseBody
	public Object list(
			@RequestParam(name="offset",required=false)Integer offset,
    		@RequestParam(name="limit",required=false)Integer limit,
    		@RequestParam(name = "sort", required = false, defaultValue = "") String sort,
			@RequestParam(name = "order", required = false, defaultValue = "") String order,
			@RequestParam(name = "cardNoStart", required = false, defaultValue = "") String cardNoStart,
			@RequestParam(name = "cardNoEnd", required = false, defaultValue = "") String cardNoEnd,
			@RequestParam(name = "status", required = false, defaultValue = "") Integer status
			
			) {
		return iCardService.cardList(offset,limit,sort,order,cardNoStart, cardNoEnd, status);
	}
	
	/**
	 * 跳转到实体卡使用记录
	 */
	@RequestMapping("/cardUseRecordList_index")
	public String cardUseRecordList_index() {
		return prefix + "carduserecords.html";
	}
	
	/**
	 * 跳转到实体卡使用记录
	 */
	@RequestMapping("/cardUseRecordList")
	@ResponseBody
	public Object cardUseRecordList(
			@RequestParam(name = "cardNoStart", required = false, defaultValue = "") String cardNoStart,
			@RequestParam(name = "cardNoEnd", required = false, defaultValue = "") String cardNoEnd,
			@RequestParam(name = "phone", required = false, defaultValue = "") String phone
			)
			  {
		return iCardService.cardUseRecordList(cardNoStart, cardNoEnd,phone);
	}
	@RequestMapping("/exportPicture/{card_no}")
	public void  exportPicture(HttpServletResponse response,
			@PathVariable Integer card_no
	){
		iZipService.exportCardPicture(response, card_no);
	}

}
