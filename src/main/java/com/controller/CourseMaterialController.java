package com.controller;

import java.util.*;

import com.entity.TeacherEntity;
import com.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;

import com.entity.CourseMaterialEntity;
import com.entity.view.CourseMaterialView;

import com.service.CourseMaterialService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.JwtUtils;

/**
 * 教学资料
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
@RestController
@RequestMapping("/courseMaterial")
public class CourseMaterialController {
    @Autowired
    private CourseMaterialService courseMaterialService;

    @Autowired
    private TeacherService teacherService;

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, CourseMaterialEntity courseMaterial,
                  String token){
        Long id = JwtUtils.getUserIdFromToken(token);
        String role=JwtUtils.getRoleFromToken(token);
        if(id==null){
            return R.error(401, "Token无效或已过期");
        }
        EntityWrapper<CourseMaterialEntity> ew = new EntityWrapper<>();
        if(role.equals("teacher")) {
            TeacherEntity teacher=teacherService.selectById(id);
            ew.eq("cm.t_username", teacher.getT_username());
            ew = (EntityWrapper<CourseMaterialEntity>) MPUtil.likeOrEq(ew, courseMaterial);
            ew = (EntityWrapper<CourseMaterialEntity>) MPUtil.between(ew, params);
            ew = (EntityWrapper<CourseMaterialEntity>) MPUtil.sort(ew, params);
            PageUtils page = courseMaterialService.queryPage(params, ew);
            return R.ok().put("data", page);
        } else{
            PageUtils page = courseMaterialService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, courseMaterial), params), params));
            return R.ok().put("data", page);
        }
    }

    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,CourseMaterialEntity courseMaterial){
        EntityWrapper<CourseMaterialEntity> ew = new EntityWrapper<CourseMaterialEntity>();
		PageUtils page = courseMaterialService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, courseMaterial), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(@RequestBody CourseMaterialEntity courseMaterial){
       	EntityWrapper<CourseMaterialEntity> ew = new EntityWrapper<CourseMaterialEntity>();
      	ew.allEq(MPUtil.allEQMapPre( courseMaterial, "cm"));
        return R.ok().put("data", courseMaterialService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(@RequestBody CourseMaterialEntity courseMaterial){
        EntityWrapper<CourseMaterialEntity> ew = new EntityWrapper<CourseMaterialEntity>();
 		ew.allEq(MPUtil.allEQMapPre(courseMaterial, "cm"));
		CourseMaterialView courseMaterialView =  courseMaterialService.selectView(ew);
		return R.ok("查询教学资料成功").put("data", courseMaterialView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CourseMaterialEntity courseMaterial = courseMaterialService.selectById(id);
		courseMaterial.setClicknum(courseMaterial.getClicknum()+1);
		courseMaterial.setClicktime(new Date());
		courseMaterialService.updateById(courseMaterial);
        courseMaterial = courseMaterialService.selectView(new EntityWrapper<CourseMaterialEntity>().eq("cm.id", id));
        return R.ok().put("data", courseMaterial);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CourseMaterialEntity courseMaterial = courseMaterialService.selectById(id);
		courseMaterial.setClicknum(courseMaterial.getClicknum()+1);
		courseMaterial.setClicktime(new Date());
		courseMaterialService.updateById(courseMaterial);
        courseMaterial = courseMaterialService.selectView(new EntityWrapper<CourseMaterialEntity>().eq("cm.id", id));
        return R.ok().put("data", courseMaterial);
    }




    /**
     * 后端保存
     */
    @RequestMapping("/add")
    public R save(@RequestBody CourseMaterialEntity courseMaterial,String token){
        Long teacherId=JwtUtils.getUserIdFromToken(token);
        TeacherEntity teacher=teacherService.selectById(teacherId);
        courseMaterial.settUsername(teacher.getT_username());
        Date publish_at=new Date();
        courseMaterial.setPublishAt(publish_at);
        courseMaterialService.insert(courseMaterial);
        return R.ok("上传成功");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CourseMaterialEntity courseMaterial,String token){
        String role=JwtUtils.getRoleFromToken(token);
        Long id=JwtUtils.getUserIdFromToken(token);
        if(role.equals("管理员")||(role.equals("teacher")&&(teacherService.selectById(id).getT_username().equals(courseMaterialService.selectById(courseMaterial.getId()).gettUsername()))))
        {
            courseMaterialService.updateById(courseMaterial);//全部更新
            return R.ok("修改成功");
        }else{
            return R.error("无修改权限");
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        courseMaterialService.deleteBatchIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }
}
