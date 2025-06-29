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


/**
 * 教师
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
@TableName("user_teacher")
public class TeacherEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public TeacherEntity() {
		
	}
	
	public TeacherEntity(T t) {
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
    @TableId
    private Long id;
	@TableField("t_username")
	private String tUsername;

	@TableField("t_name")
	private String tName;
	
	/**
	 * 密码
	 */
					
	private String password;
	
	/**
	 * 性别
	 */
					
	private String gender;
	
	/**
	 * 头像
	 */
					
	private String avatar;
	
	/**
	 * 电话
	 */
					
	private String tel;
	
	/**
	 * 课程名称
	 */
					
	private String course;
	
	
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
	/**
	 * 设置：教师姓名
	 */
	public void setT_name(String name) {
		this.tName=name;
	}
	/**
	 * 获取：教师姓名
	 */
	public String getT_name() {
		return tName;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password=password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：性别
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * 获取：性别
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * 设置：头像
	 */
	public void setAvatar(String avatar) {
		this.avatar=avatar;
	}
	/**
	 * 获取：头像
	 */
	public String getAvatar() {
		return avatar;
	}
	/**
	 * 设置：电话
	 */
	public void setTel(String tel) {
		this.tel=tel;
	}
	/**
	 * 获取：电话
	 */
	public String getTel() {
		return tel;
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

}
