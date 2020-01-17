package cn.tycoding.service;

import java.util.List;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.Subjects;
import cn.tycoding.pojo.UserTest;

public interface SubjectsService {



	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	List find();

	String handle(String oper, Subjects subjects, String[] id);
//	List<Subjects> findAllById(int id);

	List<Subjects> findAllById(int id);

//	List<UserTest> findAllByRelateType(int type);
}
