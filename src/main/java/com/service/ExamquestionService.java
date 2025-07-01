package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ExamQuestionEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ExamquestionVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ExamQuestionView;


/**
 * 试题表
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface ExamquestionService extends IService<ExamQuestionEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ExamquestionVO> selectListVO(Wrapper<ExamQuestionEntity> wrapper);
   	
   	ExamquestionVO selectVO(@Param("ew") Wrapper<ExamQuestionEntity> wrapper);
   	
   	List<ExamQuestionView> selectListView(Wrapper<ExamQuestionEntity> wrapper);
   	
   	ExamQuestionView selectView(@Param("ew") Wrapper<ExamQuestionEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ExamQuestionEntity> wrapper);

   	

}

