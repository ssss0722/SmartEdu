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


import com.dao.CourseHomeworkReviewDao;
import com.entity.CourseHomeworkReviewEntity;
import com.service.CourseHomeworkReviewService;
import com.entity.vo.CourseHomeworkReviewVO;
import com.entity.view.CourseHomeworkReviewView;

@Service("CourseHomeworkReviewService")
public class CourseHomeworkReviewServiceImpl extends ServiceImpl<CourseHomeworkReviewDao, CourseHomeworkReviewEntity> implements CourseHomeworkReviewService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CourseHomeworkReviewEntity> page = this.selectPage(
                new Query<CourseHomeworkReviewEntity>(params).getPage(),
                new EntityWrapper<CourseHomeworkReviewEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<CourseHomeworkReviewEntity> wrapper) {
		  Page<CourseHomeworkReviewView> page =new Query<CourseHomeworkReviewView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<CourseHomeworkReviewVO> selectListVO(Wrapper<CourseHomeworkReviewEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public CourseHomeworkReviewVO selectVO(Wrapper<CourseHomeworkReviewEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<CourseHomeworkReviewView> selectListView(Wrapper<CourseHomeworkReviewEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public CourseHomeworkReviewView selectView(Wrapper<CourseHomeworkReviewEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

    @Override
    public List<Map<String, Object>> selectValue(Map<String, Object> params, Wrapper<CourseHomeworkReviewEntity> wrapper) {
        return baseMapper.selectValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params, Wrapper<CourseHomeworkReviewEntity> wrapper) {
        return baseMapper.selectTimeStatValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectGroup(Map<String, Object> params, Wrapper<CourseHomeworkReviewEntity> wrapper) {
        return baseMapper.selectGroup(params, wrapper);
    }




}
