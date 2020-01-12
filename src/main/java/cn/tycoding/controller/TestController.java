package cn.tycoding.controller;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.EgContrast;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.service.AdminService;
import cn.tycoding.service.EgContrastService;
import cn.tycoding.service.SubjectsService;
import cn.tycoding.service.TestService;
import cn.tycoding.service.UserTestService;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.Subjects;
import cn.tycoding.pojo.Test;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 管理人员的Controller层
 *
 * @author TyCoding
 * @date 18-4-27上午7:05
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    /**
     * 注入service
     */
    @Autowired
    private TestService testService;



    @RequestMapping("/findsome")
    @ResponseBody
    public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows)
            throws UnsupportedEncodingException {
        if (filters != null) {
            // 转码
            filters = new String(filters.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(filters);
        }
        return testService.findByPage(_search, filters, page, rows);

    }
    
    @RequestMapping("/find")
    @ResponseBody
    public List find(){
        
        return testService.find();

    }
    
    @RequestMapping("/handle")
    @ResponseBody
    public String handle(String oper, Test test, String id[])
            throws UnsupportedEncodingException {
    	System.out.println(id);
        String temp = testService.handle(oper, test, id);
        // 对传回的中文进行编码
        return URLEncoder.encode(temp, "UTF-8");
    }
    
    /*@RequestMapping("/score")
    @ResponseBody
    public List<Subjects> usertestInfo(int id) {
        return subjectsService.findAllById(id);
    }*/
    
    
}
