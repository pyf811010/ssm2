package cn.tycoding.controller;

import cn.tycoding.pojo.State;
import cn.tycoding.service.ExcelService;
import cn.tycoding.service.GaitCyclePicUploadService;
import cn.tycoding.service.LiteratureUploadService;
import cn.tycoding.service.UpdateExcelService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author：pyf 
 * @date
 * @description：上传步态周期图像文件
 */
@Controller
@RequestMapping("/Literature")
public class LiteratureUploadController {

    @Autowired
    private LiteratureUploadService literatureUploadService;
    
    @RequestMapping(value = "/upload")
    @ResponseBody
    public State upload_multi(@RequestParam("files") MultipartFile[] files,HttpServletRequest request) {
    	String user_name = (String) request.getSession().getAttribute("user_name");
    	if(files.length != 0) {
            State state = literatureUploadService.readExcelFile(files,user_name);
            return state;
        }else {
            return new State(0, "请选择上传文件");
        }
    }
    
    @RequestMapping(value = "/singleLiteratureUpload")
    @ResponseBody
    public State upload_single(@RequestParam("files") MultipartFile[] files,String fi_info_topic,String fi_info_company,String fi_info_time,String fi_info_remark,String fi_info_writer,HttpServletRequest request) {
    	String user_name = (String) request.getSession().getAttribute("user_name");
    	State state = literatureUploadService.readSingleFile(fi_info_topic,fi_info_company,fi_info_time,fi_info_remark,fi_info_writer,files,user_name);
    	return state;
    }
}