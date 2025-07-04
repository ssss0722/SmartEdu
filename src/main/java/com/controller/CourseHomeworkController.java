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
    private StoreupService storeupService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ExamquestionbankService examquestionbankService;

    @Autowired
    private CourseHomeworkQuestionService courseHomeworkQuestionService;


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, CourseHomeworkEntity courseHomework,
                  String tableName,String token){
        Long id= JwtUtils.getUserIdFromToken(token);
		if(tableName.equals("user_teacher")) {
			courseHomework.settUsername(teacherService.selectById(id).getT_username());
		}
        EntityWrapper<CourseHomeworkEntity> ew = new EntityWrapper<CourseHomeworkEntity>();

		PageUtils page = courseHomeworkService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, courseHomework), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, CourseHomeworkEntity courseHomework){
        EntityWrapper<CourseHomeworkEntity> ew = new EntityWrapper<CourseHomeworkEntity>();
		PageUtils page = courseHomeworkService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, courseHomework), params), params));
        return R.ok().put("data", page);
    }


	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(CourseHomeworkEntity courseHomework){
       	EntityWrapper<CourseHomeworkEntity> ew = new EntityWrapper<CourseHomeworkEntity>();
      	ew.allEq(MPUtil.allEQMapPre(courseHomework, "ch"));
        return R.ok().put("data", courseHomeworkService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CourseHomeworkEntity courseHomework){
        EntityWrapper<CourseHomeworkEntity> ew = new EntityWrapper<CourseHomeworkEntity>();
 		ew.allEq(MPUtil.allEQMapPre(courseHomework, "ch"));
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
        Long id=JwtUtils.getUserIdFromToken(token);
        String role=JwtUtils.getRoleFromToken(token);
        if(role.equals("teacher")){
            courseHomework.settUsername(teacherService.selectById(id).getT_username());
        }
        courseHomework.setPublishAt(new Date());
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
            if(target.gettUsername().equals(teacher.getT_username())) {
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
                     @RequestParam Integer subjective,
                     String token,@RequestBody(required = false)List<ExamQuestionBankEntity> newQuestions){
        Long id=JwtUtils.getUserIdFromToken(token);
        String role=JwtUtils.getRoleFromToken(token);
        List<ExamQuestionBankEntity> questionList = new ArrayList<ExamQuestionBankEntity>();
        //创建试卷对象
        CourseHomeworkEntity homework = new CourseHomeworkEntity();
        homework.setHomework(title);
        homework.setAddtime(new Date());
        homework.setCourse(course);
        homework.setPublishAt(new Date());
        if(role.equals("teacher")) {
            homework.settUsername(teacherService.selectById(id).getT_username());
        }
        // 插入试卷，自动回填ID
        courseHomeworkService.insert(homework);
        Long homeworkId = homework.getId();  // 获取回填的主键ID

        if(!newQuestions.isEmpty()||newQuestions!=null){
            questionList.addAll(newQuestions);
            for(ExamQuestionBankEntity q:newQuestions){
                q.setAddtime(new Date());
                q.setT_username(teacherService.selectById(id).getT_username());
                examquestionbankService.insert(q);

                HomeworkQuestionEntity hq=new HomeworkQuestionEntity();
                hq.setHomeworkId(homeworkId);
                hq.setQuestionId(q.getId());
                hq.settUsername(teacherService.selectById(id).getT_username());
            }
        }
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
        //主观题
        if(subjective>0) {
            Integer subjectiveSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 4));
            if(subjectiveSize<subjective) {
                return R.error("主观题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(role.equals("teacher")) {
                    ew.eq("t_username", teacherService.selectById(id).getT_username());
                }
                ew.eq("type", 4).orderBy("RAND()").last("limit "+subjective);
                List<ExamQuestionBankEntity> subjectivityList = examquestionbankService.selectList(ew);
                questionList.addAll(subjectivityList);
            }
        }
        if(questionList!=null && questionList.size()>0) {
            long seq = 0;
            for(ExamQuestionBankEntity q : questionList) {
                HomeworkQuestionEntity question = new HomeworkQuestionEntity();
                question.setId(System.currentTimeMillis()+(long)Math.floor(Math.random()*1000));
                question.setHomeworkId(homeworkId);
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
