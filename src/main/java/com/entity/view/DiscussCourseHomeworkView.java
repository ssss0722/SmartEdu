package com.entity.view;

import com.entity.DiscussCourseHomeworkEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;


/**
 * 课程作业评论表
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:25
 */
@TableName("discusskechengzuoye")
public class DiscussCourseHomeworkView extends DiscussCourseHomeworkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public DiscussCourseHomeworkView(){
	}
 
 	public DiscussCourseHomeworkView(DiscussCourseHomeworkEntity discussCourseHomeworkEntity){
 	try {
			BeanUtils.copyProperties(this, discussCourseHomeworkEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
