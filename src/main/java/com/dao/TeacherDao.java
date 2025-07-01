package com.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.entity.TeacherEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.TeacherVO;
import com.entity.view.TeacherView;


/**
 * 教师
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
public interface TeacherDao extends BaseMapper<TeacherEntity> {
	
	List<TeacherVO> selectListVO(@Param("ew") Wrapper<TeacherEntity> wrapper);
	
	TeacherVO selectVO(@Param("ew") Wrapper<TeacherEntity> wrapper);
	
	List<TeacherView> selectListView(@Param("ew") Wrapper<TeacherEntity> wrapper);

	List<TeacherView> selectListView(Pagination page, @Param("ew") Wrapper<TeacherEntity> wrapper);

	
	TeacherView selectView(@Param("ew") Wrapper<TeacherEntity> wrapper);

	Page<TeacherView> selectViewPage(Page<TeacherView> page, @Param("ew") Wrapper<TeacherView> wrapper);
	

}
