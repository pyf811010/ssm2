package cn.tycoding.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.Preec;
import cn.tycoding.pojo.State;
import cn.tycoding.pojo.UserTest;

public interface PreecService {



	ObjectQuery findByPage(Boolean _search, String filters, int page, int rows);

	List find();

	String handle(String oper, Preec preec, String[] id);
	
	List<Preec> findAllById(int id);

	String authorityTemp(String oper, Preec preec, String[] id, HttpServletRequest request) throws UnsupportedEncodingException;

	State sign(int expid, HttpServletRequest request) throws IOException;
	
	State cancelSign(int expid, HttpServletRequest request) throws IOException;
	
	List<String> findDistinctActions();
//	List<Preec> findAllByRelateType(int type);
}