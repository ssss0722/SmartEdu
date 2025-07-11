package com.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.plugins.Page;
import com.dto.SubmitRequestDTO;
import com.entity.*;
import com.service.*;
import com.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;

import com.entity.view.StudentView;

/**
 * 学生
 * 后端接口
 * @author 
 * @email
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;




    @Autowired
    private TokenBlacklistService tokenBlacklistService;
    @Autowired
    private CourseStudentService courseStudentService;
    @Autowired
    private CourseTeacherService courseTeacherService;
    @Autowired
    private CourseCategoryService courseCategoryService;
    @Autowired
    private ExamPaperService examPaperService;
	@Autowired
    private ExamService examService;
    @Autowired
    ExamQuestionService examQuestionService;
    @Autowired
    private ExamQuestionBankService examQuestionBankService;
    @Autowired
    private ExamRecordService examRecordService;
    @Autowired
    private CourseEvaluationService courseEvaluationService;

	/**
	 * 登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/login")
	public R login(String username, String password) {
		StudentEntity u = studentService.selectOne(new EntityWrapper<StudentEntity>().eq("s_username", username));
        System.out.println("查询到的用户：" + u);
        System.out.println("传入密码：" + password);
        System.out.println("数据库密码：" + (u != null ? u.getPassword() : "null"));
		if(u==null || !u.getPassword().equals(password)) {
			return R.error("账号或密码不正确");
		}



        String token = JwtUtils.generateToken(u.getId(), username, "student", "student");
        return R.ok().put("token", token);
	}


	
	/**
     * 注册
     */
	@IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody StudentEntity student){
    	StudentEntity u = studentService.selectOne(new EntityWrapper<StudentEntity>().eq("s_username", student.getsUsername()));
		if(u!=null) {
			return R.error("注册用户已存在");
		}
		Long uId = new Date().getTime();
		student.setId(uId);
        studentService.insert(student);
        return R.ok("注册成功");
    }

	
	/**
	 * 退出
	 */
	@RequestMapping("/logout")
	public R logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            // 将token加入黑名单
            tokenBlacklistService.addToBlacklist(token);
            return R.ok("退出成功");
        }
        return R.error(401, "无效token");
	}
    
    /**
     * 密码重置
     */
    @IgnoreAuth
	@RequestMapping(value = "/resetPass")
    public R resetPass(@RequestParam String username,
                       @RequestParam String newPassword,
                       @RequestParam(required = false) String token){
        StudentEntity u = studentService.selectOne(new EntityWrapper<StudentEntity>().eq("s_username", username));
        if(u==null) {
            return R.error("账号不存在");
        }
        // 权限验证
        if (token != null && !token.isEmpty()) {
            try {
                //  解析当前操作者信息
                Long currentUserId = JwtUtils.getUserIdFromToken(token);
                StudentEntity currentUser = studentService.selectById(currentUserId);

                // 检查权限:自己
                if (!currentUser.getsUsername().equals(username)) {
                    return R.error("无权限重置他人密码");
                }
            } catch (Exception e) {
                // token无效时继续执行，允许自助重置
            }
        }
        u.setPassword(newPassword);
        studentService.updateById(u);
        return R.ok("密码已重置为："+newPassword);
    }



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, @RequestBody StudentEntity student){
        EntityWrapper<StudentEntity> wrapper = new EntityWrapper<>();

        // 构建查询条件
        wrapper = buildQueryWrapper(wrapper, params, student);

        PageUtils page = studentService.queryPage(params, wrapper);

        // 移除密码字段
        if (page != null && page.getList() != null) {
            for (Object obj : page.getList()) {
                if (obj instanceof StudentEntity) {
                    ((StudentEntity) obj).setPassword(null);
                }
            }
        }

        return R.ok().put("data", page);
    }

    private EntityWrapper<StudentEntity> buildQueryWrapper(EntityWrapper<StudentEntity> wrapper, Map<String, Object> params, StudentEntity student) {
        // 精确匹配条件
        if (student != null) {
            if (student.getId() != null) {
                wrapper.eq("id", student.getId());
            }
            if (StringUtils.isNotBlank(student.getsUsername())) {
                wrapper.eq("s_username", student.getsUsername());
            }
        }

        // 范围查询（来自params）
        if (params != null) {
            // 时间范围查询
            if (params.get("addtime_start") != null) {
                wrapper.ge("addtime", params.get("addtime_start"));
            }
            if (params.get("addtime_end") != null) {
                wrapper.le("addtime", params.get("addtime_end"));
            }

            // 排序处理
            if (params.get("order") != null && params.get("sort") != null) {
                String order = params.get("order").toString();
                String sort = params.get("sort").toString();

                if ("asc".equalsIgnoreCase(order)) {
                    wrapper.orderBy(sort, true);
                } else {
                    wrapper.orderBy(sort, false);
                }
            }
        }

        return wrapper;
    }


    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, @RequestBody StudentEntity student){
        EntityWrapper<StudentEntity> ew = new EntityWrapper<StudentEntity>();

        ew = buildQueryWrapper(ew, null, student);
        // 限制最大返回数量
        ew.last("LIMIT 1000");

        List<StudentView> list = studentService.selectListView(ew);

        // 移除密码字段
        list.forEach(u -> u.setPassword(null));

        return R.ok().put("data", list);
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(@RequestBody StudentEntity student,
                   @RequestParam Map<String, Object> params){
        EntityWrapper<StudentEntity> ew = new EntityWrapper<>();

        // 1. 模糊查询 + 等值查询 (核心修改)
        ew = (EntityWrapper<StudentEntity>) MPUtil.likeOrEq(ew, student);

        // 2. 范围查询 (如时间范围)
        ew = (EntityWrapper<StudentEntity>) MPUtil.between(ew, params);

        // 3. 排序支持
        ew = (EntityWrapper<StudentEntity>) MPUtil.sort(ew, params);

        // 4. 分页支持 (示例)
        Page<StudentEntity> page = new Page<>(1, 10); // 默认第1页，10条
        if(params.get("page") != null && params.get("limit") != null) {
            page = new Page<>(
                    Integer.parseInt(params.get("page").toString()),
                    Integer.parseInt(params.get("limit").toString()
                    ));
        }

        // 5. 查询列表 (原selectView改为分页查询)
        Page<StudentEntity> pageResult = studentService.selectPage(page, ew);

        return R.ok("查询成功")
                .put("data", pageResult.getRecords())
                .put("count", pageResult.getTotal());
    }
	
    /**
     * 学生主页详情
     */
    @RequestMapping("/info")
    public R getStudentInfo(@RequestParam String token){
      //解析token获取studentId
        Long studentId=JwtUtils.getUserIdFromToken(token);
        if(studentId==null){
            return R.error("无效token");
        }
        //查询学生信息
        StudentEntity student=studentService.selectById(studentId);
        if(student==null){
            return R.error("未找到该学生");
        }
        String sUsername=student.getsUsername();
        //查询学生所选课程
        List<CourseStudentEntity> courseStudentList=courseStudentService.selectList(
                new EntityWrapper<CourseStudentEntity>().eq("s_username",sUsername)
        );
        List<Long> courseIds=courseStudentList.stream()
                .map(CourseStudentEntity::getCourseId)
                .collect(Collectors.toList());
        //查询课程信息
        List<CourseCategoriesEntity> courses = courseCategoryService.selectBatchIds(courseIds);

        // 构造课程信息列表（包含 courseId 和 courseName）
        List<Map<String, Object>> courseInfoList = new ArrayList<>();
        for (CourseCategoriesEntity course : courses) {
            Map<String, Object> map = new HashMap<>();
            map.put("courseId", course.getId());
            map.put("courseName", course.getCourse());
            courseInfoList.add(map);
        }
        //构造响应数据
            Map<String, Object> result = new HashMap<>();
            result.put("s_username", student.getsUsername());
          result.put("s_name", student.getsName());
            result.put("courses", courseInfoList);

            return R.ok().put("data", result);
    }
    /**
     * 学生查看考试列表
     */
    @IgnoreAuth
    @RequestMapping("/lists")
    public R listStudentExams(@RequestParam String token){
        //解析token,获取s_username
        String role=JwtUtils.getRoleFromToken(token);
        if(!"student".equals(role)){
            return R.error("权限不足，仅限学生访问");
        }
        Long studentId=JwtUtils.getUserIdFromToken(token);
        StudentEntity student=studentService.selectById(studentId);
        if(student==null){
            return R.error("无效学生身份");
        }
        String sUsername=student.getsUsername();
        //查询course_student表，获取所有所选课程
        List<CourseStudentEntity>csList=courseStudentService.selectList(
                new EntityWrapper<CourseStudentEntity>().eq("s_username",sUsername)
        );
        List<Long>courseIds=csList.stream()
                .map(CourseStudentEntity::getCourseId)
                .collect(Collectors.toList());

        if (courseIds.isEmpty()) {
            return R.ok().put("data", Collections.emptyList());
        }
        //查询paper，找出试卷和作业
        List<ExamPaperEntity> papers = examPaperService.selectList(
                new EntityWrapper<ExamPaperEntity>()
                        .in("course_id", courseIds)

        );
        if (papers.isEmpty()) {
            return R.ok().put("data", Collections.emptyList());
        }
        List<Long> paperIds = papers.stream()
                .map(ExamPaperEntity::getId)
                .collect(Collectors.toList());
       // 查询 exam 表，找出这些试卷对应的考试
        List<ExamEntity> exams = examService.selectList(
                new EntityWrapper<ExamEntity>().in("paper_id", paperIds)
        );
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (ExamEntity exam : exams) {
            Map<String, Object> map = new HashMap<>();
            map.put("examId", exam.getId());
            map.put("examName", exam.getName());
            map.put("startTime", formatDatetime(exam.getStartTime()));
            map.put("endTime", formatDatetime(exam.getEndTime()));
            map.put("paperId",exam.getPaperId());

            // 获取试卷
            ExamPaperEntity paper = examPaperService.selectById(exam.getPaperId());
            if (paper != null) {
                map.put("paperTitle", paper.getTitle());

                // 获取课程名称
                CourseCategoriesEntity course = courseCategoryService.selectById(paper.getCourseId());
                if (course != null) {
                    map.put("courseName", course.getCourse());
                }
            }

            resultList.add(map);
        }

        return R.ok().put("data", resultList);

    }
    //格式化时间
    private String formatDatetime(Date date) {
        if (date == null) return "";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody StudentEntity student){
        if (student.getId() == null) {
            return R.error("用户ID不能为空");
        }

        StudentEntity existingUser = studentService.selectById(student.getId());
        if (existingUser == null) {
            return R.error("要更新的用户不存在");
        }
        // 检查用户名是否已存在（排除自身）
        EntityWrapper<StudentEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("s_username", student.getsUsername());
        if (student.getId() != null) {
            wrapper.ne("id", student.getId());
        }

        // 如果密码不为空，加密密码
        if (StringUtils.isNotBlank(student.getPassword())) {
            student.setPassword(student.getPassword());
        } else {
            // 保留原密码
            StudentEntity originalUser = studentService.selectById(student.getId());
            if (originalUser != null) {
                student.setPassword(originalUser.getPassword());
            }
        }

        studentService.updateById(student);
        return R.ok("更新成功");
    }
    /**
     * 学生查看试卷详情
     */
    @GetMapping("/paper/detail/{paperId}")
    public R getPaperDetail(@PathVariable Long paperId){
        //获取试卷题目列表
        List<ExamQuestionEntity> questionLinks = examQuestionService.selectList(
                new EntityWrapper<ExamQuestionEntity>().eq("paperid", paperId)
        );

        List<Long> questionIds = questionLinks.stream()
                .map(ExamQuestionEntity::getQuestionId)
                .collect(Collectors.toList());

        // 批量查出题目详情
        List<ExamQuestionBankEntity> questions = examQuestionBankService.selectBatchIds(questionIds);

        return R.ok().put("questions", questions);
    }

    /**
     * 学生提交答题记录+自动评分
     */

    @PostMapping("/paper/submit")
    public R submitPaper(@RequestBody SubmitRequestDTO submit,@RequestParam String token) {
        //解析用户token获得sUsername
        Long studentId = JwtUtils.getUserIdFromToken(token);
        if (studentId == null) {
            return R.error("无效 token");
        }

        StudentEntity student = studentService.selectById(studentId);
        if (student == null) {
            return R.error("学生不存在");
        }
        String sUsername = student.getsUsername();

        Long paperId = submit.getPaperId();
        //查询教师用户名
        ExamPaperEntity paper = examPaperService.selectById(paperId);
        if (paper == null) {
            return R.error("试卷不存在");
        }
        String tUsername = paper.getTUsername();
        Map<Long, String> answers = submit.getAnswers();
        List<ExamRecordEntity> records = new ArrayList<>();
        Long totalScore = 0L;
        for (Map.Entry<Long, String> entry : answers.entrySet()) {
            Long questionId = entry.getKey();
            String myAnswer = entry.getValue();
            ExamQuestionBankEntity question = examQuestionBankService.selectById(questionId);
            Long score = 0L;
           if(question!=null&&myAnswer!=null){
               if (question.getType() == 0 || question.getType() == 2) { // 单选/判断
                   if (myAnswer.equals(question.getAnswer())) {
                       score = 20L;
                       totalScore += 20L;
                   }
               } else if (question.getType() == 1) { // 多选
                   Set<String> correct = new HashSet<>(Arrays.asList(question.getAnswer().split("")));
                   Set<String> user = new HashSet<>(Arrays.asList(myAnswer.split("")));
                   if (correct.equals(user)) {
                       score = 20L;
                       totalScore += 20L;
                   }
               } else if (question.getType() == 3) { // 填空
                   String correct = question.getAnswer().replaceAll("\\s+", "").toLowerCase();
                   String user = myAnswer.replaceAll("\\s+", "").toLowerCase();
                   if (correct.equals(user)) {
                       score = 20L;
                       totalScore += 20L;
                   }
               }
           }
            ExamRecordEntity record = new ExamRecordEntity();
            record.setAddtime(new Date());
            record.setsUsername(sUsername);
            record.setPaperid(paperId);
            record.setQuestionId(questionId);
            record.setMyanswer(myAnswer);
            record.setMyscore(score);
            record.setIsmark(1L);
            record.settUsername(tUsername);

            records.add(record);
        }
            examRecordService.insertBatch(records); // 批量插入记录

            return R.ok("提交成功").put("totalScore", totalScore);



    }
    /**
     *学生查看课程评价
     */
    @IgnoreAuth
    @GetMapping("/evaluations/{courseId}")
    public R getStudentEvaluations(@PathVariable Long courseId) {
        //  查找当前课程写过的评价
        List<CourseEvaluationEntity> evaluations = courseEvaluationService.selectList(
                new EntityWrapper<CourseEvaluationEntity>().eq("course_id", courseId)
        );

        List<Map<String, Object>> resultList = new ArrayList<>();

        for (CourseEvaluationEntity eval : evaluations) {
            Map<String, Object> map = new HashMap<>();

            // 课程名称
            CourseCategoriesEntity course = courseCategoryService.selectById(eval.getCourseId());
            map.put("courseName", course != null ? course.getCourse() : "未知课程");

            // 学生姓名
            StudentEntity student = studentService.selectOne(
                    new EntityWrapper<StudentEntity>().eq("s_username", eval.getsUsername())
            );
            map.put("studentName", student != null ? student.getsName() : eval.getsUsername());

            map.put("rating", eval.getRating());
            map.put("comment", eval.getComment());
            map.put("addtime", formatDatetime(eval.getAddTime()));

            resultList.add(map);
        }

        return R.ok().put("data", resultList);
    }
    @IgnoreAuth
    @PostMapping("/evaluate")
    public R evaluate(@RequestBody CourseEvaluationEntity evaluation) {
        if (evaluation.getCourseId() == null || evaluation.getsUsername() == null || evaluation.getRating() == null) {
            return R.error("缺少必要字段");
        }

        // 获取课程信息
        CourseCategoriesEntity course = courseCategoryService.selectById(evaluation.getCourseId());
        if (course == null) {
            return R.error("课程不存在");
        }

        // 获取任课教师
        CourseTeacherEntity courseTeacher = courseTeacherService.selectOne(
                new EntityWrapper<CourseTeacherEntity>().eq("course_id", course.getId())
        );
        if (courseTeacher == null) {
            return R.error("未找到课程对应的任课教师");
        }

        evaluation.settUsername(courseTeacher.gettUsername());
        evaluation.setAddTime(new Date());

        courseEvaluationService.insert(evaluation);
        return R.ok("评价成功");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        studentService.deleteBatchIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }
    
	










}
