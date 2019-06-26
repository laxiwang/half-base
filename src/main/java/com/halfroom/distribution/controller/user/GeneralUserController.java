package com.halfroom.distribution.controller.user;




import java.util.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.halfroom.distribution.core.util.PhoneUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.halfroom.distribution.common.annotion.Permission;
import com.halfroom.distribution.common.annotion.log.BussinessLog;
import com.halfroom.distribution.common.constant.Dict;
import com.halfroom.distribution.common.constant.tips.AbstractTip;
import com.halfroom.distribution.common.controller.BaseController;
import com.halfroom.distribution.common.exception.BizExceptionEnum;
import com.halfroom.distribution.common.exception.BussinessException;
import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.util.ToolUtil;
import com.halfroom.distribution.dao.BranchsalerDao;
import com.halfroom.distribution.dao.GeneralUserDao;
import com.halfroom.distribution.dao.UserBranchsalerChangeDao;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.model.Branchsaler;
import com.halfroom.distribution.persistence.model.User;
import com.halfroom.distribution.persistence.model.UserBranchsalerChange;
import com.halfroom.distribution.service.IGeneralUserService;
import com.halfroom.distribution.service.ISalerInviteService;

/**
 * 普通用户控制器
 */
@Controller
@RequestMapping("/generalUser")
public class GeneralUserController extends BaseController{
	
	 private static String PREFIX = "/system/generaluser";
	 
	 @Resource
	 private GeneralUserDao userDao;
	 @Resource
	 private BranchsalerDao branchsalerDao;
	 @Resource
	 private IGeneralUserService iGeneralUserService;
	 @Resource
	 private ISalerInviteService iSalerInviteService;
	 @Resource
	 private BranchsalerMapper branchsalerMapper;
	 @Resource
	 private UserBranchsalerChangeDao userBranchsalerChangeDao;
	   /**
	     * 跳转到用户列表页面
	     */
	    @RequestMapping("")
	    public String index() {
	        return PREFIX + "/generaluser.html";
	    }
	    /**
	     * 查询普通用户列表
	     */
	    @RequestMapping("/list")
	    @Permission
	    @ResponseBody
	    public Object list(
	    		@RequestParam(name="offset",required=false)Integer offset,
	    		@RequestParam(name="limit",required=false)Integer limit,
	    		@RequestParam(name = "sort", required = false, defaultValue = "") String sort,
				@RequestParam(name = "order", required = false, defaultValue = "") String order,
				@RequestParam(name="condition",required=false,defaultValue="")String condition,
	    		@RequestParam(name="beginTime",required=false,defaultValue="")String beginTime,
	    		@RequestParam(name="endTime",required=false,defaultValue="")String endTime,
	    		@RequestParam(name="userStatus",required=false)Integer userStatus) {
	        return  iGeneralUserService.userListPage(offset,limit,sort,order,condition,null,beginTime,endTime,userStatus);
	    }
	    
	    @RequestMapping("/exportList/{condition}/{beginTime}/{endTime}/{userStatus}")
	    public void exportList(
	    		HttpServletResponse response,ServletOutputStream outputStream,
	    		@RequestParam(name = "sort", required = false, defaultValue = "") String sort,
				@RequestParam(name = "order", required = false, defaultValue = "") String order,
				@PathVariable String condition,
				@PathVariable String beginTime,
				@PathVariable String endTime,
				@PathVariable Integer userStatus) {
	          iGeneralUserService.exportUserListPage(response,outputStream,null,null,sort,order,condition,null,beginTime,endTime,userStatus);
	    }

	    /**
	     * 跳转到编辑用户页面
	     */
	    @Permission
	    @RequestMapping("/user_edit/{userId}")
	    public String userEdit(@PathVariable Integer userId, Model model) {
	    	 iGeneralUserService.userInfo(userId, model);
	    	 return PREFIX + "/generaluser_edit.html";
	    }
	    
