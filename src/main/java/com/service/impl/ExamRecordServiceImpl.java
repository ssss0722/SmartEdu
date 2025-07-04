package com.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ExamQuestionBankDao;
import com.dao.ExamRecordDao;
import com.entity.ExamQuestionBankEntity;
import com.entity.ExamRecordEntity;
import com.entity.vo.ExamRecordVO;
import com.service.ExamRecordService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


}
