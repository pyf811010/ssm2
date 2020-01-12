package cn.tycoding.service;

import java.util.List;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.EgContrast;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.Subjects;
import cn.tycoding.pojo.UserTest;

public interface EgContrastService {



	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	List find();

//	List<Subjects> findAllById(int id);

	List<EgContrast> findAllById(int id);

	String handle(String oper, EgContrast egContrast, String[] id);

//	List<UserTest> findAllByRelateType(int type);
}
