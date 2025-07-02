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
@TableName("course_homework_review")
public class CourseHomeworkReviewView extends CourseHomeworkReviewEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String homework;
	private String tName;
	private String sName;

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

	public String getHomework() {
		return homework;
	}

	public void setHomework(String homework) {
		this.homework = homework;
	}

	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}
}
