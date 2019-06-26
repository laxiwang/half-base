package com.halfroom.distribution.service;

import com.halfroom.distribution.core.util.DateTimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 图形相关服务
 */
public interface IEchartsService {
    /**
     * 日新增用户 维度最大31天
     * @param beginTime
     * @param endTime
     * @return
     */
    Map<String,Object>  userNewsEchartsDay(String beginTime,String endTime);

}
