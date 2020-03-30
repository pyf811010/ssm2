package cn.tycoding.controller;

import cn.tycoding.pojo.State;
import cn.tycoding.service.ExcelService;
import cn.tycoding.service.GaitCyclePicUploadService;
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
@RequestMapping("/GaitCycle")
public class GaitCyclePicUploadController {

    @Autowired
    private GaitCyclePicUploadService gaitCyclePicUploadService;

    @RequestMapping(value = "/upload")
    @ResponseBody
    public State upload_multi(@RequestParam("files") MultipartFile[] files,HttpServletRequest request) {
    	String user_name = (String) request.getSession().getAttribute("user_name");
    	if(files.length != 0) {
            State state = gaitCyclePicUploadService.readExcelFile(files,user_name);
            return state;
        }else {
            return new State(0, "请选择上传文件");
        }
    }

    @RequestMapping(value = "/singlePicUpload")
    @ResponseBody
    public State upload_single(@RequestParam("files") MultipartFile[] files,String fi_info,HttpServletRequest request) {
    	String user_name = (String) request.getSession().getAttribute("user_name");
    	State state = gaitCyclePicUploadService.readSingleFile(fi_info,files,user_name);
    	return state;
    }
}