package com.halfroom.distribution.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.halfroom.distribution.dao.*;
import com.halfroom.distribution.persistence.model.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.baomidou.mybatisplus.plugins.Page;
import com.halfroom.distribution.common.constant.factory.ConstantFactory;
import com.halfroom.distribution.common.exception.BizExceptionEnum;
import com.halfroom.distribution.common.exception.BussinessException;
import com.halfroom.distribution.common.page.PageInfoBT;
import com.halfroom.distribution.core.shiro.ShiroKit;
import com.halfroom.distribution.core.util.Convert;
import com.halfroom.distribution.core.util.DateTimeUtil;
import com.halfroom.distribution.core.util.ExcelUtil;
import com.halfroom.distribution.core.util.UniqueKeyGenerator;
import com.halfroom.distribution.persistence.dao.BranchsalerMapper;
import com.halfroom.distribution.persistence.vo.GeneralUserVo;
import com.halfroom.distribution.persistence.vo.OthernessOrderVo;
import com.halfroom.distribution.persistence.vo.OthernessUserVo;
import com.halfroom.distribution.persistence.vo.ProductCardVo;
import com.halfroom.distribution.persistence.vo.SalerVo;
import com.halfroom.distribution.service.IGeneralUserService;

@Service
@Transactional
public class GeneralUserServiceImpl implements IGeneralUserService {
	@Resource
	private GeneralUserDao generalUserDao;
	@Resource
	private BranchsalerMapper branchsalerMapper;
	@Resource
	private UserOrderDao userOrderDao;
	@Resource
	private IntegraLogDao integraLogDao;
	@Resource
	private UserBookDao userBookDao;
	@Resource
	private UserBranchsalerChangeDao userBranchsalerChangeDao;
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Map<String, Object>> userList(String condition, Integer inUserid,String beginTime, String endTime,Integer userStatus) {
		Map<String, Object> map = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			map.put("branchsaler", branchsaler.getId());
		}
		map.put("condition", condition);
		if (StringUtils.isNotBlank(beginTime)) {
			map.put("beginTime", DateTimeUtil.parseDate(beginTime,DateTimeUtil.DEFAULT_FORMAT_DATE));
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime", DateTimeUtil.parseDate(endTime,DateTimeUtil.DEFAULT_FORMAT_DATE));
		}
		if (inUserid!=null) {
			map.put("inUserid", inUserid);
		}
		if (userStatus!=null) {
			map.put("userStatus", userStatus);
		}
		List<Map<String, Object>> resMap = generalUserDao.selectUsers(map);
		return resMap;
	}

	@Override
	public PageInfoBT userListPage(Integer offset,Integer limit,String sort,String order,String condition, Integer inUserid, String beginTime,
			String endTime,Integer userStatus) {
		Map<String, Object> map = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			map.put("branchsaler", branchsaler.getId());
		}
		map.put("condition", condition);
		if (StringUtils.isNotBlank(beginTime)) {
			map.put("beginTime", beginTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime",endTime+" 23:59:59");
		}
		if (StringUtils.isNotBlank(sort)) {
			map.put("sort", sort);
		}
		if (StringUtils.isNotBlank(order)) {
			map.put("order", order);
		}
		if (inUserid!=null) {
			map.put("inUserid", inUserid);
		}
		if (userStatus!=null) {
			map.put("userStatus", userStatus);
		}
		if (StringUtils.isNotBlank(order)&&sort.equals("playSum")) {
			sort="playSumSelect";
			map.put("sort", sort);
		}
		Page page = new Page<>(offset/limit+1,limit);
		List<Map<String, Object>> list=generalUserDao.selectUsersPage(map, page);
		for (Map<String, Object> map2 : list) {
			//查询用户最近一次播放记录
			String string=generalUserDao.selectPlayone((long)map2.get("uid"));
			map2.put("partlog", string );
		}
		page.setRecords(list);
		PageInfoBT resPage = new PageInfoBT(page);
		return resPage;
	}

	@Override
	public User userInfo(Integer id, Model model) {
		User user = generalUserDao.selectByid(id);

		model.addAttribute("deptName",
				ConstantFactory.me().getDeptName(user.getBranchsaler()) == null
						|| "".equals(ConstantFactory.me().getDeptName(user.getBranchsaler())) ? "总会"
								: ConstantFactory.me().getDeptName(user.getBranchsaler()));
		String roleName = "";
		if (user.getRole() != null && StringUtils.isNotBlank(user.getRole() + "") && user.getRole() == 0) {
			roleName = "推广";
		} else if (user.getRole() != null && StringUtils.isNotBlank(user.getRole() + "") && user.getRole() == 1) {
			roleName = "销售";
		} else {
			roleName = "未知身份";
		}
		model.addAttribute("roleName", roleName);
		//查询以上架课程
		List<Book> books=generalUserDao.selectOnSelveBooks();
		model.addAttribute("books", books);
		model.addAttribute("user",user);
		return user;
	}

	@Override
	public Map<String, Object> generalUserStatistics(String condition, String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			map.put("branchsaler", branchsaler.getId());
		}
		if (StringUtils.isNotBlank(beginTime)) {
			map.put("beginTime", DateTimeUtil.parseDate(beginTime,DateTimeUtil.DEFAULT_FORMAT_DATE));
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime",endTime+" 23:59:59");
		}

		Map<String, Object> resMap = new HashMap<>();
		map.put("flag", false);
		int count = generalUserDao.countGeneralUser(map);
		int paid = generalUserDao.paidGeneralUser(map);
		int cardPay = generalUserDao.cardGeneralUser(map);
		int sup = generalUserDao.supGeneralUser(map);
		int suped = generalUserDao.supedGeneralUser(map);
		int giftGiving = generalUserDao.giftGiving(map);
		int exchange = generalUserDao.exchange(map);
		int cardGiving=generalUserDao.cardGiving(map);
		int fullmembers = paid + giftGiving + exchange+cardGiving+cardPay;
		resMap.put("count", count);
		resMap.put("paid", paid);
		resMap.put("cardPay", cardPay);
		resMap.put("sup", sup);
		resMap.put("suped", suped);
		resMap.put("giftGiving", giftGiving);
		resMap.put("exchange", exchange);
		resMap.put("cardGiving", cardGiving);
		resMap.put("fullmembers", fullmembers);
		//查询以上架课程  
	    List<Book> books=generalUserDao.selectOnSelveBooks();
	    for(int i=0;i<books.size();i++){
	    	 resMap.put("bookName"+(i+1),books.get(i).getName());
	    	 map.put("bookid", books.get(i).getId());
	 	     resMap.put("bookSum"+(i+1), userBookDao.selectUseBookSumbyBookid(map));
	    }
	    resMap.put("books", books.size());
		return resMap;
	}

	public List<Map<String, Object>> playRecord(Integer userid, String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<>();
		map.put("userid", userid);
		if (StringUtils.isNotBlank(beginTime)) {
			map.put("beginTime", DateTimeUtil.parseDate(beginTime,DateTimeUtil.DEFAULT_FORMAT_DATE));
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime",endTime+" 23:59:59");
		}
		List<Map<String, Object>> resMap = generalUserDao.playRecord(map);
		return resMap;
	}
	@Transactional
	public User insertUser(User user) {
		if (StringUtils.isBlank(user.getName())) {
			String setName = ("半间教室" + UniqueKeyGenerator.random(3))
					+ user.getPhone().substring(user.getPhone().length() - 3, user.getPhone().length());
			user.setName(setName);
		}
		user.setCreatetime(new Date());
	    generalUserDao.insertUser(user);
	    List<Book> books=generalUserDao.selectOnSelveBooks();
		if(user.getMemberstatus()==0){
			//添加所有试听课程
			for (Book book : books) {
				generalUserDao.insertUserBook(new UserBook(user.getId(),book.getId(),1));
			}
		}else if(user.getMemberstatus()==1){
			//添加所有正式课程
			for (Book book : books) {
				generalUserDao.insertUserBook(new UserBook(user.getId(),book.getId(),2));
			}
		}
		return user;
	}

	@Override
	public int  addintegralForUser(Integer userid, Integer integral) {
		return integraLogDao.insertSelective(new IntegraLog(userid,0,integral, 3));
	}

	@Override
	public List<Map<String, Object>> salerList(String condition,Integer status, String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			map.put("branchsaler", branchsaler.getId());
		}
		if (StringUtils.isNotBlank(beginTime)) {
			map.put("beginTime", DateTimeUtil.parseDate(beginTime,DateTimeUtil.DEFAULT_FORMAT_DATE));
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime",endTime+" 23:59:59");
		}
		if (StringUtils.isNotBlank(condition)) {
			map.put("condition", condition);
		}
		map.put("status", status);
		return generalUserDao.selectSalersStatistics(map);
	}
	@Override
	public void exprotSalerList(HttpServletResponse response, ServletOutputStream outputStream, String condition,
			Integer status, String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return ;
		}
		
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			map.put("branchsaler", branchsaler.getId());
		}
		if (condition != null && !condition.equals("null")) {
			map.put("condition", condition);
		}
		if (beginTime != null && !beginTime.equals("null")) {
			map.put("beginTime", beginTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime",endTime+" 23:59:59");
		}
		map.put("status", status);
		List<Map<String, Object>> list= generalUserDao.selectSalersStatistics(map);
		List<SalerVo> listSalerVo = new ArrayList<>();
		for (Map<String, Object> map2 : list) {
			SalerVo salerVo = (SalerVo)Convert.mapToObject(map2, SalerVo.class);
			listSalerVo.add(salerVo);
		}
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		ExcelUtil<SalerVo> myExcel = new ExcelUtil<>();
		String[] headerColumns = {"姓名" ,  "电话" , "邀请时间" , "生效时间" , "提成" , "销售总计" };
		String[] fieldColumns =  {"uName", "uPhone","inviteTime","updatetime","remark","fee"};
		try {
			HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "销售大使列表", listSalerVo, headerColumns, fieldColumns,false);
			//myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.doResponse(response, outputStream, "销售大使列表", hssfWorkbook);
	}

	
	@Override
	public PageInfoBT  extendsListPage(Integer offset,Integer limit,String condition) {
		Map<String, Object> map = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			map.put("branchsaler", branchsaler.getId());
		}
		map.put("condition", condition);
		map.put("role", 0);
		Page page = new Page<>(offset/limit+1,limit);
		List<Map<String, Object>> list=generalUserDao.selectExtendsStatistics(map,page);
		page.setRecords(list);
		PageInfoBT resPage = new PageInfoBT(page);
		return resPage;
	}
	@Override
	public void exportUserListPage(HttpServletResponse response,ServletOutputStream outputStream,Integer offset, Integer limit, String sort, String order, String condition,
			Integer inUserid, String beginTime, String endTime, Integer userStatus) {
		Map<String, Object> map = new HashMap<>();
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return ;
		}
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			map.put("branchsaler", branchsaler.getId());
		}
		if (condition != null && !condition.equals("null")) {
			map.put("condition", condition);
		}
		if (beginTime != null && !beginTime.equals("null")) {
			map.put("beginTime", beginTime);
		}
		if (StringUtils.isNotBlank(endTime)&& !endTime.equals("null")) {
			map.put("endTime",endTime+" 23:59:59");
		}
		if (userStatus != null && userStatus!=-1) {
			map.put("userStatus", userStatus);
		}
		List<Map<String, Object>> list=generalUserDao.selectUsers(map);
		List<GeneralUserVo> listGeneralUserVo = new ArrayList<>();
		for (Map<String, Object> map2 : list) {
			GeneralUserVo generalUserVo = new GeneralUserVo();
			generalUserVo=(GeneralUserVo)Convert.mapToObject(map2,GeneralUserVo.class);
			listGeneralUserVo.add(generalUserVo);
		}
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		ExcelUtil<GeneralUserVo> myExcel = new ExcelUtil<>();
		String[] headerColumns = {"省级分会","姓名" ,  "角色" , "分会" , "电话" , "手机号归属地" , "注册时间","推广信息","状态" , "用户积分" , "最近播放记录","总播放次数","总播放时长","备注"};
		String[] fieldColumns =  {"tempSimplename","uName", "uRole","bName","uPhone","address","uCreateTime","inviterInfo","uStatus","integral","partlog","playCount","playSum","remark"};
		try {
			HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "用户信息", listGeneralUserVo, headerColumns, fieldColumns,false);
			myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.doResponse(response, outputStream, "用户信息", hssfWorkbook);
	}

	@Override
	public      List  othernessUserStatistics(Integer branchsaler){
		Map<String, Object> map = new HashMap<>();
		if (branchsaler != null ) {
			map.put("branchsaler", branchsaler);
		}
		   List<Map<String,Object>> userMapList = generalUserDao.selectCityBybid(map);
		   List<OthernessUserVo> list = new ArrayList<>();
		   for (Map<String, Object> userMap : userMapList) {
			   String city="";
			   if(userMap!=null&&userMap.get("city")!=null){
				   city=(String)userMap.get("city");
				   map.put("city",city);
			   }
			    map.put("flag", false);
				int count = generalUserDao.countGeneralUser(map);
				int paid = generalUserDao.paidGeneralUser(map);
				int sup = generalUserDao.supGeneralUser(map);
				int suped = generalUserDao.supedGeneralUser(map);
				int giftGiving = generalUserDao.giftGiving(map);
				int exchange = generalUserDao.exchange(map);
				int cardGiving=generalUserDao.cardGiving(map);
				int fullmembers = paid + giftGiving + exchange+cardGiving;
				OthernessUserVo othernessUserVo = new OthernessUserVo();
				othernessUserVo.setCity(city);
				othernessUserVo.setSum(fullmembers+sup+suped);
				othernessUserVo.setFullmembers(fullmembers);
				othernessUserVo.setSup(sup);
				othernessUserVo.setSuped(suped);
				list.add(othernessUserVo);
		   }
		   list.sort(Comparator.comparing(OthernessUserVo::getSum).thenComparing(OthernessUserVo::getFullmembers).reversed());
		return  list;
	}
	
	@Override
	public void exportothernessUserStatistics(HttpServletResponse response,ServletOutputStream outputStream,Integer branchsaler) {
		Map<String, Object> map = new HashMap<>();
		if (branchsaler != null ) {
			map.put("branchsaler", branchsaler);
		}
		   List<Map<String,Object>> userMapList = generalUserDao.selectCityBybid(map);
		   List<OthernessUserVo> list = new ArrayList<>();
		   for (Map<String, Object> userMap : userMapList) {
			   String city=(String)userMap.get("city");
			   map.put("city",city);
			   map.put("flag", false);
				int count = generalUserDao.countGeneralUser(map);
				int paid = generalUserDao.paidGeneralUser(map);
				int sup = generalUserDao.supGeneralUser(map);
				int suped = generalUserDao.supedGeneralUser(map);
				int giftGiving = generalUserDao.giftGiving(map);
				int exchange = generalUserDao.exchange(map);
				int cardGiving=generalUserDao.cardGiving(map);
				int fullmembers = paid + giftGiving + exchange+cardGiving;
				OthernessUserVo othernessUserVo = new OthernessUserVo();
				othernessUserVo.setCity(city);
				othernessUserVo.setSum(fullmembers+sup+suped);
				othernessUserVo.setFullmembers(fullmembers);
				othernessUserVo.setSup(sup);
				othernessUserVo.setSuped(suped);
				list.add(othernessUserVo);
		   }
		   list.sort(Comparator.comparing(OthernessUserVo::getSum).thenComparing(OthernessUserVo::getFullmembers).reversed());
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			ExcelUtil<OthernessUserVo> myExcel = new ExcelUtil<>();
			String[] headerColumns = {"手机号归属市" ,  "用户总计" , "正式用户数" , "体验用户数" , "体验过期用户数"};
			String[] fieldColumns =  {"city", "sum","fullmembers","sup","suped"};
			try {
				HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "无归属会员统计", list, headerColumns, fieldColumns,true);
				//myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ExcelUtil.doResponse(response, outputStream, "无归属会员统计", hssfWorkbook);
	}

	@Override
	public List othernessOrderStatistics(Integer branchsaler) {
		Map<String, Object> map = new HashMap<>();
		if (branchsaler != null ) {
			map.put("branchsaler", branchsaler);
		}
	    List<Map<String,Object>> orderMapList = userOrderDao.selectCityBybid(map);
	    List<OthernessOrderVo> list = new ArrayList<>();
	    for (Map<String, Object> orderMap : orderMapList) {
	    	  String city="";
	      if(orderMap!=null){
	    	   city=(String)orderMap.get("city");
			   map.put("city",city);
	      }
		   map.put("paysource",null);  
		   Integer sum = userOrderDao.selectOrderNumBycity(map);
		   map.put("paysource",0);   
		   Integer paysource0 = userOrderDao.selectOrderNumBycity(map);
		   map.put("paysource",1);
		   Integer paysource1 = userOrderDao.selectOrderNumBycity(map);
		   map.put("paysource",2);
		   Integer paysource2 = userOrderDao.selectOrderNumBycity(map);
		   map.put("paysource",3);
		   Integer paysource3 = userOrderDao.selectOrderNumBycity(map);
		   OthernessOrderVo othernessOrderVo = new OthernessOrderVo();
		   othernessOrderVo.setCity(city);
		   othernessOrderVo.setSum(sum);
		   othernessOrderVo.setPaySource0(paysource0);
		   othernessOrderVo.setPaySource1(paysource1);
		   othernessOrderVo.setPaySource2(paysource2);
		   othernessOrderVo.setPaySource3(paysource3);
		   list.add(othernessOrderVo);
		}
	    list.sort(Comparator.comparing(OthernessOrderVo::getSum).thenComparing(OthernessOrderVo::getCity).reversed());
		return list;
	}
	
	@Override
	public void exportothernessOrderStatistics(HttpServletResponse response, ServletOutputStream outputStream,
			Integer branchsaler) {
		Map<String, Object> map = new HashMap<>();
		if (branchsaler != null ) {
			map.put("branchsaler", branchsaler);
		}
	    List<Map<String,Object>> orderMapList = userOrderDao.selectCityBybid(map);
	    List<OthernessOrderVo> list = new ArrayList<>();
	    for (Map<String, Object> orderMap : orderMapList) {
	 	   String city=(String)orderMap.get("city");
		   map.put("city",city);
		   map.put("paysource",null);  
		   Integer sum = userOrderDao.selectOrderNumBycity(map);
		   map.put("paysource",0);   
		   Integer paysource0 = userOrderDao.selectOrderNumBycity(map);
		   map.put("paysource",1);
		   Integer paysource1 = userOrderDao.selectOrderNumBycity(map);
		   map.put("paysource",2);
		   Integer paysource2 = userOrderDao.selectOrderNumBycity(map);
		   map.put("paysource",3);
		   Integer paysource3 = userOrderDao.selectOrderNumBycity(map);
		   OthernessOrderVo othernessOrderVo = new OthernessOrderVo();
		   othernessOrderVo.setCity(city);
		   othernessOrderVo.setSum(sum);
		   othernessOrderVo.setPaySource0(paysource0);
		   othernessOrderVo.setPaySource1(paysource1);
		   othernessOrderVo.setPaySource2(paysource2);
		   othernessOrderVo.setPaySource3(paysource3);
		   list.add(othernessOrderVo);
		}
	    list.sort(Comparator.comparing(OthernessOrderVo::getSum).thenComparing(OthernessOrderVo::getCity).reversed());
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		ExcelUtil<OthernessOrderVo> myExcel = new ExcelUtil<>();
		String[] headerColumns = {"手机号归属市" ,  "支付总计" , "公众号支付" , "ios支付" , "android支付","实体卡支付"};
		String[] fieldColumns =  {"city", "sum","paySource0","paySource1","paySource2","paySource3"};
		try {
			HSSFSheet creatAuditSheet = myExcel.creatAuditSheet(hssfWorkbook, "无归属订单统计", list, headerColumns, fieldColumns,true);
			myExcel.generateHeaders(hssfWorkbook, creatAuditSheet, headerColumns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.doResponse(response, outputStream, "无归属订单统计", hssfWorkbook);
	    
	    
	}

	/**
	 * 	
	 * 	-1 用户已是该课程的会员 
	 *   0 系统错误 添加失败
	 *   1 添加成功
	 */
	@Override
	public int openMember(Integer userId, Integer book) {
		UserBook userBook =userBookDao.selectByUseridAndBookid(userId, book);
		if(userBook!=null){
		  if(userBook.getType()==2){
			  return -1;
		  }
		  userBook.setType(2);
		  userBook.setEndtime(DateTimeUtil.addYear(userBook.getEndtime(), 100));;
		  userBookDao.updateByPrimaryKey(userBook);
			return 1;
		}
		return userBookDao.insertSelective(new UserBook(userId, book, 2));
	}
	@Override
	public List<Map<String, Object>> userNewlyStatistics(String beginTime,String endTime){
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		List<Map<String, Object>> list= new ArrayList<>();
		Map<String, Object> params = new HashMap<>();
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			params.put("branchsaler", branchsaler.getId());
		}
		
		if(StringUtils.isBlank(beginTime)||StringUtils.isBlank(endTime)){
			
			//日注册数
			Map<String, Object> resMap1 = new HashMap<>();
			params.put("beginTime",DateTimeUtil.getStartTimeOfDay(new Date()));
			params.put("endTime",DateTimeUtil.getEndTimeOfDay(new Date()));
			resMap1.put("name", "日新增注册用户数");
			resMap1.put("value", generalUserDao.countGeneralUser(params));
			list.add(resMap1);
			//周
			Map<String, Object> resMap2 = new HashMap<>();
			params.put("beginTime", DateTimeUtil.parseDate(DateTimeUtil.getCurrentMonday(),DateTimeUtil.DEFAULT_FORMAT_DATE));
			params.put("endTime", DateTimeUtil.getPreviousSunday()+" 23:59:59");
			resMap2.put("name", "周新增注册用户数");
			resMap2.put("value", generalUserDao.countGeneralUser(params));
			list.add(resMap2);
			//月
			Map<String, Object> resMap3 = new HashMap<>();
			params.put("beginTime", DateTimeUtil.parseDate(DateTimeUtil.getMinMonthDate(),DateTimeUtil.DEFAULT_FORMAT_DATE));
			String endTimeMonth=DateTimeUtil.getMaxMonthDate();
            endTimeMonth=endTimeMonth.substring(0,endTimeMonth.indexOf(" "));
			params.put("endTime",endTimeMonth+" 23:59:59");
			resMap3.put("name", "月新增注册用户数");
			resMap3.put("value", generalUserDao.countGeneralUser(params));
			list.add(resMap3);
			
			
			//日正式
			Map<String, Object> resMap4 = new HashMap<>();
			params.put("beginTime",DateTimeUtil.getStartTimeOfDay(new Date()));
			params.put("endTime",DateTimeUtil.getEndTimeOfDay(new Date()));
	
			resMap4.put("name", "日新增正式用户数");
			resMap4.put("value",  generalUserDao.paidGeneralUser(params)+generalUserDao.giftGiving(params)+ generalUserDao.exchange(params)+generalUserDao.cardGiving(params)+generalUserDao.cardGeneralUser(params));
			list.add(resMap4);
			
			//周正式
			Map<String, Object> resMap5 = new HashMap<>();
			params.put("beginTime", DateTimeUtil.parseDate(DateTimeUtil.getCurrentMonday(),DateTimeUtil.DEFAULT_FORMAT_DATE));
			params.put("endTime", DateTimeUtil.getPreviousSunday()+" 23:59:59");
			resMap5.put("name", "周新增正式用户数");
			resMap5.put("value",  generalUserDao.paidGeneralUser(params)+generalUserDao.giftGiving(params)+ generalUserDao.exchange(params)+generalUserDao.cardGiving(params)+generalUserDao.cardGeneralUser(params));
			list.add(resMap5);
			
			//月正式
			Map<String, Object> resMap6 = new HashMap<>();
			params.put("beginTime", DateTimeUtil.parseDate(DateTimeUtil.getMinMonthDate(),DateTimeUtil.DEFAULT_FORMAT_DATE));
			params.put("endTime",endTimeMonth+" 23:59:59");
			resMap6.put("name", "月新增正式用户数");
			resMap6.put("value",  generalUserDao.paidGeneralUser(params)+generalUserDao.giftGiving(params)+ generalUserDao.exchange(params)+generalUserDao.cardGiving(params)+generalUserDao.cardGeneralUser(params));
			list.add(resMap6);
		}else{
			params.put("beginTime", DateTimeUtil.parseDate(beginTime,DateTimeUtil.DEFAULT_FORMAT_DATE));
			params.put("endTime",endTime+" 23:59:59");
			Map<String, Object> resMap1 = new HashMap<>();
			resMap1.put("name", "新增注册用户数");
			resMap1.put("value", generalUserDao.countGeneralUser(params));
			list.add(resMap1);
			
			
			Map<String, Object> resMap2 = new HashMap<>();
			resMap2.put("name", "新增正式用户数");
			resMap2.put("value",  generalUserDao.paidGeneralUser(params)+generalUserDao.giftGiving(params)+ generalUserDao.exchange(params)+generalUserDao.cardGiving(params)+generalUserDao.cardGeneralUser(params));
			list.add(resMap2);
		}
		
		return list;
	}
	@Override
	public List<Map<String, Object>> userActive(String beginTime, String endTime) {
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		List<Map<String, Object>> list= new ArrayList<>();
		Map<String, Object> params = new HashMap<>();
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			params.put("branchsaler", branchsaler.getId());
		}
		
		if(StringUtils.isBlank(beginTime)||StringUtils.isBlank(endTime)){
			
			//日注册数
			Map<String, Object> resMap1 = new HashMap<>();
			params.put("beginTime",DateTimeUtil.getStartTimeOfDay(new Date()));
			params.put("endTime",DateTimeUtil.getEndTimeOfDay(new Date()));
			resMap1.put("name", "日活用户数");
			resMap1.put("value", generalUserDao.selectActiveNum(params));
			list.add(resMap1);
			//周
			Map<String, Object> resMap2 = new HashMap<>();
			params.put("beginTime", DateTimeUtil.parseDate(DateTimeUtil.getCurrentMonday(),DateTimeUtil.DEFAULT_FORMAT_DATE));
			params.put("endTime", DateTimeUtil.getPreviousSunday()+" 23:59:59");
			resMap2.put("name", "周活用户数");
			resMap2.put("value", generalUserDao.selectActiveNum(params));
			list.add(resMap2);
			//月
			Map<String, Object> resMap3 = new HashMap<>();
			params.put("beginTime", DateTimeUtil.parseDate(DateTimeUtil.getMinMonthDate(),DateTimeUtil.DEFAULT_FORMAT_DATE));
			String a=DateTimeUtil.getMaxMonthDate();
			a=a.substring(0,a.indexOf(" "));
			params.put("endTime",a+" 23:59:59");
			resMap3.put("name", "月活用户数");
			resMap3.put("value", generalUserDao.selectActiveNum(params));
			list.add(resMap3);
		}else{
			params.put("beginTime", DateTimeUtil.parseDate(beginTime,DateTimeUtil.DEFAULT_FORMAT_DATE));
			params.put("endTime", endTime+" 23:59:59");
			Map<String, Object> resMap1 = new HashMap<>();
			resMap1.put("name", "活跃用户数");
			resMap1.put("value", generalUserDao.selectActiveNum(params));
			list.add(resMap1);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> userBooksStatus(Integer userId) {
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<>();
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			params.put("branchsaler", branchsaler.getId());
		}
		params.put("userId",userId);
		return generalUserDao.selectUserBookStatus(params);
	}

	@Override
	public List<Map<String, Object>> userIntegra(String beginTime, String endTime) {
		int banchid = ShiroKit.getUser().getBranchsalerId();
		Branchsaler branchsaler = branchsalerMapper.selectById(banchid);
		if (branchsaler == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<>();
		if (branchsaler != null && branchsaler.getLevel() != 0) {
			params.put("branchsaler", branchsaler.getId());
		}
		if (beginTime != null && !beginTime.equals("null") && !endTime.equals("")) {
			params.put("beginTime", beginTime);
		}
		if (endTime != null && !endTime.equals("null") && !endTime.equals(""))  {
			params.put("endTime",endTime+" 23:59:59");
		}
		return generalUserDao.selectUserIntegraList(params);
	}

	@Override
	@Transactional
	public Map<String, Object> batchuserChangeBranchsaler(String arr, Integer branchsaler) {
		String[] arrs=arr.split(",");
		Map<String,Object> params = new HashMap<>();
		Map<String,Object> resMap = new HashMap<>();
		Branchsaler branchsalerModel=branchsalerMapper.selectById(branchsaler);
		resMap.put("res",0);
		if(branchsalerModel==null){
			// res 1 分会不存在
			resMap.put("res",1);
			return  resMap;
		}
		boolean flag=false;
		for (String phone: arrs) {
			params.put("phone",phone);
			User user=	generalUserDao.selectByPhone(params);
			if(user==null){
				// 手机号不存在 分会不存在
				resMap.put("res",2);
				resMap.put("phone",phone);
				return resMap;
			}
			if(user!=null&&user.getBranchsaler()==branchsaler){
				// 手机号分会与被修改分会一直
				resMap.put("res",3);
				resMap.put("phone",phone);
				return resMap;
			}
			flag=true;
		}
		if(flag){
			for (String phone: arrs) {
				params.put("phone",phone);
				User user=	generalUserDao.selectByPhone(params);

				UserBranchsalerChange userBranchsalerChange = new UserBranchsalerChange();
				userBranchsalerChange.setAdminId(1);
				userBranchsalerChange.setType(1);
				userBranchsalerChange.setUserId(user.getId());
				userBranchsalerChange.setNewBranchsalerId(branchsaler);
				userBranchsalerChange.setOldBranchsalerId(user.getBranchsaler());
				userBranchsalerChange.setCreatetime(new Date());
				userBranchsalerChangeDao.insertSelective(userBranchsalerChange);
				user.setBranchsaler(branchsaler);
				generalUserDao.updateByPrimaryKeySelective(user);
			}
		}
		return resMap;
	}

}
