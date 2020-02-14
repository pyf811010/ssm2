package cn.tycoding.util;
/**
 * 数据导出到excel表的测试用例
 */


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
 
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
@RunWith(JUnit4.class)
public class PoiExcel {
    @Test
    public void exportExcel() {
        Map<String, Integer> accts = new HashMap<String, Integer>() {
            {
                put("123456", 125);
                put("123451", 121);
                put("123457", 124);
                put("123459", 122);
 
            }
        };
 
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("FXT");
        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cellOne = row1.createCell(0);
        // 设置单元格内容
        cellOne.setCellValue("账号");
        HSSFCell cellTwo = row1.createCell(1);
        // 设置单元格内容
        cellTwo.setCellValue("金额");
 
        //行数
        int rowNum = 1;
        //遍历hashmap
        Iterator iterator = accts.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            //创建一行行记录
            rowNum++;
            // 在sheet里创建下一行
            HSSFRow newRow = sheet.createRow(rowNum);
            // 创建单元格并设置单元格内容
            newRow.createCell(0).setCellValue((String) key);
            newRow.createCell(1).setCellValue((Integer) val);
 
        }
 
        // 第六步，将文件存到指定位置
        try {
            String path = "D:/a/b.xls";
            File file = new File(path);
            //如果已经存在则删除
            if (file.exists()) {
                file.delete();
            }
            //检查父包是否存在
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            //创建文件
            file.createNewFile();
            FileOutputStream fout = new FileOutputStream(path);
            wb.write(fout);
            String str = "导出成功！";
            System.out.println(str);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
            String str1 = "导出失败！";
            System.out.println(str1);
        }
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        //sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
 
 
    }
 
}
