package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ExamQuestionBankEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ExamQuestionbankVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ExamQuestionBankView;


/**
 * 试题库表
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface ExamQuestionBankService extends IService<ExamQuestionBankEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ExamQuestionbankVO> selectListVO(Wrapper<ExamQuestionBankEntity> wrapper);
   	
   	ExamQuestionbankVO selectVO(@Param("ew") Wrapper<ExamQuestionBankEntity> wrapper);
   	
   	List<ExamQuestionBankView> selectListView(Wrapper<ExamQuestionBankEntity> wrapper);
   	
   	ExamQuestionBankView selectView(@Param("ew") Wrapper<ExamQuestionBankEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ExamQuestionBankEntity> wrapper);

   	

}

