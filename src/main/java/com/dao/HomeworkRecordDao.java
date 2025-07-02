package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.ExamRecordEntity;
import com.entity.HomeworkRecordEntity;
import com.entity.view.HomeworkRecordView;

import java.util.List;

public interface HomeworkRecordDao extends BaseMapper<HomeworkRecordEntity> {
    List<HomeworkRecordView> selectGroupBy(Page<HomeworkRecordView> page, Wrapper wrapper);
}
