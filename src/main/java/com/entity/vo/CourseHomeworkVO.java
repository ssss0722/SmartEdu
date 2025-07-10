package com.entity.vo;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 课程作业
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public class CourseHomeworkVO implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 作业名称
	 */
	
	private String name;
		
	/**
	 * 教师工号
	 */
	
	private String tUsername;
		
	/**
	 * 教师姓名
	 */
	
	private String tName;

	private Long paperId;

	/**
	 * 作业开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	/**
	 * 作业结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;


}
