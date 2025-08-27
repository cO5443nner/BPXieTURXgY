// 代码生成时间: 2025-08-28 07:44:46
package com.example.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Excel表格自动生成器服务类
 */
public class ExcelGeneratorService {

    private static final String TEMPLATE_PATH = "/path/to/template.xlsx"; // 模板文件路径
    private static final String OUTPUT_PATH = "/path/to/output.xlsx"; // 输出文件路径

    /**
     * 生成Excel表格
     *
     * @param data 要写入的数据
     * @throws IOException 如果写入文件时发生错误
     */
    public void generateExcel(List<List<String>> data) throws IOException {
        // 使用POI读取Excel模板
        Workbook workbook = new XSSFWorkbook(TEMPLATE_PATH);

        try {
            // 假设模板中只有一个sheet
            int sheetIndex = workbook.getSheetIndex("Sheet1");
            if (sheetIndex == -1) {
                throw new IllegalArgumentException("模板文件中未找到工作表");
            }

            // 获取工作表
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(sheetIndex);

            // 写入数据
            int rowIndex = 1; // 假设数据从第二行开始写入
            for (List<String> rowData : data) {
                if (rowIndex >= sheet.getLastRowNum() + 1) {
                    throw new IOException("工作表行数超出模板限制");
                }

                org.apache.poi.ss.usermodel.Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    row = sheet.createRow(rowIndex);
                }

                int cellIndex = 0;
                for (String cellData : rowData) {
                    org.apache.poi.ss.usermodel.Cell cell = row.getCell(cellIndex);
                    if (cell == null) {
                        cell = row.createCell(cellIndex);
                    }
                    cell.setCellValue(cellData);
                    cellIndex++;
                }
                rowIndex++;
            }

            // 写入文件
            try (FileOutputStream outputStream = new FileOutputStream(OUTPUT_PATH)) {
                workbook.write(outputStream);
            } finally {
                workbook.close();
            }

        } catch (Exception e) {
            throw new IOException("生成Excel文件时发生错误", e);
        }
    }
}
