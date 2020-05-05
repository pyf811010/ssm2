    
package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.entity.assist.DBTableComment;
import cn.tycoding.pojo.FilesLiterature;
import cn.tycoding.pojo.FilesOxygen;

public interface FilesLiteratureMapper {
	
	FilesLiterature selectByPrimaryKey(Integer expid);

	List findByFilters(String sql);

	List find();

	List findByPage(int i, int rows);

	int findByFiltersSum(String getSumSql);

	int getSum();

	int edit(FilesLiterature filesLiterature);

	void del(String string);

	int add(FilesLiterature filesLiterature);

	String getPathByExpid(int expid);

	String getFile_name(int expid);
	
	List<DBTableComment> findDbTableComment();
	
}