package com.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.plugins.Page;
import com.entity.AdminEntity;
import com.service.TokenBlacklistService;
import com.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;

import com.entity.TeacherEntity;
import com.entity.view.TeacherView;

import com.service.TeacherService;
import com.service.TokenService;

/**
 * 教师
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;
    
	@Autowired
	private TokenService tokenService;
	
	/**
	 * 登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/login")
	public R login(String username, String password) {
		TeacherEntity u = teacherService.selectOne(new EntityWrapper<TeacherEntity>().eq("t_username", username));
		if(u==null || !u.getPassword().equals(password)) {
			return R.error("账号或密码不正确");
		}

        String token = JwtUtils.generateToken(u.getId(), username, "teacher", "teacher");
        return R.ok().put("token", token);
	}


	
	/**
     * 注册
     */
	@IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody TeacherEntity teacher){
    	TeacherEntity u = teacherService.selectOne(new EntityWrapper<TeacherEntity>().eq("t_username", teacher.getT_username()));
		if(u!=null) {
			return R.error("注册用户已存在");
		}
		Long uId = new Date().getTime();
		teacher.setId(uId);
        teacherService.insert(teacher);
        return R.ok("注册成功");
    }

	
	/**
	 * 退出
	 */
	@RequestMapping("/logout")
	public R logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            // 将token加入黑名单
            tokenBlacklistService.addToBlacklist(token);
            return R.ok("退出成功");
        }
        return R.error(401, "无效token");
	}

    /**
     * 密码重置
     */
    @IgnoreAuth
	@RequestMapping(value = "/resetPass")
    public R resetPass(@RequestParam String username,
                       @RequestParam String newPassword,
                       @RequestParam(required = false) String token){
    	TeacherEntity u = teacherService.selectOne(new EntityWrapper<TeacherEntity>().eq("t_username", username));
    	if(u==null) {
    		return R.error("账号不存在");
    	}
        // 权限验证
        if (token != null && !token.isEmpty()) {
            try {
                //  解析当前操作者信息
                Long currentUserId = JwtUtils.getUserIdFromToken(token);
                TeacherEntity currentUser = teacherService.selectById(currentUserId);

                // 检查权限:自己
                if (!currentUser.getT_username().equals(username)) {
                    return R.error("无权限重置他人密码");
                }
            } catch (Exception e) {
                // token无效时继续执行，允许自助重置
            }
        }
        u.setPassword(newPassword);
        teacherService.updateById(u);
        return R.ok("密码已重置为："+newPassword);
    }



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, @RequestBody TeacherEntity teacher){
        EntityWrapper<TeacherEntity> wrapper = new EntityWrapper<>();

        // 构建查询条件
        wrapper = buildQueryWrapper(wrapper, params, teacher);

        PageUtils page = teacherService.queryPage(params, wrapper);

        // 移除密码字段
        if (page != null && page.getList() != null) {
            for (Object obj : page.getList()) {
                if (obj instanceof TeacherEntity) {
                    ((TeacherEntity) obj).setPassword(null);
                }
            }
        }

        return R.ok().put("data", page);
    }

    private EntityWrapper<TeacherEntity> buildQueryWrapper(EntityWrapper<TeacherEntity> wrapper,
                                                         Map<String, Object> params,
                                                         TeacherEntity teacher) {
        // 精确匹配条件
        if (teacher != null) {
            if (teacher.getId() != null) {
                wrapper.eq("id", teacher.getId());
            }
            if (StringUtils.isNotBlank(teacher.getT_username())) {
                wrapper.eq("t_username", teacher.getT_username());
            }
        }

        // 范围查询（来自params）
        if (params != null) {
            // 时间范围查询
            if (params.get("addtime_start") != null) {
                wrapper.ge("addtime", params.get("addtime_start"));
            }
            if (params.get("addtime_end") != null) {
                wrapper.le("addtime", params.get("addtime_end"));
            }

            // 排序处理
            if (params.get("order") != null && params.get("sort") != null) {
                String order = params.get("order").toString();
                String sort = params.get("sort").toString();

                if ("asc".equalsIgnoreCase(order)) {
                    wrapper.orderBy(sort, true);
                } else {
                    wrapper.orderBy(sort, false);
                }
            }
        }

        return wrapper;
    }

    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestBody TeacherEntity teacher){
        EntityWrapper<TeacherEntity> wrapper = new EntityWrapper<>();

        // 构建查询条件
        wrapper = buildQueryWrapper(wrapper, null, teacher);

        // 限制最大返回数量
        wrapper.last("LIMIT 1000");

        List<TeacherView> list = teacherService.selectListView(wrapper);

        // 移除密码字段
        list.forEach(u -> u.setPassword(null));

        return R.ok().put("data", list);
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(@RequestBody TeacherEntity teacher,
                   @RequestParam Map<String, Object> params) {

        EntityWrapper<TeacherEntity> ew = new EntityWrapper<>();

        // 1. 模糊查询 + 等值查询 (核心修改)
        ew = (EntityWrapper<TeacherEntity>) MPUtil.likeOrEq(ew, teacher);

        // 2. 范围查询 (如时间范围)
        ew = (EntityWrapper<TeacherEntity>) MPUtil.between(ew, params);

        // 3. 排序支持
        ew = (EntityWrapper<TeacherEntity>) MPUtil.sort(ew, params);

        // 4. 分页支持 (示例)
        Page<TeacherEntity> page = new Page<>(1, 10); // 默认第1页，10条
        if(params.get("page") != null && params.get("limit") != null) {
            page = new Page<>(
                    Integer.parseInt(params.get("page").toString()),
                    Integer.parseInt(params.get("limit").toString()
                    ));
        }

        // 5. 查询列表 (原selectView改为分页查询)
        Page<TeacherEntity> pageResult = teacherService.selectPage(page, ew);

        return R.ok("查询教师成功")
                .put("data", pageResult.getRecords())
                .put("count", pageResult.getTotal());
    }
	
    /**
     * 获取用户信息
     */
    @RequestMapping("/info")
    public R info(String token){
        try {
            Long userId = JwtUtils.getUserIdFromToken(token);
            TeacherEntity teacher = teacherService.selectById(userId);
            return R.ok().put("data", teacher);
        }catch (Exception e) {
            return R.error(401, "token解析失败");
        }
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody TeacherEntity teacher){
        if (teacher.getId() == null) {
            return R.error("用户ID不能为空");
        }

        TeacherEntity existingUser = teacherService.selectById(teacher.getId());
        if (existingUser == null) {
            return R.error("要更新的用户不存在");
        }
        // 检查用户名是否已存在（排除自身）
        EntityWrapper<TeacherEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("t_username", teacher.getT_username());
        if (teacher.getId() != null) {
            wrapper.ne("id", teacher.getId());
        }

        // 如果密码不为空，加密密码
        if (StringUtils.isNotBlank(teacher.getPassword())) {
            teacher.setPassword(teacher.getPassword());
        } else {
            // 保留原密码
            TeacherEntity originalUser = teacherService.selectById(teacher.getId());
            if (originalUser != null) {
                teacher.setPassword(originalUser.getPassword());
            }
        }

        teacherService.updateById(teacher);
        return R.ok("更新成功");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        teacherService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
