package cn.tycoding.controller;

import cn.tycoding.pojo.State;
import cn.tycoding.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author：pyf lsy fdd
 * @date： 2020/1/3  15:51:05
 * @description：
 */
@Controller
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @RequestMapping(value = "/upload")
    @ResponseBody
    public State upload_multi( @RequestParam("files") MultipartFile[] files) {
        /*Query query = new Query();
        System.out.println(files.length == 0 ? "files为空" : "正常");
        StringBuilder msg = new StringBuilder();
        MultipartFile[] multipartFiles = ReadExcel.sort(files);
        for (int i = 0; i < 3; i++) {
            msg.append(ReadExcel.importExcel(multipartFiles[i]).getMessage());
        }
        System.out.println(msg.toString());
        return msg.toString();*/
        State state = excelService.readExcelFile(files);
        return state;
    }
}