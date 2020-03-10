package cn.tycoding.mapper;

import java.util.List;

import cn.tycoding.entity.assist.DBTableComment;
import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.Preec;

public interface AdminMapper {
    
    Admin login(String a_name);

    int insert(Admin admin);

    Admin findByName(String a_name);

	int resetPassword(Admin admin);

	List findByFilters(String sql);

	/**
	 * 增加管理员信息
	 * @param admin 新增的管理员实例
	 * @return 增加的条数
	 */
	int add(Admin admin);

	void del(String string);

	int edit(Admin admin);

	int getSum();

	List findByPage(int i, int rows);

	int findByFiltersSum(String getSumSql);

	List<Admin> findAll();

	/**
	 * 查找表格中所有字段的详细描述信息
	 * @return 封装了字段信息的list集合
	 */
	List<DBTableComment> findDbTableComment();

	/**
	 * 从模板中导入信息
	 * @param admin 模板文件中一行封装的admin对象
	 * @return 成功导入数据库的数量
	 */
	int addTemplate(Admin admin);
}
