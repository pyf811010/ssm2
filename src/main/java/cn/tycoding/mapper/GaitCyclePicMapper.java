package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.pojo.GaitCyclePic;

public interface GaitCyclePicMapper {
    int deleteByPrimaryKey(Integer p_id);

    int insert(GaitCyclePic record);

    int insertSelective(GaitCyclePic record);

    GaitCyclePic selectByPrimaryKey(Integer p_id);

    int updateByPrimaryKeySelective(GaitCyclePic record);

    int updateByPrimaryKey(GaitCyclePic record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(GaitCyclePic gaitCyclePic);

	String getPathByp_id(int p_id);

	String getName(int p_id);

	int add(GaitCyclePic gaitCyclePic);

	void del(String string);

	Integer getPidByFileName(String name);
	
	String findTypeByUserName(String user_name);

	void sign(int expid);

	void cancelSign(int expid); 

}