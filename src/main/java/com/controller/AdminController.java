
package com.controller;


import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.service.TokenBlacklistService;
import com.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.annotation.IgnoreAuth;
import com.entity.AdminEntity;
import com.service.TokenService;
import com.service.AdminService;

/**
 * 登录相关
 */
@RequestMapping("users")
@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private TokenBlacklistService tokenBlacklistService;

	@IgnoreAuth
	@PostMapping("/login") // 建议明确请求方法
	public R login(
			@RequestParam String username,
			@RequestParam String password) {

		AdminEntity user = adminService.selectOne(new EntityWrapper<AdminEntity>().eq("username", username));
		if (user == null || !password.equals(user.getPassword())) {
			return R.error("账号或密码不正确");
		}

		String token = JwtUtils.generateToken(user.getId(), username, "users", user.getRole());
		return R.ok().put("token", token);
	}
	
	/**
	 * 注册
	 */
	@IgnoreAuth
	@PostMapping(value = "/register")
	public R register(@RequestBody AdminEntity user){
//    	ValidatorUtils.validateEntity(user);
    	if(adminService.selectOne(new EntityWrapper<AdminEntity>().eq("username", user.getUsername())) !=null) {
    		return R.error("用户已存在");
    	}
        adminService.insert(user);
        return R.ok("注册成功");
    }

	/**
	 * 退出
	 */
	@PostMapping(value = "/logout")
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
	@PostMapping("/resetPass")
	public R resetPass(
			@RequestParam String username,
			@RequestParam String newPassword,
			@RequestParam(required = false) String token) {
		AdminEntity targetUser = adminService.selectOne(new EntityWrapper<AdminEntity>().eq("username", username));
		if (targetUser == null) {
			return R.error("账号不存在");
		}

		// 权限验证
		if (token != null && !token.isEmpty()) {
			try {
				//  解析当前操作者信息
				Long currentUserId = JwtUtils.getUserIdFromToken(token);
				AdminEntity currentUser = adminService.selectById(currentUserId);

				// 检查权限：管理员或自己
				if (!"管理员".equals(currentUser.getRole()) &&
						!currentUser.getUsername().equals(username)) {
					return R.error("无权限重置他人密码");
				}
			} catch (Exception e) {
				// token无效时继续执行，允许自助重置
			}
		}

		// 更新密码
		targetUser.setPassword(newPassword);
		adminService.updateById(targetUser);

		return R.ok("密码重置为"+newPassword);
	}

	/**
	 * 分页列表
	 */
	@RequestMapping("/page")
	public R page(@RequestParam Map<String, Object> params, @RequestBody AdminEntity user) {
		EntityWrapper<AdminEntity> wrapper = new EntityWrapper<>();

		// 构建查询条件
		wrapper = buildQueryWrapper(wrapper, params, user);

		PageUtils page = adminService.queryPage(params, wrapper);

		// 移除密码字段
		if (page != null && page.getList() != null) {
			for (Object obj : page.getList()) {
				if (obj instanceof AdminEntity) {
					((AdminEntity) obj).setPassword(null);
				}
			}
		}

		return R.ok().put("data", page);
	}

	/**
	 * 全量列表
	 */
	@RequestMapping("/list")
	public R list(@RequestBody AdminEntity user) {
		EntityWrapper<AdminEntity> wrapper = new EntityWrapper<>();

		// 构建查询条件
		wrapper = buildQueryWrapper(wrapper, null,user);

		// 限制最大返回数量
		wrapper.last("LIMIT 1000");

		List<AdminEntity> list = adminService.selectListView(wrapper);

		// 移除密码字段
		list.forEach(u -> u.setPassword(null));

		return R.ok().put("data", list);
	}

	/**
	 * 构建查询条件
	 */
	private EntityWrapper<AdminEntity> buildQueryWrapper(EntityWrapper<AdminEntity> wrapper,
														 Map<String, Object> params,
														 AdminEntity user) {
		// 精确匹配条件
		if (user != null) {
			if (user.getId() != null) {
				wrapper.eq("id", user.getId());
			}
			if (StringUtils.isNotBlank(user.getUsername())) {
				wrapper.eq("username", user.getUsername());
			}
			if (StringUtils.isNotBlank(user.getRole())) {
				wrapper.eq("role", user.getRole());
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
     * 信息
     */
    @RequestMapping("/info")
    public R info(String token) {
		try {
			// 直接使用工具类从token获取用户ID
			Long userId = JwtUtils.getUserIdFromToken(token);
			AdminEntity user = adminService.selectById(userId);
			return R.ok().put("data", user);
		} catch (Exception e) {
			return R.error(401, "token解析失败");
		}
	}

	/**
	 * 修改用户信息
	 */
	@RequestMapping("/update")
	public R update(@RequestBody AdminEntity user) {

		if (user.getId() == null) {
			return R.error("用户ID不能为空");
		}

		AdminEntity existingUser = adminService.selectById(user.getId());
		if (existingUser == null) {
			return R.error("要更新的用户不存在");
		}
		// 检查用户名是否已存在（排除自身）
		EntityWrapper<AdminEntity> wrapper = new EntityWrapper<>();
		wrapper.eq("username", user.getUsername());
		if (user.getId() != null) {
			wrapper.ne("id", user.getId());
		}

		// 如果密码不为空，加密密码
		if (StringUtils.isNotBlank(user.getPassword())) {
			user.setPassword(user.getPassword());
		} else {
			// 保留原密码
			AdminEntity originalUser = adminService.selectById(user.getId());
			if (originalUser != null) {
				user.setPassword(originalUser.getPassword());
			}
		}

		adminService.updateById(user);
		return R.ok("更新成功");
	}

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        adminService.deleteBatchIds(Arrays.asList(ids));
        return R.ok("删除成功");
    }
}
