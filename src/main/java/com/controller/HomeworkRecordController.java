package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.*;
import com.entity.view.HomeworkRecordView;
import com.service.*;
import com.utils.JwtUtils;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
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

    @Autowired
    private CourseHomeworkService courseHomeworkService;

    @Autowired
    private ExamQuestionBankService examquestionbankService;

    @Autowired
    private ExamPaperService exampaperService;

    /**
     * 作业记录接口
     */
    @RequestMapping("/groupby")
    public R page2(@RequestParam Map<String, Object> params, HomeworkRecordEntity homeworkRecord,String token){
        // 1. 安全处理token
        if (token == null) {
            return R.error("token不能为空");
        }
        Long id = JwtUtils.getUserIdFromToken(token);
        if (id == null) {
            return R.error("无效的token");
        }

        // 2. 根据角色设置条件
        String role = JwtUtils.getRoleFromToken(token);
        if ("teacher".equals(role)) {
            TeacherEntity teacher = teacherService.selectById(id);
            if (teacher != null && teacher.getT_username() != null) {
                homeworkRecord.settUsername(teacher.getT_username());
            }
        } else if (!"管理员".equals(role)) {
            StudentEntity student = studentService.selectById(id);
            if (student != null && student.getsUsername() != null) {
                homeworkRecord.setsUsername(student.getsUsername());
            }
        }

        // 3. 创建EntityWrapper并添加表别名
        EntityWrapper<HomeworkRecordEntity> ew = new EntityWrapper<>();

        // 4. 使用增强的MPUtil方法（带表别名支持）
        ew = (EntityWrapper<HomeworkRecordEntity>) MPUtil.likeOrEqWithAlias(ew, homeworkRecord, "r");
        ew = (EntityWrapper<HomeworkRecordEntity>) MPUtil.betweenWithAlias(ew, params, "r");
        ew = (EntityWrapper<HomeworkRecordEntity>) MPUtil.sortWithAlias(ew, params, "r");

        PageUtils page = homeworkRecordService.queryPageGroupBy(params, ew);
        return R.ok().put("data", page);
    }

    /**
     * 选项统计接口
     */

    @RequestMapping("/options/num")
    public R optionsNum(@RequestParam Map<String, Object> params, HomeworkRecordEntity record){
        // 创建EntityWrapper并添加表别名
        EntityWrapper<HomeworkRecordEntity> ew = new EntityWrapper<>();

        // 使用增强的MPUtil方法（带表别名支持）
        ew = (EntityWrapper<HomeworkRecordEntity>) MPUtil.likeOrEqWithAlias(ew, record, "r");
        ew = (EntityWrapper<HomeworkRecordEntity>) MPUtil.betweenWithAlias(ew, params, "r");
        ew = (EntityWrapper<HomeworkRecordEntity>) MPUtil.sortWithAlias(ew, params, "r");

        PageUtils page = homeworkRecordService.queryPageOptionsNum(params, ew);
        return R.ok().put("data", page);
    }

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HomeworkRecordEntity homeworkRecord,String token){
        // 1. 安全处理token
        if (token == null) {
            return R.error("token不能为空");
        }
        Long id = JwtUtils.getUserIdFromToken(token);
        if (id == null) {
            return R.error("无效的token");
        }

        // 2. 根据角色设置条件
        String role = JwtUtils.getRoleFromToken(token);
        if ("teacher".equals(role)) {
            TeacherEntity teacher = teacherService.selectById(id);
            if (teacher != null && teacher.getT_username() != null) {
                homeworkRecord.settUsername(teacher.getT_username());
            }
        } else if (!"管理员".equals(role)) {
            StudentEntity student = studentService.selectById(id);
            if (student != null && student.getsUsername() != null) {
                homeworkRecord.setsUsername(student.getsUsername());
            }
        }
        // 3. 创建EntityWrapper并添加表别名
        EntityWrapper<HomeworkRecordEntity> ew = new EntityWrapper<>();

        // 4. 使用增强的MPUtil方法（带表别名支持）
        // 注意：只使用带别名的方法，不使用原始方法
        ew = (EntityWrapper<HomeworkRecordEntity>) MPUtil.likeOrEqWithAlias(ew, homeworkRecord, "r");
        ew = (EntityWrapper<HomeworkRecordEntity>) MPUtil.betweenWithAlias(ew, params, "r");
        ew = (EntityWrapper<HomeworkRecordEntity>) MPUtil.sortWithAlias(ew, params, "r");

        PageUtils page = homeworkRecordService.queryPage(params, ew);
        return R.ok().put("data", page);
    }

    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HomeworkRecordEntity homeworkRecord,String token){
        // 1. 安全处理token
        if (token == null) {
            return R.error("token不能为空");
        }
        Long id = JwtUtils.getUserIdFromToken(token);
        if (id == null) {
            return R.error("无效的token");
        }

        // 2. 根据角色设置条件
        String role = JwtUtils.getRoleFromToken(token);
        if ("teacher".equals(role)) {
            TeacherEntity teacher = teacherService.selectById(id);
            if (teacher != null && teacher.getT_username() != null) {
                homeworkRecord.settUsername(teacher.getT_username());
            }
        }
        EntityWrapper<HomeworkRecordEntity> ew = new EntityWrapper<>();
        ew = (EntityWrapper<HomeworkRecordEntity>) MPUtil.likeOrEqWithAlias(ew, homeworkRecord, "r");
        ew = (EntityWrapper<HomeworkRecordEntity>) MPUtil.betweenWithAlias(ew, params, "r");
        ew = (EntityWrapper<HomeworkRecordEntity>) MPUtil.sortWithAlias(ew, params, "r");

        PageUtils page = homeworkRecordService.queryPage(params, ew);
        return R.ok().put("data", page);
    }



    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( HomeworkRecordEntity homeworkRecordEntity){
        EntityWrapper<HomeworkRecordEntity> ew = new EntityWrapper<HomeworkRecordEntity>();
        ew.allEq(MPUtil.allEQMapPre( homeworkRecordEntity, "r"));
        return R.ok().put("data", homeworkRecordService.selectListView(ew));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(HomeworkRecordEntity record){
        EntityWrapper<HomeworkRecordEntity> ew = new EntityWrapper<HomeworkRecordEntity>();
        ew.allEq(MPUtil.allEQMapPre( record, "r"));
        HomeworkRecordView view =  homeworkRecordService.selectView(ew);
        return R.ok("查询作业记录成功").put("data", view);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        HomeworkRecordEntity record = homeworkRecordService.selectById(id);
        return R.ok().put("data", record);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        HomeworkRecordEntity record = homeworkRecordService.selectById(id);
        return R.ok().put("data", record);
    }

    /**
     * 后端保存
     */
    @RequestMapping("/add")
    public R save(@RequestBody HomeworkRecordEntity record,String token){
        // 1. 安全处理token
        if (token == null) {
            return R.error("token不能为空");
        }
        Long id = JwtUtils.getUserIdFromToken(token);
        if (id == null) {
            return R.error("无效的token");
        }
        ExamQuestionBankEntity question=examquestionbankService.selectById(record.getQuestionId());
        /**
         * 试题类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空） 4:主观题
         */
        Long type=question.getType();
        if(type!=4){
            String answer= question.getAnswer();
            if(answer.equals(record.getMyanswer())){
                record.setIsmark(Long.valueOf(1));
                record.setMyscore(question.getScore());
            }
            else {
                record.setIsmark(Long.valueOf(1));
                record.setMyscore(Long.valueOf(0));
            }
        }else{
            record.setIsmark(Long.valueOf(0));
            record.setMyscore(Long.valueOf(0));
        }
        record.setsUsername(studentService.selectById(id).getsUsername());
        CourseHomeworkEntity homework = courseHomeworkService.selectById(record.getPaperid());
        record.settUsername(exampaperService.selectById(homework.getPaperId()).getTUsername());
        homeworkRecordService.insert(record);
        return R.ok("添加成功");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody HomeworkRecordEntity record) {
        // 使用EntityWrapper构建查询条件
        EntityWrapper<HomeworkRecordEntity> wrapper = new EntityWrapper<>();

        // 添加复合查询条件（homeworkId + questionId + sUsername）
        wrapper.eq("homework_id", record.getPaperid())
                .eq("question_id", record.getQuestionId())
                .eq("s_username", record.getsUsername());

        // 查询现有记录
        HomeworkRecordEntity existingRecord = homeworkRecordService.selectOne(wrapper);

        // 处理记录不存在的情况
        if (existingRecord == null) {
            return R.error("未找到对应的作业记录，请检查参数");
        }

        // 只更新允许修改的字段
        existingRecord.setIsmark(record.getIsmark());
        existingRecord.setMyscore(record.getMyscore());
        existingRecord.setMyanswer(record.getMyanswer());

        // 根据ID更新记录
        homeworkRecordService.updateById(existingRecord);
        return R.ok("更新成功");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        homeworkRecordService.deleteBatchIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }

    /**
     * 当重新考试时，删除考生的某个试卷的所有作业记录
     */
    @RequestMapping("/deleteRecords")
    public R deleteRecords(@RequestParam String token,@RequestParam Long homeworkId){
        Long id=JwtUtils.getUserIdFromToken(token);
        StudentEntity student=studentService.selectById(id);
        String userid=student.getsUsername();
        homeworkRecordService.delete(new EntityWrapper<HomeworkRecordEntity>().eq("paperid", homeworkId).eq("s_username", userid));
        return R.ok("删除成功");
    }


    /**
     * 批改完所有主观题后计算总分
     */
    @RequestMapping("/calculateTotal")
    public R calculateTotal(@RequestParam String sUsername,@RequestParam Long homeworkId){
        try {
            int score=homeworkRecordService.calculateTotalScore(sUsername, homeworkId);
            return R.ok("作业总分计算成功").put("totalScore",score);
        } catch (Exception e) {
            return R.error(500, e.getMessage());
        }
    }

    /**
     * 查找学生作业记录
     */
    @RequestMapping("/selectHomework")
    public R selectHomework(@RequestParam String token){
        Long id=JwtUtils.getUserIdFromToken(token);
        String teacherUsername = teacherService.selectById(id).getT_username();
        // 2. 查询该教师布置的所有作业的学生提交记录
        List<Map<String, Object>> homeworkList = homeworkRecordService.selectTeacherHomework(teacherUsername);
        // 3. 返回结果
        return R.ok().put("data", homeworkList);
    }
}
