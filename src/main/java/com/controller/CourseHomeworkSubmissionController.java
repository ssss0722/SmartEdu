package com.controller;

import java.util.Arrays;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.entity.CourseHomeworkSubmissionEntity;
import com.entity.view.CourseHomeworkSubmissionView;

import com.service.ZuoyetijiaoService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 作业提交
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@RestController
@RequestMapping("/zuoyetijiao")
public class CourseHomeworkSubmissionController {
    @Autowired
    private ZuoyetijiaoService zuoyetijiaoService;




    



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, CourseHomeworkSubmissionEntity zuoyetijiao,
                  HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaoshi")) {
			zuoyetijiao.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("xuesheng")) {
			zuoyetijiao.setXueshengzhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<CourseHomeworkSubmissionEntity> ew = new EntityWrapper<CourseHomeworkSubmissionEntity>();

		PageUtils page = zuoyetijiaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, zuoyetijiao), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, CourseHomeworkSubmissionEntity zuoyetijiao,
                  HttpServletRequest request){
        EntityWrapper<CourseHomeworkSubmissionEntity> ew = new EntityWrapper<CourseHomeworkSubmissionEntity>();

		PageUtils page = zuoyetijiaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, zuoyetijiao), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( CourseHomeworkSubmissionEntity zuoyetijiao){
       	EntityWrapper<CourseHomeworkSubmissionEntity> ew = new EntityWrapper<CourseHomeworkSubmissionEntity>();
      	ew.allEq(MPUtil.allEQMapPre( zuoyetijiao, "zuoyetijiao")); 
        return R.ok().put("data", zuoyetijiaoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CourseHomeworkSubmissionEntity zuoyetijiao){
        EntityWrapper<CourseHomeworkSubmissionEntity> ew = new EntityWrapper<CourseHomeworkSubmissionEntity>();
 		ew.allEq(MPUtil.allEQMapPre( zuoyetijiao, "zuoyetijiao")); 
		CourseHomeworkSubmissionView zuoyetijiaoView =  zuoyetijiaoService.selectView(ew);
		return R.ok("查询作业提交成功").put("data", zuoyetijiaoView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CourseHomeworkSubmissionEntity zuoyetijiao = zuoyetijiaoService.selectById(id);
        return R.ok().put("data", zuoyetijiao);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CourseHomeworkSubmissionEntity zuoyetijiao = zuoyetijiaoService.selectById(id);
        return R.ok().put("data", zuoyetijiao);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CourseHomeworkSubmissionEntity zuoyetijiao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(zuoyetijiao);
        zuoyetijiaoService.insert(zuoyetijiao);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody CourseHomeworkSubmissionEntity zuoyetijiao, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(zuoyetijiao);
        zuoyetijiaoService.insert(zuoyetijiao);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CourseHomeworkSubmissionEntity zuoyetijiao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(zuoyetijiao);
        zuoyetijiaoService.updateById(zuoyetijiao);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        zuoyetijiaoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	










}
