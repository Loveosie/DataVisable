package com.itheima.utils.impor;

import com.itheima.pojo.User;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

/**
 * @program: user_management
 * @description: 百万数据导入处理器
 * @author: lance
 * @create: 2021-04-14 22:57
 */
public class SheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

    private User user = null;
	@Override
	public void startRow(int rowIndex) {//每一行开始
		if (rowIndex == 0){
			user = null;
		}
		if (rowIndex>0){
			user = new User();
		}
	}

	/**
	 * 数据处理
	 * @param cellName
	 * @param cellValue
	 * @param comment
	 */
	@Override
	public void cell(String cellName, String cellValue, XSSFComment comment) {
		if (user != null){
			String letter = cellName.substring(0, 1);
			switch (letter){
				case "A":user.setId(Long.parseLong(cellValue)); break;
				case "B":user.setUserName(cellValue); break;
			}
		}

	}

	// 实际操作时: 需要写入数据库(或者进行数据处理)
	@Override
	public void endRow(int rowIndex) {
		if (rowIndex != 0){
			System.out.println(user);
		}

	}
}
