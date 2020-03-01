    
package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.FilesOxygen;

public interface FilesOxygenMapper {

    int deleteByPrimaryKey(Integer expid);

    int insert(FilesOxygen record);

    int insertSelective(FilesOxygen record);

    FilesOxygen selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesOxygen record);

    int updateByPrimaryKey(FilesOxygen record);

	List findByFilters(String sql);

	List find();

	List findByPage(int i, int rows);

	int findByFiltersSum(String getSumSql);

	int getSum();

	int edit(FilesOxygen filesOxygen);

	void del(String string);

	int add(FilesOxygen filesOxygen);

	String getPathByExpid(int expid);

	String getFile_name(int expid);
}