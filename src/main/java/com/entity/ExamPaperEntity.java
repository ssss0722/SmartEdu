package com.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 在线考试表
 * 数据库通用操作实体类（普通增删改查）
 */
@TableName("paper")
public class ExamPaperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 主键id，自增
	@TableId(type = IdType.AUTO)
	private Long id;

	// 在线考试名称
	private String title;

	// 测试时长(分钟)
	private Integer time;

	// 在线考试状态
	private Integer status;

	// 教师工号，字段名与数据库不一致时用注解映射
	@TableField("t_username")
	private String tUsername;

	// 课程ID，数据库是 bigint(20)，用 Long
	@TableField("course_id")
	private Long courseId;

	// 创建时间
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;
	@TableField(exist = false)
	private Integer questionCount;
	@TableField(exist=false)
	private String courseName;

	// get/set 方法
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}



	public String getTUsername() {
		return tUsername;
	}

	public void setTUsername(String tUsername) {
		this.tUsername = tUsername;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Integer getQuestionCount(){
		return questionCount;
	}
	public void setQuestionCount(Integer questionCount){
		this.questionCount=questionCount;
	}


	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
