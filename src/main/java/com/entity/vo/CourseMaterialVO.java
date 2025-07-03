package com.entity.vo;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 教学资料
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
public class CourseMaterialVO implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 课程类别
	 */
	
	private String course;
		
	/**
	 * 图片
	 */
	
	private String picture;
		
	/**
	 * 附件
	 */
	
	private String attachment;
		
	/**
	 * 教师工号
	 */
	
	private String tUsername;
		
	/**
	 * 教师姓名
	 */
	
	private String tName;
		
	/**
	 * 发布时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date publishAt;
		
	/**
	 * 内容
	 */
	
	private String content;
		
	/**
	 * 最近点击时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date clicktime;
		
	/**
	 * 点击次数
	 */
	
	private Integer clicknum;
		
	/**
	 * 评论数
	 */
	
	private Integer discussnum;
		
	/**
	 * 收藏数
	 */
	
	private Integer storeupnum;
				
	
	/**
	 * 设置：课程类别
	 */
	 
	public void setCourse(String course) {
		this.course = course;
	}
	
	/**
	 * 获取：课程类别
	 */
	public String getCourse() {
		return course;
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
	 * 设置：附件
	 */
	 
	public void setAttachment(String attachment) {
		this.attachment=attachment;
	}
	
	/**
	 * 获取：附件
	 */
	public String getAttachment() {
		return attachment;
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
	 * 设置：内容
	 */
	 
	public void setContent(String content) {
		this.content=content;
	}
	
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}
				
	
	/**
	 * 设置：最近点击时间
	 */
	 
	public void setClicktime(Date clicktime) {
		this.clicktime = clicktime;
	}
	
	/**
	 * 获取：最近点击时间
	 */
	public Date getClicktime() {
		return clicktime;
	}
				
	
	/**
	 * 设置：点击次数
	 */
	 
	public void setClicknum(Integer clicknum) {
		this.clicknum = clicknum;
	}
	
	/**
	 * 获取：点击次数
	 */
	public Integer getClicknum() {
		return clicknum;
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
