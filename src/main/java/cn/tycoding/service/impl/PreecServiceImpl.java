package cn.tycoding.service.impl;

import cn.tycoding.mapper.AdminMapper;
import cn.tycoding.mapper.EgContrastMapper;
import cn.tycoding.mapper.FilesElectromyographyMapper;
import cn.tycoding.mapper.FilesFootPressureAscMapper;
import cn.tycoding.mapper.FilesFootPressureFgtMapper;
import cn.tycoding.mapper.FilesKandMapper;
import cn.tycoding.mapper.FilesMontionCaptureMapper;
import cn.tycoding.mapper.FilesOxygenMapper;
import cn.tycoding.mapper.FilesSlotMachineMapper;
import cn.tycoding.mapper.FilesVideoMapper;
import cn.tycoding.mapper.PreecMapper;
import cn.tycoding.mapper.UserTestMapper;
import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.Preec;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.UserTest;
import cn.tycoding.service.AdminService;
import cn.tycoding.service.PreecService;
import cn.tycoding.service.UserTestService;
import cn.tycoding.util.DataFormatUtil;
import cn.tycoding.util.ExceptionUtil;
import cn.tycoding.util.QueryCondition;
import cn.tycoding.util.QueryUtil;
import cn.tycoding.util.SqlJointUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author TyCoding
 * @date 18-4-27上午7:09
 */
@Service
public class PreecServiceImpl implements PreecService {

    /**
     * 注入service层
     */
    @Autowired
    private PreecMapper preecMapper;
    @Autowired
    private FilesKandMapper filesKandMapper;
    @Autowired
    private FilesMontionCaptureMapper filesMotionCaptureMapper;
    @Autowired
    private FilesFootPressureAscMapper filesFootPressureAscMapper;
    @Autowired
    private FilesFootPressureFgtMapper filesFootPressureFgtMapper;
    @Autowired
    private FilesOxygenMapper filesOxygenMapper;
    @Autowired
    private FilesSlotMachineMapper filesSlotMachineMapper;
    @Autowired
    private FilesVideoMapper filesVideoMapper;
    @Autowired
    private FilesElectromyographyMapper filesElectromyographyMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private EgContrastMapper egContrastMapper;


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

