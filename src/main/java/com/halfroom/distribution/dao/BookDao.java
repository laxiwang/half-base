package com.halfroom.distribution.dao;

import com.halfroom.distribution.persistence.model.BookChapterPartComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BookDao {
	//展示所有课程的视频
	List<Map<String,Object>>   selectPartVideos(Map<String,Object>  map);

	void updateCommentHot(@Param("id") Integer id,@Param("host") Integer host);

	String selectResById(Integer id);
}
