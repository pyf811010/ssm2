package cn.tycoding.service.impl;

import cn.tycoding.pojo.FilesMontionCapture;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.mapper.FilesMontionCaptureMapper;
import cn.tycoding.service.MotionCaptureService;
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
public class MotionCaptureServiceImpl implements MotionCaptureService{
	@Autowired
	private FilesMontionCaptureMapper filesMCMapper;
	@Autowired
	private SqlSessionTemplate SqlSessionTemplate;
	
	@Override
	public FilesMontionCapture findByExpid(Integer expid) {
		// TODO Auto-generated method stub
		return filesMCMapper.selectByPrimaryKey(expid);
	}
	
	@Override
	public FilesMontionCapture findByIdQuery(Integer idQuery) {
		// TODO Auto-generated method stub
		return filesMCMapper.selectByIdQuery(idQuery);
	}

	@Override
	public Integer getMaxExpid() {
		return Integer.valueOf(filesMCMapper.selectMaxExpid()) + 1;
	}

}
