package com.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.CourseStudentDao;
import com.entity.CourseStudentEntity;
import com.service.CourseStudentService;
import org.springframework.stereotype.Service;

@Service("CourseStudentService")
public class CourseStudentServiceImpl extends ServiceImpl<CourseStudentDao, CourseStudentEntity> implements CourseStudentService {
}
