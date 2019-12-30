package cn.tycoding.mapper;

import cn.tycoding.pojo.Admin;

public interface AdminMapper {
    
    Admin login(String a_name);

    int insert(Admin admin);

    Admin findByName(String a_name);

	int resetPassword(Admin admin);

}
