package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.FilesVideo;

public interface FilesVideoMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesVideo record);

    int insertSelective(FilesVideo record);

    FilesVideo selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesVideo record);

    int updateByPrimaryKey(FilesVideo record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(FilesVideo filesSlotMachine);

	String getPathByExpid(int expid);

	void del(String string);

	int add(FilesVideo filesSlotMachine);

	String getFile_name(int expid);
	
	boolean dataifexist(String url); 
	
	String findTypeByUserName(String user_name); 
}