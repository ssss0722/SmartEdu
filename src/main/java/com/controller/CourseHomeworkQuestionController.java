package com.controller;


import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.HomeworkQuestionEntity;
import com.entity.TeacherEntity;
import com.entity.view.CourseHomeworkQuestionView;
import com.service.CourseHomeworkQuestionService;
import com.service.TeacherService;
import com.utils.JwtUtils;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/courseHomeworkQuestion")
public class CourseHomeworkQuestionController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseHomeworkQuestionService courseHomeworkQuestionService;


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HomeworkQuestionEntity homeworkQuestionEntity,
                  String tableName, String token){
        // 1. 安全处理token和tableName
        if (token == null) {
            return R.error("token不能为空");
        }
        Long id = JwtUtils.getUserIdFromToken(token);
        if (id == null) {
            return R.error("无效的token");
        }

        // 2. 避免空指针 - 使用常量在前的方式比较字符串
        if ("user_teacher".equals(tableName)) {
            TeacherEntity teacher = teacherService.selectById(id);
            if (teacher != null && teacher.getT_username() != null) {
                homeworkQuestionEntity.settUsername(teacher.getT_username());
            }
        }
        // 3. 创建EntityWrapper并添加表别名
        EntityWrapper<HomeworkQuestionEntity> ew = new EntityWrapper<>();

        // 4. 增强MPUtil - 添加表别名支持
        ew = (EntityWrapper<HomeworkQuestionEntity>) MPUtil.likeOrEqWithAlias(ew, homeworkQuestionEntity, "chq");
        ew = (EntityWrapper<HomeworkQuestionEntity>) MPUtil.betweenWithAlias(ew, params, "chq");
        ew = (EntityWrapper<HomeworkQuestionEntity>) MPUtil.sortWithAlias(ew, params, "chq");

        PageUtils page = courseHomeworkQuestionService.queryPage(params, ew);
        return R.ok().put("data", page);
    }

    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HomeworkQuestionEntity question){
        EntityWrapper<HomeworkQuestionEntity> ew = new EntityWrapper<>();

        PageUtils page = courseHomeworkQuestionService.queryPage(params, (EntityWrapper<HomeworkQuestionEntity>) MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, question), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(HomeworkQuestionEntity question){
        EntityWrapper<HomeworkQuestionEntity> ew = new EntityWrapper<>();
        ew.allEq(MPUtil.allEQMapPre(question, "chq"));
        return R.ok().put("data", courseHomeworkQuestionService.selectListView(ew));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(HomeworkQuestionEntity question){
        EntityWrapper<HomeworkQuestionEntity> ew = new EntityWrapper<HomeworkQuestionEntity>();
        ew.allEq(MPUtil.allEQMapPre( question, "chq"));
        CourseHomeworkQuestionView examquestionView =  courseHomeworkQuestionService.selectView(ew);
        return R.ok("查询试题表成功").put("data", examquestionView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        HomeworkQuestionEntity question = courseHomeworkQuestionService.selectById(id);
        return R.ok().put("data", question);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        HomeworkQuestionEntity question = courseHomeworkQuestionService.selectById(id);
        return R.ok().put("data", question);
    }

    /**
     * 后端保存
     */
    @RequestMapping("/add")
    public R save(@RequestBody HomeworkQuestionEntity homeworkQuestion,String token){
        Long id=JwtUtils.getUserIdFromToken(token);
        String role=JwtUtils.getRoleFromToken(token);
        if(role.equals("teacher")){
            homeworkQuestion.settUsername(teacherService.selectById(id).getT_username());
        }
        homeworkQuestion.setAddtime(new Date());
        courseHomeworkQuestionService.insert(homeworkQuestion);
        return R.ok("添加成功");
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody HomeworkQuestionEntity question){
        courseHomeworkQuestionService.updateById(question);//全部更新
        return R.ok("修改成功");
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        courseHomeworkQuestionService.deleteBatchIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }

}
