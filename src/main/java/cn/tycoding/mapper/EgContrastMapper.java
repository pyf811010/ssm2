package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.entity.assist.DBTableComment;
import cn.tycoding.pojo.EgContrast;
import cn.tycoding.pojo.Subjects;

public interface EgContrastMapper {
    int deleteByPrimaryKey(Integer EXPID);

    int insert(EgContrast record);

    int insertSelective(EgContrast record);

    EgContrast selectByPrimaryKey(Integer EXPID);

    int updateByPrimaryKeySelective(EgContrast record);

    int updateByPrimaryKey(EgContrast record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List<EgContrast> findAllById(int id);

	/**
	 * 查找表格中所有字段的详细描述信息
	 * @return 封装了字段信息的list集合
	 */
	List<DBTableComment> findDbTableComment();

	int add(EgContrast egContrast);

	void del(String string);

	int edit(EgContrast egContrast);

	List findByPage(int i, int rows);

	int getSum();


	List find();
}