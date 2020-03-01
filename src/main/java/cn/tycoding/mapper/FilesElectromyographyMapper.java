package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.FilesElectromyography;

public interface FilesElectromyographyMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesElectromyography record);

    int insertSelective(FilesElectromyography record);

    FilesElectromyography selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesElectromyography record);

    int updateByPrimaryKey(FilesElectromyography record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(FilesElectromyography filesElectromyography);

	void del(String string);

	int add(FilesElectromyography filesElectromyography);

	String getPathByExpid(int expid);

	String getFile_name(int expid);
}