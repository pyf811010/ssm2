package cn.tycoding.controller;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.FilesMontionCapture;
import cn.tycoding.pojo.TmpMotionCapture;
import cn.tycoding.pojo.UserTest;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.service.AdminService;
import cn.tycoding.service.MotionCaptureService;
import cn.tycoding.util.SmallExcelReaderUtil;
import cn.tycoding.util.ReflectUtil;

import com.alibaba.fastjson.JSONObject;

import org.mybatis.spring.SqlSessionTemplate;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mybatis.spring.SqlSessionFactoryBean;
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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping(value = "/motioncapture")
public class MotionCaptureController {
	@Autowired
	private MotionCaptureService mcService;
	private SmallExcelReaderUtil excelUtil;
	private int startrow = 7;//excel文件从第startrow行开始扫描（从1开始计数）
	private int startcol = 2;//excel文件从第startcol列开始扫描（从1开始计数）
//	private SqlSessionTemplate SqlSessionTemplate;

	@RequestMapping(value = "/findByIdQuery")
	@ResponseBody
	public State findByIdQuery(String jsonIdQuery) throws Exception {
		State message = new State();
		message.setSuccess(1);//初始置为成功状态（0：失败，1：成功，2：部分失败）
		List<Integer> queryList = JSONObject.parseArray(jsonIdQuery,Integer.class);
		HashSet<Integer> h = new HashSet<Integer>(queryList);//去重
		queryList.clear();
		queryList.addAll(h);
		String INFO = "";
		mcService.truncateTable();
		int count = 0;
		for (int j = 0; j < queryList.size(); j++) {
			Integer id = queryList.get(j);
			try {
				FilesMontionCapture fileResult = mcService.findByIdQuery(id);
				String filePath = fileResult.getUrl();
				System.out.println(filePath);
				List<List<List<Object>>> excelDataList = excelUtil.getListByExcel(filePath, this.startrow, this.startcol);
				System.out.println("excel readed");

				List<TmpMotionCapture> mcParams = new ArrayList<TmpMotionCapture>();
				DecimalFormat df = new DecimalFormat("000");
//		      simple insert
				long start=System.currentTimeMillis();
				for (int sheet = 0; sheet < excelDataList.size(); sheet++) {
					//Integer maxexpid = mcService.getMaxExpid();
					mcParams.clear();
					for (int i = 0; i < excelDataList.get(sheet).size(); i++) {
						TmpMotionCapture tmp = new TmpMotionCapture();
						tmp.setExpid(id);
						tmp.setId(0);
						ReflectUtil.setValues(tmp, excelDataList.get(sheet).get(i));
						mcParams.add(tmp);
						if (i % 1000 == 0 || i == excelDataList.get(sheet).size() - 1) {
							mcService.insertBybatch(mcParams);
							mcParams.clear();
						}
					}
				}
				long end=System.currentTimeMillis();
				System.out.println("执行时长"+(end-start));
			}catch(NullPointerException e) {
				INFO = INFO+"id-Query:"+id+"的实验出错：\n"+"   不存在，请核对后再试!\n";
				message.setSuccess(2);
				count++;
				System.out.println("--------------------------------");
				continue;
			}catch(Exception e) {
				INFO = INFO+"id-Query:"+id+"的实验出错：\n"+e.toString()+"\n";
				message.setSuccess(2);
				count++;
				System.out.println("*********");
				continue;
			}
		}
		System.out.println("count:"+count+"size:"+queryList.size());
		if(count == queryList.size()) {
			INFO = "所有查询全部存在错误\n"+INFO;
			message.setSuccess(0);
		}
		message.setInfo(INFO);
		System.out.print(message.getSuccess());
		return message;
	}

	

	
	
	@RequestMapping(value = "/findByPage")
	@ResponseBody
	public ObjectQuery findByPage(Boolean _search, String filters,int page, int rows)
            throws UnsupportedEncodingException {
//        if (filters != null) {
//            // 转码
//            //filters = new String(filters.getBytes("ISO-8859-1"), "UTF-8");
//            System.out.println(filters);
//        }
        return mcService.findByPage(_search, filters, page, rows);
    }
	
	 @RequestMapping("/handle")
	    @ResponseBody
	    public String handle(String oper, TmpMotionCapture tmpKand, String expid[],String id[])
	            throws UnsupportedEncodingException {
	        String temp = mcService.handle(oper, tmpKand, expid, id);
	        // 对传回的中文进行编码
	        return URLEncoder.encode(temp, "UTF-8");
	    }

}
