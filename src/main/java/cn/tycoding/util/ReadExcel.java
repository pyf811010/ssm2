package cn.tycoding.util;

import cn.tycoding.pojo.EgContrast;
import cn.tycoding.pojo.Preec;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author：pyf lsy fdd
 * @date： 2020/1/6  20:32:01
 * @description：
 */
public class ReadExcel {

    /*public static ExcelFile importExcel(MultipartFile file) throws IOException {
        ExcelFile excelFile = new ExcelFile();
        String originalFilename = file.getOriginalFilename();
        String msg = "";
        String url = "";
        //判断是否已存在同名文件，默认存在
        boolean flag = true;
        if (file != null && originalFilename != null && originalFilename.length() > 0) {
            //月
            String date = new SimpleDateFormat("yyyy-MM").format(new Date());
            //日
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File dataFile = new File("E:/data/" + date);
            File file1 = new File(dataFile.getAbsolutePath() + "/" + format);
            if (!dataFile.exists()) {
                dataFile.mkdirs();
                flag = false;
            }
            if (!file1.exists()) {
                file1.mkdirs();
                if (flag) {
                    flag = false;
                }
            }
            File newFile = new File(file1, originalFilename);
            if (!flag || !newFile.exists()) {
                file.transferTo(newFile);
            } else {
                msg = "已存在" + file.getOriginalFilename();
            }
            url = file1.getAbsolutePath();
        }
        excelFile.setUrl(url);
        excelFile.setMessage(msg);
        return excelFile;
    }*/

    public static MultipartFile[] sort(MultipartFile[] files) {
        MultipartFile[] newFiles = new MultipartFile[files.length];
        int i = 0;
        //运动学与动力学 耗氧量； 实验条件，实验者，传感器对照表
        for (MultipartFile file : files) {
            String name = file.getOriginalFilename();
            if ("运动学与动力学".equals(name.substring(0, name.lastIndexOf(".")))) {
                newFiles[i++] = file;
                break;
            }
        }
        for (MultipartFile file : files) {
            String name = file.getOriginalFilename();
            if (!"运动学与动力学".equals(name.substring(0, name.lastIndexOf(".")))) {
                newFiles[i++] = file;
            }
        }
        System.out.println("===================");
        System.out.println("newFiles的长度为：" + newFiles.length);
        for (MultipartFile f : newFiles) {
            System.out.println(f.getOriginalFilename());
        }
        System.out.println("===================");
        return newFiles;
    }

    /**
     * 解析excel，每一行封装为一个对象，存在list集合中
     *
     * @param file
     * @return
     */
    public static List<Object> readExcel(CommonsMultipartFile file, Object o) throws Exception {

        //如果是文件夹
        /*if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files.length != 0) {
                for (File f : files) {
                    return readExcel(f.getAbsolutePath());
                }
            }else {
                throw new IOException("文件夹为空");
            }
            //如果上传文件
        } else {*/

        String fileName = file.getOriginalFilename();
        System.out.println("文件名：" + fileName);
        //获取后缀
        Iterator<Sheet> sheetIterator;
        String endFile = (fileName.lastIndexOf(".") == -1 ? "" :
                fileName.substring(fileName.lastIndexOf(".") + 1)).trim();
        if ("xls".equals(endFile)) {
            sheetIterator = new HSSFWorkbook(file.getInputStream()).iterator();
            /*list = readXls(file);*/
        } else if ("xlsx".equals(endFile)) {
            sheetIterator = new XSSFWorkbook(file.getInputStream()).iterator();
            /*list = readXlsx(file);*/
        } else {
            throw new IOException("不支持的文件类型");
        }
        return listExcel(sheetIterator, o);
    }

    /**
     * 封装实体类
     *
     * @param sheetIterator
     * @return
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
                //int numberOfCells = row.getPhysicalNumberOfCells();
                if (o instanceof EgContrast) {
                    int j = 0;
                    EgContrast egContrast = new EgContrast();
                    //封装egContrast
                    Cell cell;
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor01(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor02(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor03(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor04(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor05(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor06(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor07(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor08(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor09(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor10(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor11(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor12(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor13(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor14(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor15(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor16(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor17(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor18(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor19(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor20(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor21(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor22(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor23(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor24(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor25(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor26(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor27(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor28(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor29(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor30(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor31(cell.getStringCellValue());
                    }
                    if ((cell = row.getCell(j++)) != null){
                        cell.setCellType(CellType.STRING);
                        egContrast.setSensor32(cell.getStringCellValue());
                    }
                    /*egContrast.setSensor03(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor04(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor05(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor06(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor07(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor08(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor09(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor10(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor11(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor12(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor13(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor14(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor15(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor16(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor17(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor18(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor19(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor20(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor21(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor22(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor23(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor24(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor25(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor26(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor27(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor28(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor29(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor30(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor31(row.getCell(j++).getNumericCellValue());
                    egContrast.setSensor32(row.getCell(j++).getNumericCellValue());*/
                    list.add(egContrast);
                }
                if (o instanceof Preec) {
                    Preec preec = new Preec();
                    int j = 0;
                    row.getCell(j).setCellType(CellType.STRING);
                    preec.setId_subjects(Integer.parseInt(row.getCell(j++).getStringCellValue()));
                    preec.setAdvance(row.getCell(j++).getStringCellValue());
                    preec.setRemark(row.getCell(j++).getStringCellValue());
                    list.add(preec);
                }
            }
        }
        return list;
    }
}
