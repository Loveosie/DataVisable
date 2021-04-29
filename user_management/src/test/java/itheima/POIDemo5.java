package itheima;


import com.itheima.utils.impor.ExcelParse;

/**
 * @program: user_management
 * @description:  百万数据导入测试
 * @author: lance
 * @create: 2021-04-10 13:30
 */
public class POIDemo5 {
    public static void main(String[] args) throws Exception {
        // 1.XSSF 导入堆溢出
        // 2. 使用导入解析器
        ExcelParse parser = new ExcelParse();
        parser.parse("C:\\Users\\lance\\Downloads\\百万数据导出.xlsx");
    }

}
