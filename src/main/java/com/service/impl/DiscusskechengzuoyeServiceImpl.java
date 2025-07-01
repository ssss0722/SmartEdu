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


import com.dao.DiscussCourseHomeworkDao;
import com.entity.DiscussCourseHomeworkEntity;
import com.service.DiscusskechengzuoyeService;
import com.entity.vo.DiscusskechengzuoyeVO;
import com.entity.view.DiscussCourseHomeworkView;

@Service("discusskechengzuoyeService")
public class DiscusskechengzuoyeServiceImpl extends ServiceImpl<DiscussCourseHomeworkDao, DiscussCourseHomeworkEntity> implements DiscusskechengzuoyeService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DiscussCourseHomeworkEntity> page = this.selectPage(
                new Query<DiscussCourseHomeworkEntity>(params).getPage(),
                new EntityWrapper<DiscussCourseHomeworkEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<DiscussCourseHomeworkEntity> wrapper) {
		  Page<DiscussCourseHomeworkView> page =new Query<DiscussCourseHomeworkView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<DiscusskechengzuoyeVO> selectListVO(Wrapper<DiscussCourseHomeworkEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public DiscusskechengzuoyeVO selectVO(Wrapper<DiscussCourseHomeworkEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<DiscussCourseHomeworkView> selectListView(Wrapper<DiscussCourseHomeworkEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public DiscussCourseHomeworkView selectView(Wrapper<DiscussCourseHomeworkEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
