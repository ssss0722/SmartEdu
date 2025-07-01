package com.dao;

import com.entity.CourseVideoEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.JiaoxueshipinVO;
import com.entity.view.CourseVideoView;


/**
 * 教学视频
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
public interface CourseVideoDao extends BaseMapper<CourseVideoEntity> {
	
	List<JiaoxueshipinVO> selectListVO(@Param("ew") Wrapper<CourseVideoEntity> wrapper);
	
	JiaoxueshipinVO selectVO(@Param("ew") Wrapper<CourseVideoEntity> wrapper);
	
	List<CourseVideoView> selectListView(@Param("ew") Wrapper<CourseVideoEntity> wrapper);

	List<CourseVideoView> selectListView(Pagination page, @Param("ew") Wrapper<CourseVideoEntity> wrapper);

	
	CourseVideoView selectView(@Param("ew") Wrapper<CourseVideoEntity> wrapper);
	

}
