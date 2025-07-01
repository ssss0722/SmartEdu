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


import com.dao.DiscussCourseMaterialDao;
import com.entity.DiscussCourseMaterialEntity;
import com.service.DiscussCourseMaterialService;
import com.entity.vo.DiscussjiaoxueziliaoVO;
import com.entity.view.DiscussCourseMaterialView;

@Service("discussjiaoxueziliaoService")
public class DiscussCourseMaterialServiceImpl extends ServiceImpl<DiscussCourseMaterialDao, DiscussCourseMaterialEntity> implements DiscussCourseMaterialService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DiscussCourseMaterialEntity> page = this.selectPage(
                new Query<DiscussCourseMaterialEntity>(params).getPage(),
                new EntityWrapper<DiscussCourseMaterialEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<DiscussCourseMaterialEntity> wrapper) {
		  Page<DiscussCourseMaterialView> page =new Query<DiscussCourseMaterialView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<DiscussjiaoxueziliaoVO> selectListVO(Wrapper<DiscussCourseMaterialEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public DiscussjiaoxueziliaoVO selectVO(Wrapper<DiscussCourseMaterialEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<DiscussCourseMaterialView> selectListView(Wrapper<DiscussCourseMaterialEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public DiscussCourseMaterialView selectView(Wrapper<DiscussCourseMaterialEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
