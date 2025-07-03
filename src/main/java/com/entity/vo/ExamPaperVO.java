package com.entity.vo;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
 

/**
 * 在线考试表
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public class ExamPaperVO implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 测试时长(分钟)
	 */
	
	private Integer time;
		
	/**
	 * 在线考试状态
	 */
	
	private String status;

	/**
	 * 教师工号
	 */
	@TableField("t_username")
	private String tUsername;
				
	
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
