package com.dao;

import com.entity.ExamPaperEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ExamPaperVO;
import com.entity.view.ExamPaperView;


/**
 * 在线考试表
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface ExamPaperDao extends BaseMapper<ExamPaperEntity> {
	
	List<ExamPaperVO> selectListVO(@Param("ew") Wrapper<ExamPaperEntity> wrapper);
	
	ExamPaperVO selectVO(@Param("ew") Wrapper<ExamPaperEntity> wrapper);
	
	List<ExamPaperView> selectListView(@Param("ew") Wrapper<ExamPaperEntity> wrapper);

	List<ExamPaperView> selectListView(Pagination page, @Param("ew") Wrapper<ExamPaperEntity> wrapper);

	
	ExamPaperView selectView(@Param("ew") Wrapper<ExamPaperEntity> wrapper);
	

}
