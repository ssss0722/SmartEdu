package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.CourseVideoEntity;
import com.entity.ScreenVideoEntity;
import com.service.ScreenVideoService;
import com.service.TeacherService;
import com.utils.JwtUtils;
import com.utils.MPUtil;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/screenVideo")
public class ScreenVideoController {

    @Autowired
    private ScreenVideoService screenVideoService;

    @Autowired
    private TeacherService teacherService;

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(ScreenVideoEntity video){
        EntityWrapper<ScreenVideoEntity> ew = new EntityWrapper<ScreenVideoEntity>();
        ew.allEq(MPUtil.allEQMapPre( video, "sv"));
        return R.ok().put("data", screenVideoService.selectListView(ew));
    }

    /**
     * 详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ScreenVideoEntity video = screenVideoService.selectById(id);
        screenVideoService.updateById(video);
        EntityWrapper<ScreenVideoEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("sv.id", id);
        video = screenVideoService.selectView(wrapper);
        return R.ok().put("data", video);
    }

    /**
     * 后端保存
     */
    @RequestMapping("/add")
    public R save(@RequestBody ScreenVideoEntity video, String token){
        Long id= JwtUtils.getUserIdFromToken(token);
        String role=JwtUtils.getRoleFromToken(token);
        if(role.equals("teacher"))
        {
            video.settUsername(teacherService.selectById(id).getT_username());
        }
        video.setAddtime(new Date());
        screenVideoService.insert(video);
        return R.ok("添加成功");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ScreenVideoEntity video){
        screenVideoService.updateById(video);//全部更新
        return R.ok("更新成功");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        screenVideoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }
}
