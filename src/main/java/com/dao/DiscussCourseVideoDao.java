package com.dao;

import com.entity.DiscussCourseVideoEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.DiscussjiaoxueshipinVO;
import com.entity.view.DiscussCourseVideoView;


/**
 * 教学视频评论表
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface DiscussCourseVideoDao extends BaseMapper<DiscussCourseVideoEntity> {
	
	List<DiscussjiaoxueshipinVO> selectListVO(@Param("ew") Wrapper<DiscussCourseVideoEntity> wrapper);
	
	DiscussjiaoxueshipinVO selectVO(@Param("ew") Wrapper<DiscussCourseVideoEntity> wrapper);
	
	List<DiscussCourseVideoView> selectListView(@Param("ew") Wrapper<DiscussCourseVideoEntity> wrapper);

	List<DiscussCourseVideoView> selectListView(Pagination page, @Param("ew") Wrapper<DiscussCourseVideoEntity> wrapper);

	
	DiscussCourseVideoView selectView(@Param("ew") Wrapper<DiscussCourseVideoEntity> wrapper);
	

}
