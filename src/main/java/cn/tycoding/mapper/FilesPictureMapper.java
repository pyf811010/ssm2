package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.FilesPicture;

public interface FilesPictureMapper {
    int deleteByPrimaryKey(Integer expid);

    int insert(FilesPicture record);

    int insertSelective(FilesPicture record);

    FilesPicture selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesPicture record);

    int updateByPrimaryKey(FilesPicture record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(FilesPicture filesPicture);

	String getPathByExpid(int expid);

	void del(String string);

	int add(FilesPicture filesPicture);

	String getFile_name(int expid);
	
	boolean dataifexist(String url); 
}
