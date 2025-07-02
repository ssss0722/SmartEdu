package com.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ExamRecordDao;
import com.dao.HomeworkRecordDao;
import com.entity.ExamRecordEntity;
import com.entity.HomeworkRecordEntity;
import com.entity.view.ExamRecordView;
import com.entity.view.HomeworkRecordView;
import com.service.ExamrecordService;
import com.service.HomeworkRecordService;
import com.utils.PageUtils;
import com.utils.Query;

import java.util.Map;

public class HomeworkRecordServiceImpl extends ServiceImpl<HomeworkRecordDao, HomeworkRecordEntity> implements HomeworkRecordService {

    @Override
    public PageUtils queryPageGroupBy(Map<String, Object> params, Wrapper wrapper) {
        Page<HomeworkRecordView> page =new Query<HomeworkRecordView>(params).getPage();
        page.setRecords(baseMapper.selectGroupBy(page,wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }
}
