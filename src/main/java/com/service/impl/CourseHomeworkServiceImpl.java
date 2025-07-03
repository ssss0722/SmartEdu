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


import com.dao.CourseHomeworkDao;
import com.entity.CourseHomeworkEntity;
import com.service.CourseHomeworkService;
import com.entity.vo.CourseHomeworkVO;
import com.entity.view.CourseHomeworkView;

@Service("CourseHomeworkService")
public class CourseHomeworkServiceImpl extends ServiceImpl<CourseHomeworkDao, CourseHomeworkEntity> implements CourseHomeworkService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CourseHomeworkEntity> page = this.selectPage(
                new Query<CourseHomeworkEntity>(params).getPage(),
                new EntityWrapper<CourseHomeworkEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<CourseHomeworkEntity> wrapper) {
		  Page<CourseHomeworkView> page =new Query<CourseHomeworkView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<CourseHomeworkVO> selectListVO(Wrapper<CourseHomeworkEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public CourseHomeworkVO selectVO(Wrapper<CourseHomeworkEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<CourseHomeworkView> selectListView(Wrapper<CourseHomeworkEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public CourseHomeworkView selectView(Wrapper<CourseHomeworkEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
