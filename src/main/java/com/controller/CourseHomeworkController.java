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
import com.annotation.IgnoreAuth;

import com.entity.CourseHomeworkEntity;
import com.entity.view.CourseHomeworkView;

import com.service.KechengzuoyeService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.service.StoreupService;

/**
 * 课程作业
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@RestController
@RequestMapping("/kechengzuoye")
public class CourseHomeworkController {
    @Autowired
    private KechengzuoyeService kechengzuoyeService;

    @Autowired
    private StoreupService storeupService;



    



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, CourseHomeworkEntity kechengzuoye,
                  HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaoshi")) {
			kechengzuoye.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<CourseHomeworkEntity> ew = new EntityWrapper<CourseHomeworkEntity>();

		PageUtils page = kechengzuoyeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, kechengzuoye), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, CourseHomeworkEntity kechengzuoye,
                  HttpServletRequest request){
        EntityWrapper<CourseHomeworkEntity> ew = new EntityWrapper<CourseHomeworkEntity>();

		PageUtils page = kechengzuoyeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, kechengzuoye), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( CourseHomeworkEntity kechengzuoye){
       	EntityWrapper<CourseHomeworkEntity> ew = new EntityWrapper<CourseHomeworkEntity>();
      	ew.allEq(MPUtil.allEQMapPre( kechengzuoye, "kechengzuoye")); 
        return R.ok().put("data", kechengzuoyeService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CourseHomeworkEntity kechengzuoye){
        EntityWrapper<CourseHomeworkEntity> ew = new EntityWrapper<CourseHomeworkEntity>();
 		ew.allEq(MPUtil.allEQMapPre( kechengzuoye, "kechengzuoye")); 
		CourseHomeworkView kechengzuoyeView =  kechengzuoyeService.selectView(ew);
		return R.ok("查询课程作业成功").put("data", kechengzuoyeView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CourseHomeworkEntity kechengzuoye = kechengzuoyeService.selectById(id);
        return R.ok().put("data", kechengzuoye);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CourseHomeworkEntity kechengzuoye = kechengzuoyeService.selectById(id);
        return R.ok().put("data", kechengzuoye);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CourseHomeworkEntity kechengzuoye, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(kechengzuoye);
        kechengzuoyeService.insert(kechengzuoye);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody CourseHomeworkEntity kechengzuoye, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(kechengzuoye);
        kechengzuoyeService.insert(kechengzuoye);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CourseHomeworkEntity kechengzuoye, HttpServletRequest request){
        //ValidatorUtils.validateEntity(kechengzuoye);
        kechengzuoyeService.updateById(kechengzuoye);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        kechengzuoyeService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	










}
