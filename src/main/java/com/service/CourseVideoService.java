package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.CourseVideoEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.CourseVideoVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.CourseVideoView;


/**
 * 教学视频
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
public interface CourseVideoService extends IService<CourseVideoEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<CourseVideoVO> selectListVO(Wrapper<CourseVideoEntity> wrapper);
   	
   	CourseVideoVO selectVO(@Param("ew") Wrapper<CourseVideoEntity> wrapper);
   	
   	List<CourseVideoView> selectListView(Wrapper<CourseVideoEntity> wrapper);
   	
   	CourseVideoView selectView(@Param("ew") Wrapper<CourseVideoEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<CourseVideoEntity> wrapper);

   	

}

