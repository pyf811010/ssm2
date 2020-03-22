package cn.tycoding.controller;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.Preec;
import cn.tycoding.service.AdminService;
import cn.tycoding.pojo.State;

import com.alibaba.fastjson.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
    public State dealLogin(@RequestParam(value="us_id") String a_name, @RequestParam(value="us_password") String a_password,String type) {
    	Admin admin = new Admin();
    	admin.setA_name(a_name);
    	admin.setA_password(a_password);
    	admin.setType(type);
        State state = adminService.dealLogin(admin);
        return state;

    }
    
    
    
    /**
     * 注册功能
     */
    @RequestMapping("/register")
    @ResponseBody
    public State dealRegister(@RequestParam(value="us_id") String a_name, @RequestParam(value="us_password") String a_password,String type) {
    	Admin admin = new Admin();
    	admin.setA_name(a_name);
    	admin.setA_password(a_password);
    	admin.setType("普通用户");
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
     * 打开matlab可执行文件
     */
    @RequestMapping(value = "/open")
    public void open() {
    	Runtime runtime = Runtime.getRuntime();  
    	try {
			Process exec = runtime.exec("C:\\Users\\pyf\\Desktop\\lab_mo_cap\\for_testing\\lab_mo_cap.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
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
    
    //对管理员密码进行重置
    @RequestMapping("/resetPassword")
    @ResponseBody
    public State resetPassword(Admin admin) {
        return adminService.resetPassword(admin);
    }
    
    @RequestMapping("/findsome")
    @ResponseBody
    public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows)
            throws UnsupportedEncodingException {
        if (filters != null) {
            // 转码
            filters = new String(filters.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(filters);
        }
        return adminService.findByPage(_search, filters, page, rows);

    }
    
    @RequestMapping("/handle")
    @ResponseBody
    public String handle(String oper, Admin admin, String id[])
            throws UnsupportedEncodingException {
        String temp = adminService.handle(oper, admin, id);
        // 对传回的中文进行编码
        return URLEncoder.encode(temp, "UTF-8");
    }
    
    /**
     * 用于将修改后的数据保存为excel格式的文件
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save")
	@ResponseBody
	public State save() throws Exception {
		State message = new State();
		message.setSuccess(1);//初始置为成功状态（0：失败，1：成功，2：部分失败）
		String INFO = "";
		try {
			List<Admin> admin = new ArrayList<>();
			admin = adminService.findAll();
			// 创建HSSFWorkbook对象(excel的文档对象)
			HSSFWorkbook wb = new HSSFWorkbook();
			// 建立新的sheet对象（excel的表单）
			HSSFSheet sheet = wb.createSheet("FXT");
			// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
			HSSFRow row1 = sheet.createRow(0);
			// 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
			HSSFCell cellOne = row1.createCell(0);
			// 设置单元格内容
			cellOne.setCellValue("a_id");
			HSSFCell cellTwo = row1.createCell(1);
			// 设置单元格内容
			cellTwo.setCellValue("a_name");
			HSSFCell cellThree = row1.createCell(2);
			// 设置单元格内容
			cellThree.setCellValue("a_password");
			//行数
			int rowNum = 1;
			List<List<Object>> content = new ArrayList<List<Object>>();
			for (Admin a : admin) {
			    int a_id = a.getA_id();
			    String a_name = a.getA_name();
			    String a_password = a.getA_password();
			    //创建一行行记录
			    // 在sheet里创建下一行
			    HSSFRow newRow = sheet.createRow(rowNum);
			    newRow.createCell(0).setCellValue(a_id);
			    newRow.createCell(1).setCellValue(a_name);
			    newRow.createCell(2).setCellValue(a_password);
			    rowNum++;
			}
			String path = "D:/a/b.xls";
            File file = new File(path);
            //如果已经存在则删除
            if (file.exists()) {
                file.delete();
            }
            //检查父包是否存在
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            //创建文件
            file.createNewFile();
            FileOutputStream fout = new FileOutputStream(path);
            wb.write(fout);
            String str = "导出成功！";
            System.out.println(str);
            fout.close();
            INFO = "保存成功"+INFO;
            message.setInfo(INFO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String str1 = "导出失败！";
            System.out.println(str1);
            INFO = "保存失败"+INFO;
            message.setSuccess(0);
            message.setInfo(INFO);
		}
		return message;
	}
    
    
    
   
}
