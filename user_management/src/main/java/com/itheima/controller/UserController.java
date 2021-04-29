package com.itheima.controller;

import com.itheima.pojo.User;
import com.itheima.service.UserService;
import jxl.write.WriteException;
import net.sf.jasperreports.engine.JRException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/{id}")
    public User findById(@PathVariable("id")Long id){
        return  userService.findById(id);
    }
    @GetMapping("/findPage")
    public List<User> findPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer pageSize) {
        return userService.findPage(page, pageSize);
    }

    @GetMapping(value = "/downLoadXlsByJxl", name = "使用Jxl导出excel")
    public void downLoadXlsByJxl(HttpServletResponse response) {
        try {
            userService.downLoadXlsByJxl(response);
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }
    }


    @PostMapping(value = "/uploadExcel", name = "POI上传用户数据")
    public void uploadExcel(MultipartFile file) throws Exception {
        // userService.uploadFile(file);
        userService.uploadFileByEasyPOI(file);
    }

    @GetMapping(value = "/downLoadXlsxByPoi", name = "使用POI导出用户信息")
    public void downLoadXlsxByPoi(HttpServletResponse response) throws Exception {
        // userService.downLoadXlsxByPoi(response);
        // userService.downLoadXlsxByPoiWithCellStyle(response);
        userService.downLoadXlsxByPoiWithTemplate(response);

    }

    @GetMapping(value = "/download", name = "使用POI导出用户详细信息")
    public void downloadUserInfoByTemplate(Long id,HttpServletResponse response) throws IOException, InvalidFormatException, JRException {
        // userService.downloadUserInfoByTemplate(id,response);
        // userService.downloadUserInfoByTemplate2(id,response);
        // userService.downloadUserInfoByEasyPOI(id,response);
        userService.downloadUserInfoPDF(id,response);

    }


    @GetMapping(value = "/downLoadMillion", name = "百万数据导出")
    public void downLoadMillion(HttpServletResponse response) throws IOException, InvalidFormatException {
        userService.downLoadMillion(response);
    }

    @GetMapping(value = "/downLoadCSV", name = "使用csv进行百万数据导出")
    public void downLoadCSV(HttpServletResponse response) throws IOException, InvalidFormatException {
        // userService.downLoadCSV(response);
        userService.downLoadCSVWithEasyPOI(response);
    }

    @RequestMapping(value = "/downloadContract",name = "下载用户合同信息")
    public void downloadContract(Long id,HttpServletResponse response) throws Exception {
        // userService.downloadContract(id,response);
        userService.downloadContractByEasyPOI(id,response);
    }

    @RequestMapping(value = "/downLoadWithEasyPOI",name = "使用easyPOI导出用户信息")
    public void downLoadWithEasyPOI(HttpServletResponse response) throws IOException {
        userService.downLoadWithEasyPOI(response);
    }

    @RequestMapping(value = "/downLoadPDF",name = "使用jasper导出pdf文件")
    public void downLoadPDF(HttpServletResponse response) throws Exception {
        // jasper 从数据库直接获取数据生成pdf
        // userService.downLoadPDF(response);
        //jasper 后台处理后的数据放入模板(日期)
        userService.downLoadPDF02(response);
    }

    /**
     * 在浏览器中显示柱状图; 模拟数据
     */
    @RequestMapping(value = "/jFreeChart",name = "显示柱状图")
    public void jFreeChart(HttpServletResponse response) throws IOException {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        dataSet.addValue(200,"销售部","2011年");
        dataSet.addValue(210,"销售部","2012年");
        dataSet.addValue(220,"销售部","2013年");
        dataSet.addValue(230,"销售部","2014年");

        dataSet.addValue(300,"技术部","2011年");
        dataSet.addValue(310,"技术部","2012年");
        dataSet.addValue(320,"技术部","2013年");
        dataSet.addValue(330,"技术部","2014年");

        dataSet.addValue(410,"广告部","2011年");
        dataSet.addValue(420,"广告部","2012年");
        dataSet.addValue(430,"广告部","2013年");
        dataSet.addValue(440,"广告部","2014年");

        StandardChartTheme theme = new StandardChartTheme("CN");
        // 大标题  图例 内容的字体设置
        theme.setExtraLargeFont(new Font("华文宋体",Font.BOLD,25));
        theme.setRegularFont(new Font("华文宋体",Font.BOLD,20));
        theme.setLargeFont(new Font("华文宋体",Font.BOLD,15));
        ChartFactory.setChartTheme(theme);
        JFreeChart lineChart = ChartFactory.createBarChart("部门招聘人数图","部门类别","人数",dataSet);
        ChartUtils.writeChartAsJPEG(response.getOutputStream(),0.1F,lineChart,1000,500);
    }

}

