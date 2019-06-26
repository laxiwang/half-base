package com.halfroom.distribution.service;

import com.halfroom.distribution.common.page.PageInfoBT;

import java.util.List;
import java.util.Map;

/**
 * 评论服务
 */
public interface IPartCommentService {
    /***评论列表**/
    PageInfoBT<Map<String, Object>> list(Integer offset,Integer limit,String sort,String order,String condition,Integer type,Integer hot,Integer reply,String beginTime,String endTime);
}
