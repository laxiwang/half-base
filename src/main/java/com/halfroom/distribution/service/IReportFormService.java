package com.halfroom.distribution.service;

import java.util.List;
import java.util.Map;

/**
 *  报表服务
 */
public interface IReportFormService {

    List<Map<String,Object>> reportFormUser(String condition, String beginTime, String endTime);

    List<Map<String,Object>> reportFormOrder(String condition, String beginTime, String endTime);
}
