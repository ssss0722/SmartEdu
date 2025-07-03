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


import com.dao.ExamRecordDao;
import com.entity.ExamRecordEntity;
import com.service.ExamrecordService;
import com.entity.vo.ExamRecordVO;
import com.entity.view.ExamRecordView;

@Service("examrecordService")
public class ExamrecordServiceImpl extends ServiceImpl<ExamRecordDao, ExamRecordEntity> implements ExamrecordService {
	
	@Override
	public PageUtils queryPageGroupBy(Map<String, Object> params, Wrapper<ExamRecordEntity> wrapper) {
		Page<ExamRecordView> page =new Query<ExamRecordView>(params).getPage();
        page.setRecords(baseMapper.selectGroupBy(page,wrapper));
    	PageUtils pageUtil = new PageUtils(page);
    	return pageUtil;
	}

    @Override
    public PageUtils queryPageOptionsNum(Map<String, Object> params, Wrapper<ExamRecordEntity> wrapper) {
        Page<ExamRecordView> page =new Query<ExamRecordView>(params).getPage();
        page.setRecords(baseMapper.selectOptionsNum(page,wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExamRecordEntity> page = this.selectPage(
                new Query<ExamRecordEntity>(params).getPage(),
                new EntityWrapper<ExamRecordEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ExamRecordEntity> wrapper) {
		  Page<ExamRecordView> page =new Query<ExamRecordView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ExamRecordVO> selectListVO(Wrapper<ExamRecordEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ExamRecordVO selectVO(Wrapper<ExamRecordEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ExamRecordView> selectListView(Wrapper<ExamRecordEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ExamRecordView selectView(Wrapper<ExamRecordEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
