package cn.tycoding.util;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.FilesLiterature;
import cn.tycoding.pojo.GaitPicRemark;
import cn.tycoding.pojo.LiteratureRemark;
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
     * @param user_name 
     * @return
     */
    public static List<Object> readExcel(CommonsMultipartFile file, Object o, String user_name) throws Exception {
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
        return listExcel(sheetIterator, o,user_name);
    }

    /**
     * 封装实体类
     *
     * @param sheetIterator 迭代器
     * @param user_name 
     * @return  封装好excel数据的list
     */
    private static List<Object> listExcel(Iterator<Sheet> sheetIterator, Object o, String user_name) {
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
                    if(row.getCell(0)==null) return null;
                    else {
                    	row.getCell(0).setCellType(CellType.STRING);
                    	admin.setA_name(row.getCell(0).getStringCellValue());
                    }
                    if(row.getCell(1)==null) return null;
                    else{
                    	row.getCell(1).setCellType(CellType.STRING);
                        admin.setA_password(row.getCell(1).getStringCellValue());
                    }
                    if(row.getCell(2)==null) return null;
                    else{
                    	row.getCell(2).setCellType(CellType.STRING);
                        admin.setType(row.getCell(2).getStringCellValue());
                    }
                    list.add(admin);
                }
                if (o instanceof Machine){
                    Machine machine = new Machine();
                    if(row.getCell(0)==null){
                    	//设备名称列不能为空
                    	return null;
                    }else {
                    	row.getCell(0).setCellType(CellType.STRING);
                    	machine.setName(row.getCell(0).getStringCellValue());
                    }
                    if(row.getCell(1)==null){
                    	machine.setType("");
                    }else {
                    	row.getCell(1).setCellType(CellType.STRING);
                    	machine.setType(row.getCell(1).getStringCellValue());
                    }
                    if(row.getCell(2)==null){
                    	machine.setCompany("");
                    }else {
                    	row.getCell(2).setCellType(CellType.STRING);
                    	machine.setCompany(row.getCell(2).getStringCellValue());
                    }
                    if(row.getCell(3)==null){
                    	machine.setPlace("");
                    }else{
                    	row.getCell(3).setCellType(CellType.STRING);
                    	machine.setPlace(row.getCell(3).getStringCellValue());
                    } 
                    if(row.getCell(4)==null){
                    	machine.setRemark("");
                    }else{
                    	row.getCell(4).setCellType(CellType.STRING);
                    	machine.setRemark(row.getCell(4).getStringCellValue());
                    } 
                    machine.setUser_name(user_name);
                    list.add(machine);
                }
                if (o instanceof Subjects){
                    Subjects subjects = new Subjects();
                    /*row.getCell(0).setCellType(CellType.STRING);*/
                    //身份证
                    if(row.getCell(0)==null) subjects.setIdentity_card("");
                    else{
                    	row.getCell(0).setCellType(CellType.STRING);
                    	subjects.setIdentity_card(row.getCell(0).getStringCellValue());
                    } 
                    //姓名
                    if(row.getCell(1)==null) subjects.setName("");
                    else{
                    	row.getCell(1).setCellType(CellType.STRING);
                    	subjects.setName(row.getCell(1).getStringCellValue());
                    }
                    if(row.getCell(2)==null) subjects.setAge(null);
                    else{
                    	row.getCell(2).setCellType(CellType.STRING);
                    	//年龄
                    	subjects.setAge(Integer.valueOf(row.getCell(2).getStringCellValue()));
                    }
                    if(row.getCell(3)==null) subjects.setTestdate("");
                    else{
                    	//excel必须为文本格式
                    	row.getCell(3).setCellType(CellType.STRING);
                    	//日期
                    	subjects.setTestdate(row.getCell(3).getStringCellValue());
                    }
                    //体重
                    if(row.getCell(4)==null) return null;
                    else subjects.setWeight((float) row.getCell(4).getNumericCellValue());
                    //身高
                    if(row.getCell(5)==null) return null;
                    else subjects.setHeight((float) row.getCell(5).getNumericCellValue());
                    //备注信息
                    if(row.getCell(6)==null) subjects.setRemark("");
                    else{
                    	row.getCell(6).setCellType(CellType.STRING);
                    	subjects.setRemark(row.getCell(6).getStringCellValue());
                    }
                    subjects.setUser_name(user_name);
                    list.add(subjects);
                }
                if (o instanceof GaitPicRemark){
                    GaitPicRemark gaitPicRemark = new GaitPicRemark();
                    Cell cell = row.getCell(0);
                	cell.setCellType(CellType.STRING);
                    gaitPicRemark.setFileName(cell.getStringCellValue());
                    if(row.getCell(1)!= null){
                    	Cell cell1 = row.getCell(1);
                     	cell1.setCellType(CellType.STRING);
                    	gaitPicRemark.setFileInfo(cell1.getStringCellValue());
                    }else{
                    	gaitPicRemark.setFileInfo("未添加任何记录");
                    }
                    list.add(gaitPicRemark);
                }
                if (o instanceof LiteratureRemark){
                	LiteratureRemark literatureRemark = new LiteratureRemark();
                	Cell cell = row.getCell(0);
                	cell.setCellType(CellType.STRING);
                	literatureRemark.setFiles_name(cell.getStringCellValue());
                	if(row.getCell(1)!= null){
                		Cell cell1 = row.getCell(1);
                    	cell1.setCellType(CellType.STRING);
                		literatureRemark.setTopic(cell1.getStringCellValue());
                	}else{
                		literatureRemark.setTopic("无");
                	}
                	if(row.getCell(2)!= null){
                		Cell cell2 = row.getCell(2);
                    	cell2.setCellType(CellType.STRING);
                		literatureRemark.setWriter(cell2.getStringCellValue());
                	}else{
                		literatureRemark.setWriter("无");
                	}
                	if(row.getCell(3)!= null){
                		Cell cell3 = row.getCell(3);
                    	cell3.setCellType(CellType.STRING);
                		literatureRemark.setCompany(cell3.getStringCellValue());
                	}else{
                		literatureRemark.setCompany("无");
                	}
                	if(row.getCell(4)!= null){
                		Cell cell4 = row.getCell(4);
                    	cell4.setCellType(CellType.STRING);
                		literatureRemark.setTime(cell4.getStringCellValue());
                	}else{
                		literatureRemark.setTime("无");
                	}
                	if(row.getCell(5)!= null){
                		Cell cell5 = row.getCell(5);
                    	cell5.setCellType(CellType.STRING);
                		literatureRemark.setRemark(cell5.getStringCellValue());
                	}else{
                		literatureRemark.setRemark("无");
                	}
                	list.add(literatureRemark);
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
