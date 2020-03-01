package cn.tycoding.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.tycoding.mapper.EgContrastMapper;
import cn.tycoding.mapper.FilesElectromyographyMapper;
import cn.tycoding.mapper.FilesKandUpdateinfoMapper;
import cn.tycoding.pojo.EgContrast;
import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesKandUpdateinfo;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.service.FilesEleService;
import cn.tycoding.service.FilesKandUpdateService;
import cn.tycoding.util.DataFormatUtil;
import cn.tycoding.util.ExceptionUtil;
import cn.tycoding.util.QueryCondition;
import cn.tycoding.util.QueryUtil;
import cn.tycoding.util.SqlJointUtil;


@Service
public class FilesKandUpdateServiceImpl implements FilesKandUpdateService {
    /**
     * 注入service层
     */
    @Autowired
    private FilesKandUpdateinfoMapper filesKandUpdateinfoMapper;


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

	            String sql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, false, "files_kand_updateInfo");

	            List list = filesKandUpdateinfoMapper.findByFilters(sql);
	            String getSumSql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, true, "files_kand_updateInfo");
	            int records = filesKandUpdateinfoMapper.findByFiltersSum(getSumSql);
	            int total = QueryUtil.getTotalPage(records, rows);
	            ObjectQuery sq = new ObjectQuery(page, total, records, list);
	            return sq;
	        }
	}

	private ObjectQuery findByPage(int page, int rows) {
		// 本次操作不是搜索，而是按条件进行查询
        // 查询全部
        // page 当前所处页数 rows 每页显示的条数
        List list = filesKandUpdateinfoMapper.findByPage((page - 1) * rows, rows);
        // 获得总记录数
        int records = filesKandUpdateinfoMapper.getSum();
        // 获得总页数
        int total = QueryUtil.getTotalPage(records, rows);
        // 第一个参数为当前页数，第二个为总页数，第三个参数为总记录数，第四个参数为模型对象
        ObjectQuery sq = new ObjectQuery(page, total, records, list);
        return sq;
	}

	@Override
	public List find() {
		
		List list = filesKandUpdateinfoMapper.find();
		
		return list;
	}

	@Override
    public String handle(String oper, FilesKandUpdateinfo filesKandUpdateinfo, String id[]) {
		filesKandUpdateinfo = DataFormatUtil.checkNull(filesKandUpdateinfo);
        // oper有三种操作 add,del,edit,
        switch (oper) {
            case "edit":
                // 按st_id进行更改学生数据
                if (id != null) {
//                    student.setSt_id(id[0]);
                	filesKandUpdateinfo.setU_id(Integer.valueOf(id[0]));
                }
                try {
                    int editAffectedRow = filesKandUpdateinfoMapper.edit(filesKandUpdateinfo);
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
			try {
				for (int i = 0; i < id.length; i++) {
//                	String fileName = filesKandUpdateinfoMapper.getFile_name(id[i]);
                	//将id[i]由String转为int，以便调用mapper方法
                	int u_id = Integer.parseInt(id[i]);
                	String url = filesKandUpdateinfoMapper.getPathByExpid(u_id);
                	filesKandUpdateinfoMapper.del(id[i]);
//                	url = url.substring(0, url.lastIndexOf("/"));
                	System.out.println(url);
                	File file = new File(url);
                	if(file.delete() == false){
                		System.out.println("未删除成功");
                	}
                    count++;
                }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("失败");
				e.printStackTrace();
				return "删除失败";
			}
                String str = count + "条成功删除" + (id.length - count) + "条删除失败";
                System.out.println(str);
                return str;
            case "add":
                // 新增对象
                System.out.println(filesKandUpdateinfo.toString());
                try {
                    int addAffectedRow = filesKandUpdateinfoMapper.add(filesKandUpdateinfo);
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
	public void download(int u_id, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
        State state = new State();
        String path = filesKandUpdateinfoMapper.getPathByExpid(u_id);
        String filename = filesKandUpdateinfoMapper.getFile_name(u_id);
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
        String fileName = URLEncoder.encode(filename, "utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while ((len = bis.read()) != -1) {
            out.write(len);
            out.flush();
//          if(bis.available()<=0) break;  
        }
        out.close();
        bis.close();
		
	}

}
