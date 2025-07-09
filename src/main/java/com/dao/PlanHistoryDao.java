package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.entity.PlanHistoryEntity;
import org.apache.ibatis.annotations.Select;

public interface PlanHistoryDao extends BaseMapper<PlanHistoryEntity> {

    @Select("select content from ai_plan where t_id=#{id} and type=#{i}")
    String selectHistory(Long id, int i);

}
