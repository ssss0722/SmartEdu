package com.entity.view;

import com.entity.ExamQuestionBankEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;


/**
 * 试题库表
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
@TableName("examquestionbank")
public class ExamQuestionBankView extends ExamQuestionBankEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public ExamQuestionBankView(){
	}
 
 	public ExamQuestionBankView(ExamQuestionBankEntity examquestionbankEntity){
 	try {
			BeanUtils.copyProperties(this, examquestionbankEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
