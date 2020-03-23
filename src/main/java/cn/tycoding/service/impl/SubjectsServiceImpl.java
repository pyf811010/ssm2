package cn.tycoding.service.impl;

import cn.tycoding.mapper.SubjectsMapper;
import cn.tycoding.mapper.UserTestMapper;
import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.Subjects;
import cn.tycoding.pojo.UserTest;
import cn.tycoding.service.AdminService;
import cn.tycoding.service.SubjectsService;
import cn.tycoding.service.UserTestService;
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

/**
 * @author TyCoding
 * @date 18-4-27上午7:09
 */
@Service
public class SubjectsServiceImpl implements SubjectsService {

    /**
     * 注入service层
     */
    @Autowired
    private SubjectsMapper subjectsMapper;


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

	            String sql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, false, "subjects");

	            List list = subjectsMapper.findByFilters(sql);
	            String getSumSql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, true, "subjects");
	            int records = subjectsMapper.findByFiltersSum(getSumSql);
	            int total = QueryUtil.getTotalPage(records, rows);
	            ObjectQuery sq = new ObjectQuery(page, total, records, list);
	            return sq;
	        }
	}

	private ObjectQuery findByPage(int page, int rows) {
		// 本次操作不是搜索，而是按条件进行查询
        // 查询全部
        // page 当前所处页数 rows 每页显示的条数
        List list = subjectsMapper.findByPage((page - 1) * rows, rows);
        // 获得总记录数
        int records = subjectsMapper.getSum();
        // 获得总页数
        int total = QueryUtil.getTotalPage(records, rows);
        // 第一个参数为当前页数，第二个为总页数，第三个参数为总记录数，第四个参数为模型对象
        ObjectQuery sq = new ObjectQuery(page, total, records, list);
        return sq;
	}

	@Override
	public List find() {
		
		List list = subjectsMapper.find();
		
		return list;
	}

	@Override
    public String handle(String oper, Subjects subjects, String id[]) {
		subjects = DataFormatUtil.checkNull(subjects);
        // oper有三种操作 add,del,edit,
        switch (oper) {
            case "edit":
                // 按st_id进行更改学生数据
                if (id != null) {
//                    student.setSt_id(id[0]);
                	subjects.setSub_id(Integer.valueOf(id[0]));
                }
                try {
                    int editAffectedRow = subjectsMapper.edit(subjects);
                    if (editAffectedRow == 1) {
                        return "success";
                    }
                } catch (Exception e) {
                    return ExceptionUtil.HandleDataException(e);
                }
                break;
            case "del":

                // 会按st_id来删除，考虑到存在多选，此时主键id是数组
			int count;
			count = 0;
			try {
                for (int i = 0; i < id.length; i++) {
                	subjectsMapper.del(id[i]);
                    count++;
                }
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				return "fail";
			}
                String str = count + "条成功删除" + (id.length - count) + "条删除失败";
                System.out.println(str);
                return str;
            case "add":
                // 新增对象
                System.out.println(subjects.toString());
                try {
                    int addAffectedRow = subjectsMapper.add(subjects);
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
	public List<Subjects> findAllById(int id) {
		List<Subjects> findAllById = subjectsMapper.findAllById(id);
		int size = findAllById.size();
		return findAllById;
		
	}

	/*@Override
	public List<UserTest> findAllByRelateType(int type) {
		List<UserTest> findAllByRelateType = subjectsMapper.findAllByRelateType(type);
		int size = findAllByRelateType.size();
		return findAllByRelateType;
	}*/
}
