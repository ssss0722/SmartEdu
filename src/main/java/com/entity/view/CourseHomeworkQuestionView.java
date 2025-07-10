package com.entity.view;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.entity.ExamQuestionEntity;
import com.entity.HomeworkQuestionEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@TableName("question")
public class CourseHomeworkQuestionView extends HomeworkQuestionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    // 作业名称
    private String homework;
    // 教师姓名
    private String tName;
    // 课程名称
    private String course;

    /**
     * 作业题目
     */

    @TableField("question_name")
    private String questionName;

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
     * 试题类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空）4:主观题
     */

    private Long type;

    /**
     * 试题排序，值越大排越前面
     */

    private Long sequence;


    public CourseHomeworkQuestionView(){
    }

    public CourseHomeworkQuestionView(HomeworkQuestionEntity homeworkQuestion){
        try {
            BeanUtils.copyProperties(this, homeworkQuestion);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    /**
     * 获取：作业题目
     */

    public String getQuestionName() {
        return questionName;
    }

    /**
     * 设置：作业题目
     */


    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     * 获取：题目选项
     */

    public String getOptions() {
        return options;
    }

    /**
     * 设置：题目选项
     */

    public void setOptions(String options) {
        this.options = options;
    }

    /**
     * 获取：题目分值
     */

    public Long getScore() {
        return score;
    }

    /**
     * 设置：题目分值
     */

    public void setScore(Long score) {
        this.score = score;
    }

    /**
     * 获取：正确选项
     */

    public String getAnswer() {
        return answer;
    }

    /**
     * 设置：正确选项
     */

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 获取：题目解析
     */

    public String getAnalysis() {
        return analysis;
    }

    /**
     * 设置：题目解析
     */

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    /**
     * 获取：题目类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空）4:主观题
     */

    public Long getType() {
        return type;
    }

    /**
     * 设置：题目类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空）4:主观题
     */

    public void setType(Long type) {
        this.type = type;
    }


    /**
     * 获取：试题排序，值越大排越前面
     */
    public Long getSequence() {
        return sequence;
    }

    /**
     * 设置：试题排序，值越大排越前面
     */

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

}
