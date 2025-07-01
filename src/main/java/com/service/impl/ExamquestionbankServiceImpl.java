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


import com.dao.ExamQuestionBankDao;
import com.entity.ExamQuestionBankEntity;
import com.service.ExamquestionbankService;
import com.entity.vo.ExamquestionbankVO;
import com.entity.view.ExamQuestionBankView;

@Service("examquestionbankService")
public class ExamquestionbankServiceImpl extends ServiceImpl<ExamQuestionBankDao, ExamQuestionBankEntity> implements ExamquestionbankService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExamQuestionBankEntity> page = this.selectPage(
                new Query<ExamQuestionBankEntity>(params).getPage(),
                new EntityWrapper<ExamQuestionBankEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ExamQuestionBankEntity> wrapper) {
		  Page<ExamQuestionBankView> page =new Query<ExamQuestionBankView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ExamquestionbankVO> selectListVO(Wrapper<ExamQuestionBankEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ExamquestionbankVO selectVO(Wrapper<ExamQuestionBankEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ExamQuestionBankView> selectListView(Wrapper<ExamQuestionBankEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ExamQuestionBankView selectView(Wrapper<ExamQuestionBankEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
