package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.NewsTypeEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.NewstypeVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.NewsTypeView;


/**
 * 系统公告分类
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface NewstypeService extends IService<NewsTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<NewstypeVO> selectListVO(Wrapper<NewsTypeEntity> wrapper);
   	
   	NewstypeVO selectVO(@Param("ew") Wrapper<NewsTypeEntity> wrapper);
   	
   	List<NewsTypeView> selectListView(Wrapper<NewsTypeEntity> wrapper);
   	
   	NewsTypeView selectView(@Param("ew") Wrapper<NewsTypeEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<NewsTypeEntity> wrapper);

   	

}

