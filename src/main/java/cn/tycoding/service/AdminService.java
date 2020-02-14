package cn.tycoding.service;

import java.util.List;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.Preec;
import cn.tycoding.pojo.State;

public interface AdminService {

    Admin login(String a_name);

    void insert(Admin admin);

    Admin findByName(String a_name);
    
    public State dealLogin(Admin admin);

	State dealRegister(Admin admin);

	State resetPassword(Admin admin);

	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	String handle(String oper, Admin admin, String[] id);

	List<Admin> findAll();


}
