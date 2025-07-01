package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.DiscussCourseHomeworkEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.DiscusskechengzuoyeVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.DiscussCourseHomeworkView;


/**
 * 课程作业评论表
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:25
 */
public interface DiscusskechengzuoyeService extends IService<DiscussCourseHomeworkEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DiscusskechengzuoyeVO> selectListVO(Wrapper<DiscussCourseHomeworkEntity> wrapper);
   	
   	DiscusskechengzuoyeVO selectVO(@Param("ew") Wrapper<DiscussCourseHomeworkEntity> wrapper);
   	
   	List<DiscussCourseHomeworkView> selectListView(Wrapper<DiscussCourseHomeworkEntity> wrapper);
   	
   	DiscussCourseHomeworkView selectView(@Param("ew") Wrapper<DiscussCourseHomeworkEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<DiscussCourseHomeworkEntity> wrapper);

   	

}

