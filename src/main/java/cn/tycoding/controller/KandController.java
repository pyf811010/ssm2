package cn.tycoding.controller;

import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.TmpKand;
import cn.tycoding.pojo.UserTest;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.TmpKand;
import cn.tycoding.service.KandService;
import cn.tycoding.util.SmallExcelReaderUtil;
import cn.tycoding.util.ReflectUtil;

import cn.tycoding.mapper.TmpKandMapper;
import com.alibaba.fastjson.JSONObject;

import org.mybatis.spring.SqlSessionTemplate;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping(value = "/kand")
public class KandController {
	@Autowired
	private KandService kandService;
	private SmallExcelReaderUtil excelUtil;
//	private SqlSessionTemplate SqlSessionTemplate;

	@RequestMapping(value = "/findByIdQuery")
	@ResponseBody
	public State findByIdQuery(String idQuery) throws Exception {
		State message = new State();
		try {
			Integer id = Integer.valueOf(idQuery);
			FilesKand fileResult = kandService.findByIdQuery(id);
			String filePath = fileResult.getUrl();
			System.out.println(filePath);
			List<List<List<Object>>> excelDataList = excelUtil.getListByExcel(filePath, 2);
			System.out.println("excel readed");

			List<TmpKand> kandParams = new ArrayList<TmpKand>();

			DecimalFormat df = new DecimalFormat("000");
//	      simple insert
			long start=System.currentTimeMillis();
			for (int sheet = 0; sheet < excelDataList.size(); sheet++) {
				Integer maxexpid = kandService.getMaxExpid();
				kandParams.clear();
				for (int i = 0; i < excelDataList.get(sheet).size(); i++) {
					TmpKand tmp = new TmpKand();
					tmp.setExpid(maxexpid);
					tmp.setId(0);
					ReflectUtil.setKandValues(tmp, excelDataList.get(sheet).get(i));
					kandParams.add(tmp);
					if (i % 1000 == 0 || i == excelDataList.get(sheet).size() - 1) {
						kandService.insertBybatch(kandParams);
						kandParams.clear();
					}
				}
			}
			long end=System.currentTimeMillis();
			System.out.println("执行时长"+(end-start));
			message.setSuccess(true);
			message.setInfo(filePath);
		}catch(Exception e) {
			message.setSuccess(false);
			message.setInfo(e.toString());
		}
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
        return kandService.findByPage(_search, filters, page, rows);
    }
	
	 @RequestMapping("/handle")
	    @ResponseBody
	    public String handle(String oper, TmpKand tmpKand, String expid[],String id[])
	            throws UnsupportedEncodingException {
	        String temp = kandService.handle(oper, tmpKand, expid, id);
	        // 对传回的中文进行编码
	        return URLEncoder.encode(temp, "UTF-8");
	    }

}
