package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.UserTest;

public interface UserTestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTest record);

    int insertSelective(UserTest record);

    UserTest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserTest record);

    int updateByPrimaryKey(UserTest record);
    
	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(UserTest usertest);

	void del(String string);

	int add(UserTest usertest);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);
<<<<<<< HEAD
=======
	List<UserTest> findAllById(int id);

	List<UserTest> findAllByRelateType(int type);
>>>>>>> dev
}