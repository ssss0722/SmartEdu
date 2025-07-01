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


import com.dao.CourseCategoriesDao;
import com.entity.CourseCategoriesEntity;
import com.service.KechengleibieService;
import com.entity.vo.KechengleibieVO;
import com.entity.view.CourseCategoriesView;

@Service("kechengleibieService")
public class KechengleibieServiceImpl extends ServiceImpl<CourseCategoriesDao, CourseCategoriesEntity> implements KechengleibieService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CourseCategoriesEntity> page = this.selectPage(
                new Query<CourseCategoriesEntity>(params).getPage(),
                new EntityWrapper<CourseCategoriesEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<CourseCategoriesEntity> wrapper) {
		  Page<CourseCategoriesView> page =new Query<CourseCategoriesView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<KechengleibieVO> selectListVO(Wrapper<CourseCategoriesEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public KechengleibieVO selectVO(Wrapper<CourseCategoriesEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<CourseCategoriesView> selectListView(Wrapper<CourseCategoriesEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public CourseCategoriesView selectView(Wrapper<CourseCategoriesEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
