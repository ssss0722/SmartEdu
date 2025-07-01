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


import com.dao.CourseHomeworkSubmissionDao;
import com.entity.CourseHomeworkSubmissionEntity;
import com.service.ZuoyetijiaoService;
import com.entity.vo.ZuoyetijiaoVO;
import com.entity.view.CourseHomeworkSubmissionView;

@Service("zuoyetijiaoService")
public class ZuoyetijiaoServiceImpl extends ServiceImpl<CourseHomeworkSubmissionDao, CourseHomeworkSubmissionEntity> implements ZuoyetijiaoService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CourseHomeworkSubmissionEntity> page = this.selectPage(
                new Query<CourseHomeworkSubmissionEntity>(params).getPage(),
                new EntityWrapper<CourseHomeworkSubmissionEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<CourseHomeworkSubmissionEntity> wrapper) {
		  Page<CourseHomeworkSubmissionView> page =new Query<CourseHomeworkSubmissionView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ZuoyetijiaoVO> selectListVO(Wrapper<CourseHomeworkSubmissionEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ZuoyetijiaoVO selectVO(Wrapper<CourseHomeworkSubmissionEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<CourseHomeworkSubmissionView> selectListView(Wrapper<CourseHomeworkSubmissionEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public CourseHomeworkSubmissionView selectView(Wrapper<CourseHomeworkSubmissionEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
