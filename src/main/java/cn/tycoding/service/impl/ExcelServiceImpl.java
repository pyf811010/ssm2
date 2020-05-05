package cn.tycoding.service.impl;

import cn.tycoding.constant.ExcelConstant;
import cn.tycoding.constant.FileConstant;
import cn.tycoding.entity.assist.DBTableComment;
import cn.tycoding.mapper.*;
import cn.tycoding.pojo.*;
import cn.tycoding.service.ExcelService;
import cn.tycoding.util.ExcelUtil;
import cn.tycoding.util.ReadExcel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author：pyf lsy fdd
 * @date： 2020/1/6  20:25:40
 * @description：
 */

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private FilesKandMapper filesKandMapper;

    @Autowired
    private FilesOxygenMapper filesOxygenMapper;

    @Autowired
    private SubjectsMapper subjectsMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PreecMapper preecMapper;

    @Autowired
    private EgContrastMapper egContrastMapper;

    @Autowired
    private MachineMapper machineMapper;
    
    @Autowired
    private FilesLiteratureMapper filesLiteratureMapper;

    @Override
    public synchronized State readExcelFile(MultipartFile file,String user_name) {
        //保存上传结果信息
        State state = new State();
        StringBuffer stringBuffer = new StringBuffer();
        int number = 0;
        //存放excel表中的信息封装的对象。
        List<Object> list = null;
        if (file instanceof CommonsMultipartFile) {
            CommonsMultipartFile f = (CommonsMultipartFile) file;
            String originalFilename = f.getOriginalFilename();
            System.out.println("文件名：" + originalFilename);
            //文件名
            String fileName = originalFilename.substring(0, originalFilename.lastIndexOf(".")).trim().toLowerCase();
            //文件后缀，即文件类型
            String suffix = (originalFilename.lastIndexOf(".") == -1 ? "" :
                    originalFilename.substring(originalFilename.lastIndexOf(".") + 1)).trim();
            System.out.println("文件类型：" + suffix);
            //判断文件类型是否在正确
            if (!"xls".equals(suffix) && !"xlsx".equals(suffix)) {
                return new State(0, "请选择正确的上传文件类型（.xls;.xlsx）");
            }
            //管理员导入
            if ("admin".equals(fileName)) {
                try {
                    //如果文件类型正确，对文件进行解析，封装成实体类，并保存在list集合中
                    list = ReadExcel.readExcel(f, new Admin(),user_name);
                    if(list==null) return new State(0,"上传数据存在为空的字段，此次上传失败！（姓名、密码、用户类型均不能为空）");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //遍历list集合，依次存入数据库
                // 要判断数据库中是否已经有这条数据：如果已经存在一条相同数据，就提示而不导入数据库。
                // 可以先从数据库中读出数据封装成对象，然后比较，需要对象重写equals方法
                for (int i = 0; i < list.size(); i++){
                    int num = adminMapper.addTemplate((Admin) list.get(i));
                    if (num != 0){
                        number += num;
                    }else {
                        stringBuffer.append("第" + (i + 1) + "行信息重复上传" + "\n");
                    }
                }
            }
            //实验机器
            else if ("machine".equals(fileName)) {
                try {
                    list = ReadExcel.readExcel(f, new Machine(),user_name);
                    if(list==null) return new State(0,"设备名称不能为空，此次上传失败");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < list.size(); i++){
                    int num = machineMapper.addTemplate((Machine) list.get(i));
                    if (num != 0){
                        number += num;
                    }else {
                        stringBuffer.append("第" + (i + 1) + "行信息重复上传" + "\n");
                    }
                }
            }
            //实验对象
            else if ("subjects".equals(fileName)) {
                try {
                    list = ReadExcel.readExcel(f, new Subjects(),user_name);
                    if(list==null) return new State(0,"身高、体重列不能为空，此次上传失败");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < list.size(); i++){
                //for (Object o : list) {
                    int num = subjectsMapper.addTemplate((Subjects) list.get(i));
                    if (num != 0){
                        number += num;
                    }else {
                        stringBuffer.append("第" + (i + 1) + "行信息重复上传" + "\n");
                    }
                }
            }
            else {
                state.setInfo("上传错误：文件名无法识别！");
                state.setSuccess(0);
            }
        }
        //判断上传状态
        if (number > 0) {
            if (number == list.size()) {
                state.setSuccess(1);
                state.setInfo("全部信息上传成功");
            } else {
                state.setSuccess(2);
                state.setInfo("部分信息上传成功" + "\n" + stringBuffer.toString());
            }
        } else {
            state.setSuccess(0);
            if (stringBuffer.length() == 0){
                state.setInfo("上传失败：文件内容可能为空");
            }else {
                state.setInfo("上传失败："  + "\n" + stringBuffer.toString());
            }

        }
        //返回上传信息
        return state;
    }


    @Override
    public XSSFWorkbook getTemplate(String name) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = sheet.createRow(0);
        List<String> header = getExcelHeader(name);
        //根据name表的字段信息创建excel表格的表头。
        for (int i = 0; i < header.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(header.get(i));
        }
        ExcelUtil.setColumnSize(sheet);
        return workbook;
    }

    /**
     * 从数据库中获取name表的字段信息。
     *
     * @param name 模板表格的名字，对应数据库中的相应的表格
     * @return 数据库中相应表格的字段信息。
     */
    public List<String> getExcelHeader(String name) {
        int[] index = new int[33];
        List<DBTableComment> list = new ArrayList<>();
        List<String> result = new ArrayList<>();
        switch (name) {
            case "egcontrast":
                list = egContrastMapper.findDbTableComment();
                index = ExcelConstant.EGCONTRAST_INDEX;
                break;
            case "machine":
                list = machineMapper.findDbTableComment();
                index = ExcelConstant.MACHINE_INDEX;
                break;
            case "subjects":
                list = subjectsMapper.findDbTableComment();
                index = ExcelConstant.SUBJECT_INDEX;
                break;
            case "admin":
                list = adminMapper.findDbTableComment();
                index = ExcelConstant.ADMIN_INDEX;
                break;
            case "preec":
                list = preecMapper.findDbTableComment();
                list.add(new DBTableComment("sub_id", "受试者ID(受试者ID与受试者信息二选一进行填写，若二者都填写，则只按受试者ID进行处理）"));
                list.add(new DBTableComment("identity_card", "受试者身份证"));
                list.add(new DBTableComment("name", "受试者姓名"));
                list.add(new DBTableComment("age", "受试者年龄"));
                list.add(new DBTableComment("weight", "受试者体重"));
                list.add(new DBTableComment("height", "受试者身高"));
                list.add(new DBTableComment("remark", "受试者备注信息"));
                index = ExcelConstant.PREEC_INDEX;
                break;
            case "literature":
                list = filesLiteratureMapper.findDbTableComment();
                index = ExcelConstant.LITERATURE_INDEX;
                break;
        }
        for (int i = 0; i < index.length; i++) {
            result.add(list.get(index[i]).getComment());
        }
        return result;
    }
}
