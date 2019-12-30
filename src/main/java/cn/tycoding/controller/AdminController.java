package cn.tycoding.controller;

import cn.tycoding.pojo.Admin;
import cn.tycoding.service.AdminService;
import cn.tycoding.pojo.State;

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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 管理人员的Controller层
 *
 * @author TyCoding
 * @date 18-4-27上午7:05
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    /**
     * 注入service
     */
    @Autowired
    private AdminService adminService;


    /**
     * 跳转到系统登录首页
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

   

    @RequestMapping("/login")
    @ResponseBody
    public State dealLogin(@RequestParam(value="us_id") String a_name, @RequestParam(value="us_password") String a_password) {
    	Admin admin = new Admin();
    	admin.setA_name(a_name);
    	admin.setA_password(a_password);
        State state = adminService.dealLogin(admin);
        return state;

    }
    
    
    
    /**
     * 注册功能
     */
    @RequestMapping("/register")
    @ResponseBody
    public State dealRegister(@RequestParam(value="us_id") String a_name, @RequestParam(value="us_password") String a_password) {
    	Admin admin = new Admin();
    	admin.setA_name(a_name);
    	admin.setA_password(a_password);
    	State state = adminService.dealRegister(admin);
        return state;
    }
    

    /**
     * 退出登录的功能
     */
    @RequestMapping(value = "/outLogin")
    public String outLogin(HttpSession session) {
        session.invalidate();
        return "index";
    }

    /**
     * 跳转到page首页面
     */
    @RequestMapping(value = "/page")
    public String page() {
        return "view/page";
    }

    /**
     * 根据用户名查询
     */
    @ResponseBody
    @RequestMapping(value = "/findByName")
    public String findByName(@RequestBody Admin admin) {
        Admin info = adminService.findByName(admin.getA_name());
        System.out.println(JSONObject.toJSONString(info));
        return JSONObject.toJSONString(info);
    }
    
    /*@ResponseBody
    @RequestMapping(value = "/getAdminById")
    public Map<String, String> getAdminById(@RequestParam(value="us_id") String a_name) {
    	Map<String, String> map = new HashMap<>();
        map.put("name", a_name);
        return map;
    }*/
    
    @RequestMapping("/resetPassword")
    @ResponseBody
    public State resetPassword(Admin admin) {
        return adminService.resetPassword(admin);
    }
    
    
    
    
}
