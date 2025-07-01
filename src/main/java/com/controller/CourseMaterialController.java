package com.controller;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.plugins.Page;
import com.entity.TeacherEntity;
import com.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
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
import com.service.StoreupService;
import com.entity.StoreupEntity;
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
    private StoreupService storeupService;

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
            ew.eq("t_username", teacher.getT_username());
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
 		ew.allEq(MPUtil.allEQMapPre( courseMaterial, "cm"));
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
        courseMaterial = courseMaterialService.selectView(new EntityWrapper<CourseMaterialEntity>().eq("id", id));
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
        courseMaterial = courseMaterialService.selectView(new EntityWrapper<CourseMaterialEntity>().eq("id", id));
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
        courseMaterial.settName(teacher.getT_name());
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


	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,
                      @ModelAttribute CourseMaterialEntity courseMaterial,
                      @RequestParam(required = false) String prefix){
        EntityWrapper<CourseMaterialEntity> ew = new EntityWrapper<CourseMaterialEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (prefix.endsWith(".")) {
				newMap.put(prefix + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(prefix)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(prefix + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicknum");
        params.put("order", "desc");
		PageUtils page = courseMaterialService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, courseMaterial), params), params));
        return R.ok().put("data", page);
    }


    /**
     * 协同算法（按收藏推荐）
     */
    @RequestMapping("/autoSort2")
    public R autoSort2(@RequestParam Map<String, Object> params, @ModelAttribute CourseMaterialEntity courseMaterial,String token){
        String userId = String.valueOf(JwtUtils.getUserIdFromToken(token));

        // 1. 从params获取分页参数（带默认值）
        int pageNum = Integer.parseInt(params.getOrDefault("page", "1").toString());
        int pageSize = Integer.parseInt(params.getOrDefault("limit", "10").toString());

        // 2. 获取用户收藏的课程类别
        List<StoreupEntity> storeups = storeupService.selectList(
                new EntityWrapper<StoreupEntity>()
                        .eq("type", 1)
                        .eq("userid", userId)
                        .eq("tablename", "course_material")
        );

        Set<String> userFavoriteCategories = storeups.stream()
                .map(StoreupEntity::getInteltype)
                .collect(Collectors.toSet());

        // 3. 构建查询条件
        EntityWrapper<CourseMaterialEntity> ew = new EntityWrapper<>();

        // 4. 添加类别过滤（如果有收藏类别）
        if (!userFavoriteCategories.isEmpty()) {
            ew.in("course", userFavoriteCategories);
        }

        // 5. 按收藏量降序排序
        ew.orderBy("storeupnum", false);

        // 6. 正确使用分页参数
        Page<CourseMaterialEntity> page = new Page<>(pageNum, pageSize);

        // 7. 执行分页查询
        Page<CourseMaterialEntity> result = courseMaterialService.selectPage(page, ew);

        return R.ok().put("data", new PageUtils(result));
    }
}
