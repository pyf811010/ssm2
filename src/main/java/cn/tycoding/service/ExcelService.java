package cn.tycoding.service;

import cn.tycoding.pojo.State;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pyf lsy fdd
 * @date 2020/1/6 20:25:56
 * @description
 */
public interface ExcelService {

    /**
     *  上传文件内的数据
     * @param file
     * @return 上传结果信息
     */
    State readExcelFile(MultipartFile file);

    /**
     * 下载模板对象
     * @param name 模板的名字
     * @return 模板
     */
    HSSFWorkbook getTemplate(String name);
}