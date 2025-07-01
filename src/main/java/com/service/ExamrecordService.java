package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ExamRecordEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ExamrecordVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ExamRecordView;


/**
 * 测试记录表
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface ExamrecordService extends IService<ExamRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ExamrecordVO> selectListVO(Wrapper<ExamRecordEntity> wrapper);
   	
   	ExamrecordVO selectVO(@Param("ew") Wrapper<ExamRecordEntity> wrapper);
   	
   	List<ExamRecordView> selectListView(Wrapper<ExamRecordEntity> wrapper);
   	
   	ExamRecordView selectView(@Param("ew") Wrapper<ExamRecordEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ExamRecordEntity> wrapper);

   	
   	PageUtils queryPageGroupBy(Map<String, Object> params,Wrapper<ExamRecordEntity> wrapper);

    PageUtils queryPageOptionsNum(Map<String, Object> params,Wrapper<ExamRecordEntity> wrapper);

}

