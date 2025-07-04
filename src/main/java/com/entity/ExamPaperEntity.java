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
 * 在线考试表
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2025-07-01 10:41:24
 */
@TableName("exam_paper")
public class ExamPaperEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public ExamPaperEntity() {
		
	}
	
	public ExamPaperEntity(T t) {
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
	 * 在线考试名称
	 */
					
	private String title;
	
	/**
	 * 测试开始时间(分钟)
	 */

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;
	/**
	 * 测试结束时间(分钟)
	 */

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;
	/**
	 * 测试结束时间(分钟)
	 */
	@TableField(exist = false)
	private String durationText;

	public String getDurationText() {
		return durationText;
	}
	public void setDurationText(String durationText) {
		this.durationText = durationText;
	}

	
	/**
	 * 在线考试状态
	 */
					
	private String status;
	
	/**
	 * 教师工号
	 */
@TableField("t_username")
	private String tUsername;

private int courseId;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;
	public int getCourseId(){return courseId;}
	public void setCourseId(int courseId){this.courseId=courseId;}

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
	 * 设置：在线考试名称
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：在线考试名称
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：测试开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：测试开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：测试结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime =endTime ;
	}
	/**
	 * 获取：测试结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：在线考试状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：在线考试状态
	 */
	public String getStatus() {
		return status;
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
