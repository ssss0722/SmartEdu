package com.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import com.dto.ExamPaperRequestDTO;
import com.dto.ExamPaperUpdateRequest;
import com.entity.*;
import com.service.*;
import com.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.view.ExamPaperView;

/**
 * 在线考试表
 * 后端接口
 * @author 
 * @email 
 * @date 2025-07-05 20:03:54
 */
@RestController
@RequestMapping("/paper")
public class ExamPaperController {
    @Autowired
    private ExampaperService exampaperService;




    @Autowired
    private ExamquestionService examquestionService;

    @Autowired
    private ExamquestionbankService examquestionbankService;

    @Autowired
    private ExamRecordService examrecordService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ExamService examService;
    @Autowired
    private CourseCategoryService courseCategoryService;
    @Autowired
    private CourseTeacherService courseTeacherService;

    /**
     * 考试试卷列表
     */
	@IgnoreAuth
    @RequestMapping("/managerlist")
    public R list(@RequestParam Map<String, Object> params, ExamPaperEntity exampaper,
                  @RequestParam String token
    ){
        String role=JwtUtils.getRoleFromToken(token);
//创建查询条件构造器
        EntityWrapper<ExamPaperEntity> ew = new EntityWrapper<ExamPaperEntity>();
        // 若为教师用户，则只查询自己创建的试卷
        if ("teacher".equals(role)) {
            Long teacherId = JwtUtils.getUserIdFromToken(token);
            TeacherEntity teacher = teacherService.selectById(teacherId);
            if (teacher == null) {
                return R.error("无效教师身份");
            }

            String tUsername = teacher.getT_username();
            ew.eq("t_username", tUsername);
        }



        PageUtils page = exampaperService.queryPage(params, ew);
        // 拿到当前页的试卷列表
        List<ExamPaperEntity> paperList = (List<ExamPaperEntity>) page.getList();

        for (ExamPaperEntity paper : paperList) {
            Long paperId = paper.getId();
            int count = examquestionService.selectCount(
                    new EntityWrapper<ExamQuestionEntity>().eq("paperid", paperId)
            );
            paper.setQuestionCount(count);

        //设置课程信息
            Long courseId=paper.getCourseId();
            if (courseId != null) {
                CourseCategoriesEntity courseCategories = courseCategoryService.selectById(courseId);
                if (courseCategories != null) {
                    paper.setCourseName(courseCategories.getCourse());
                }
            }
        }
        // 打印 SQL 片段
        System.out.println("查询条件 SQL：" + ew.getSqlSegment());
        System.out.println("查询参数：" + ew.getParamNameValuePairs());
        return R.ok().put("data", page);
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ExamPaperEntity exampaper){
        EntityWrapper<ExamPaperEntity> ew = new EntityWrapper<ExamPaperEntity>();
 		ew.allEq(MPUtil.allEQMapPre( exampaper, "paper"));
		ExamPaperView exampaperView =  exampaperService.selectView(ew);
		return R.ok("查询在线考试表成功").put("data", exampaperView);
    }

    /**
     * 教师查看试卷详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ExamPaperEntity exampaper = exampaperService.selectById(id);
        List<ExamQuestionEntity> examQuestions = examquestionService.selectList(
                new EntityWrapper<ExamQuestionEntity>().eq("paperid", id)
        );
        List<Long> questionIds = examQuestions.stream()
                .map(ExamQuestionEntity::getQuestionId)
                .collect(Collectors.toList());

        List<ExamQuestionBankEntity> questions = new ArrayList<>();
        if (!questionIds.isEmpty()) {
            questions = examquestionbankService.selectBatchIds(questionIds);
        }
        // 返回组合数据
        Map<String, Object> result = new HashMap<>();
        result.put("paper", exampaper);
        result.put("questions", questions);

        return R.ok().put("data", result);
    }
     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        ExamPaperEntity exampaper = exampaperService.selectOne(new EntityWrapper<ExamPaperEntity>().eq("", username));
        return R.ok().put("data", exampaper);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody ExamPaperUpdateRequest req,@RequestParam String token){
        // 查找并更新试卷基础信息
        ExamPaperEntity exampaper = exampaperService.selectById(req.getId());
        // 验证试卷是否存在
        if (exampaper == null) {
            return R.error("试卷不存在");
        }
        // 更新基本信息
        exampaper.setTitle(req.getTitle());
        exampaper.setCourseId(req.getCourseId());
        exampaper.setAddtime(new Date());
        exampaperService.updateById(exampaper);//全部更新
        // 删除原试卷的所有题目
        examquestionService.delete(new EntityWrapper<ExamQuestionEntity>().eq("paperid", exampaper.getId()));
        // 获取当前教师
        String role = JwtUtils.getRoleFromToken(token);
        Long teacherId = JwtUtils.getUserIdFromToken(token);
        TeacherEntity teacher = teacherService.selectById(teacherId);

        // 按题型随机组卷
        List<ExamQuestionBankEntity> allQuestions = new ArrayList<>();
        allQuestions.addAll(getRandomQuestions(0, req.getSingle(), teacher, role));
        allQuestions.addAll(getRandomQuestions(1, req.getMultiple(), teacher, role));
        allQuestions.addAll(getRandomQuestions(2, req.getJudge(), teacher, role));
        allQuestions.addAll(getRandomQuestions(3, req.getBlank(), teacher, role));
        long seq = 0;
        for (ExamQuestionBankEntity q : allQuestions) {
            ExamQuestionEntity eq = new ExamQuestionEntity();
            eq.setId(System.currentTimeMillis() + (long) Math.floor(Math.random() * 1000));
            eq.setPaperid(exampaper.getId());
            eq.setQuestionId(q.getId());
            eq.setT_username(teacher.getT_username());
            examquestionService.insert(eq);
        }
        return R.ok();
    }
    /*
    抽取题目方法封装
     */
    private List<ExamQuestionBankEntity> getRandomQuestions(int type, int count, TeacherEntity teacher, String role) {
        if (count <= 0) return new ArrayList<>();

        Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>().eq("type", type);
        if ("teacher".equals(role)) {
            ew.eq("t_username", teacher.getT_username());
        }
        ew.orderBy("RAND()").last("LIMIT " + count);
        List<ExamQuestionBankEntity> list = examquestionbankService.selectList(ew);
        if (list.size() < count) {
            throw new RuntimeException(getTypeName(type) + "题库数量不足");
        }
        return list;
    }

