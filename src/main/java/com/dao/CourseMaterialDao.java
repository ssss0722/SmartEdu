package com.dao;

import com.entity.CourseMaterialEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.CourseMaterialVO;
import com.entity.view.CourseMaterialView;


/**
 * 教学资料
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
public interface CourseMaterialDao extends BaseMapper<CourseMaterialEntity> {
	
	List<CourseMaterialVO> selectListVO(@Param("ew") Wrapper<CourseMaterialEntity> wrapper);
	
	CourseMaterialVO selectVO(@Param("ew") Wrapper<CourseMaterialEntity> wrapper);
	
	List<CourseMaterialView> selectListView(@Param("ew") Wrapper<CourseMaterialEntity> wrapper);

	List<CourseMaterialView> selectListView(Pagination page, @Param("ew") Wrapper<CourseMaterialEntity> wrapper);

	
	CourseMaterialView selectView(@Param("ew") Wrapper<CourseMaterialEntity> wrapper);
	

}
