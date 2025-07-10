package com.controller;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.CourseCategoriesEntity;
import com.entity.CourseTeacherEntity;
import com.entity.TeacherEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.CourseCategoryService;
import com.service.CourseTeacherService;
import com.service.TeacherService;
import com.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;

import com.entity.ExamQuestionBankEntity;
import com.entity.view.ExamQuestionBankView;

import com.service.ExamquestionbankService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 试题库表
 * 后端接口
 * @author 
 * @email 
 * @date 2025-07-04 19:40:32
 */
@RestController
@RequestMapping("/questionbank")
public class ExamQuestionBankController {
    @Autowired
    private ExamquestionbankService examquestionbankService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseTeacherService courseTeacherService;
    @Autowired
    private CourseCategoryService courseCategoryService;
    /**
     *课程列表
     */
    @RequestMapping("/courselist")
    public R listCourses(@RequestParam String token){
        String role=JwtUtils.getRoleFromToken(token);
        if(!"teacher".equals(role)){
            return R.ok("只有教师可以查看课程列表");
        }
        Long teacherId=JwtUtils.getUserIdFromToken(token);
        TeacherEntity teacher=teacherService.selectById(teacherId);
        if(teacher==null){
            return R.error("无效的教师身份");
        }
        List<CourseTeacherEntity> ctList = courseTeacherService.selectList(
                new EntityWrapper<CourseTeacherEntity>().eq("t_username", teacher.getT_username())
        );
        List<Map<String,Object>> courseList=new ArrayList<>();
        for(CourseTeacherEntity ct:ctList){
            CourseCategoriesEntity courseCategories=courseCategoryService.selectById(ct.getCourseId());
            if(courseCategories!=null) {
                Map<String,Object>map=new HashMap<>();
                map.put("id",courseCategories.getId());
                map.put("name",courseCategories.getCourse());
                courseList.add(map);
            }
        }
        return R.ok().put("data",courseList);

    }
    
