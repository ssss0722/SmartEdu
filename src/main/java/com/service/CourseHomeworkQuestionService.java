package com.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.HomeworkQuestionEntity;
import com.entity.view.CourseHomeworkQuestionView;
import com.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CourseHomeworkQuestionService extends IService<HomeworkQuestionEntity> {
    PageUtils queryPage(Map<String, Object> params, EntityWrapper<HomeworkQuestionEntity> wrapper);

    List<HomeworkQuestionEntity> selectListView(EntityWrapper<HomeworkQuestionEntity> wrapper);

    CourseHomeworkQuestionView selectView(EntityWrapper<HomeworkQuestionEntity> ew);
}
