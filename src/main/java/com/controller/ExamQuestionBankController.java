package com.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.TeacherEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.TeacherService;
import com.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;

import com.entity.ExamQuestionBankEntity;
import com.entity.view.ExamQuestionBankView;

import com.service.ExamquestionbankService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 试题库表
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@RestController
@RequestMapping("/questionbank")
public class ExamQuestionBankController {
    @Autowired
    private ExamquestionbankService examquestionbankService;
    @Autowired
    private TeacherService teacherService;




    



    /**
     * 后端列表
     */
    @RequestMapping("/list")
    public R page(@RequestParam Map<String, Object> params, ExamQuestionBankEntity examquestionbank,
                  @RequestParam String token){
        String role = JwtUtils.getRoleFromToken(token);
        if ("user_teacher".equals(role)) {
            // 教师只能查看自己发布的题库
            Long teacherId = JwtUtils.getUserIdFromToken(token);
            TeacherEntity teacher = teacherService.selectById(teacherId);
            if (teacher == null) {
                return R.error("无效教师身份");
            }
            examquestionbank.setT_username(teacher.getT_username());
        }
        EntityWrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();

		PageUtils page = examquestionbankService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examquestionbank), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/managerlist")
    public R list(@RequestParam Map<String, Object> params,
                  @RequestParam String token) {


        EntityWrapper<ExamQuestionBankEntity> ew = new EntityWrapper<>();
        // 添加title模糊查询
        String title = (String) params.get("title");
        if (title != null && !title.trim().isEmpty()) {
            ew.like("title", title.trim());
            System.out.println("【调试信息】添加title模糊查询条件：" + title);
        }

        // 解析用户角色
        String role = JwtUtils.getRoleFromToken(token);
        System.out.println("【调试信息】用户角色：" + role);
        if ("teacher".equals(role)) {
            // 教师只能查看自己发布的题库
            Long teacherId = JwtUtils.getUserIdFromToken(token);
            System.out.println("【调试信息】教师ID：" + teacherId);
            TeacherEntity teacher = teacherService.selectById(teacherId);
            if (teacher == null) {
                return R.error("无效教师身份");
            }
            // 添加限定条件
            ew.eq("t_username", teacher.getT_username());
            System.out.println("【调试信息】添加教师条件后的SQL：" + ew.getSqlSegment());
        }


        // 查询分页数据
        PageUtils page = examquestionbankService.queryPage(params, ew);
        // 打印 SQL 片段
        System.out.println("查询条件 SQL：" + ew.getSqlSegment());
        System.out.println("查询参数：" + ew.getParamNameValuePairs());
        // ✅ 打印数据到控制台
        System.out.println("【题库分页查询】当前页码：" + page.getCurrPage());
        System.out.println("【题库分页查询】每页数量：" + page.getPageSize());
        System.out.println("【题库分页查询】结果列表：");
        ObjectMapper mapper= new ObjectMapper();
        List<?> list = page.getList();
        if (list != null) {
            for (Object obj : page.getList()) {
                try {
                    System.out.println(mapper.writeValueAsString(obj));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return R.ok().put("data", page);
    }




    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ExamQuestionBankEntity examquestionbank){
       	EntityWrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
      	ew.allEq(MPUtil.allEQMapPre( examquestionbank, "examquestionbank")); 
        return R.ok().put("data", examquestionbankService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ExamQuestionBankEntity examquestionbank){
        EntityWrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
 		ew.allEq(MPUtil.allEQMapPre( examquestionbank, "examquestionbank")); 
		ExamQuestionBankView examquestionbankView =  examquestionbankService.selectView(ew);
		return R.ok("查询试题库表成功").put("data", examquestionbankView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ExamQuestionBankEntity examquestionbank = examquestionbankService.selectById(id);
        return R.ok().put("data", examquestionbank);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ExamQuestionBankEntity examquestionbank = examquestionbankService.selectById(id);
        return R.ok().put("data", examquestionbank);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ExamQuestionBankEntity examquestionbank, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(examquestionbank);
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("user_teacher")) {
            examquestionbank.setT_username((String)request.getSession().getAttribute("username"));
        }
        examquestionbankService.insert(examquestionbank);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/create")
    public R add(@RequestBody ExamQuestionBankEntity examquestionbank,@RequestHeader("Authorization") String authHeader){
        // 解析用户角色
        // 从 Authorization: Bearer xxx 中提取 token
        String token = authHeader.replace("Bearer ", "");
        String role = JwtUtils.getRoleFromToken(token);
        if ("teacher".equals(role)) {
            // 教师创建题库时，自动设置创建者为当前教师
            Long teacherId = JwtUtils.getUserIdFromToken(token);

            TeacherEntity teacher = teacherService.selectById(teacherId);
            if (teacher == null) {
                return R.error("无效教师身份");
            }
            // 设置题库创建者为当前教师
            examquestionbank.setT_username(teacher.getT_username());

            // 设置默认创建时间
            if (examquestionbank.getAddtime() == null) {
                examquestionbank.setAddtime(new Date());
            }
        }
        try {
            examquestionbankService.insert(examquestionbank);
            return R.ok().put("message", "题库创建成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("题库创建失败");
        }




    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        ExamQuestionBankEntity examquestionbank = examquestionbankService.selectOne(new EntityWrapper<ExamQuestionBankEntity>().eq("", username));
        return R.ok().put("data", examquestionbank);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody ExamQuestionBankEntity examquestionbank, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examquestionbank);
        examquestionbankService.updateById(examquestionbank);//全部更新
        return R.ok();
    }

    /**
     * 删除单个试题
     */
    @RequestMapping("/delete")
    public R delete(@RequestParam Long id) {
        boolean removed = examquestionbankService.deleteById(id);
        if (removed) {
            return R.ok();
        } else {
            return R.error("删除失败，试题不存在");
        }
    }


    

    /**
     * 批量删除试题
     */
    @RequestMapping("/delete_batch")
    public R delete(@RequestBody Long[] ids){
        examquestionbankService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params, ExamQuestionBankEntity examquestionbank, HttpServletRequest request, String pre){
        EntityWrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
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
		PageUtils page = examquestionbankService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examquestionbank), params), params));
        return R.ok().put("data", page);
    }
}
