package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.bean.BeanUtil;
import com.entity.StudentEntity;
import com.entity.TeacherEntity;
import com.service.StudentService;
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

import com.entity.CourseHomeworkReviewEntity;
import com.entity.view.CourseHomeworkReviewView;

import com.service.CourseHomeworkReviewService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 作业批改
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@RestController
@RequestMapping("/courseHomeworkReview")
public class CourseHomeworkReviewController {
    @Autowired
    private CourseHomeworkReviewService courseHomeworkReviewService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,
                  CourseHomeworkReviewEntity courseHomeworkReview,
                  @RequestParam(required = false) String tableName,
                  String token) {

        // 1. 安全处理token和tableName
        if (token == null) {
            return R.error("token不能为空");
        }
        Long id = JwtUtils.getUserIdFromToken(token);
        if (id == null) {
            return R.error("无效的token");
        }

        // 2. 避免空指针 - 使用常量在前的方式比较字符串
        if ("user_teacher".equals(tableName)) {
            TeacherEntity teacher = teacherService.selectById(id);
            if (teacher != null && teacher.getT_username() != null) {
                courseHomeworkReview.settUsername(teacher.getT_username());
            }
        }
        else if ("user_student".equals(tableName)) {
            StudentEntity student = studentService.selectById(id);
            if (student != null && student.getsUsername() != null) {
                courseHomeworkReview.setsUsername(student.getsUsername());
            }
        }

        // 3. 创建EntityWrapper并添加表别名
        EntityWrapper<CourseHomeworkReviewEntity> ew = new EntityWrapper<>();

        // 4. 增强MPUtil - 添加表别名支持
        ew = (EntityWrapper<CourseHomeworkReviewEntity>) MPUtil.likeOrEqWithAlias(ew, courseHomeworkReview, "chr");
        ew = (EntityWrapper<CourseHomeworkReviewEntity>) MPUtil.betweenWithAlias(ew, params, "chr");
        ew = (EntityWrapper<CourseHomeworkReviewEntity>) MPUtil.sortWithAlias(ew, params, "chr");

        PageUtils page = courseHomeworkReviewService.queryPage(params, ew);
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, CourseHomeworkReviewEntity homeworkReview){
        EntityWrapper<CourseHomeworkReviewEntity> ew = new EntityWrapper<CourseHomeworkReviewEntity>();

		PageUtils page = courseHomeworkReviewService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew,homeworkReview), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( CourseHomeworkReviewEntity homeworkReview){
       	EntityWrapper<CourseHomeworkReviewEntity> ew = new EntityWrapper<CourseHomeworkReviewEntity>();
      	ew.allEq(MPUtil.allEQMapPre( homeworkReview, "chr"));
        return R.ok().put("data", courseHomeworkReviewService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CourseHomeworkReviewEntity homeworkReview){
        EntityWrapper<CourseHomeworkReviewEntity> ew = new EntityWrapper<CourseHomeworkReviewEntity>();
 		ew.allEq(MPUtil.allEQMapPre( homeworkReview, "chr"));
		CourseHomeworkReviewView homeworkReviewView =  courseHomeworkReviewService.selectView(ew);
		return R.ok("查询作业批改成功").put("data", homeworkReviewView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CourseHomeworkReviewEntity zuoyepigai = courseHomeworkReviewService.selectById(id);
        return R.ok().put("data", zuoyepigai);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CourseHomeworkReviewEntity homeworkReview = courseHomeworkReviewService.selectById(id);
        return R.ok().put("data", homeworkReview);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/add")
    public R save(@RequestBody CourseHomeworkReviewEntity homeworkReview,String token){
        Long id=JwtUtils.getUserIdFromToken(token);
        String role=JwtUtils.getRoleFromToken(token);
        if(role.equals("teacher")){
            homeworkReview.settUsername(teacherService.selectById(id).getT_username());
        }
        homeworkReview.setReviewedAt(new Date());
        courseHomeworkReviewService.insert(homeworkReview);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CourseHomeworkReviewEntity homeworkReview){
        courseHomeworkReviewService.updateById(homeworkReview);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        courseHomeworkReviewService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	






    /**
     * （按值统计）
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}")
    public R value(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        EntityWrapper<CourseHomeworkReviewEntity> ew = new EntityWrapper<CourseHomeworkReviewEntity>();
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaoshi")) {
            ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("xuesheng")) {
            ew.eq("xueshengzhanghao", (String)request.getSession().getAttribute("username"));
		}
        List<Map<String, Object>> result = courseHomeworkReviewService.selectValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

    /**
     * （按值统计(多)）
     */
    @RequestMapping("/valueMul/{xColumnName}")
    public R valueMul(@PathVariable("xColumnName") String xColumnName,@RequestParam String yColumnNameMul, HttpServletRequest request) {
        String[] yColumnNames = yColumnNameMul.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        List<List<Map<String, Object>>> result2 = new ArrayList<List<Map<String,Object>>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        EntityWrapper<CourseHomeworkReviewEntity> ew = new EntityWrapper<CourseHomeworkReviewEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("jiaoshi")) {
            ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("xuesheng")) {
            ew.eq("xueshengzhanghao", (String)request.getSession().getAttribute("username"));
        }
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = courseHomeworkReviewService.selectValue(params, ew);
            for(Map<String, Object> m : result) {
                for(String k : m.keySet()) {
                    if(m.get(k) instanceof Date) {
                        m.put(k, sdf.format((Date)m.get(k)));
                    }
                }
            }
            result2.add(result);
        }
        return R.ok().put("data", result2);
    }

    /**
     * （按值统计）时间统计类型
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}/{timeStatType}")
    public R valueDay(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        params.put("timeStatType", timeStatType);
        EntityWrapper<CourseHomeworkReviewEntity> ew = new EntityWrapper<CourseHomeworkReviewEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("jiaoshi")) {
            ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("xuesheng")) {
            ew.eq("xueshengzhanghao", (String)request.getSession().getAttribute("username"));
        }
        List<Map<String, Object>> result = courseHomeworkReviewService.selectTimeStatValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

    /**
     * （按值统计）时间统计类型(多)
     */
    @RequestMapping("/valueMul/{xColumnName}/{timeStatType}")
    public R valueMulDay(@PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType,@RequestParam String yColumnNameMul,HttpServletRequest request) {
        String[] yColumnNames = yColumnNameMul.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("timeStatType", timeStatType);
        List<List<Map<String, Object>>> result2 = new ArrayList<List<Map<String,Object>>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        EntityWrapper<CourseHomeworkReviewEntity> ew = new EntityWrapper<CourseHomeworkReviewEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("jiaoshi")) {
            ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("xuesheng")) {
            ew.eq("xueshengzhanghao", (String)request.getSession().getAttribute("username"));
        }
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = courseHomeworkReviewService.selectTimeStatValue(params, ew);
            for(Map<String, Object> m : result) {
                for(String k : m.keySet()) {
                    if(m.get(k) instanceof Date) {
                        m.put(k, sdf.format((Date)m.get(k)));
                    }
                }
            }
            result2.add(result);
        }
        return R.ok().put("data", result2);
    }

    /**
     * 分组统计
     */
    @RequestMapping("/group/{columnName}")
    public R group(@PathVariable("columnName") String columnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("column", columnName);
        EntityWrapper<CourseHomeworkReviewEntity> ew = new EntityWrapper<CourseHomeworkReviewEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("jiaoshi")) {
            ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
        }
        if(tableName.equals("xuesheng")) {
            ew.eq("xueshengzhanghao", (String)request.getSession().getAttribute("username"));
        }
        List<Map<String, Object>> result = courseHomeworkReviewService.selectGroup(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }







}
