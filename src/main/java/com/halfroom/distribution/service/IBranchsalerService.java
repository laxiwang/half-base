package com.halfroom.distribution.service;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 分会服务
 */
public interface IBranchsalerService {

    /**
     * 删除分会
     */
   void deleteDept(Integer deptId);
   /**
    * 渠道分会订单统计展示
    * @param condition
    * @return
    */
   List<Map<String, Object>> agentOrderlist(String beginTime,String endTime,String condition);

   /**
    * 导出渠道分会订单统计展示
    * @param response
    * @param outputStream
    * @param beginTime
    * @param endTime
    * @param condition
    */
   void   agentOrderlistExecl(HttpServletResponse response, ServletOutputStream outputStream, String beginTime, String endTime, String condition);

   /**
    * 导出渠道分会分润记录展示
    * @param response
    * @param outputStream
    * @param beginTime
    * @param endTime
    * @param condition
    */
   void   agentChangelistExecl(HttpServletResponse response, ServletOutputStream outputStream, String beginTime, String endTime, String condition);
   /**
    * 渠道分会分润记录展示
    * @param condition
    * @return
    */
   List<Map<String, Object>> agentAccountChangelist(String beginTime,String endTime,String condition);
   /**
    * 下级分会用户数统计
    * @return
    */
   List<Map<String, Object>> agentUserNum(String beginTime,String endTime,String condition);

   void  exportAgentUserNum(HttpServletResponse response, ServletOutputStream outputStream,String beginTime,String endTime,String condition);
   /**
    * 验证 branchsaler2 是否为branchsaler1 的直属分会 
    * @param branchsaler1
    * @param branchsaler2
    * @return
    */
   boolean   branchsalerDirectlyUnder(Integer branchsaler1,Integer branchsaler2);

   /**
    * 验证两个分会是否在同一个体系中
    * @param branchsaler1
    * @param branchsaler2
    * @return
    */
   boolean   branchsalerOnTeam(Integer branchsaler1,Integer branchsaler2);

   /**
    * 下级分会 分会数量统计
    * @param beginTime
    * @param endTime
    * @param condition
    * @return
    */
   List<Map<String, Object>> agentBranchSalerNumList(String beginTime,String endTime,String condition);

   void exportAgentBranchSalerNumList(HttpServletResponse response, ServletOutputStream outputStream,String beginTime,String endTime,String condition);

   /**
    * 活动订单统计
    * @param timeType 期数
    * @param level 分会等级
    * @return
    */
   List<Map<String, Object>> orderActivityList(Integer timeType,Integer level);
}
