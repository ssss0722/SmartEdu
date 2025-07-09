package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.PlanHistoryDao;
import com.dao.ScreenVideoDao;
import com.entity.PlanHistoryEntity;
import com.entity.ScreenVideoEntity;
import com.entity.view.ScreenVideoView;
import com.service.PlanHistoryService;
import com.service.ScreenVideoService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("ScreenVideoService")
public class ScreenVideoServiceImpl extends ServiceImpl<ScreenVideoDao, ScreenVideoEntity> implements ScreenVideoService {
    @Override
    public List<ScreenVideoView> selectListView(EntityWrapper<ScreenVideoEntity> ew) {
        return baseMapper.selectListView(ew);
    }

    @Override
    public ScreenVideoView selectView(EntityWrapper<ScreenVideoEntity> wrapper) {
        return baseMapper.selectView(wrapper);
    }
}
