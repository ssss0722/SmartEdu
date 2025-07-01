package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.CourseMaterialEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.CourseMaterialVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.CourseMaterialView;


/**
 * 教学资料
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
public interface CourseMaterialService extends IService<CourseMaterialEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<CourseMaterialVO> selectListVO(Wrapper<CourseMaterialEntity> wrapper);
   	
   	CourseMaterialVO selectVO(@Param("ew") Wrapper<CourseMaterialEntity> wrapper);
   	
   	List<CourseMaterialView> selectListView(Wrapper<CourseMaterialEntity> wrapper);
   	
   	CourseMaterialView selectView(@Param("ew") Wrapper<CourseMaterialEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<CourseMaterialEntity> wrapper);

   	

}

