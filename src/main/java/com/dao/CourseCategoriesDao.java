package com.dao;

import com.entity.CourseCategoriesEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.KechengleibieVO;
import com.entity.view.CourseCategoriesView;


/**
 * 课程类别
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
public interface CourseCategoriesDao extends BaseMapper<CourseCategoriesEntity> {
	
	List<KechengleibieVO> selectListVO(@Param("ew") Wrapper<CourseCategoriesEntity> wrapper);
	
	KechengleibieVO selectVO(@Param("ew") Wrapper<CourseCategoriesEntity> wrapper);
	
	List<CourseCategoriesView> selectListView(@Param("ew") Wrapper<CourseCategoriesEntity> wrapper);

	List<CourseCategoriesView> selectListView(Pagination page, @Param("ew") Wrapper<CourseCategoriesEntity> wrapper);

	
	CourseCategoriesView selectView(@Param("ew") Wrapper<CourseCategoriesEntity> wrapper);
	

}
