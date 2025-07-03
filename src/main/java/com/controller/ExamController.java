package com.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.dto.ExamRequestDTO;
import com.dto.ExamUpdateDTO;
import com.entity.ExamEntity;
import com.entity.ExamPaperEntity;
import com.entity.ExamStudentEntity;
import com.entity.StudentEntity;
import com.service.ExamService;
import com.service.ExamStudentService;
import com.service.ExampaperService;
import com.service.StudentService;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/exam")
public class ExamController {
    @Autowired
    private ExamService examService;
    @Autowired
    private ExampaperService exampaperService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ExamStudentService examStudentService;
//获取考试列表
    @RequestMapping("/list")
    public R list(@RequestParam(required = false)String name,@RequestParam(required = false)String status){

        // 构建查询条件
        EntityWrapper<ExamEntity> wrapper = new EntityWrapper<>();
        if (name != null && !name.trim().isEmpty()) {
            wrapper.like("name", name);
        }
        if (status != null && !status.trim().isEmpty()) {
            wrapper.eq("status", status);
        }

        List<ExamEntity> exams = examService.selectList(wrapper);
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (ExamEntity exam : exams) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", exam.getId());
            map.put("name", exam.getName());
            map.put("paperId", exam.getPaperId());

            // 查询试卷标题
            ExamPaperEntity paper = exampaperService.selectById(exam.getPaperId());
            map.put("paperTitle", paper != null ? paper.getTitle() : "");
            //查询参加考试的学生列表
            List<ExamStudentEntity>examStudents=examStudentService.selectList(
                    new EntityWrapper<ExamStudentEntity>().eq("exam_id",exam.getId())
            );
          List<Long>studentIds=new ArrayList<>();
            for(ExamStudentEntity examStudent:examStudents){
                studentIds.add(examStudent.getStudentId());
            }
            // 批量查询学生信息，通过studentService
            List<StudentEntity> students = studentService.selectBatchIds(studentIds);

            Map<Long, StudentEntity> studentMap = new HashMap<>();
            for (StudentEntity stu : students) {
                studentMap.put(stu.getId(), stu);
            }

            List<String> usernames = new ArrayList<>();
            List<String> names = new ArrayList<>();

            for (Long studentId : studentIds) {
                StudentEntity stu = studentMap.get(studentId);
                if (stu != null) {
                    usernames.add(stu.getsUsername());
                    names.add(stu.getsName());
                }
            }
            map.put("target", String.join(",", usernames));
            map.put("targetNames", String.join(",", names));
            // 转换状态：0 -> draft，1 -> published
            String statusLabel = "draft";
            if (exam.getStatus() != null && exam.getStatus() == 1) {
                statusLabel = "published";
            }
            map.put("status", statusLabel);

            map.put("startTime", formatDatetime(exam.getStartTime()));
            map.put("endTime", formatDatetime(exam.getEndTime()));
            map.put("examTime", formatDatetime(exam.getStartTime()) + " to " + formatDatetime(exam.getEndTime()));

            resultList.add(map);
        }

        return R.ok().put("data", resultList);
    }
//查看学生列表
    @GetMapping("/studentlist")
    public R getStudengList(){
        //查询学生
        List<StudentEntity>students=studentService.selectList(null);
        List<Map<String,String>>result=new ArrayList<>();
        for (StudentEntity student : students) {
            Map<String, String> map = new HashMap<>();
            map.put("value", student.getsUsername()); // 学号/登录名
            map.put("label", student.getsName());     // 真实姓名
            result.add(map);
        }
        return R.ok().put("data", result);
    }
    //创建考试
    @RequestMapping("/create")
    public R createExam(@RequestBody ExamRequestDTO request){
        // 1. 新建考试实体
        ExamEntity exam = new ExamEntity();
        exam.setName(request.getName());
        exam.setPaperId(request.getPaperId());
        exam.setStatus(request.getStatus());
        exam.setStartTime(request.getStartTime());
        exam.setEndTime(request.getEndTime());
        // 2. 保存考试（假设是插入操作，id自动生成）
        examService.insert(exam);

        // 3. 批量保存考试-学生关联
        List<ExamStudentEntity> examStudentList = new ArrayList<>();
        for (Long studentId : request.getStudentIds()) {
            ExamStudentEntity examStudent = new ExamStudentEntity();
            examStudent.setExamId(exam.getId());  // 刚保存考试后会有ID
            examStudent.setStudentId(studentId);
            examStudentList.add(examStudent);
        }
        examStudentService.insertBatch(examStudentList);

        return R.ok();
    }
    //更新考试
    @PutMapping("/update")
    public R updateExam(@RequestBody ExamUpdateDTO request){
        ExamEntity exam = examService.selectById(request.getId());
        if (exam == null) return R.error("考试不存在");

        exam.setName(request.getName());
        exam.setPaperId(request.getPaperId());
        exam.setStatus(request.getStatus());
        exam.setStartTime(request.getStartTime());
        exam.setEndTime(request.getEndTime());

        examService.updateById(exam);

        examStudentService.delete(new EntityWrapper<ExamStudentEntity>().eq("exam_id", request.getId()));
        if (request.getStudentIds() != null) {
            for (Long studentId : request.getStudentIds()) {
                ExamStudentEntity rel = new ExamStudentEntity();
                rel.setExamId(request.getId());
                rel.setStudentId(studentId);
                examStudentService.insert(rel);
            }
        }

        return R.ok("考试更新成功");
    }


    //删除考试
    @DeleteMapping("/delete/{id}")
    public R deleteExam(@PathVariable("id") Long id) {
        // 删除考试本身
        examService.deleteById(id);

        // 同时删除与该考试相关的 exam_student 关联记录
        examStudentService.delete(new EntityWrapper<ExamStudentEntity>().eq("exam_id", id));

        return R.ok("删除成功");
    }







    // 工具方法：格式化时间
    private String formatDatetime(Date date) {
        if (date == null) return "";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    }

