package com.itheima.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 员工
 */
@Data
// @Table(name="tb_user2")  百万数据操作时使用
@Table(name="tb_user")
public class User {
    @Id
    //@KeySql(useGeneratedKeys = true)  插入数据时, 生成主键使用
    @Excel(name = "主键",width = 10)
    private Long id;         //主键
    @Excel(name = "员工名",width = 15,isImportField = "true")
    private String userName; //员工名
    @Excel(name = "手机号",width = 15, isImportField = "true")
    private String phone;    //手机号
    @Excel(name = "省份名",width = 15,isImportField = "true")
    private String province; //省份名
    @Excel(name = "城市名",width = 15,isImportField = "true")
    private String city;     //城市名
    @Excel(name = "工资",width = 10, type =10,isImportField = "true" )
    private Integer salary;   // 工资
    @Excel(name = "入职日期",width = 15,format = "yyyy-MM-dd",isImportField = "true")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date hireDate; // 入职日期
    /**
     * Transient: 不需要和表对应
     * JsonIgnore: 转json时忽略
     */
    @Transient
    @JsonIgnore
    private String hireDateStr; // 处理pdf输出时, 格式不正确
    private String deptId;   //部门id
    private Date birthday; //出生日期
    @Excel(name = "照片", type = 2, width = 10,isImportField = "true",savePath = "E:\\code\\project\\user_management\\src\\main\\resources\\static\\user_photos")
    private String photo;    //一寸照片
    private String address;  //现在居住地址
    private List<Resource> resourceList; //办公用品

 /*   @Override
    public String toString() {
        return "User{" +
                       "id=" + id +
                       ", userName='" + userName + '\'' +
                       '}';
    }*/

   /* @Override
    public String toString() {
        return "User{" +
                       "id=" + id +
                       ", userName='" + userName + '\'' +
                       ", phone='" + phone + '\'' +
                       ", hireDate=" + hireDate +
                       ", address='" + address + '\'' +
                       '}';
    }*/
}
