package com.controller;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import com.entity.*;
import com.service.*;
import com.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;

import com.entity.view.ExamRecordView;

import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 测试记录表
 * 后端接口
 * @author 
 * @email 
 * @date 2025-07-03 16:59:44
 */
@RestController
@RequestMapping("/api/examrecord")
public class ExamRecordController {
    @Autowired
    private ExamrecordService examrecordService;
    @Autowired
    private ExampaperService exampaperService;
    @Autowired
    private ExamService examService;
    @Autowired
    private ExamquestionbankService examquestionbankService;
    @Autowired
    private TeacherService teacherService;
    /**
     * 考试记录列表
     */
    @GetMapping("/list")
    public R getExamRecords(@RequestParam(required = false) String examName,
                            @RequestParam(required = false) String submitter,
                            @RequestParam String tableName,
                            @RequestParam String token) {
      //检查身份是否为教师
        if (!"user_teacher".equals(tableName)) {
            return R.error("无权限访问考试记录");
        }
        // 从 token 中解析出用户 ID
        Long teacherId = JwtUtils.getUserIdFromToken(token);
        if (teacherId == null) {
            return R.error("无效 token");
        }

        // 获取教师账号名
        String tUsername = teacherService.selectById(teacherId).getT_username();
        EntityWrapper<ExamRecordEntity> wrapper = new EntityWrapper<>();
        //根据考试名模糊查找考试ID

        if (examName != null && !examName.trim().isEmpty()) {
            List<ExamEntity> exams = examService.selectList(
                    new EntityWrapper<ExamEntity>().like("name", examName).eq("t_username",tUsername)
            );
            if (!exams.isEmpty()) {
                List<Long> examIds = exams.stream().map(ExamEntity::getId).collect(Collectors.toList());
                wrapper.in("exam_id", examIds);
            } else {
                //如果没有匹配到试卷名，直接返回空数据
                return R.ok().put("data", Collections.emptyList());
            }
        }else {
            //没有指定考试名，查找该教师发布的所有考试记录
            List<ExamEntity> exams = examService.selectList(
                    new EntityWrapper<ExamEntity>().eq("t_username", tUsername)
            );
            if (!exams.isEmpty()) {
                List<Long> examIds = exams.stream().map(ExamEntity::getId).collect(Collectors.toList());
                wrapper.in("exam_id", examIds);
            } else {
                return R.ok().put("data", Collections.emptyList());
            }
        }


        if (submitter != null && !submitter.trim().isEmpty()) {
            wrapper.like("submitter", submitter);
        }

        List<ExamRecordEntity> records = examrecordService.selectList(wrapper);
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (ExamRecordEntity record : records) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", record.getId());
            map.put("paperId", record.getPaperid());
            map.put("submitter", record.getS_username());
            map.put("score", record.getScore());
            map.put("submitTime", record.getAddtime());

            // 查询试卷名
            ExamPaperEntity paper = exampaperService.selectById(record.getPaperid());
            map.put("paperTitle", paper != null ? paper.getTitle() : "");

            resultList.add(map);
        }

