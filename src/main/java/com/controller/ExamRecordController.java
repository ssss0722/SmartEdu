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

import com.entity.ExamRecordEntity;
import com.entity.view.ExamRecordView;

import com.service.ExamrecordService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.service.ExampaperService;
import com.entity.ExamPaperEntity;

/**
 * 测试记录表
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@RestController
@RequestMapping("/examrecord")
public class ExamRecordController {
    @Autowired
    private ExamrecordService examrecordService;
    @Autowired
    private ExampaperService exampaperService;




    

   	    /**
     * 考试记录接口
     */
    @RequestMapping("/groupby")
    public R page2(@RequestParam Map<String, Object> params, ExamRecordEntity examrecord, HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaoshi")) {
			examrecord.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
		}
		else {
            if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
                examrecord.setUserid((Long)request.getSession().getAttribute("userId"));
            }
        }
        EntityWrapper<ExamRecordEntity> ew = new EntityWrapper<ExamRecordEntity>();
		PageUtils page = examrecordService.queryPageGroupBy(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examrecord), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 选项统计接口
     */
    @RequestMapping("/options/num")
    public R optionsNum(@RequestParam Map<String, Object> params, ExamRecordEntity examrecord, HttpServletRequest request){
        EntityWrapper<ExamRecordEntity> ew = new EntityWrapper<ExamRecordEntity>();
        PageUtils page = examrecordService.queryPageOptionsNum(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examrecord), params), params));
        return R.ok().put("data", page);
    }


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, ExamRecordEntity examrecord,
                  HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaoshi")) {
			examrecord.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
		}
		else {
            if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
                examrecord.setUserid((Long)request.getSession().getAttribute("userId"));
            }
        }
        EntityWrapper<ExamRecordEntity> ew = new EntityWrapper<ExamRecordEntity>();

		PageUtils page = examrecordService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examrecord), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, ExamRecordEntity examrecord,
                  HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaoshi")) {
			examrecord.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<ExamRecordEntity> ew = new EntityWrapper<ExamRecordEntity>();

		PageUtils page = examrecordService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examrecord), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ExamRecordEntity examrecord){
       	EntityWrapper<ExamRecordEntity> ew = new EntityWrapper<ExamRecordEntity>();
      	ew.allEq(MPUtil.allEQMapPre( examrecord, "examrecord")); 
        return R.ok().put("data", examrecordService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ExamRecordEntity examrecord){
        EntityWrapper<ExamRecordEntity> ew = new EntityWrapper<ExamRecordEntity>();
 		ew.allEq(MPUtil.allEQMapPre( examrecord, "examrecord")); 
		ExamRecordView examrecordView =  examrecordService.selectView(ew);
		return R.ok("查询测试记录表成功").put("data", examrecordView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ExamRecordEntity examrecord = examrecordService.selectById(id);
        return R.ok().put("data", examrecord);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ExamRecordEntity examrecord = examrecordService.selectById(id);
        return R.ok().put("data", examrecord);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ExamRecordEntity examrecord, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(examrecord);
    	examrecord.setUserid((Long)request.getSession().getAttribute("userId"));
        ExamPaperEntity exampaper = exampaperService.selectById(examrecord.getPaperid());
        examrecord.setJiaoshigonghao(exampaper.getJiaoshigonghao());
        examrecordService.insert(examrecord);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ExamRecordEntity examrecord, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(examrecord);
    	examrecord.setUserid((Long)request.getSession().getAttribute("userId"));
        ExamPaperEntity exampaper = exampaperService.selectById(examrecord.getPaperid());
        examrecord.setJiaoshigonghao(exampaper.getJiaoshigonghao());
        examrecordService.insert(examrecord);
        return R.ok();
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        ExamRecordEntity examrecord = examrecordService.selectOne(new EntityWrapper<ExamRecordEntity>().eq("", username));
        return R.ok().put("data", examrecord);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody ExamRecordEntity examrecord, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examrecord);
        examrecordService.updateById(examrecord);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        examrecordService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params, ExamRecordEntity examrecord, HttpServletRequest request, String pre){
        EntityWrapper<ExamRecordEntity> ew = new EntityWrapper<ExamRecordEntity>();
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
		PageUtils page = examrecordService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examrecord), params), params));
        return R.ok().put("data", page);
    }



    /**
     * 当重新考试时，删除考生的某个试卷的所有考试记录
     */
    @RequestMapping("/deleteRecords")
    public R deleteRecords(@RequestParam Long userid,@RequestParam Long paperid){
    	examrecordService.delete(new EntityWrapper<ExamRecordEntity>().eq("paperid", paperid).eq("userid", userid));
        return R.ok();
    }







}
