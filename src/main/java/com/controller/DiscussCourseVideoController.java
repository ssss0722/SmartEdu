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

import com.entity.DiscussCourseVideoEntity;
import com.entity.view.DiscussCourseVideoView;

import com.service.DiscussjiaoxueshipinService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 教学视频评论表
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@RestController
@RequestMapping("/discussjiaoxueshipin")
public class DiscussCourseVideoController {
    @Autowired
    private DiscussjiaoxueshipinService discussjiaoxueshipinService;




    



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, DiscussCourseVideoEntity discussjiaoxueshipin,
                  HttpServletRequest request){
        EntityWrapper<DiscussCourseVideoEntity> ew = new EntityWrapper<DiscussCourseVideoEntity>();

		PageUtils page = discussjiaoxueshipinService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussjiaoxueshipin), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, DiscussCourseVideoEntity discussjiaoxueshipin,
                  HttpServletRequest request){
        EntityWrapper<DiscussCourseVideoEntity> ew = new EntityWrapper<DiscussCourseVideoEntity>();

		PageUtils page = discussjiaoxueshipinService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussjiaoxueshipin), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( DiscussCourseVideoEntity discussjiaoxueshipin){
       	EntityWrapper<DiscussCourseVideoEntity> ew = new EntityWrapper<DiscussCourseVideoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( discussjiaoxueshipin, "discussjiaoxueshipin")); 
        return R.ok().put("data", discussjiaoxueshipinService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DiscussCourseVideoEntity discussjiaoxueshipin){
        EntityWrapper<DiscussCourseVideoEntity> ew = new EntityWrapper<DiscussCourseVideoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( discussjiaoxueshipin, "discussjiaoxueshipin")); 
		DiscussCourseVideoView discussjiaoxueshipinView =  discussjiaoxueshipinService.selectView(ew);
		return R.ok("查询教学视频评论表成功").put("data", discussjiaoxueshipinView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DiscussCourseVideoEntity discussjiaoxueshipin = discussjiaoxueshipinService.selectById(id);
        return R.ok().put("data", discussjiaoxueshipin);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        DiscussCourseVideoEntity discussjiaoxueshipin = discussjiaoxueshipinService.selectById(id);
        return R.ok().put("data", discussjiaoxueshipin);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DiscussCourseVideoEntity discussjiaoxueshipin, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(discussjiaoxueshipin);
        discussjiaoxueshipinService.insert(discussjiaoxueshipin);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody DiscussCourseVideoEntity discussjiaoxueshipin, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(discussjiaoxueshipin);
        discussjiaoxueshipinService.insert(discussjiaoxueshipin);
        return R.ok();
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        DiscussCourseVideoEntity discussjiaoxueshipin = discussjiaoxueshipinService.selectOne(new EntityWrapper<DiscussCourseVideoEntity>().eq("", username));
        return R.ok().put("data", discussjiaoxueshipin);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody DiscussCourseVideoEntity discussjiaoxueshipin, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussjiaoxueshipin);
        discussjiaoxueshipinService.updateById(discussjiaoxueshipin);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        discussjiaoxueshipinService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params, DiscussCourseVideoEntity discussjiaoxueshipin, HttpServletRequest request, String pre){
        EntityWrapper<DiscussCourseVideoEntity> ew = new EntityWrapper<DiscussCourseVideoEntity>();
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
		PageUtils page = discussjiaoxueshipinService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussjiaoxueshipin), params), params));
        return R.ok().put("data", page);
    }










}
