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
 * 作业批改
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@TableName("course_homework_review")
public class CourseHomeworkReviewEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public CourseHomeworkReviewEntity() {
		
	}
	
	public CourseHomeworkReviewEntity(T t) {
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
	 * 课程名称
	 */
					
	private String course;
	
	/**
	 * 作业id
	 */
	@TableField("homework_id")
	private Long homeworkId;
	
	/**
	 * 教师工号
	 */

	@TableField("t_username")
	private String tUsername;

	/**
	 * 学生账号
	 */

	@TableField("s_username")
	private String sUsername;

	/**
	 * 作业评分
	 */

	private String grade;
	
	/**
	 * 批改时间
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	@TableField("reviewed_at")
	private Date reviewedAt;
	
	/**
	 * 教师评语
	 */

	@TableField("teacher_comment")
	private String teacherComment;
	
	
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
	 * 设置：课程名称
	 */
	public void setCourse(String course) {
		this.course=course;
	}
	/**
	 * 获取：课程名称
	 */
	public String getCourse() {
		return course;
	}
	/**
	 * 设置：作业id
	 */
	public void setHomeworkId(Long id) {
		this.homeworkId=id;
	}
	/**
	 * 获取：作业id
	 */
	public Long getHomeworkId() {
		return homeworkId;
	}
	/**
	 * 设置：教师工号
	 */
	public void settUsername(String tUsername) {
		this.tUsername=tUsername;
	}
	/**
	 * 获取：教师工号
	 */
	public String gettUsername() {
		return tUsername;
	}

	/**
	 * 设置：学生账号
	 */
	public void setsUsername(String sUsername) {
		this.sUsername=sUsername;
	}
	/**
	 * 获取：学生账号
	 */
	public String getsUsername() {
		return sUsername;
	}

	/**
	 * 设置：作业评分
	 */
	public void setGrade(String grade) {
		this.grade=grade;
	}
	/**
	 * 获取：作业评分
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * 设置：批改时间
	 */
	public void setReviewedAt(Date reviewedAt) {
		this.reviewedAt=reviewedAt;
	}
	/**
	 * 获取：批改时间
	 */
	public Date getReviewedAt() {
		return reviewedAt;
	}
	/**
	 * 设置：教师评语
	 */
	public void setTeacherComment(String teacherComment) {
		this.teacherComment=teacherComment;
	}
	/**
	 * 获取：教师评语
	 */
	public String getTeacherComment() {
		return teacherComment;
	}

}
