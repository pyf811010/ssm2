package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.FilesMedia;

public interface FilesMediaMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesMedia record);

    int insertSelective(FilesMedia record);

    FilesMedia selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesMedia record);

    int updateByPrimaryKey(FilesMedia record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(FilesMedia filesMedia);

	String getPathByExpid(int expid);

	void del(String string);

	int add(FilesMedia filesMedia);

	String getFile_name(int expid);
	
	boolean dataifexist(String url); 
}