package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.ExamEntity;
import com.entity.view.ExamView;
import com.entity.vo.ExamVO;
import com.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExamService extends IService<ExamEntity> {
    /**
     * 基础分页查询（不带条件）
     */
    PageUtils queryPage(Map<String, Object> params);
    /**
     * 分页带条件查询（Wrapper 条件构造器）
     */
    PageUtils queryPage(Map<String, Object> params, Wrapper<ExamEntity> wrapper);
    /**
     * 返回 VO 列表（视图对象）
     */
    List<ExamVO> selectListVO(Wrapper<ExamEntity> wrapper);
    /**
     * 返回单个 VO
     */
    ExamVO selectVO(@Param("ew") Wrapper<ExamEntity> wrapper);
    /**
     * 返回视图列表
     */
    List<ExamView> selectListView(Wrapper<ExamEntity> wrapper);
    /**
     * 返回单个视图对象
     */
    ExamView selectView(@Param("ew") Wrapper<ExamEntity> wrapper);
}
