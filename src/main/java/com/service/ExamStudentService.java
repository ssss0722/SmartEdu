package com.service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.ExamStudentEntity;
import com.entity.view.ExamStudentView;
import com.entity.vo.ExamStudentVO;
import com.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 考试学生关联表服务接口
 */
public interface ExamStudentService extends IService<ExamStudentEntity> {

    /**
     * 基础分页查询（不带条件）
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 分页带条件查询
     */
    PageUtils queryPage(Map<String, Object> params, Wrapper<ExamStudentEntity> wrapper);

    /**
     * 返回 VO 列表
     */
    List<ExamStudentVO> selectListVO(Wrapper<ExamStudentEntity> wrapper);

    /**
     * 返回单个 VO
     */
    ExamStudentVO selectVO(@Param("ew") Wrapper<ExamStudentEntity> wrapper);

    /**
     * 返回视图列表
     */
    List<ExamStudentView> selectListView(Wrapper<ExamStudentEntity> wrapper);

    /**
     * 返回单个视图对象
     */
    ExamStudentView selectView(@Param("ew") Wrapper<ExamStudentEntity> wrapper);

    /**
     * 根据考试ID获取关联学生实体列表（Entity）
     */
    List<ExamStudentEntity> getByExamId(Long examId);
}
