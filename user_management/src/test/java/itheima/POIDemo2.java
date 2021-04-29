package itheima;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @program: user_management
 * @description: 使用POI创建高版本excel, 并写入一段话
 * @author: lance
 * @create: 2021-04-10 13:30
 */
public class POIDemo2 {

    public static void main(String[] args) throws IOException {
       opreateHighLevel();
    }

    private static void opreateHighLevel() throws IOException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("POI高版本excel");
        Row rowOne = sheet.createRow(0);
        //通过行创建单元格
        Cell cell = rowOne.createCell(0);
        cell.setCellValue("这是第一行的第一个单元格");
        workbook.write(new FileOutputStream("d://x.xlsx"));
    }


}
