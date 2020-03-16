package cn.tycoding.controller;

import cn.tycoding.pojo.State;
import cn.tycoding.service.ExcelService;
import cn.tycoding.service.UpdateExcelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author：pyf 
 * @date
 * @description：上传修改后的运动学与动力学数据文件
 */
@Controller
@RequestMapping("/updateExcel")
public class UpdateExcelController {

    @Autowired
    private UpdateExcelService updateExcelService;

    @RequestMapping(value = "/upload")
    @ResponseBody
    public State upload_multi(String fi_info,@RequestParam("files") MultipartFile[] files) {
        State state = updateExcelService.readExcelFile(fi_info,files);
        return state;
    }
}