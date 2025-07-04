package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.CourseCategoriesEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.CourseCategoriesVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.CourseCategoriesView;


/**
 * 课程类别
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:23
 */
public interface CourseCategoryService extends IService<CourseCategoriesEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<CourseCategoriesVO> selectListVO(Wrapper<CourseCategoriesEntity> wrapper);
   	
   	CourseCategoriesVO selectVO(@Param("ew") Wrapper<CourseCategoriesEntity> wrapper);
   	
   	List<CourseCategoriesView> selectListView(Wrapper<CourseCategoriesEntity> wrapper);
   	
   	CourseCategoriesView selectView(@Param("ew") Wrapper<CourseCategoriesEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<CourseCategoriesEntity> wrapper);


	List<CourseCategoriesEntity> selectByTeacher(String tUsername);

	CourseCategoriesEntity selectByName(String courseName);
}

