package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.entity.assist.DBTableComment;
import cn.tycoding.pojo.Machine;

public interface MachineMapper {
    int deleteByPrimaryKey(Integer m_id);

    int insert(Machine record);

    int insertSelective(Machine record);

    Machine selectByPrimaryKey(Integer m_id);

    int updateByPrimaryKeySelective(Machine record);

    int updateByPrimaryKey(Machine record);

	List findByFilters(String sql);

	int findByFiltersSum(String getSumSql);

	List findByPage(int i, int rows);

	int getSum();

	List find();

	int edit(Machine machine);

	void del(String string);

	/**
	 * 增加实验机器信息
	 * @param machine 新增的实验机器对象
	 * @return	新增机器个数/数据库插入记录条数
	 */
	int add(Machine machine);

	String getPathByM_id(int m_id);

	String getFileName(int m_id);

	/**
	 * 查找表格中所有字段的详细描述信息
	 * @return 封装了字段信息的list集合
	 */
	List<DBTableComment> findDbTableComment();

	/**
	 * 从模板文件中导入信息
	 * @param machine excel文件中一行信息封装的machine对象
	 * @return 返回插入成功的数据数量
	 */
	int addTemplate(Machine machine);
	
	String findTypeByUserName(String user_name);

	void sign(int expid);

	void cancelSign(int expid); 
}