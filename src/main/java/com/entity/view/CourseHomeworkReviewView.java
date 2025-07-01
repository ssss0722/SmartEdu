package com.entity.view;

import com.entity.CourseHomeworkReviewEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;


/**
 * 作业批改
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@TableName("zuoyepigai")
public class CourseHomeworkReviewView extends CourseHomeworkReviewEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public CourseHomeworkReviewView(){
	}
 
 	public CourseHomeworkReviewView(CourseHomeworkReviewEntity courseHomeworkReviewEntity){
 	try {
			BeanUtils.copyProperties(this, courseHomeworkReviewEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
