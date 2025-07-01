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


import com.dao.CourseVideoDao;
import com.entity.CourseVideoEntity;
import com.service.JiaoxueshipinService;
import com.entity.vo.JiaoxueshipinVO;
import com.entity.view.CourseVideoView;

@Service("jiaoxueshipinService")
public class JiaoxueshipinServiceImpl extends ServiceImpl<CourseVideoDao, CourseVideoEntity> implements JiaoxueshipinService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CourseVideoEntity> page = this.selectPage(
                new Query<CourseVideoEntity>(params).getPage(),
                new EntityWrapper<CourseVideoEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<CourseVideoEntity> wrapper) {
		  Page<CourseVideoView> page =new Query<CourseVideoView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<JiaoxueshipinVO> selectListVO(Wrapper<CourseVideoEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public JiaoxueshipinVO selectVO(Wrapper<CourseVideoEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<CourseVideoView> selectListView(Wrapper<CourseVideoEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public CourseVideoView selectView(Wrapper<CourseVideoEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
