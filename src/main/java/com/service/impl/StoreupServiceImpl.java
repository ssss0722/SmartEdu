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


import com.dao.StoreUpDao;
import com.entity.StoreUpEntity;
import com.service.StoreupService;
import com.entity.vo.StoreupVO;
import com.entity.view.StoreUpView;

@Service("storeupService")
public class StoreupServiceImpl extends ServiceImpl<StoreUpDao, StoreUpEntity> implements StoreupService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<StoreUpEntity> page = this.selectPage(
                new Query<StoreUpEntity>(params).getPage(),
                new EntityWrapper<StoreUpEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<StoreUpEntity> wrapper) {
		  Page<StoreUpView> page =new Query<StoreUpView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<StoreupVO> selectListVO(Wrapper<StoreUpEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public StoreupVO selectVO(Wrapper<StoreUpEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<StoreUpView> selectListView(Wrapper<StoreUpEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public StoreUpView selectView(Wrapper<StoreUpEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
