package com.service.impl;

import com.dao.ScoreAnalysisDao;
import com.entity.StudentScore;
import com.service.ScoreAnalysisService;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.nio.charset.StandardCharsets; // 导入 StandardCharsets

@Service
public class ScoreAnalysisServiceImpl implements ScoreAnalysisService {

    @Autowired
    private ScoreAnalysisDao scoreAnalysisDao;

    @Value("${qianfan.apiKey}")
    private String apiKey;

    @Override
    public String analyzeByExamName(String examName) throws IOException {
        List<StudentScore> scores = scoreAnalysisDao.queryScoresByExamName(examName);

        if (scores == null || scores.isEmpty()) {
            return "未找到考试 \"" + examName + "\" 的成绩数据。";
        }

        String prompt = buildPrompt(examName, scores);
        return callQianfanAPI(prompt);
    }

    // 构建 AI 分析用的提示词
    private String buildPrompt(String examName, List<StudentScore> scores) {
        StringBuilder sb = new StringBuilder();
        sb.append("请帮我分析以下考试成绩：\n");
        sb.append("考试名称：").append(examName).append("\n");

        double total = 0;
        double max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        for (StudentScore s : scores) {
            total += s.getTotalScore();
            max = Math.max(max, s.getTotalScore());
            min = Math.min(min, s.getTotalScore());
        }

        double avg = total / scores.size();

        sb.append("平均分：").append(String.format("%.2f", avg)).append("\n");
        sb.append("最高分：").append((int) max).append("\n");
        sb.append("最低分：").append((int) min).append("\n\n");
        sb.append("学生成绩如下：\n");

        for (StudentScore s : scores) {
            sb.append(s.getS_name()).append("：").append(s.getTotalScore()).append("分\n");
        }

        sb.append("\n请从整体表现、分数分布、学生差异、教学建议等方面进行分析。");

        return sb.toString();
    }

    // 调用百度千帆 API 进行分析
    private String callQianfanAPI(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // 连接超时：30秒
                .readTimeout(60, TimeUnit.SECONDS)    // 读取超时：60秒 (AI响应可能较长)
                .writeTimeout(30, TimeUnit.SECONDS)   // 写入超时：30秒
                .build();

        JSONObject json = new JSONObject();
        json.put("model", "ernie-3.5-8k");

        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", "你是一个考试成绩分析专家"));
        messages.put(new JSONObject().put("role", "user").put("content", prompt));

        json.put("messages", messages);

        RequestBody body = RequestBody.create(
                json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://qianfan.baidubce.com/v2/chat/completions")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            // 这里修改了错误信息，确保打印响应体以便调试
            String errorBody = (response.body() != null) ? response.body().string() : "无响应体";
            throw new IOException("调用百度千帆API失败：" + response.code() + " " + response.message() + " 响应体: " + errorBody);
        }

        String responseStr;
        if (response.body() != null) {
            // **核心修改：显式指定响应体的编码为 UTF-8**
            // 获取原始字节数组
            byte[] responseBytes = response.body().bytes();
            // 使用 UTF-8 编码将字节数组转换为字符串
            responseStr = new String(responseBytes, StandardCharsets.UTF_8);
        } else {
            responseStr = "";
        }

        JSONObject result = new JSONObject(responseStr);

        return result.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }
}