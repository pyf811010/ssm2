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
import cn.tycoding.pojo.FilesKandUpdateinfo;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.service.FilesEleService;
import cn.tycoding.service.FilesKandUpdateService;

/**
 * 运动学与动力学更新数据
 * @author pyf
 *
 */
@Controller
@RequestMapping(value = "/KandUpdate")
public class FilesKandUpdateController {
    /**
     * 注入service
     */
    @Autowired
    private FilesKandUpdateService filesKandUpdateService;
    
    @RequestMapping("/findsome")
    @ResponseBody
    public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows)
            throws UnsupportedEncodingException {
        if (filters != null) {
            // 转码
            filters = new String(filters.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(filters);
        }
        return filesKandUpdateService.findByPage(_search, filters, page, rows);

    }
    
    @RequestMapping("/find")
    @ResponseBody
    public List find(){
        
        return filesKandUpdateService.find();

    }
    
    @RequestMapping("/handle")
    @ResponseBody
    public String handle(String oper, FilesKandUpdateinfo filesKandUpdateinfo, String id[],HttpServletRequest request)
            throws UnsupportedEncodingException {
    	return filesKandUpdateService.authorityTemp(oper,filesKandUpdateinfo,id,request);
    }
    
    @RequestMapping("/download/{u_id}")
    @ResponseBody
    public void download(@PathVariable int u_id, HttpServletResponse response) {
    	try {
    		filesKandUpdateService.download(u_id, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/open")
    @ResponseBody
    public void open(int u_id) {
    	try {
    		filesKandUpdateService.open(u_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/Sign")
    @ResponseBody
    public State Sign(int expid,HttpServletRequest request) {
    	try {
    		State state = filesKandUpdateService.sign(expid,request);
    		return state;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    	
    }
    
    
    @RequestMapping("/CancelSign")
    @ResponseBody
    public State CancelSign(int expid,HttpServletRequest request) {
    	try {
    		State state =filesKandUpdateService.cancelSign(expid,request);
    		return state;
    	} catch (IOException e) {
    		e.printStackTrace();
    		return null;
    	}
    }

}
