package cn.tycoding.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.tycoding.constant.ExcelConstant;
import cn.tycoding.entity.assist.DBTableComment;
import cn.tycoding.mapper.*;
import cn.tycoding.pojo.*;
import cn.tycoding.service.ExcelService;
import cn.tycoding.util.MyExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author：pyf lsy fdd
 * @date： 2020/1/6  20:25:40
 * @description：
 */

@Service
public class ExcelServiceImpl implements ExcelService {

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

    /**
     * 读取excel中的信息
     *
     * @param file 需要上传的文件
     * @param req  请求头
     * @return 上传结果
     */
    @Override
    public State readExcelFile(MultipartFile file, HttpServletRequest req) {
        //保存上传结果信息
        State state = new State();
        StringBuffer stringBuffer = new StringBuffer();
        int number = 0;
        //存放excel表中的信息封装的对象。
        List<Object> list = null;
        if (file instanceof CommonsMultipartFile) {
            CommonsMultipartFile f = (CommonsMultipartFile) file;
            String fileName = MyExcelUtil.getFileName(f);
            try {
                list = parseExcel(f, fileName, req);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //管理员导入
            if ("admin".equals(fileName)) {

                //遍历list集合，依次存入数据库
                // 要判断数据库中是否已经有这条数据：如果已经存在一条相同数据，就提示而不导入数据库。
                // 可以先从数据库中读出数据封装成对象，然后比较，需要对象重写equals方法
                for (int i = 0; i < list.size(); i++) {
                    int num = adminMapper.addTemplate((Admin) list.get(i));
                    if (num != 0) {
                        number += num;
                    } else {
                        stringBuffer.append("第" + (i + 1) + "行信息重复上传" + "\n");
                    }
                }
            }
            //实验机器
            else if ("machine".equals(fileName)) {
                for (int i = 0; i < list.size(); i++) {
                    int num = machineMapper.addTemplate((Machine) list.get(i));
                    if (num != 0) {
                        number += num;
                    } else {
                        stringBuffer.append("第" + (i + 1) + "行信息重复上传" + "\n");
                    }
                }
            }
            //实验对象
            else if ("subjects".equals(fileName)) {
                for (int i = 0; i < list.size(); i++) {
                    //for (Object o : list) {
                    int num = subjectsMapper.addTemplate((Subjects) list.get(i));
                    if (num != 0) {
                        number += num;
                    } else {
                        stringBuffer.append("第" + (i + 1) + "行信息重复上传" + "\n");
                    }
                }
            } else {
                state.setInfo("上传错误：文件名错误！");
                state.setSuccess(0);
                return state;
            }
        }
        //返回上传信息
        return getResult(state, number, stringBuffer, list);
    }

    /**
     * 上传结果
     *
     * @param state        封装结果信息
     * @param number       上传成功的信息数量
     * @param stringBuffer 返回结果
     * @param list
     * @return
     */
    private State getResult(State state, int number, StringBuffer stringBuffer, List<Object> list) {
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
            if (stringBuffer.length() == 0) {
                state.setInfo("上传失败：文件内容可能为空");
            } else {
                state.setInfo("上传失败：" + "\n" + stringBuffer.toString());
            }
        }
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
        MyExcelUtil.setColumnSize(sheet);
        return workbook;
    }

