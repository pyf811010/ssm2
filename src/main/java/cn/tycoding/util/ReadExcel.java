package cn.tycoding.util;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.GaitPicRemark;
import cn.tycoding.pojo.Machine;
import cn.tycoding.pojo.Subjects;
import com.sun.org.apache.bcel.internal.generic.FLOAD;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @author： pyf lsy fdd
 * @date： 2020/1/6  20:32:01
 * @description： 读取excel文件内容，存入数据库
 */
public class ReadExcel {
    /**
     * 解析excel，每一行封装为一个对象，存在list集合中
     *
     * @param file
     * @return
     */
    public static List<Object> readExcel(CommonsMultipartFile file, Object o) throws Exception {
        Iterator<Sheet> sheetIterator;
        //获取后缀
        String endFile = getSuffix(file);
        System.out.println("文件后缀为："+endFile);
        //文件类型
        if ("xls".equals(endFile)) {
            sheetIterator = new HSSFWorkbook(file.getInputStream()).iterator();
        } else if ("xlsx".equals(endFile)) {
            sheetIterator = new XSSFWorkbook(file.getInputStream()).iterator();
        } else {
            throw new IOException("不支持的文件类型");
        }
        return listExcel(sheetIterator, o);
    }

    /**
     * 封装实体类
     *
     * @param sheetIterator 迭代器
     * @return  封装好excel数据的list
     */
    private static List<Object> listExcel(Iterator<Sheet> sheetIterator, Object o) {
        ArrayList<Object> list = new ArrayList<>();
        Sheet sheet;
        while (sheetIterator.hasNext() && (sheet = sheetIterator.next()) != null) {
            int lastRowNum = sheet.getLastRowNum();
            //从第一行开始读取
            for (int i = 1; i <= lastRowNum; i++) {
                //获取行
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                if (o instanceof Admin){
                    Admin admin = new Admin();
                    admin.setA_name(row.getCell(0).getStringCellValue());
                    row.getCell(1).setCellType(CellType.STRING);
                    admin.setA_password(row.getCell(1).getStringCellValue());
                    row.getCell(2).setCellType(CellType.STRING);
                    admin.setType(row.getCell(2).getStringCellValue());
                    list.add(admin);
                }
                if (o instanceof Machine){
                    Machine machine = new Machine();
                    machine.setName(row.getCell(0).getStringCellValue());
                    machine.setType(row.getCell(1).getStringCellValue());
                    machine.setRemark(row.getCell(2).getStringCellValue());
                    list.add(machine);
                }
                if (o instanceof Subjects){
                    Subjects subjects = new Subjects();
                    /*row.getCell(0).setCellType(CellType.STRING);*/
                    //身份证
                    subjects.setIdentity_card(row.getCell(0).getStringCellValue());
                    //姓名
                    subjects.setName(row.getCell(1).getStringCellValue());
                    row.getCell(2).setCellType(CellType.STRING);
                    //excel必须为文本格式
                    row.getCell(3).setCellType(CellType.STRING);
                    //年龄
                    subjects.setAge(Integer.valueOf(row.getCell(2).getStringCellValue()));
                    //日期
                    subjects.setTestdate(row.getCell(3).getStringCellValue());
                    //体重
                    subjects.setWeight((float) row.getCell(4).getNumericCellValue());
                    //身高
                    subjects.setHeight((float) row.getCell(5).getNumericCellValue());
                    //备注信息
                    subjects.setRemark(row.getCell(6).getStringCellValue());
                    list.add(subjects);
                }
                if (o instanceof GaitPicRemark){
                    GaitPicRemark gaitPicRemark = new GaitPicRemark();
                    gaitPicRemark.setFileName(row.getCell(0).getStringCellValue());
                    if(row.getCell(1)!= null){
                    	gaitPicRemark.setFileInfo(row.getCell(1).getStringCellValue());
                    }else{
                    	gaitPicRemark.setFileInfo("未添加任何记录");
                    }
                    list.add(gaitPicRemark);
                }
            }
        }
        return list;
    }

    /**
     * 获取文件后缀，文件类型
     * @param file 判断文件
     * @return 文件类型
     */
    public static String getSuffix (CommonsMultipartFile file){
        String fileName = file.getOriginalFilename();
        System.out.println("文件名：" + fileName);
        return (fileName.lastIndexOf(".") == -1 ? "" :
                fileName.substring(fileName.lastIndexOf(".") + 1)).trim();
    }

    /**
     * 判断文件是不是excel
     * @param file 进行判断的文件
     * @return 如果是文件返回true，否则返回false
     */
    public static boolean isExcel(CommonsMultipartFile file){
        String suffix = getSuffix(file);
        return ("xls".equals(suffix) || "xlsx".equals(suffix));
    }
}
