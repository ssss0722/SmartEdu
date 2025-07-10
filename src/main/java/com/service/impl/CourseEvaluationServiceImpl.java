package com.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.CourseEvaluationDao;
import com.entity.CourseEvaluationEntity;
import com.service.CourseEvaluationService;
import org.springframework.stereotype.Service;

@Service("CourseEvaluationService")

public class CourseEvaluationServiceImpl extends ServiceImpl<CourseEvaluationDao, CourseEvaluationEntity> implements CourseEvaluationService{
}
