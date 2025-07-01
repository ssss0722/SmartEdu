package com.dao;

import com.entity.CourseHomeworkSubmissionEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ZuoyetijiaoVO;
import com.entity.view.CourseHomeworkSubmissionView;


/**
 * 作业提交
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface CourseHomeworkSubmissionDao extends BaseMapper<CourseHomeworkSubmissionEntity> {
	
	List<ZuoyetijiaoVO> selectListVO(@Param("ew") Wrapper<CourseHomeworkSubmissionEntity> wrapper);
	
	ZuoyetijiaoVO selectVO(@Param("ew") Wrapper<CourseHomeworkSubmissionEntity> wrapper);
	
	List<CourseHomeworkSubmissionView> selectListView(@Param("ew") Wrapper<CourseHomeworkSubmissionEntity> wrapper);

	List<CourseHomeworkSubmissionView> selectListView(Pagination page, @Param("ew") Wrapper<CourseHomeworkSubmissionEntity> wrapper);

	
	CourseHomeworkSubmissionView selectView(@Param("ew") Wrapper<CourseHomeworkSubmissionEntity> wrapper);
	

}
