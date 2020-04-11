package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.FilesMontionCapture;

public interface FilesMontionCaptureMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesMontionCapture record);

    int insertSelective(FilesMontionCapture record);

    FilesMontionCapture selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesMontionCapture record);

    int updateByPrimaryKey(FilesMontionCapture record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List find();

	int getSum();

	List findByPage(int i, int rows);

	int edit(FilesMontionCapture filesMontionCapture);

	String getFile_name(int expid);

	String getPathByExpid(int expid);

	int add(FilesMontionCapture filesMontionCapture);

	void del(String string);

	boolean dataifexist(String url); 
	
	String findTypeByUserName(String user_name); 
}