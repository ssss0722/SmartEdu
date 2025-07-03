package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.ExamStudentEntity;
import com.entity.view.ExamStudentView;
import com.entity.vo.ExamStudentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamStudentDao extends BaseMapper<ExamStudentEntity> {
    /**
     * 获取 VO 列表
     */
    List<ExamStudentVO> selectListVO(@Param("ew") Wrapper<ExamStudentEntity> wrapper);

    /**
     * 获取单个 VO
     */
    ExamStudentVO selectVO(@Param("ew") Wrapper<ExamStudentEntity> wrapper);

    /**
     * 获取视图列表
     */
    List<ExamStudentView> selectListView(@Param("ew") Wrapper<ExamStudentEntity> wrapper);

    /**
     * 分页获取视图列表
     */
    List<ExamStudentView> selectListView(Pagination page, @Param("ew") Wrapper<ExamStudentEntity> wrapper);

    /**
     * 获取单个视图对象
     */
    ExamStudentView selectView(@Param("ew") Wrapper<ExamStudentEntity> wrapper);
}
