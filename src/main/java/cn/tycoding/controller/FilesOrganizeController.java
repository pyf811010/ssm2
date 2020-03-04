package cn.tycoding.controller;

import cn.tycoding.pojo.FilesMontionCapture;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.FilesOxygen;
import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesFootPressureAsc;
import cn.tycoding.pojo.FilesFootPressureFgt;
import cn.tycoding.pojo.FilesSlotMachine;
import cn.tycoding.pojo.Query;
import cn.tycoding.pojo.State;
import cn.tycoding.service.FilesOrganizeService;
import cn.tycoding.service.QueryService;
import cn.tycoding.util.SmallExcelReaderUtil;
import cn.tycoding.util.ReflectUtil;

import cn.tycoding.mapper.TmpKandMapper;
import com.alibaba.fastjson.JSONObject;

import org.mybatis.spring.SqlSessionTemplate;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hamcrest.SelfDescribing;
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
@RequestMapping(value = "/filesOrganize")
public class FilesOrganizeController {
	@Autowired
	private FilesOrganizeService filesOrganizeService;
	private QueryService qeuryService;
	
	@RequestMapping(value = "/insertFiles")
	@ResponseBody
	public State insertFiles(@RequestBody String jsonFilePath) throws Exception{
		State message = new State();
		message.setSuccess(1);//初始置为成功状态（0：失败，1：成功，2：部分失败）
		JSONObject object = JSONObject.parseObject(jsonFilePath);
		String mc = object.getString("mc");
		String kand = object.getString("kand");
		String ele = object.getString("ele");
		String ox = object.getString("oxygen");
		String fpa = object.getString("fpa");
		String fpf = object.getString("fpf");
		String sm = object.getString("sm");
		
		return message;
	}
	

}
