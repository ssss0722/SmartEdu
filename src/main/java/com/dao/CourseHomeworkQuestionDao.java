package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.HomeworkQuestionEntity;
import com.entity.view.CourseHomeworkQuestionView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseHomeworkQuestionDao extends BaseMapper<HomeworkQuestionEntity> {

    List<CourseHomeworkQuestionView> selectListView(Pagination page, @Param("ew") Wrapper<HomeworkQuestionEntity> wrapper);

    List<HomeworkQuestionEntity> selectListView(@Param("ew") EntityWrapper<HomeworkQuestionEntity> wrapper);

    CourseHomeworkQuestionView selectView(@Param("ew")EntityWrapper<HomeworkQuestionEntity> ew);
}
