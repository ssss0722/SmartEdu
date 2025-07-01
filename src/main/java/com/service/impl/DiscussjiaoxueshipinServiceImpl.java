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


import com.dao.DiscussCourseVideoDao;
import com.entity.DiscussCourseVideoEntity;
import com.service.DiscussjiaoxueshipinService;
import com.entity.vo.DiscussjiaoxueshipinVO;
import com.entity.view.DiscussCourseVideoView;

@Service("discussjiaoxueshipinService")
public class DiscussjiaoxueshipinServiceImpl extends ServiceImpl<DiscussCourseVideoDao, DiscussCourseVideoEntity> implements DiscussjiaoxueshipinService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DiscussCourseVideoEntity> page = this.selectPage(
                new Query<DiscussCourseVideoEntity>(params).getPage(),
                new EntityWrapper<DiscussCourseVideoEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<DiscussCourseVideoEntity> wrapper) {
		  Page<DiscussCourseVideoView> page =new Query<DiscussCourseVideoView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<DiscussjiaoxueshipinVO> selectListVO(Wrapper<DiscussCourseVideoEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public DiscussjiaoxueshipinVO selectVO(Wrapper<DiscussCourseVideoEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<DiscussCourseVideoView> selectListView(Wrapper<DiscussCourseVideoEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public DiscussCourseVideoView selectView(Wrapper<DiscussCourseVideoEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
