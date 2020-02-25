package cn.tycoding.service.impl;
import cn.tycoding.pojo.TmpMotionCapture;
import cn.tycoding.pojo.FilesMontionCapture;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.mapper.FilesMontionCaptureMapper;
import cn.tycoding.mapper.TmpMotionCaptureMapper;
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
	private TmpMotionCaptureMapper tmpMCMapper;
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
		return Integer.valueOf(tmpMCMapper.selectMaxExpid()) + 1;

	}
	
	@Override
	public void insertList(List<TmpMotionCapture> list) {
		tmpMCMapper.insertList(list);
	}
	
	@Override
	public void insertBybatch(List<TmpMotionCapture> list) {
		SqlSession sqlSession = SqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		TmpMotionCaptureMapper mapper = sqlSession.getMapper(TmpMotionCaptureMapper.class);
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

	            String sql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, false, "tmp_MC");

	            List list = tmpMCMapper.findByFilters(sql);
	            String getSumSql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, true, "tmp_MC");
	            int records = tmpMCMapper.findByFiltersSum(getSumSql);
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
        List list = tmpMCMapper.findByPage((page - 1) * rows, rows);
        // 获得总记录数
        int records = tmpMCMapper.getSum();
        // 获得总页数
        int total = QueryUtil.getTotalPage(records, rows);
        // 第一个参数为当前页数，第二个为总页数，第三个参数为总记录数，第四个参数为模型对象
        ObjectQuery sq = new ObjectQuery(page, total, records, list);
        return sq;
	}
	
	@Override
    public String handle(String oper, TmpMotionCapture tmpMC,String expid[], String id[]) {
		tmpMC = DataFormatUtil.checkNull(tmpMC);
        // oper有三种操作 add,del,edit,
        switch (oper) {
            case "edit":
                // 按st_id进行更改学生数据
                if (id != null && expid != null) {
//                    student.setSt_id(id[0]);
                	tmpMC.setExpid(Integer.valueOf(expid[0]));
                    tmpMC.setId(Integer.valueOf(id[0]));
                }
                try {
                    int editAffectedRow = tmpMCMapper.updateByPrimaryKeySelective(tmpMC);
                    if (editAffectedRow == 1) {
                        return "success";
                    }
                } catch (Exception e) {
                    return ExceptionUtil.HandleDataException(e);
                }
                break;
            case "del":

                // 会按st_id来删除，考虑到存在多选，此时主键id是数组
                int count = 0;
                for (int i = 0; i <expid.length; i++) {
                	for (int j = 0; j < id.length; j++) {
                    	tmpMCMapper.del(expid[i],id[j]);
                        count++;
                    }
                }
                String str = count + "条成功删除" + (id.length - count) + "条删除失败";
                System.out.println(str);
                return str;
            case "add":
                // 新增对象
                System.out.println(tmpMCMapper.toString());
                try {
                    int addAffectedRow = tmpMCMapper.insert(tmpMC);
                    if (addAffectedRow == 1) {
                        return "success";
                    }
                } catch (Exception e) {
                    return ExceptionUtil.HandleDataException(e);
                }
        }
        return "success";
    }
	
	@Override
	public void truncateTable() {
		tmpMCMapper.truncate();
	}
}
