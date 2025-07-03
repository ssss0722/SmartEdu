package com.dao;

import com.entity.ExamRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

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
public interface ExamRecordDao extends BaseMapper<ExamRecordEntity> {
	
	List<ExamRecordVO> selectListVO(@Param("ew") Wrapper<ExamRecordEntity> wrapper);
	
	ExamRecordVO selectVO(@Param("ew") Wrapper<ExamRecordEntity> wrapper);
	
	List<ExamRecordView> selectListView(@Param("ew") Wrapper<ExamRecordEntity> wrapper);

	List<ExamRecordView> selectListView(Pagination page, @Param("ew") Wrapper<ExamRecordEntity> wrapper);

	
	ExamRecordView selectView(@Param("ew") Wrapper<ExamRecordEntity> wrapper);
	
	List<ExamRecordView> selectGroupBy(Pagination page, @Param("ew") Wrapper<ExamRecordEntity> wrapper);

    List<ExamRecordView> selectOptionsNum(Pagination page, @Param("ew") Wrapper<ExamRecordEntity> wrapper);

}
