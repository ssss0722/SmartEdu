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

import com.entity.CourseCategoriesEntity;
import com.entity.view.CourseCategoriesView;

import com.service.KechengleibieService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 课程类别
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
@RestController
@RequestMapping("/kechengleibie")
public class CourseCategoriesController {
    @Autowired
    private KechengleibieService kechengleibieService;




    



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, CourseCategoriesEntity kechengleibie,
                  HttpServletRequest request){
        EntityWrapper<CourseCategoriesEntity> ew = new EntityWrapper<CourseCategoriesEntity>();

		PageUtils page = kechengleibieService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, kechengleibie), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, CourseCategoriesEntity kechengleibie,
                  HttpServletRequest request){
        EntityWrapper<CourseCategoriesEntity> ew = new EntityWrapper<CourseCategoriesEntity>();

		PageUtils page = kechengleibieService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, kechengleibie), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( CourseCategoriesEntity kechengleibie){
       	EntityWrapper<CourseCategoriesEntity> ew = new EntityWrapper<CourseCategoriesEntity>();
      	ew.allEq(MPUtil.allEQMapPre( kechengleibie, "kechengleibie")); 
        return R.ok().put("data", kechengleibieService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CourseCategoriesEntity kechengleibie){
        EntityWrapper<CourseCategoriesEntity> ew = new EntityWrapper<CourseCategoriesEntity>();
 		ew.allEq(MPUtil.allEQMapPre( kechengleibie, "kechengleibie")); 
		CourseCategoriesView kechengleibieView =  kechengleibieService.selectView(ew);
		return R.ok("查询课程类别成功").put("data", kechengleibieView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CourseCategoriesEntity kechengleibie = kechengleibieService.selectById(id);
        return R.ok().put("data", kechengleibie);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CourseCategoriesEntity kechengleibie = kechengleibieService.selectById(id);
        return R.ok().put("data", kechengleibie);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CourseCategoriesEntity kechengleibie, HttpServletRequest request){
        if(kechengleibieService.selectCount(new EntityWrapper<CourseCategoriesEntity>().eq("kechengleibie", kechengleibie.getKechengleibie()))>0) {
            return R.error("课程类别已存在");
        }
    	//ValidatorUtils.validateEntity(kechengleibie);
        kechengleibieService.insert(kechengleibie);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody CourseCategoriesEntity kechengleibie, HttpServletRequest request){
        if(kechengleibieService.selectCount(new EntityWrapper<CourseCategoriesEntity>().eq("kechengleibie", kechengleibie.getKechengleibie()))>0) {
            return R.error("课程类别已存在");
        }
    	//ValidatorUtils.validateEntity(kechengleibie);
        kechengleibieService.insert(kechengleibie);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CourseCategoriesEntity kechengleibie, HttpServletRequest request){
        //ValidatorUtils.validateEntity(kechengleibie);
        if(kechengleibieService.selectCount(new EntityWrapper<CourseCategoriesEntity>().ne("id", kechengleibie.getId()).eq("kechengleibie", kechengleibie.getKechengleibie()))>0) {
            return R.error("课程类别已存在");
        }
        kechengleibieService.updateById(kechengleibie);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        kechengleibieService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	










}
