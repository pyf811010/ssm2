package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.FilesFootPressureFgt;

public interface FilesFootPressureFgtMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesFootPressureFgt record);

    int insertSelective(FilesFootPressureFgt record);

    FilesFootPressureFgt selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesFootPressureFgt record);

    int updateByPrimaryKey(FilesFootPressureFgt record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(FilesFootPressureFgt filesFootPressureFgt);

	String getPathByExpid(int expid);

	int add(FilesFootPressureFgt filesFootPressureFgt);

	String getFile_name(int expid);

	void del(String string);
}