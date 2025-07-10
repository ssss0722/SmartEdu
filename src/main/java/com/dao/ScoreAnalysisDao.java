package com.dao;

import com.entity.StudentScore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreAnalysisDao {
    List<StudentScore> queryScoresByExamName(@Param("examName") String examName);
}
