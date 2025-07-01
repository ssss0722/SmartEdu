package com.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

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

import com.entity.DiscussCourseHomeworkEntity;
import com.entity.view.DiscussCourseHomeworkView;

import com.service.DiscusskechengzuoyeService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 课程作业评论表
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:25
 */
@RestController
@RequestMapping("/discusskechengzuoye")
public class DiscussCourseHomeworkController {
    @Autowired
    private DiscusskechengzuoyeService discusskechengzuoyeService;




    



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, DiscussCourseHomeworkEntity discusskechengzuoye,
                  HttpServletRequest request){
        EntityWrapper<DiscussCourseHomeworkEntity> ew = new EntityWrapper<DiscussCourseHomeworkEntity>();

		PageUtils page = discusskechengzuoyeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discusskechengzuoye), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, DiscussCourseHomeworkEntity discusskechengzuoye,
                  HttpServletRequest request){
        EntityWrapper<DiscussCourseHomeworkEntity> ew = new EntityWrapper<DiscussCourseHomeworkEntity>();

		PageUtils page = discusskechengzuoyeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discusskechengzuoye), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( DiscussCourseHomeworkEntity discusskechengzuoye){
       	EntityWrapper<DiscussCourseHomeworkEntity> ew = new EntityWrapper<DiscussCourseHomeworkEntity>();
      	ew.allEq(MPUtil.allEQMapPre( discusskechengzuoye, "discusskechengzuoye")); 
        return R.ok().put("data", discusskechengzuoyeService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DiscussCourseHomeworkEntity discusskechengzuoye){
        EntityWrapper<DiscussCourseHomeworkEntity> ew = new EntityWrapper<DiscussCourseHomeworkEntity>();
 		ew.allEq(MPUtil.allEQMapPre( discusskechengzuoye, "discusskechengzuoye")); 
		DiscussCourseHomeworkView discusskechengzuoyeView =  discusskechengzuoyeService.selectView(ew);
		return R.ok("查询课程作业评论表成功").put("data", discusskechengzuoyeView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DiscussCourseHomeworkEntity discusskechengzuoye = discusskechengzuoyeService.selectById(id);
        return R.ok().put("data", discusskechengzuoye);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        DiscussCourseHomeworkEntity discusskechengzuoye = discusskechengzuoyeService.selectById(id);
        return R.ok().put("data", discusskechengzuoye);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DiscussCourseHomeworkEntity discusskechengzuoye, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(discusskechengzuoye);
        discusskechengzuoyeService.insert(discusskechengzuoye);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody DiscussCourseHomeworkEntity discusskechengzuoye, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(discusskechengzuoye);
        discusskechengzuoyeService.insert(discusskechengzuoye);
        return R.ok();
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        DiscussCourseHomeworkEntity discusskechengzuoye = discusskechengzuoyeService.selectOne(new EntityWrapper<DiscussCourseHomeworkEntity>().eq("", username));
        return R.ok().put("data", discusskechengzuoye);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody DiscussCourseHomeworkEntity discusskechengzuoye, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discusskechengzuoye);
        discusskechengzuoyeService.updateById(discusskechengzuoye);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        discusskechengzuoyeService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params, DiscussCourseHomeworkEntity discusskechengzuoye, HttpServletRequest request, String pre){
        EntityWrapper<DiscussCourseHomeworkEntity> ew = new EntityWrapper<DiscussCourseHomeworkEntity>();
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
		PageUtils page = discusskechengzuoyeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discusskechengzuoye), params), params));
        return R.ok().put("data", page);
    }










}
