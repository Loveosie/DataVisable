package com.itheima.service;

import cn.afterturn.easypoi.csv.CsvExportUtil;
import cn.afterturn.easypoi.csv.entity.CsvExportParams;
import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ResourceMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.Resource;
import com.itheima.pojo.User;
import com.itheima.utils.EntityUtils;
import com.itheima.utils.ExcelExportEngine;
import com.opencsv.CSVWriter;
import com.zaxxer.hikari.HikariDataSource;
import jxl.write.WriteException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResourceMapper resourceMapper;


    public List<User> findAll() {
        return userMapper.selectAll();
    }

    /**
     * ??????id,???????????????????????????
     * @param id
     * @return
     */
    public User findById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        Resource resource = new Resource();
        resource.setUserId(id);
        List<Resource> resources = resourceMapper.select(resource);
        user.setResourceList(resources);
        return user;
    }

    public List<User> findPage(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);  //????????????
        Page<User> userPage = (Page<User>) userMapper.selectAll(); //????????????
        return userPage.getResult();
    }

    public void downLoadXlsByJxl(HttpServletResponse response) throws IOException, WriteException {

        // String[] title = {"??????", "??????", "?????????", "????????????", "?????????"};
        // WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
        // WritableSheet sheet = workbook.createSheet("jxl??????", 1);
        //
        // //????????????: ??????
        // sheet.setColumnView(0,5);
        // sheet.setColumnView(1,8);
        // sheet.setColumnView(2,15);
        // sheet.setColumnView(3,15);
        // sheet.setColumnView(4,30);
        //
        // // ??????
        // Label label = null;
        // for (int i = 0; i < title.length; i++) {
        //     label = new Label(i,0,title[i]);
        //     sheet.addCell(label);
        // }
        //
        // //????????????
        // List<User> users = userMapper.selectAll();
        // int  rowIndex = 1;
        // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // for (User user : users) {
        //     label = new Label(0, rowIndex, String.valueOf(user.getId()));
        //     sheet.addCell(label);
        //     label = new Label(1, rowIndex, user.getUserName() );
        //     sheet.addCell(label);
        //
        //     label = new Label(2, rowIndex, user.getPhone());
        //     sheet.addCell(label);
        //
        //     label = new Label(3,rowIndex , format.format(user.getHireDate())) ;
        //     sheet.addCell(label);
        //
        //     label = new Label(4,rowIndex , user.getAddress());
        //     sheet.addCell(label);
        //
        //     rowIndex++;
        // }
        // //??????: ?????????, ?????????: ?????????????????????(in-line attachment), ??????????????????mime??????
        // String fileName = "JXL??????.xls";
        // response.setHeader("Content-Disposition","attachment;filename=" + new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        // response.setContentType("application/vnd.vs-excel");
        // workbook.write();
        // workbook.close();
        // response.getOutputStream().close();
    }

    /**
     * POI????????????, ????????????
     * @param file
     * @throws IOException
     * @throws ParseException
     */
    public void uploadFile(MultipartFile file) throws IOException, ParseException {
        InputStream is = file.getInputStream();
        org.apache.poi.ss.usermodel.Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        Row row = null;
        User user = new User();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //?????????	?????????	??????	??????	??????	????????????	????????????	????????????
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
            Integer salary = ((Double) row.getCell(4).getNumericCellValue()).intValue();

            String hireDateStr = row.getCell(5).getStringCellValue(); //????????????
            Date hireDate = df.parse(hireDateStr);
            String birthdayStr = row.getCell(6).getStringCellValue(); //????????????
            Date birthday = df.parse(birthdayStr);
            String address = row.getCell(7).getStringCellValue();
            //???????????????
            user.setUserName(userName);
            user.setPhone(phone);
            user.setProvince(province);
            user.setCity(city);
            user.setSalary(salary);
            user.setHireDate(hireDate);
            user.setBirthday(birthday);
            user.setAddress(address);
            userMapper.insertSelective(user);
        }
    }

    /**
     * ??????POI????????????????????????
     * @param response
     */
    public void downLoadXlsxByPoi(HttpServletResponse response) throws IOException {

        List<User> users = userMapper.selectAll();

        org.apache.poi.ss.usermodel.Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("????????????");
        //????????????
        sheet.autoSizeColumn(10,true);

        String[] title = {"??????", "??????", "?????????", "????????????", "?????????"};
        Row titleRow = sheet.createRow(0);
        //??????????????????
        for (int i = 0; i < title.length; i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(title[i]);
        }
        //??????????????????
        Row row = null;
        Cell cell = null;
        int rowIndex = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (User user : users) {
            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellValue(user.getId());

            cell = row.createCell(1);
            cell.setCellValue(user.getUserName());

            cell = row.createCell(2);
            cell.setCellValue(user.getPhone());

            cell = row.createCell(3);
            cell.setCellValue(sdf.format(user.getHireDate()));

            cell = row.createCell(4);
            cell.setCellValue(user.getAddress());
            rowIndex ++;
        }

        String fileName = "????????????.xlsx";
        //????????????: ?????????; ?????????????????????(in-line attachment); ??????????????????mime??????  "Content-Disposition","attachment;filename="
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        workbook.write(response.getOutputStream());
        workbook.close();
        response.getOutputStream().close();
    }

    /**
     * ???????????????????????????
     * @param response
     * @throws IOException
     */
    public void downLoadXlsxByPoiWithCellStyle(HttpServletResponse response) throws IOException {
        org.apache.poi.ss.usermodel.Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("????????????????????????");
        // ??????
        sheet.setColumnWidth(0,15*256);
        sheet.setColumnWidth(1,15*256);
        sheet.setColumnWidth(2,15*256);
        sheet.setColumnWidth(3,15*256);
        sheet.setColumnWidth(4,30*256);
//?????????
        // ??????
        Row titleHeadRow = sheet.createRow(0);
        titleHeadRow.setHeightInPoints(42);
        //??????
        CellStyle titleHeadStyle = workbook.createCellStyle();
        titleHeadStyle.setBorderBottom(BorderStyle.THIN);
        titleHeadStyle.setBorderLeft(BorderStyle.THIN);
        titleHeadStyle.setBorderRight(BorderStyle.THIN);
        titleHeadStyle.setBorderTop(BorderStyle.THIN);
        //??????: ????????????,????????????
        titleHeadStyle.setAlignment(HorizontalAlignment.CENTER);
        titleHeadStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // ???????????????
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0,0,0,4);
        sheet.addMergedRegion(cellRangeAddress);
        //??????
        Font font = workbook.createFont();
        font.setFontName("??????");
        font.setFontHeightInPoints((short)18);
        titleHeadStyle.setFont(font);
        for (int i = 0; i < 5; i++) {
            Cell cell = titleHeadRow.createCell(i);
            cell.setCellStyle(titleHeadStyle);
        }
        sheet.getRow(0).getCell(0).setCellValue("??????????????????");

