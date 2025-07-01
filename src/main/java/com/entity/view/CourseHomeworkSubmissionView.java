package com.entity.view;

import com.entity.CourseHomeworkSubmissionEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;


/**
 * 作业提交
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@TableName("zuoyetijiao")
public class CourseHomeworkSubmissionView extends CourseHomeworkSubmissionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public CourseHomeworkSubmissionView(){
	}
 
 	public CourseHomeworkSubmissionView(CourseHomeworkSubmissionEntity courseHomeworkSubmissionEntity){
 	try {
			BeanUtils.copyProperties(this, courseHomeworkSubmissionEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
