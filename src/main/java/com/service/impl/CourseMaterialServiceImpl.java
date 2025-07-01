package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.CourseMaterialDao;
import com.entity.CourseMaterialEntity;
import com.service.CourseMaterialService;
import com.entity.vo.CourseMaterialVO;
import com.entity.view.CourseMaterialView;

@Service("jiaoxueziliaoService")
public class CourseMaterialServiceImpl extends ServiceImpl<CourseMaterialDao, CourseMaterialEntity> implements CourseMaterialService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CourseMaterialEntity> page = this.selectPage(
                new Query<CourseMaterialEntity>(params).getPage(),
                new EntityWrapper<CourseMaterialEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<CourseMaterialEntity> wrapper) {
		  Page<CourseMaterialView> page =new Query<CourseMaterialView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<CourseMaterialVO> selectListVO(Wrapper<CourseMaterialEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public CourseMaterialVO selectVO(Wrapper<CourseMaterialEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<CourseMaterialView> selectListView(Wrapper<CourseMaterialEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public CourseMaterialView selectView(Wrapper<CourseMaterialEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
