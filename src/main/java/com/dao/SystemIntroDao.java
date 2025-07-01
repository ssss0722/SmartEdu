package com.dao;

import com.entity.SystemIntroEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.SystemintroVO;
import com.entity.view.SystemIntroView;


/**
 * 系统简介
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface SystemIntroDao extends BaseMapper<SystemIntroEntity> {
	
	List<SystemintroVO> selectListVO(@Param("ew") Wrapper<SystemIntroEntity> wrapper);
	
	SystemintroVO selectVO(@Param("ew") Wrapper<SystemIntroEntity> wrapper);
	
	List<SystemIntroView> selectListView(@Param("ew") Wrapper<SystemIntroEntity> wrapper);

	List<SystemIntroView> selectListView(Pagination page, @Param("ew") Wrapper<SystemIntroEntity> wrapper);

	
	SystemIntroView selectView(@Param("ew") Wrapper<SystemIntroEntity> wrapper);
	

}
