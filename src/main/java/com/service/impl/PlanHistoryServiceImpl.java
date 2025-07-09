package com.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.PlanHistoryDao;
import com.entity.PlanHistoryEntity;
import com.service.PlanHistoryService;
import org.springframework.stereotype.Service;

@Service("PlanHistoryService")
public class PlanHistoryServiceImpl extends ServiceImpl<PlanHistoryDao, PlanHistoryEntity> implements PlanHistoryService {
    @Override
    public String selectHistory(Long id, int i) {
        return baseMapper.selectHistory(id,i);
    }
}
