package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.dto.ExamExportDTO;
import com.entity.ExamEntity;
import com.entity.view.ExamView;
import com.entity.vo.ExamVO;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ExamDao extends BaseMapper<ExamEntity> {

  List<ExamVO> selectListVO(@Param("ew") Wrapper<ExamEntity> wrapper);

    ExamVO selectVO(@Param("ew") Wrapper<ExamEntity> wrapper);

    List<ExamView> selectListView(@Param("ew") Wrapper<ExamEntity> wrapper);

    List<ExamView> selectListView(Pagination page, @Param("ew") Wrapper<ExamEntity> wrapper);

    ExamView selectView(@Param("ew") Wrapper<ExamEntity> wrapper);

    List<ExamExportDTO> exportExamResult(@Param("examName") String examName);

}
