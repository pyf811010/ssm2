package cn.tycoding.controller;

import cn.tycoding.pojo.FilesOxygen;
import cn.tycoding.pojo.TmpOxygen;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.TestOxygen;
import cn.tycoding.service.OxygenService;
import cn.tycoding.util.SmallExcelReaderUtil;
import cn.tycoding.mapper.TestOxygenMapper;
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
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/oxygen")
public class OxygenController {
	@Autowired
	private OxygenService oxygenservice;
	private SmallExcelReaderUtil excelUtil;
	@RequestMapping(value = "/findByExpid")
	@ResponseBody
	public FilesOxygen findByExpid(String expid) throws Exception {
		Integer id = Integer.valueOf(expid);
		FilesOxygen fileResult = oxygenservice.findByExpid(id);
		String filePath = fileResult.getUrl();
		System.out.println(filePath);
		List<List<List<Object>>> excelDataList = excelUtil.getListByExcel(filePath,4);
		System.out.println("excel readed");
		
		Integer maxexpid = oxygenservice.getMaxExpid();
		
		System.out.println(maxexpid);
		List<TestOxygen> oxygenParams = new ArrayList<TestOxygen>();

		for(int i=0;i<excelDataList.get(0).size();i++){
			TestOxygen tmp = new TestOxygen();
			tmp.setExpid(maxexpid);
			tmp.setId(0);
			tmp.setTimestamp(Double.valueOf(excelDataList.get(0).get(i).get(0).toString()));
			tmp.setVo2(Double.valueOf(excelDataList.get(0).get(i).get(1).toString()));
			tmp.setVco2(Double.valueOf(excelDataList.get(0).get(i).get(2).toString()));
			System.out.println(i+" "+tmp.getTimestamp()+" "+ tmp.getVo2()+" "+tmp.getVco2());
			oxygenParams.add(tmp);
	    }
		oxygenservice.insertList(oxygenParams);
		return null;
	}
	
	
}
