package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.CourseHomeworkQuestionDao;
import com.entity.HomeworkQuestionEntity;
import com.entity.view.CourseHomeworkQuestionView;
import com.entity.view.CourseHomeworkReviewView;
import com.service.CourseHomeworkQuestionService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CourseHomeworkQuestionService")
public class CourseHomeworkQuestionServiceImpl extends ServiceImpl<CourseHomeworkQuestionDao, HomeworkQuestionEntity> implements CourseHomeworkQuestionService {
    @Override
    public PageUtils queryPage(Map<String, Object> params, EntityWrapper<HomeworkQuestionEntity> wrapper) {
        Page<CourseHomeworkQuestionView> page =new Query<CourseHomeworkQuestionView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }

    @Override
    public List<HomeworkQuestionEntity> selectListView(EntityWrapper<HomeworkQuestionEntity> wrapper) {
        return baseMapper.selectListView(wrapper);
    }

    @Override
    public CourseHomeworkQuestionView selectView(EntityWrapper<HomeworkQuestionEntity> ew) {
        return baseMapper.selectView(ew);
    }
}
