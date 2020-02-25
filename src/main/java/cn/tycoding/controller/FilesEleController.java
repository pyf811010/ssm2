package cn.tycoding.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.service.FilesEleService;

/**
 * 肌电数据
 * @author pyf
 *
 */
@Controller
@RequestMapping(value = "/ele")
public class FilesEleController {
    /**
     * 注入service
     */
    @Autowired
    private FilesEleService filesEleService;
    
    @RequestMapping("/findsome")
    @ResponseBody
    public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows)
            throws UnsupportedEncodingException {
        if (filters != null) {
            // 转码
            filters = new String(filters.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(filters);
        }
        return filesEleService.findByPage(_search, filters, page, rows);

    }
    
    @RequestMapping("/find")
    @ResponseBody
    public List find(){
        
        return filesEleService.find();

    }
    
    @RequestMapping("/handle")
    @ResponseBody
    public String handle(String oper, FilesElectromyography filesElectromyography, String id[])
            throws UnsupportedEncodingException {
    	System.out.println(id);
        String temp = filesEleService.handle(oper, filesElectromyography, id);
        // 对传回的中文进行编码
        return URLEncoder.encode(temp, "UTF-8");
    }

}