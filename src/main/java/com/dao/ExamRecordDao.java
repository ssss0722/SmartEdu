package com.dao;

import com.entity.ExamQuestionBankEntity;
import com.entity.ExamRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.entity.vo.ExamRecordVO;
import com.entity.view.ExamRecordView;


/**
 * 测试记录表
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@Mapper
public interface ExamRecordDao extends BaseMapper<ExamRecordEntity> {
	List<ExamRecordVO> selectExamRecordDetail(@Param("ew") Wrapper<?> wrapper);

	int markSingleQuestionById(@Param("id") Long id, @Param("score") Long score);

}
