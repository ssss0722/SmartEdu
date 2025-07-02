package com.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ExamRecordEntity;
import com.entity.HomeworkRecordEntity;
import com.service.HomeworkRecordService;
import com.service.StudentService;
import com.service.TeacherService;
import com.utils.JwtUtils;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/homeworkRecord")
public class HomeworkRecordController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private HomeworkRecordService homeworkRecordService;

    /**
     * 作业记录接口
     */
    @RequestMapping("/groupby")
    public R page2(@RequestParam Map<String, Object> params, HomeworkRecordEntity homeworkRecord,String token){
        Long id= JwtUtils.getUserIdFromToken(token);
        String role=JwtUtils.getRoleFromToken(token);
        if("teacher".equals(role)) {
            homeworkRecord.settUsername(teacherService.selectById(id).getT_username());
        }
        else {
            if(!"管理员".equals(role)) {
                homeworkRecord.setsUsername(studentService.selectById(id).getsUsername());
            }
        }
        EntityWrapper<HomeworkRecordEntity> ew = new EntityWrapper<>();
        PageUtils page = homeworkRecordService.queryPageGroupBy(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, homeworkRecord), params), params));
        return R.ok().put("data", page);
    }

}
