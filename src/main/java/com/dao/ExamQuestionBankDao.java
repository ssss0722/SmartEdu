package com.dao;

import com.entity.ExamQuestionBankEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.entity.vo.ExamQuestionbankVO;
import com.entity.view.ExamQuestionBankView;


/**
 * 试题库表
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@Mapper
public interface ExamQuestionBankDao extends BaseMapper<ExamQuestionBankEntity> {
	
	List<ExamQuestionbankVO> selectListVO(@Param("ew") Wrapper<ExamQuestionBankEntity> wrapper);
	
	ExamQuestionbankVO selectVO(@Param("ew") Wrapper<ExamQuestionBankEntity> wrapper);
	
	List<ExamQuestionBankView> selectListView(@Param("ew") Wrapper<ExamQuestionBankEntity> wrapper);

	List<ExamQuestionBankView> selectListView(Pagination page, @Param("ew") Wrapper<ExamQuestionBankEntity> wrapper);

	ExamQuestionBankView selectView(@Param("ew") Wrapper<ExamQuestionBankEntity> wrapper);

}