	    @RequestMapping("/user_edit")
	    @ResponseBody
	    public AbstractTip edit(@Valid User user,BindingResult result){
	    	try {
	    		
	    		User user2 = userDao.selectByid(user.getId());
	    		int oldBranchsaler=user2.getBranchsaler();
	    		if(oldBranchsaler!=user.getBranchsaler()){
	    			userBranchsalerChangeDao.insertSelective(new UserBranchsalerChange(user.getId(),oldBranchsaler,user.getBranchsaler()));
	    		}
	    		userDao.updateById(user);
			} catch (Exception e) {
				new BussinessException(BizExceptionEnum.SERVER_ERROR);
			}
	    	return  SUCCESS_TIP;
	    }
	    
	    
	    /**
	     * 冻结用户
	     */
	    @RequestMapping("/freeze")
	    @BussinessLog(value = "冻结用户", key = "userId", dict = Dict.USER_DICT)
	    @ResponseBody
	    public AbstractTip freeze(@RequestParam Integer userId) {
	        if (ToolUtil.isEmpty(userId)) {
	            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
	        }
	        this.userDao.setStatus(userId,1);
	        return SUCCESS_TIP;
	    }
	    
	    /**
	     * 解除冻结用户
	     */
	    @RequestMapping("/unfreeze")
	    @BussinessLog(value = "解除冻结用户", key = "userId", dict = Dict.USER_DICT)
	    @ResponseBody
	    public AbstractTip unfreeze(@RequestParam Integer userId) {
	        if (ToolUtil.isEmpty(userId)) {
	            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
	        }
	        this.userDao.setStatus(userId, 0);
	        return SUCCESS_TIP;
	    }
	    @RequestMapping("/userStatusStatisticsIndex")
	    public String userStatusStatisticsIndex() {
	    	return PREFIX + "/userstatus.html";
	    }
	    
	    @RequestMapping("/userStatusStatistics")
	    @ResponseBody
	    public Object userStatusStatistics( String condition, String beginTime,  String endTime) {
	    	return iGeneralUserService.generalUserStatistics(condition, beginTime, endTime);
	    }

	    /**
	     *播放记录页面跳转
	     */
	    @RequestMapping("/playRecordIndex/{userId}")
	    public Object playRecordIndex(@PathVariable Integer userId,Model model){
	    	 model.addAttribute("userId",userId);
	    	 return PREFIX + "/playrecord.html";
	    }	    
	    /**
	     *播放记录列表
	     */
	    @RequestMapping("/playRecordList")
	    @ResponseBody
	    public Object playRecordList(Integer userid,
	    		@RequestParam(name="beginTime",required=false,defaultValue="")String beginTime,
	    		@RequestParam(name="endTime",required=false,defaultValue="")String endTime) {
	        return  iGeneralUserService. playRecord(userid,beginTime,endTime);
	    }
	    
	    @RequestMapping("generaluser__add_index")
	    public Object generaluser__add_index(){
	    	 return PREFIX + "/generaluser__add.html";
	    }	 
	    /**
	     * 添加用户
	     * @param user
	     * @return
	     */
	    @RequestMapping("add")
	    @ResponseBody
	    public Object add(@Valid User user){
	    	Map<String, Object> params= new HashMap<String, Object>();
	    	params.put("phone", user.getPhone());
	    	User findUser=  userDao.selectByPhone(params);
	    	if(findUser!=null){
	    		return -1;
	    	}
			Map<String, String>  phoneInfoResult=PhoneUtil.getProvinceAndCityByPhone( user.getPhone());
			String province = phoneInfoResult.get("province");
			String city = phoneInfoResult.get("city");
			user.setHeadimage("http://static.half-room.com/default_avatar.png");
	    	user.setPhone(user.getPhone().trim());
			user.setProvince(province);
			user.setCity(city);
	    	iGeneralUserService.insertUser(user);
	    	return SUCCESS_TIP;
	    }
	  
	    
	    
