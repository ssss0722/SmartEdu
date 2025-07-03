package com.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.HomeworkRecordEntity;
import com.entity.view.HomeworkRecordView;
import com.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HomeworkRecordService extends IService<HomeworkRecordEntity> {
    PageUtils queryPageGroupBy(Map<String, Object> params, @Param("ew") Wrapper wrapper);

    PageUtils queryPageOptionsNum(Map<String, Object> params,@Param("ew") Wrapper wrapper);

    PageUtils queryPage(Map<String, Object> params,@Param("ew") Wrapper wrapper);

    List<HomeworkRecordView> selectListView(@Param("ew") EntityWrapper<HomeworkRecordEntity> ew);

    HomeworkRecordView selectView(@Param("ew") EntityWrapper<HomeworkRecordEntity> ew);
}
