package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.dto.ExamRequestDTO;
import com.dto.ExamUpdateDTO;
import com.entity.*;
import com.service.*;
import com.utils.JwtUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    private ExamService examService;
    @Autowired
    private ExampaperService exampaperService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ExamStudentService examStudentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseStudentService courseStudentService;
    @Autowired
    private CourseCategoryService courseCategoryService;
    @Autowired
    private ExamquestionService examquestionService;
    @Autowired
    private ExamquestionbankService examquestionbankService;
    //获取考试列表
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam(required = false)String name,@RequestParam(required = false) Long courseId,@RequestParam String token){
        String role=JwtUtils.getRoleFromToken(token);
        // 仅允许教师访问
        if (!"teacher".equals(role)) {
            return R.error("无权限访问考试列表");
        }

        // 获取当前教师用户名
        Long teacherId = JwtUtils.getUserIdFromToken(token);
        if (teacherId == null) {
            return R.error("无效的 token");
        }
        String tUsername = teacherService.selectById(teacherId).getT_username();
        System.out.println("【调试】当前教师用户名: " + tUsername);

        //从 exam_paper 表查出该教师发布的、status=0 的试卷
        // 查询教师发布的试卷（课程ID条件为可选）
        EntityWrapper<ExamPaperEntity> paperWrapper = (EntityWrapper<ExamPaperEntity>) new EntityWrapper<ExamPaperEntity>()
                .eq("t_username", tUsername)
                .eq("status", 1); // 考试类试卷
        if (courseId != null) {
            paperWrapper.eq("course_id", courseId);
        }
        List<ExamPaperEntity> examPapers = exampaperService.selectList(paperWrapper);
        if (examPapers == null || examPapers.isEmpty()) {
            return R.ok().put("data", new ArrayList<>()); // 没有考试类试卷
        }

        // 收集所有该教师的考试试卷的 ID
        List<Long> paperIds = examPapers.stream()
                .map(ExamPaperEntity::getId)
                .collect(Collectors.toList());
        System.out.println("【调试】试卷ID列表: " + paperIds);

        // 第二步：查询 exam 表中 paper_id ∈ 上面试卷ID列表的考试记录
        EntityWrapper<ExamEntity> wrapper = new EntityWrapper<>();
        wrapper.in("paper_id", paperIds);
        // 如果 name 有关键词搜索
        if (name != null && !name.trim().isEmpty()) {
            wrapper.like("name", name);
            System.out.println("【调试】搜索关键词: " + name);
        }
        List<ExamEntity> exams = examService.selectList(wrapper);
        System.out.println("【调试】找到的考试记录数量: " + (exams != null ? exams.size() : 0));

        List<Map<String, Object>> resultList = new ArrayList<>();
        for (ExamEntity exam : exams) {
            System.out.println("【调试】处理考试ID: " + exam.getId() + ", 考试名称: " + exam.getName());

            ExamPaperEntity paper=exampaperService.selectById(exam.getPaperId());
            Map<String, Object> map = new HashMap<>();
            map.put("id", exam.getId());
            map.put("name", exam.getName());
            map.put("paperId", exam.getPaperId());
            map.put("paperTitle", paper != null ? paper.getTitle() : "");
            //  新增：获取课程名称
            if (paper != null && paper.getCourseId() != null) {
                CourseCategoriesEntity course = courseCategoryService.selectById(paper.getCourseId());
                map.put("courseName", course != null ? course.getCourse() : "");
            } else {
                map.put("courseName", ""); // 空值兜底
            }

            //查询参加考试的学生列表
            List<ExamStudentEntity>examStudents=examStudentService.selectList(
                    new EntityWrapper<ExamStudentEntity>().eq("exam_id",exam.getId())
            );
            System.out.println("【调试】考试ID " + exam.getId() + " 的参加学生数量: " + (examStudents != null ? examStudents.size() : 0));

            if (examStudents != null && !examStudents.isEmpty()) {
                for (ExamStudentEntity examStudent : examStudents) {
                    System.out.println("【调试】参加考试的学生用户名: " + examStudent.getsUsername());
                }
            }

            // 获取所有学生用户名
            List<String> sUsernames = examStudents.stream()
                    .map(ExamStudentEntity::getsUsername)
                    .collect(Collectors.toList());
            System.out.println("【调试】收集到的学生用户名列表: " + sUsernames);

            // 根据用户名批量查询学生信息
            List<StudentEntity> students = new ArrayList<>();
            if (!sUsernames.isEmpty()) {
                students = studentService.selectList(
                        new EntityWrapper<StudentEntity>().in("s_username", sUsernames)
                );
                System.out.println("【调试】查询到的学生实体数量: " + (students != null ? students.size() : 0));

                if (students != null && !students.isEmpty()) {
                    for (StudentEntity student : students) {
                        System.out.println("【调试】学生实体 - 用户名: " + student.getsUsername() + ", 姓名: " + student.getsName());
                    }
                }
            } else {
                System.out.println("【调试】学生用户名列表为空，跳过查询学生实体");
            }

            // 建立 username → student 的映射关系
            Map<String, StudentEntity> studentMap = new HashMap<>();
            for (StudentEntity stu : students) {
                studentMap.put(stu.getsUsername(), stu);
            }
            System.out.println("【调试】学生映射关系建立完成，映射数量: " + studentMap.size());

            List<String> usernames = new ArrayList<>();
            List<String> names = new ArrayList<>();

            for (String sUsername : sUsernames) {
                StudentEntity stu = studentMap.get(sUsername);
                if (stu != null) {
                    usernames.add(stu.getsUsername());
                    names.add(stu.getsName());
                    System.out.println("【调试】成功匹配学生 - 用户名: " + stu.getsUsername() + ", 姓名: " + stu.getsName());
                } else {
                    System.out.println("【调试】警告：找不到用户名为 " + sUsername + " 的学生信息");
                }
            }

            System.out.println("【调试】最终用户名列表: " + usernames);
            System.out.println("【调试】最终姓名列表: " + names);

            map.put("target", String.join(",", usernames));
            map.put("targetNames", String.join(",", names));
            map.put("startTime", formatDatetime(exam.getStartTime()));
            map.put("endTime", formatDatetime(exam.getEndTime()));
            map.put("examTime", formatDatetime(exam.getStartTime()) + " to " + formatDatetime(exam.getEndTime()));
            System.out.println("【考试信息】" + map);

            resultList.add(map);
        }

        return R.ok().put("data", resultList);
    }
    //查看试卷列表
    @GetMapping("/paperlistbycourse")
    public R getPapersByCourse(@RequestParam Long courseId, @RequestParam String token) {
        // 解析 token 获取教师身份（可选）
        Long teacherId = JwtUtils.getUserIdFromToken(token);
        if (teacherId == null) {
            return R.error("无效 token");
        }

        TeacherEntity teacher = teacherService.selectById(teacherId);
        if (teacher == null) {
            return R.error("教师不存在");
        }

        // 查询指定课程下属于该教师的试卷
        EntityWrapper<ExamPaperEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("t_username", teacher.getT_username());
        wrapper.eq("status", 1); // 只查“考试”类型试卷（非作业）

        List<ExamPaperEntity> paperList = exampaperService.selectList(wrapper);

        // 可选：只返回部分字段简化前端数据量
        List<Map<String, Object>> result = paperList.stream().map(paper -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", paper.getId());
            map.put("title", paper.getTitle());
            return map;
        }).collect(Collectors.toList());

        return R.ok().put("data", result);
    }

    //查看学生列表
    @GetMapping("/studentlistbycourse")
    public R getStudentListByCourse(@RequestParam Long courseId){
        //查询学生
        List<CourseStudentEntity> csList=courseStudentService.selectList(new EntityWrapper<CourseStudentEntity>().eq("course_id",courseId));
        if (csList == null || csList.isEmpty()) {
            return R.ok().put("data", new ArrayList<>());
        }
        // 收集 studentIds
        List<String> sUsernames = csList.stream()
                .map(CourseStudentEntity::getsUsername)
                .collect(Collectors.toList());
        //批量查询 StudentEntity（user_student 表中字段为 s_username）
        List<StudentEntity> studentList = studentService.selectList(
                new EntityWrapper<StudentEntity>().in("s_username", sUsernames)
        );
        //提取 studentId 列表
        List<Long> studentIds = studentList.stream()
                .map(StudentEntity::getId)
                .collect(Collectors.toList());
        // 查询学生信息
        List<StudentEntity> students = studentService.selectBatchIds(studentIds);

        return R.ok().put("data", students);
    }
    //创建考试
    @RequestMapping("/create")
    public R createExam(@RequestBody ExamRequestDTO request,@RequestParam String token){
        //解析并验证token
        Long teacherId = JwtUtils.getUserIdFromToken(token);
        if (teacherId == null) {
            return R.error("无效的 token，无法创建考试");
        }

        TeacherEntity teacher = teacherService.selectById(teacherId);
        if (teacher == null) {
            return R.error("教师信息不存在");
        }
        //  新建考试实体
        ExamEntity exam = new ExamEntity();
        exam.setName(request.getName());
        exam.setPaperId(request.getPaperId());
        exam.setStartTime(request.getStartTime());
        exam.setEndTime(request.getEndTime());

        // 保存考试（假设是插入操作，id自动生成）
        examService.insert(exam);

        //  批量保存考试-学生关联
        List<ExamStudentEntity> examStudentList = new ArrayList<>();
        for (String sUsername : request.getsUsernames()) {
            ExamStudentEntity examStudent = new ExamStudentEntity();
            examStudent.setExamId(exam.getId());  // 刚保存考试后会有ID
            examStudent.setsUsername(sUsername);
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
        exam.setStartTime(request.getStartTime());
        exam.setEndTime(request.getEndTime());

        examService.updateById(exam);
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
    //查看考试
    @GetMapping("/detail/{examId}")
    public R getExamDetail(@PathVariable Long examId){
        //查询考试信息
        ExamEntity exam=examService.selectById(examId);
        if(exam==null){
            return R.error("考试不存在");
        }
        //查询试卷信息
        ExamPaperEntity paper=exampaperService.selectById(exam.getPaperId());
        if(paper==null){
            return R.error("试卷不存在");
        }
        //查询该试卷对应的题目ID列表
        List<ExamQuestionEntity>examQuestions=examquestionService.selectList(
                new EntityWrapper<ExamQuestionEntity>().eq("paperid",paper.getId())
        );
        //提取题目列表
        List<Long> questionIds = examQuestions.stream()
                .map(ExamQuestionEntity::getQuestionId)
                .collect(Collectors.toList());
        // 批量查询题目信息
        List<ExamQuestionBankEntity> questions = new ArrayList<>();
        if (!questionIds.isEmpty()) {
            questions = examquestionbankService.selectBatchIds(questionIds);
        }
        // 构造返回结构
        Map<String, Object> result = new HashMap<>();
        result.put("examName", exam.getName());
        result.put("paperTitle", paper.getTitle());
        result.put("startTime", formatDatetime(exam.getStartTime()));
        result.put("endTime", formatDatetime(exam.getEndTime()));
        result.put("questions", questions);

        return R.ok().put("data", result);
    }
    // 工具方法：格式化时间
    private String formatDatetime(Date date) {
        if (date == null) return "";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    @GetMapping("/teacher")
    public List<Map<String, Object>> getExamsByTeacher(HttpServletRequest request) {
        String fullToken = request.getHeader("Authorization");

        if (!JwtUtils.isValidToken(fullToken)) {
            System.out.println("无效 token");
            return Collections.emptyList();
        }

        // ✅ 手动处理 token 去掉 Bearer 前缀再传入
        String token = fullToken.startsWith("Bearer ") ? fullToken.substring(7) : fullToken;
        String teacherUsername = JwtUtils.getUserName(token);
        String role = JwtUtils.getRoleFromToken(token);

        System.out.println("教师账号：" + teacherUsername);
        System.out.println("角色：" + role);

        if (teacherUsername == null || !"teacher".equals(role)) {
            return Collections.emptyList();
        }

        return examService.getExamsByTeacher(teacherUsername);
    }
}

