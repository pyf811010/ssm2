package cn.tycoding.service;

import java.util.List;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.EgContrast;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.Subjects;
import cn.tycoding.pojo.Test;
import cn.tycoding.pojo.UserTest;

public interface TestService {



	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	List find();

//	List<Subjects> findAllById(int id);

	List<Test> findAllById(int id);


	String handle(String oper, Test test, String[] id);

//	List<UserTest> findAllByRelateType(int type);
}
