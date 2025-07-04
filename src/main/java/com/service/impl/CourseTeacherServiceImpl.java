package com.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.CourseMaterialDao;
import com.dao.CourseTeacherDao;
import com.entity.CourseMaterialEntity;
import com.entity.CourseTeacherEntity;
import com.service.CourseMaterialService;
import com.service.CourseTeacherService;
import org.springframework.stereotype.Service;

@Service("CourseTeacherService")
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherDao, CourseTeacherEntity> implements CourseTeacherService {
}
