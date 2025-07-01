package com.dao;

import com.entity.AboutUsEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.AboutusVO;
import com.entity.view.AboutUsView;


/**
 * 关于我们
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface AboutUsDao extends BaseMapper<AboutUsEntity> {
	
	List<AboutusVO> selectListVO(@Param("ew") Wrapper<AboutUsEntity> wrapper);
	
	AboutusVO selectVO(@Param("ew") Wrapper<AboutUsEntity> wrapper);
	
	List<AboutUsView> selectListView(@Param("ew") Wrapper<AboutUsEntity> wrapper);

	List<AboutUsView> selectListView(Pagination page, @Param("ew") Wrapper<AboutUsEntity> wrapper);

	
	AboutUsView selectView(@Param("ew") Wrapper<AboutUsEntity> wrapper);
	

}
