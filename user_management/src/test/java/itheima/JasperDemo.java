package itheima;

import net.sf.jasperreports.engine.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: user_management
 * @description: jasper测试: 读取文件
 * @author: lance
 * @create: 2021-04-18 13:07
 */
public class JasperDemo {
	public static void main(String[] args) throws FileNotFoundException, JRException {
		FileInputStream inputStream = new FileInputStream("D:\\test.jasper");
		Map<String, Object> params = new HashMap();
		params.put("userNameP","张三");
		params.put("phoneP","1345657894");

		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream,params,new JREmptyDataSource());
		JasperExportManager.exportReportToPdfStream(jasperPrint,new FileOutputStream("d:\\test.pdf"));;
	}
}
