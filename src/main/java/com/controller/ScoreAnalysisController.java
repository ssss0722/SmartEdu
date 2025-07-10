package com.controller;

import com.service.ScoreAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // 导入 RequestParam 注解
import org.springframework.web.bind.annotation.RestController;

// 注意：如果 examName 从参数获取，就不再需要 @RequestBody 和 Map<String, String> 了
// import java.util.Map; // 不再需要

@RestController
@RequestMapping("/ai")
public class ScoreAnalysisController {

    @Autowired
    private ScoreAnalysisService scoreAnalysisService;

    @PostMapping(value = "/analyze", produces = "application/json;charset=UTF-8")
    // 修改为从请求参数获取 examName
    public ResponseEntity<?> analyzeExam(@RequestParam("examName") String examName) {
        try {
            String result = scoreAnalysisService.analyzeByExamName(examName);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 在实际应用中，这里应该记录异常日志
            // logger.error("分析考试出错: " + examName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("分析失败：" + e.getMessage());
        }
    }
}