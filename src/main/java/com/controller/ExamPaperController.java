package com.controller;

import java.util.ArrayList;
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
import com.service.ExamquestionService;
import com.service.ExamquestionbankService;
import com.service.ExamRecordService;
import com.entity.ExamQuestionEntity;
import com.entity.ExamQuestionBankEntity;

/**
 * 在线考试表
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@RestController
@RequestMapping("/api/paper")
public class ExamPaperController {
    @Autowired
    private ExampaperService exampaperService;




    @Autowired
    private ExamquestionService examquestionService;

    @Autowired
    private ExamquestionbankService examquestionbankService;

    @Autowired
    private ExamRecordService examrecordService;
    



    /**
     * 后端列表
     */
    @RequestMapping("/list")
    public R page(@RequestParam Map<String, Object> params, ExamPaperEntity exampaper,
                  HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("user_teacher")) {
			exampaper.setT_username((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<ExamPaperEntity> ew = new EntityWrapper<ExamPaperEntity>();

		PageUtils page = exampaperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, exampaper), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/managerlist")
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
        if(tableName.equals("user_teacher")) {
            exampaper.setT_username((String)request.getSession().getAttribute("username"));
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
     * 单独删除试卷
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long id){
        boolean removed = exampaperService.deleteById(id);
        if (removed) {
            return R.ok();
        } else {
            return R.error("删除失败，试卷不存在");
        }
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
     * 创建试卷和组卷
     */
    @RequestMapping("/create")
    public R compose(HttpServletRequest request,  @RequestParam String title, @RequestParam Integer single,
                     @RequestParam Integer multiple, @RequestParam Integer judge, @RequestParam Integer blank, @RequestParam Integer subjective){
        List<ExamQuestionBankEntity> questionList = new ArrayList<ExamQuestionBankEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        //创建试卷对象
        ExamPaperEntity exampaper = new ExamPaperEntity();
        exampaper.setTitle(title);
        exampaper.setAddtime(new Date());
        if(tableName.equals("user_teacher")) {
            exampaper.setT_username((String)request.getSession().getAttribute("username"));
        }
        // 插入试卷，自动回填ID
        exampaperService.insert(exampaper);
        Long paperid = exampaper.getId();  // 获取回填的主键ID
        //单选题
        if(single>0) {
            Integer singleSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 0));
            if(singleSize<single) {
                return R.error("单选题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("user_teacher")) {
                    ew.eq("t_username", (String)request.getSession().getAttribute("username"));
                }
                ew.eq("type", 0).orderBy("RAND()").last("limit "+single);
                List<ExamQuestionBankEntity> radioList = examquestionbankService.selectList(ew);
                questionList.addAll(radioList);
            }
        }
        //多选题
        if(multiple>0) {
            Integer multipleChoiceSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 1));
            if(multipleChoiceSize<multiple) {
                return R.error("多选题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("user_teacher")) {
                    ew.eq("t_username", (String)request.getSession().getAttribute("username"));
                }
                ew.eq("type", 1).orderBy("RAND()").last("limit "+multiple);
                List<ExamQuestionBankEntity> multipleChoiceList = examquestionbankService.selectList(ew);
                questionList.addAll(multipleChoiceList);
            }
        }
        //判断题
        if(judge>0) {
            Integer judgeSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 2));
            if(judgeSize<judge) {
                return R.error("判断题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("user_teacher")) {
                    ew.eq("t_username", (String)request.getSession().getAttribute("username"));
                }
                ew.eq("type", 2).orderBy("RAND()").last("limit "+judge);
                List<ExamQuestionBankEntity> determineList = examquestionbankService.selectList(ew);
                questionList.addAll(determineList);
            }
        }
        //填空题
        if(blank>0) {
            Integer blankSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 3));
            if(blankSize<blank) {
                return R.error("填空题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("user_teacher")) {
                    ew.eq("t_username", (String)request.getSession().getAttribute("username"));
                }
                ew.eq("type", 3).orderBy("RAND()").last("limit "+blank);
                List<ExamQuestionBankEntity> fillList = examquestionbankService.selectList(ew);
                questionList.addAll(fillList);
            }
        }
        //主观题
        if(subjective>0) {
            Integer subjectiveSize = examquestionbankService.selectCount(new EntityWrapper<ExamQuestionBankEntity>().eq("type", 4));
            if(subjectiveSize<subjective) {
                return R.error("主观题库不足");
            } else {
                Wrapper<ExamQuestionBankEntity> ew = new EntityWrapper<ExamQuestionBankEntity>();
                if(tableName.equals("user_teacher")) {
                    ew.eq("t_username", (String)request.getSession().getAttribute("username"));
                }
                ew.eq("type", 4).orderBy("RAND()").last("limit "+subjective);
                List<ExamQuestionBankEntity> subjectivityList = examquestionbankService.selectList(ew);
                questionList.addAll(subjectivityList);
            }
        }
        if(questionList!=null && questionList.size()>0) {
            long seq = 0;
            for(ExamQuestionBankEntity q : questionList) {
                ExamQuestionEntity examquestion = new ExamQuestionEntity();
                examquestion.setId(System.currentTimeMillis()+(long)Math.floor(Math.random()*1000));
                examquestion.setPaperid(paperid);
                examquestion.setQuestionId(q.getId());
                examquestion.setSequence(++seq);
                if(tableName.equals("user_teacher")) {
                    examquestion.setT_username((String)request.getSession().getAttribute("username"));
                }
                examquestionService.insert(examquestion);
            }
        }
        return R.ok();
    }
}
