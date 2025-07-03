package com.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;

import com.entity.CourseCategoriesEntity;
import com.entity.view.CourseCategoriesView;

import com.service.CourseCategoryService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 课程类别
 * 后端接口
 */
@RestController
@RequestMapping("/CourseCategories")
public class CourseCategoriesController {
    @Autowired
    private CourseCategoryService courseCategoryService;

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, CourseCategoriesEntity courseEntity,
                  HttpServletRequest request){
        EntityWrapper<CourseCategoriesEntity> ew = new EntityWrapper<CourseCategoriesEntity>();

        PageUtils page = courseCategoryService.queryPage(params,
                MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, courseEntity), params), params));

        return R.ok().put("data", page);
    }

    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, CourseCategoriesEntity courseEntity,
                  HttpServletRequest request){
        EntityWrapper<CourseCategoriesEntity> ew = new EntityWrapper<CourseCategoriesEntity>();

        PageUtils page = courseCategoryService.queryPage(params,
                MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, courseEntity), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @IgnoreAuth
    @RequestMapping("/lists")
    public R list(CourseCategoriesEntity courseEntity){
        // 查询所有数据
        EntityWrapper<CourseCategoriesEntity> ew = new EntityWrapper<CourseCategoriesEntity>();
        ew.allEq(MPUtil.allEQMapPre(courseEntity, "course"));
        // 查询列表
        List<CourseCategoriesEntity> list = courseCategoryService.selectList(ew);
        // 提取课程名称
        List<String> courseNames = list.stream()
                .map(CourseCategoriesEntity::getCourse)
                .collect(Collectors.toList());
        // 返回字符串数组
        return R.ok().put("data", courseNames);
    }

//    @IgnoreAuth
//    @RequestMapping("/lists")
//    public R list(CourseCategoriesEntity courseEntity){
//        EntityWrapper<CourseCategoriesEntity> ew = new EntityWrapper<CourseCategoriesEntity>();
//        ew.allEq(MPUtil.allEQMapPre(courseEntity, "course"));
//        return R.ok().put("data", courseCategoryService.selectListView(ew));
//    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CourseCategoriesEntity courseEntity){
        EntityWrapper<CourseCategoriesEntity> ew = new EntityWrapper<CourseCategoriesEntity>();
        ew.allEq(MPUtil.allEQMapPre(courseEntity, "course"));
        CourseCategoriesView courseView = courseCategoryService.selectView(ew);
        return R.ok("查询课程类别成功").put("data", courseView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CourseCategoriesEntity courseEntity = courseCategoryService.selectById(id);
        return R.ok().put("data", courseEntity);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CourseCategoriesEntity courseEntity = courseCategoryService.selectById(id);
        return R.ok().put("data", courseEntity);
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CourseCategoriesEntity courseEntity, HttpServletRequest request){
        if (courseCategoryService.selectCount(
                new EntityWrapper<CourseCategoriesEntity>()
                        .eq("course", courseEntity.getCourse())
        ) > 0) {
            return R.error("课程类别已存在");
        }
        courseCategoryService.insert(courseEntity);
        return R.ok();
    }

    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody CourseCategoriesEntity courseEntity, HttpServletRequest request){
        if (courseCategoryService.selectCount(
                new EntityWrapper<CourseCategoriesEntity>()
                        .eq("course", courseEntity.getCourse())
        ) > 0) {
            return R.error("课程类别已存在");
        }
        courseCategoryService.insert(courseEntity);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CourseCategoriesEntity courseEntity, HttpServletRequest request){
        if (courseCategoryService.selectCount(
                new EntityWrapper<CourseCategoriesEntity>()
                        .ne("id", courseEntity.getId())
                        .eq("course", courseEntity.getCourse())
        ) > 0) {
            return R.error("课程类别已存在");
        }
        courseCategoryService.updateById(courseEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        courseCategoryService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}
