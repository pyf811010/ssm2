package cn.tycoding.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

	String authorityTemp(String oper, EgContrast egContrast, String[] id, HttpServletRequest request) throws UnsupportedEncodingException;

//	List<UserTest> findAllByRelateType(int type);
}
