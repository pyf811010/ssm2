package cn.tycoding.service.impl;




import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.TmpOxygen;
import cn.tycoding.pojo.FilesOxygen;
import cn.tycoding.pojo.TestOxygen;
import cn.tycoding.mapper.FilesOxygenMapper;
import cn.tycoding.mapper.TmpOxygenMapper;
import cn.tycoding.mapper.TestOxygenMapper;
import cn.tycoding.service.OxygenService;
import cn.tycoding.util.DataFormatUtil;
import cn.tycoding.util.ExceptionUtil;
import cn.tycoding.util.QueryCondition;
import cn.tycoding.util.QueryUtil;
import cn.tycoding.util.SqlJointUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
 
@Service
public class OxygenServicelmpl implements OxygenService {

	@Autowired
    private FilesOxygenMapper fileOxygenMapper;
	@Autowired
	private TmpOxygenMapper tmpOxygenMapper;
	@Autowired
	private TestOxygenMapper testOxygenMapper;

	
	@Override
	public FilesOxygen findByExpid(Integer expid) {
		// TODO Auto-generated method stub
		return fileOxygenMapper.selectByPrimaryKey(expid);
	}
	
	@Override
	public Integer getMaxExpid() {
		System.out.println("Start query");
		Integer ans = Integer.valueOf(testOxygenMapper.selectMaxExpid()) + 1;
		System.out.println("The ans is"+ans);
		return ans;
	}
	
	@Override
	public void insertList(List<TestOxygen> list) {
		testOxygenMapper.insertList(list);
	}
	

}
