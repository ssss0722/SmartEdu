package com.controller;

import com.dto.ExamExportDTO;
import com.service.ExamService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamExportController {

    @Autowired
    private ExamService examService;

    @GetMapping("/exportExcel")
    public void exportExamResult(@RequestParam String examName, HttpServletResponse response) throws IOException {
        List<ExamExportDTO> dataList = examService.getExportData(examName);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("考试成绩表");

        HSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("学生姓名");
        header.createCell(1).setCellValue("考试名称");
        header.createCell(2).setCellValue("开始时间");
        header.createCell(3).setCellValue("结束时间");
        header.createCell(4).setCellValue("总成绩");

        for (int i = 0; i < dataList.size(); i++) {
            ExamExportDTO dto = dataList.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(dto.getStudentName());
            row.createCell(1).setCellValue(dto.getExamName());
            row.createCell(2).setCellValue(dto.getStartTime());
            row.createCell(3).setCellValue(dto.getEndTime());
            row.createCell(4).setCellValue(dto.getTotalScore() == null ? 0 : dto.getTotalScore());
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(examName + "_成绩表.xls", "UTF-8"));

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
