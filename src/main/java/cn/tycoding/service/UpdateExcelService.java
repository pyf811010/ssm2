package cn.tycoding.service;

import cn.tycoding.pojo.State;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pyf lsy fdd
 * @date 2020/1/6 20:25:56
 * @description
 */
public interface UpdateExcelService {

    State readExcelFile(String fi_info, MultipartFile[] files);
}