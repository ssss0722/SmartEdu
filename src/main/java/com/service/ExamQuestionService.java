package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ExamQuestionEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ExamQuestionVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ExamQuestionView;


/**
 * 试题表
 *
 * @author 
 * @email
 */
public interface ExamQuestionService extends IService<ExamQuestionEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ExamQuestionVO> selectListVO(Wrapper<ExamQuestionEntity> wrapper);
   	
   	ExamQuestionVO selectVO(@Param("ew") Wrapper<ExamQuestionEntity> wrapper);
   	
   	List<ExamQuestionView> selectListView(Wrapper<ExamQuestionEntity> wrapper);
   	
   	ExamQuestionView selectView(@Param("ew") Wrapper<ExamQuestionEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ExamQuestionEntity> wrapper);

   	

}

