package com.halfroom.distribution.controller.card;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.halfroom.distribution.dao.CardWholeSaleDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.persistence.model.CardWholeSale;
import com.halfroom.distribution.service.ICardWholeSaleService;
import com.halfroom.distribution.service.IZipService;
/**
 * 实体卡划拨记录 --接受划拨记录
 * 
 * @author tingyunjava
 *
 */
@Controller
@RequestMapping("/card/cardWholeSale")
public class CardWholeSaleController extends BaseController {
	private String prefix = "/system/card/cardwholesale/";
	@Resource
	private ICardWholeSaleService iCardWholeSaleService;
	@Resource
	private IZipService iZipService;
	@Resource
	private CardWholeSaleDao cardWholeSaleDao;

	/**
	 * 跳转到划拨出去记录首页
	 */
	@RequestMapping("")
	public String index() {
		return prefix + "out_cardwholesale.html";
	}

	/**
	 * 跳转到划拨进来记录首页
	 */
	@RequestMapping("/in_Index")
	public String in_Index() {
		return prefix + "in_cardwholesale.html";
	}

	/**
	 * 划拨出去的记录
	 */
	@RequestMapping("/wholeOutList")
	@ResponseBody
	public Object list(@RequestParam(name = "beginTime", required = false, defaultValue = "") String beginTime,
			@RequestParam(name = "endTime", required = false, defaultValue = "") String endTime,
			@RequestParam(name = "status", required = false, defaultValue = "") Integer status) {
		return iCardWholeSaleService.cardWholeSaleList(beginTime, endTime, status, true);
	}
	/**
	 * 划拨记录导出
	 */
	@RequestMapping("/exportWholeList/{beginTime}/{endTime}/{status}/{flag}")
	public void exportWholeList(
			HttpServletResponse response,ServletOutputStream outputStream,
			@PathVariable String beginTime,
			@PathVariable String endTime,
			@PathVariable Integer status,
			@PathVariable Boolean flag
			) {
		 iCardWholeSaleService.exportWholeList(response,outputStream,beginTime, endTime, status, flag);
	}
	/**
	 * 划拨进来的记录
	 */
	@RequestMapping("/wholeInList")
	@ResponseBody
	public Object wholeInList(@RequestParam(name = "beginTime", required = false, defaultValue = "") String beginTime,
			@RequestParam(name = "endTime", required = false, defaultValue = "") String endTime,
			@RequestParam(name = "status", required = false, defaultValue = "") Integer status) {
		return iCardWholeSaleService.cardWholeSaleList(beginTime, endTime, status, false);
	}

	/***
	 * 跳转至增加划拨页面
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return prefix + "cardwholesale_add.html";
	}

	/***
	 * 跳转至总部调拨页面
	 * @return
	 */
	@RequestMapping("/addPrivate")
	public String addPrivate() {
		return prefix + "cardwholesale_private_add.html";
	}


	/**
	 * 划拨操作
	 */
	@RequestMapping("/wholeSale")
	@ResponseBody
	public Object wholeSale(@Valid CardWholeSale cardWholeSale) {
		return iCardWholeSaleService.wholeSale(cardWholeSale);
	}


	/**
	 * 调拨操作
	 */
	@RequestMapping("/private_wholeSale")
	@ResponseBody
	public Object private_wholeSale(@Valid CardWholeSale cardWholeSale) {
		return iCardWholeSaleService.private_wholeSale(cardWholeSale);
	}

	/**
	 * 取消划拨
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/cancelWholeSale/{id}")
	@ResponseBody
	public Object cancelWholeSale(@PathVariable Integer id) {
		return iCardWholeSaleService.cancelWholeSale(id);
	}

	/**
	 * 确认划拨
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/confirmWholeSale/{id}")
	@ResponseBody
	public Object confirmWholeSale(@PathVariable Integer id) {
		return iCardWholeSaleService.confirmWholeSale(id);
	}
	/**
	 * 验证接收记录是否为半月卡
	 *  @param id
	 */
	@RequestMapping("/checkHalfCard/{id}")
	@ResponseBody
	public Object checkHalfCard(@PathVariable Integer id) {
		Integer res=0;
		if(iCardWholeSaleService.checkHalfCard(id)){
			res=1;//不是半月卡
		}
		CardWholeSale record = cardWholeSaleDao.selectByPrimaryKey(id);
		if(record.getStatus()!=1){
			res=2;//不是接收状态
		}
		return res;
	}
	/**
	 * 导出半月卡
	 *  @param id
	 */
	@RequestMapping("/exportHalfPicture/{id}")
	public void exportHalfPicture(@PathVariable Integer id,HttpServletResponse response) {
		 iZipService.exportHalfPicture(response,id);
	}

	/**
	 * 导出半月卡表格
	 *  @param id
	 */
	@RequestMapping("/exportHalfExecl/{id}")
	public void exportHalfExecl(@PathVariable Integer id,HttpServletResponse response, ServletOutputStream outputStream) {
		iCardWholeSaleService.exportHalfExecl(id,response,outputStream);
	}
}
