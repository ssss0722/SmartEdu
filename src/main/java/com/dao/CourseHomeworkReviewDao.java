package com.dao;

import com.entity.CourseHomeworkReviewEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ZuoyepigaiVO;
import com.entity.view.CourseHomeworkReviewView;


/**
 * 作业批改
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface CourseHomeworkReviewDao extends BaseMapper<CourseHomeworkReviewEntity> {
	
	List<ZuoyepigaiVO> selectListVO(@Param("ew") Wrapper<CourseHomeworkReviewEntity> wrapper);
	
	ZuoyepigaiVO selectVO(@Param("ew") Wrapper<CourseHomeworkReviewEntity> wrapper);
	
	List<CourseHomeworkReviewView> selectListView(@Param("ew") Wrapper<CourseHomeworkReviewEntity> wrapper);

	List<CourseHomeworkReviewView> selectListView(Pagination page, @Param("ew") Wrapper<CourseHomeworkReviewEntity> wrapper);

	
	CourseHomeworkReviewView selectView(@Param("ew") Wrapper<CourseHomeworkReviewEntity> wrapper);
	

    List<Map<String, Object>> selectValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<CourseHomeworkReviewEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<CourseHomeworkReviewEntity> wrapper);

    List<Map<String, Object>> selectGroup(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<CourseHomeworkReviewEntity> wrapper);



}
