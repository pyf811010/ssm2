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
	public FilesKand findByIdQuery(Integer idQuery) {
		// TODO Auto-generated method stub
		return filesKandMapper.selectByIdQuery(idQuery);
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

	@Override
	public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows) {
		 if (!_search) {
	            return findByPage(page, rows);
	        } else {
	        	// 按filters中的条件查找
	            QueryCondition queryCondition = null;
	            try {
	                queryCondition = new ObjectMapper().readValue(filters, QueryCondition.class);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	            String sql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, false, "tmp_kand");

	            List list = tmpKandMapper.findByFilters(sql);
	            String getSumSql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, true, "tmp_kand");
	            int records = tmpKandMapper.findByFiltersSum(getSumSql);
	            int total = QueryUtil.getTotalPage(records, rows);
	            ObjectQuery sq = new ObjectQuery(page, total, records, list);
	            return sq;
	        }
	}

	@Override
	public ObjectQuery findByPage(int page, int rows) {
		// 本次操作不是搜索，而是按条件进行查询
        // 查询全部
        // page 当前所处页数 rows 每页显示的条数
        List list = tmpKandMapper.findByPage((page - 1) * rows, rows);
        // 获得总记录数
        int records = tmpKandMapper.getSum();
        // 获得总页数
        int total = QueryUtil.getTotalPage(records, rows);
        // 第一个参数为当前页数，第二个为总页数，第三个参数为总记录数，第四个参数为模型对象
        ObjectQuery sq = new ObjectQuery(page, total, records, list);
        return sq;
	}

}
