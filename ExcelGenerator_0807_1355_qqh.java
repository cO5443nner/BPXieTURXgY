// 代码生成时间: 2025-08-07 13:55:09
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelGenerator {

    /**
     * Generates an Excel file with the given data and writes it to the specified path.
     *
     * @param data       a 2D array containing the data to be written to the Excel file
     * @param sheetName  the name of the sheet in the Excel file
     * @param filePath   the path where the Excel file will be saved
     * @throws IOException if an I/O error occurs while writing the file
     */
    public void generateExcelFile(String[][] data, String sheetName, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook(); // Create a new Excel workbook
        Sheet sheet = workbook.createSheet(sheetName); // Create a sheet with the given name

        // Iterate over the data and write it to the sheet
        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i); // Create a row for each data array
            for (int j = 0; j < data[i].length; j++) {
                Cell cell = row.createCell(j); // Create a cell for each data element
                cell.setCellValue(data[i][j]); // Set the cell value
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream); // Write the workbook to the file
        } finally {
            workbook.close(); // Close the workbook to free resources
        }
    }

    public static void main(String[] args) {
        ExcelGenerator excelGenerator = new ExcelGenerator();

        // Example data to be written to the Excel file
        // The data should be provided or read from a source in a real application
        String[][] data = {
                {