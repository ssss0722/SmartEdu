package com.dao;

import com.entity.DiscussCourseMaterialEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.DiscussjiaoxueziliaoVO;
import com.entity.view.DiscussCourseMaterialView;


/**
 * 教学资料评论表
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface DiscussCourseMaterialDao extends BaseMapper<DiscussCourseMaterialEntity> {
	
	List<DiscussjiaoxueziliaoVO> selectListVO(@Param("ew") Wrapper<DiscussCourseMaterialEntity> wrapper);
	
	DiscussjiaoxueziliaoVO selectVO(@Param("ew") Wrapper<DiscussCourseMaterialEntity> wrapper);
	
	List<DiscussCourseMaterialView> selectListView(@Param("ew") Wrapper<DiscussCourseMaterialEntity> wrapper);

	List<DiscussCourseMaterialView> selectListView(Pagination page, @Param("ew") Wrapper<DiscussCourseMaterialEntity> wrapper);

	
	DiscussCourseMaterialView selectView(@Param("ew") Wrapper<DiscussCourseMaterialEntity> wrapper);
	

}
