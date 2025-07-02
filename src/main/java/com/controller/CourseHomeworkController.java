package com.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.entity.TeacherEntity;
import com.service.TeacherService;
import com.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;

import com.entity.CourseHomeworkEntity;
import com.entity.view.CourseHomeworkView;

import com.service.CourseHomeworkService;
import com.service.StoreupService;

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
      	ew.allEq(MPUtil.allEQMapPre( courseHomework, "ch"));
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
    
	










}
