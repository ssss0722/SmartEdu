package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.ExamRecordEntity;
import com.entity.HomeworkRecordEntity;
import com.entity.view.HomeworkRecordView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface HomeworkRecordDao extends BaseMapper<HomeworkRecordEntity> {
    List<HomeworkRecordView> selectGroupBy(Page<HomeworkRecordView> page,@Param("ew") Wrapper wrapper);

    List<HomeworkRecordView> selectOptionsNum(Page<HomeworkRecordView> page,@Param("ew") Wrapper wrapper);

    List<HomeworkRecordView> selectListView(Page<HomeworkRecordView> page,@Param("ew") Wrapper wrapper);

    List<HomeworkRecordView> selectListView(@Param("ew") EntityWrapper<HomeworkRecordEntity> ew);

    HomeworkRecordView selectView(@Param("ew") EntityWrapper<HomeworkRecordEntity> ew);

    @Select("SELECT SUM(myscore) AS total_score " +
            "FROM course_homework_record " +
            "WHERE s_username = #{s_username} " +
            "AND homework_id = #{homework_id}")
    int selectTotalScore(Map<String, Object> params);
}
