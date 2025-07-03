package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ExamStudentDao;
import com.entity.ExamStudentEntity;
import com.entity.view.ExamStudentView;
import com.entity.vo.ExamStudentVO;
import com.service.ExamStudentService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("examStudentService")
public class ExamStudentServiceImpl extends ServiceImpl<ExamStudentDao, ExamStudentEntity> implements ExamStudentService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExamStudentEntity> page = this.selectPage(
                new Query<ExamStudentEntity>(params).getPage(),
                new EntityWrapper<>()
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<ExamStudentEntity> wrapper) {
        Page<ExamStudentView> page = new Query<ExamStudentView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page, wrapper));
        return new PageUtils(page);
    }

    @Override
    public List<ExamStudentVO> selectListVO(Wrapper<ExamStudentEntity> wrapper) {
        return baseMapper.selectListVO(wrapper);
    }

    @Override
    public ExamStudentVO selectVO(Wrapper<ExamStudentEntity> wrapper) {
        return baseMapper.selectVO(wrapper);
    }

    @Override
    public List<ExamStudentView> selectListView(Wrapper<ExamStudentEntity> wrapper) {
        return baseMapper.selectListView(wrapper);
    }

    @Override
    public ExamStudentView selectView(Wrapper<ExamStudentEntity> wrapper) {
        return baseMapper.selectView(wrapper);
    }

    @Override
    public List<ExamStudentEntity> getByExamId(Long examId) {
        return baseMapper.selectList(new EntityWrapper<ExamStudentEntity>().eq("exam_id", examId));
    }
}
