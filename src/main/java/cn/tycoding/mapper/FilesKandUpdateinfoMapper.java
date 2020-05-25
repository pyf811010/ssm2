package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.FilesKandUpdateinfo;

public interface FilesKandUpdateinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FilesKandUpdateinfo record);

    int insertSelective(FilesKandUpdateinfo record);

    FilesKandUpdateinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FilesKandUpdateinfo record);

    int updateByPrimaryKey(FilesKandUpdateinfo record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(FilesKandUpdateinfo filesKandUpdateinfo);

	void del(String string);

	int add(FilesKandUpdateinfo filesKandUpdateinfo);

	String getPathByExpid(int id);
	
	String getFile_name(int id);

	int getNameCount(String subname);
	
	String findTypeByUserName(String user_name); 
	
	void sign(int expid);

	void cancelSign(int expid);
	

}