	    /**
	     * 增加积分
	     * @param userId
	     * @return
	     */
	    @RequestMapping("/addintegralForUser/{userId}/{integral}")
	    @ResponseBody
	    public Object addintegralForUser(@PathVariable Integer userId,@PathVariable Integer integral){
	    	return iGeneralUserService.addintegralForUser(userId,integral);
	    }
	    /**
	     * 开通会员
	     * @param userId
	     * @return
	     */
	    @RequestMapping("/openMember/{userId}/{book}")
	    @ResponseBody
	    public Object openMember(@PathVariable Integer userId,@PathVariable Integer book){
	    	return iGeneralUserService.openMember(userId, book);
	    }
	    
	    
	    /**
	     * 无归属会员跳转页面
	     * @param 
	     * @return
	     */
	    @RequestMapping("/othernessUser")
	    public String othernessUser(){
	    	return  PREFIX+"/othernessuser.html";
	    }
	    /**
	     * 无归属会员统计
	     * @param 
	     * @return
	     */
	    @RequestMapping("/othernessUserStatistics")
	    @ResponseBody
	    public Object othernessUserStatistics(){
	    	int banchid = ShiroKit.getUser().getBranchsalerId();
			Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
			if (branchsaler == null) {
				return null;
			}
	    	return  iGeneralUserService.othernessUserStatistics(branchsaler.getId());
	    }
	    /**
	     * 无归属会员统计导出
	     * @param 
	     * @return
	     */
	    @RequestMapping("/exportothernessUserStatistics")
	    public void exportothernessUserStatistics(HttpServletResponse response,ServletOutputStream outputStream){
	    	int banchid = ShiroKit.getUser().getBranchsalerId();
			Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
			if (branchsaler == null) {
				return;
			}
	       iGeneralUserService.exportothernessUserStatistics(response,outputStream,branchsaler.getId());
	    }
	    
	    
	    /**
	     * 无归属订单跳转页面
	     * @param 
	     * @return
	     */
	    @RequestMapping("/othernessOrder")
	    public String othernessOrder(){
	    	return  PREFIX+"/othernessorder.html";
	    }
	    
	    /**
	     * 无归属订单统计
	     * @param 
	     * @return
	     */
	    @RequestMapping("/othernessOrderStatistics")
	    @ResponseBody
	    public Object othernessOrderStatistics(){
	    	int banchid = ShiroKit.getUser().getBranchsalerId();
			Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
			if (branchsaler == null) {
				return null;
			}
	    	return  iGeneralUserService.othernessOrderStatistics(branchsaler.getId());
	    }
	    
	    /**
	     * 无归属订单统计导出
	     * @param 
	     * @return
	     */
	    @RequestMapping("/exportothernessOrderStatistics")
	    public void exportothernessOrderStatistics(HttpServletResponse response,ServletOutputStream outputStream){
	    	int banchid = ShiroKit.getUser().getBranchsalerId();
			Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
			if (branchsaler == null) {
				return;
			}
	       iGeneralUserService.exportothernessOrderStatistics(response,outputStream,branchsaler.getId());
	    }
	    /**
	     * 修改备注跳转
	     * @param userId
	     * @param model
	     * @return
	     */
	    @RequestMapping("/remark_edit/{userId}")
	    public String remark_edit(@PathVariable Integer userId,Model model){
	    	User user = userDao.selectByid(userId);
	    	model.addAttribute("user",user);
	    	return PREFIX+"/remark_edit.html";
	    }
	    @RequestMapping("/remark_edit")
	    @ResponseBody
	    public Integer remark_edit(@RequestParam Integer userId,@RequestParam String remark){
	    	User user = userDao.selectByid(userId);
	    	user.setRemark(remark);
	    	return userDao.updateByPrimaryKeySelective(user);
	    }
	    @RequestMapping("/userBranchsalerChange_index")
	    @Permission
	    public String userBranchsalerChange_index(){
	    	return PREFIX+"/userbranchsalerchange.html";
	    }

		/**
		 * 用户变更分会记录
		 * @param condition
		 * @param beginTime
		 * @param endTime
		 * @return*/
	    @RequestMapping("/userBranchsalerChange")
		@ResponseBody
	    public Object userBranchsalerChange(
	    		@RequestParam(name="condition",required=false,defaultValue="") String condition,
	    		@RequestParam(name="beginTime",required=false,defaultValue="") String beginTime,
	    		@RequestParam(name="endTime",required=false,defaultValue="") String endTime){
	    	Map<String, Object> params = new HashMap<>();
	    	params.put("condition", condition);
	    	if(StringUtils.isNotBlank(beginTime))
	    	params.put("beginTime", beginTime);
			if(StringUtils.isNotBlank(endTime))
	    	params.put("endTime", endTime+" 23:59:59");
	    	List<Map<String, Object>>	 list =userBranchsalerChangeDao.selectLists(params);
	    	return list;
	    }

