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
 * 课程作业
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@TableName("exam_homework")
public class CourseHomeworkEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public CourseHomeworkEntity() {
		
	}
	
	public CourseHomeworkEntity(T t) {
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
	 * 作业名称
	 */
					
	private String homework;
	
	/**
	 * 图片
	 */
					
	private String picture;
	
	/**
	 * 相关附件
	 */
					
	private String attachment;
	
	/**
	 * 教师工号
	 */

	@TableField("t_username")
	private String tUsername;
	
	/**
	 * 发布时间
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	@TableField("publish_at")
	private Date publishAt;
	
	/**
	 * 作业内容
	 */

	private String contxt;
	
	/**
	 * 评论数
	 */
					
	private Integer discussnum;
	
	/**
	 * 收藏数
	 */
					
	private Integer storeupnum;
	
	
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
	 * 设置：图片
	 */
	public void setPicture(String picture) {
		this.picture=picture;
	}
	/**
	 * 获取：图片
	 */
	public String getPicture() {
		return picture;
	}
	/**
	 * 设置：相关附件
	 */
	public void setAttachment(String attachment) {
		this.attachment=attachment;
	}
	/**
	 * 获取：相关附件
	 */
	public String getAttachment() {
		return attachment;
	}
	/**
	 * 设置：教师工号
	 */
	public void settUsername(String username) {
		this.tUsername=username;
	}
	/**
	 * 获取：教师工号
	 */
	public String gettUsername() {
		return tUsername;
	}
	/**
	 * 设置：发布时间
	 */
	public void setPublishAt(Date publishAt) {
		this.publishAt=publishAt;
	}
	/**
	 * 获取：发布时间
	 */
	public Date getPublishAt() {
		return publishAt;
	}
	/**
	 * 设置：作业内容
	 */
	public void setContxt(String contxt) {
		this.contxt=contxt;
	}
	/**
	 * 获取：作业内容
	 */
	public String getContxt() {
		return contxt;
	}
	/**
	 * 设置：评论数
	 */
	public void setDiscussnum(Integer discussnum) {
		this.discussnum = discussnum;
	}
	/**
	 * 获取：评论数
	 */
	public Integer getDiscussnum() {
		return discussnum;
	}
	/**
	 * 设置：收藏数
	 */
	public void setStoreupnum(Integer storeupnum) {
		this.storeupnum = storeupnum;
	}
	/**
	 * 获取：收藏数
	 */
	public Integer getStoreupnum() {
		return storeupnum;
	}

}
