package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.Preec;

public interface AdminMapper {
    
    Admin login(String a_name);

    int insert(Admin admin);

    Admin findByName(String a_name);

	int resetPassword(Admin admin);

	List findByFilters(String sql);

	int add(Admin admin);

	void del(String string);

	int edit(Admin admin);

	int getSum();

	List findByPage(int i, int rows);

	int findByFiltersSum(String getSumSql);



}