    private String getTypeName(int type) {
        switch (type) {
            case 0: return "单选";
            case 1: return "多选";
            case 2: return "判断";
            case 3: return "填空";
            default: return "未知";
        }
    }



    /**
     * 单独删除试卷
     */
    @RequestMapping("/delete/{id}")
    public R delete(@PathVariable Long id){
        //删除 exam_homework 表中依赖此试卷的记录
        examService.delete(new EntityWrapper<ExamEntity>().eq("paper_id", id));
        //删除 question 表中依赖此试卷的记录
        examquestionService.delete(new EntityWrapper<ExamQuestionEntity>().eq("paperid", id));
        boolean removed = exampaperService.deleteById(id);
        if (removed) {
            return R.ok();
        } else {
            return R.error("删除失败，试卷不存在");
        }
    }
    
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params, ExamPaperEntity exampaper, HttpServletRequest request, String pre){
        EntityWrapper<ExamPaperEntity> ew = new EntityWrapper<ExamPaperEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicktime");
        params.put("order", "desc");
		PageUtils page = exampaperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, exampaper), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 创建试卷和组卷
     */
    @RequestMapping("/create")
    public R compose(@RequestBody ExamPaperRequestDTO req, @RequestParam String token){
        //获取当前用户身份（教师）
        String tableName = JwtUtils.getRoleFromToken(token);
        Long teacherId = JwtUtils.getUserIdFromToken(token);
        // 获取教师信息
        TeacherEntity teacher = teacherService.selectById(teacherId);
        List<ExamQuestionBankEntity> questionList = new ArrayList<ExamQuestionBankEntity>();
        //创建试卷对象
        ExamPaperEntity exampaper = new ExamPaperEntity();
        exampaper.setTUsername(teacher.getT_username());
        exampaper.setTitle(req.getTitle());
        exampaper.setAddtime(new Date());
        exampaper.setCourseId(req.getCourseId());
        exampaper.setStatus(req.getStatus());
        exampaper.setTime(req.getTime());
        // 插入试卷，自动回填ID
        exampaperService.insert(exampaper);
        Long paperid = exampaper.getId();  // 获取回填的主键ID
        //筛选出课程id对应的单选题
        if(req.getSingle()>0) {
            Integer singleSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 0));
            if(singleSize< req.getSingle()) {
                return R.error("单选题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("teacher")) {
                    ew.eq("t_username", teacher.getT_username());
                }
                ew.eq("type", 0).
                        eq("course_id",req.getCourseId()).
                        orderBy("RAND()").last("limit "+req.getSingle());
                List<ExamQuestionBankEntity> radioList = examquestionbankService.selectList(ew);
                questionList.addAll(radioList);
            }
        }
        //筛选出课程id对应的多选题
        if(req.getMultiple()>0) {
            Integer multipleChoiceSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 1));
            if(multipleChoiceSize< req.getMultiple()) {
                return R.error("多选题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("teacher")) {
                    ew.eq("t_username", teacher.getT_username());
                }
                ew.eq("type", 1).
                        eq("course_id", req.getCourseId()).
                orderBy("RAND()").last("limit "+req.getMultiple());
                List<ExamQuestionBankEntity> multipleChoiceList = examquestionbankService.selectList(ew);
                questionList.addAll(multipleChoiceList);
            }
        }
        //判断题
        if(req.getJudge()>0) {
            Integer judgeSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 2));
            if(judgeSize<req.getJudge()) {
                return R.error("判断题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("teacher")) {
                    ew.eq("t_username", teacher.getT_username());
                }
                ew.eq("type", 2).eq("course_id", req.getCourseId()).orderBy("RAND()").last("limit "+req.getJudge());
                List<ExamQuestionBankEntity> determineList = examquestionbankService.selectList(ew);
                questionList.addAll(determineList);
            }
        }
        //填空题
        if(req.getBlank()>0) {
            Integer blankSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 3));
            if(blankSize< req.getBlank()) {
                return R.error("填空题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("teacher")) {
                    ew.eq("t_username", teacher.getT_username());
                }
                ew.eq("type", 3).eq("course_id", req.getCourseId()).orderBy("RAND()").last("limit "+req.getBlank());
                List<ExamQuestionBankEntity> fillList = examquestionbankService.selectList(ew);
                questionList.addAll(fillList);
            }
        }
        if(questionList!=null && questionList.size()>0) {
            long seq = 0;
            for(ExamQuestionBankEntity q : questionList) {
                ExamQuestionEntity examquestion = new ExamQuestionEntity();
                examquestion.setId(System.currentTimeMillis()+(long)Math.floor(Math.random()*1000));
                examquestion.setPaperid(paperid);
                examquestion.setQuestionId(q.getId());

                if(tableName.equals("teacher")) {
                    examquestion.setT_username(teacher.getT_username());
                }
                examquestionService.insert(examquestion);
            }
        }
        return R.ok();
    }
}
