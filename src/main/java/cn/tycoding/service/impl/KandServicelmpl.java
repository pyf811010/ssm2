package cn.tycoding.service.impl;

import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.FilesOxygen;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.TmpKand;
import cn.tycoding.pojo.UserTest;
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

import org.apache.commons.io.filefilter.AndFileFilter;
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
	
	@Override
    public String handle(String oper, TmpKand tmpKand,String expid[], String id[]) {
		tmpKand = DataFormatUtil.checkNull(tmpKand);
        // oper有三种操作 add,del,edit,
        switch (oper) {
            case "edit":
                // 按st_id进行更改学生数据
                if (id != null && expid != null) {
//                    student.setSt_id(id[0]);
                	tmpKand.setExpid(Integer.valueOf(expid[0]));
                    tmpKand.setId(Integer.valueOf(id[0]));
                }
                try {
                    int editAffectedRow = tmpKandMapper.updateByPrimaryKeySelective(tmpKand);
                    if (editAffectedRow == 1) {
                        return "success";
                    }
                } catch (Exception e) {
                    return "fail";
                }
                break;
            case "del":

                // 会按st_id来删除，考虑到存在多选，此时主键id是数组
                int count = 0;
                for (int i = 0; i <expid.length; i++) {
                	for (int j = 0; j < id.length; j++) {
                    	tmpKandMapper.del(expid[i],id[j]);
                        count++;
                    }
                }
                String str = count + "条成功删除" + (id.length - count) + "条删除失败";
                System.out.println(str);
                return str;
            case "add":
                // 新增对象
                System.out.println(tmpKand.toString());
                try {
                    int addAffectedRow = tmpKandMapper.insert(tmpKand);
                    if (addAffectedRow == 1) {
                        return "success";
                    }
                } catch (Exception e) {
                    return "fail";
                }
        }
        return "success";
    }
	
	@Override
	public void truncateTable() {
		tmpKandMapper.truncate();
	}

}