	    @RequestMapping("/userNewly_index")
	    public String userNewly(){
	    	return PREFIX+"/usernewly.html";
	    }
	    /**
	     * 新增用户统计
	     * @return
	     */
	    @RequestMapping("/userNewly")
	    @ResponseBody
	    public Object userNewly(
	    		@RequestParam(name="beginTime",required=false,defaultValue="") String beginTime,
	    		@RequestParam(name="endTime",required=false,defaultValue="") String endTime
	    		){
	    	return  iGeneralUserService.userNewlyStatistics(beginTime,endTime);
		}
	    @RequestMapping("/userActive_index")
	    public String userActive(){
	    	return PREFIX+"/active.html";
	    }
	    
	    /**
	     * 活跃用户统计
	     * @return
	     */
	    @RequestMapping("/userActive")
	    @ResponseBody
	    public Object userActive(
	    		@RequestParam(name="beginTime",required=false,defaultValue="") String beginTime,
	    		@RequestParam(name="endTime",required=false,defaultValue="") String endTime
	    		){
	    	return  iGeneralUserService.userActive(beginTime, endTime);
		}


	/**
	 *用户课程状态跳转
	 */
	@RequestMapping("/userBooksStatus_index/{userId}")
	public Object userBooksStatus_index(@PathVariable Integer userId,Model model){
		model.addAttribute("userId",userId);
		return PREFIX + "/userbooks_status.html";
	}
	@RequestMapping("/userBooksStatus")
	@ResponseBody
	public Object userBooksStatus(
			@RequestParam(name="userId",required=false) Integer userId
	){
		return  iGeneralUserService.userBooksStatus(userId);
	}


	/**
	 *积分排行页面
	 */
	@RequestMapping("/userIntegraIndex")
	public String userIntegraIndex(){
		return PREFIX + "/userintegra.html";
	}


	/**
	 *积分排行
	 */
	@RequestMapping("/userIntegra")
	@ResponseBody
	public Object userIntegra(@RequestParam(name="beginTime",required=false,defaultValue="") String beginTime,
							  @RequestParam(name="endTime",required=false,defaultValue="") String endTime){
		return iGeneralUserService.userIntegra(beginTime,endTime);
	}

	/**
	 * 变更用户分会跳转
	 * @return
	 */
	@RequestMapping("/userChangeBranchsalerIndex")
	public String userChangeBranchsalerIndex(){
		return PREFIX + "/userChangeBranchsalerIndex.html";
	}

	@RequestMapping("/userChangeBranchsaler")
	@ResponseBody
	public Object BatchuserChangeBranchsaler(
			@RequestParam(name="arr",required=true) String arr,
			@RequestParam(name="branchsaler",required=true) Integer branchsaler
	){
		return iGeneralUserService.batchuserChangeBranchsaler(arr,branchsaler);
	}


	/**
	 * 分会会长用户播放记录排名
	 * @return
	 */
	@RequestMapping("/branchsalerUserPlay")
	public String branchsalerUserPlay(){
		return PREFIX + "/branchsaleruserplay.html";
	}


