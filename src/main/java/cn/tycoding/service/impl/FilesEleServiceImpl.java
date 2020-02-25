package cn.tycoding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.tycoding.mapper.EgContrastMapper;
import cn.tycoding.mapper.FilesElectromyographyMapper;
import cn.tycoding.pojo.EgContrast;
import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.service.FilesEleService;
import cn.tycoding.util.DataFormatUtil;
import cn.tycoding.util.ExceptionUtil;
import cn.tycoding.util.QueryCondition;
import cn.tycoding.util.QueryUtil;
import cn.tycoding.util.SqlJointUtil;


@Service
public class FilesEleServiceImpl implements FilesEleService {
    /**
     * 注入service层
     */
    @Autowired
    private FilesElectromyographyMapper filesElectromyographyMapper;


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

	            String sql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, false, "files_electromyography");

	            List list = filesElectromyographyMapper.findByFilters(sql);
	            String getSumSql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, true, "files_electromyography");
	            int records = filesElectromyographyMapper.findByFiltersSum(getSumSql);
	            int total = QueryUtil.getTotalPage(records, rows);
	            ObjectQuery sq = new ObjectQuery(page, total, records, list);
	            return sq;
	        }
	}

	private ObjectQuery findByPage(int page, int rows) {
		// 本次操作不是搜索，而是按条件进行查询
        // 查询全部
        // page 当前所处页数 rows 每页显示的条数
        List list = filesElectromyographyMapper.findByPage((page - 1) * rows, rows);
        // 获得总记录数
        int records = filesElectromyographyMapper.getSum();
        // 获得总页数
        int total = QueryUtil.getTotalPage(records, rows);
        // 第一个参数为当前页数，第二个为总页数，第三个参数为总记录数，第四个参数为模型对象
        ObjectQuery sq = new ObjectQuery(page, total, records, list);
        return sq;
	}

	@Override
	public List find() {
		
		List list = filesElectromyographyMapper.find();
		
		return list;
	}

	@Override
    public String handle(String oper, FilesElectromyography filesElectromyography, String id[]) {
		filesElectromyography = DataFormatUtil.checkNull(filesElectromyography);
        // oper有三种操作 add,del,edit,
        switch (oper) {
            case "edit":
                // 按st_id进行更改学生数据
                if (id != null) {
//                    student.setSt_id(id[0]);
                	filesElectromyography.setExpid(Integer.valueOf(id[0]));
                }
                try {
                    int editAffectedRow = filesElectromyographyMapper.edit(filesElectromyography);
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
                for (int i = 0; i < id.length; i++) {
                	filesElectromyographyMapper.del(id[i]);
                    count++;
                }
                String str = count + "条成功删除" + (id.length - count) + "条删除失败";
                System.out.println(str);
                return str;
            case "add":
                // 新增对象
                System.out.println(filesElectromyography.toString());
                try {
                    int addAffectedRow = filesElectromyographyMapper.add(filesElectromyography);
                    if (addAffectedRow == 1) {
                        return "success";
                    }
                } catch (Exception e) {
                    return ExceptionUtil.HandleDataException(e);
                }
        }
        return "success";
    }

}
