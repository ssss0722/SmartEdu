package com.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.Date;

import com.service.TeacherService;
import com.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;

import com.entity.CourseVideoEntity;
import com.entity.view.CourseVideoView;

import com.service.CourseVideoService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 教学视频
 * 后端接口
 * @author 
 * @email 

 */
@RestController
@RequestMapping("/courseVideo")
public class CourseVideoController {
    @Autowired
    private CourseVideoService courseVideoService;

    @Autowired
    private TeacherService teacherService;
    



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, CourseVideoEntity courseVideo,
                  String token){
        Long id= JwtUtils.getUserIdFromToken(token);
        String role=JwtUtils.getRoleFromToken(token);

        // 创建带别名的条件构造器
        EntityWrapper<CourseVideoEntity> ew = new EntityWrapper<>();

        // 使用带表别名的方法构建条件
        ew = (EntityWrapper<CourseVideoEntity>) MPUtil.likeOrEqWithAlias(ew, courseVideo, "cv");
        ew = (EntityWrapper<CourseVideoEntity>) MPUtil.betweenWithAlias(ew, params, "cv");
        ew = (EntityWrapper<CourseVideoEntity>) MPUtil.sortWithAlias(ew, params, "cv");

        // 添加教师专属条件
        if(role.equals("teacher")) {
            String tUsername = teacherService.selectById(id).getT_username();
            ew.eq("cv.t_username", tUsername);  // 明确指定表别名
        }

        PageUtils page = courseVideoService.queryPage(params, ew);
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, CourseVideoEntity courseVideo){
        EntityWrapper<CourseVideoEntity> ew = new EntityWrapper<CourseVideoEntity>();
		PageUtils page = courseVideoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, courseVideo), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(CourseVideoEntity courseVideo){
       	EntityWrapper<CourseVideoEntity> ew = new EntityWrapper<CourseVideoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( courseVideo, "cv"));
        return R.ok().put("data", courseVideoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CourseVideoEntity courseVideo){
        EntityWrapper<CourseVideoEntity> ew = new EntityWrapper<CourseVideoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( courseVideo, "cv"));
		CourseVideoView courseVideoView =  courseVideoService.selectView(ew);
		return R.ok("查询教学视频成功").put("data", courseVideoView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CourseVideoEntity courseVideo = courseVideoService.selectById(id);
		courseVideo.setClicknum(courseVideo.getClicknum()+1);
		courseVideo.setClicktime(new Date());
		courseVideoService.updateById(courseVideo);
        EntityWrapper<CourseVideoEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("cv.id", id);
        courseVideo = courseVideoService.selectView(wrapper);
        return R.ok().put("data", courseVideo);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CourseVideoEntity courseVideo = courseVideoService.selectById(id);
		courseVideo.setClicknum(courseVideo.getClicknum()+1);
		courseVideo.setClicktime(new Date());
		courseVideoService.updateById(courseVideo);
        EntityWrapper<CourseVideoEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("cv.id", id);
        courseVideo = courseVideoService.selectView(wrapper);
        return R.ok().put("data", courseVideo);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/add")
    public R save(@RequestBody CourseVideoEntity courseVideo,String token){
        Long id=JwtUtils.getUserIdFromToken(token);
        String role=JwtUtils.getRoleFromToken(token);
        if(role.equals("teacher"))
        {
            courseVideo.settUsername(teacherService.selectById(id).getT_username());
        }
        courseVideo.setPublishedAt(new Date());
        courseVideoService.insert(courseVideo);
        return R.ok("添加成功");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CourseVideoEntity courseVideo){
        courseVideoService.updateById(courseVideo);//全部更新
        return R.ok("更新成功");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        courseVideoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }





}
