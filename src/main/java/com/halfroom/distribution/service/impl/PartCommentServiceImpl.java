package com.halfroom.distribution.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.halfroom.distribution.common.page.PageInfoBT;
import com.halfroom.distribution.core.util.DateTimeUtil;
import com.halfroom.distribution.dao.BookChapterPartCommentDao;
import com.halfroom.distribution.service.IPartCommentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class PartCommentServiceImpl implements IPartCommentService {
    @Resource
    private BookChapterPartCommentDao partCommentDao;
    @Override
    public PageInfoBT<Map<String, Object>> list(Integer offset,Integer limit,String sort,String order,String condition,Integer type,Integer hot,Integer reply,String beginTime,String endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("condition", condition);
        if (StringUtils.isNotBlank(beginTime)) {
            map.put("beginTime", beginTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            map.put("endTime", endTime+" 23:59:59");
        }
        if (StringUtils.isNotBlank(sort)) {
            map.put("sort", sort);
        }
        if (StringUtils.isNotBlank(order)) {
            map.put("order", order);
        }
        if (type!=null) {
            map.put("type", type);
        }
        if (hot!=null) {
            map.put("hot", hot);
        }
        if (reply!=null) {
            map.put("reply", reply);
        }
        Page page = new Page<>(offset/limit+1,limit);
        List<Map<String, Object>> list=partCommentDao.list(map,page);
        page.setRecords(list);
        PageInfoBT resPage = new PageInfoBT(page);
        return resPage;
    }
}
