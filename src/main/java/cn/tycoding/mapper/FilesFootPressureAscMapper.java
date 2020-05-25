package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.FilesFootPressureAsc;

public interface FilesFootPressureAscMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesFootPressureAsc record);

    int insertSelective(FilesFootPressureAsc record);

    FilesFootPressureAsc selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesFootPressureAsc record);

    int updateByPrimaryKey(FilesFootPressureAsc record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	String getFile_name(int expid);

	String getPathByExpid(int expid);

	int add(FilesFootPressureAsc filesFootPressureAsc);

	void del(String string);
	
	String findTypeByUserName(String user_name); 
	
	int edit(FilesFootPressureAsc filesFootPressureAsc);
	boolean dataifexist(String url); 
	
	void sign(int expid);

	void cancelSign(int expid);
}