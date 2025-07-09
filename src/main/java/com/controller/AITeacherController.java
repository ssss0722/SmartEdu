package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.PlanHistoryEntity;
import com.service.PlanHistoryService;
import com.service.SparkTeacherService;
import com.utils.JwtUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/ai-teacher")
public class AITeacherController {
    @Autowired
    private SparkTeacherService teacherService;

    @Autowired
    private PlanHistoryService planHistoryService;

    @IgnoreAuth
    @PostMapping("/generate-plan")
    public R generatePlan(@RequestParam String subject,
                          @RequestParam String grade,
                          @RequestParam String topic,
                          @RequestParam String token) {
        try {
            Long id=JwtUtils.getUserIdFromToken(token);
            String batchUuid = UUID.randomUUID().toString();
            String plan1 = teacherService.generateTeachingPlan(subject, grade,1,topic);
            PlanHistoryEntity planHistory1=new PlanHistoryEntity<>();
            planHistory1.setContent(plan1);
            planHistory1.setGrade(grade);
            planHistory1.setType(1);
            planHistory1.setTopic(topic);
            planHistory1.setSubject(subject);
            planHistory1.setT_id(id);
            planHistory1.setUuid(batchUuid);
            planHistoryService.insert(planHistory1);
            String plan2 = teacherService.generateTeachingPlan(subject, grade,2,topic);
            PlanHistoryEntity planHistory2=new PlanHistoryEntity<>();
            planHistory2.setContent(plan2);
            planHistory2.setGrade(grade);
            planHistory2.setType(2);
            planHistory2.setTopic(topic);
            planHistory2.setSubject(subject);
            planHistory2.setT_id(id);
            planHistory2.setUuid(batchUuid);
            planHistoryService.insert(planHistory2);
            String plan3 = teacherService.generateTeachingPlan(subject, grade,3,topic);
            PlanHistoryEntity planHistory3=new PlanHistoryEntity<>();
            planHistory3.setContent(plan3);
            planHistory3.setGrade(grade);
            planHistory3.setType(3);
            planHistory3.setTopic(topic);
            planHistory3.setSubject(subject);
            planHistory3.setT_id(id);
            planHistory3.setUuid(batchUuid);
            planHistoryService.insert(planHistory3);
            String plan4 = teacherService.generateTeachingPlan(subject, grade,4,topic);
            PlanHistoryEntity planHistory4=new PlanHistoryEntity<>();
            planHistory4.setContent(plan4);
            planHistory4.setGrade(grade);
            planHistory4.setType(4);
            planHistory4.setTopic(topic);
            planHistory4.setSubject(subject);
            planHistory4.setT_id(id);
            planHistory4.setUuid(batchUuid);
            planHistoryService.insert(planHistory4);
            return R.ok().put("plan", plan1).put("lessonPlan", plan2).put("Outline", plan3).put("activities", plan4);
        } catch (Exception e) {
            return R.error("生成失败: " + e.getMessage());
        }
    }

    @IgnoreAuth
    @GetMapping("/history")
    public R getHistory(@RequestParam String token) {
        Long teacherId = JwtUtils.getUserIdFromToken(token);

        // 1. 查询该教师的所有AI计划记录
        EntityWrapper<PlanHistoryEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("t_id", teacherId)
                .orderBy("created_time", false); // false表示降序
        List<PlanHistoryEntity> allPlans = planHistoryService.selectList(wrapper);

        // 2. 按UUID分组（相同UUID为同一批次）
        Map<String, List<PlanHistoryEntity>> uuidMap = new LinkedHashMap<>();
        for (PlanHistoryEntity plan : allPlans) {
            String uuid = plan.getUuid();
            if (uuid != null) {
                if (!uuidMap.containsKey(uuid)) {
                    uuidMap.put(uuid, new ArrayList<>(4));
                }
                uuidMap.get(uuid).add(plan);
            }
        }

        // 3. 创建日期格式化器
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 4. 构建返回数据结构
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, List<PlanHistoryEntity>> entry : uuidMap.entrySet()) {
            List<PlanHistoryEntity> batch = entry.getValue();

            // 只处理包含4种type的完整批次
            if (batch.size() == 4) {
                Map<String, Object> batchResult = new LinkedHashMap<>();
                batchResult.put("uuid", entry.getKey());

                // 格式化日期
                Date createdTime = batch.get(0).getCreatedTime();
                batchResult.put("createdTime", sdf.format(createdTime));

                batchResult.put("subject", batch.get(0).getSubject());
                batchResult.put("grade", batch.get(0).getGrade());
                batchResult.put("topic", batch.get(0).getTopic());

                // 初始化内容字段
                String planContent = "";
                String lessonPlanContent = "";
                String outlineContent = "";
                String activitiesContent = "";

                // 按type分类存储内容
                for (PlanHistoryEntity planItem : batch) {
                    switch (planItem.getType()) {
                        case 1:
                            planContent = planItem.getContent();
                            break;
                        case 2:
                            lessonPlanContent = planItem.getContent();
                            break;
                        case 3:
                            outlineContent = planItem.getContent();
                            break;
                        case 4:
                            activitiesContent = planItem.getContent();
                            break;
                    }
                }

                // 填充到结果对象
                batchResult.put("plan", planContent);
                batchResult.put("lessonPlan", lessonPlanContent);
                batchResult.put("Outline", outlineContent);
                batchResult.put("activities", activitiesContent);

                result.add(batchResult);
            }
        }

        return R.ok().put("data", result);
    }

    @IgnoreAuth
    @DeleteMapping("/deleteHistory")
    public R deleteHistory(String token){
        Long teacherId=JwtUtils.getUserIdFromToken(token);
        // 1. 构建删除条件
        EntityWrapper<PlanHistoryEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("t_id", teacherId);

        // 2. 执行删除操作
        boolean deleteResult = planHistoryService.delete(wrapper);

        if (deleteResult) {
            return R.ok("删除成功");
        } else {
            return R.error("删除失败，请稍后重试");
        }
    }
}