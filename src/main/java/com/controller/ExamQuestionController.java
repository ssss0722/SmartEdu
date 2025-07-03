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

import com.entity.ExamQuestionEntity;
import com.entity.view.ExamQuestionView;

import com.service.ExamquestionService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 试题表
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@RestController
@RequestMapping("/examquestion")
public class ExamQuestionController {
    @Autowired
    private ExamquestionService examquestionService;




    



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, ExamQuestionEntity examquestion,
                  HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("user_teacher")) {
			examquestion.setT_username((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<ExamQuestionEntity> ew = new EntityWrapper<ExamQuestionEntity>();

		PageUtils page = examquestionService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examquestion), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, ExamQuestionEntity examquestion,
                  HttpServletRequest request){
        EntityWrapper<ExamQuestionEntity> ew = new EntityWrapper<ExamQuestionEntity>();

		PageUtils page = examquestionService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examquestion), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ExamQuestionEntity examquestion){
       	EntityWrapper<ExamQuestionEntity> ew = new EntityWrapper<ExamQuestionEntity>();
      	ew.allEq(MPUtil.allEQMapPre( examquestion, "examquestion")); 
        return R.ok().put("data", examquestionService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ExamQuestionEntity examquestion){
        EntityWrapper<ExamQuestionEntity> ew = new EntityWrapper<ExamQuestionEntity>();
 		ew.allEq(MPUtil.allEQMapPre( examquestion, "examquestion")); 
		ExamQuestionView examquestionView =  examquestionService.selectView(ew);
		return R.ok("查询试题表成功").put("data", examquestionView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ExamQuestionEntity examquestion = examquestionService.selectById(id);
        return R.ok().put("data", examquestion);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ExamQuestionEntity examquestion = examquestionService.selectById(id);
        return R.ok().put("data", examquestion);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ExamQuestionEntity examquestion, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(examquestion);
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("user_teacher")) {
            examquestion.setT_username((String)request.getSession().getAttribute("username"));
        }
        examquestionService.insert(examquestion);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ExamQuestionEntity examquestion, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(examquestion);
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("user_teacher")) {
            examquestion.setT_username((String)request.getSession().getAttribute("username"));
        }
        examquestionService.insert(examquestion);
        return R.ok();
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        ExamQuestionEntity examquestion = examquestionService.selectOne(new EntityWrapper<ExamQuestionEntity>().eq("", username));
        return R.ok().put("data", examquestion);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody ExamQuestionEntity examquestion, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examquestion);
        examquestionService.updateById(examquestion);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        examquestionService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params, ExamQuestionEntity examquestion, HttpServletRequest request, String pre){
        EntityWrapper<ExamQuestionEntity> ew = new EntityWrapper<ExamQuestionEntity>();
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
		PageUtils page = examquestionService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examquestion), params), params));
        return R.ok().put("data", page);
    }










}
