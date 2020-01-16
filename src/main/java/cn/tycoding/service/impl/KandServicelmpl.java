package cn.tycoding.service.impl;

import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.FilesOxygen;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.TmpKand;
import cn.tycoding.mapper.FilesKandMapper;
import cn.tycoding.mapper.TmpKandMapper;
import cn.tycoding.service.KandService;
import cn.tycoding.util.DataFormatUtil;
import cn.tycoding.util.ExceptionUtil;
import cn.tycoding.util.QueryCondition;
import cn.tycoding.util.QueryUtil;
import cn.tycoding.util.SqlJointUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KandServicelmpl implements KandService {

	@Autowired
	private FilesKandMapper filesKandMapper;
	@Autowired
	private TmpKandMapper tmpKandMapper;
	@Autowired
	private SqlSessionTemplate SqlSessionTemplate;
	
	@Override
	public FilesKand findByExpid(Integer expid) {
		// TODO Auto-generated method stub
		return filesKandMapper.selectByPrimaryKey(expid);
	}

	@Override
	public Integer getMaxExpid() {
		return Integer.valueOf(tmpKandMapper.selectMaxExpid()) + 1;

	}
	
	@Override
	public void insertList(List<TmpKand> list) {
		tmpKandMapper.insertList(list);
	}
	
	@Override
	public void insertBybatch(List<TmpKand> list) {
		SqlSession sqlSession = SqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		TmpKandMapper mapper = sqlSession.getMapper(TmpKandMapper.class);
		try {
			for (int i = 0; i < list.size(); ++i) {
				mapper.insert(list.get(i));
			}
			sqlSession.commit();
			sqlSession.clearCache();
		} catch(Exception e) {
			// 没有提交的数据可以回滚
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
	}
}
