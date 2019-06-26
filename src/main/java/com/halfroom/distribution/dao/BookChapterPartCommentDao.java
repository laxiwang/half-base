package com.halfroom.distribution.dao;


import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BookChapterPartCommentDao {
   List<Map<String,Object>> list(Map<String,Object> map,@Param("page") Page page);
}