package com.dao;

import com.entity.CourseHomeworkEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.KechengzuoyeVO;
import com.entity.view.CourseHomeworkView;


/**
 * 课程作业
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface CourseHomeworkDao extends BaseMapper<CourseHomeworkEntity> {
	
	List<KechengzuoyeVO> selectListVO(@Param("ew") Wrapper<CourseHomeworkEntity> wrapper);
	
	KechengzuoyeVO selectVO(@Param("ew") Wrapper<CourseHomeworkEntity> wrapper);
	
	List<CourseHomeworkView> selectListView(@Param("ew") Wrapper<CourseHomeworkEntity> wrapper);

	List<CourseHomeworkView> selectListView(Pagination page, @Param("ew") Wrapper<CourseHomeworkEntity> wrapper);

	
	CourseHomeworkView selectView(@Param("ew") Wrapper<CourseHomeworkEntity> wrapper);
	

}
