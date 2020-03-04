package cn.tycoding.controller;

import cn.tycoding.pojo.State;
import cn.tycoding.pojo.FilesFolder;
import cn.tycoding.service.FilesOrganizeService;
import cn.tycoding.util.FolderPathUtil;

import java.util.List;

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
    private FilesOrganizeService filesOrganizeService;
    private FolderPathUtil folderPathUtil;

    @RequestMapping(value = "/upload")
    @ResponseBody
    public State upload_multi( @RequestParam("files") MultipartFile[] files) {
    	
    	System.out.println("开始分析文件");
    	List<FilesFolder> filesFoldersList = folderPathUtil.getFolderInfo("D:/ProgrammData/", files);
    	System.out.println("成功获取文件信息");
        State state = filesOrganizeService.insertByString(filesFoldersList);
        return state;
    }
}