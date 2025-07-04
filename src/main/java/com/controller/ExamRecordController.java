package com.controller;

import com.annotation.IgnoreAuth;
import com.entity.vo.ExamRecordVO;
import com.service.ExamRecordService;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
