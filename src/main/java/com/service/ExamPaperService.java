package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ExamPaperEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ExamPaperVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ExamPaperView;


/**
 * 在线考试表
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface ExamPaperService extends IService<ExamPaperEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ExamPaperVO> selectListVO(Wrapper<ExamPaperEntity> wrapper);
   	
   	ExamPaperVO selectVO(@Param("ew") Wrapper<ExamPaperEntity> wrapper);
   	
   	List<ExamPaperView> selectListView(Wrapper<ExamPaperEntity> wrapper);
   	
   	ExamPaperView selectView(@Param("ew") Wrapper<ExamPaperEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ExamPaperEntity> wrapper);

   	

}

