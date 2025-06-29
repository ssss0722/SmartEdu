package com.entity.view;

import com.entity.TeacherEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;


/**
 * 教师
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
@TableName("user_teacher")
public class TeacherView extends TeacherEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public TeacherView(){
	}
 
 	public TeacherView(TeacherEntity teacherEntity){
 	try {
			BeanUtils.copyProperties(this, teacherEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
