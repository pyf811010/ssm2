package cn.tycoding.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tycoding.entity.assist.DBTableComment;
import cn.tycoding.pojo.Subjects;

public interface SubjectsMapper {
    int deleteByPrimaryKey(Integer ID);

    int insert(Subjects record);

    int insertSelective(Subjects record);

    Subjects selectByPrimaryKey(Integer ID);

    int updateByPrimaryKeySelective(Subjects record);

    int updateByPrimaryKey(Subjects record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(Subjects subjects);

	void del(String id);

	int add(Subjects subjects);


	List<Subjects> findAllById(int id);

	/**
	 * 查找表格中所有字段的详细描述信息
	 * @return 封装了字段信息的list集合
	 */
	List<DBTableComment> findDbTableComment();
	
	Integer selectExistSubject(@Param("identity_card") String identity_card, @Param("name")String name, @Param("age")Integer age,@Param("weight") Float weight,@Param("height") Float height);
	
	Integer insertReturnID(Subjects record);
}