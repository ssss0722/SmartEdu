package com.entity.view;

import com.baomidou.mybatisplus.annotations.TableField;
import com.entity.CourseMaterialEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;


/**
 * 教学资料
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
@TableName("course_material")
public class CourseMaterialView extends CourseMaterialEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableField("t_name")
	private String tName;

	public CourseMaterialView(){
	}
 
 	public CourseMaterialView(CourseMaterialEntity courseMaterialEntity){
 	try {
			BeanUtils.copyProperties(this, courseMaterialEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}

	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}
}
