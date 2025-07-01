package com.dao;

import com.entity.DiscussCourseHomeworkEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.DiscusskechengzuoyeVO;
import com.entity.view.DiscussCourseHomeworkView;


/**
 * 课程作业评论表
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:25
 */
public interface DiscussCourseHomeworkDao extends BaseMapper<DiscussCourseHomeworkEntity> {
	
	List<DiscusskechengzuoyeVO> selectListVO(@Param("ew") Wrapper<DiscussCourseHomeworkEntity> wrapper);
	
	DiscusskechengzuoyeVO selectVO(@Param("ew") Wrapper<DiscussCourseHomeworkEntity> wrapper);
	
	List<DiscussCourseHomeworkView> selectListView(@Param("ew") Wrapper<DiscussCourseHomeworkEntity> wrapper);

	List<DiscussCourseHomeworkView> selectListView(Pagination page, @Param("ew") Wrapper<DiscussCourseHomeworkEntity> wrapper);

	
	DiscussCourseHomeworkView selectView(@Param("ew") Wrapper<DiscussCourseHomeworkEntity> wrapper);
	

}
