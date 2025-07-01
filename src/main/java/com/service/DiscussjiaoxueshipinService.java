package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.DiscussCourseVideoEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.DiscussjiaoxueshipinVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.DiscussCourseVideoView;


/**
 * 教学视频评论表
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface DiscussjiaoxueshipinService extends IService<DiscussCourseVideoEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DiscussjiaoxueshipinVO> selectListVO(Wrapper<DiscussCourseVideoEntity> wrapper);
   	
   	DiscussjiaoxueshipinVO selectVO(@Param("ew") Wrapper<DiscussCourseVideoEntity> wrapper);
   	
   	List<DiscussCourseVideoView> selectListView(Wrapper<DiscussCourseVideoEntity> wrapper);
   	
   	DiscussCourseVideoView selectView(@Param("ew") Wrapper<DiscussCourseVideoEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<DiscussCourseVideoEntity> wrapper);

   	

}

