package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@TableName("course_homework_record")
public class HomeworkRecordEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public HomeworkRecordEntity() {

    }

    public HomeworkRecordEntity(T t) {
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
     * 学生用户名
     */

    private String sUsername;

    /**
     * 老师用户名
     */

    private String tUsername;

    /**
     * 作业id（外键）
     */

    private Long homeworkId;

    /**
     * 题目id（外键）
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

    public String gettUsername() {
        return tUsername;
    }

    public void settUsername(String tUsername) {
        this.tUsername = tUsername;
    }

    public Long getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Long homeworkId) {
        this.homeworkId = homeworkId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    public String getMyanswer() {
        return myanswer;
    }

    public void setMyanswer(String myanswer) {
        this.myanswer = myanswer;
    }
}
