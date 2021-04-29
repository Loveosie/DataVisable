package com.itheima.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ExcelExportEngine {
    public static Workbook writeToExcel(Object object, Workbook workbook, String imagePath) throws IOException {
        Map<String, Object> map = EntityUtils.entityToMap(object);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = null;
        Cell cell = null;
        for (int i = 0; i < 100; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                break;
            } else {
                for (int j = 0; j < 100; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        writeToCell(cell, map);
                    }
                }
            }
        }

        if (imagePath != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
            String imageType = imagePath.substring(imagePath.lastIndexOf(".") + 1).toUpperCase();
            ImageIO.write(bufferedImage, imageType, outputStream);

            //图片位置
            Sheet pictureIndexSheet = workbook.getSheetAt(1);
            int col1 = ((Double)pictureIndexSheet.getRow(0).getCell(0).getNumericCellValue()).intValue();
            int row1 = ((Double)pictureIndexSheet.getRow(0).getCell(1).getNumericCellValue()).intValue();
            int col2 = ((Double)pictureIndexSheet.getRow(0).getCell(2).getNumericCellValue()).intValue();
            int row2 = ((Double)pictureIndexSheet.getRow(0).getCell(3).getNumericCellValue()).intValue();
            ClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, col1, row1, col2, row2);
            workbook.removeSheetAt(1);
            //图片类型
            int format = 0;
            switch (imageType) {
                case "JPG":
                    format = XSSFWorkbook.PICTURE_TYPE_JPEG;
                case "JPEG":
                    format = XSSFWorkbook.PICTURE_TYPE_JPEG;
                case "PNG":
                    format = XSSFWorkbook.PICTURE_TYPE_PNG;
            }
            //使用patriarch 控制图片的写入
            Drawing patriarch = sheet.createDrawingPatriarch();
            //使用anchor 指定图片位置 dx,dy 偏移量(1cm=360000) col row 左上角和右下角位置
            patriarch.createPicture(anchor, workbook.addPicture(outputStream.toByteArray(), format));
        }
        return workbook;
    }

    /**
     * 单元格中的值和map中的key一直, 如果一直向单元格写入毛的值
     *
     * @param cell
     * @param map
     */
    private static void writeToCell(Cell cell, Map<String, Object> map) {
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case FORMULA:
                break;
            default: {
                String cellValue = cell.getStringCellValue();
                if (StringUtils.isNoneBlank(cellValue)) {
                    for (String key : map.keySet()) {
                        if (key.equals(cellValue)) {
                            cell.setCellValue(map.get(key).toString());
                        }
                    }
                }
            }
        }

    }
}