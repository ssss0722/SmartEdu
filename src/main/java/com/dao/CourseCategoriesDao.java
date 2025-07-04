package com.dao;

import com.entity.CourseCategoriesEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.entity.vo.CourseCategoriesVO;
import com.entity.view.CourseCategoriesView;
import org.apache.ibatis.annotations.Select;


/**
 * 课程类别
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
@Mapper
public interface CourseCategoriesDao extends BaseMapper<CourseCategoriesEntity> {
	
	List<CourseCategoriesVO> selectListVO(@Param("ew") Wrapper<CourseCategoriesEntity> wrapper);

	CourseCategoriesVO selectVO(@Param("ew") Wrapper<CourseCategoriesEntity> wrapper);

	List<CourseCategoriesView> selectListView(@Param("ew") Wrapper<CourseCategoriesEntity> wrapper);

	List<CourseCategoriesView> selectListView(Pagination page, @Param("ew") Wrapper<CourseCategoriesEntity> wrapper);


	CourseCategoriesView selectView(@Param("ew") Wrapper<CourseCategoriesEntity> wrapper);


	@Select("SELECT cc.id, cc.course,cc.addtime " +
			"FROM course_categories cc " +
			"INNER JOIN course_teacher ct ON cc.id = ct.course_id " +
			"INNER JOIN user_teacher ut ON ct.t_username = ut.t_username " +
			"WHERE ut.t_username = #{tUsername}")
	List<CourseCategoriesEntity> selectByTeacher(String tUsername);
}
