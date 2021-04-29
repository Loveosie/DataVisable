package itheima;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @program: user_management
 * @description:  excel 数据格式设置: 1.边框线, 2.合并单元格, 3.行高列宽, 4. 对其方式, 5.字体
 * @author: lance
 * @create: 2021-04-10 13:30
 */
public class POIDemo4 {
    public static void main(String[] args) throws IOException {
        // 需求: 全边框, 字体, 合并单元格  水平垂直居中, 行号列宽

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("带样式的表格输出");
        // 行高, 列宽
        sheet.setColumnWidth(0,15*256);
        sheet.setColumnWidth(1,15*256);
        sheet.setColumnWidth(2,15*256);
        sheet.setColumnWidth(3,15*256);
        sheet.setColumnWidth(4,15*256);
        Row titleHeadRow = sheet.createRow(0);
        titleHeadRow.setHeightInPoints(42);

//大标题
        //边框
        CellStyle titleHeadStyle = workbook.createCellStyle();
        titleHeadStyle.setBorderBottom(BorderStyle.THIN);
        titleHeadStyle.setBorderLeft(BorderStyle.THIN);
        titleHeadStyle.setBorderRight(BorderStyle.THIN);
        titleHeadStyle.setBorderTop(BorderStyle.THIN);
        //对齐: 会平居中,垂直居中
        titleHeadStyle.setAlignment(HorizontalAlignment.CENTER);
        titleHeadStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 合并单元格
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0,0,0,4);
        sheet.addMergedRegion(cellRangeAddress);
        //字体
        Font font = workbook.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)18);
        titleHeadStyle.setFont(font);
        for (int i = 0; i < 5; i++) {
            Cell cell = titleHeadRow.createCell(i);
            cell.setCellStyle(titleHeadStyle);
        }
        sheet.getRow(0).getCell(0).setCellValue("用户信息数据");

// 小标题
        //边框
        CellStyle littleHeadStyle = workbook.createCellStyle();
        littleHeadStyle.cloneStyleFrom(titleHeadStyle);
        //字体
        Font littleFont = workbook.createFont();
        littleFont.setFontName("宋体");
        littleFont.setFontHeightInPoints((short)12);
        littleFont.setBold(true);
        littleHeadStyle.setFont(littleFont);
        Row littleHeadRow = sheet.createRow(1);
        littleHeadRow.setHeightInPoints(31.5F);
        String[] littleHead = new String[]{"编号","姓名","手机号","入职日期","现住址"};
        for (int i = 0; i < littleHead.length; i++) {
            Cell cell = littleHeadRow.createCell(i);
            cell.setCellValue(littleHead[i]);
            cell.setCellStyle(littleHeadStyle);
        }

// data区域
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.cloneStyleFrom(littleHeadStyle);
        Font dataFont = workbook.createFont();
        dataFont.setFontName("宋体");
        dataFont.setFontHeightInPoints((short)11);
        dataFont.setBold(false);
        dataStyle.setFont(dataFont);
        dataStyle.setAlignment(HorizontalAlignment.LEFT);
        String[] data = new String[]{"1","大一1","13800000001","2001-3-1","北京市西城区宣武大街1号院"};
        Row dataRow = sheet.createRow(2);
        for (int i = 0; i < data.length; i++) {
            Cell cell = dataRow.createCell(i);
            cell.setCellValue(data[i]);
            cell.setCellStyle(dataStyle);
        }
        workbook.write(new FileOutputStream("d://style.xlsx"));
        workbook.close();
    }


}
