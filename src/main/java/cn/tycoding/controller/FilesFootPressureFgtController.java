package cn.tycoding.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesFootPressureFgt;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.FilesOxygen;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.service.FilesEleService;
import cn.tycoding.service.FilesFootPressureFgtService;
import cn.tycoding.service.FilesKandService;
import cn.tycoding.service.FilesOxygenService;

/**
 * 足底压力.fgt
 * @author pyf
 *
 */
@Controller
@RequestMapping(value = "/FilesFootPressureFgt")
public class FilesFootPressureFgtController {
    /**
     * 注入service
     */
    @Autowired
    private FilesFootPressureFgtService filesFootPressureFgtService;
    
    @RequestMapping("/findsome")
    @ResponseBody
    public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows)
            throws UnsupportedEncodingException {
        if (filters != null) {
            // 转码
            filters = new String(filters.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(filters);
        }
        return filesFootPressureFgtService.findByPage(_search, filters, page, rows);

    }
    
    @RequestMapping("/find")
    @ResponseBody
    public List find(){
        
        return filesFootPressureFgtService.find();

    }
    
    @RequestMapping("/handle")
    @ResponseBody
    public String handle(String oper, FilesFootPressureFgt filesFootPressureFgt, String id[])
            throws UnsupportedEncodingException {
    	System.out.println(id);
        String temp = filesFootPressureFgtService.handle(oper, filesFootPressureFgt, id);
        // 对传回的中文进行编码
        return URLEncoder.encode(temp, "UTF-8");
    }
    
    @RequestMapping("/download/{expid}")
    @ResponseBody
    public void download(@PathVariable int expid, HttpServletResponse response) {
    	try {
    		filesFootPressureFgtService.download(expid, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/open")
    @ResponseBody
    public void open(int expid) {
    	try {
    		filesFootPressureFgtService.open(expid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
