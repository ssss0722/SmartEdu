package com.service.impl;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.StudentDao;
import com.entity.StudentEntity;
import com.service.StudentService;
import com.entity.vo.StudentVO;
import com.entity.view.StudentView;

@Service("StudentService")
public class StudentServiceImpl extends ServiceImpl<StudentDao, StudentEntity> implements StudentService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<StudentEntity> page = this.selectPage(
                new Query<StudentEntity>(params).getPage(),
                new EntityWrapper<StudentEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<StudentEntity> wrapper) {
		  Page<StudentView> page =new Query<StudentView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

	@Override
	public List<StudentEntity> findStudentsByHomeworkId(Long id) {
		// 创建查询条件
		EntityWrapper<StudentEntity> ew = new EntityWrapper<>();

		// 构建SQL查询：通过exam_homework_student表关联
		ew.addFilter("s_username IN (SELECT s_username FROM exam_homework_student WHERE exam_id = " + id + ")");

		return this.selectList(ew);
	}


	@Override
	public List<StudentVO> selectListVO(Wrapper<StudentEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public StudentVO selectVO(Wrapper<StudentEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<StudentView> selectListView(Wrapper<StudentEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public StudentView selectView(Wrapper<StudentEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
