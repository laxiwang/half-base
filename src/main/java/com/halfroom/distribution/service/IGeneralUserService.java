package com.halfroom.distribution.service;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import com.halfroom.distribution.common.page.PageInfoBT;
import com.halfroom.distribution.persistence.model.User;

/**
 * 普通用户服务
 * @author tingyunjava
 *
 */
@SuppressWarnings("all")
public interface IGeneralUserService {
   /**
   * 用户信息
   * @param id
   * @param model
   */
   User userInfo(Integer id, Model model);
   /***
   * 用户列表
   * @param condition
   * @param beginTime
   * @param endTime
   * @return
   */
   List<Map<String, Object>> userList(String condition,Integer inUserid,String beginTime,String endTime,Integer userStatus);
   
   PageInfoBT<Map<String, Object>> userListPage(Integer offset,Integer limit,String sort,String order,String condition,Integer inUserid,String beginTime,String endTime,Integer userStatus);
   
   void exportUserListPage(HttpServletResponse response,ServletOutputStream outputStream,Integer offset,Integer limit,String sort,String order,String condition,Integer inUserid,String beginTime,String endTime,Integer userStatus);
   /**
    * 统计
    * @param condition
    * @param beginTime
    * @param endTime
    * @return
    */
   Map<String,Object>  generalUserStatistics(String condition, String beginTime, String endTime);
   
   /**
    * 用户播放记录
    * @param userid
    * @param beginTime
    * @param endTime
    * @return
    */
   List<Map<String, Object>> playRecord(Integer userid,String beginTime,String endTime);
   
   User insertUser(User user);
  /**
   * 用户增加积分
   * @param userid
   * @param integral
   * @return
   */
   int  addintegralForUser(Integer userid,Integer integral);
   /**
    * 开通会员
    * @param userId
    * @param book
    * @return
    */
   int openMember( Integer userId,Integer book);
   
   /**
    * 销售统计列表
    * @param status
    * @param beginTime
    * @param endTime
    * @return
    */
   List<Map<String, Object>> salerList(String condition,Integer status,String beginTime,String endTime);
   
   void exprotSalerList(HttpServletResponse response,ServletOutputStream outputStream,String condition,Integer status,String beginTime,String endTime);
   
   	/**
   	 * 推广统计列表
   	 * @param condition
   	 * @return
   	 */
    PageInfoBT   extendsListPage(Integer offset,Integer limit,String condition);
    /**
     * 无归属会员统计
     * @return
     */
    //@Cacheable(value = Cache.DEFAULTCACHE , key = "'" + CacheKey.OTHERNEESS_USER + "'+#branchsaler")
    List  othernessUserStatistics(Integer branchsaler);
    /**
     * 无归属会员导出
     * @param branchsaler
     */
    void  exportothernessUserStatistics(HttpServletResponse response,ServletOutputStream outputStream,Integer branchsaler);
    /**
     * 无归属订单统计
     * @return
     */
    //@Cacheable(value = Cache.DEFAULTCACHE , key = "'" + CacheKey.OTHERNEESS_ORDER + "'+#branchsaler")
    List  othernessOrderStatistics(Integer branchsaler);
    
    /**
     * 无归属订单导出
     * @param branchsaler
     */
    void  exportothernessOrderStatistics(HttpServletResponse response,ServletOutputStream outputStream,Integer branchsaler);
    /**
     * 新增用户统计
     * @return
     */
    List<Map<String, Object>> userNewlyStatistics(String beginTime,String endTime);
    /**
     * 活跃用户统计
     * @param beginTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> userActive(String beginTime,String endTime);

    /**
     * 查询用户课程状态列表
     * @param userId
     * @return
     */
    List<Map<String, Object>> userBooksStatus(Integer userId);

    /**
     * 推广大使积分统计
     * @param beginTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> userIntegra(String beginTime,String endTime);

    /**
     * 批量修改用户分会
     * @param arr  手机号数组 字符串
     * @param branchsaler
     * @return
     */
    Map<String,Object> batchuserChangeBranchsaler(String arr,Integer branchsaler);
}
