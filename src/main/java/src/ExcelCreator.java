package src;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class ExcelCreator {
    static void createExcelTable(ArrayList<Human> humans, ArrayList<String> namesColumn) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Просто лист");
        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        for (int i = 0; i < namesColumn.size(); i++) {
            row.createCell(i).setCellValue(namesColumn.get(i));
            row.getCell(i).setCellStyle(getSampleStyle(workbook));
        }
        for (Human human : humans) {
            createSheetHeader(sheet, ++rowNum, human);
        }
        for (int i = 0; i < namesColumn.size(); i++) {
            sheet.autoSizeColumn(i);
        }
        File outFile = new File("users.xls");
        try (FileOutputStream out = new FileOutputStream(outFile)) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Excel файл создан. Путь:" + outFile.getAbsolutePath());
    }

    private static void createSheetHeader(HSSFSheet sheet, int rowNum, Human human) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(human.getName());
        row.createCell(1).setCellValue(human.getSurname());
        row.createCell(2).setCellValue(human.getPatronymic());
        row.createCell(3).setCellValue(human.getAge());
        row.createCell(4).setCellValue(human.getSex() == SEX.MALE ? "М" : "Ж");
        row.createCell(5).setCellValue(human.getBirthDay().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        row.createCell(6).setCellValue(human.getInn());
        row.createCell(7).setCellValue(human.getMailIndex());
        row.createCell(8).setCellValue(human.getCountry());
        row.createCell(9).setCellValue(human.getRegion());
        row.createCell(10).setCellValue(human.getTown());
        row.createCell(11).setCellValue(human.getStreet());
        row.createCell(12).setCellValue(human.getNumberHouse());
        row.createCell(13).setCellValue(human.getNumberFlat());
    }

    private static HSSFCellStyle getSampleStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setWrapText(false);
        return style;
    }
}
