package com.halfroom.distribution.controller.user;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.persistence.model.SalerInvite;
import com.halfroom.distribution.service.IGeneralUserService;
import com.halfroom.distribution.service.ISalerInviteService;
import com.halfroom.distribution.service.IUserOrderService;
import com.halfroom.distribution.service.IZipService;

/**
 * 销售控制器
 */
@Controller
@RequestMapping("/saler")
@SuppressWarnings("all")
public class SalerController extends BaseController {
	private static String PREFIX = "/system/generaluser/saler";

	@Resource
	private IGeneralUserService iGeneralUserService;
	@Resource
	private IUserOrderService iUserOrderService;
	@Resource
	private ISalerInviteService iSalerInviteService;
	@Resource
	private IZipService iZipService;
	/**
	 * 跳转到销售列表页面
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "/saler.html";
	}

	/**
	 * 查询销售列表 默认已接受邀请
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(Integer status,
					   @RequestParam(name = "condition", required = false, defaultValue = "") String condition,
			@RequestParam(name = "beginTime", required = false, defaultValue = "") String beginTime,
			@RequestParam(name = "endTime", required = false, defaultValue = "") String endTime) {
		status=1;
		return iGeneralUserService.salerList(condition,status,beginTime, endTime);
	}
	@RequestMapping("/exportList/{beginTime}/{endTime}")
	public void exportList(
			HttpServletResponse response,ServletOutputStream outputStream,
			Integer status,
			@PathVariable String beginTime,
			@PathVariable String endTime) {
			status=1;
		 iGeneralUserService.exprotSalerList(response,outputStream,null,status, beginTime, endTime);
	}
	/**
	 * 销售记录页面跳转
	 */
	@RequestMapping("/salerOrderListIndex/{inUserid}")
	public Object salerOrderListIndex(@PathVariable Integer inUserid, Model model) {
		model.addAttribute("inUserid", inUserid);
		return PREFIX + "/salerOrders.html";
	}

	/**
	 * 销售记录列表
	 */
	@RequestMapping("/salerOrderList")
	@ResponseBody
	public Object salerOrderList(Integer inUserid) {
		return iUserOrderService.orderList(null, inUserid, null,1, null, null);
	}

	/**
	 * 推广记录页面跳转
	 */
	@RequestMapping("/salerUserListIndex/{inUserid}")
	public Object salerUserListIndex(@PathVariable Integer inUserid, Model model) {
		model.addAttribute("inUserid", inUserid);
		return PREFIX + "/salerUsers.html";
	}

	/**
	 * 推广记录列表
	 */
	@RequestMapping("/salerUserList")
	@ResponseBody
	public Object salerUserList(Integer inUserid) {
		return iGeneralUserService.userList(null, inUserid, null, null,null);
	}

	/**
	 * 邀请记录列表页面
	 */
	@RequestMapping("/inviteListIndex")
	public String inviteListIndex() {
		return PREFIX + "/invites.html";
	}

	/**
	 * 邀请记录列表
	 */
	@RequestMapping("/inviteList")
	@ResponseBody
	public Object inviteList(
			@RequestParam(name = "condition", required = false, defaultValue = "") String condition, 
			Integer status,
			@RequestParam(name = "beginTime", required = false, defaultValue = "") String beginTime,
			@RequestParam(name = "endTime", required = false, defaultValue = "") String endTime) {
		return iGeneralUserService.salerList( condition,status, beginTime, endTime);
	}
	
	/**
	 * 邀请销售大使页面
	 */
	@RequestMapping("/inviteSalerListIndex")
	public String inviteSalerListIndex() {
		return PREFIX + "/inviteSaler.html";
	}
	
	
	
	/**
	 * 邀请销售大使展示推广大使的销售记录
	 */
	@RequestMapping("/inviteSalerList")
	@ResponseBody
	public Object inviteSalerList(	
			@RequestParam(name = "offset", required = false, defaultValue = "") Integer offset,
			@RequestParam(name = "limit", required = false, defaultValue = "") Integer limit,
			@RequestParam(name = "condition", required = false, defaultValue = "") String condition) {
		 return iGeneralUserService.extendsListPage(offset,limit,condition);
	}
	  /**
     * 添加邀请成为销售大使记录跳转页面
     */
    @RequestMapping("/openSalerInvite/{userId}")
    public String openSalerInvite(@PathVariable Integer userId,Model model){
    	model.addAttribute("userId",userId);
    	return PREFIX + "/opensalerinvite.html";
    }
    /**
     * 添加邀请成为销售大使记录
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("/addSalerInvite/{remark}/{userId}")
    @ResponseBody
    public Object addSalerInvite(@PathVariable String remark,@PathVariable Integer userId) throws UnsupportedEncodingException{
    	 Base64.Decoder decoder = Base64.getDecoder();
    	remark=new String(decoder.decode(remark.getBytes()), "UTF-8");
    	return iSalerInviteService.insertSalerInvite(userId,remark);
    }
    /**
     * 修改销售大使提成页面跳转
     */
    @RequestMapping("/updateSalerInvite/{userId}")
    public String updateSalerInvite(@PathVariable Integer userId,Model model){
    	//查询销售大使提成
    	SalerInvite salerInvite = iSalerInviteService.selectByUserId(userId);
    	model.addAttribute("remark",salerInvite.getRemark());
    	model.addAttribute("userId",userId);
    	return PREFIX + "/updatesalerInvite.html";
    }
    
    /**
     * 修改销售大使提成页面跳转
     */
    @RequestMapping("/updateSalerInvite")
    @ResponseBody
    public Object updateSalerInvite(@RequestParam Integer userId,@RequestParam String remark){
    	//查询销售大使提成
    	SalerInvite salerInvite = iSalerInviteService.selectByUserId(userId);
    	salerInvite.setRemark(remark);
    	return iSalerInviteService.updateSalerInvite(salerInvite);
    	
    }
    
    /**
     * 撤销邀请
     * @param userId
     * @return
     */
    @RequestMapping("/closeSalerInvite/{userId}")
    @ResponseBody
    public Object closeSalerInvite(@PathVariable Integer userId){
    	return iSalerInviteService.closeSalerInvite(userId);
    }
    
    /**
     * 导出图片压缩包
     * @param userid
     * @param response
     */
    @RequestMapping("/exportPicture/{userid}")
    public void exportPicture(HttpServletResponse response,@PathVariable Integer userid) {
    	iZipService.exportSalrQRcode(response, userid);
    }
}
