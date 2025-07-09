package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.PlanHistoryEntity;

public interface PlanHistoryService extends IService<PlanHistoryEntity> {
    String selectHistory(Long id, int i);
}
