package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.FilesSlotMachine;

public interface FilesSlotMachineMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesSlotMachine record);

    int insertSelective(FilesSlotMachine record);

    FilesSlotMachine selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesSlotMachine record);

    int updateByPrimaryKey(FilesSlotMachine record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(FilesSlotMachine filesSlotMachine);

	String getPathByExpid(int expid);

	void del(String string);

	int add(FilesSlotMachine filesSlotMachine);

	String getFile_name(int expid);
}