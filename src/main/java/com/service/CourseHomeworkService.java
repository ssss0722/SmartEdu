package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.CourseHomeworkEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.CourseHomeworkVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.CourseHomeworkView;


/**
 * 课程作业
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface CourseHomeworkService extends IService<CourseHomeworkEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<CourseHomeworkVO> selectListVO(Wrapper<CourseHomeworkEntity> wrapper);
   	
   	CourseHomeworkVO selectVO(@Param("ew") Wrapper<CourseHomeworkEntity> wrapper);
   	
   	List<CourseHomeworkView> selectListView(Wrapper<CourseHomeworkEntity> wrapper);
   	
   	CourseHomeworkView selectView(@Param("ew") Wrapper<CourseHomeworkEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<CourseHomeworkEntity> wrapper);

   	

}