        return R.ok().put("data", resultList);

    }

    //获取考试详情
    @GetMapping("/detail/{id}")
    public R getExamDetail(@PathVariable("id")Long id,
                           @RequestParam String token,
                           @RequestParam String tableName){
        // 解析用户 ID
        Long userId = JwtUtils.getUserIdFromToken(token);
        if (userId == null) {
            return R.error("无效 token");
        }
        // 查询考试记录
        ExamRecordEntity record = examrecordService.selectById(id);
        if (record == null) {
            return R.error("未找到该考试记录");
        }
        if ("user_teacher".equals(tableName)) {
            // 查考试是否是这位老师发布的
            ExamEntity exam = examService.selectById(record.getId());
            String tUsername = teacherService.selectById(userId).getT_username();
            if (exam == null || !exam.getT_username().equals(tUsername)) {
                return R.error("无权限查看该考试记录");
            }
        } else {
            return R.error("无效身份");
        }
        Long paperId =record.getPaperid(); //试卷ID
        String submitter= record.getS_username();//学生名字
        // 查询所有该试卷+该提交人 的答题记录
        List<ExamRecordEntity> detailList = examrecordService.selectList(
                new EntityWrapper<ExamRecordEntity>()
                        .eq("paper_id", paperId)
                        .eq("submitter", submitter)
        );



        //  构建返回结果
        List<Map<String, Object>> answerList = new ArrayList<>();
        for (ExamRecordEntity detail : detailList) {
            Long questionId = detail.getQuestionid();
            ExamQuestionBankEntity question = examquestionbankService.selectById(questionId);
            if (question == null) continue;

            Map<String, Object> item = new HashMap<>();
            item.put("question", question.getTitle()); // 题干
            item.put("answer", detail.getAnswer());           // 学生答题
            item.put("correct", question.getAnswer());        // 正确答案

            answerList.add(item);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("answers", answerList);
        return R.ok().put("data", data);
    }
    /*
    *修改考试成绩
     */
    @PostMapping("/score/update")
    public R updateScore(@RequestBody Map<String, Object> payload,
                         @RequestParam String token,@RequestParam String tableName) {
        Long teacherId = JwtUtils.getUserIdFromToken(token);
        if (teacherId == null) {
            return R.error("无效 token");
        }
        Long id = Long.valueOf(payload.get("id").toString());
        Long score = Long.valueOf(payload.get("score").toString());

        ExamRecordEntity record = examrecordService.selectById(id);
        if (record == null) {
            return R.error("记录不存在");
        }

        record.setScore(score);
        examrecordService.updateById(record);

        return R.ok().put("message", "成绩更新成功");
    }
    /**
     * 考试分析列表
     */
    @GetMapping("/analysisList")
    public R getExamList(@RequestParam String token,@RequestParam String tableName) {
        // 仅教师有权限访问
        if (!"user_teacher".equals(tableName)) {
            return R.error("无权限访问考试列表");
        }
        // 从 token 获取教师 ID
        Long teacherId = JwtUtils.getUserIdFromToken(token);
        if (teacherId == null) {
            return R.error("无效 token");
        }
        // 获取教师用户名
        String tUsername = teacherService.selectById(teacherId).getT_username();

        // 查询该教师发布的所有考试，只返回id和name
        List<ExamEntity> exams = examService.selectList(
                new EntityWrapper<ExamEntity>().eq("t_username", tUsername)
        );

        List<Map<String, Object>> result = exams.stream().map(exam -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", exam.getId());
            map.put("name", exam.getName());
            return map;
        }).collect(Collectors.toList());

        return R.ok().put("data", result);
    }
    /**
     * 获取考试成绩列表
     */
    @GetMapping("/score/{examId}")
    public R getExamScore(@PathVariable Long examId, @RequestParam String token,@RequestParam String tableName) {
        // 仅允许教师角色访问
        if (!"user_teacher".equals(tableName)) {
            return R.error("无权限访问考试成绩");
        }

        // 从 token 中解析教师 ID
        Long teacherId = JwtUtils.getUserIdFromToken(token);
        if (teacherId == null) {
            return R.error("无效的 token");
        }

        // 获取教师用户名
        String tUsername = teacherService.selectById(teacherId).getT_username();

        // 验证考试是否属于该教师
        ExamEntity exam = examService.selectById(examId);
        if (exam == null || !tUsername.equals(exam.getT_username())) {
            return R.error("无权限查看该考试成绩");
        }

        // 查询考试成绩列表，假设ExamRecordEntity包含学生名、分数、答对数和总题数字段
        EntityWrapper<ExamRecordEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("exam_id", examId);

        List<ExamRecordEntity> records = examrecordService.selectList(wrapper);
        // 按学生分组统计
        Map<String, List<ExamRecordEntity>> recordsByStudent = records.stream()
                .collect(Collectors.groupingBy(ExamRecordEntity::getS_username));
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, List<ExamRecordEntity>> entry : recordsByStudent.entrySet()) {
            String student = entry.getKey();
            List<ExamRecordEntity> studentRecords = entry.getValue();

            int total = studentRecords.size();
            int correct = 0;
            int totalScore = 0;
            for (ExamRecordEntity r : studentRecords) {
                if (r.getMyscore() != null && r.getMyscore() != 0L) {
                    correct++;
                }
                if (r.getScore() != null) {
                    totalScore += r.getScore();  // 如果每条有分数
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("student", student);
            map.put("score", totalScore);
            map.put("correct", correct);
            map.put("total", total);

            result.add(map);
        }

        return R.ok().put("data", result);

    }



}
