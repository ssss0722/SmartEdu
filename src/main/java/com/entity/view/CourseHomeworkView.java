package com.entity.view;

import com.entity.CourseHomeworkEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;


/**
 * 课程作业
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@TableName("exam_homework")
public class CourseHomeworkView extends CourseHomeworkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public CourseHomeworkView(){
	}
 
 	public CourseHomeworkView(CourseHomeworkEntity courseHomeworkEntity){
 	try {
			BeanUtils.copyProperties(this, courseHomeworkEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