    /**
     * 教师查看题库列表
     */
    @IgnoreAuth
    @RequestMapping("/managerlist")
    public R list(@RequestParam Map<String, Object> params,
                  @RequestParam String token) {


        EntityWrapper<ExamQuestionBankEntity> ew = new EntityWrapper<>();
        // 添加title模糊查询
        String title = (String) params.get("title");
        if (title != null && !title.trim().isEmpty()) {
            ew.like("title", title.trim());
            System.out.println("【调试信息】添加title模糊查询条件：" + title);
        }
        //添加courseId精准查询
        String courseIdStr=(String)params.get("courseId");
        if (courseIdStr != null && !courseIdStr.trim().isEmpty()) {
            try {
                Long courseId = Long.parseLong(courseIdStr);
                ew.eq("course_id", courseId);
                System.out.println("【调试】添加课程筛选条件：course_id = " + courseId);
            } catch (NumberFormatException e) {
                System.out.println("【警告】courseId 解析失败：" + courseIdStr);
            }
        }

        // 解析用户角色
        String role = JwtUtils.getRoleFromToken(token);
        System.out.println("【调试信息】用户角色：" + role);
        if ("teacher".equals(role)) {
            // 教师只能查看自己发布的题库
            Long teacherId = JwtUtils.getUserIdFromToken(token);
            System.out.println("【调试信息】教师ID：" + teacherId);
            TeacherEntity teacher = teacherService.selectById(teacherId);
            if (teacher == null) {
                return R.error("无效教师身份");
            }
            // 添加限定条件
            ew.eq("t_username", teacher.getT_username());
            System.out.println("【调试信息】添加教师条件后的SQL：" + ew.getSqlSegment());
        }


        // 查询分页数据
        PageUtils page = examquestionbankService.queryPage(params, ew);
        List<ExamQuestionBankEntity> eqbList = (List<ExamQuestionBankEntity>) page.getList();
        // 5. 批量查询课程信息（避免N+1问题）
        Set<Long> courseIdSet = eqbList.stream()
                .map(ExamQuestionBankEntity::getCourseId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, CourseCategoriesEntity> courseMap = new HashMap<>();
        if (!courseIdSet.isEmpty()) {
            List<CourseCategoriesEntity> courseList = courseCategoryService.selectBatchIds(courseIdSet);
            courseMap = courseList.stream()
                    .collect(Collectors.toMap(CourseCategoriesEntity::getId, Function.identity()));
        }
        //  填充课程名称和类别
        for (ExamQuestionBankEntity qb : eqbList) {
            CourseCategoriesEntity course = courseMap.get(qb.getCourseId());
            if (course != null) {
                qb.setCourseName(course.getCourse());

            }
        }
        // 打印 SQL 片段
        System.out.println("查询条件 SQL：" + ew.getSqlSegment());
        System.out.println("查询参数：" + ew.getParamNameValuePairs());
        //  打印数据到控制台
        System.out.println("【题库分页查询】当前页码：" + page.getCurrPage());
        System.out.println("【题库分页查询】每页数量：" + page.getPageSize());
        System.out.println("【题库分页查询】结果列表：");
        ObjectMapper mapper= new ObjectMapper();
        List<?> list = page.getList();
        for (ExamQuestionBankEntity qb : eqbList) {
            try {
                System.out.println(mapper.writeValueAsString(qb));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return R.ok().put("data", page);
    }




    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ExamQuestionBankEntity examquestionbank){
       	EntityWrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
      	ew.allEq(MPUtil.allEQMapPre( examquestionbank, "examquestionbank")); 
        return R.ok().put("data", examquestionbankService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ExamQuestionBankEntity examquestionbank){
        EntityWrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
 		ew.allEq(MPUtil.allEQMapPre( examquestionbank, "question_bank"));
		ExamQuestionBankView examquestionbankView =  examquestionbankService.selectView(ew);
		return R.ok("查询试题库表成功").put("data", examquestionbankView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ExamQuestionBankEntity examquestionbank = examquestionbankService.selectById(id);
        return R.ok().put("data", examquestionbank);
    }

    /**
     * 教师查看试卷详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ExamQuestionBankEntity examquestionbank = examquestionbankService.selectById(id);
        return R.ok().put("data", examquestionbank);
    }

    /**
     * 教师端创建题库
     */
    @RequestMapping("/create")
    public R add(@RequestBody ExamQuestionBankEntity examquestionbank,@RequestHeader("Authorization") String authHeader){
        // 解析用户角色
        // 从 Authorization: Bearer xxx 中提取 token
        String token = authHeader.replace("Bearer ", "");
        String role = JwtUtils.getRoleFromToken(token);
        if ("teacher".equals(role)) {
            // 教师创建题库时，自动设置创建者为当前教师
            Long teacherId = JwtUtils.getUserIdFromToken(token);

            TeacherEntity teacher = teacherService.selectById(teacherId);
            if (teacher == null) {
                return R.error("无效教师身份");
            }
            // 设置题库创建者为当前教师
            examquestionbank.setT_username(teacher.getT_username());

            // 设置默认创建时间
            if (examquestionbank.getAddtime() == null) {
                examquestionbank.setAddtime(new Date());
            }
        }
        try {
            examquestionbankService.insert(examquestionbank);
            return R.ok().put("message", "题库创建成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("题库创建失败");
        }




    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        ExamQuestionBankEntity examquestionbank = examquestionbankService.selectOne(new EntityWrapper<ExamQuestionBankEntity>().eq("", username));
        return R.ok().put("data", examquestionbank);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody ExamQuestionBankEntity examquestionbank, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examquestionbank);
        examquestionbankService.updateById(examquestionbank);//全部更新
        return R.ok();
    }

    /**
     * 删除单个试题
     */
    @RequestMapping("/delete")
    public R delete(@RequestParam Long id) {
        boolean removed = examquestionbankService.deleteById(id);
        if (removed) {
            return R.ok();
        } else {
            return R.error("删除失败，试题不存在");
        }
    }


    

    /**
     * 批量删除试题
     */
    @RequestMapping("/delete_batch")
    public R delete(@RequestBody Long[] ids){
        examquestionbankService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params, ExamQuestionBankEntity examquestionbank, HttpServletRequest request, String pre){
        EntityWrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
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
		PageUtils page = examquestionbankService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examquestionbank), params), params));
        return R.ok().put("data", page);
    }
}
