package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.entity.assist.DBTableComment;
import cn.tycoding.pojo.Preec;

public interface PreecMapper {
    int deleteByPrimaryKey(Integer id);

    void insert(Preec record);

    int insertSelective(Preec record);

    Preec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Preec record);

    int updateByPrimaryKey(Preec record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(Preec preec);

	void del(String string);

	int add(Preec preec);

	List<Preec> findAllById(int id);

	/**
	 * 查找表格中所有字段的详细描述信息
	 * @return 封装了字段信息的list集合
	 */
	List<DBTableComment> findDbTableComment();

	void sign(int expid);

	void cancelSign(int expid);

	List<Integer> getIdByAdvance(String fi_info);
	
	List<String> findDistinctAction();
}