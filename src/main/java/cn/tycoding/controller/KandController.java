package cn.tycoding.controller;

import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.TmpKand;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.TmpKand;
import cn.tycoding.service.KandService;
import cn.tycoding.util.SmallExcelReaderUtil;
import cn.tycoding.util.ReflectUtil;

import cn.tycoding.mapper.TmpKandMapper;
import com.alibaba.fastjson.JSONObject;

import org.mybatis.spring.SqlSessionTemplate;
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

	@RequestMapping(value = "/findByExpid")
	@ResponseBody
	public FilesKand findByExpid(String expid) throws Exception {
		Integer id = Integer.valueOf(expid);
		FilesKand fileResult = kandService.findByExpid(id);
		String filePath = fileResult.getUrl();
		System.out.println(filePath);
		List<List<List<Object>>> excelDataList = excelUtil.getListByExcel(filePath, 2);
		System.out.println("excel readed");

		List<TmpKand> kandParams = new ArrayList<TmpKand>();

		DecimalFormat df = new DecimalFormat("000");
//      simple insert
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
//		SqlSession session = SqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//		TmpKandMapper tmpKandMapper = session.getMapper(TmpKandMapper.class);
//		Integer count = 0;
//		try {
//			for (int sheet = 0; sheet < excelDataList.size(); sheet++) {
//				Integer maxexpid = kandService.getMaxExpid();
//				count += excelDataList.get(sheet).size();
//				for (int i = 0; i < excelDataList.get(sheet).size(); i++) {
//					TmpKand tmp = new TmpKand();
//					tmp.setExpid(maxexpid);
//					tmp.setId(0);
//					ReflectUtil.setKandValues(tmp, excelDataList.get(sheet).get(i));
//					tmpKandMapper.insert(tmp);
//					if (i % 1000 == 0 || i == excelDataList.get(sheet).size() - 1) {
//						session.commit();
//						session.clearCache();
//					}
//				}
//			}
//		} catch (Exception e) {
//			// 没有提交的数据可以回滚
//			session.rollback();
//		} finally {
//			session.close();
//		}
//		System.out.println("total count:" + count);
//		 try {
//	            exec(kandParams);
//	        } catch (InterruptedException e) {
//	            e.printStackTrace();
//	        }
//		TmpKand tmp = new TmpKand();
//		System.out.println("The exceldata is"+excelDataList.get(0).get(0).get(1).toString());
//		ReflectUtil.setKandValues(tmp, "feature090", excelDataList.get(0).get(0).get(1).toString());
//		System.out.println(tmp.getFeature090());

		return null;
	}

	@RequestMapping(value = "/findAllByPage")
	@ResponseBody
	public ObjectQuery findByPage(int page, int rows)
            throws UnsupportedEncodingException {
//        if (filters != null) {
//            // 转码
//            //filters = new String(filters.getBytes("ISO-8859-1"), "UTF-8");
//            System.out.println(filters);
//        }
        return kandService.findByPage(false,null, page, rows);
    }

//	public static void exec(List<TmpKand> list) throws InterruptedException {
//		int count = 500; // 一个线程处理300条数据
//		int listSize = list.size(); // 数据集合大小
//		int runSize = (listSize / count) + 1; // 开启的线程数
//		List<TmpKand> newlist = null; // 存放每个线程的执行数据
//		System.out.println("runsize=" + runSize);
//		ExecutorService executor = Executors.newFixedThreadPool(runSize); // 创建一个线程池，数量和开启线程的数量一样
////创建两个个计数器
//		System.out.println("creating thread pool success");
//		CountDownLatch begin = new CountDownLatch(1);
//		CountDownLatch end = new CountDownLatch(runSize);
//		// 循环创建线程
//		for (int i = 0; i < runSize; i++) {
//			// 计算每个线程执行的数据
//			if ((i + 1) == runSize) {
//				int startIndex = (i * count);
//				int endIndex = list.size();
//				newlist = list.subList(startIndex, endIndex);
//			} else {
//				int startIndex = (i * count);
//				int endIndex = (i + 1) * count;
//				newlist = list.subList(startIndex, endIndex);
//			}
//			// 线程类
//			KandThreadUtil mythead = new KandThreadUtil(newlist, begin, end);
//			// 这里执行线程的方式是调用线程池里的executor.execute(mythead)方法。
//
//			executor.execute(mythead);
//		}
//
//		begin.countDown();
//		end.await();
//
//		// 执行完关闭线程池
//		executor.shutdown();
//	}

}
