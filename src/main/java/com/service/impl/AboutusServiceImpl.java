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


import com.dao.AboutUsDao;
import com.entity.AboutUsEntity;
import com.service.AboutusService;
import com.entity.vo.AboutusVO;
import com.entity.view.AboutUsView;

@Service("aboutusService")
public class AboutusServiceImpl extends ServiceImpl<AboutUsDao, AboutUsEntity> implements AboutusService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<AboutUsEntity> page = this.selectPage(
                new Query<AboutUsEntity>(params).getPage(),
                new EntityWrapper<AboutUsEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<AboutUsEntity> wrapper) {
		  Page<AboutUsView> page =new Query<AboutUsView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<AboutusVO> selectListVO(Wrapper<AboutUsEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public AboutusVO selectVO(Wrapper<AboutUsEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<AboutUsView> selectListView(Wrapper<AboutUsEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public AboutUsView selectView(Wrapper<AboutUsEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
