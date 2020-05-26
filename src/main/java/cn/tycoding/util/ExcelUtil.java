package cn.tycoding.util;

import cn.tycoding.constant.ExcelConstant;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 付东东
 * @data 2020-03-05 10:46
 * @description class ExcelUtil excel的工具类
 */
public class ExcelUtil {

    /**
     * 下载excel
     *
     * @param response
     * @param workbook HSSFWorkbook对象
     * @param fileName 文件名，包含后缀名
     */
    public static void downloadExcel(HttpServletResponse response, XSSFWorkbook workbook, String fileName) {
        System.out.println("已经进入到导出的程序");
        // 6.设置reponse参数
        response.setHeader("Content-Disposition", "inline; filename=" + fileName);
        // 确保发送的当前文本格式
        response.setContentType("application/vnd.ms-excel");
        OutputStream ops = null;
        try {
            ops = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(ops);
            bos.flush();
            workbook.write(bos);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置列宽
     * @param sheet
     */
    public static void setColumnSize(XSSFSheet sheet) {
        XSSFRow row = sheet.getRow(0);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            sheet.setColumnWidth(i, ExcelConstant.COLUMN_WIDTH);
        }
    }
}
