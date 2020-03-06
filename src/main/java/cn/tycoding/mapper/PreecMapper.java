package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.Preec;

public interface PreecMapper {
    int deleteByPrimaryKey(Integer id);

    void insert(Preec record);

    int insertSelective(Preec record);

    Preec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Preec record);

    int updateByPrimaryKey(Preec record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(Preec preec);

	void del(String string);

	int add(Preec preec);

	List<Preec> findAllById(int id);
	
	
}