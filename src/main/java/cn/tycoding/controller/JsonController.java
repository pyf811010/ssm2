package cn.tycoding.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tycoding.pojo.Json;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.UserTest;
import cn.tycoding.service.JsonService;
import cn.tycoding.service.UserTestService;

@Controller
@RequestMapping(value = "/json")
public class JsonController {

    /**
     * 注入service
     */
    @Autowired
    private JsonService jsonService;



    @RequestMapping("/findsome")
    @ResponseBody
    public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows)
            throws UnsupportedEncodingException {
        if (filters != null) {
            // 转码
            filters = new String(filters.getBytes("ISO-8859-1"), "UTF-8");
            //此步为了解决页面编码和后台编码不一致的问题，如果不加此步骤，前端传入后台进行查询的sql条件会是乱码

            System.out.println(filters);
        }
        return jsonService.findByPage(_search, filters, page, rows);

    }
    
    @RequestMapping("/find")
    @ResponseBody
    public List find(){
        
        return jsonService.find();

    }
    
    @RequestMapping("/handle")
    @ResponseBody
    public String handle(String oper, Json json, String id[])
            throws UnsupportedEncodingException {
        String temp = jsonService.handle(oper, json, id);
        // 对传回的中文进行编码
        return URLEncoder.encode(temp, "UTF-8");
    }
    
    
    
}
