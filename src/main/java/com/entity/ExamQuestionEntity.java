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
 * 试题表
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@TableName("examquestion")
public class ExamQuestionEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public ExamQuestionEntity() {
		
	}
	
	public ExamQuestionEntity(T t) {
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
	 * 所属在线考试id（外键）
	 */
					
	private Long paperid;

	/**
	 * 题目ID（关联题库表）
	 */
	@TableField("question_id")
	private Long questionId;
	/**
	 * 分值
	 */
					
	private Long score;



	
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
	 * 设置：所属在线考试id（外键）
	 */
	public void setPaperid(Long paperid) {
		this.paperid = paperid;
	}
	/**
	 * 获取：所属在线考试id（外键）
	 */
	public Long getPaperid() {
		return paperid;
	}
	/**
	 * 获取：题目id（外键）
	 */
	public Long getQuestionId() {
		return questionId;
	}
	/**
	 * 设置：题目id（外键）
	 */
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
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
		this.tUsername = username;
	}
	/**
	 * 获取：教师工号
	 */
	public String getT_username() {
		return tUsername;
	}

}
