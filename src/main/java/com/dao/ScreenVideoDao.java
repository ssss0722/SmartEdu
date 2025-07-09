package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ScreenVideoEntity;
import com.entity.view.ScreenVideoView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScreenVideoDao extends BaseMapper<ScreenVideoEntity> {
    List<ScreenVideoView> selectListView(@Param("ew") EntityWrapper<ScreenVideoEntity> ew);

    ScreenVideoView selectView(@Param("ew") EntityWrapper<ScreenVideoEntity> wrapper);
}
