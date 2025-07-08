package com.controller;

import com.annotation.IgnoreAuth;
import com.entity.vo.ExamRecordVO;
import com.entity.vo.ExamStudentResultVO;
import com.service.ExamRecordService;
import com.utils.JwtUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 测试记录表 Controller
 */
@RestController
@RequestMapping("/examrecord")
public class ExamRecordController {
    @Autowired
    private ExamRecordService examRecordService;
    /**
     * 查询考试记录列表
     */

    @GetMapping("/list")
    public R list() {
        List<ExamRecordVO> records = examRecordService.queryExamRecordList();
        return R.ok().put("data", records);
    }


    @PostMapping("/mark")
    public R mark(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        Long score = Long.valueOf(params.get("score").toString());

        examRecordService.markSingleQuestion(id, score);

        return R.ok().put("message", "批改成功");
    }

    @PostMapping("/autoMark/{id}")
    public R autoMark(@PathVariable("id") Long id) {
        examRecordService.autoMarkRecord(id);
        return R.ok("自动判分成功");
    }

    @IgnoreAuth
    @GetMapping("/detail")
    public R detail(@RequestParam("paperId") Long paperId,
                    @RequestParam("sUsername") String sUsername) {
        Map<String, Object> detail = examRecordService.getExamDetail(paperId, sUsername);
        return R.ok().put("data", detail);
    }
    @IgnoreAuth
    @GetMapping("/resultList")
    public R getResultList(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return R.error("未携带合法的token");
        }
        String token = authorization.substring(7); // 去除 Bearer 空格部分
        String teacherUsername = null;
        try {
            teacherUsername = JwtUtils.getUserName(token);
        } catch (Exception e) {
            return R.error("token解析失败");
        }
        List<ExamStudentResultVO> resultList = examRecordService.getStudentExamResults(teacherUsername);
        System.out.println("teacherUsername = " + teacherUsername);
        return R.ok().put("data", resultList);
    }

}
