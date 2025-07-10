package com.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 测试记录表
 * 数据库通用操作实体类（普通增删改查）
 */
@TableName("record")
public class ExamRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public ExamRecordEntity() {}

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 学生账号
	 */
	private String sUsername;

	/**
	 * 在线考试id
	 */
	private Long paperid;

	/**
	 * 试题id
	 */
	private Long questionId;

	/**
	 * 是否批卷
	 */
	private Long ismark;

	/**
	 * 试题得分
	 */
	private Long myscore;

	/**
	 * 考生答案
	 */
	private String myanswer;

	/**
	 * 教师工号
	 */
	private String tUsername;

	/**
	 * 创建时间
	 */
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	// ------------------ Getter & Setter ------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getsUsername() {
		return sUsername;
	}

	public void setsUsername(String sUsername) {
		this.sUsername = sUsername;
	}

	public Long getPaperid() {
		return paperid;
	}

	public void setPaperid(Long paperid) {
		this.paperid = paperid;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}




	public String getMyanswer() {
		return myanswer;
	}

	public void setMyanswer(String myanswer) {
		this.myanswer = myanswer;
	}

	public String gettUsername() {
		return tUsername;
	}

	public void settUsername(String tUsername) {
		this.tUsername = tUsername;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}


	public Long getIsmark() {
		return ismark;
	}

	public void setIsmark(Long ismark) {
		this.ismark = ismark;
	}

	public Long getMyscore() {
		return myscore;
	}

	public void setMyscore(Long myscore) {
		this.myscore = myscore;
	}
}