	            String sql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, false, "preec");
	            System.out.println(sql);
	            List list = preecMapper.findByFilters(sql);
	            String getSumSql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, true, "preec");
	            int records = preecMapper.findByFiltersSum(getSumSql);
	            int total = QueryUtil.getTotalPage(records, rows);
	            ObjectQuery sq = new ObjectQuery(page, total, records, list);
	            return sq;
	        }
	}

	private ObjectQuery findByPage(int page, int rows) {
		// 本次操作不是搜索，而是按条件进行查询
        // 查询全部
        // page 当前所处页数 rows 每页显示的条数
        List list = preecMapper.findByPage((page - 1) * rows, rows);
        // 获得总记录数
        int records = preecMapper.getSum();
        // 获得总页数
        int total = QueryUtil.getTotalPage(records, rows);
        // 第一个参数为当前页数，第二个为总页数，第三个参数为总记录数，第四个参数为模型对象
        ObjectQuery sq = new ObjectQuery(page, total, records, list);
        return sq;
	}

	@Override
	public List find() {
		
		List list = preecMapper.find();
		
		return list;
	}

	@Override
    public String handle(String oper, Preec preec, String id[]) {
		preec = DataFormatUtil.checkNull(preec);
        // oper有三种操作 add,del,edit,
        switch (oper) {
            case "edit":
                // 按st_id进行更改学生数据
                if (id != null) {
//                    student.setSt_id(id[0]);
                	preec.setExpid(Integer.valueOf(id[0]));
                }
                try {
                    int editAffectedRow = preecMapper.edit(preec);
                    if (editAffectedRow == 1) {
                        return "success";
                    }
                } catch (Exception e) {
                    return ExceptionUtil.HandleDataException(e,"preec");
                }
                break;
            case "del":
                // 会按st_id来删除，考虑到存在多选，此时主键id是数组
                int count = 0;
                //关联删除，当删除前置实验表时，将相关的数据文件均删除
				for (int i = 0; i < id.length; i++) {
                	preecMapper.del(id[i]);
                	int expid = Integer.parseInt(id[i]);
                	String url = filesMotionCaptureMapper.getPathByExpid(expid);
                	if(url != null){
                		File file = new File(url);
                		boolean result = file.delete();
                		int tryCount = 0;
                		while (!result && tryCount++ < 10) {
                			System.gc();    //回收资源，解决因文件下载失败而造成的占用文件情况
                			result = file.delete();
                		}
                		if(result) System.out.println("成功删除动捕数据");else System.out.println("删除动补数据失败");
                	}
                    String url2 = filesKandMapper.getPathByExpid(expid);
                    if(url2!=null){
                    	File file2 = new File(url2);
                    	boolean result2 = file2.delete();
                    	int tryCount2 = 0;
                    	while (!result2 && tryCount2++ < 10) {
                    		System.gc();    //回收资源
                    		result2 = file2.delete();
                    	}
                    	if(result2) System.out.println("成功删除运动学与动力学数据");else System.out.println("删除运动学与动力学数据失败");
                    }
                    String url3 = filesFootPressureAscMapper.getPathByExpid(expid);
                    if(url3!=null){
                    	File file3 = new File(url3);
                    	boolean result3 = file3.delete();
                    	int tryCount3 = 0;
                    	while (!result3 && tryCount3++ < 10) {
                    		System.gc();    //回收资源
                    		result3 = file3.delete();
                    	}
                    	if(result3) System.out.println("成功删除足底压力asc数据");else System.out.println("删除足底压力asc数据失败");
                    }
                    String url4 = filesFootPressureFgtMapper.getPathByExpid(expid);
                    if(url4!=null){
                    	File file4 = new File(url4);
                    	boolean result4 = file4.delete();
                    	int tryCount4 = 0;
                    	while (!result4 && tryCount4++ < 10) {
                    		System.gc();    //回收资源
                    		result4 = file4.delete();
                    	}
                    	if(result4) System.out.println("成功删除足底压力fgt数据");else System.out.println("删除足底压力fgt数据失败");
                    }
                    String url5 = filesOxygenMapper.getPathByExpid(expid);
                    if(url5!=null){
                    	File file5 = new File(url5);
                    	boolean result5 = file5.delete();
                    	int tryCount5 = 0;
                    	while (!result5 && tryCount5++ < 10) {
                    		System.gc();    //回收资源
                    		result5 = file5.delete();
                    	}
                    	if(result5) System.out.println("成功删除耗氧量数据");else System.out.println("删除耗氧量数据失败");
                    }
                    String url6 = filesSlotMachineMapper.getPathByExpid(expid);
                    if(url6!=null){
                    	File file6 = new File(url6);
                    	boolean result6 = file6.delete();
                    	int tryCount6 = 0;
                    	while (!result6 && tryCount6++ < 10) {
                    		System.gc();    //回收资源
                    		result6 = file6.delete();
                    	}
                    	if(result6) System.out.println("成功删除跑台数据");else System.out.println("删除跑台数据失败");
                    }
                    String url7 = filesVideoMapper.getPathByExpid(expid);
                    if(url7!=null){
                    	File file7 = new File(url7);
                    	boolean result7 = file7.delete();
                    	int tryCount7 = 0;
                    	while (!result7 && tryCount7++ < 10) {
                    		System.gc();    //回收资源
                    		result7 = file7.delete();
                    	}
                    	if(result7) System.out.println("成功删除视频数据");else System.out.println("删除视频数据失败");
                    }
                    String url8 = filesElectromyographyMapper.getPathByExpid(expid);
                    if(url8!=null){
                    	File file8 = new File(url8);
                    	boolean result8 = file8.delete();
                    	int tryCount8 = 0;
                    	while (!result8 && tryCount8++ < 10) {
                    		System.gc();    //回收资源
                    		result8 = file8.delete();
                    	}
                    	if(result8) System.out.println("成功删除肌电数据");else System.out.println("删除肌电数据失败");
                    }
                    egContrastMapper.del(id[i]);
                	filesKandMapper.del(id[i]);
                	filesMotionCaptureMapper.del(id[i]);
                	filesFootPressureAscMapper.del(id[i]);
                	filesFootPressureFgtMapper.del(id[i]);
                	filesOxygenMapper.del(id[i]);
                	filesSlotMachineMapper.del(id[i]);
                	filesVideoMapper.del(id[i]);
                	filesElectromyographyMapper.del(id[i]);
                    count++;
                }
                String str = count + "条成功删除" + (id.length - count) + "条删除失败";
                System.out.println(str);
                return "success";
            case "add":
                // 新增对象
                System.out.println(preec.toString());
                try {
                    int addAffectedRow = preecMapper.add(preec);
                    if (addAffectedRow == 1) {
                        return "success";
                    }
                } catch (Exception e) {
                    return ExceptionUtil.HandleDataException(e,"preec");
                }
        }
        return "success";
    }
	@Override
	public List<Preec> findAllById(int id) {
		List<Preec> findAllById = preecMapper.findAllById(id);
		int size = findAllById.size();
		return findAllById;
	}

	@Override
	public String authorityTemp(String oper, Preec preec, String[] id, HttpServletRequest request) throws UnsupportedEncodingException {
		String user_name = (String) request.getSession().getAttribute("user_name");
		String type = adminMapper.findTypeByUserName(user_name);
		if(type.equals("管理员")){
			System.out.println("用户为管理员");
			if(oper.equals("add")) preec.setUser_name(user_name);
			return URLEncoder.encode(handle(oper, preec, id), "UTF-8");
		}
        System.out.println("session中的user_name -----> " + user_name);
        if(id == null){
        	//点击页面下方编辑按钮会传入后台id，而直接在修改行提交修改则不会直接传入后台id
        	String preecUser_name2 = preec.getUser_name();
        	System.out.println(preecUser_name2);
            if (!user_name.equals(preecUser_name2)) {
                System.out.println("无权限修改");
                return URLEncoder.encode("fail", "UTF-8");
            }
            String temp = handle(oper, preec, id);
            System.out.println(temp);
            // 对传回的中文进行编码
            return URLEncoder.encode(temp, "UTF-8");
        }else{
        	//增加受试者信息的判断(增加受试者信息时，id为_empty，需要数据库自增)
        	 if(id[0].equals("_empty")){
        		 preec.setUser_name(user_name);
                 String temp = handle(oper, preec, id);
                 System.out.println(temp);
                 // 对传回的中文进行编码
                 return URLEncoder.encode(temp, "UTF-8");
             }
            for (int i = 0; i < id.length; i++) {
                String preecUser_name = ((Preec) selectByPrimaryKey(Integer.valueOf(id[i]))).getUser_name();
                System.out.println("数据库中的user_name -----> " + preecUser_name);
                if (!user_name.equals(preecUser_name)) {
                    System.out.println("无权限修改");
                    return URLEncoder.encode("fail", "UTF-8");
                }
            }
            String temp = handle(oper, preec, id);
            System.out.println(temp);
            // 对传回的中文进行编码
            return URLEncoder.encode(temp, "UTF-8");
        }
	}

	private Preec selectByPrimaryKey(Integer expid) {
		return preecMapper.selectByPrimaryKey(expid);
	}

	@Override
	public State sign(int expid,HttpServletRequest request) throws IOException {
		State state = new State();
		String user_name = (String) request.getSession().getAttribute("user_name");
		String type = adminMapper.findTypeByUserName(user_name);
		if(type.equals("管理员")){
			System.out.println("用户为管理员");
			preecMapper.sign(expid);
			filesKandMapper.sign(expid);
			filesMotionCaptureMapper.sign(expid);
			filesFootPressureAscMapper.sign(expid);
			filesFootPressureFgtMapper.sign(expid);
			filesOxygenMapper.sign(expid);
			filesSlotMachineMapper.sign(expid);
			filesVideoMapper.sign(expid);
			filesElectromyographyMapper.sign(expid);
			filesElectromyographyMapper.sign(expid);
			state.setInfo("标记成功");
			state.setSuccess(1);
			return state;
		}else{
			String FilesUser_name = (selectByPrimaryKey(expid)).getUser_name();
            System.out.println("数据库中的user_name -----> " + FilesUser_name);
            if (!user_name.equals(FilesUser_name)) {
                System.out.println("无权限修改");
    			state.setInfo("无标记权限");
    			state.setSuccess(0);//0代表失败
    			return state;
            }else{
            	preecMapper.sign(expid);
    			filesKandMapper.sign(expid);
    			filesMotionCaptureMapper.sign(expid);
    			filesFootPressureAscMapper.sign(expid);
    			filesFootPressureFgtMapper.sign(expid);
    			filesOxygenMapper.sign(expid);
    			filesSlotMachineMapper.sign(expid);
    			filesVideoMapper.sign(expid);
    			filesElectromyographyMapper.sign(expid);
    			filesElectromyographyMapper.sign(expid);
            	state.setInfo("标记成功");
    			state.setSuccess(1);
    			return state;
            }
		}
		
	}
	
	@Override
	public List<String> findDistinctActions(){
		return preecMapper.findDistinctAction();
	}

	@Override
	public State cancelSign(int expid,HttpServletRequest request) throws IOException {
		State state = new State();
		String user_name = (String) request.getSession().getAttribute("user_name");
		String type = adminMapper.findTypeByUserName(user_name);
		if(type.equals("管理员")){
			System.out.println("用户为管理员");
			preecMapper.cancelSign(expid);
			filesKandMapper.cancelSign(expid);
			filesMotionCaptureMapper.cancelSign(expid);
			filesFootPressureAscMapper.cancelSign(expid);
			filesFootPressureFgtMapper.cancelSign(expid);
			filesOxygenMapper.cancelSign(expid);
			filesSlotMachineMapper.cancelSign(expid);
			filesVideoMapper.cancelSign(expid);
			filesElectromyographyMapper.cancelSign(expid);
			filesElectromyographyMapper.cancelSign(expid);
			state.setInfo("标记成功");
			state.setSuccess(1);
			return state;
		}else{
			String FilesUser_name = (selectByPrimaryKey(expid)).getUser_name();
            System.out.println("数据库中的user_name -----> " + FilesUser_name);
            if (!user_name.equals(FilesUser_name)) {
                System.out.println("无权限修改");
    			state.setInfo("无标记权限");
    			state.setSuccess(0);//0代表失败
    			return state;
            }else{
            	preecMapper.cancelSign(expid);
    			filesKandMapper.cancelSign(expid);
    			filesMotionCaptureMapper.cancelSign(expid);
    			filesFootPressureAscMapper.cancelSign(expid);
    			filesFootPressureFgtMapper.cancelSign(expid);
    			filesOxygenMapper.cancelSign(expid);
    			filesSlotMachineMapper.cancelSign(expid);
    			filesVideoMapper.cancelSign(expid);
    			filesElectromyographyMapper.cancelSign(expid);
    			filesElectromyographyMapper.cancelSign(expid);
            	state.setInfo("标记成功");
    			state.setSuccess(1);
    			return state;
            }
		}
	}

	
}