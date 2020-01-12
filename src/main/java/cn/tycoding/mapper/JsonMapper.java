package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.Json;
import cn.tycoding.pojo.UserTest;

public interface JsonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Json record);

    int insertSelective(Json record);

    Json selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Json record);

    int updateByPrimaryKey(Json record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(Json json);

	void del(String string);

	int add(Json json);
}