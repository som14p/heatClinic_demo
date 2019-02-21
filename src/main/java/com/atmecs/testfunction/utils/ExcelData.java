package com.atmecs.testfunction.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.atmecs.falcon.automation.util.parser.XlsReader;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;

public class ExcelData {

    private final XlsReader xlsReader = new XlsReader();
    private final ReportLogService logService = new ReportLogServiceImpl(ExcelData.class);

    public Object[][] getExcelData(String filePath, String sheetName) {
        String[][] data = null;
        int rowCount;
        int col;
        try {
            xlsReader.setPath(filePath);
            System.out.println("*****filepath*********"+filePath);
            rowCount = xlsReader.getRowCount(sheetName);
            col = xlsReader.getColumnCount(sheetName);
            data = new String[rowCount][col];
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < col; j++) {
                    data[i][j] = xlsReader.getCellDataByColumnIndex(sheetName, j, i + 1);
                    System.out.println("value("+i+","+j+") = "+ data[i][j]);
                }
            }

        } catch (FileNotFoundException e) {
            logService.info("File not found ," + e.getMessage());
        } catch (IOException e) {
            logService.info(e.getMessage());
        }
        return data;
    }

}
