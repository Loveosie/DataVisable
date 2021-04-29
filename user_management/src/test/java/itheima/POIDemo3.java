package itheima;


import com.itheima.pojo.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: user_management
 * @description: 使用POI读取excel数据并转为User对象
 * @author: lance
 * @create: 2021-04-10 13:30
 */
public class POIDemo3 {

    public static void main(String[] args) throws IOException, ParseException {
       readExcel2User();
    }

    /**
     * 读取excel 并转为User对象 ,写入数据库
     * @throws IOException
     * @throws ParseException
     */
    private static void readExcel2User() throws IOException, ParseException {
        Workbook workbook = new XSSFWorkbook(new FileInputStream("D://用户导入测试数据.xlsx"));
        Sheet sheet = workbook.getSheetAt(0);

        Row row = null;
        User user = new User();
        List<User> users = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //用户名	手机号	省份	城市	工资	入职日期	出生日期	现住地址
        int lastRowNIndex = sheet.getLastRowNum();
        for (int i = 1; i < lastRowNIndex; i++) {
             row = sheet.getRow(i);
            String userName = row.getCell(0).getStringCellValue();
            String phone = null;
            try {
                phone = row.getCell(1).getStringCellValue();
            } catch (Exception exception) {
                phone = row.getCell(1).getStringCellValue() + "";
            }
            String province = row.getCell(2).getStringCellValue();
            String city = row.getCell(3).getStringCellValue();
            Integer salary = ((Double)row.getCell(4).getNumericCellValue()).intValue();

            String hireDateStr = row.getCell(5).getStringCellValue(); //入职日期
            Date hireDate = df.parse(hireDateStr);
            String birthdayStr = row.getCell(6).getStringCellValue(); //出生日期
            Date birthday = df.parse(birthdayStr);
            String address = row.getCell(7).getStringCellValue();
            System.out.println("userName:" + userName + ",phone" + phone +",province"+
                    province+",city"+city+ ",salary" + salary +",hireDate"+hireDate+ ",birthday"+birthday+",address"+address);

            user.setUserName(userName);
            user.setPhone(phone);
            user.setProvince(province);
            user.setCity(city);
            user.setSalary(salary);
            user.setHireDate(hireDate);
            user.setBirthday(birthday);
            user.setAddress(address);
            users.add(user);
        }



    }


}
