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
import com.halfroom.distribution.persistence.model.CardProductRecord;
import com.halfroom.distribution.service.ICardProductRecordService;
import com.halfroom.distribution.service.IZipService;

/**
 * 实体卡生产--投放 控制器
 */
@Controller
@RequestMapping("/card/cardProductRecord")
@SuppressWarnings("all")
public class CardProductRecordController extends BaseController {
	 @Resource
	 private ICardProductRecordService iCardProductRecordService;
	 @Resource
	 private IZipService iZipService;
	 private String prefix = "/system/card/cardproductrecord/";
	    /**
	     * 跳转到生产记录首页
	     */
	    @RequestMapping("")
	    public String index() {
	        return prefix + "cardproductrecord.html";
	    }
	    
	    
	    /**
	     * 到生产记录
	     */
	    @RequestMapping("/list")
	    @ResponseBody
	    public Object list(	@RequestParam(name="beginTime",required=false,defaultValue="")String beginTime,
	    		            @RequestParam(name="endTime",required=false,defaultValue="")String endTime) {
	    	return iCardProductRecordService.cardProductRecordList(beginTime,endTime);
	    }
	    /***
	     * 跳转至增加生产页面
	     * @return
	     */
	    @RequestMapping("add")
	    public String add() {
	        return prefix + "cardproductrecord_add.html";
	    }
	    
	    /**
	     * 创建生产记录以及半成品卡
	     * @param CardProductRecord
	     * @return
	     */
	    @RequestMapping("_add")
	    @ResponseBody
	    public Object add(@Valid CardProductRecord CardProductRecord) {
	    	return iCardProductRecordService.addcardProductRecordAndCardRaw(CardProductRecord);
	    }
	    
	    
		/**
		 * 将指定的生产记录投放使用
		 * @param id
		 * @return
		 */
	    @RequestMapping("/puton/{id}")
		@ResponseBody
		public Object putOn(@PathVariable Integer id){
	    	return iCardProductRecordService.puton(id);
	    }
	    
	  
	    
	    /**
	     * 通过生产记录
		 * 导出实体卡半成品
		 * @param id
		 */
	    @RequestMapping("/export_excel/{id}")
	    @ResponseBody
		public void export_excel(@PathVariable Integer id,HttpServletResponse response, ServletOutputStream outputStream){
	    	iCardProductRecordService.exportExcel(response, outputStream, id);
	    }
	    /**
	     * 删除生产记录，同时删除cardRaw 
	     * @param id
	     * @return
	     */
	    @RequestMapping("delete/{id}")
	    @ResponseBody
	    public Object del(@PathVariable Integer id) {
	    	 return  iCardProductRecordService.delcardProductRecordAndCardRaw(id);
	    }
	    
	    /**
	     * 通过生产记录
		 * 导出实体卡二维码
		 * @param id
		 */
	    @RequestMapping("/exportPicture/{id}")
		public void exportPicture(@PathVariable Integer id,HttpServletResponse response){
	    	iZipService.exportPicture(response, id);
	    }
}
