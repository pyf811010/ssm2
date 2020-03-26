package cn.tycoding.service.impl;

import cn.tycoding.mapper.AdminMapper;
import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.Preec;
import cn.tycoding.pojo.State;
import cn.tycoding.service.AdminService;
import cn.tycoding.util.DataFormatUtil;
import cn.tycoding.util.ExceptionUtil;
import cn.tycoding.util.QueryCondition;
import cn.tycoding.util.QueryUtil;
import cn.tycoding.util.SqlJointUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author TyCoding
 * @date 18-4-27上午7:09
 */
@Service
public class AdminServiceImpl implements AdminService {

    /**
     * 注入service层
     */
    @Autowired
    private AdminMapper adminMapper;
    
    @Autowired
    private HttpSession session;

    /**
     * 登录的功能
     *
     * @param a_name 输入的用户名
     * @return 返回查询到的该用户名对应的密码
     */
    public Admin login(String a_name) {
        return adminMapper.login(a_name);
    }

    /**
     * 注册功能
     *
     * @param admin 注册的信息
     * @return 返回影响的行数
     */
    public void insert(Admin admin) {
        adminMapper.insert(admin);
    }

    /**
     * 根据用户名查询
     *
     * @param a_name 用户名
     * @return 返回影响的行数
     */
    public Admin findByName(String a_name) {
        return adminMapper.findByName(a_name);
    }

	public State dealLogin(Admin admin) {
		State state = null;
		String loginType = admin.getType();
        Admin a = adminMapper.findByName(admin.getA_name());
        //不存在此用户
        if (null == a) {
            state = new State(0, "此用户名不存在！");
            return state;
        }
        //获得用户类型
        String realType = a.getType();
        if (!loginType.equals(realType)) {
            state = new State(0, "用户名或密码错误!");
            return state;
        }


        String password = a.getA_password();
        if(!(password.equals(admin.getA_password()))){
        	state = new State(0, "用户名或密码错误!");
            return state;
        }else{
        	String us_name = a.getA_name();
        	 session.setAttribute("user_name", a.getA_name());
        	//将us_name放入session中,用于拦截器判断是否登陆
        	session.setAttribute("type", loginType);
        	//没有异常，将用户名设置进消息中
        	String info = us_name+":"+loginType;
            state = new State(1, info);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(state);
            System.out.println("用户ID:" + a.getA_id() + " 姓名:" + a.getA_name() + " 于" + df.format(new Date()) + " 登录系统");
            return state;
        }
	}

	@Override
	public State dealRegister(Admin admin) {
		State state = null;
        Admin a = adminMapper.findByName(admin.getA_name());

        //已存在此用户
        if (null != a) {
            state = new State(0, "此用户名已经存在！");
            return state;
        }else{
        	state = new State(1, "普通用户注册成功");
        	int i = adminMapper.insert(admin);
            if (i > 0) {
                return state;
            }
        }
        state = new State(0, "添加失败");
        return state;
	}

	@Override
	public State resetPassword(Admin admin) {
		String a_password = admin.getA_password();
		admin.setA_password(a_password);
        State state = null;
        int i = 0;
        try {
            i = adminMapper.resetPassword(admin);
        } catch (Exception e) {
            state = new State(0, "重置失败");
        }
        if (i > 0) {
            state = new State(1, "重置成功");
        } else {
            state = new State(0, "重置失败");
        }

        return state;
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

	            String sql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, false, "admin");

	            List list = adminMapper.findByFilters(sql);
	            String getSumSql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, true, "admin");
	            int records = adminMapper.findByFiltersSum(getSumSql);
	            int total = QueryUtil.getTotalPage(records, rows);
	            ObjectQuery sq = new ObjectQuery(page, total, records, list);
	            return sq;
	        }
	}

	private ObjectQuery findByPage(int page, int rows) {
		// 本次操作不是搜索，而是按条件进行查询
        // 查询全部
        // page 当前所处页数 rows 每页显示的条数
        List list = adminMapper.findByPage((page - 1) * rows, rows);
        // 获得总记录数
        int records = adminMapper.getSum();
        // 获得总页数
        int total = QueryUtil.getTotalPage(records, rows);
        // 第一个参数为当前页数，第二个为总页数，第三个参数为总记录数，第四个参数为模型对象
        ObjectQuery sq = new ObjectQuery(page, total, records, list);
        return sq;
	}

	
	@Override
    public String handle(String oper, Admin admin, String id[]) {
		admin = DataFormatUtil.checkNull(admin);
        // oper有三种操作 add,del,edit,
        switch (oper) {
            case "edit":
                // 按st_id进行更改学生数据
                if (id != null) {
//                    student.setSt_id(id[0]);
                	admin.setA_id(Integer.valueOf(id[0]));
                }
                try {
                    int editAffectedRow = adminMapper.edit(admin);
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
                	adminMapper.del(id[i]);
                    count++;
                }
                String str = count + "条成功删除" + (id.length - count) + "条删除失败";
                System.out.println(str);
                return str;
            case "add":
                // 新增对象
                System.out.println(admin.toString());
                try {
                    int addAffectedRow = adminMapper.add(admin);
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
	public List<Admin> findAll() {
		List<Admin> admin = new ArrayList<>();
		admin = adminMapper.findAll();
		return admin;
	}

}
