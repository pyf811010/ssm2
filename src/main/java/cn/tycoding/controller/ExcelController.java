package cn.tycoding.controller;

import cn.tycoding.pojo.FilesFolder;
import cn.tycoding.pojo.State;
import cn.tycoding.service.ExcelService;
import cn.tycoding.service.FilesOrganizeService;
import cn.tycoding.util.FolderPathUtil;
import cn.tycoding.util.MyExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


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
    public State upload_multi( @RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
    	String user_name = (String) request.getSession().getAttribute("user_name");
    	System.out.println("开始分析文件");
    	Map<String,List<FilesFolder>> filesFoldersList = FolderPathUtil.getFolderInfo("D:/ProgrammData/", files);
    	if (filesFoldersList.containsKey("false")) {
    		State state = new State();
    		state.setSuccess(0);
    		for (FilesFolder fs: filesFoldersList.get("false")) {
    			state.setInfo(fs.getInfo());
    		}
    		return state;
    	}
    	System.out.println("成功获取文件信息");
        State state = filesOrganizeService.insertByString(filesFoldersList,user_name);
        System.err.println(state.getInfo()+state.getSuccess());
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
        MyExcelUtil.downloadExcel(response, workbook, fileName);
    }

    @ResponseBody
    @RequestMapping("/uploadFile")
    public State uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        return excelService.readExcelFile(file, request);
    }
}