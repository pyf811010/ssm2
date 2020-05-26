package cn.tycoding.controller;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.EgContrast;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.service.AdminService;
import cn.tycoding.service.EgContrastService;
import cn.tycoding.service.SubjectsService;
import cn.tycoding.service.UserTestService;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.Subjects;
import cn.tycoding.pojo.UserTest;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 肌电对照
 *
 * @author TyCoding
 * @date 18-4-27上午7:05
 */
@Controller
@RequestMapping(value = "/egcontrast")
public class EgContrastController {

    /**
     * 注入service
     */
    @Autowired
    private EgContrastService egContrastService;



    @RequestMapping("/findsome")
    @ResponseBody
    public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows)
            throws UnsupportedEncodingException {
        if (filters != null) {
            // 转码
            filters = new String(filters.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(filters);
        }
        return egContrastService.findByPage(_search, filters, page, rows);

    }
    
    @RequestMapping("/find")
    @ResponseBody
    public List find(){
        
        return egContrastService.find();

    }
    
    @RequestMapping("/handle")
    @ResponseBody
    public String handle(String oper, EgContrast egContrast, String id[],HttpServletRequest request )
            throws UnsupportedEncodingException {
    	return egContrastService.authorityTemp(oper,egContrast,id,request);
    }
    
    @RequestMapping("/Sign")
    @ResponseBody
    public State Sign(int expid,HttpServletRequest request) {
    	try {
    		State state = egContrastService.sign(expid,request);
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
    		State state =egContrastService.cancelSign(expid,request);
    		return state;
    	} catch (IOException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    
}
