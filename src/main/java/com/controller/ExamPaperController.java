package com.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
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
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.ExamPaperEntity;
import com.entity.view.ExamPaperView;

import com.service.ExampaperService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.service.ExamquestionService;
import com.service.ExamquestionbankService;
import com.service.ExamrecordService;
import com.entity.ExamQuestionEntity;
import com.entity.ExamQuestionBankEntity;
import com.entity.ExamRecordEntity;

/**
 * 在线考试表
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@RestController
@RequestMapping("/exampaper")
public class ExamPaperController {
    @Autowired
    private ExampaperService exampaperService;




    @Autowired
    private ExamquestionService examquestionService;

    @Autowired
    private ExamquestionbankService examquestionbankService;

    @Autowired
    private ExamrecordService examrecordService;
    



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, ExamPaperEntity exampaper,
                  HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jiaoshi")) {
			exampaper.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<ExamPaperEntity> ew = new EntityWrapper<ExamPaperEntity>();

		PageUtils page = exampaperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, exampaper), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, ExamPaperEntity exampaper,
                  HttpServletRequest request){
        EntityWrapper<ExamPaperEntity> ew = new EntityWrapper<ExamPaperEntity>();

		PageUtils page = exampaperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, exampaper), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ExamPaperEntity exampaper){
       	EntityWrapper<ExamPaperEntity> ew = new EntityWrapper<ExamPaperEntity>();
      	ew.allEq(MPUtil.allEQMapPre( exampaper, "exampaper")); 
        return R.ok().put("data", exampaperService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ExamPaperEntity exampaper){
        EntityWrapper<ExamPaperEntity> ew = new EntityWrapper<ExamPaperEntity>();
 		ew.allEq(MPUtil.allEQMapPre( exampaper, "exampaper")); 
		ExamPaperView exampaperView =  exampaperService.selectView(ew);
		return R.ok("查询在线考试表成功").put("data", exampaperView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ExamPaperEntity exampaper = exampaperService.selectById(id);
        return R.ok().put("data", exampaper);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ExamPaperEntity exampaper = exampaperService.selectById(id);
        return R.ok().put("data", exampaper);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ExamPaperEntity exampaper, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(exampaper);
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("jiaoshi")) {
            exampaper.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
        }
        exampaperService.insert(exampaper);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ExamPaperEntity exampaper, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(exampaper);
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("jiaoshi")) {
            exampaper.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
        }
        exampaperService.insert(exampaper);
        return R.ok();
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        ExamPaperEntity exampaper = exampaperService.selectOne(new EntityWrapper<ExamPaperEntity>().eq("", username));
        return R.ok().put("data", exampaper);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody ExamPaperEntity exampaper, HttpServletRequest request){
        //ValidatorUtils.validateEntity(exampaper);
        exampaperService.updateById(exampaper);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        exampaperService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params, ExamPaperEntity exampaper, HttpServletRequest request, String pre){
        EntityWrapper<ExamPaperEntity> ew = new EntityWrapper<ExamPaperEntity>();
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
		PageUtils page = exampaperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, exampaper), params), params));
        return R.ok().put("data", page);
    }









    /**
     * 组卷
     */
    @RequestMapping("/compose")
    public R compose(HttpServletRequest request,@RequestParam Long paperid, @RequestParam String papername, @RequestParam Integer radioNum,
        @RequestParam Integer multipleChoiceNum, @RequestParam Integer determineNum, @RequestParam Integer fillNum, @RequestParam Integer subjectivityNum){
        //如果已存在考试记录，不能进行重新组卷
        if(examrecordService.selectCount(new EntityWrapper<ExamRecordEntity>().eq("paperid", paperid))>0) {
            return R.error("已存在考试记录，无法重新组卷");
        }
        //组卷之前删除该试卷之前的所有题目
        examquestionService.deleteByMap(new MapUtils().put("paperid", paperid));
        List<ExamQuestionBankEntity> questionList = new ArrayList<ExamQuestionBankEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        //单选题
        if(radioNum>0) {
            Integer radioSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 0));
            if(radioSize<radioNum) {
                return R.error("单选题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("jiaoshi")) {
                    ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
                }
                ew.eq("type", 0).orderBy("RAND()").last("limit "+radioNum);
                List<ExamQuestionBankEntity> radioList = examquestionbankService.selectList(ew);
                questionList.addAll(radioList);
            }
        }
        //多选题
        if(multipleChoiceNum>0) {
            Integer multipleChoiceSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 1));
            if(multipleChoiceSize<multipleChoiceNum) {
                return R.error("多选题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("jiaoshi")) {
                    ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
                }
                ew.eq("type", 1).orderBy("RAND()").last("limit "+multipleChoiceNum);
                List<ExamQuestionBankEntity> multipleChoiceList = examquestionbankService.selectList(ew);
                questionList.addAll(multipleChoiceList);
            }
        }
        //判断题
        if(determineNum>0) {
            Integer determineSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 2));
            if(determineSize<determineNum) {
                return R.error("判断题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("jiaoshi")) {
                    ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
                }
                ew.eq("type", 2).orderBy("RAND()").last("limit "+determineNum);
                List<ExamQuestionBankEntity> determineList = examquestionbankService.selectList(ew);
                questionList.addAll(determineList);
            }
        }
        //填空题
        if(fillNum>0) {
            Integer fillSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 3));
            if(fillSize<fillNum) {
                return R.error("填空题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("jiaoshi")) {
                    ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
                }
                ew.eq("type", 3).orderBy("RAND()").last("limit "+fillNum);
                List<ExamQuestionBankEntity> fillList = examquestionbankService.selectList(ew);
                questionList.addAll(fillList);
            }
        }
        //主观题
        if(subjectivityNum>0) {
            Integer subjectivitySize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 4));
            if(subjectivitySize<subjectivityNum) {
                return R.error("主观题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("jiaoshi")) {
                    ew.eq("jiaoshigonghao", (String)request.getSession().getAttribute("username"));
                }
                ew.eq("type", 4).orderBy("RAND()").last("limit "+subjectivityNum);
                List<ExamQuestionBankEntity> subjectivityList = examquestionbankService.selectList(ew);
                questionList.addAll(subjectivityList);
            }
        }
        if(questionList!=null && questionList.size()>0) {
            long seq = 0;
            for(ExamQuestionBankEntity q : questionList) {
                ExamQuestionEntity examquestion = new ExamQuestionEntity();
                examquestion.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
                examquestion.setPaperid(paperid);
                examquestion.setPapername(papername);
                examquestion.setQuestionname(q.getQuestionname());
                examquestion.setOptions(q.getOptions());
                examquestion.setScore(q.getScore());
                examquestion.setAnswer(q.getAnswer());
                examquestion.setAnalysis(q.getAnalysis());
                examquestion.setType(q.getType());
                examquestion.setSequence(++seq);
                if(tableName.equals("jiaoshi")) {
                    examquestion.setJiaoshigonghao((String)request.getSession().getAttribute("username"));
                }
                examquestionService.insert(examquestion);
            }
        }
        return R.ok();
    }

}
