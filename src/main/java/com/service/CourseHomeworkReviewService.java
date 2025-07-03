package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.CourseHomeworkReviewEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.CourseHomeworkReviewVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.CourseHomeworkReviewView;


/**
 * 作业批改
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface CourseHomeworkReviewService extends IService<CourseHomeworkReviewEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<CourseHomeworkReviewVO> selectListVO(Wrapper<CourseHomeworkReviewEntity> wrapper);
   	
   	CourseHomeworkReviewVO selectVO(@Param("ew") Wrapper<CourseHomeworkReviewEntity> wrapper);
   	
   	List<CourseHomeworkReviewView> selectListView(Wrapper<CourseHomeworkReviewEntity> wrapper);
   	
   	CourseHomeworkReviewView selectView(@Param("ew") Wrapper<CourseHomeworkReviewEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<CourseHomeworkReviewEntity> wrapper);

   	

    List<Map<String, Object>> selectValue(Map<String, Object> params,Wrapper<CourseHomeworkReviewEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params,Wrapper<CourseHomeworkReviewEntity> wrapper);

    List<Map<String, Object>> selectGroup(Map<String, Object> params,Wrapper<CourseHomeworkReviewEntity> wrapper);



}

