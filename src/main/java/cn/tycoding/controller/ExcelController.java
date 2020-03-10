package cn.tycoding.controller;

import cn.tycoding.pojo.State;
import cn.tycoding.pojo.FilesFolder;
import cn.tycoding.service.ExcelService;
import cn.tycoding.service.FilesOrganizeService;
import cn.tycoding.util.ExcelUtil;
import cn.tycoding.util.FolderPathUtil;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author：pyf lsy fdd
 * @date： 2020/1/3  15:51:05
 * @description：处理文件的上传
 */
@Controller
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private FilesOrganizeService filesOrganizeService;

    @Autowired
    private ExcelService excelService;

    @RequestMapping(value = "/upload")
    @ResponseBody
    public State upload_multi( @RequestParam("files") MultipartFile[] files) {
    	
    	System.out.println("开始分析文件");
    	Map<String,List<FilesFolder>> filesFoldersList = FolderPathUtil.getFolderInfo("D:/ProgrammData/", files);
    	System.out.println("成功获取文件信息");
        State state = filesOrganizeService.insertByString(filesFoldersList);
        return state;
    }

    /**
     * 下载excel模板
     * @param response
     */
    @ResponseBody
    @RequestMapping("/downloadTemplate")
    public void  downloadTemplate(HttpServletResponse response, @RequestParam(value = "name") String name){
        XSSFWorkbook workbook = excelService.getTemplate(name);
        String fileName = name + ".xlsx";
        ExcelUtil.downloadExcel(response, workbook, fileName);
    }

    @ResponseBody
    @RequestMapping("/uploadFile")
    public State uploadFile(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()){
            return excelService.readExcelFile(file);
        }else {
            return new State(0, "请选择上传文件");
        }
    }
}