package com.halfroom.distribution.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.halfroom.distribution.persistence.model.Book;
import com.halfroom.distribution.persistence.model.User;
import com.halfroom.distribution.persistence.model.UserBook;
import com.halfroom.distribution.persistence.model.UserOrder;

public interface GeneralUserDao  extends BaseMapper<User>{
	   List<Map<String, Object>> selectUsersPage(Map<String,Object>  map,@Param("page")Page page);
	   List<Map<String, Object>> selectUsers(Map<String,Object>  map);
	   
	   User selectByid(Integer id);
	   User selectByPhone(Map<String, Object> params);
	   
	   int setStatus(@Param("userId") Integer userId, @Param("status") int status);
	   
	    
	   //总用户  flag 是否统计包含下级  true 包含 false 不包含
	   Integer   countGeneralUser(Map<String,Object>  map);
	   //已付费  --flag
	   Integer   paidGeneralUser(Map<String,Object>  map);
	   //实体卡  --flag
	   Integer   cardGeneralUser(Map<String,Object>  map);
	   //体验  --flag
	   Integer   supGeneralUser(Map<String,Object>  map);
	   //体验过期--flag
	   Integer   supedGeneralUser(Map<String,Object>  map);
	   
	   //知识送礼 --flag
	   Integer    giftGiving(Map<String,Object>  map);
      //积分兑换 --flag
	   Integer    exchange(Map<String,Object>  map);
	   //赠卡 --flag
	   Integer    cardGiving(Map<String,Object>  map);
	   Integer  updateByPrimaryKeySelective(User user);
	   //体验用户 或者过期用户 --flag
	   UserBook   experienceGeneralUser(Map<String, Object> map);
	   //付费用户--flag
	   UserOrder  payedGeneralUser(Map<String, Object> map);
	   //播放记录
	   List<Map<String, Object>> playRecord(Map<String, Object> map);
	   
	   void insertUser(User user);
	   
	   //查询所有已上架课程
	   List<Book>  selectOnSelveBooks();

	   //添加用户课程关联
	   void insertUserBook(UserBook userBook);
	   
	   //销售大使统计列表
	   List<Map<String, Object>> selectSalersStatistics(Map<String, Object> map);
	   
	   //推广大使统计列表
	   List<Map<String, Object>> selectExtendsStatistics(Map<String, Object> map,@Param("page")Page page);
	   
	   //分会下的用户分布统计市   
	   List<Map<String, Object>> selectCityBybid (Map<String, Object> map);

	   List<Map<String, Object>> selectCity ();
	   List<Map<String, Object>> selectProvince ();
	   //查询用户最近一次播放记录
	   String selectPlayone(@Param("userId") long userId);
	   
	   //活跃用户
	   Integer selectActiveNum(Map<String, Object> map);

	   //查询用户课程
	   List<Map<String, Object>>  selectUserBookStatus(Map<String, Object> map);

	   //用户积分统计
	   List<Map<String, Object>>  selectUserIntegraList(Map<String, Object> map);

	   //分会会长播放记录排名
	   List<Map<String, Object>>  selectBranchsalerUserPlay(Map<String, Object> map);
}
