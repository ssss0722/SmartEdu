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


import com.dao.ExamQuestionDao;
import com.entity.ExamQuestionEntity;
import com.service.ExamquestionService;
import com.entity.vo.ExamQuestionVO;
import com.entity.view.ExamQuestionView;

@Service("examquestionService")
public class ExamquestionServiceImpl extends ServiceImpl<ExamQuestionDao, ExamQuestionEntity> implements ExamquestionService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExamQuestionEntity> page = this.selectPage(
                new Query<ExamQuestionEntity>(params).getPage(),
                new EntityWrapper<ExamQuestionEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ExamQuestionEntity> wrapper) {
		  Page<ExamQuestionView> page =new Query<ExamQuestionView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ExamQuestionVO> selectListVO(Wrapper<ExamQuestionEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ExamQuestionVO selectVO(Wrapper<ExamQuestionEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ExamQuestionView> selectListView(Wrapper<ExamQuestionEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ExamQuestionView selectView(Wrapper<ExamQuestionEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
