package itheima;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @program: user_management
 * @description: poi操作worddemo
 * @author: lance
 * @create: 2021-04-17 13:13
 */
public class WordDemo {
	public static void main(String[] args) throws IOException {
		XWPFDocument document = new XWPFDocument(new FileInputStream("D:\\test.docx"));

		List<XWPFParagraph> paragraphs = document.getParagraphs();
		// 打印片段
		for (XWPFParagraph paragraph : paragraphs) {
			List<XWPFRun> runs = paragraph.getRuns();
			for (XWPFRun run : runs) {
				System.out.println(run.getText(0));
			}
		}
		XWPFTable table = document.getTables().get(0);
		for (XWPFTableRow row : table.getRows()) {
			for (XWPFTableCell cell : row.getTableCells()) {
				for (XWPFParagraph paragraph : cell.getParagraphs()) {
					System.out.println(paragraph.getText());
				}
			}
		}
	}
}
