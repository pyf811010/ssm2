package cn.tycoding.service;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.State;

public interface AdminService {

    Admin login(String a_name);

    void insert(Admin admin);

    Admin findByName(String a_name);
    
    public State dealLogin(Admin admin);
}
