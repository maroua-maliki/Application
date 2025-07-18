package org.demo.demo.services;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.demo.demo.controller.AddFileController.RowData;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReaderService {

    public List<RowData> readExcelFile(String filePath) throws IOException {
        List<RowData> tableData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook;

            if (filePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (filePath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(fis);
            } else {
                throw new IOException("Format de fichier non pris en charge.");
            }

            Sheet sheet = workbook.getSheetAt(2); // Troisième feuille
            int currentRow = 0;

            for (Row row : sheet) {
                if (currentRow >= 5) {
                    Cell col1 = row.getCell(0);
                    Cell col2 = row.getCell(1);
                    Cell col3 = row.getCell(148);
                    Cell col4 = row.getCell(149);

                    if (col2 != null && !col2.toString().trim().isEmpty()) {
                        String value1 = (col1 != null) ? col1.toString() : "";
                        String value2 = col2.toString();
                        String value3 = (col3 != null) ? col3.toString() : "";
                        String value4 = (col4 != null) ? col4.toString() : "";
                        tableData.add(new RowData(value1, value2, value3, value4));
                    }
                }
                currentRow++;
            }

            workbook.close();
        }

        return tableData;
    }
}

