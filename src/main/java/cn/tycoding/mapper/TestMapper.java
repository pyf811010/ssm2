package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.Test;

public interface TestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Test record);

    int insertSelective(Test record);

    Test selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(Test test);


	void del(String string);

	int add(Test test);

	List<Test> findAllById(int id);
}