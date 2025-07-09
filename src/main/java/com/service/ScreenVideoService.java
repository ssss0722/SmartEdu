package com.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.ScreenVideoEntity;
import com.entity.view.CourseVideoView;
import com.entity.view.ScreenVideoView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScreenVideoService extends IService<ScreenVideoEntity> {
    List<ScreenVideoView> selectListView(@Param("ew") EntityWrapper<ScreenVideoEntity> ew);

    ScreenVideoView selectView(@Param("ew") EntityWrapper<ScreenVideoEntity> wrapper);
}
