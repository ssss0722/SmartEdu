package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ExamPaperDao;
import com.dao.ExamQuestionBankDao;
import com.dao.ExamRecordDao;
import com.dao.StudentDao;
import com.entity.ExamQuestionBankEntity;
import com.entity.ExamRecordEntity;
import com.entity.StudentEntity;
import com.entity.vo.ExamDetailQuestionVO;
import com.entity.vo.ExamRecordVO;
import com.entity.vo.ExamStudentResultVO;
import com.service.ExamRecordService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service("examRecordService")
public class ExamRecordServiceImpl extends ServiceImpl<ExamRecordDao, ExamRecordEntity> implements ExamRecordService {

	@Autowired
	private ExamRecordDao examRecordDao;

	@Autowired
	private ExamQuestionBankDao examQuestionBankDao;

	@Override
	public List<ExamRecordVO> queryExamRecordList() {
		// 如果需要条件，可以传 EntityWrapper 进去
		return examRecordDao.selectExamRecordDetail(null);
	}

	@Override
	public void markSingleQuestion(Long id, Long score) {
		int updated = examRecordDao.markSingleQuestionById(id, score);
		if (updated == 0) {
			throw new RuntimeException("未找到指定记录");
		}
	}

	@Override
	public void autoMarkRecord(Long recordId) {
		ExamRecordEntity record = this.selectById(recordId);
		if (record == null) {
			throw new RuntimeException("试题记录不存在");
		}

		ExamQuestionBankEntity question = examQuestionBankDao.selectById(record.getQuestionId());
		if (question == null) {
			throw new RuntimeException("试题不存在");
		}

		Long qType = question.getType();
		String correctAnswer = question.getAnswer();
		Long qScore = question.getScore();

		if (qType == null || qScore == null || correctAnswer == null) {
			throw new RuntimeException("试题配置信息不完整");
		}

		// 判断题型
		if (Arrays.asList(0L, 1L, 2L, 3L).contains(qType)) {
			// 客观题
			boolean correct = false;

			if (qType == 1L) {
				// 多选题
				Set<String> stdSet = new HashSet<>(Arrays.asList(correctAnswer.split(",")));
				Set<String> ansSet = new HashSet<>(Arrays.asList(record.getMyanswer().split(",")));
				correct = stdSet.equals(ansSet);
			} else {
				// 单选/判断/填空
				correct = correctAnswer.trim().equals(record.getMyanswer().trim());
			}

			record.setMyscore(correct ? qScore : 0L);
			record.setIsmark(1L);
		} else {
			// 主观题，不能自动判分
			throw new RuntimeException("主观题不支持自动判分");
		}

		this.updateById(record);
	}
	@Autowired
	private ExamPaperDao examPaperDao;

	@Autowired
	private StudentDao userStudentDao;

	@Override
	public Map<String, Object> getExamDetail(Long paperId, String sUsername) {
		// 查试卷
		String paperName = examPaperDao.selectById(paperId).getTitle();

		// 查学生
		List<StudentEntity> students = userStudentDao.selectList(
				new EntityWrapper<StudentEntity>().eq("s_username", sUsername)
		);
		if (students.isEmpty()) {
			throw new RuntimeException("学生不存在");
		}
		String studentName = students.get(0).getsName();
		// 查答题记录
		List<ExamRecordEntity> records = examRecordDao.selectList(
				new EntityWrapper<ExamRecordEntity>()
						.eq("paperid", paperId)
						.eq("s_username", sUsername)
		);

		// 拼装题目列表
		List<ExamDetailQuestionVO> questions = new ArrayList<>();
		for (ExamRecordEntity record : records) {
			ExamQuestionBankEntity question = examQuestionBankDao.selectById(record.getQuestionId());
			ExamDetailQuestionVO vo = new ExamDetailQuestionVO();
			vo.setId(record.getQuestionId());
			vo.setQuestion(question.getTitle());
			vo.setCorrectAnswer(question.getAnswer());
			vo.setStudentAnswer(record.getMyanswer());

			String typeStr;
			switch (question.getType().intValue()) {
				case 0: typeStr = "单选题"; break;
				case 1: typeStr = "多选题"; break;
				case 2: typeStr = "判断题"; break;
				case 3: typeStr = "填空题"; break;
				case 4: typeStr = "主观题"; break;
				default: typeStr = "未知类型";
			}
			vo.setType(typeStr);

			questions.add(vo);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("paperName", paperName);
		result.put("studentName", studentName);
		result.put("questions", questions);

		return result;
	}

	@Autowired
	private ExamRecordDao examRecordMapper;

	@Override
	public List<ExamStudentResultVO> getStudentExamResults(String teacherUsername) {
		return examRecordMapper.selectExamStudentResultList(teacherUsername);
	}


}
