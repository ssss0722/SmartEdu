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
 * 试题库表
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@TableName("examquestionbank")
public class ExamQuestionBankEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public ExamQuestionBankEntity() {
		
	}
	
	public ExamQuestionBankEntity(T t) {
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
	 * 试题名称
	 */
					
	private String title;
	
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
	 * 试题类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空） 4:主观题
	 */
					
	private Long type;
	
	/**
	 * 试题排序，值越大排越前面
	 */
					
	private Long sequence;
	
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
	 * 设置：试题名称
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：试题名称
	 */
	public String getTitle() {
		return title;
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
	 * 设置：试题排序，值越大排越前面
	 */
	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}
	/**
	 * 获取：试题排序，值越大排越前面
	 */
	public Long getSequence() {
		return sequence;
	}
	/**
	 * 设置：教师工号
	 */
	public void setT_username(String username) {
		this.tUsername=username;
	}
	/**
	 * 获取：教师工号
	 */
	public String getT_username() {
		return tUsername;
	}

}