	/**
	 * 分会会长用户播放记录排名
	 * @return
	 */
	@RequestMapping("/branchsalerUserPlay_do")
	@ResponseBody
	public Object branchsalerUserPlay_do(
			@RequestParam(name="condition",required=false) String condition){

		Map<String,Object> params = new HashMap<>();
		String phones="'13336636363', '13915274219', '18868961396', '18811637326', '18507492249', '15298653745', '17673633650', '15607410123', '14726982907', '13739074992', '15574333818', '18117131790', '18280881240', '15179293438', '13777112288', '13566001937', '13738387713', '15715854764', '13032108050', '18916615385', '13041650041', '13166273297', '18302183367', '13917787313', '13611784658', '18701875065', '15901632506', '15900523506', '13122975688', '13917530411', '13522005408', '13817924748', '15972120899', '13807178369', '13872528081', '15997593702', '13952165109', '15050825025', '13813285100', '13918585495', '15161131534', '18551677827', '13372001888', '13585197336', '18761891493', '13655197233', '13770661050', '13913897199', '18120132506', '18602509387', '13337800832', '15895892620', '15951856241', '15250981552', '18013899509', '13851842871', '13851634568', '18913389061', '13338607668', '13851951140', '18055010789', '18015095917', '17715277713', '18651873950', '13914451627', '18662186618', '18362650053', '18662162551', '13773031515', '13182646076', '17768798686', '18632227866', '13773669390', '13861949302', '13910276320', '13899088122', '18510259999', '13279090333', '15001585150', '15276291111', '18999055251', '13609973879', '13805998770', '18650599836', '13788816770', '18876506905', '13599253892', '18606993699', '15750968678', '18559250656', '13806059997', '18659232048', '18050097502', '13375072555', '18006980555', '13159392898', '13950901008', '13960509726', '15359086688', '13959389385', '18859268582', '13515969437', '13779964356', '15863739229', '13562710462', '17862967629', '18653368892', '18663363580', '18769316866', '15065550690', '18663335070', '18763986278', '15562798888', '18963058999', '18653022229', '15554531111', '15615601116', '13905309117', '15063579976', '13906353860', '13616353223', '18563565696', '13375608158', '18561372932', '13687689131', '18890482627', '15197959973', '13873956998', '17673950677', '18373130788', '18684881603', '17775800101', '18570375766', '18008400125', '18711036545', '15807465628', '18890385757', '18692770661', '18692733392', '15700735985', '17375728528', '17347120850', '17600607555', '13553170555', '13327191488', '18104722384', '13204720681', '18447063884', '18747555448', '18947215757', '13451326128', '18947205757', '15204728889', '18747452442', '18847959312', '15771384691', '18686253676', '13940403431', '13224249123', '13998363265', '15041238620', '15998262110', '13942153231', '13942581236', '13940403431', '18722588919', '18222088098', '18622075816', '18522318417', '18512205450', '15838390410', '18637932355', '19937973090', '18637932095', '13903893353', '18638903353', '15939167773', '15839167765', '13910956452', '13716605118', '15603924351', '18639265266', '15342624131', '15037065666', '13700703789', '15822453621', '13104639833', '13552820001', '15204728889', '13327191488', '18104722384', '13204720681', '18447063884', '18747555448', '18947215757', '13451326128', '18947205757'";
		params.put("phones",phones);
		int limit = 20;
		if(StringUtils.isNotBlank(condition))
			limit=1;
		List<Map<String,Object>> list  = userDao.selectBranchsalerUserPlay(params);
		int size = limit == 1?list.size():20;
		List<Map<String,Object>> resList=new ArrayList<>();
		for(int i=0;i<size;i++){
			if(StringUtils.isNotBlank(condition)){
				if(list.get(i).get("userPhone")!=null&&list.get(i).get("userPhone").equals(condition)){
					if(i==0)
						list.get(i).put("userName", " <div style='display:inline-block'> <img src='http://image.half-room.com/icon_number_1.png' height='20px' width='20px' ></div>&nbsp;&nbsp;&nbsp;&nbsp;"+list.get(i).get("userName"));
					if(i==1)
						list.get(i).put("userName", " <div style='display:inline-block'> <img src='http://image.half-room.com/icon_number_2.png' height='20px' width='20px' ></div>&nbsp;&nbsp;&nbsp;&nbsp;"+list.get(i).get("userName"));
					if(i==2)
						list.get(i).put("userName", " <div style='display:inline-block'> <img src='http://image.half-room.com/icon_number_3.png' height='20px' width='20px'></div>&nbsp;&nbsp;&nbsp;&nbsp;"+list.get(i).get("userName"));
					resList.add(list.get(i));
					break;
				}
			}else {

				list.get(i).put("index",(i + 1));
                if(i==0)
                    list.get(i).put("userName", " <div style='display:inline-block'> <img src='http://image.half-room.com/icon_number_1.png' height='20px' width='20px' ></div>&nbsp;&nbsp;&nbsp;&nbsp;"+list.get(i).get("userName"));
                if(i==1)
                    list.get(i).put("userName", " <div style='display:inline-block'> <img src='http://image.half-room.com/icon_number_2.png' height='20px' width='20px' ></div>&nbsp;&nbsp;&nbsp;&nbsp;"+list.get(i).get("userName"));
                if(i==2)
                    list.get(i).put("userName", " <div style='display:inline-block'> <img src='http://image.half-room.com/icon_number_3.png' height='20px' width='20px'></div>&nbsp;&nbsp;&nbsp;&nbsp;"+list.get(i).get("userName"));
                resList.add(list.get(i));
			}
		}
		return resList;
	}
	public static void main(String[] args){

	}
}
