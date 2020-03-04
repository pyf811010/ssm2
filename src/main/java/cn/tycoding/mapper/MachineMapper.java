package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.Machine;

public interface MachineMapper {
    int deleteByPrimaryKey(Integer m_id);

    int insert(Machine record);

    int insertSelective(Machine record);

    Machine selectByPrimaryKey(Integer m_id);

    int updateByPrimaryKeySelective(Machine record);

    int updateByPrimaryKey(Machine record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(Machine machine);

	void del(String string);

	int add(Machine machine);

	String getPathByM_id(int m_id);

	String getFileName(int m_id);
}