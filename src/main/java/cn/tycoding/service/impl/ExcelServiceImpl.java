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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public State readExcelFile(MultipartFile file) {
        //保存上传结果信息
        State state = new State();
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
            if ("admin".equals(fileName)) {
                try {
                    //如果文件类型正确，对文件进行解析，封装成实体类，并保存在list集合中
                    list = ReadExcel.readExcel(f, new Admin());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //遍历list集合，依次存入数据库
                for (Object o : list) {
                    number += adminMapper.add((Admin) o);
                }
            }
            else if ("machine".equals(fileName)) {
                try {
                    list = ReadExcel.readExcel(f, new Machine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (Object o : list) {
                    number += machineMapper.add((Machine) o);
                }
            }
            else if ("subjects".equals(fileName)) {
                try {
                    list = ReadExcel.readExcel(f, new Subjects());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (Object o : list) {
                    number += subjectsMapper.add((Subjects) o);
                }
            }
            else {
                state.setInfo("上传错误：文件名无法识别！");
                state.setSuccess(0);
            }
                /*switch (fileName) {
                    case "admin":
                        System.out.println(o);
                        break;
                    case "machine":

                        break;
                    case "subjects":

                        break;
                    default:

                        break;
                }*/

        }
        //判断上传状态
        if (number > 0) {
            if (number == list.size()) {
                state.setSuccess(1);
                state.setInfo("全部信息上传成功");
            } else {
                state.setSuccess(2);
                state.setInfo("部分信息上传成功");
            }
        } else {
            state.setSuccess(0);
            state.setInfo("上传失败：文件内容可能为空！");
        }
        //返回上传信息
        return state;
    }


    @Override
    public HSSFWorkbook getTemplate(String name) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        List<String> header = getExcelHeader(name);
        //根据name表的字段信息创建excel表格的表头。
        for (int i = 0; i < header.size(); i++) {
            HSSFCell cell = row.createCell(i);
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
                index = ExcelConstant.PREEC_INDEX;
                break;

        }
        for (int i = 0; i < index.length; i++) {
            result.add(list.get(index[i]).getComment());
        }
        return result;
    }
}
