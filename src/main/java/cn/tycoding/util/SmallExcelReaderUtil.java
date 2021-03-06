package cn.tycoding.util;
/**
 * @author lsy
 * @Funtion 读取excel文件，入口getListByExcel(文件路径，开始行(excel中的行标)),返回<sheet-list <row-list <column-list>>>
 */
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.tycoding.pojo.EgContrast;
import cn.tycoding.pojo.ExcelBean;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.config.YamlMapFactoryBean;

import ch.qos.logback.core.pattern.color.ANSIConstants;

import java.io.FileInputStream;
public class SmallExcelReaderUtil {

    private final static String excel2003L = ".xls"; // 2003- 版本的excel
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel

    /**
     * Excel导入
     */
    public static List<List<List<Object>>> getListByExcel(String filePath,int startrow) throws Exception {
    	List<List<List<Object>>> ansList = new ArrayList<List<List<Object>>>();
    	List<List<Object>> list = null;
        // 创建Excel工作薄
        FileInputStream instr = new FileInputStream(filePath); 
    	Workbook work = null;
        String fileType = filePath.substring(filePath.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            work = new HSSFWorkbook(instr); // 2003-
        } else if (excel2007U.equals(fileType)) {
            work = new XSSFWorkbook(instr); // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }

        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
       
        // 遍历Excel中所有的sheet
        //System.out.println("开始解析excel");
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
        	
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            // 遍历当前sheet中的所有行
            // 包含头部，所以要小于等于最后一列数,这里也可以在初始值加上头部行数，以便跳过头部
            list = new ArrayList<List<Object>>();
            System.out.println("共"+sheet.getLastRowNum()+"行");
            int totalColumn = sheet.getRow(0).getLastCellNum();
            for (int j = sheet.getFirstRowNum()+startrow-1; j <= sheet.getLastRowNum(); j++) {
                // 读取一行
            	//System.out.println("	第"+j+"行");
                row = sheet.getRow(j);
                // 去掉空行和表头
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                // 遍历所有的列
                //System.out.println("共"+row.getLastCellNum()+"行");
                List<Object> li = new ArrayList<Object>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                	//System.out.println("		解析第"+y+"列：");
                	try{	
						cell = row.getCell(y);
						//System.out.println("		第"+y+"列："+getCellValue(cell));
	                    li.add(getCellValue(cell));
					}catch(Exception e){
						//System.out.println("		捕捉到空cell");	
						li.add("");
					}
                    
                }
                if (totalColumn > row.getLastCellNum()) {
                	for (int y = 0; y < totalColumn -row.getLastCellNum(); y++) {
                		li.add("");
                	}
                }
                list.add(li);
            }
            ansList.add(list);
            
        }
        //System.out.println("解析excel完成");
        //关闭文件流
        if(work != null)
        	work.close();
        if(instr != null)
        	instr.close();
        
        return ansList;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     */
    public static Workbook getWorkbook(String filePath) throws Exception {
    	FileInputStream instr = new FileInputStream(filePath); 
    	Workbook wb = null;
        String fileType = filePath.substring(filePath.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(instr); // 2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(instr); // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        if (cell.getCellType() == CellType.STRING) {
            value = cell.getRichStringCellValue().getString();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            value = String.valueOf(cell.getNumericCellValue());
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            value = cell.getBooleanCellValue();
        } else if (cell.getCellType() == CellType.BLANK) {
            value = "";
        }
        return value;
    }

    /**
     * 导入Excel表结束 导出Excel表开始
     *
     * @param sheetName
     *            工作簿名称
     * @param clazz
     *            数据源model类型
     * @param objs
     *            excel标题列以及对应model字段名
     * @param map
     *            标题列行数以及cell字体样式
     */
    public static XSSFWorkbook createExcelFile(Class clazz, List objs, Map<Integer, List<ExcelBean>> map,
                                               String sheetName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
            ClassNotFoundException, IntrospectionException {
        // 创建新的Excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称
        XSSFSheet sheet = workbook.createSheet(sheetName);
        // 以下为excel的字体样式以及excel的标题与内容的创建，下面会具体分析;
        createTableHeader(sheet, map); //创建标题（头）
        createTableRows(sheet, map, objs, clazz); // 创建内容
        return workbook;
    }

    /**
     * @param sheet
     *            工作簿
     * @param map
     *            每行每个单元格对应的列头信息
     */
    public static final void createTableHeader(XSSFSheet sheet, Map<Integer, List<ExcelBean>> map) {
        for (Map.Entry<Integer, List<ExcelBean>> entry : map.entrySet()) {
            XSSFRow row = sheet.createRow(entry.getKey());
            List<ExcelBean> excels = entry.getValue();
            for (int x = 0; x < excels.size(); x++) {
                XSSFCell cell = row.createCell(x);
                cell.setCellValue(excels.get(x).getHeadTextName());// 设置内容
            }
        }
    }

    public static void createTableRows(XSSFSheet sheet, Map<Integer, List<ExcelBean>> map, List objs, Class clazz)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException{
        int rowindex = map.size();
        int maxKey = 0;
        List<ExcelBean> ems = new ArrayList<>();
        for (Map.Entry<Integer, List<ExcelBean>> entry : map.entrySet()) {
            if (entry.getKey() > maxKey) {
                maxKey = entry.getKey();
            }
        }
        ems = map.get(maxKey);
        for (Object obj : objs) {
            XSSFRow row = sheet.createRow(rowindex);
            for (int i = 0; i < ems.size(); i++) {
                ExcelBean em = (ExcelBean) ems.get(i);
                // 获得get方法
                PropertyDescriptor pd = new PropertyDescriptor(em.getPropertyName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object rtn = getMethod.invoke(obj);
                String value = "";
                // 如果是日期类型进行转换
                if (rtn != null) {
                    if (rtn instanceof Date) {
                        value = DateUtils.formatDate((Date) rtn, "yyyy-MM-dd");
                    } else {
                        value = rtn.toString();
                    }
                }
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(value);
                
            }
            rowindex++;
        }
    }
    
    public static List<EgContrast> readEGcontrasts(String filePath, int startrow) throws Exception {
    	List<EgContrast> ansList = new ArrayList<EgContrast>();
    	List<List<List<Object>>> excel = getListByExcel(filePath, startrow);
		for (List<List<Object>> sheet : excel) {
			for (List<Object> row : sheet) {
				System.out.println("rowsize:"+row.size());
				int j = 1;
				String cell = null;
				EgContrast egContrast = new EgContrast();
				Float floattmppreexpid = Float.parseFloat((String) row.get(0));
				Integer tmpexpid = floattmppreexpid.intValue() ;
				System.out.println("tmpexpid"+ tmpexpid);
				egContrast.setExpid(tmpexpid);
		    	if (!(cell = row.get(j++).toString()).equals("")){
		    		System.out.println("cell1"+cell);
		            egContrast.setSensor01(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            egContrast.setSensor02(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){	
		            egContrast.setSensor03(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            egContrast.setSensor04(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            egContrast.setSensor05(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            egContrast.setSensor06(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            egContrast.setSensor07(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            egContrast.setSensor08(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            egContrast.setSensor09(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            egContrast.setSensor10(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor11(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor12(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor13(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor14(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor15(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor16(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor17(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor18(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor19(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor20(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor21(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor22(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor23(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor24(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor25(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor26(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor27(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor28(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor29(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor30(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor31(cell);
		        }
		        if (!(cell = row.get(j++).toString()).equals("")){
		            
		            egContrast.setSensor32(cell);
		        }
		        
		        ansList.add(egContrast);
			}
		}
	    return ansList;
    }
}

