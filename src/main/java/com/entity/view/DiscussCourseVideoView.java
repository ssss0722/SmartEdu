package com.entity.view;

import com.entity.DiscussCourseVideoEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;


/**
 * 教学视频评论表
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@TableName("discussjiaoxueshipin")
public class DiscussCourseVideoView extends DiscussCourseVideoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public DiscussCourseVideoView(){
	}
 
 	public DiscussCourseVideoView(DiscussCourseVideoEntity discussCourseVideoEntity){
 	try {
			BeanUtils.copyProperties(this, discussCourseVideoEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
