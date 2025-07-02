package com.entity.vo;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 作业批改
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public class CourseHomeworkReviewVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Date addtime;
	private String course;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	/**
	 * 作业名称
	 */
	
	private String homework;
		
	/**
	 * 教师工号
	 */
	
	private String tUsername;
		
	/**
	 * 教师姓名
	 */
	
	private String tName;
		
	/**
	 * 学生账号
	 */
	
	private String sUsername;
		
	/**
	 * 学生姓名
	 */
	
	private String sName;
		
	/**
	 * 作业评分
	 */
	
	private String grade;
		
	/**
	 * 批改时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date reviewedAt;
		
	/**
	 * 教师评语
	 */
	
	private String teacherComment;
				
	
	/**
	 * 设置：作业名称
	 */
	 
	public void setHomework(String homework) {
		this.homework=homework;
	}
	
	/**
	 * 获取：作业名称
	 */
	public String getHomework() {
		return homework;
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
	 * 设置：教师姓名
	 */
	 
	public void settName(String tName) {
		this.tName=tName;
	}
	
	/**
	 * 获取：教师姓名
	 */
	public String gettName() {
		return tName;
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
	 * 设置：学生姓名
	 */
	 
	public void setsName(String sName) {
		this.sName=sName;
	}
	
	/**
	 * 获取：学生姓名
	 */
	public String getsName() {
		return sName;
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
