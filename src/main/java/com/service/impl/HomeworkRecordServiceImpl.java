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
        // 1. 检查所有题目是否都已批改
        int totalQuestions = selectCount(new EntityWrapper<HomeworkRecordEntity>()
                .eq("s_username", sUsername)
                .eq("homework_id", homeworkId));

        int markedQuestions = selectCount(new EntityWrapper<HomeworkRecordEntity>()
                .eq("s_username", sUsername)
                .eq("homework_id", homeworkId)
                .eq("ismark", 1));

        if (totalQuestions == 0 || totalQuestions != markedQuestions) {
            throw new RuntimeException("作业尚未全部批改完成");
        }

        // 2. 计算总分
        Map<String, Object> params = new HashMap<>();
        params.put("s_username", sUsername);
        params.put("homework_id", homeworkId);

        int totalScore = baseMapper.selectTotalScore(params);

        // 3. 获取作业信息
        CourseHomeworkEntity homework = courseHomeworkService.selectById(homeworkId);
        if (homework == null) {
            throw new RuntimeException("找不到对应的作业信息");
        }

        return totalScore;
    }
}
