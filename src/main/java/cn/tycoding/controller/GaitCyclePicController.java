package cn.tycoding.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.GaitCyclePic;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.service.FilesEleService;
import cn.tycoding.service.FilesKandService;
import cn.tycoding.service.GaitCyclePicService;

/**
 * 步态周期图像
 * @author pyf
 *
 */
@Controller
@RequestMapping(value = "/GaitCyclePic")
public class GaitCyclePicController {
    /**
     * 注入service
     */
    @Autowired
    private GaitCyclePicService gaitCyclePicService;
    
    @RequestMapping("/findsome")
    @ResponseBody
    public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows)
            throws UnsupportedEncodingException {
        if (filters != null) {
            // 转码
            filters = new String(filters.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(filters);
        }
        return gaitCyclePicService.findByPage(_search, filters, page, rows);

    }
    
    @RequestMapping("/find")
    @ResponseBody
    public List find(){
        
        return gaitCyclePicService.find();

    }
    
    @RequestMapping("/handle")
    @ResponseBody
    public String handle(String oper, GaitCyclePic gaitCyclePic, String id[],HttpServletRequest request)
            throws UnsupportedEncodingException {
        return gaitCyclePicService.authorityTemp(oper,gaitCyclePic,id,request);
    }
    
    @RequestMapping("/open")
    @ResponseBody
    public void open(int p_id) {
    	try {
    		gaitCyclePicService.open(p_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/download/{p_id}")
    @ResponseBody
    public void download(@PathVariable int p_id, HttpServletResponse response) {
    	try {
    		gaitCyclePicService.download(p_id, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
