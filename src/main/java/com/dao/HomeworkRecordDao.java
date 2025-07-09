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
            "FROM record " +
            "WHERE s_username = #{s_username} " +
            "AND paperid = #{homework_id}")
    int selectTotalScore(Map<String, Object> params);

    @Select("SELECT" +
            "    ANY_VALUE(s.s_name) AS studentName, " +
            "    s.s_username AS sUsername, " +
            "    ANY_VALUE(p.title) AS homeworkName, " +
            "    ANY_VALUE(cc.course) AS courseName, " +
            "    SUM(r.myscore) AS score, " +
            "    p.id AS homeworkId, " +
            "    (SELECT SUM(qb.score) " +
            "     FROM question q " +
            "     JOIN question_bank qb ON q.question_id = qb.id " +
            "     WHERE q.paperid = p.id) AS total_score, " +
            "    MAX(r.addtime) AS submitTime, " +
            "    IF(MIN(r.ismark) > 0, 1, 0) AS isMarked, " +
            "    ANY_VALUE(t.t_name) AS teacherName " +
            "FROM record r " +
            "JOIN user_student s ON r.s_username = s.s_username " +
            "JOIN paper p ON r.paperid = p.id " +
            "JOIN user_teacher t ON p.t_username = t.t_username " +
            "JOIN course_categories cc ON p.course_id = cc.id " +
            "WHERE t.t_username = #{teacherUsername} " +
            "  AND p.status = '0' " +
            "GROUP BY p.id, s.s_username") // 按作业和学生分组
    List<Map<String, Object>> selectTeacherHomework(String teacherUsername);
}
