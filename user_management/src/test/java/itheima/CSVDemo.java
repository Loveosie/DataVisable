package itheima;

import com.itheima.pojo.User;
import com.opencsv.CSVReader;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @program: user_management
 * @description:   CSV 百万数据读取
 * @author: lance
 * @create: 2021-04-17 11:00
 */
public class CSVDemo {
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	@Test
	public void readByCsv() throws IOException, ParseException {
		CSVReader reader = new CSVReader(new FileReader("C:\\Users\\lance\\Downloads\\百万数据导出.csv"));
		String[] titles = reader.readNext();
		User user = null;
		while (true){
			user = new User();
			String[] content = reader.readNext();
			if (content == null){
				break;
			}
			user.setId(Long.parseLong(content[0]));
			user.setUserName(content[1]);
			// "编号","姓名","手机号","入职日期","现住址"
			user.setPhone(content[2]);
			user.setHireDate(dateFormat.parse(content[3]));
			user.setAddress(content[4]);
			// 插入数据库
			System.out.println(user);
		}

	}
}
