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


import com.dao.ExamPaperDao;
import com.entity.ExamPaperEntity;
import com.service.ExampaperService;
import com.entity.vo.ExampaperVO;
import com.entity.view.ExamPaperView;

@Service("exampaperService")
public class ExampaperServiceImpl extends ServiceImpl<ExamPaperDao, ExamPaperEntity> implements ExampaperService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExamPaperEntity> page = this.selectPage(
                new Query<ExamPaperEntity>(params).getPage(),
                new EntityWrapper<ExamPaperEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ExamPaperEntity> wrapper) {
		  Page<ExamPaperView> page =new Query<ExamPaperView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ExampaperVO> selectListVO(Wrapper<ExamPaperEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ExampaperVO selectVO(Wrapper<ExamPaperEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ExamPaperView> selectListView(Wrapper<ExamPaperEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ExamPaperView selectView(Wrapper<ExamPaperEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
