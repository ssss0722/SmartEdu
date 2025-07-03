package com.dao;

import com.entity.ExamQuestionEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ExamQuestionVO;
import com.entity.view.ExamQuestionView;


/**
 * 试题表
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface ExamQuestionDao extends BaseMapper<ExamQuestionEntity> {
	
	List<ExamQuestionVO> selectListVO(@Param("ew") Wrapper<ExamQuestionEntity> wrapper);
	
	ExamQuestionVO selectVO(@Param("ew") Wrapper<ExamQuestionEntity> wrapper);
	
	List<ExamQuestionView> selectListView(@Param("ew") Wrapper<ExamQuestionEntity> wrapper);

	List<ExamQuestionView> selectListView(Pagination page, @Param("ew") Wrapper<ExamQuestionEntity> wrapper);

	
	ExamQuestionView selectView(@Param("ew") Wrapper<ExamQuestionEntity> wrapper);
	

}
