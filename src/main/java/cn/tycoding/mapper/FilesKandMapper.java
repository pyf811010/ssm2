package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.FilesKand;

/**
 * @author pyf lsy fdd
 *  动力学与运动学表
 */
public interface FilesKandMapper {

    int deleteByPrimaryKey(Integer expid);

    int insert(FilesKand record);

    int insertSelective(FilesKand record);

    FilesKand selectByPrimaryKey(Integer expid);

    int updateByPrimaryKeySelective(FilesKand record);

    int updateByPrimaryKey(FilesKand record);
    
    FilesKand selectByIdQuery(Integer idQuery);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(FilesKand filesKand);

	void del(String string);

	int add(FilesKand filesKand);

	String getPathByExpid(int expid);

	String getFile_name(int expid);
	
	Integer getExpidByFileName(String file_name);
	
	boolean dataifexist(String url); 
	
	String findTypeByUserName(String user_name); 
	
	void sign(int expid);

	void cancelSign(int expid);


	List<Integer> getIdByAdvance(String fi_info);
	
}