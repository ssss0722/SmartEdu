package com.entity.view;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.entity.ExamRecordEntity;
import com.entity.HomeworkRecordEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

@TableName("record")
public class HomeworkRecordView extends HomeworkRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public HomeworkRecordView(){
    }

    public HomeworkRecordView(HomeworkRecordEntity homeworkRecordEntity){
        try {
            BeanUtils.copyProperties(this, homeworkRecordEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private BigDecimal accuracy;

    private Integer anum;

    private Integer bnum;

    private Integer cnum;

    private Integer dnum;

    /**
     * 作业名称
     */

    private String homeworkName;

    /**
     * 课程名称
     */

    private String course;

    /**
     * 作业题目
     */

    private String title;

    /**
     * 选项，json字符串
     */

    private String options;

    /**
     * 分值
     */

    private Long score;

    /**
     * 正确答案
     */

    private String answer;

    /**
     * 答案解析
     */

    private String analysis;

    /**
     * 试题类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空） 4:主观题
     */

    private Long type;

    private Long questionId;

    @Override
    public Long getQuestionId() {
        return questionId;
    }

    @Override
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public BigDecimal getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(BigDecimal accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getAnum() {
        return anum;
    }

    public void setAnum(Integer anum) {
        this.anum = anum;
    }

    public Integer getBnum() {
        return bnum;
    }

    public void setBnum(Integer bnum) {
        this.bnum = bnum;
    }

    public Integer getCnum() {
        return cnum;
    }

    public void setCnum(Integer cnum) {
        this.cnum = cnum;
    }

    public Integer getDnum() {
        return dnum;
    }

    public void setDnum(Integer dnum) {
        this.dnum = dnum;
    }

    public String getHomeworkName() {
        return homeworkName;
    }

    public void setHomeworkName(String homeworkName) {
        this.homeworkName = homeworkName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
