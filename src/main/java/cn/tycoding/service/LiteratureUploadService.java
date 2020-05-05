package cn.tycoding.service;

import cn.tycoding.pojo.State;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pyf 
 * @date 2020/4/22 20:25:56
 * @description
 */
public interface LiteratureUploadService {

    State readExcelFile(MultipartFile[] files, String user_name);

	State readSingleFile(String fi_info_topic,String fi_info_company,String fi_info_time,String fi_info_remark,String fi_info_writer, MultipartFile[] files,String user_name);
}