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


import com.dao.NewsTypeDao;
import com.entity.NewsTypeEntity;
import com.service.NewstypeService;
import com.entity.vo.NewstypeVO;
import com.entity.view.NewsTypeView;

@Service("newstypeService")
public class NewstypeServiceImpl extends ServiceImpl<NewsTypeDao, NewsTypeEntity> implements NewstypeService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<NewsTypeEntity> page = this.selectPage(
                new Query<NewsTypeEntity>(params).getPage(),
                new EntityWrapper<NewsTypeEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<NewsTypeEntity> wrapper) {
		  Page<NewsTypeView> page =new Query<NewsTypeView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<NewstypeVO> selectListVO(Wrapper<NewsTypeEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public NewstypeVO selectVO(Wrapper<NewsTypeEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<NewsTypeView> selectListView(Wrapper<NewsTypeEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public NewsTypeView selectView(Wrapper<NewsTypeEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
