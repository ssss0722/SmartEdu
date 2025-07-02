package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.HomeworkRecordEntity;
import com.utils.PageUtils;

import java.util.Map;

public interface HomeworkRecordService extends IService<HomeworkRecordEntity> {
    PageUtils queryPageGroupBy(Map<String, Object> params, Wrapper wrapper);
}
