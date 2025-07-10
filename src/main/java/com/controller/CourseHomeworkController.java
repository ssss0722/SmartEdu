package com.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.service.*;
import com.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;

import com.entity.view.CourseHomeworkView;

/**
 * 课程作业
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@RestController
@RequestMapping("/courseHomework")
public class CourseHomeworkController {
    @Autowired
    private CourseHomeworkService courseHomeworkService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ExamquestionbankService examquestionbankService;

    @Autowired
    private CourseHomeworkQuestionService courseHomeworkQuestionService;

    @Autowired
    private ExampaperService exampaperService;

    @Autowired
    private StudentService studentService;


	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(CourseHomeworkEntity courseHomework,String token){
        Long id=JwtUtils.getUserIdFromToken(token);
        String username=teacherService.selectById(id).getT_username();
       	EntityWrapper<CourseHomeworkEntity> ew = new EntityWrapper<CourseHomeworkEntity>();
      	ew.allEq(MPUtil.allEQMapPre(courseHomework, "eh"));
        ew.addFilter("paper_id IN (SELECT id FROM paper WHERE status = 0 AND t_username = '" + username + "')");
        // 4. 查询作业列表
        List<CourseHomeworkView> homeworkList = courseHomeworkService.selectListView(ew);

        // 5. 为每个作业查询学生列表
        for (CourseHomeworkView homework : homeworkList) {
            // 查询该作业的学生列表
            List<StudentEntity> students = studentService.findStudentsByHomeworkId(homework.getId());
            homework.setStudentList(students);
        }

        return R.ok().put("data", homeworkList);
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CourseHomeworkEntity courseHomework,String token){
        Long id=JwtUtils.getUserIdFromToken(token);
        String username=teacherService.selectById(id).getT_username();
        EntityWrapper<CourseHomeworkEntity> ew = new EntityWrapper<CourseHomeworkEntity>();
 		ew.allEq(MPUtil.allEQMapPre(courseHomework, "eh"));
        ew.addFilter("paper_id IN (SELECT id FROM paper WHERE status = 0 AND t_username = '" + username + "')");
		CourseHomeworkView courseHomeworkView =  courseHomeworkService.selectView(ew);
		return R.ok("查询课程作业成功").put("data", courseHomeworkView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CourseHomeworkEntity courseHomework = courseHomeworkService.selectById(id);
        return R.ok().put("data", courseHomework);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        CourseHomeworkEntity courseHomework = courseHomeworkService.selectById(id);
        return R.ok().put("data", courseHomework);
    }

    /**
     * 后端保存
     */
    @RequestMapping("/add")
    public R save(@RequestBody CourseHomeworkEntity courseHomework,String token){
        courseHomeworkService.insert(courseHomework);
        return R.ok("保存成功");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CourseHomeworkEntity courseHomework,String token){
        Long id=JwtUtils.getUserIdFromToken(token);
        String role=JwtUtils.getRoleFromToken(token);
        TeacherEntity teacher=teacherService.selectById(id);
        CourseHomeworkEntity target=courseHomeworkService.selectById(courseHomework.getId());
        if(role.equals("teacher")){
            if(true) {
                courseHomeworkService.updateById(courseHomework);//全部更新
                return R.ok("修改成功");
            }else{
                return R.error("无权限");
            }
        }else{
            courseHomeworkService.updateById(courseHomework);//全部更新
            return R.ok("修改成功");
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        courseHomeworkService.deleteBatchIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }

    /**
     * 创建作业和自动组卷
     */
    @RequestMapping("/create")
    public R compose(@RequestParam String title,@RequestParam String course,
                     @RequestParam Integer single, @RequestParam Integer multiple,
                     @RequestParam Integer judge, @RequestParam Integer blank,
                     String token,String status){
        Long id=JwtUtils.getUserIdFromToken(token);
        String role=JwtUtils.getRoleFromToken(token);
        List<ExamQuestionBankEntity> questionList = new ArrayList<ExamQuestionBankEntity>();
        //创建试卷对象
        ExamPaperEntity homework = new ExamPaperEntity();
        homework.setTitle(title);
        if(role.equals("teacher")) {
            homework.setTUsername(teacherService.selectById(id).getT_username());
        }
        // 插入试卷，自动回填ID
        exampaperService.insert(homework);
        Long homeworkId = homework.getId();  // 获取回填的主键ID

        //单选题
        if(single>0) {
            Integer singleSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 0));
            if(singleSize<single) {
                return R.error("单选题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(role.equals("teacher")) {
                    ew.eq("t_username", teacherService.selectById(id).getT_username());
                }
                ew.eq("type", 0).orderBy("RAND()").last("limit "+single);
                List<ExamQuestionBankEntity> radioList = examquestionbankService.selectList(ew);
                questionList.addAll(radioList);
            }
        }
        //多选题
        if(multiple>0) {
            Integer multipleChoiceSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 1));
            if(multipleChoiceSize<multiple) {
                return R.error("多选题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(role.equals("teacher")) {
                    ew.eq("t_username", teacherService.selectById(id).getT_username());
                }
                ew.eq("type", 1).orderBy("RAND()").last("limit "+multiple);
                List<ExamQuestionBankEntity> multipleChoiceList = examquestionbankService.selectList(ew);
                questionList.addAll(multipleChoiceList);
            }
        }
        //判断题
        if(judge>0) {
            Integer judgeSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 2));
            if(judgeSize<judge) {
                return R.error("判断题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(role.equals("teacher")) {
                    ew.eq("t_username", teacherService.selectById(id).getT_username());
                }
                ew.eq("type", 2).orderBy("RAND()").last("limit "+judge);
                List<ExamQuestionBankEntity> determineList = examquestionbankService.selectList(ew);
                questionList.addAll(determineList);
            }
        }
        //填空题
        if(blank>0) {
            Integer blankSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 3));
            if(blankSize<blank) {
                return R.error("填空题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(role.equals("teacher")) {
                    ew.eq("t_username", teacherService.selectById(id).getT_username());
                }
                ew.eq("type", 3).orderBy("RAND()").last("limit "+blank);
                List<ExamQuestionBankEntity> fillList = examquestionbankService.selectList(ew);
                questionList.addAll(fillList);
            }
        }
        if(questionList!=null && questionList.size()>0) {
            long seq = 0;
            for(ExamQuestionBankEntity q : questionList) {
                HomeworkQuestionEntity question = new HomeworkQuestionEntity();
                question.setId(System.currentTimeMillis()+(long)Math.floor(Math.random()*1000));
                question.setPaperid(homeworkId);
                question.setQuestionId(q.getId());
                if(role.equals("teacher")) {
                    question.settUsername(teacherService.selectById(id).getT_username());
                }
                courseHomeworkQuestionService.insert(question);
            }
        }
        return R.ok("创建成功").put("data",homework);
    }

}
