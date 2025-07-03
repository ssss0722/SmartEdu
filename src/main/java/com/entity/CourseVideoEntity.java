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
 * 教学视频
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
@TableName("course_video")
public class CourseVideoEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public CourseVideoEntity() {
		
	}
	
	public CourseVideoEntity(T t) {
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
	 * 标题
	 */
					
	private String title;
	
	/**
	 * 课程类别
	 */
					
	private String course;
	
	/**
	 * 封面
	 */
					
	private String cover;
	
	/**
	 * 教师工号
	 */
	@TableField("t_username")
	private String tUsername;
	
	/**
	 * 教学视频
	 */
					
	private String video;
	
	/**
	 * 发布时间
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@DateTimeFormat
	@TableField("published_at")
	private Date publishedAt;
	
	/**
	 * 基本介绍
	 */
					
	private String intro;
	
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
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title=title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：课程类别
	 */
	public void setCourse(String course) {
		this.course=course;
	}
	/**
	 * 获取：课程类别
	 */
	public String getCourse() {
		return course;
	}
	/**
	 * 设置：封面
	 */
	public void setCover(String cover) {
		this.cover=cover;
	}
	/**
	 * 获取：封面
	 */
	public String getCover() {
		return cover;
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
	 * 设置：教学视频
	 */
	public void setVideo(String video) {
		this.video=video;
	}
	/**
	 * 获取：教学视频
	 */
	public String getVideo() {
		return video;
	}
	/**
	 * 设置：发布时间
	 */
	public void setPublishedAt(Date publishAt) {
		this.publishedAt=publishAt;
	}
	/**
	 * 获取：发布时间
	 */
	public Date getPublishedAt() {
		return publishedAt;
	}
	/**
	 * 设置：基本介绍
	 */
	public void setIntro(String intro) {
		this.intro=intro;
	}
	/**
	 * 获取：基本介绍
	 */
	public String getIntro() {
		return intro;
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
