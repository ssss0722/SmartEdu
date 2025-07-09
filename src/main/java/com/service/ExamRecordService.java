package com.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import com.entity.ExamRecordEntity;
import com.entity.vo.ExamDetailVO;
import com.entity.vo.ExamRecordVO;
import com.entity.vo.ExamStudentResultVO;

import java.util.List;
import java.util.Map;

public interface ExamRecordService extends IService<ExamRecordEntity> {

	/**
	 * 查询考试记录列表（含试卷、学生、课程信息）
	 * @return List<ExamRecordVO>
	 */
	List<ExamRecordVO> queryExamRecordList();

	void markSingleQuestion(Long id, Long score);

	/**
	 * 自动判分（根据记录id自动判分）
	 * @param recordId record表主键
	 */
	void autoMarkRecord(Long recordId);

	Map<String, Object> getExamDetail(Long paperId, String sUsername);
	List<ExamStudentResultVO> getStudentExamResults(String teacherUsername);
	List<ExamDetailVO> getExamDetailByHomework(Long examHomeworkId, String studentUsername);

}
