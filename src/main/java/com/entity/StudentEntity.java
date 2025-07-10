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
 * 学生
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
@TableName("user_student")
public class StudentEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public StudentEntity() {
		
	}
	
	public StudentEntity(T t) {
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
	/**
	 * 学生账号
	 */
	@TableField("s_username")
					
	private String sUsername;
	
	/**
	 * 学生姓名
	 */
					
	private String sName;
	
	/**
	 * 密码
	 */
					
	private String password;
	
	/**
	 * 性别
	 */
					
	private String gender;
	
	/**
	 * 联系电话
	 */
					
	private String tel;
	
	/**
	 * 头像
	 */
					
	private String avatar;
	
	
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
	 * 设置：学生账号
	 */
	public void setsUsername(String username) {
		this.sUsername=username;
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
	public void setsName(String name) {
		this.sName=name;
	}
	/**
	 * 获取：学生姓名
	 */
	public String getsName() {
		return sName;
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
		this.gender=gender;
	}
	/**
	 * 获取：性别
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * 设置：联系电话
	 */
	public void setTel(String tel) {
		this.tel=tel;
	}
	/**
	 * 获取：联系电话
	 */
	public String getTel() {
		return tel;
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

}
