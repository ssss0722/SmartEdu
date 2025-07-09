package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ExamRecordDao;
import com.dao.HomeworkRecordDao;
import com.entity.CourseHomeworkEntity;
import com.entity.ExamRecordEntity;
import com.entity.HomeworkRecordEntity;
import com.entity.view.ExamRecordView;
import com.entity.view.HomeworkRecordView;
import com.service.CourseHomeworkService;
import com.service.ExamRecordService;
import com.service.HomeworkRecordService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("homeworkRecordService")
public class HomeworkRecordServiceImpl extends ServiceImpl<HomeworkRecordDao, HomeworkRecordEntity> implements HomeworkRecordService {

    @Autowired
    private CourseHomeworkService courseHomeworkService;

    @Override
    public PageUtils queryPageGroupBy(Map<String, Object> params, Wrapper wrapper) {
        Page<HomeworkRecordView> page =new Query<HomeworkRecordView>(params).getPage();
        page.setRecords(baseMapper.selectGroupBy(page,wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }

    @Override
    public PageUtils queryPageOptionsNum(Map<String, Object> params, Wrapper wrapper) {
        Page<HomeworkRecordView> page =new Query<HomeworkRecordView>(params).getPage();
        page.setRecords(baseMapper.selectOptionsNum(page,wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper wrapper) {
        Page<HomeworkRecordView> page =new Query<HomeworkRecordView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }

    @Override
    public List<HomeworkRecordView> selectListView(EntityWrapper<HomeworkRecordEntity> ew) {
        return baseMapper.selectListView(ew);
    }

    @Override
    public HomeworkRecordView selectView(EntityWrapper<HomeworkRecordEntity> ew) {
        return baseMapper.selectView(ew);
    }

    @Override
    public int calculateTotalScore(String sUsername, Long homeworkId) {
        Map<String, Object> params = new HashMap<>();
        params.put("s_username", sUsername);
        params.put("homework_id", homeworkId);
        int totalScore = baseMapper.selectTotalScore(params);
        return totalScore;
    }

    @Override
    public List<Map<String, Object>> selectTeacherHomework(String teacherUsername) {
        return baseMapper.selectTeacherHomework(teacherUsername);
    }
}
