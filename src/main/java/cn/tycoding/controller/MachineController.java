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
import cn.tycoding.pojo.Machine;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.service.FilesEleService;
import cn.tycoding.service.FilesKandService;
import cn.tycoding.service.MachineService;
import cn.tycoding.util.FindMachineInfo;

/**
 * 实验机器
 * @author pyf
 *
 */
@Controller
@RequestMapping(value = "/Machine")
public class MachineController {
    /**
     * 注入service
     */
    @Autowired
    private MachineService machineService;
    
    @RequestMapping("/findsome")
    @ResponseBody
    public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows)
            throws UnsupportedEncodingException {
        if (filters != null) {
            // 转码
            filters = new String(filters.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(filters);
        }
        return machineService.findByPage(_search, filters, page, rows);

    }
    
    @RequestMapping("/find")
    @ResponseBody
    public List find(){
        
        return machineService.find();

    }
    
    @RequestMapping("/find1")
    @ResponseBody
    public List find1(String[] text){

        return FindMachineInfo.findMachine(machineService.find(),FindMachineInfo.splitString(text));
    }
    
    @RequestMapping("/handle")
    @ResponseBody
    public String handle(String oper, Machine machine, String id[],HttpServletRequest request)
            throws UnsupportedEncodingException {
        return machineService.authorityTemp(oper,machine,id,request);

    }
    
    @RequestMapping("/Sign")
    @ResponseBody
    public State Sign(int expid,HttpServletRequest request) {
    	try {
    		State state = machineService.sign(expid,request);
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
    		State state =machineService.cancelSign(expid,request);
    		return state;
    	} catch (IOException e) {
    		e.printStackTrace();
    		return null;
    	}
    }


}
