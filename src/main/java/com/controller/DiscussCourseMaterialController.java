package com.controller;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import com.entity.CourseMaterialEntity;
import com.entity.TeacherEntity;
import com.service.CourseMaterialService;
import com.service.TeacherService;
import com.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;

import com.entity.DiscussCourseMaterialEntity;
import com.entity.view.DiscussCourseMaterialView;

import com.service.DiscussCourseMaterialService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 教学资料评论表
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@RestController
@RequestMapping("/discussCourseMaterial")
public class DiscussCourseMaterialController {
    @Autowired
    private DiscussCourseMaterialService discussCourseMaterialService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseMaterialService courseMaterialService;

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,
                  @RequestBody(required = false) DiscussCourseMaterialEntity discussCourseMaterial,
                  String token){
        Long userId= JwtUtils.getUserIdFromToken(token);
        String role=JwtUtils.getRoleFromToken(token);
        EntityWrapper<DiscussCourseMaterialEntity> ew = new EntityWrapper<DiscussCourseMaterialEntity>();
        if(role.equals("管理员")){
            
        } else if (role.equals("teacher")) {
            // 获取当前教师的工号
            TeacherEntity teacher = teacherService.selectById(userId);
            if(teacher != null) {
                String teacherUsername = teacher.getT_username();

                // 获取该教师上传的所有资料ID
                List<CourseMaterialEntity> materials = courseMaterialService.selectList(
                        new EntityWrapper<CourseMaterialEntity>()
                                .eq("t_username", teacherUsername)
                );

                if(!materials.isEmpty()) {
                    // 提取资料ID
                    List<Long> materialIds = materials.stream()
                            .map(CourseMaterialEntity::getId)
                            .collect(Collectors.toList());

                    // 只查询这些资料的评论
                    ew.in("refid", materialIds);
                } else {
                    // 如果教师没有上传任何资料，返回空结果
                    ew.eq("1", "0");
                }
            }
        }
        else {
            return R.error("无权限");
        }
        // 5. 添加其他查询条件
        ew = (EntityWrapper<DiscussCourseMaterialEntity>) MPUtil.likeOrEq(ew, discussCourseMaterial); // 使用正确的likeOrEq方法

        // 6. 处理范围查询
        ew = (EntityWrapper<DiscussCourseMaterialEntity>) MPUtil.between(ew, params);

        // 7. 处理排序
        ew = (EntityWrapper<DiscussCourseMaterialEntity>) MPUtil.sort(ew, params);

        // 8. 执行分页查询
        PageUtils page = discussCourseMaterialService.queryPage(params, ew);

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,
                  @RequestBody(required = false) DiscussCourseMaterialEntity discussCourseMaterial){
        EntityWrapper<DiscussCourseMaterialEntity> ew = new EntityWrapper<DiscussCourseMaterialEntity>();

		PageUtils page = discussCourseMaterialService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussCourseMaterial), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( DiscussCourseMaterialEntity discussCourseMaterial){
       	EntityWrapper<DiscussCourseMaterialEntity> ew = new EntityWrapper<DiscussCourseMaterialEntity>();
      	ew.allEq(MPUtil.allEQMapPre( discussCourseMaterial, "discusscm"));
        return R.ok().put("data", discussCourseMaterialService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DiscussCourseMaterialEntity discussjiaoxueziliao){
        EntityWrapper<DiscussCourseMaterialEntity> ew = new EntityWrapper<DiscussCourseMaterialEntity>();
 		ew.allEq(MPUtil.allEQMapPre( discussjiaoxueziliao, "discussjiaoxueziliao")); 
		DiscussCourseMaterialView discussjiaoxueziliaoView =  discussCourseMaterialService.selectView(ew);
		return R.ok("查询教学资料评论表成功").put("data", discussjiaoxueziliaoView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DiscussCourseMaterialEntity discussjiaoxueziliao = discussCourseMaterialService.selectById(id);
        return R.ok().put("data", discussjiaoxueziliao);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        DiscussCourseMaterialEntity discussjiaoxueziliao = discussCourseMaterialService.selectById(id);
        return R.ok().put("data", discussjiaoxueziliao);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DiscussCourseMaterialEntity discussjiaoxueziliao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(discussjiaoxueziliao);
        discussCourseMaterialService.insert(discussjiaoxueziliao);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody DiscussCourseMaterialEntity discussjiaoxueziliao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(discussjiaoxueziliao);
        discussCourseMaterialService.insert(discussjiaoxueziliao);
        return R.ok();
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        DiscussCourseMaterialEntity discussjiaoxueziliao = discussCourseMaterialService.selectOne(new EntityWrapper<DiscussCourseMaterialEntity>().eq("", username));
        return R.ok().put("data", discussjiaoxueziliao);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody DiscussCourseMaterialEntity discussjiaoxueziliao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussjiaoxueziliao);
        discussCourseMaterialService.updateById(discussjiaoxueziliao);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        discussCourseMaterialService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params, DiscussCourseMaterialEntity discussjiaoxueziliao, HttpServletRequest request, String pre){
        EntityWrapper<DiscussCourseMaterialEntity> ew = new EntityWrapper<DiscussCourseMaterialEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicktime");
        params.put("order", "desc");
		PageUtils page = discussCourseMaterialService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussjiaoxueziliao), params), params));
        return R.ok().put("data", page);
    }










}
