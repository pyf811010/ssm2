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
import cn.tycoding.pojo.FilesOxygen;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.service.FilesEleService;
import cn.tycoding.service.FilesKandService;
import cn.tycoding.service.FilesOxygenService;

/**
 * 耗氧量数据
 * @author pyf
 *
 */
@Controller
@RequestMapping(value = "/FilesOxygen")
public class FilesOxygenController {
    /**
     * 注入service
     */
    @Autowired
    private FilesOxygenService filesOxygenService;
    
    @RequestMapping("/findsome")
    @ResponseBody
    public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows)
            throws UnsupportedEncodingException {
        if (filters != null) {
            // 转码
            filters = new String(filters.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(filters);
        }
        return filesOxygenService.findByPage(_search, filters, page, rows);

    }
    
    @RequestMapping("/find")
    @ResponseBody
    public List find(){
        
        return filesOxygenService.find();

    }
    
    @RequestMapping("/handle")
    @ResponseBody
    public String handle(String oper, FilesOxygen filesOxygen, String id[],HttpServletRequest request)
            throws UnsupportedEncodingException {
    	return filesOxygenService.authorityTemp(oper,filesOxygen,id,request);
    }
    
    @RequestMapping("/download/{expid}")
    @ResponseBody
    public void download(@PathVariable int expid, HttpServletResponse response) {
    	try {
    		filesOxygenService.download(expid, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/open")
    @ResponseBody
    public void open(int expid) {
    	try {
    		filesOxygenService.open(expid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/Sign")
    @ResponseBody
    public State Sign(int expid,HttpServletRequest request) {
    	try {
    		State state = filesOxygenService.sign(expid,request);
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
    		State state =filesOxygenService.cancelSign(expid,request);
    		return state;
    	} catch (IOException e) {
    		e.printStackTrace();
    		return null;
    	}
    }


}
