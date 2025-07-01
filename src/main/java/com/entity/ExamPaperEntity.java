package com.entity;

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
 * @date 2024-03-05 11:41:24
 */
@TableName("exampaper")
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
					
	private String name;
	
	/**
	 * 测试时长(分钟)
	 */
					
	private Integer time;
	
	/**
	 * 在线考试状态
	 */
					
	private Integer status;
	
	/**
	 * 教师工号
	 */
					
	private String jiaoshigonghao;
	
	
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
	 * 设置：在线考试名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：在线考试名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：测试时长(分钟)
	 */
	public void setTime(Integer time) {
		this.time = time;
	}
	/**
	 * 获取：测试时长(分钟)
	 */
	public Integer getTime() {
		return time;
	}
	/**
	 * 设置：在线考试状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：在线考试状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：教师工号
	 */
	public void setJiaoshigonghao(String jiaoshigonghao) {
		this.jiaoshigonghao = jiaoshigonghao;
	}
	/**
	 * 获取：教师工号
	 */
	public String getJiaoshigonghao() {
		return jiaoshigonghao;
	}

}
