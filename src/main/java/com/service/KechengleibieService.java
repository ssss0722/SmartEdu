package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.CourseCategoriesEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.KechengleibieVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.CourseCategoriesView;


/**
 * 课程类别
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
public interface KechengleibieService extends IService<CourseCategoriesEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<KechengleibieVO> selectListVO(Wrapper<CourseCategoriesEntity> wrapper);
   	
   	KechengleibieVO selectVO(@Param("ew") Wrapper<CourseCategoriesEntity> wrapper);
   	
   	List<CourseCategoriesView> selectListView(Wrapper<CourseCategoriesEntity> wrapper);
   	
   	CourseCategoriesView selectView(@Param("ew") Wrapper<CourseCategoriesEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<CourseCategoriesEntity> wrapper);

   	

}

