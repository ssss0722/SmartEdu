package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ExamDao;
import com.dto.ExamExportDTO;
import com.entity.ExamEntity;
import com.entity.view.ExamView;
import com.entity.vo.ExamVO;
import com.service.ExamService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("examService")
public class ExamServiceImpl extends ServiceImpl<ExamDao, ExamEntity> implements ExamService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExamEntity> page = this.selectPage(
                new Query<ExamEntity>(params).getPage(),
                new EntityWrapper<ExamEntity>()
        );
        return new PageUtils(page);
    }
    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<ExamEntity> wrapper) {
        Page<ExamView> page = new Query<ExamView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page, wrapper));
        return new PageUtils(page);
    }

    @Override
    public List<ExamVO> selectListVO(Wrapper<ExamEntity> wrapper) {
        return baseMapper.selectListVO(wrapper);
    }

    @Override
    public ExamVO selectVO(Wrapper<ExamEntity> wrapper) {
        return baseMapper.selectVO(wrapper);
    }

    @Override
    public List<ExamView> selectListView(Wrapper<ExamEntity> wrapper) {
        return baseMapper.selectListView(wrapper);
    }

    @Override
    public ExamView selectView(Wrapper<ExamEntity> wrapper) {
        return baseMapper.selectView(wrapper);
    }
    @Autowired
    private ExamDao examDao;

    @Override
    public List<ExamExportDTO> getExportData(String examName) {
        return examDao.exportExamResult(examName);
    }

}
