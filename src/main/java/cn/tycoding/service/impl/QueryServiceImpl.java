package cn.tycoding.service.impl;


import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.Query;
import cn.tycoding.service.QueryService;
import cn.tycoding.mapper.QueryMapper;
import cn.tycoding.util.DataFormatUtil;
import cn.tycoding.util.ExceptionUtil;
import cn.tycoding.util.QueryCondition;
import cn.tycoding.util.QueryUtil;
import cn.tycoding.util.SqlJointUtil;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QueryServiceImpl implements QueryService {

	@Autowired
	private QueryMapper queryMapper;
	@Autowired
	private SqlSessionTemplate SqlSessionTemplate;
	
	
	@Override 
	public int SelectMaxExpid() {
		return queryMapper.selectMaxExpid();
	}
	
	@Override
	public void insert(Query record) {
		queryMapper.insert(record);
	}

}
