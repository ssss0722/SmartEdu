package com.entity.view;


import com.baomidou.mybatisplus.annotations.TableName;
import com.entity.ExamStudentEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * 视图类：考试学生关联视图
 *
 * 可用于封装关联查询结果、前端展示用。
 */
@TableName("exam_student")
public class ExamStudentView extends ExamStudentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public ExamStudentView() {
    }

    public ExamStudentView(ExamStudentEntity examStudentEntity) {
        try {
            BeanUtils.copyProperties(this, examStudentEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