    /**
     * 从数据库中获取name表的字段信息。
     *
     * @param name 模板表格的名字，对应数据库中的相应的表格
     * @return 数据库中相应表格的字段信息。
     */
    private List<String> getExcelHeader(String name) {
        int[] index = getIndex(name);
        List<DBTableComment> list = getDbTableCommentList(name);
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < index.length; i++) {
            result.add(list.get(index[i]).getComment());
        }
        return result;
    }

    /**
     * 获取表格的字段名和备注
     *
     * @param name
     * @return
     */
    private List<DBTableComment> getDbTableCommentList(String name) {
        System.out.println("获取表头信息开始。。。。。");
        List<DBTableComment> list = new ArrayList<>();
        switch (name) {
            case "egcontrast":
                list = egContrastMapper.findDbTableComment();
                break;
            case "machine":
                list = machineMapper.findDbTableComment();
                break;
            case "subjects":
                list = subjectsMapper.findDbTableComment();
                break;
            case "admin":
                list = adminMapper.findDbTableComment();
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
                break;
            default:
                break;
        }
        System.out.println("获取表头信息结束。。。。。");
        System.out.println(list);
        return list;
    }

    /**
     * 获取模板表头的在DBTableCommentlist中的下标
     *
     * @param name
     * @return
     */
    private int[] getIndex(String name) {
        int[] index = new int[33];
        switch (name) {
            case "egcontrast":
                index = ExcelConstant.EGCONTRAST_INDEX;
                break;
            case "machine":
                index = ExcelConstant.MACHINE_INDEX;
                break;
            case "subjects":
                index = ExcelConstant.SUBJECT_INDEX;
                break;
            case "admin":
                index = ExcelConstant.ADMIN_INDEX;
                break;
            case "preec":
                index = ExcelConstant.PREEC_INDEX;
                break;
            default:
                break;
        }
        return index;
    }

    public <T> List<T> parseExcel(CommonsMultipartFile file, String name, HttpServletRequest req) throws IOException {
        Class<T> beanType = getBeanType(name);

        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        // map就是一行。
        //[{设备名称=测试设备1, 设备型号=12-2349-12313, 所属单位=null, 布置场地=503, 备注=测试用}]
        List<Map<String, Object>> mapList = reader.read(0, 1, Integer.MAX_VALUE);

        System.out.println("excel中读取数据为："+mapList);

        mapList = changeMap(mapList, name, req);
        //Bean实例保存在beanList中
        final List<T> beanList = new ArrayList<>(mapList.size());

        for (Map<String, Object> map : mapList) {
            beanList.add(BeanUtil.mapToBean(map, beanType, false));
        }
        //打印beanlist
        for (int i = 0; i < beanList.size(); i++) {
            T t = beanList.get(i);
            System.out.println(t);
        }
        return beanList;
    }

    /**
     * 获取文件对应的JavaBean的Class类
     *
     * @param name 文件名
     * @param <T>  类型
     * @return Class
     */
    private <T> Class<T> getBeanType(String name) {
        Class beanType;
        switch (name) {
            case "machine":
                beanType = Machine.class;
                break;
            case "subjects":
                beanType = Subjects.class;
                break;
            case "admin":
                beanType = Admin.class;
                break;
            default:
                beanType = Object.class;
                break;
        }
        return beanType;
    }

    /**
     * 将map中的key换成JavaBean中属性列名，便于封装成Bean对象
     *
     * @param list excel文件中的信息
     * @param name 文件名，可以知道文件对应哪个JavaBean实例
     * @return list
     */
    private List<Map<String, Object>> changeMap(List<Map<String, Object>> list, String name, HttpServletRequest req) {

        List<Map<String, Object>> newList = new ArrayList<>(list.size());

        //获得属性名和备注
        List<DBTableComment> dbTableCommentList = getDbTableCommentList(name);
        //将备注换成属性名
        //逐行遍历替换map
        for (int i = 0; i < list.size(); i++) {
            //遍历list取出每个map，对每一个map（行）进行修改key
            //取出map
            Map<String, Object> objectMap = list.get(i);
            //新得map
            Map<String, Object> map = new HashMap<>(objectMap.size());
            //遍历原来的map取出key值
            Set<String> keySet = objectMap.keySet();
            Iterator<String> iterator = keySet.iterator();
            //逐个替换key
            while (iterator.hasNext()) {
                String key = iterator.next();
                //找到key对应的列名
                for (int j = 0; j < dbTableCommentList.size(); j++) {
                    DBTableComment dbTableComment = dbTableCommentList.get(j);

                    if ((dbTableComment.getComment()).equals(key)) {
                        String newKey = dbTableComment.getField();
                        //put到新的map中
                        map.put(newKey, objectMap.get(key));
                        //找到就退出循环
                        break;
                    }
                }
            }
            map.put("user_name", req.getSession().getAttribute("user_name"));

            //map放到list中
            newList.add(map);
        }
        return newList;
    }
}
