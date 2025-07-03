package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.ExamRecordEntity;
import com.entity.HomeworkRecordEntity;
import com.entity.view.HomeworkRecordView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HomeworkRecordDao extends BaseMapper<HomeworkRecordEntity> {
    List<HomeworkRecordView> selectGroupBy(Page<HomeworkRecordView> page,@Param("ew") Wrapper wrapper);

    List<HomeworkRecordView> selectOptionsNum(Page<HomeworkRecordView> page,@Param("ew") Wrapper wrapper);

    List<HomeworkRecordView> selectListView(Page<HomeworkRecordView> page,@Param("ew") Wrapper wrapper);

    List<HomeworkRecordView> selectListView(@Param("ew") EntityWrapper<HomeworkRecordEntity> ew);

    HomeworkRecordView selectView(@Param("ew") EntityWrapper<HomeworkRecordEntity> ew);
}
