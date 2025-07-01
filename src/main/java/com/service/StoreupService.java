package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.StoreUpEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.StoreupVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.StoreUpView;


/**
 * 收藏表
 *
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface StoreupService extends IService<StoreUpEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<StoreupVO> selectListVO(Wrapper<StoreUpEntity> wrapper);
   	
   	StoreupVO selectVO(@Param("ew") Wrapper<StoreUpEntity> wrapper);
   	
   	List<StoreUpView> selectListView(Wrapper<StoreUpEntity> wrapper);
   	
   	StoreUpView selectView(@Param("ew") Wrapper<StoreUpEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<StoreUpEntity> wrapper);

   	

}

