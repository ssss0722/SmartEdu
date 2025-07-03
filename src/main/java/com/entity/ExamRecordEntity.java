package com.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 测试记录表
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@TableName("examrecord")
public class ExamRecordEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public ExamRecordEntity() {
		
	}
	
	public ExamRecordEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 主键id
	 */
    @TableId(type = IdType.AUTO)
    private Long id;
	/**
	 * 用户id
	 */
					
	private Long userid;
	
	/**
	 * 用户名
	 */
	@TableField("s_username")
	private String sUsername;
	
	/**
	 * 在线考试id（外键）
	 */
					
	private Long paperid;
	
	/**
	 * 在线考试名称
	 */
					
	private String papername;
	
	/**
	 * 试题id（外键）
	 */
					
	private Long questionid;
	
	/**
	 * 试题名称
	 */
					
	private String questionname;
	
	/**
	 * 选项，json字符串
	 */
					
	private String options;
	
	/**
	 * 分值
	 */
					
	private Long score;
	
	/**
	 * 正确答案
	 */
					
	private String answer;
	
	/**
	 * 答案解析
	 */
					
	private String analysis;
	
	/**
	 * 是否批卷
	 */
					
	private Long ismark;
	
	/**
	 * 试题类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空） 4:主观题
	 */
					
	private Long type;
	
	/**
	 * 试题得分
	 */
					
	private Long myscore;
	
	/**
	 * 考生答案
	 */
					
	private String myanswer;
	
	/**
	 * 教师工号
	 */
	@TableField("t_username")
	private String tUsername;
	
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserid() {
		return userid;
	}
	/**
	 * 设置：用户名
	 */
	public void setS_username(String username) {
		this.sUsername = username;
	}
	/**
	 * 获取：用户名
	 */
	public String getS_username() {
		return sUsername;
	}
	/**
	 * 设置：在线考试id（外键）
	 */
	public void setPaperid(Long paperid) {
		this.paperid = paperid;
	}
	/**
	 * 获取：在线考试id（外键）
	 */
	public Long getPaperid() {
		return paperid;
	}
	/**
	 * 设置：在线考试名称
	 */
	public void setPapername(String papername) {
		this.papername = papername;
	}
	/**
	 * 获取：在线考试名称
	 */
	public String getPapername() {
		return papername;
	}
	/**
	 * 设置：试题id（外键）
	 */
	public void setQuestionid(Long questionid) {
		this.questionid = questionid;
	}
	/**
	 * 获取：试题id（外键）
	 */
	public Long getQuestionid() {
		return questionid;
	}
	/**
	 * 设置：试题名称
	 */
	public void setQuestionname(String questionname) {
		this.questionname = questionname;
	}
	/**
	 * 获取：试题名称
	 */
	public String getQuestionname() {
		return questionname;
	}
	/**
	 * 设置：选项，json字符串
	 */
	public void setOptions(String options) {
		this.options = options;
	}
	/**
	 * 获取：选项，json字符串
	 */
	public String getOptions() {
		return options;
	}
	/**
	 * 设置：分值
	 */
	public void setScore(Long score) {
		this.score = score;
	}
	/**
	 * 获取：分值
	 */
	public Long getScore() {
		return score;
	}
	/**
	 * 设置：正确答案
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * 获取：正确答案
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * 设置：答案解析
	 */
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	/**
	 * 获取：答案解析
	 */
	public String getAnalysis() {
		return analysis;
	}
	/**
	 * 设置：是否批卷
	 */
	public void setIsmark(Long ismark) {
		this.ismark = ismark;
	}
	/**
	 * 获取：是否批卷
	 */
	public Long getIsmark() {
		return ismark;
	}
	/**
	 * 设置：试题类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空） 4:主观题
	 */
	public void setType(Long type) {
		this.type = type;
	}
	/**
	 * 获取：试题类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空） 4:主观题
	 */
	public Long getType() {
		return type;
	}
	/**
	 * 设置：试题得分
	 */
	public void setMyscore(Long myscore) {
		this.myscore = myscore;
	}
	/**
	 * 获取：试题得分
	 */
	public Long getMyscore() {
		return myscore;
	}
	/**
	 * 设置：考生答案
	 */
	public void setMyanswer(String myanswer) {
		this.myanswer = myanswer;
	}
	/**
	 * 获取：考生答案
	 */
	public String getMyanswer() {
		return myanswer;
	}
	/**
	 * 设置：教师工号
	 */
	public void setT_username(String username) {
		this.tUsername = username;
	}
	/**
	 * 获取：教师工号
	 */
	public String getT_username() {
		return tUsername;
	}

}
