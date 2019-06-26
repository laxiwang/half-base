package com.halfroom.distribution.controller.user;


import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.halfroom.distribution.common.annotion.Permission;
import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.service.IUserOrderService;

/**
 * 订单控制器
 */
@Controller
@RequestMapping("/userOrder")
@SuppressWarnings("all")
public class UserOrderController extends BaseController{
	
	 private static String PREFIX = "/system/userorder";
	 
  
      @Resource
	  private IUserOrderService iUserOrderService;
	   /**
	     * 跳转到订单列表页面
	     */
	    @RequestMapping("")
	    public String index() {
	        return PREFIX + "/userorder.html";
	    }
	    /**
	     * 查询订单列表
	     */
	    @RequestMapping("/list")
	    @Permission
	    @ResponseBody
	    public Object list(String condition,
	    		@RequestParam(name="beginTime",required=false,defaultValue="")String beginTime,
	    		@RequestParam(name="endTime",required=false,defaultValue="")String endTime,
	    		@RequestParam(name="paysource",required=false) Integer paysource,
	    		@RequestParam(name="payrole",required=false) Integer payrole) {
	        return  iUserOrderService.orderList(condition,null,paysource,payrole,beginTime,endTime);
	    }
	    
	    
	    @RequestMapping("/exportList/{condition}/{beginTime}/{endTime}/{paysource}/{payrole}")
	    public void exportList(
	    		HttpServletResponse response,ServletOutputStream outputStream,
				@PathVariable String condition,
				@PathVariable String beginTime,
				@PathVariable String endTime,
				@PathVariable Integer paysource,
				@PathVariable Integer payrole
				) {
	    	iUserOrderService.exportOrderList(response,outputStream,condition,null,paysource,payrole,beginTime,endTime);
	    }
	    /**
	     * 跳转到订单统计页面
	     */
	    @RequestMapping("/orderStatisticsIndex")
	    public String orderStatisticsIndex() {
	        return PREFIX + "/orderstatistics.html";
	    }
	    /**
	     * 订单统计
	     */
	    @RequestMapping("/orderStatistics")
	    @ResponseBody
	    public Object orderStatistics() {
	        return  iUserOrderService.orderStatistics(null, null);
	    }


		/**
		 * 充值记录页面
		 * @return
		 */
		@RequestMapping("/userRechargeRecordIndex")
			public String userRechargeRecordIndex() {
			return PREFIX + "/userrechargerecord.html";
		}

		/**
		 * 充值记录
		 * @param condition
		 * @param beginTime
		 * @param endTime
		 * @return
		 */
		@RequestMapping("/userRechargeRecordList")
		@ResponseBody
		public  Object  userRechargeRecordList(
				@RequestParam(name="condition",required=false,defaultValue="")String condition,
				@RequestParam(name="beginTime",required=false,defaultValue="")String beginTime,
				@RequestParam(name="endTime",required=false,defaultValue="")String endTime){
	    	return iUserOrderService.userRechargeRecordList(condition,beginTime,endTime);
		}

		/**
		 * 导出充值记录
		 * @param response
		 * @param outputStream
		 * @param condition
		 * @param beginTime
		 * @param endTime
		 */
		@RequestMapping("/exportUserRechargeRecord/{condition}/{beginTime}/{endTime}")
		public void exportUserRechargeRecord(
				HttpServletResponse response,ServletOutputStream outputStream,
				@PathVariable String condition,
				@PathVariable String beginTime,
				@PathVariable String endTime){
			iUserOrderService.exportUserRechargeRecord(response,outputStream,condition,beginTime,endTime);
		}


}
