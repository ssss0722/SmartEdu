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


import com.dao.SystemIntroDao;
import com.entity.SystemIntroEntity;
import com.service.SystemintroService;
import com.entity.vo.SystemintroVO;
import com.entity.view.SystemIntroView;

@Service("systemintroService")
public class SystemintroServiceImpl extends ServiceImpl<SystemIntroDao, SystemIntroEntity> implements SystemintroService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SystemIntroEntity> page = this.selectPage(
                new Query<SystemIntroEntity>(params).getPage(),
                new EntityWrapper<SystemIntroEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<SystemIntroEntity> wrapper) {
		  Page<SystemIntroView> page =new Query<SystemIntroView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<SystemintroVO> selectListVO(Wrapper<SystemIntroEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public SystemintroVO selectVO(Wrapper<SystemIntroEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<SystemIntroView> selectListView(Wrapper<SystemIntroEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public SystemIntroView selectView(Wrapper<SystemIntroEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
