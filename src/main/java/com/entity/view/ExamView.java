package com.entity.view;

import com.baomidou.mybatisplus.annotations.TableName;
import com.entity.ExamEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * 考试视图实体类，用于多表联查结果封装（如试卷标题、学生名称等）
 * 继承自 ExamEntity，添加额外的视图字段
 */
@TableName("exam")
public class ExamView extends ExamEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    // ========== 额外的字段 ==========

    /** 试卷标题 */
    private String paperTitle;

    /** 考试对象（多个学生账号） */
    private String target;

    /** 考试对象名称（多个学生名） */
    private String targetNames;

    /** 考试时间范围（组合 startTime - endTime） */
    private String examTime;

    // ========== 构造函数 ==========

    public ExamView() {}

    public ExamView(ExamEntity examEntity) {
        try {
            BeanUtils.copyProperties(this, examEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // ========== Getter / Setter ==========

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTargetNames() {
        return targetNames;
    }

    public void setTargetNames(String targetNames) {
        this.targetNames = targetNames;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }
}