// ?????????
        //??????
        CellStyle littleHeadStyle = workbook.createCellStyle();
        littleHeadStyle.cloneStyleFrom(titleHeadStyle);
        //??????
        Font littleFont = workbook.createFont();
        littleFont.setFontName("??????");
        littleFont.setFontHeightInPoints((short)12);
        littleFont.setBold(true);
        littleHeadStyle.setFont(littleFont);
        Row littleHeadRow = sheet.createRow(1);
        littleHeadRow.setHeightInPoints(31.5F);
        String[] littleHead = new String[]{"??????","??????","?????????","????????????","?????????"};
        for (int i = 0; i < littleHead.length; i++) {
            Cell cell = littleHeadRow.createCell(i);
            cell.setCellValue(littleHead[i]);
            cell.setCellStyle(littleHeadStyle);
        }

// data??????
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.cloneStyleFrom(littleHeadStyle);
        Font dataFont = workbook.createFont();
        dataFont.setFontName("??????");
        dataFont.setFontHeightInPoints((short)11);
        dataFont.setBold(false);
        dataStyle.setFont(dataFont);
        dataStyle.setAlignment(HorizontalAlignment.LEFT);
        //UserData
        List<User> users = userMapper.selectAll();
        //??????????????????
        Row row = null;
        Cell cell = null;
        int rowIndex = 2;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (User user : users) {
            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(user.getId());

            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(user.getUserName());

            cell = row.createCell(2);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(user.getPhone());

            cell = row.createCell(3);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(sdf.format(user.getHireDate()));

            cell = row.createCell(4);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(user.getAddress());
            rowIndex ++;
        }

        String fileName = "????????????.xlsx";
        //????????????: ?????????; ?????????????????????(in-line attachment); ??????????????????mime??????  "Content-Disposition","attachment;filename="
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        workbook.write(response.getOutputStream());
        workbook.close();
        response.getOutputStream().close();

    }

    /**
     * ??????????????????????????????
     * @param response
     */
    public void downLoadXlsxByPoiWithTemplate(HttpServletResponse response) throws Exception {
        // 1. ????????????
        File rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        File templateFile = new File(rootFile, "/excel_template/userList.xlsx");
        Workbook workbook = new XSSFWorkbook(templateFile);
        //2 ????????????????????????
        List<User> users = userMapper.selectAll();
        Sheet sheet = workbook.getSheetAt(0);
        int  rowIndex = 2;
        Row row = null;
        Cell cell = null;
        // ???????????????sheet???????????????????????????
        CellStyle dataStyle = workbook.getSheetAt(1).getRow(0).getCell(0).getCellStyle();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (User user : users) {
            row = sheet.createRow(rowIndex);
            row.setHeightInPoints(35F);
            cell = row.createCell(0);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(user.getId());

            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(user.getUserName());

            cell = row.createCell(2);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(user.getPhone());

            cell = row.createCell(3);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(sdf.format(user.getHireDate()));

            cell = row.createCell(4);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(user.getAddress());
            rowIndex ++;
        }
        workbook.removeSheetAt(1);
        String fileName = "????????????.xlsx";
        //????????????: ?????????; ?????????????????????(in-line attachment); ??????????????????mime??????  "Content-Disposition","attachment;filename="
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        workbook.write(response.getOutputStream());
        workbook.close();
        response.getOutputStream().close();
    }

    /**
     * ??????excel??????,??????????????????
     * @param id
     * @param response
     * @throws IOException
     * @throws InvalidFormatException
     */
    public void downloadUserInfoByTemplate(Long id, HttpServletResponse response) throws IOException, InvalidFormatException {
        // 1. ????????????
        File rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        File templateFile = new File(rootFile, "/excel_template/userInfo.xlsx");
        Workbook workbook = new XSSFWorkbook(templateFile);

        //2. ??????????????????
        User user = userMapper.selectByPrimaryKey(id);

        //3. ?????????????????????
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Sheet sheet = workbook.getSheetAt(0);
        // ?????????  ?????????   ?????? ?????? ????????????  ??????  ?????????   ??????   ??????   ??????
        sheet.getRow(1).getCell(1).setCellValue(user.getUserName());
        sheet.getRow(2).getCell(1).setCellValue(user.getPhone());
        sheet.getRow(3).getCell(1).setCellValue(sdf.format(user.getBirthday()));
        sheet.getRow(4).getCell(1).setCellValue(user.getSalary());
        sheet.getRow(5).getCell(1).setCellValue(sdf.format(user.getHireDate()));
        sheet.getRow(6).getCell(1).setCellValue(user.getProvince());
        sheet.getRow(7).getCell(1).setCellValue(user.getAddress());
        // ???????????????????????? =CONCATENATE(DATEDIF(B6,TODAY(),"Y"),"???",DATEDIF(B6,TODAY(),"YM"),"??????")
        sheet.getRow(5).getCell(3).setCellFormula("CONCATENATE(DATEDIF(B6,TODAY(),\"Y\"),\"???\",DATEDIF(B6,TODAY(),\"YM\"),\"??????\")");;
        sheet.getRow(6).getCell(3).setCellValue(user.getCity());
        //????????????
        // ??????????????????????????????
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = ImageIO.read(new File(rootFile, user.getPhoto()));
        String imageType = user.getPhoto().substring(user.getPhoto().lastIndexOf(".") + 1).toUpperCase();
        ImageIO.write(bufferedImage,imageType,outputStream);
        int format = 0;
        switch (imageType){
            case "JPG": format = XSSFWorkbook.PICTURE_TYPE_JPEG;
            case "JPEG": format = XSSFWorkbook.PICTURE_TYPE_JPEG;
            case "PNG": format = XSSFWorkbook.PICTURE_TYPE_PNG;
        }
        //??????patriarch ?????????????????????
        Drawing patriarch = sheet.createDrawingPatriarch();
        //??????anchor ?????????????????? dx,dy ?????????(1cm=360000) col row ???????????????????????????
        ClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, 2, 1, 4, 5);
        patriarch.createPicture(anchor,workbook.addPicture(outputStream.toByteArray(),format));

        String fileName = "??????" +user.getUserName()+ "??????????????????.xlsx";
        //????????????: ?????????; ?????????????????????(in-line attachment); ??????????????????mime??????  "Content-Disposition","attachment;filename="
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        workbook.write(response.getOutputStream());
        workbook.close();
        response.getOutputStream().close();
    }

    /**
     * ?????????????????????
     * @param id
     * @param response
     * @throws IOException
     * @throws InvalidFormatException
     */
    public void downloadUserInfoByTemplate2(Long id, HttpServletResponse response) throws IOException, InvalidFormatException {
        // 1. ????????????
        File rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        File templateFile = new File(rootFile, "/excel_template/userInfo2.xlsx");
        Workbook workbook = new XSSFWorkbook(templateFile);

        User  user = userMapper.selectByPrimaryKey(id);
        workbook = ExcelExportEngine.writeToExcel(user, workbook,rootFile.getPath()+user.getPhoto());

        String fileName = "??????" +user.getUserName()+ "??????????????????.xlsx";
        //????????????: ?????????; ?????????????????????(in-line attachment); ??????????????????mime??????  "Content-Disposition","attachment;filename="
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        workbook.write(response.getOutputStream());
        workbook.close();
        response.getOutputStream().close();
    }

    /**
     * ??????????????????: 1. ?????????excel, 2.sax????????????; ??????: 1. ????????????,??????????????????
     * @param response
     */
	public void downLoadMillion(HttpServletResponse response) throws IOException {
	    Workbook workbook = new SXSSFWorkbook();
	    int page = 1;
	    int sheetSize = 0; //???????????????????????????
        int rowIndex = 1;
        Sheet sheet = null;
	    while(true){
            List<User> userList = this.findPage(page, 100000);
            if (CollectionUtils.isEmpty(userList)){
                break;
            }

            //????????????????????????????????????  (????????????????????????)
            if (sheetSize % 1000000 == 0){
                sheet = workbook.createSheet("???" + (( sheetSize / 1000000) + 1) + "????????????");
                rowIndex = 1;//?????????????????????rowIndex
                String[] titles = new String[]{"??????","??????","?????????","????????????","?????????"};
                Row row = sheet.createRow(0);
                for (int i = 0; i < titles.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(titles[i]);
                }
            }
            // ???????????????????????????
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Row row = null;
            for (User user : userList) {
                row = sheet.createRow(rowIndex);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getUserName());
                row.createCell(2).setCellValue(user.getPhone());
                row.createCell(3).setCellValue(format.format(user.getHireDate()));
                row.createCell(4).setCellValue(user.getAddress());
                rowIndex++;
                sheetSize++;
            }
            page++;
        }
        String fileName = "??????????????????.xlsx";
        //????????????: ?????????; ?????????????????????(in-line attachment); ??????????????????mime??????  "Content-Disposition","attachment;filename="
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        workbook.write(response.getOutputStream());
        workbook.close();
	}

    /**
     * ??????CSV??????????????????
     * @param response
     */
    public void downLoadCSV(HttpServletResponse response) throws IOException {
        String fileName = "??????????????????.csv";
        //????????????: ?????????; ?????????????????????(in-line attachment); ??????????????????mime??????  "Content-Disposition","attachment;filename="
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("text/csv");

        // CSVWriter writer = new CSVWriter(response.getWriter());
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(response.getOutputStream(),StandardCharsets.UTF_8));
        // ??????
        String[] titles = new String[]{"??????","??????","?????????","????????????","?????????"};
        writer.writeNext(titles);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int page = 1;
        while (true){
            List<User> userList = this.findPage(page, 200000);
            if (CollectionUtils.isEmpty(userList)) {
                break;
            }
            for (User user : userList) {
                writer.writeNext(new String[]{user.getId().toString(),user.getUserName(),user.getPhone(),
                        dateFormat.format(user.getHireDate()), user.getAddress()});
            }
            page++;
            writer.flush();
        }
        writer.close();
    }

    /**
     * ????????????????????????
     * @param id
     * @param response
     * @throws IOException
     */
    public void downloadContract  (Long id,HttpServletResponse response) throws IOException {
        // 1.????????????
        File rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        File templateFile = new File(rootFile, "/word_template/contract_template.docx");
        XWPFDocument document = new XWPFDocument(new FileInputStream(templateFile));
        // 2.??????????????????-->map
        User user = this.findById(id);
        Map<String, Object> params = EntityUtils.entityToMap(user);
        //3. ????????????: ??????
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                for (String key : params.keySet()) {
                    if (text.contains(key)) {
                        run.setText(text.replaceAll(key,(String) params.get(key)),0);
                    }
                }
            }
        }
        //3. ????????????: ??????

        List<Resource> resourceList = user.getResourceList();
        XWPFTable table = document.getTables().get(0);
        XWPFTableRow row = table.getRow(0);
        int rowIndex = 1;
        for (Resource resource : resourceList) {
            //??????  ??????  ??????????????????  ??????
            // table.addRow(row);  ?????????row????????????????????????
            copyRow(table,row,rowIndex);
            XWPFTableRow tableRow = table.getRow(rowIndex);
            tableRow.getCell(0).setText(resource.getName());
            tableRow.getCell(1).setText(resource.getPrice().toString());
            tableRow.getCell(2).setText(resource.getNeedReturn() ? "??????" : "?????????");
            // ?????????
            File imageFile = new File(rootFile, "/static" + resource.getPhoto());
            saveImage(tableRow.getCell(3),imageFile);
            rowIndex++;
        }

        //4. ??????word
        String fileName = "??????(" +user.getUserName()+ ")??????????????????.docx";
        //????????????: ?????????; ?????????????????????(in-line attachment); ??????????????????mime??????  "Content-Disposition","attachment;filename="
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        document.write(response.getOutputStream());
        document.close();
    }
    public void downloadContractByEasyPOI(Long id, HttpServletResponse response) throws Exception {
        //??????
        File rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        File templateFile = new File(rootFile, "/word_template/contract_template2.docx");
        //??????
        User user = this.findById(id);
        // Map<String, Object> map = EntityUtils.entityToMap(user);
        //??????????????????
        Map<String,Object> params = new HashMap<>();
        params.put("userName",user.getUserName());
        params.put("hireDate",dateFormat.format(user.getHireDate()));
        params.put("address",user.getAddress());
        //????????????
        List<Map<String, Object>> resourceList = new ArrayList<>();
        Map<String, Object> map =null;
        for (Resource resource : user.getResourceList()) {
            map = new HashMap<>();
            map.put("name",resource.getName());
            map.put("price",resource.getPrice());
            map.put("needReturn",resource.getNeedReturn());
            map.put("photo",resource.getPhoto());
            resourceList.add(map);
        }
        params.put("maplist",resourceList);
        //easyPOI??????
        XWPFDocument document = WordExportUtil.exportWord07(templateFile.getPath(), params);
        //4. ??????word
        String fileName = "??????(" +user.getUserName()+ ")??????????????????.docx";
        //????????????: ?????????; ?????????????????????(in-line attachment); ??????????????????mime??????  "Content-Disposition","attachment;filename="
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        document.write(response.getOutputStream());
        document.close();
    }

    /**
     * ??????easyPOI??????????????????
     * @param response
     * @throws IOException
     */
    public void downLoadWithEasyPOI(HttpServletResponse response) throws IOException {
        ExportParams exportParams = new ExportParams("????????????", "???????????????", ExcelType.XSSF);
        List<User> users = userMapper.selectAll();
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, users);

        String fileName = "easyPOI????????????.xlsx";
        //????????????: ?????????; ?????????????????????(in-line attachment); ??????????????????mime??????  "Content-Disposition","attachment;filename="
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    /**
     * ????????????????????????
     * @param cell
     * @param imageFile
     */
    private void saveImage(XWPFTableCell cell, File imageFile) {
        XWPFRun run = cell.getParagraphs().get(0).createRun();
        try(FileInputStream inputStream = new FileInputStream(imageFile)) {
            run.addPicture(inputStream,XWPFDocument.PICTURE_TYPE_JPEG,imageFile.getName(), Units.toEMU(100),Units.toEMU(50));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ????????????????????????
     * @param table
     * @param sourceRow
     * @param rowIndex
     */
    private void copyRow(XWPFTable table, XWPFTableRow sourceRow, int rowIndex) {
        XWPFTableRow targetRow = table.insertNewTableRow(rowIndex);
        // ??????sourceRow: ???????????? ?????????de??????
        // ?????????????????????
        targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
        List<XWPFTableCell> cells = sourceRow.getTableCells();
        if (CollectionUtils.isEmpty(cells)){
            return;
        }
        XWPFTableCell targetCell = null;
        for (XWPFTableCell sourceCell : cells) {
            // ????????????????????????
            targetCell = targetRow.addNewTableCell();
            targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
            targetCell.getParagraphs().get(0).getCTP().setPPr(sourceCell.getParagraphs().get(0).getCTP().getPPr());

        }

    }

    /**
     * ??????easyPOI????????????
     * @param file
     */
    public void uploadFileByEasyPOI(MultipartFile file) throws Exception {
        ImportParams importParams = new ImportParams();
        importParams.setNeedSave(false);
        importParams.setTitleRows(1);
        importParams.setHeadRows(1);
        List<User> users = ExcelImportUtil.importExcel(file.getInputStream(), User.class, importParams);
        for (User user : users) {
            user.setId(null);
            userMapper.insert(user);
        }
    }


    public void downloadUserInfoByEasyPOI(Long id, HttpServletResponse response) throws IOException, InvalidFormatException {
        File rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        File templateFile = new File(rootFile, "/excel_template/userInfo3.xlsx");
        TemplateExportParams templateExportParams = new TemplateExportParams(templateFile.getPath(),true);
        User user = userMapper.selectByPrimaryKey(id);

        Map<String, Object> map = EntityUtils.entityToMap(user);
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setUrl(user.getPhoto());
        imageEntity.setColspan(2);
        imageEntity.setRowspan(4);
        map.put("photo",imageEntity);
        Workbook workbook = ExcelExportUtil.exportExcel(templateExportParams, map);

        String fileName = "easyPOI????????????????????????.xlsx";
        //????????????: ?????????; ?????????????????????(in-line attachment); ??????????????????mime??????  "Content-Disposition","attachment;filename="
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        workbook.write(response.getOutputStream());
        workbook.close();

    }

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * ??????easyPOI ????????????
     * @param response
     */
    public void downLoadCSVWithEasyPOI(HttpServletResponse response) throws IOException {
        String fileName = "easyPOI????????????.csv";
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("text/csv");
        OutputStream outputStream = response.getOutputStream();

        CsvExportParams exportParams = new CsvExportParams();
        exportParams.setExclusions(new String[]{"??????"});
        List<User> users = userMapper.selectAll();
        CsvExportUtil.exportCsv(exportParams,User.class,users,outputStream);
        outputStream.close();
    }

    @Autowired
    private HikariDataSource dataSource;
    /**
     *  // jasper ????????????????????????????????????pdf
     * @param response
     */
    public void downLoadPDF(HttpServletResponse response) throws Exception {
        File rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        File templateFile = new File(rootFile, "/pdf_template/userList_db.jasper");
        HashMap<String, Object> params = new HashMap<>();
        String fileName = "jasper??????????????????.pdf";
        // JasperPrint jasperPrint = JasperFillManager.fillReport(new FileInputStream(templateFile), params, getCon());
        JasperPrint jasperPrint = JasperFillManager.fillReport(new FileInputStream(templateFile), params, dataSource.getConnection() );
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("application/pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());

    }

    public void downLoadPDF02(HttpServletResponse response) throws Exception{
        File rootFile = new File(ResourceUtils.getURL("classpath:").getPath());

        File templateFile = new File(rootFile, "/pdf_template/userList2.jasper");
        HashMap<String, Object> params = new HashMap<>();
        // File templateFile = new File(rootFile, "/pdf_template/userList.jasper");



        // ???????????????, ????????????????????????????????????
        Example example = new Example(User.class);
        example.setOrderByClause("province desc");
        List<User> users = userMapper.selectByExample(example);
        //???hireDate?????????????????????hireDateStr?????????, jasper???????????????hireDateStr
        // users = users.stream().map(user->{
        users = users.stream().peek(user->{
           user.setHireDateStr(dateFormat.format(user.getHireDate()));
           // return user;
        }).collect(Collectors.toList());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);
        JasperPrint jasperPrint = JasperFillManager.fillReport(new FileInputStream(templateFile), params,dataSource);
        String fileName = "jasper??????????????????.pdf";
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("application/pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
    }

    /**
     * ??????jasper????????????pdf??????
     * @param id
     * @param response
     */
    public void downloadUserInfoPDF(Long id, HttpServletResponse response) throws IOException, JRException {
        File rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        File templateFile = new File(rootFile, "/pdf_template/userInfo.jasper");

        User user = userMapper.selectByPrimaryKey(id);
        Map<String, Object> params = EntityUtils.entityToMap(user);
        params.put("salary",user.getSalary().toString());
        params.put("photo",rootFile+user.getPhoto());
        JasperPrint jasperPrint = JasperFillManager.fillReport(new FileInputStream(templateFile), params,new JREmptyDataSource());
        String fileName = "??????" + user.getUserName()+ "????????????.pdf";
        response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes(),StandardCharsets.ISO_8859_1));
        response.setContentType("application/pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
    }
/*
    private Connection getCon() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost/report_manager_db", "root", "root");
    }*/
}
