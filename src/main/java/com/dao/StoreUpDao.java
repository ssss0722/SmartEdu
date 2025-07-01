package com.dao;

import com.entity.StoreUpEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.StoreupVO;
import com.entity.view.StoreUpView;


/**
 * 收藏表
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface StoreUpDao extends BaseMapper<StoreUpEntity> {
	
	List<StoreupVO> selectListVO(@Param("ew") Wrapper<StoreUpEntity> wrapper);
	
	StoreupVO selectVO(@Param("ew") Wrapper<StoreUpEntity> wrapper);
	
	List<StoreUpView> selectListView(@Param("ew") Wrapper<StoreUpEntity> wrapper);

	List<StoreUpView> selectListView(Pagination page, @Param("ew") Wrapper<StoreUpEntity> wrapper);

	
	StoreUpView selectView(@Param("ew") Wrapper<StoreUpEntity> wrapper);
	

}
