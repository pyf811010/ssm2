package cn.tycoding.service.impl;

import cn.tycoding.mapper.AdminMapper;
import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.service.AdminService;
import cn.tycoding.util.QueryUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Admin a = adminMapper.findByName(admin.getA_name());

        //不存在此用户
        if (null == a) {
            state = new State(false, "此用户名不存在！");
            return state;
        }

       /* if (a.getUs_type().equals("student")) {
            state = new State(false, "请进入学生入口进行登录，当前系统为教师版!");
            return state;
        }*/


        String password = a.getA_password();
        if(!(password.equals(admin.getA_password()))){
        	state = new State(false, "用户名或密码错误!");
            return state;
        }else{
        	String us_name = a.getA_name();

            //没有异常，将用户名设置进消息中
            state = new State(true, us_name);

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
            state = new State(false, "此用户名已经存在！");
            return state;
        }else{
        	state = new State(true, "管理员注册成功");
        	int i = adminMapper.insert(admin);
            if (i > 0) {
                return state;
            }
        }
        state = new State(false, "添加失败");
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
            state = new State(false, "重置失败");
        }
        if (i > 0) {
            state = new State(true, "重置成功");
        } else {
            state = new State(false, "重置失败");
        }

        return state;
	}
	
}
