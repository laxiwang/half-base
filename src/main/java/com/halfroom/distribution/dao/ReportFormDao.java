package com.halfroom.distribution.dao;

import java.util.List;
import java.util.Map;

public interface ReportFormDao {
    /**注册用户数 --注册时间**/
    Integer  selectRegisterUserNum(Map<String,Object> params);
    /***付费用户数   isCard  是否包含实体卡   isRepeat  是否计算复购  --订单时间**/
    List<Map<String,Object>> selectPayUserNumOrderTime(Map<String,Object> params);

    List<Map<String,Object>> selectPayUserNumUserTime(Map<String,Object> params);

    /***活跃用户 --听课时间**/
    Integer selectActiveNum(Map<String, Object> params);
    /***活跃用户注册 --听课时间**/
    Integer selectActiveNum2(Map<String, Object> params);
    /***订单数量  isCard  是否包含实体卡 paysource 来源   fee金额    --订单时间**/
    Integer selectOrderNum(Map<String, Object> params);
    //体验用户 至少播放过一次的
    Integer   supGeneralUser(Map<String,Object>  params);

}
