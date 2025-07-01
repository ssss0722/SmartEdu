package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.DiscussCourseMaterialEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.DiscussjiaoxueziliaoVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.DiscussCourseMaterialView;


/**
 * 教学资料评论表
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface DiscussCourseMaterialService extends IService<DiscussCourseMaterialEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DiscussjiaoxueziliaoVO> selectListVO(Wrapper<DiscussCourseMaterialEntity> wrapper);
   	
   	DiscussjiaoxueziliaoVO selectVO(@Param("ew") Wrapper<DiscussCourseMaterialEntity> wrapper);
   	
   	List<DiscussCourseMaterialView> selectListView(Wrapper<DiscussCourseMaterialEntity> wrapper);
   	
   	DiscussCourseMaterialView selectView(@Param("ew") Wrapper<DiscussCourseMaterialEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<DiscussCourseMaterialEntity> wrapper);

   	

}

