package bo.com.reportate.util;

/**
 * Created by :MC4
 * Autor      :Ricardo Laredo
 * Email      :rlaredo@mc4.com.bo
 * Date       :02-06-19
 * Project    :reportate
 * Package    :bo.com.reportate.core.util
 * Copyright  : MC4
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ExcelPOIHelper {

    public Map<Integer, List<MyCell>> readExcel(String fileLocation) throws IOException {

        Map<Integer, List<MyCell>> data = new HashMap<>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(fileLocation));
            if (fileLocation.endsWith(".xls")) {
                data = readHSSFWorkbook(fis);
            } else if (fileLocation.endsWith(".xlsx")) {
                data = readXSSFWorkbook(fis);
            }

            int maxNrCols = data.values().stream()
                    .mapToInt(List::size)
                    .max()
                    .orElse(0);

            data.values().stream()
                    .filter(ls -> ls.size() < maxNrCols)
                    .forEach(ls -> {
                        IntStream.range(ls.size(), maxNrCols)
                                .forEach(i -> ls.add(new MyCell("")));
                    });
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error("Error al liberar el recurso FileInputStream de cargar archivo", e);
                }
            }
        }

        return data;
    }

    public Map<Integer, List<MyCell>> readExcel(MultipartFile bankStatement) throws IOException {
        Map<Integer, List<MyCell>> data = new HashMap<>();
        if (bankStatement.getOriginalFilename().endsWith(".xls")) {
            data = readHSSFWorkbook(bankStatement.getInputStream());
        } else if (bankStatement.getOriginalFilename().endsWith(".xlsx")) {
            data = readXSSFWorkbook(bankStatement.getInputStream());
        }
        int maxNrCols = data.values().stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        data.values().stream()
                .filter(ls -> ls.size() < maxNrCols)
                .forEach(ls -> {
                    IntStream.range(ls.size(), maxNrCols)
                            .forEach(i -> ls.add(new MyCell("")));
                });

        return data;
    }

    private String readCellContent(Cell cell) {
        String content;

        switch (cell.getCellTypeEnum()) {
            case STRING:
                content = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    content = cell.getDateCellValue() + "";
                } else {
                    content = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                content = cell.getBooleanCellValue() + "";
                break;
            case FORMULA:
                content = cell.getCellFormula() + "";
                break;
            default:
                content = "";
        }
        return content;
    }

    private Map<Integer, List<MyCell>> readHSSFWorkbook(InputStream fis) throws IOException {
        Map<Integer, List<MyCell>> data = new HashMap<>();
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(fis);

            HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                data.put(i, new ArrayList<>());
                if (row != null) {
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        HSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            HSSFCellStyle cellStyle = cell.getCellStyle();

                            MyCell myCell = new MyCell();

                            HSSFColor bgColor = cellStyle.getFillForegroundColorColor();
                            if (bgColor != null) {
                                short[] rgbColor = bgColor.getTriplet();
                                myCell.setBgColor("rgb(" + rgbColor[0] + "," + rgbColor[1] + "," + rgbColor[2] + ")");
                            }
                            HSSFFont font = cell.getCellStyle()
                                    .getFont(workbook);
                            myCell.setTextSize(font.getFontHeightInPoints() + "");
                            if (font.getBold()) {
                                myCell.setTextWeight("bold");
                            }
                            HSSFColor textColor = font.getHSSFColor(workbook);
                            if (textColor != null) {
                                short[] rgbColor = textColor.getTriplet();
                                myCell.setTextColor("rgb(" + rgbColor[0] + "," + rgbColor[1] + "," + rgbColor[2] + ")");
                            }
                            myCell.setContent(readCellContent(cell));
                            data.get(i)
                                    .add(myCell);
                        } else {
                            data.get(i)
                                    .add(new MyCell(""));
                        }
                    }
                }
            }
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return data;
    }

    private Map<Integer, List<MyCell>> readXSSFWorkbook(InputStream fis) throws IOException {
        XSSFWorkbook workbook = null;
        Map<Integer, List<MyCell>> data = new HashMap<>();
        try {

            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                data.put(i, new ArrayList<>());
                if (row != null) {
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        XSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            XSSFCellStyle cellStyle = cell.getCellStyle();

                            MyCell myCell = new MyCell();
                            XSSFColor bgColor = cellStyle.getFillForegroundColorColor();
                            if (bgColor != null) {
                                byte[] rgbColor = bgColor.getRGB();
                                myCell.setBgColor("rgb(" + (rgbColor[0] < 0 ? (rgbColor[0] + 0xff) : rgbColor[0]) + "," + (rgbColor[1] < 0 ? (rgbColor[1] + 0xff) : rgbColor[1]) + "," + (rgbColor[2] < 0 ? (rgbColor[2] + 0xff) : rgbColor[2]) + ")");
                            }
                            XSSFFont font = cellStyle.getFont();
                            myCell.setTextSize(font.getFontHeightInPoints() + "");
                            if (font.getBold()) {
                                myCell.setTextWeight("bold");
                            }
                            XSSFColor textColor = font.getXSSFColor();
                            if (textColor != null) {
                                byte[] rgbColor = textColor.getRGB();
                                myCell.setTextColor("rgb(" + (rgbColor[0] < 0 ? (rgbColor[0] + 0xff) : rgbColor[0]) + "," + (rgbColor[1] < 0 ? (rgbColor[1] + 0xff) : rgbColor[1]) + "," + (rgbColor[2] < 0 ? (rgbColor[2] + 0xff) : rgbColor[2]) + ")");
                            }
                            myCell.setContent(readCellContent(cell));
                            data.get(i)
                                    .add(myCell);
                        } else {
                            data.get(i)
                                    .add(new MyCell(""));
                        }
                    }
                }
            }
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return data;
    }


}
