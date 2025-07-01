package com.dao;

import com.entity.NewsTypeEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.NewstypeVO;
import com.entity.view.NewsTypeView;


/**
 * 系统公告分类
 * 
 * @author 
 * @email 
 * @date 2024-03-05 11:41:24
 */
public interface NewsTypeDao extends BaseMapper<NewsTypeEntity> {
	
	List<NewstypeVO> selectListVO(@Param("ew") Wrapper<NewsTypeEntity> wrapper);
	
	NewstypeVO selectVO(@Param("ew") Wrapper<NewsTypeEntity> wrapper);
	
	List<NewsTypeView> selectListView(@Param("ew") Wrapper<NewsTypeEntity> wrapper);

	List<NewsTypeView> selectListView(Pagination page, @Param("ew") Wrapper<NewsTypeEntity> wrapper);

	
	NewsTypeView selectView(@Param("ew") Wrapper<NewsTypeEntity> wrapper);
	

}
