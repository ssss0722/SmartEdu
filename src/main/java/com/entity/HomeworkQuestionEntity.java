package com.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

@TableName("course_homework_question")
public class HomeworkQuestionEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public HomeworkQuestionEntity(){

    }

    public HomeworkQuestionEntity(T t){
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
     * 作业id
     */

    @TableField("homework_id")
    private Long homeworkId;


    /**
     * 题目id
     */

    @TableField("question_id")
    private Long questionId;

    /**
     * 教师工号
     */

    @TableField("t_username")
    private String tUsername;

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
     * 获取：所属作业id（外键）
     */
    public Long getHomeworkId() {
        return homeworkId;
    }

    /**
     * 设置：所属作业id（外键）
     */
    public void setHomeworkId(Long homeworkId) {
        this.homeworkId = homeworkId;
    }


    /**
     * 获取：题目id（外键）
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * 设置：题目id（外键）
     */
    public void setQuestionId(Long questionId) {
        this.questionId=questionId;
    }


    /**
     * 获取：教师用户名
     */

    public String gettUsername() {
        return tUsername;
    }

    /**
     * 设置：教师用户名
     */

    public void settUsername(String tUsername) {
        this.tUsername = tUsername;
    }
}
